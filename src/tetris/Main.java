package tetris;

public class Main {

    public static void main(String[] args) {
        Gameplay gameplay = new Gameplay();
        Game game = new Game(
                "Tetris",
                Constants.GRID_WIDTH * Constants.BLOCK_SIZE + Constants.SIDE_PANEL_SIZE,
                Constants.GRID_HEIGHT * Constants.BLOCK_SIZE + 20,
                Constants.BACKGROUND_COLOR,
                gameplay);
        game.startGameplay();
    }

}
