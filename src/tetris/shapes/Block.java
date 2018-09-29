package tetris.shapes;

import tetris.Constants;
import tetris.Direction;

import java.awt.*;

public class Block {

    private int x, y;
    private Color blockColor;

    public Block(int pX, int pY, Color pBlockColor) {
        this.x = pX;
        this.y = pY;
        this.blockColor = pBlockColor;
    }

    public void moveBlock(Direction direction) {
        switch (direction) {
            case LEFT:
                moveBlock(x + -1, y);
                break;
            case RIGHT:
                moveBlock(x + 1, y);
                break;
            case UP:
                moveBlock(x, y + -1);
                break;
            case DOWN:
                moveBlock(x, y + 1);
                break;
        }
    }

    public void moveBlock(int pX, int pY) {
        x = pX;
        y = pY;
    }

    public boolean doesBlockCollide(Block pBlock) {
        return y == pBlock.getY() && x == pBlock.getX();
    }

    public void drawBlock(Graphics pGraphics) {
        pGraphics.setColor(this.blockColor);
        pGraphics.fillRect(
                this.x * Constants.BLOCK_SIZE,
                this.y * Constants.BLOCK_SIZE,
                Constants.BLOCK_SIZE,
                Constants.BLOCK_SIZE);
        pGraphics.setColor(Color.BLACK);
        pGraphics.drawRect(
                this.x * Constants.BLOCK_SIZE,
                this.y * Constants.BLOCK_SIZE,
                Constants.BLOCK_SIZE,
                Constants.BLOCK_SIZE);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

}
