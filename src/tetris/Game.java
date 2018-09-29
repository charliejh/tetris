package tetris;

import javax.swing.*;
import java.awt.*;

public class Game extends JFrame {

    private Gameplay gameplay;

    public Game(String pTitle, int pFrameWidth, int pFrameHeight, Color pBackgroundColor, Gameplay pGameplay) {
        setTitle(pTitle);
        setSize(pFrameWidth, pFrameHeight);
        getContentPane().setBackground(pBackgroundColor);
        this.gameplay = pGameplay;
        add(pGameplay);
        setResizable(false);
        setLocationRelativeTo(null);
        addKeyListener(gameplay);
        setVisible(true);
    }

    public void startGameplay() {
        gameplay.startGame();
    }

}
