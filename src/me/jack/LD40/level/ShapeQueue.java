package me.jack.LD40.level;

import me.jack.LD40.level.tile.Shape;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

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


    public ShapeQueue(int x, int y, int screenW, int screenH) {
        this.screenW = screenW;
        this.screenH = screenH;
        this.x = x;
        this.y = y;
    }

    public void render(Graphics g) {
        g.translate(x, y);
        g.setColor(Color.gray);
        g.fillRect(0, 0, screenW, screenH);
        int yPos = 0;
        g.setColor(Color.white);
        for (ShapeContainer container : shapes) {
            Shape s = container.getShape();
            Rectangle r = container.r;
            g.drawRect(r.x, r.y, r.width, r.height);
            Image prev = s.getPreview(16);
            g.drawImage(prev, r.x + (r.width / 2 - prev.getWidth() / 2), r.y + (r.height / 2 - prev.getHeight() / 2));
            yPos += screenW;
        }
        g.resetTransform();
        moveDown();
        checkAdd();
    }

    private void checkAdd() {
        boolean found = false;
        for (ShapeContainer c : shapes) {
            if (c.r.intersects(new Rectangle(0, 0, screenW, screenW))) {
                found = true;
                return;
            }
        }
        if(!found && !waiting.isEmpty()){
            shapes.add(new ShapeContainer(new Rectangle(0, 0, screenW, screenW), waiting.pop()));
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
                if (r.y + r.height + 1 >= screenH)
                    continue;
                r.translate(0, container.vel);
                container.vel += 1;
                if (container.vel > 8)
                    container.vel = 8;
            } else {
                container.vel = 0;
            }
        }
    }

    public void addShape(Shape shape) {
        if(shapes.size() >= maxSize || shapes.size() + waiting.size() >= maxSize)
            return;
        for (ShapeContainer c : shapes) {
            if (c.r.intersects(new Rectangle(0, 0, screenW, screenW))) {
                waiting.add(shape);
                return;
            }
        }
        shapes.add(new ShapeContainer(new Rectangle(0, 0, screenW, screenW), shape));
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
            }
        }
    }
}

class ShapeContainer {
    Rectangle r;
    Shape shape;
    int vel = 0;

    public ShapeContainer(Rectangle r, Shape shape) {
        this.r = r;
        this.shape = shape;
    }

    public Rectangle getR() {
        return r;
    }

    public Shape getShape() {
        return shape;
    }
}