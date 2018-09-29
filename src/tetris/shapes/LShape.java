package tetris.shapes;

import tetris.Constants;
import java.awt.*;

public class LShape extends Shape {

    public LShape(Color shapeColor) {
        super(shapeColor);
        positionBlocks();
    }

    @Override
    public void positionBlocks() {
        shapePositions = new int[][][]{
                {{0,0}, {-1,0}, {1,0}, {1,-1}},
                {{0,0}, {0,1}, {0,-1}, {1,1}},
                {{0,0}, {1,0}, {-1,0}, {-1,1}},
                {{0,0}, {0,1}, {0,-1}, {-1,-1}}
        };
        centerBlock = new Block(Constants.GRID_WIDTH / 2 - 1, 1, shapeColor);
        shapeBlocks[0] = centerBlock;
        for (int i = 1; i < shapeBlocks.length; i++) {
            shapeBlocks[i] = new Block(centerBlock.getX() + shapePositions[rotation][i][0], centerBlock.getY() + shapePositions[rotation][i][1], shapeColor);
        }
    }

}
