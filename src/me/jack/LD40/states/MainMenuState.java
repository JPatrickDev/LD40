package me.jack.LD40.states;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import java.awt.*;

/**
 * Created by Jack on 03/12/2017.
 */
public class MainMenuState extends BasicGameState {

    Image image;
    boolean go = false;
    Rectangle play = new Rectangle(70,124,340,98);
    @Override
    public int getID() {
        return 1;
    }

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        image = new Image("res/mainMenu.png");
    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
        graphics.drawImage(image, 0, 0);
    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) throws SlickException {
        if(go)
            stateBasedGame.enterState(0);
    }

    @Override
    public void mouseReleased(int button, int x, int y) {
        super.mouseReleased(button, x, y);
        if(play.contains(x,y)){
            go = true;
        }
    }
}
