package me.jack.LD40.level;

import me.jack.LD40.level.tile.Shape;
import me.jack.LD40.states.InGameState;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.w3c.dom.css.Rect;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Jack on 02/12/2017.
 */
public class InformationDisplay {

    private int screenW, screenH;
    private int x, y;

    Rectangle pauseButton, quitButton;

    public InformationDisplay(int x, int y, int screenW, int screenH) {
        this.screenW = screenW;
        this.screenH = screenH;
        this.x = x;
        this.y = y;
        int bW = screenW / 4;
        pauseButton = new Rectangle(screenW - bW, 0, bW, screenH / 2);
        quitButton = new Rectangle(screenW - bW, screenH / 2, bW, screenH / 2);
    }

    public void render(Graphics g, InGameState state) {
        g.translate(x, y);
        g.setColor(Color.orange);
        g.fillRect(0, 0, screenW, screenH);
        g.setColor(Color.black);
        //  g.drawString("Next Shape added in: " + (1500 - state.timer), 0, 0);
        g.fillRect(0, 20, 100, 5);
        g.setColor(Color.white);
        g.fillRect(0, 20, 100 * (1 - (state.timer / 1500.0f)), 5);
        g.setColor(Color.black);
        g.drawString("Next Shape:", 0, 0);

        drawButton(g, pauseButton, "Pause");
        drawButton(g, quitButton, "Quit");


        g.resetTransform();
    }

    public void drawButton(Graphics g, Rectangle rectangle, String text) {
        g.setColor(Color.gray);
        g.fillRect(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
        g.setColor(Color.black);
        g.drawRect(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
        g.setColor(Color.white);
        g.drawString(text, (float) (rectangle.x + (rectangle.getWidth() / 2) - g.getFont().getWidth(text) / 2), (float) (rectangle.y + (rectangle.getHeight() / 2) - g.getFont().getLineHeight() / 2));
    }

    public void click(int x,int y,InGameState state){
        x -= getX();
        y -= getY();
        if(pauseButton.contains(x,y)){
            state.paused = true;
        }
    }
    public int getX() {
        return x;
    }

    public int getWidth() {
        return screenW;
    }

    public int getY() {
        return y;
    }

    public int getHeight() {
        return screenH;
    }
}
