package Tetris;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.ArrayList;
import javax.swing.Timer;
import static Tetris.Shape.rotation;

class Gameplay extends JComponent implements KeyListener {

    /** The frame to the application */
    private Frame frame;
    /** An ArrayList of the 7 shapes to drop down */
    private ArrayList<Shape> nextShapes = new ArrayList<>();
    /** The current falling piece */
    private Shape activeShape;
    /** An ArrayList of each fallen block that hasn't been cleared */
    private ArrayList<Block> usedBlocks = new ArrayList<>();
    /** Variables used for the timer */
    private int timeVal = 900;
    private int fastTime = 50;
    private int dropTime = 0;
    private int regTime = 900;
    private Timer timer;
    /** Keeps track of the games state */
    private static boolean gameOver = false;
    private static boolean gamePaused = false;
    /** Keeps track of the current score and the current level **/
    private static int score = 0;
    private static int level = 1;

    /**
     * Constructor
     */
    public Gameplay(Frame frame) {
        this.frame = frame;
        frame.addKeyListener(this);
        makeShapes();
        activeShape = nextShapes.get(0);
        nextShapes.remove(0);
    }

    /**
     * Creates all 7 shapes and then shuffles them
     */
    private void makeShapes(){
        rotation = 0;
        ArrayList<Shape> tempShapeArrayList = new ArrayList<>();
        tempShapeArrayList.add(new sqShape());
        tempShapeArrayList.add(new lineShape());
        tempShapeArrayList.add(new jShape());
        tempShapeArrayList.add(new lShape());
        tempShapeArrayList.add(new tShape());
        tempShapeArrayList.add(new sShape());
        tempShapeArrayList.add(new zShape());
        Collections.shuffle(tempShapeArrayList);
        nextShapes.addAll(tempShapeArrayList);
    }

    /**
     * Starts the timer
     */
    void startGame(){
        timer = new Timer(10, e -> update());
        timer.start();
    }


    /**
     * Draws the components
     */
    public void paintComponent(Graphics graphics) {
        drawGridBackground(graphics);
        drawScoreBoard(graphics);
        drawNextShape(graphics);
        activeShape.draw(graphics);
        for (int i = 0; i < usedBlocks.size(); i++) {
            usedBlocks.get(i).draw(graphics);
        }
        if(gamePaused){
            drawPauseScreen(graphics, "Paused!", 100);
        }
        if(gameOver){
            drawPauseScreen(graphics, "Game Over!", 70);
        }
    }

    /**
     * Updates the game
     */
    private void update() {
        boolean justCreated = false;
        dropTime += 10;
        if(dropTime >= timeVal){
            if (!testCollisionDown(activeShape)) {
                for (int i = 0; i < 4; i++) {
                    Block blocks = new Block(activeShape.blocks[i].getX(), activeShape.blocks[i].getY(), activeShape.color);
                    usedBlocks.add(blocks);
                }
                if (nextShapes.size() < 2) {
                    makeShapes();
                }
                rotation = 0;
                activeShape = nextShapes.get(0);
                justCreated = true;
                nextShapes.remove(0);
                if(!testCollisionDown(activeShape)){
                    timer.stop();
                    gameOver = true;
                }
            }
            if (testCollisionDown(activeShape) && !justCreated){
                nextMove();
                frame.repaint();
            }
            dropTime = 0;
        }
        clearLine();
        frame.repaint();
    }

    /**
     * Moves the activeShape 0 and the x axis and 1 on the y axis
     */
    private void nextMove(){
        if(!gamePaused){
            activeShape.move(0, 1);
        }
    }

    /**
     * Rotates the shape using the shapes next set of 3D coordinates
     */
    private void rotateShape(Shape activeShape ){
        rotation += 1;
        if(rotation > 3){
            rotation = 0;
        }
        Shape temp;
        if(activeShape.getClass() == lineShape.class){
            temp = new lineShape();
        }
        else if(activeShape.getClass() == jShape.class){
            temp = new jShape();
        }
        else if(activeShape.getClass() == lShape.class){
            temp = new lShape();
        }
        else if(activeShape.getClass() == tShape.class){
            temp = new tShape();
        }
        else if(activeShape.getClass() == sShape.class){
            temp = new sShape();
        }
        else if(activeShape.getClass() == zShape.class){
            temp = new zShape();
        }
        else {
            temp = new sqShape();
        }
        for (int i = 1; i < 4; i++) {
            activeShape.blocks[i].move(activeShape.center.getX() + temp.shapeArr[rotation][i][0], activeShape.center.getY() + temp.shapeArr[rotation][i][1]);
        }
    }

    /**
     * Tests for collision when rotating the activeShape
     */
    private boolean testCollisionRotation(){
        Shape temp;
        if(activeShape.getClass() == lineShape.class){
            temp = new lineShape();
        }
        else if(activeShape.getClass() == jShape.class){
            temp = new jShape();
        }
        else if(activeShape.getClass() == lShape.class){
            temp = new lShape();
        }
        else if(activeShape.getClass() == tShape.class){
            temp = new tShape();
        }
        else if(activeShape.getClass() == sShape.class){
            temp = new sShape();
        }
        else if(activeShape.getClass() == zShape.class){
            temp = new zShape();
        }
        else {
            temp = new sqShape();
        }
        for (int i = 0; i < activeShape.blocks.length; i++) {
            temp.blocks[i].setX(activeShape.blocks[i].getX());
            temp.blocks[i].setY(activeShape.blocks[i].getY());
        }
        rotateShape(temp);
        for (int i = 0; i < temp.blocks.length; i++) {
            if (temp.blocks[i].getX() < 0 || temp.blocks[i].getX() > 9 || temp.blocks[i].getY() > 18 || temp.blocks[i].getY() < 0) {
                rotation -= 1;
                return true;
            }
            for (int j = 0; j < usedBlocks.size(); j++) {
                if (temp.blocks[i].getX() == usedBlocks.get(j).getX() && temp.blocks[i].getY() == usedBlocks.get(j).getY()) {
                    rotation -= 1;
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Tests for collision when moving the activeShape to left
     * Returns false if activeShape will collide
     */
    private boolean testCollisionLeft(Shape activeShape) {
        for (int i = 0; i < activeShape.blocks.length; i++) {
            if (activeShape.blocks[i].getX() == 0) {
                return false;
            }
            for (int j = 0; j < usedBlocks.size(); j++) {
                if (activeShape.blocks[i].getX() - 1 == usedBlocks.get(j).getX() && activeShape.blocks[i].getY() == usedBlocks.get(j).getY()) {
                    return false;
                }
                if (activeShape.blocks[i].getX() == usedBlocks.get(j).getX() && activeShape.blocks[i].getY() == usedBlocks.get(j).getY()) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Tests for collision when moving the activeShape to right
     * Returns false if activeShape will collide
     */
    private boolean testCollisionRight(Shape activeShape) {
        for (int i = 0; i < activeShape.blocks.length; i++) {
            if (activeShape.blocks[i].getX() == 9) {
                return false;
            }
            for (int j = 0; j < usedBlocks.size(); j++) {
                if (activeShape.blocks[i].getX() + 1 == usedBlocks.get(j).getX() && activeShape.blocks[i].getY() == usedBlocks.get(j).getY()) {
                    return false;
                }
                if (activeShape.blocks[i].getX() == usedBlocks.get(j).getX() && activeShape.blocks[i].getY() == usedBlocks.get(j).getY()) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Tests the activeShape for collision when falling down
     * Returns false if the shape collides downwards
     */
    private boolean testCollisionDown(Shape activeShape){
        for (int i = 0; i < activeShape.blocks.length; i++) {
            if(activeShape.blocks[i].getY() >= 19) {
                return false;
            }
            for (int j = 0; j < usedBlocks.size(); j++) {
                if(activeShape.blocks[i].getX() == usedBlocks.get(j).getX() && activeShape.blocks[i].getY() + 1 == usedBlocks.get(j).getY()){
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Clears full lines and increases the level if the score exceeds certain numbers
     */
    private void clearLine(){
        for (int i = 0; i < 20; i++) {
            ArrayList<Block> clearLine = new ArrayList<>();
            for (int j = 0; j < usedBlocks.size(); j++) {
                if(usedBlocks.get(j).getY() == i){
                    clearLine.add(usedBlocks.get(j));
                }
                if(clearLine.size() == 10){
                    score +=10;
                    if(score == 60) {
                        level++;
                        timeVal -= 200;
                        regTime -= 200;
                    }
                    if(score == 120) {
                        level++;
                        timeVal -= 200;
                        regTime -= 200;
                    }
                    if(score == 180) {
                        level++;
                        timeVal -= 200;
                        regTime -= 200;
                    }
                    if(score == 240) {
                        level++;
                        timeVal -= 100;
                        regTime -= 100;
                    }
                    for (int b = 0; b < usedBlocks.size(); b++) {
                            if(clearLine.contains(usedBlocks.get(b))){
                                usedBlocks.remove(usedBlocks.get(b));
                            b--;
                        }
                    }
                    for (int k = 0; k < usedBlocks.size(); k++) {
                        if(usedBlocks.get(k).getY() < i){
                            usedBlocks.get(k).move(usedBlocks.get(k).getX(), usedBlocks.get(k).getY() + 1);
                        }
                    }
                }
            }
        }
    }

    /**
     * Draws the next shape in the scoreboard area
     */
    private void drawNextShape(Graphics graphics) {
        graphics.setColor(new Color(0,0,0, 120));
        graphics.fillRect(420, 260,4 * 40, 3 * 40);
        Shape nextShapeToDraw;
        if(nextShapes.get(0).getClass() == lineShape.class){
            nextShapeToDraw = new lineShape();
            for (int i = 1; i < 4; i++) {
                nextShapeToDraw.blocks[i].move(nextShapeToDraw.center.getX() + nextShapeToDraw.shapeArr[1][i][0], nextShapeToDraw.center.getY() + nextShapeToDraw.shapeArr[1][i][1]);
            }
            nextShapeToDraw.move(8, 7);
        }
        else if(nextShapes.get(0).getClass() == jShape.class) {
            nextShapeToDraw = new jShape();
            nextShapeToDraw.move(7, 7);
        }
        else if(nextShapes.get(0).getClass() == lShape.class) {
            nextShapeToDraw = new lShape();
            nextShapeToDraw.move(8, 7);
        }
        else if(nextShapes.get(0).getClass() == tShape.class) {
            nextShapeToDraw = new tShape();
            nextShapeToDraw.move(8, 7);
        }
        else if(nextShapes.get(0).getClass() == sShape.class) {
            nextShapeToDraw = new sShape();
            nextShapeToDraw.move(8, 7);
        }
        else if (nextShapes.get(0).getClass() == zShape.class) {
            nextShapeToDraw = new zShape();
            nextShapeToDraw.move(8, 7);
        }
        else {
            nextShapeToDraw = new sqShape();
            nextShapeToDraw.move(8, 7);
        }
        nextShapeToDraw.draw(graphics);
    }

    /**
     * Draws the grid background
     */
    private void drawGridBackground(Graphics graphics) {
        graphics.setColor(new Color(64,64,64));
        graphics.fillRect(0, 0, 400, 800);
        graphics.setColor(Color.BLACK);
        for (int i = 0; i < 840; i += 40) {
            graphics.drawLine(0, i, 400, i);
        }
        for (int i = 0; i < 440; i += 40) {
            graphics.drawLine(i, 0, i, 800);
        }
    }

    /**
     * Draws the scoreboard
     */
    private void drawScoreBoard(Graphics graphics) {
        graphics.setColor(Color.white);
        graphics.setFont(new Font("Verdana", Font.PLAIN, 20));
        graphics.drawString("Charlie Harris", 430, 45);
        graphics.drawString("1505033", 455, 70);
        graphics.drawString("Score: " + score, 455, 120);
        graphics.drawString("Level: " + level, 462, 150);
        graphics.drawString("Next Shape:", 440, 230);
        graphics.setFont(new Font("Verdana", Font.PLAIN, 15));
        graphics.drawString("P to Pause Game", 438, 450);
        graphics.drawString("R to Resume Game", 428, 500);
    }

    /**
     * Draws the pause screen
     */
    private void drawPauseScreen(Graphics graphics, String text, int x) {
        graphics.setColor(new Color(0,0,0, 120));
        graphics.fillRect(0,0,10 * 40, 20 *40);
        graphics.setColor(Color.white);
        graphics.setFont(new Font("Verdana", Font.PLAIN, 40));
        graphics.drawString(text, x, 400);
    }

    /**
     * KeyListener Methods
     */
    public void keyTyped(KeyEvent k){}
    public void keyPressed(KeyEvent k){
        if(k.getKeyCode() == KeyEvent.VK_SPACE){
            timeVal = fastTime;
        }
        if(k.getKeyCode() == KeyEvent.VK_P){
            gamePaused = true;
        }
        if(k.getKeyCode() == KeyEvent.VK_R){
            gamePaused = false;
        }
        if((k.getKeyCode() == KeyEvent.VK_UP || k.getKeyCode() == KeyEvent.VK_DOWN) && !gamePaused){
            if (!testCollisionRotation()) {
                rotation -= 1;
                if(rotation == -1){
                    rotation = 3;
                }
                rotateShape(activeShape);
            }
            frame.repaint();
        }
        if(k.getKeyCode() == KeyEvent.VK_LEFT && !gamePaused){
            if (testCollisionLeft(activeShape) && !gameOver){
                activeShape.move(-1, 0);
            }
            frame.repaint();
        }
        if(k.getKeyCode() == KeyEvent.VK_RIGHT && !gamePaused){
            if (testCollisionRight(activeShape) && !gameOver){
                activeShape.move(1, 0);
            }
            frame.repaint();
        }
    }
    public void keyReleased(KeyEvent k){
        if(k.getKeyCode() == KeyEvent.VK_SPACE){
            timeVal = regTime;
        }
    }

}
