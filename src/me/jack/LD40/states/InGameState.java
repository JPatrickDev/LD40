package me.jack.LD40.states;

import me.jack.LD40.level.GameBoard;
import me.jack.LD40.level.InformationDisplay;
import me.jack.LD40.level.ShapeQueue;
import me.jack.LD40.level.tile.Shape;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
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

    @Override
    public int getID() {
        return 0;
    }

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        queue = new ShapeQueue(0, 0, 80, gameContainer.getHeight() - 80);
        board = new GameBoard(80, 0, 10, 10, 400, 400);
        display = new InformationDisplay(0,400,480,80);
        queue.addShape(Shape.shapes[0]);
       // queue.addShape(Shape.shapes[1]);
    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
        queue.render(graphics);
        board.render(graphics);
        display.render(graphics);
    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) throws SlickException {
    }


    @Override
    public void mouseReleased(int button, int x, int y ) {
        super.mousePressed(button, x, y);
       // board.setSize(board.getW() + 1, board.getH() + 1);
        if(x >= queue.getX() && x <= queue.getX() + queue.getWidth()){
            if(y >= queue.getY() && y <= queue.getY() + queue.getHeight()){
                System.out.println("Queue click");
                queue.click(x,y);
            }
        }
    }

    @Override
    public void keyPressed(int key, char c) {
        super.keyPressed(key, c);
        if(key == Keyboard.KEY_A){
            queue.addShape(Shape.shapes[new Random().nextInt(Shape.shapes.length)]);
        }
    }
}
