package hunted;

import javax.swing.JFrame;

// ---------------- GAME WINDOW ----------------

public class GameWindow {

    public static void main(String[] args) {

        // TASK 1: CREATE THE WINDOW FOR THE GAME

        JFrame frame = new JFrame("Predator vs Prey Game");

        // TASK 2: CREATE THE GAME CONTROLLER (STORED IN A PANEL)

        GameController gameController = new GameController();

        // TASK 3: ADD THE GAME CONTROLLER TO THE WINDOW FOR PLAY

        frame.add(gameController);

        frame.pack();

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setVisible(true);

    }

}