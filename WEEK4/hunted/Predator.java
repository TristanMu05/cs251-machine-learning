package hunted;

//---------------- PREY CLASS ----------------

class Predator {

    // PREY ATTRIBUTES: PREY POSITION ON THE BOARD

    public int row, col;

    private Board gameBoard;

    // PREY CONSTRUCTOR

    public Predator(int row, int col, Board board) {

        this.row = row;

        this.col = col;

        gameBoard = board;

    }

    public void aiMoveGreedyBestFirstSearch(Prey prey, Board board) {
        int bestRow = row;
        int bestCol = col;
        int minHeuristic = Integer.MAX_VALUE;

        // explore all the possible moves
        int[] dRows = {-1,1,0,0};
        int[] dCols = {0,0,-1,1};

        for (int i = 0; i < 4; i++){
            int newRow = row + dRows[i];
            int newCol = col + dCols[i];
            if (isValidMove(newRow, newCol)){
                int distToPrey = Math.abs(newRow - prey.row) + Math.abs(newCol - prey.col);
                int distToExit = Math.abs(newRow - board.exitRow) + Math.abs(newCol - board.exitCol);
                
                // Weight hunting as primary goal (70% priority) and exit defense as secondary (30% priority)
                int heuristic = (distToPrey * 2) + distToExit;
                
                if (heuristic < minHeuristic){
                    minHeuristic = heuristic;
                    bestRow = newRow;
                    bestCol = newCol;
                }
            }
        }

        this.row = bestRow;
        this.col = bestCol;

    }

    private boolean isValidMove(int r, int c) {

        if (r < 0 || r >= Board.ROWS || c < 0 || c >= Board.COLS)

            return false;

        return gameBoard.getBoardCode(r, c) != Board.isBLOCK;

    }

}