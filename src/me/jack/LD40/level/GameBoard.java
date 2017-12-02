package me.jack.LD40.level;

import me.jack.LD40.level.tile.Shape;
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
    private int[][] highlight;

    public int previousTileSize = 0;

    public GameBoard(int x, int y, int w, int h, int screenW, int screenH) {
        this.w = w;
        this.h = h;
        tiles = new int[w][h];
        highlight = new int[w][h];
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
                if (highlight[xx][yy] == 1 || tiles[xx][yy] == 1) {
                    fillRect(xx * tileSize, yy * tileSize, tileSize, tileSize, drawSurface);
                }
            }
        }
        Image i = drawSurface.getImage();
        i.setFilter(Image.FILTER_NEAREST);
        Image correctSize = i.getScaledCopy(screenW, screenH);
        g.drawImage(correctSize, 0, 0);
        g.resetTransform();
        previousTileSize = screenW / w;
        highlight = new int[w][h];
    }

    private void drawRect(int x, int y, int w, int h, ImageBuffer buffer) {
        for (int xX = x; xX != x + w; xX++) {
            for (int yY = y; yY != y + h; yY++) {
                if (xX == 0 || yY == 0 || xX == (x + w) - 1 || yY == (y + h) - 1)
                    buffer.setRGBA(xX, yY, 255, 255, 255, 255);
            }
        }
    }

    private void fillRect(int x, int y, int w, int h, ImageBuffer buffer) {
        for (int xX = x; xX != x + w; xX++) {
            for (int yY = y; yY != y + h; yY++) {
                buffer.setRGBA(xX, yY, 255, 0, 0, 255);
            }
        }
    }

    public boolean canPlace(Shape shape, int x, int y) {
        for (int xX = x; xX != x + shape.getW(); xX++) {
            for (int yY = y; yY != y + shape.getH(); yY++) {
                try {
                    int i = tiles[xX][yY];
                    if (i == 1) {
                        if (shape.getShape()[xX - x][yY - y] == 1) {
                            return false;
                        }
                    }
                } catch (Exception e) {
                    return false;
                }
            }
        }
        return true;
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

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setHighlighted(int x, int y, Shape shape) {
        try {
            for (int xX = x; xX != x + shape.getW(); xX++) {
                for (int yY = y; yY != y + shape.getH(); yY++) {
                    if (shape.getShape()[xX - x][yY - y] == 1) {
                        highlight[xX][yY] = 1;
                    }
                }
            }
        } catch (Exception e) {
            //TODO - Fix
        }
    }

    public void placeShape(int x, int y, Shape shape) {
        try {
            for (int xX = x; xX != x + shape.getW(); xX++) {
                for (int yY = y; yY != y + shape.getH(); yY++) {
                    if (shape.getShape()[xX - x][yY - y] == 1) {
                        tiles[xX][yY] = 1;
                    }
                }
            }
        } catch (Exception e) {
            //TODO - Fix
        }
    }
}
