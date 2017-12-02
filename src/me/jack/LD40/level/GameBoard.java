package me.jack.LD40.level;

import org.newdawn.slick.*;

/**
 * Created by Jack on 02/12/2017.
 */
public class GameBoard {

    private int[][] tiles;
    private int x, y, w, h;
    private int screenW, screenH;
    private int tileSize = 16;
    private ImageBuffer drawSurface;
    private Image emptyTile = null;

    public GameBoard(int x, int y, int w, int h, int screenW, int screenH) {
        this.w = w;
        this.h = h;
        tiles = new int[w][h];
        this.screenW = screenW;
        this.screenH = screenH;
        this.x = x;
        this.y = y;
        try {
            drawSurface = new ImageBuffer(w * tileSize, h * tileSize);
            emptyTile = new Image("res/emptyTile.png");
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }

    public void render(Graphics g) throws SlickException {
        drawSurface = new ImageBuffer(w * tileSize, h * tileSize);
        g.translate(x, y);
        for (int xx = 0; xx != w; xx++) {
            for (int yy = 0; yy != h; yy++) {
                drawRect(xx * tileSize, yy * tileSize, tileSize, tileSize, drawSurface);
            }
        }
        Image correctSize = drawSurface.getImage().getScaledCopy(screenW, screenH);
        g.drawImage(correctSize, 0, 0);
        g.resetTransform();
    }

    private void drawRect(int x, int y, int w, int h, ImageBuffer buffer) {
        for (int xX = x; xX != x + w; xX++) {
            for (int yY = y; yY != y + h; yY++) {
                if (xX == 0 || yY == 0 || xX == (x + w) - 1 || yY == (y + h) - 1)
                    buffer.setRGBA(xX, yY, 255, 255, 255, 255);
            }
        }
    }

    public void setSize(int x, int h) {
        this.w = x;
        this.h = h;
    }

    public int getW() {
        return w;
    }

    public int getH() {
        return h;
    }
}
