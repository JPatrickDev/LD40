package me.jack.LD40.states;

import me.jack.LD40.level.GameBoard;
import me.jack.LD40.level.InformationDisplay;
import me.jack.LD40.level.ShapeQueue;
import me.jack.LD40.level.tile.Shape;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.newdawn.slick.*;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import java.util.Random;

/**
 * Created by Jack on 02/12/2017.
 */
public class InGameState extends BasicGameState {

    private GameBoard board;
    private ShapeQueue queue;
    private InformationDisplay display;

    private Shape currentShape;
    private Image currentShapeImage;


    @Override
    public int getID() {
        return 0;
    }

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        queue = new ShapeQueue(0, 0, 80, gameContainer.getHeight() - 80, this);
        board = new GameBoard(80, 0, 8, 8, 400, 400);
        display = new InformationDisplay(0, 400, 480, 80);
    }

    public boolean paused = false;

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
        queue.render(graphics);
        board.render(graphics);
        display.render(graphics, this);
        if (paused) {
            graphics.drawString("PAUSED", 240, 240);
        } else {
            Input i = gameContainer.getInput();
            int x = i.getMouseX();
            int y = i.getMouseY();
            boolean inQueueArea = false;
            if (x >= queue.getX() && x <= queue.getX() + queue.getWidth()) {
                if (y >= queue.getY() && y <= queue.getY() + queue.getHeight()) {
                    inQueueArea = true;
                    if (currentShapeImage != null) {
                        graphics.drawImage(currentShapeImage, x, y);
                    }
                }
            }
            if (!inQueueArea && currentShape != null) {
                int tX = x - board.getX();
                tX /= board.previousTileSize;
                int tY = y + board.getY();
                tY /= board.previousTileSize;
                System.out.println(tX + ":" + tY);
                if (board.canPlace(currentShape, tX, tY)) {
                    board.setHighlighted(tX, tY, currentShape);
                } else {
                    graphics.drawImage(currentShapeImage, x, y);
                }
            }
        }
    }

    public float timer = 0;
    public float counter = 0;
    public float score = 0;

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) throws SlickException {
        if (!paused) {
            timer += i;
            counter += i;
            System.out.println(board.count() + ":" + counter);
            if (timer >= 1500 || queue.isEmpty()) {
                timer = 0;
                queue.addShape(Shape.shapes[new Random().nextInt(Shape.shapes.length)]);
            }
            queue.update(i, this);
            board.update();
            if (counter != 0)
                score += (board.count() / counter) * 100;
        }
    }


    @Override
    public void mouseReleased(int button, int x, int y) {
        super.mousePressed(button, x, y);
        if (paused) {
            paused = false;
            return;
        }
        // board.setSize(board.getW() + 1, board.getH() + 1);
        if (x >= queue.getX() && x <= queue.getX() + queue.getWidth()) {
            if (y >= queue.getY() && y <= queue.getY() + queue.getHeight()) {
                System.out.println("Queue click");
                queue.click(x, y);
                return;
            }
        }
        if (x >= display.getX() && x <= display.getX() + display.getWidth()) {
            if (y >= display.getY() && y <= display.getY() + display.getHeight()) {
                System.out.println("display click");
                display.click(x, y, this);
                return;
            }
        }
        if (currentShape != null) {
            int tX = x - board.getX();
            tX /= board.previousTileSize;
            int tY = y + board.getY();
            tY /= board.previousTileSize;
            System.out.println(tX + ":" + tY);
            if (board.canPlace(currentShape, tX, tY)) {
                board.placeShape(tX, tY, currentShape);
                currentShape = null;
                currentShapeImage = null;
                timer = 0;
            }
        }
    }

    public void startGame() {
        queue.empty();
        board.clear();
        timer = 0;
        counter = 0;
        score = 0;
        currentShape = null;
        currentShapeImage = null;
    }

    @Override
    public void keyPressed(int key, char c) {
        super.keyPressed(key, c);
        if (paused) {
            paused = false;
            return;
        }
        if (key == Keyboard.KEY_A) {
            queue.addShape(Shape.shapes[new Random().nextInt(Shape.shapes.length)]);
        }
        if (key == Keyboard.KEY_Z) {
            board.setSize(board.getW() + 1, board.getH() + 1);
        }
    }

    public void setCurrentShape(Shape shape) {
        if (currentShape != null) {
            queue.addShape(currentShape);
            currentShape = null;
            currentShapeImage = null;
        }
        currentShape = shape;
        currentShapeImage = shape.getPreview(16);
    }
}
