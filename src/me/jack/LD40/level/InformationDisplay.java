package me.jack.LD40.level;

import me.jack.LD40.level.tile.Shape;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

import java.util.ArrayList;

/**
 * Created by Jack on 02/12/2017.
 */
public class InformationDisplay {

    private int screenW, screenH;
    private int x, y;


    public InformationDisplay(int x, int y, int screenW, int screenH) {
        this.screenW = screenW;
        this.screenH = screenH;
        this.x = x;
        this.y = y;
    }

    public void render(Graphics g) {
        g.translate(x, y);
        g.setColor(Color.red);
        g.fillRect(0, 0, screenW, screenH);
        g.resetTransform();
    }

}
