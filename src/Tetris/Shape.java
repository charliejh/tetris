package Tetris;

import java.awt.*;
import static java.awt.Color.*;

/**
 * Parent class for every Tetris shape
 * Each shape rotation will have coordinates stored using a 3D array
 * Each shape will be made up of 4 blocks
 * Each shape will have a center block
 * The other 3 blocks will be situated around the center block
 */
abstract class Shape {

    Color color;
    int shapeArr [][][];
    static int rotation = 0;
    Block[] blocks = new Block[4];
    Block center;

    /**
     * Moves the shape by moving each individual block
     * Uses the block's move method
     */
    void move(int x, int y){
        for (int i = 0; i < blocks.length; i++){
            blocks[i].move(blocks[i].getX() + x, blocks[i].getY() + y);
        }
    }

    /**
     * Draws the shape by calling the blocks draw method
     */
    void draw(Graphics graphics){
        graphics.setColor(color);
        for (int i = 0; i < blocks.length; i++) {
            blocks[i].draw(graphics);
        }
    }

    /**
     * Used for creating new shapes of a specific type
     */
    abstract Shape returnNewShape();

}

/**
 * Classes for each Tetris shape
 */
class sqShape extends Shape{

    sqShape() {
        color = yellow;
        shapeArr = new int[][][] {
                {{0,0},{0,1},{1,0},{1,1}},
                {{0,0},{0,1},{1,0},{1,1}},
                {{0,0},{0,1},{1,0},{1,1}},
                {{0,0},{0,1},{1,0},{1,1}}
        };
        center = new Block(4, 0, color);
        blocks[0] = center;
        for (int i = 1; i < 4; i++) {
            blocks[i] = new Block(center.getX() + shapeArr[0][i][0], center.getY() + shapeArr[0][i][1], color);
        }
    }

    @Override
    Shape returnNewShape() {
        return new sqShape();
    }
}

class lineShape extends Shape{

    lineShape() {
        color = cyan;
        shapeArr = new int[][][] {
                {{0,0},{-1,0},{1,0},{2,0}},
                {{0,0},{0,-1},{0,1},{0,2}},
                {{0,0},{-1,0},{1,0},{2,0}},
                {{0,0},{0,-1},{0,1},{0,2}}
        };
        center = new Block(4, 0, color);
        blocks[0] = center;
        for (int i = 1; i < 4; i++) {
            blocks[i] = new Block(center.getX() + shapeArr[0][i][0], center.getY() + shapeArr[0][i][1], color);
        }
    }

    @Override
    Shape returnNewShape() {
        return new lineShape();
    }

}

class jShape extends Shape {

    jShape() {
        color = blue;
        shapeArr = new int[][][]{
                {{0,0}, {-1,0}, {1,0}, {1,1}},
                {{0,0}, {0,1}, {0,-1}, {-1,1}},
                {{0,0}, {-1,0}, {1,0}, {-1,-1}},
                {{0,0}, {0,1}, {0,-1}, {1,-1}}
        };
        center = new Block(5, 0, color);
        blocks[0] = center;
        for (int i = 1; i < 4; i++) {
            blocks[i] = new Block(center.getX() + shapeArr[0][i][0], center.getY() + shapeArr[0][i][1], color);
        }
    }

    @Override
    Shape returnNewShape() {
        return new jShape();
    }

}

class lShape extends Shape {

    lShape() {
        color = orange;
        shapeArr = new int[][][]{
                {{0,0}, {-1,0}, {1,0}, {1,-1}},
                {{0,0}, {0,1}, {0,-1}, {1,1}},
                {{0,0}, {1,0}, {-1,0}, {-1,1}},
                {{0,0}, {0,1}, {0,-1}, {-1,-1}}
        };
        center = new Block(4, 1, color);
        blocks[0] = center;
        for (int i = 1; i < 4; i++) {
            blocks[i] = new Block(center.getX() + shapeArr[0][i][0], center.getY() + shapeArr[0][i][1], color);
        }
    }

    @Override
    Shape returnNewShape() {
        return new lShape();
    }


}

class tShape extends Shape {

    tShape() {
        color = magenta;
        shapeArr = new int[][][]{
                {{0,0}, {-1,0}, {1,0}, {0,-1}},
                {{0,0}, {0,-1}, {0,1}, {1,0}},
                {{0,0}, {0,1}, {-1,0}, {1,0}},
                {{0,0}, {-1,0}, {0,1}, {0,-1}}
        };
        center = new Block(4, 1, color);
        blocks[0] = center;
        for (int i = 1; i < 4; i++) {
            blocks[i] = new Block(center.getX() + shapeArr[0][i][0], center.getY() + shapeArr[0][i][1], color);
        }
    }

    @Override
    Shape returnNewShape() {
        return new tShape();
    }

}

class sShape extends Shape {

    sShape() {
        color = green;
        shapeArr = new int[][][]{
                {{0,0}, {1,0}, {0,1}, {-1,1}},
                {{0,0}, {0,1}, {-1,0}, {-1,-1}},
                {{0,0}, {1,0}, {0,1}, {-1,1}},
                {{0,0}, {0,1}, {-1,0}, {-1,-1}}
        };
        center = new Block(4, 0, color);
        blocks[0] = center;
        for (int i = 1; i < 4; i++) {
            blocks[i] = new Block(center.getX() + shapeArr[0][i][0], center.getY() + shapeArr[0][i][1], color);
        }
    }

    @Override
    Shape returnNewShape() {
        return new sShape();
    }

}

class zShape extends Shape {

    zShape() {
        color = red;
        shapeArr = new int[][][]{
                {{0,0}, {-1,0}, {0,1}, {1,1}},
                {{0,0}, {0,-1}, {-1,0}, {-1,1}},
                {{0,0}, {-1,0}, {0,1}, {1,1}},
                {{0,0}, {0,-1}, {-1,0}, {-1,1}}
        };
        center = new Block(4, 0, color);
        blocks[0] = center;
        for (int i = 1; i < 4; i++) {
            blocks[i] = new Block(center.getX() + shapeArr[0][i][0], center.getY() + shapeArr[0][i][1], color);
        }
    }

    @Override
    Shape returnNewShape() {
        return new zShape();
    }

}