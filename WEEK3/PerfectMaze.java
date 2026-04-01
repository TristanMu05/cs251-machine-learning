import java.awt.*;
import java.util.Stack;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class PerfectMaze extends JPanel {
    static final long serialVersionUID = 1;
    // WINDOW CELL DIMENSIONS
    static final int COLS = 50;
    static final int ROWS = 50;
    static final int N_CELLS = COLS * ROWS;
    static final int SIZE = 15;
    static final int OFFSET = 5;
    // MAZE, MAZE RUNNER, AND SOLUTION PATH
    static MazeCell[] maze;
    static java.util.ArrayList<Integer> solutionPath;
    static MazeRunnerDFS dfsrunner;
    static MazeRunnerBFS runner;
    static javax.swing.Timer timer;

    // DEFAULT CONSTRUCTOR
    public PerfectMaze() {
        this.setBackground(Color.WHITE);
    }

    public static void main(String[] args) {
        // TASK 1: INITIALIZE THE MAZE GRID
        init();
        // TASK 2: USE A BACKTRACKER METHOD TO CARVE OUT THE MAZE: STORE DATA
        buildMaze();
        // TASK 3: CREATE A WINDOW TO DRAW THE MAZE (FROM STORED DATA)
        JFrame frame = new JFrame("Perfect MazeRunner DFS");
        frame.setSize(800, 800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // TASK 4: CONSTRUCT A PANEL TO HOLD THE LINE DRAWINGS
        PerfectMaze pm = new PerfectMaze();
        frame.add(pm);
        frame.setVisible(true);
        // TASK 5: CREATE AN ANIMATED RUNNER TO NAVIGATE THE MAZE
        runner = new MazeRunnerBFS();
        // NOTE: USE A TIMER TO ANIMATE THE RUNNER'S MOVES
        timer = new javax.swing.Timer(100, e -> {
            runner.step();
            if (runner.finished)
                timer.stop();
            frame.repaint();
        });
        timer.start();
    }

    public static void init() {
        // TASK 1: DECLARE A MAZE ARRAY OF SIZE N_CELLS TO HOLD THE CELLS
        maze = new MazeCell[N_CELLS];
        // TASK 2: INSTANTIATE CELL OBJECTS FOR EACH CELL IN THE MAZE
        int cellID = 0;
        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS; c++) {
                // STEP 1: GENERATE A MAZE CELL WITH THE X, Y, AND CELL ID INITIALIZED
                int x = c * SIZE + OFFSET;
                int y = r * SIZE + OFFSET;
                MazeCell cell = new MazeCell(x, y, cellID);
                // STEP 2: PLACE THE CELL IN THE MAZE
                maze[cellID] = cell;
                cellID++;
            }
        }
    }

    // DRAW THE MAZE
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        // DRAW VISITED AND BACKTRACKED CELLS
        for (int i = 0; i < N_CELLS; i++) {
            int x = maze[i].x;
            int y = maze[i].y;
            if (runner.visited[i]) {
                g.setColor(new Color(0, 0, 0)); // visited (200, 220, 255)
                g.fillRect(x + 1, y + 1, SIZE - 2, SIZE - 2);
            }
            if (runner.backtracked[i]) {
                g.setColor(new Color(255, 0, 0)); // backtracked in gray (220, 220, 220)
                g.fillRect(x + 1, y + 1, SIZE - 2, SIZE - 2);
            }
        }
        // DRAW THE MAZE WALLS
        for (int i = 0; i < N_CELLS; i++) {
            int x = maze[i].x;
            int y = maze[i].y;
            if (maze[i].north)
                g.drawLine(x, y, x + SIZE, y);
            if (maze[i].south)
                g.drawLine(x, y + SIZE, x + SIZE, y + SIZE);
            if (maze[i].east)
                g.drawLine(x + SIZE, y, x + SIZE, y + SIZE);
            if (maze[i].west)
                g.drawLine(x, y, x, y + SIZE);
        }
        // DRAW RUNNER
        if (runner != null) {
            g.setColor(Color.RED);
            MazeCell c = maze[runner.currentCell];
            int cx = c.x + SIZE / 2;
            int cy = c.y + SIZE / 2;
            int r = SIZE / 4;
            g.fillOval(cx - r, cy - r, r * 2, r * 2);
        }
    }

    // ___________________ BUILDING THE RANDOM MAZE _____________________________
    static void buildMaze() {
        // TASK 1: CREATE THE MAZE VARIABLES AND INITIALIZE THEM
        Stack<Integer> stack = new Stack<Integer>();
        int top;
        // TASK 2: VISIT THE FIRST CELL AND PUSH IT ONTO THE STACK
        int visitedCells = 1; // COUNTS HOW MANY CELLS HAVE BEEN VISITED
        int cellID = 0; // THE FIRST CELL IN THE MAZE
        maze[cellID].visited = true;
        stack.push(cellID);
        // TASK 3: BACKTRACK UNTIL ALL THE CELLS HAVE BEEN VISITED
        while (visitedCells < N_CELLS) {
            // STEP 1: WHICH WALLS CAN BE TAKEN DOWN FOR A GIVEN CELL?
            String possibleWalls = "";
            if (maze[cellID].north == true && cellID >= COLS) {
                if (!maze[cellID - COLS].visited) {
                    possibleWalls += "N";
                }
            }
            if (maze[cellID].west == true && cellID % COLS != 0) {
                if (!maze[cellID - 1].visited) {
                    possibleWalls += "W";
                }
            }
            if (maze[cellID].east == true && cellID % COLS != COLS - 1) {
                if (!maze[cellID + 1].visited) {
                    possibleWalls += "E";
                }
            }
            if (maze[cellID].south == true && cellID < COLS * ROWS - COLS) {
                if (!maze[cellID + COLS].visited) {
                    possibleWalls += "S";
                }
            }
            // STEP 2: RANDOMLY SELECT A RANDOM WALL FROM THE AVAILABLE WALLS
            if (possibleWalls.length() > 0) {
                int index = Math.round((int) (Math.random() * possibleWalls.length()));
                char randomWall = possibleWalls.charAt(index);
                switch (randomWall) {
                    case 'N':
                        maze[cellID].north = false;
                        maze[cellID - COLS].south = false;
                        cellID -= COLS;
                        break;
                    case 'S':
                        maze[cellID].south = false;
                        maze[cellID + COLS].north = false;
                        cellID += COLS;
                        break;
                    case 'E':
                        maze[cellID].east = false;
                        maze[cellID + 1].west = false;
                        cellID++;
                        break;
                    case 'W':
                        maze[cellID].west = false;
                        maze[cellID - 1].east = false;
                        cellID--;
                }
                maze[cellID].visited = true;
                stack.push(cellID);
                visitedCells++;
            }
            // IF NO WALLS TO REMOVE, BACKTRACK BY GRABBING THE TOP OF THE STACK
            else {
                top = stack.pop();
                if (top == cellID) {
                    cellID = stack.pop();
                    stack.push(cellID);
                }
            }
        }
    }
}