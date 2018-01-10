package Tetris;

import java.awt.*;

class Block {

    private final int blockSize = 40;
    private int x;
    private int y;
    private Color color;

    /**
     * Constructor
     */
    Block(int x, int y, Color color) {
        this.x = x;
        this.y = y;
        this.color = color;
    }

    /**
     * Moves the block
     * @param x - x position of the block
     * @param y - y position of the block
     */
    void move(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Draws a block
     */
    void draw(Graphics g){
        g.setColor(color);
        g.fillRect(x * blockSize, y * blockSize, blockSize, blockSize);
        g.setColor(new Color(0,0,0));
        g.drawRect(x * blockSize, y * blockSize, blockSize, blockSize);
    }

    /**
     * Getters for the blocks x and y position
     */
    public int getX() { return x; }
    public int getY() { return y; }

    /**
     * Setters for the blocks x and y position
     */
    public void setX(int x) { this.x = x; }
    public void setY(int y) { this.y = y; }

}