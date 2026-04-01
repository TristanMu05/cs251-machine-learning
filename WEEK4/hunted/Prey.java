package hunted;

//---------------- PREY CLASS ----------------

class Prey {

    // PREY ATTRIBUTES: PREY POSITION ON THE BOARD

    public int row, col;

    private Board gameBoard;

    // PREY CONSTRUCTOR

    public Prey(int row, int col, Board board) {

        this.row = row;

        this.col = col;

        gameBoard = board;

    }

    public void move(int r, int c) {

        if (isValidMove(r, c)) {

            row = r;

            col = c;

        }

    }

    private boolean isValidMove(int r, int c) {

        if (r < 0 || r >= Board.ROWS || c < 0 || c >= Board.COLS)

            return false;

        return gameBoard.getBoardCode(r, c) != Board.isBLOCK;

    }

}