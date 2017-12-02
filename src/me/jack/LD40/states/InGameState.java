package me.jack.LD40.states;

import me.jack.LD40.level.GameBoard;
import me.jack.LD40.level.ShapeQueue;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

/**
 * Created by Jack on 02/12/2017.
 */
public class InGameState extends BasicGameState {

    private GameBoard board;
    private ShapeQueue queue;

    @Override
    public int getID() {
        return 0;
    }

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        queue = new ShapeQueue(0, 0, 100, gameContainer.getHeight());
        board = new GameBoard(100, 0, 10, 10, 500, 480);
    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
        queue.render(graphics);
        board.render(graphics);
    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) throws SlickException {

    }

    @Override
    public void mousePressed(int button, int x, int y) {
        super.mousePressed(button, x, y);
        board.setSize(board.getW() + 1, board.getH() + 1);
    }
}
