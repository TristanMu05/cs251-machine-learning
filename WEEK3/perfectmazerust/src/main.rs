use macroquad::prelude::*;
use ::rand::Rng;
use ::rand::thread_rng;

const COLS: usize = 45;
const ROWS: usize = 45;
const N_CELLS: usize = COLS * ROWS;

const SIZE: f32 = 15.0;
const OFFSET: f32 = 5.0;

#[derive(Clone, Copy)]
struct MazeCell {
    x: f32,
    y: f32,
    north: bool,
    south: bool,
    east: bool,
    west: bool,
    visited: bool, // used for maze generation, not runner
}

impl MazeCell {
    fn new(col: usize, row: usize) -> Self {
        let x = col as f32 * SIZE + OFFSET;
        let y = row as f32 * SIZE + OFFSET;

        Self {
            x,
            y,
            north: true,
            south: true,
            east: true,
            west: true,
            visited: false,
        }
    }
}

fn init_maze() -> Vec<MazeCell> {
    let mut maze = Vec::with_capacity(N_CELLS);
    for r in 0..ROWS {
        for c in 0..COLS {
            maze.push(MazeCell::new(c, r));
        }
    }
    maze
}

fn build_maze(maze: &mut [MazeCell]) {
    let mut rng = thread_rng();

    let mut stack: Vec<usize> = Vec::new();
    let mut visited_cells = 1usize;

    let mut cell_id = 0usize;
    maze[cell_id].visited = true;
    stack.push(cell_id);

    while visited_cells < N_CELLS {
        let mut options: Vec<(char, usize)> = Vec::new();

        // N
        if maze[cell_id].north && cell_id >= COLS {
            let next = cell_id - COLS;
            if !maze[next].visited {
                options.push(('N', next));
            }
        }
        // W
        if maze[cell_id].west && (cell_id % COLS != 0) {
            let next = cell_id - 1;
            if !maze[next].visited {
                options.push(('W', next));
            }
        }
        // E
        if maze[cell_id].east && (cell_id % COLS != COLS - 1) {
            let next = cell_id + 1;
            if !maze[next].visited {
                options.push(('E', next));
            }
        }
        // S
        if maze[cell_id].south && cell_id < (COLS * ROWS - COLS) {
            let next = cell_id + COLS;
            if !maze[next].visited {
                options.push(('S', next));
            }
        }

        if !options.is_empty() {
            let pick = rng.gen_range(0..options.len());
            let (dir, next_id) = options[pick];

            match dir {
                'N' => {
                    maze[cell_id].north = false;
                    maze[next_id].south = false;
                }
                'S' => {
                    maze[cell_id].south = false;
                    maze[next_id].north = false;
                }
                'E' => {
                    maze[cell_id].east = false;
                    maze[next_id].west = false;
                }
                'W' => {
                    maze[cell_id].west = false;
                    maze[next_id].east = false;
                }
                _ => {}
            }

            cell_id = next_id;
            maze[cell_id].visited = true;
            stack.push(cell_id);
            visited_cells += 1;
        } else {
            stack.pop();
            if let Some(&new_top) = stack.last() {
                cell_id = new_top;
            } else {
                break;
            }
        }
    }

    // Important: reset generation visited flags so runner can have its own visited array
    for cell in maze.iter_mut() {
        cell.visited = false;
    }
}

fn draw_maze(maze: &[MazeCell]) {
    for cell in maze {
        let x = cell.x;
        let y = cell.y;

        if cell.north {
            draw_line(x, y, x + SIZE, y, 1.0, BLACK);
        }
        if cell.south {
            draw_line(x, y + SIZE, x + SIZE, y + SIZE, 1.0, BLACK);
        }
        if cell.east {
            draw_line(x + SIZE, y, x + SIZE, y + SIZE, 1.0, BLACK);
        }
        if cell.west {
            draw_line(x, y, x, y + SIZE, 1.0, BLACK);
        }
    }
}

fn cell_center(maze: &[MazeCell], id: usize) -> (f32, f32) {
    let c = maze[id];
    (c.x + SIZE / 2.0, c.y + SIZE / 2.0)
}

fn cell_rect(maze: &[MazeCell], id: usize) -> (f32, f32, f32, f32) {
    let c = maze[id];
    (c.x, c.y, SIZE, SIZE)
}

struct MazeRunnerDFS {
    current_cell: usize,
    stack: Vec<usize>,
    visited: Vec<bool>,
    backtracked: Vec<bool>,
    finished: bool,
}

impl MazeRunnerDFS {
    fn new() -> Self {
        let mut stack = Vec::new();
        stack.push(0);

        let mut visited = vec![false; N_CELLS];
        visited[0] = true;

        Self {
            current_cell: 0,
            stack,
            visited,
            backtracked: vec![false; N_CELLS],
            finished: false,
        }
    }

    fn reset(&mut self) {
        *self = Self::new();
    }

    fn step(&mut self, maze: &[MazeCell]) {
        if self.finished || self.stack.is_empty() {
            return;
        }

        self.current_cell = *self.stack.last().unwrap();

        if self.current_cell == N_CELLS - 1 {
            self.finished = true;
            return;
        }

        let cell = maze[self.current_cell];

        // Try moves in order (matches your Java):
        // N, S, E, W
        if self.try_move(maze, &cell, -(COLS as isize), !cell.north) {
            return;
        }
        if self.try_move(maze, &cell, COLS as isize, !cell.south) {
            return;
        }
        if self.try_move(maze, &cell, 1, !cell.east) {
            return;
        }
        if self.try_move(maze, &cell, -1, !cell.west) {
            return;
        }

        // No moves: backtrack
        self.backtracked[self.current_cell] = true;
        self.stack.pop();
    }

    fn try_move(&mut self, _maze: &[MazeCell], _cell: &MazeCell, delta: isize, open: bool) -> bool {
        if !open {
            return false;
        }

        let next_isize = self.current_cell as isize + delta;
        if next_isize < 0 || next_isize >= N_CELLS as isize {
            return false;
        }
        let next = next_isize as usize;

        if self.visited[next] {
            return false;
        }

        self.visited[next] = true;
        self.stack.push(next);
        true
    }
}

fn draw_runner_overlay(maze: &[MazeCell], runner: &MazeRunnerDFS) {
    // Goal cell highlight
    let (gx, gy, gw, gh) = cell_rect(maze, N_CELLS - 1);
    draw_rectangle(gx, gy, gw, gh, Color::new(0.8, 1.0, 0.8, 0.5));

    // Visited + backtracked overlays
    for i in 0..N_CELLS {
        if runner.visited[i] {
            let (x, y, w, h) = cell_rect(maze, i);
            draw_rectangle(x, y, w, h, Color::new(0.7, 0.85, 1.0, 0.35));
        }
        if runner.backtracked[i] {
            let (x, y, w, h) = cell_rect(maze, i);
            draw_rectangle(x, y, w, h, Color::new(1.0, 0.75, 0.75, 0.35));
        }
    }

    // Current cell highlight
    let (cx, cy, cw, ch) = cell_rect(maze, runner.current_cell);
    draw_rectangle(cx, cy, cw, ch, Color::new(1.0, 1.0, 0.5, 0.6));

    // Draw current position dot
    let (px, py) = cell_center(maze, runner.current_cell);
    draw_circle(px, py, SIZE * 0.18, BLACK);

    // Draw path (stack) as connected line
    if runner.stack.len() >= 2 {
        for w in runner.stack.windows(2) {
            let (a, b) = (w[0], w[1]);
            let (ax, ay) = cell_center(maze, a);
            let (bx, by) = cell_center(maze, b);
            draw_line(ax, ay, bx, by, 2.0, BLACK);
        }
    }
}

#[macroquad::main("Perfect MazeRunner DFS")]
async fn main() {
    let mut maze = init_maze();
    build_maze(&mut maze);

    let mut runner = MazeRunnerDFS::new();

    let mut auto_run = true;
    let mut steps_per_second: f32 = 120.0;
    let mut step_accumulator: f32 = 0.0;

    loop {
        // Controls
        if is_key_pressed(KeyCode::Space) {
            auto_run = !auto_run;
        }
        if is_key_pressed(KeyCode::N) {
            runner.step(&maze);
        }
        if is_key_pressed(KeyCode::R) {
            maze = init_maze();
            build_maze(&mut maze);
            runner.reset();
        }
        if is_key_pressed(KeyCode::Up) {
            steps_per_second = (steps_per_second + 30.0).min(2000.0);
        }
        if is_key_pressed(KeyCode::Down) {
            steps_per_second = (steps_per_second - 30.0).max(1.0);
        }

        // Auto-run stepping using delta time
        if auto_run && !runner.finished {
            step_accumulator += get_frame_time() * steps_per_second;
            while step_accumulator >= 1.0 && !runner.finished {
                runner.step(&maze);
                step_accumulator -= 1.0;
            }
        }

        clear_background(WHITE);

        // Draw overlays first so walls stay crisp on top
        draw_runner_overlay(&maze, &runner);
        draw_maze(&maze);

        // HUD text
        let status = if runner.finished { "FINISHED" } else { "RUNNING" };
        let mode = if auto_run { "AUTO" } else { "PAUSED" };
        draw_text(
            &format!(
                "Status: {status} | Mode: {mode} | Steps/s: {:.0} | Space=toggle, N=step, R=regen, Up/Down=speed",
                steps_per_second
            ),
            10.0,
            20.0,
            18.0,
            BLACK,
        );

        next_frame().await;
    }
}
