package me.jack.LD40.level;

import me.jack.LD40.level.tile.Shape;
import me.jack.LD40.states.InGameState;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import java.awt.*;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by Jack on 02/12/2017.
 */
public class ShapeQueue {

    private int screenW, screenH;
    private int x, y;
    private ArrayDeque<Shape> waiting = new ArrayDeque();
    CopyOnWriteArrayList<ShapeContainer> shapes = new CopyOnWriteArrayList<>();
    int maxSize = 5;

    int fullCounter = 0;


    private Image background;
    private InGameState parent;

    public ShapeQueue(int x, int y, int screenW, int screenH, InGameState parent) {
        this.screenW = screenW;
        this.screenH = screenH;
        this.x = x;
        this.y = y;
        this.parent = parent;
        try {
            background = new Image("res/queueBg.png");
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }

    public void render(Graphics g) {
        g.translate(x, y);
        g.setColor(Color.gray);
        g.fillRect(0, 0, screenW, screenH);
        g.drawImage(background,0,0);
        int yPos = 0;
        g.setColor(Color.white);
        for (ShapeContainer container : shapes) {
            Rectangle r = container.r;
            g.drawRect(r.x, r.y, r.width, r.height);
            Image prev = container.image;
            g.drawImage(prev, r.x + (r.width / 2 - prev.getWidth() / 2), r.y + (r.height / 2 - prev.getHeight() / 2));
            g.fillRect(r.x, r.y, r.width * (1 - (container.age / container.maxAge)), 5);
            yPos += screenW;
            if (container.age > container.maxAge)
                shapes.remove(container);
        }
        g.setColor(Color.red);
        if (fullCounter != 0)
            g.fillRect(0, 0, 5, screenH * (1 - (fullCounter / 5000.0f)));
        g.resetTransform();
    }

    private void checkAdd() {
        boolean found = false;
        for (ShapeContainer c : shapes) {
            if (c.r.intersects(new Rectangle(0, 0, screenW, screenW))) {
                found = true;
                return;
            }
        }
        if (!found && !waiting.isEmpty()) {
            shapes.add(new ShapeContainer(new Rectangle(0, 0, screenW, screenW), waiting.pop(), 16));
        }
    }

    public void moveDown() {
        for (ShapeContainer container : shapes) {
            Rectangle r = container.r;
            Rectangle rMove = new Rectangle(r.x, r.y + container.vel, r.width, r.height);
            boolean found = false;
            for (ShapeContainer container2 : shapes) {
                Rectangle r2 = container2.r;
                if (r2 == r) continue;
                if (rMove.intersects(r2)) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                if (r.y + r.height + 1 >= screenH) {
                    continue;
                }
                r.translate(0, container.vel);
                container.vel += 1;
                if (container.vel > 8)
                    container.vel = 8;
            } else {
                container.vel = 0;
            }
        }
    }

    public void update(int delta, InGameState state) {
        moveDown();
        checkAdd();
        for (ShapeContainer container : shapes) {
            container.age++;
        }
        if (shapes.size() == maxSize) {
            fullCounter += delta;
        } else {
            fullCounter = 0;
        }
        if (fullCounter >= 5000) {
            System.out.println("Game over");
            state.gameOver();
        }
    }

    public void addShape(Shape shape) {
        if (shapes.size() >= maxSize || shapes.size() + waiting.size() >= maxSize)
            return;
        for (ShapeContainer c : shapes) {
            if (c.r.intersects(new Rectangle(0, 0, screenW, screenW))) {
                waiting.add(shape);
                return;
            }
        }
        shapes.add(new ShapeContainer(new Rectangle(0, 0, screenW, screenW), shape, 16));
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

    public void click(int x, int y) {
        for (ShapeContainer c : shapes) {
            if (c.r.contains(x, y)) {
                System.out.println("Shape clicked");
                shapes.remove(c);
                parent.setCurrentShape(c.getShape());
            }
        }
    }

    public boolean isEmpty() {
        return shapes.isEmpty() && waiting.isEmpty();
    }

    public void empty() {
        shapes.clear();
        ;
        waiting.clear();
        fullCounter = 0;
    }
}

class ShapeContainer {
    Rectangle r;
    Shape shape;
    int vel = 0;
    Image image;
    int age = 0;
    final float maxAge = 2000.0f;

    public ShapeContainer(Rectangle r, Shape shape, int tileSize) {
        this.r = r;
        this.shape = shape;
        image = shape.getPreview(tileSize);
    }

    public Rectangle getR() {
        return r;
    }

    public Shape getShape() {
        return shape;
    }
}