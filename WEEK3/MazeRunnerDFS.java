import java.util.Stack;

public class MazeRunnerDFS {

    public int currentCell = 0;

    private Stack<Integer> stack = new Stack<>();

    public boolean[] visited = new boolean[PerfectMaze.N_CELLS];

    public boolean[] backtracked = new boolean[PerfectMaze.N_CELLS];

    public boolean finished = false;

    public MazeRunnerDFS() {

        stack.push(0);

        visited[0] = true;

    }

    public void step() {

        if (finished || stack.isEmpty())
            return;

        currentCell = stack.peek();

        if (currentCell == PerfectMaze.N_CELLS - 1) {

            finished = true;

            return;

        }

        MazeCell cell = PerfectMaze.maze[currentCell];

        // Try moves in order

        if (tryMove(cell, -PerfectMaze.COLS, !cell.north))
            return;

        if (tryMove(cell, +PerfectMaze.COLS, !cell.south))
            return;

        if (tryMove(cell, +1, !cell.east))
            return;

        if (tryMove(cell, -1, !cell.west))
            return;

        // No moves → backtrack

        backtracked[currentCell] = true;

        stack.pop();

    }

    private boolean tryMove(MazeCell cell, int delta, boolean open) {

        int next = currentCell + delta;

        if (!open || next < 0 || next >= PerfectMaze.N_CELLS)
            return false;

        if (visited[next])
            return false;

        visited[next] = true;

        stack.push(next);

        return true;

    }

}