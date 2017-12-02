package me.jack.LD40;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;

/**
 * Created by Jack on 02/12/2017.
 */
public class Main {

    public static void main(String[] args) throws SlickException {
        AppGameContainer agc = new AppGameContainer(new LD40("LD40 - Theme: The more you have, the worse it is."));
        agc.setDisplayMode(600,480,false);
        agc.setTargetFrameRate(60);
        agc.start();
    }


}
