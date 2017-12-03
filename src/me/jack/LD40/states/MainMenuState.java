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
    boolean mainMenu = false,aboutMenu = false;
    Rectangle play = new Rectangle(70,124,340,98);
    Rectangle about = new Rectangle(70,306,340,98);
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
        if(mainMenu) {
            stateBasedGame.enterState(0);
            mainMenu = false;
        }
        if(aboutMenu) {
            stateBasedGame.enterState(2);
            aboutMenu = false;
        }
    }

    @Override
    public void mouseReleased(int button, int x, int y) {
        super.mouseReleased(button, x, y);
        if(play.contains(x,y)){
            InGameState.sounds.get("place").play();
            mainMenu = true;
        }
        if(about.contains(x,y)){
            InGameState.sounds.get("place").play();
            aboutMenu = true;
        }
    }
}
