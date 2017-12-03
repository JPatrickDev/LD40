package me.jack.LD40;

import me.jack.LD40.states.AboutState;
import me.jack.LD40.states.InGameState;
import me.jack.LD40.states.MainMenuState;
import org.newdawn.slick.Game;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

/**
 * Created by Jack on 02/12/2017.
 */
public class LD40 extends StateBasedGame {
    public LD40(String s) {
        super(s);
    }

    @Override
    public void initStatesList(GameContainer gameContainer) throws SlickException {
        addState(new MainMenuState());
        addState(new InGameState());
        addState(new AboutState());
    }
}
