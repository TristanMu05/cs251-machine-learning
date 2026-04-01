import java.util.Queue;
import java.util.LinkedList;

public class MazeRunnerBFS {

    public int currentCell = 0;

    private Queue<Integer> queue = new LinkedList<>();

    public boolean[] visited = new boolean[PerfectMaze.N_CELLS];

    public boolean[] backtracked = new boolean[PerfectMaze.N_CELLS];

    public boolean finished = false;

    public MazeRunnerBFS() {

        queue.add(0);

        visited[0] = true;

    }

    public void step() {

        if (finished || queue.isEmpty())
            return;

        currentCell = queue.peek();

        if (currentCell == PerfectMaze.N_CELLS - 1) {

            finished = true;

            return;

        }

        MazeCell cell = PerfectMaze.maze[currentCell];

        // Try moves in order (BFS explores level by level)
        boolean moved = false;

        if (tryMove(cell, -PerfectMaze.COLS, !cell.north))
            moved = true;

        if (tryMove(cell, +PerfectMaze.COLS, !cell.south))
            moved = true;

        if (tryMove(cell, +1, !cell.east))
            moved = true;

        if (tryMove(cell, -1, !cell.west))
            moved = true;

        // If no moves available, dequeue the current cell
        if (!moved) {

            backtracked[currentCell] = true;

            queue.remove();

        }

    }

    private boolean tryMove(MazeCell cell, int delta, boolean open) {

        int next = currentCell + delta;

        if (!open || next < 0 || next >= PerfectMaze.N_CELLS)
            return false;

        if (visited[next])
            return false;

        visited[next] = true;

        queue.add(next);

        return true;

    }

}
