package me.jack.LD40.level;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

/**
 * Created by Jack on 02/12/2017.
 */
public class ShapeQueue {

    private int screenW,screenH;
    private int x,y;
    public ShapeQueue(int x,int y,int screenW, int screenH) {
        this.screenW = screenW;
        this.screenH = screenH;
        this.x = x;
        this.y = y;
    }

    public void render(Graphics g){
        g.setColor(Color.gray);
        g.fillRect(x,y,screenW,screenH);
    }
}
