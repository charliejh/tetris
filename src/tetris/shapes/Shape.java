package tetris.shapes;

import tetris.Direction;

import java.awt.*;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public abstract class Shape {

    Block[] shapeBlocks;
    Block centerBlock;
    int shapePositions[][][];
    Color shapeColor;
    int rotation;

    public Shape(Color pShapeColor) {
        shapeColor = pShapeColor;
        rotation = 0;
        shapeBlocks = new Block[4];
    }

    public void moveShapeDirection(Direction pDirection) {
        switch (pDirection) {
            case DOWN:
                for (Block block : shapeBlocks) {
                    block.moveBlock(Direction.DOWN);
                }
                break;
            case LEFT:
                for (Block block : shapeBlocks) {
                    block.moveBlock(Direction.LEFT);
                }
                break;
            case RIGHT:
                for (Block block : shapeBlocks) {
                    block.moveBlock(Direction.RIGHT);
                }
                break;
            case UP:
                for (Block block : shapeBlocks) {
                    block.moveBlock(Direction.UP);
                }
                break;
        }
    }

    public void moveShape(int pX, int pY) {
        for (Block block : shapeBlocks) {
            block.moveBlock(block.getX() + pX, block.getY() + pY);
        }
    }

    public void rotateShape(Direction direction) {
        switch (direction) {
            case RIGHT:
                rotation = Math.floorMod(rotation + 1, shapePositions.length);
                for (int i = 1; i < shapeBlocks.length; i++) {
                    shapeBlocks[i].moveBlock(centerBlock.getX() + shapePositions[rotation][i][0], centerBlock.getY() + shapePositions[rotation][i][1]);
                }
                break;
            case LEFT:
                rotation = Math.floorMod(rotation - 1, shapePositions.length);
                for (int i = 1; i < shapeBlocks.length; i++) {
                    shapeBlocks[i].moveBlock(centerBlock.getX() + shapePositions[rotation][i][0], centerBlock.getY() + shapePositions[rotation][i][1]);
                }
                break;
        }
    }

    public boolean doesShapeCollide(List<Block> blocks) {
        for (Block block : blocks) {
            for (Block shapeBlock : shapeBlocks) {
                if (shapeBlock.doesBlockCollide(block)) {
                    return true;
                }
            }
        }
        return false;
    }

    public int getMaxYValue() {
        return Arrays.stream(shapeBlocks).max(Comparator.comparing(Block::getY)).get().getY();
    }

    public int getMinYValue() {
        return Arrays.stream(shapeBlocks).min(Comparator.comparing(Block::getY)).get().getY();
    }

    public int getMaxXValue() {
        return Arrays.stream(shapeBlocks).max(Comparator.comparing(Block::getX)).get().getX();
    }

    public int getMinXValue() {
        return Arrays.stream(shapeBlocks).min(Comparator.comparing(Block::getX)).get().getX();
    }

    public void drawShape(Graphics pGraphics) {
        for (Block block : shapeBlocks) {
            block.drawBlock(pGraphics);
        }
    }

    public abstract void positionBlocks();

    public Block[] getShapeBlocks() {
        return shapeBlocks;
    }

}
