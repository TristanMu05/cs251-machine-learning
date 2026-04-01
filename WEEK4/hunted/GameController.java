package hunted;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

//---------------- GAME ENGINE: PLAYED IN A J PANEL ----------------

class GameController extends JPanel {

    // GAME ELEMENTS

    private Board board;

    private Prey prey;

    private Predator predator;

    private Image preyImg, predatorImg, exitImg, blockImg;

    private boolean gameOver;

    public GameController() {

        initializeGame();

        // TASK 4: SET THE WINDOW JPANEL ATTRIBUTES

        setPreferredSize(new Dimension(Board.ROWS * Board.CELLSIZE, Board.COLS * Board.CELLSIZE));

        setBackground(Color.WHITE);

        setFocusable(true);

        requestFocusInWindow();

        addKeyListener(new KeyAdapter() {
            
            @Override
            public void keyPressed(KeyEvent e) {

                int key = e.getKeyCode();

                // RESTART THE GAME WITH 'R' KEY
                if (key == KeyEvent.VK_R) {
                    restartGame();
                    return;
                }

                // SKIP MOVEMENT IF GAME IS OVER
                if (gameOver) {
                    return;
                }

                // MOVE THE PREY BASED ON ARROW KEY INPUT

                switch (key) {

                    case KeyEvent.VK_UP:

                        prey.move(prey.row - 1, prey.col); break;

                    case KeyEvent.VK_DOWN:

                        prey.move(prey.row + 1, prey.col); break;

                    case KeyEvent.VK_LEFT:

                        prey.move(prey.row, prey.col - 1); break;

                    case KeyEvent.VK_RIGHT:

                        prey.move(prey.row, prey.col + 1);

                }

                // MOVE THE PREDATOR USING AN AI SEARCH ALGORITHM: TURN-BASED

                predator.aiMoveGreedyBestFirstSearch(prey, board);

                repaint();
                checkGameOver();

            }
        });

    }

    private void initializeGame() {
        // TASK 1: CREATE THE GAME BOARD
        board = new Board();
        while (!board.validateBoard()){
            board.resetBoard();
        }

        // TASK 2: CREATE THE PREY AND THE PREDATOR OBJECTS
        prey = new Prey(1, 1, board);
        predator = new Predator(8, 8, board);

        // TASK 3: LOAD IMAGES FOR QUICK PAINTING
        preyImg = new ImageIcon("images/bunny.png").getImage();
        predatorImg = new ImageIcon("images/wolf.png").getImage();
        blockImg = new ImageIcon("images/block.png").getImage();
        exitImg = new ImageIcon("images/exit.png").getImage();

        gameOver = false;
    }

    private void restartGame() {
        initializeGame();
        repaint();
    }

    private void checkGameOver() {

        if (prey.row == predator.row && prey.col == predator.col) {

            JOptionPane.showMessageDialog(this, "Sorry! You were caught by the predator. Press 'R' to restart.");

            gameOver = true;

        }

        else if (board.getBoardCode(prey.row, prey.col) == Board.isEXIT) {

            JOptionPane.showMessageDialog(this, "Congratulations! You reached the exit! Press 'R' to restart.");

            gameOver = true;

        }

    }

    @Override

    protected void paintComponent(Graphics g) {

        super.paintComponent(g);

        // TASK 1: PAINT VERTICAL AND HORIZONTAL GRID LINES ON A LIGHT GRAY BACKGROUJND

        g.setColor(Color.LIGHT_GRAY);

        for (int row = 0; row <= Board.ROWS; row++) {

            // VERTICAL LINE

            g.drawLine(row * Board.CELLSIZE, 0, row * Board.CELLSIZE, Board.ROWS * Board.CELLSIZE);

            // HORIZONTAL LINE

            g.drawLine(0, row * Board.CELLSIZE, Board.COLS * Board.CELLSIZE, row * Board.CELLSIZE);

        }

        // TASK 2: DRAW OBSTACLE ELEMENTS ON THE BOARD

        // TASK 2: PAINT THE BOARD CELL BY CELL (ROW BY COLUMN)

        for (int row = 0; row < Board.ROWS; row++) {

            for (int col = 0; col < Board.COLS; col++) {

                if (board.getBoardCode(row, col) == Board.isBLOCK) {

                    g.drawImage(blockImg, col * Board.CELLSIZE, row * Board.CELLSIZE, Board.CELLSIZE, Board.CELLSIZE,
                            this);

                }

                else if (board.getBoardCode(row, col) == Board.isEXIT) {

                    g.drawImage(exitImg, col * Board.CELLSIZE, row * Board.CELLSIZE, Board.CELLSIZE, Board.CELLSIZE,
                            this);

                }

            }

        }

        // TASK 3: PAINT THE PREY AND PREDATOR ON THE BOARD

        g.drawImage(preyImg, prey.col * Board.CELLSIZE, prey.row * Board.CELLSIZE,
        Board.CELLSIZE, Board.CELLSIZE, this);

        g.drawImage(predatorImg, predator.col * Board.CELLSIZE, predator.row *
        Board.CELLSIZE, Board.CELLSIZE, Board.CELLSIZE, this);

    }

}
