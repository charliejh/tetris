package tetris;

import tetris.shapes.*;
import tetris.shapes.Shape;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Gameplay extends JComponent implements KeyListener {

    private int score, level;
    private List<Shape> shapesQueue;
    private Shape fallingShape;
    private List<Block> fallenShapes;
    private Timer gameplayTimer, speedGameplayTimer;
    private boolean gameOver, gamePaused;

    public Gameplay() {
        score = 0;
        level = 1;
        shapesQueue = new ArrayList<>();
        fallenShapes = new ArrayList<>();
        createNewShapesToQueue();
        fallingShape = getNextShapeFromList();
        gameplayTimer = new Timer(800, event -> flowOfGameplay());
        speedGameplayTimer = new Timer(30, event -> flowOfGameplay());
    }

    public void startGame() {
        gameplayTimer.start();
    }

    private void endGame() {
        gameOver = true;
        gameplayTimer.stop();
        speedGameplayTimer.stop();
    }

    private void flowOfGameplay() {
        if (!gamePaused && !gameOver) {
            if (shapesQueue.size() < 2) {
                createNewShapesToQueue();
            }
            if (canShapeMove(Direction.DOWN)) {
                fallingShape.moveShapeDirection(Direction.DOWN);
            } else {
                addFallingShapeToFallenShapes();
                clearFullLines();
                fallingShape = getNextShapeFromList();
                if (!canShapeMove(Direction.DOWN)) {
                    endGame();
                }
            }
        }
        repaint();
    }

    private void createNewShapesToQueue() {
        ArrayList<Shape> shapesArrayList = new ArrayList<>();
        shapesArrayList.add(new JShape(Constants.J_SHAPE_COLOR));
        shapesArrayList.add(new SquareShape(Constants.SQUARE_SHAPE_COLOR));
        shapesArrayList.add(new LineShape(Constants.LINE_SHAPE_COLOR));
        shapesArrayList.add(new LShape(Constants.L_SHAPE_COLOR));
        shapesArrayList.add(new SShape(Constants.S_SHAPE_COLOR));
        shapesArrayList.add(new TShape(Constants.T_SHAPE_COLOR));
        shapesArrayList.add(new ZShape(Constants.Z_SHAPE_COLOR));
        Collections.shuffle(shapesArrayList);
        shapesQueue.addAll(shapesArrayList);
    }

    private void addFallingShapeToFallenShapes() {
        fallenShapes.addAll(Arrays.asList(fallingShape.getShapeBlocks()));
    }

    private Shape getNextShapeFromList() {
        return shapesQueue.remove(0);
    }

    private boolean canShapeMove(Direction direction) {
        boolean canShapeMove = true;
        switch (direction) {
            case DOWN:
                fallingShape.moveShapeDirection(Direction.DOWN);
                canShapeMove = !(fallingShape.doesShapeCollide(fallenShapes) || fallingShape.getMaxYValue() > 19);
                fallingShape.moveShapeDirection(Direction.UP);
                break;
            case LEFT:
                fallingShape.moveShapeDirection(Direction.LEFT);
                canShapeMove = !(fallingShape.doesShapeCollide(fallenShapes) || fallingShape.getMinXValue() < 0);
                fallingShape.moveShapeDirection(Direction.RIGHT);
                break;
            case RIGHT:
                fallingShape.moveShapeDirection(Direction.RIGHT);
                canShapeMove = !(fallingShape.doesShapeCollide(fallenShapes) || fallingShape.getMaxXValue() > 9);
                fallingShape.moveShapeDirection(Direction.LEFT);
                break;
        }
        return canShapeMove;
    }

    private boolean canShapeRotate() {
        fallingShape.rotateShape(Direction.RIGHT);
        boolean canShapeRotate =  !(fallingShape.doesShapeCollide(fallenShapes)
                || fallingShape.getMaxXValue() > 9
                || fallingShape.getMinXValue() < 0
                || fallingShape.getMaxYValue() > 19
                || fallingShape.getMinYValue() < 0);
        fallingShape.rotateShape(Direction.LEFT);
        return canShapeRotate;
    }

    private void clearFullLines() {
        for (int i = 0; i < Constants.GRID_HEIGHT; i++) {
            int lineToCheck = i;
            List<Block> line = fallenShapes.stream().filter(block -> block.getY() == lineToCheck).collect(Collectors.toList());
            if (line.size() == 10) {
                fallenShapes.removeAll(line);
                for (Block block : fallenShapes) {
                    if (block.getY() < lineToCheck) {
                        block.moveBlock(Direction.DOWN);
                    }
                }
                score += 10;
                increaseLevel();
            }
        }
    }

    private void increaseLevel() {
        level = score != 0 && score % 50 == 0 && level < 5 ? level + 1 : level;
        switch (score) {
            case 50:
                gameplayTimer.setDelay(600);
                gameplayTimer.setInitialDelay(600);
                break;
            case 100:
                gameplayTimer.setDelay(450);
                gameplayTimer.setInitialDelay(450);
                break;
            case 150:
                gameplayTimer.setDelay(300);
                gameplayTimer.setInitialDelay(300);
                break;
            case 200:
                gameplayTimer.setDelay(200);
                gameplayTimer.setInitialDelay(200);
                break;
        }
    }

    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        fallingShape.drawShape(graphics);
        for (Block block : fallenShapes) {
            block.drawBlock(graphics);
        }
        drawGridBackground(graphics);
        drawSidePanel(graphics);
        if(gameOver) {
            drawPauseScreen(graphics, "Game Over!", 75);
        }
        if(gamePaused) {
            drawPauseScreen(graphics, "Paused!", 120);
        }
    }

    private void drawGridBackground(Graphics graphics) {
        graphics.setColor(Color.BLACK);
        for (int i = 0; i < (Constants.GRID_HEIGHT + 1) * Constants.BLOCK_SIZE; i += Constants.BLOCK_SIZE) {
            graphics.drawLine(0, i, Constants.GRID_WIDTH * Constants.BLOCK_SIZE, i);
        }
        for (int i = 0; i < (Constants.GRID_WIDTH + 1) * Constants.BLOCK_SIZE; i += Constants.BLOCK_SIZE) {
            graphics.drawLine(i, 0, i, Constants.GRID_HEIGHT * Constants.BLOCK_SIZE);
        }
    }

    private void drawSidePanel(Graphics graphics) {
        graphics.setColor(Color.white);
        graphics.setFont(new Font("Verdana", Font.PLAIN, 20));
        graphics.drawString("Charlie Harris", Constants.BLOCK_SIZE * Constants.GRID_WIDTH + 30, 45);
        graphics.drawString("1505033", Constants.BLOCK_SIZE * Constants.GRID_WIDTH + 55, 70);
        graphics.drawString("Score: " + score, Constants.BLOCK_SIZE * Constants.GRID_WIDTH + 55, 120);
        graphics.drawString("Level: " + level, Constants.BLOCK_SIZE * Constants.GRID_WIDTH + 62, 150);
        graphics.drawString("Next Shape:", Constants.BLOCK_SIZE * Constants.GRID_WIDTH + 40, 230);
        graphics.setFont(new Font("Verdana", Font.PLAIN, 15));
        graphics.drawString("Press P to", Constants.BLOCK_SIZE * Constants.GRID_WIDTH + 60, 450);
        graphics.drawString("Pause/Resume Game", Constants.BLOCK_SIZE * Constants.GRID_WIDTH + 20, 470);
        graphics.drawString("Press Q to Quit", Constants.BLOCK_SIZE * Constants.GRID_WIDTH + 40, 510);
        drawNextShapeArea(graphics);
    }

    private void drawNextShapeArea(Graphics graphics) {
        graphics.setColor(Constants.DARK_BACKGROUND_COLOR);
        graphics.fillRect(Constants.BLOCK_SIZE * Constants.GRID_WIDTH + 20,
                260,
                4 * Constants.BLOCK_SIZE,
                3 * Constants.BLOCK_SIZE);
        graphics.setColor(Color.BLACK);
        graphics.drawRect(Constants.BLOCK_SIZE * Constants.GRID_WIDTH + 20,
                260,
                4 * Constants.BLOCK_SIZE,
                3 * Constants.BLOCK_SIZE);
        for (int i = 280; i < Constants.BLOCK_SIZE * Constants.GRID_WIDTH; i += Constants.BLOCK_SIZE) {
            graphics.drawLine(Constants.BLOCK_SIZE * Constants.GRID_WIDTH + 20, i, Constants.BLOCK_SIZE * Constants.GRID_WIDTH + 20 + (4 * Constants.BLOCK_SIZE), i );
        }
        for (int i = 440; i < 620; i += Constants.BLOCK_SIZE) {
            graphics.drawLine(i, 260, i, 260 + (3 * Constants.BLOCK_SIZE) );
        }
        //TODO: move the shape into the area
        shapesQueue.get(0).drawShape(graphics);
    }

    private void drawPauseScreen(Graphics graphics, String message, int xPosition) {
        graphics.setColor(Constants.DARK_BACKGROUND_COLOR);
        graphics.fillRect(0,0,Constants.BLOCK_SIZE * Constants.GRID_WIDTH, Constants.BLOCK_SIZE * Constants.GRID_HEIGHT);
        graphics.setColor(Color.white);
        graphics.setFont(new Font("Verdana", Font.PLAIN, 40));
        graphics.drawString(message, xPosition, Constants.BLOCK_SIZE * Constants.GRID_HEIGHT / 2);
    }

    @Override
    public void keyPressed(KeyEvent key) {
        switch (key.getKeyCode()) {
            case KeyEvent.VK_RIGHT :
                if (canShapeMove(Direction.RIGHT) && !gamePaused && !gameOver) {
                    fallingShape.moveShapeDirection(Direction.RIGHT);
                }
                break;
            case KeyEvent.VK_LEFT :
                if (canShapeMove(Direction.LEFT) && !gamePaused && !gameOver) {
                    fallingShape.moveShapeDirection(Direction.LEFT);
                }
                break;
            case KeyEvent.VK_UP :
                if (canShapeRotate() && !gamePaused && !gameOver) {
                    fallingShape.rotateShape(Direction.RIGHT);
                }
                break;
            case KeyEvent.VK_DOWN :
                if (canShapeRotate() && !gamePaused && !gameOver) {
                    fallingShape.rotateShape(Direction.RIGHT);
                }
                break;
            case KeyEvent.VK_P:
                if (!gameOver) {
                    gamePaused = !gamePaused;
                }
                break;
            case KeyEvent.VK_Q:
                if (!gameOver) {
                    gamePaused = false;
                    endGame();
                }
                break;
            case KeyEvent.VK_SPACE:
                if (!gamePaused && !gameOver) {
                    gameplayTimer.stop();
                    speedGameplayTimer.start();
                }
                break;
        }
        repaint();
    }

    @Override
    public void keyReleased(KeyEvent key) {
        switch (key.getKeyCode()) {
            case KeyEvent.VK_SPACE:
                speedGameplayTimer.stop();
                gameplayTimer.start();
                break;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) { }

}
