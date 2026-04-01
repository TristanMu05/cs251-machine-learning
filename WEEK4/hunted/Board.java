package hunted;

public class Board {
    final static int ROWS = 15;
    final static int COLS = 15;
    final static int CELLSIZE = 50;
    final static int isBLOCK = 2;
    final static int isEXIT = 3;
    final static int isEMPTY = 1;
    public int[][] gameBoard;
    final static double FILL = 0.25; // 10% will be filled with blocks
    public int exitRow = ROWS - 1;
    public int exitCol = COLS - 2;

    /* 
    public static void main(String[] args) {
        Board b = new Board();
    }
    */

    public Board() {
        gameBoard = new int[ROWS][COLS];
        gameBoardLogic();
    }

    public boolean validateBoard() {
        // Ensure there is a valid path from (1,1) to exit
        boolean[][] visited = new boolean[ROWS][COLS];
        return dfs(1, 1, visited);
    }

    public boolean dfs(int r, int c, boolean[][] visited) {
        if (r < 0 || r >= ROWS || c < 0 || c >= COLS) return false;
        if (gameBoard[r][c] == isBLOCK || visited[r][c]) return false;
        if (r == exitRow && c == exitCol) return true;

        visited[r][c] = true;

        // Explore all four directions
        boolean found = dfs(r - 1, c, visited) || dfs(r + 1, c, visited) ||
                        dfs(r, c - 1, visited) || dfs(r, c + 1, visited);
        
        return found;
    }

    public void resetBoard() {
        gameBoardLogic();
    }

    public void gameBoardLogic() {
        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS; c++) {

                // 1) set the edges
                if (r == 0 || r == ROWS - 1 || c == 0 || c == COLS - 1) {
                    gameBoard[r][c] = isBLOCK;
                }

                // 2) empty cells
                else {
                    gameBoard[r][c] = isEMPTY;
                }

                // 3) blocks in 10%
                double rand = Math.random();
                if (rand < FILL) {
                    gameBoard[r][c] = isBLOCK;
                }
            }

            // set exit
            gameBoard[ROWS - 1][COLS - 2] = isEXIT;
        }

        /* // print the board for testing
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                System.out.print(gameBoard[i][j] + " ");
            }
            System.out.println();
        }
        */

    }

    public int getBoardCode(int row, int col) {
        return gameBoard[row][col];
    }
}
