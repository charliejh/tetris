package Tetris;

public class Main {

    public static void main(String[] args) {
        Frame frame = new Frame();
        Gameplay newGame = new Gameplay(frame);
        frame.add(newGame);
        frame.setVisible(true);
        newGame.startGame();
    }

}