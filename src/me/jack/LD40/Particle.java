package me.jack.LD40;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Jack on 02/12/2017.
 */
public class Particle {
    public static final ArrayList<Image> particles = new ArrayList<>();
    public static Random r = new Random();
    public boolean dead = false;
    int age = 0;

    public static void init() throws SlickException {
        particles.add(new Image("res/redBB.png"));
        particles.add(new Image("res/greenBB.png"));
    }

    private Image image;
    private float dX, dY;
    private int x, y;

    public Particle(int x, int y) {
        this.x = x;
        this.y = y;
        image = particles.get(r.nextInt(particles.size()));
        dX = r.nextFloat() * 30;
        dY = r.nextFloat() * 30;
        dX -= 15;
        dY -= 15;
    }

    public void render(Graphics g) {
        g.drawImage(image, x, y);

        dX /= 1.3;
        dY /= 1.3;

        x += dX;
        y += dY;

        if(Math.abs(dX) <= 0.1 || Math.abs(dY) <= 0.1){
            dX = 0;
            dY = 0;
        }
        age++;
        if(age > 5 && r.nextInt(20) == 0){
            dead = true;
        }
    }

}
