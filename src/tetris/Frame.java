package Tetris;

import javax.swing.*;
import java.awt.*;

public class Frame extends JFrame {

    public Frame(){
        setTitle("Tetris - Charlie Harris");
        setSize(15 * 40 + 6, 20 * 40 + 30);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setBackground(new Color(80,80,80));
        setResizable(false);
    }

}
