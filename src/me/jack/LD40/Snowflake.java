package me.jack.LD40;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import java.util.Random;

/**
 * Created by Jack on 02/12/2017.
 */
public class Snowflake {
    private float x, y;
    private float yVel, xVel;
    private float age;
    private static Image img;
    public boolean dead = false;
    Random r = new Random();

    public Snowflake(int x, int y) throws SlickException {
        this.x = x;
        this.y = y;
        if (img == null)
            img = new Image("res/snow.png");
    }

    public void render(Graphics g) {
        if(x < 0) {
            xVel = 0.25f;
        }
        g.drawImage(img, x, y);
    }

    public void update() {
        y += yVel;
        x += xVel;
        yVel += 0.01;
        if (r.nextInt(20) == 0)
            xVel = (float) Math.sin(Math.toRadians(age)) * 0.5f;
        age += r.nextInt(20);
        if (age > 360)
            age = 0;
        if (y > 480) {
            dead = true;
        }
    }
}
