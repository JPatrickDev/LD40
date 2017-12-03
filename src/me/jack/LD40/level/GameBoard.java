package me.jack.LD40.level;

import me.jack.LD40.Particle;
import me.jack.LD40.Snowflake;
import me.jack.LD40.level.tile.Shape;
import org.newdawn.slick.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

/**
 * Created by Jack on 02/12/2017.
 */
public class GameBoard {

    private int[][] tiles;
    private int x, y, w, h;
    private int screenW, screenH;
    private int tileSize = 16;
    private ImageBuffer drawSurface;
    private int[][] highlight;

    public int previousTileSize = 0;


    ArrayList<Particle> p = new ArrayList<>();
    ArrayList<Snowflake> snow = new ArrayList<>();

    public GameBoard(int x, int y, int w, int h, int screenW, int screenH) {
        this.w = w;
        this.h = h;
        tiles = new int[w][h];
        highlight = new int[w][h];
        this.screenW = screenW;
        this.screenH = screenH;
        this.x = x;
        this.y = y;

        drawSurface = new ImageBuffer(w * tileSize, h * tileSize);

        //   for (int i = 0; i != 400; i++)
        //     p.add(new Particle(screenW/2, screenH/2));

    }

    public void render(Graphics g) throws SlickException {
        fillRect(0, 0, w * tileSize, h * tileSize, drawSurface, new int[]{0, 0, 0, 0});
        g.translate(x, y);
        for (Snowflake f : snow) {
            f.update();
            f.render(g);
        }
        for (int xx = 0; xx != w; xx++) {
            for (int yy = 0; yy != h; yy++) {
                if (highlight[xx][yy] == 1 || tiles[xx][yy] == 1) {
                    fillRect(xx * tileSize, yy * tileSize, tileSize, tileSize, drawSurface, new int[]{255, 0, 0, 255});
                }
                drawRect(xx * tileSize, yy * tileSize, tileSize, tileSize, drawSurface, new int[]{255, 255, 255});
            }
        }
        Image i = drawSurface.getImage();
        i.setFilter(Image.FILTER_NEAREST);
        i.draw(0, 0, screenW, screenH);
        i = null;
        for (Particle pa : p) {
            pa.render(g);
        }
        g.resetTransform();
        previousTileSize = screenW / w;
        highlight = new int[w][h];

    }

    public void update() {
        checkForClear();
        Iterator<Particle> iterator = p.iterator();
        while (iterator.hasNext()) {
            Particle p = iterator.next();
            if (p.dead)
                iterator.remove();
        }
        Iterator<Snowflake> siterator = snow.iterator();
        while (siterator.hasNext()) {
            Snowflake p = siterator.next();
            if (p.dead)
                siterator.remove();
        }
        Random r = new Random();
        try {
            for (int i = 0; i != 3; i++) {
                int x = r.nextInt(screenW);
                snow.add(new Snowflake(x, -20));
            }
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }


    public int count() {
        int i = 0;
        for (int xX = 0; xX != w; xX++) {
            for (int yY = 0; yY != h; yY++) {
                if (tiles[xX][yY] == 1)
                    i += 1;
            }
        }
        return i;
    }


    private void drawRect(int x, int y, int w, int h, ImageBuffer buffer, int[] col) {
        for (int xX = x; xX != x + w; xX++) {
            for (int yY = y; yY != y + h; yY++) {
                if (xX == 0 || yY == 0 || xX == (x + w) - 1 || yY == (y + h) - 1)
                    buffer.setRGBA(xX, yY, col[0], col[1], col[2], 255);
            }
        }
    }

    private void fillRect(int x, int y, int w, int h, ImageBuffer buffer, int[] col) {
        for (int xX = x; xX != x + w; xX++) {
            for (int yY = y; yY != y + h; yY++) {
                buffer.setRGBA(xX, yY, col[0], col[1], col[2], col[3]);
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

    public void checkForClear() {
        ArrayList<Run> found = new ArrayList<>();
        for (int y = 0; y != h; y++) {
            int length = 0;
            int startX = 0;
            for (int x = 0; x != w; x++) {
                if (tiles[x][y] == 1) {
                    startX = x;
                    length = 1;
                    while ((x + 1) < w && tiles[x + 1][y] == 1) {
                        length++;
                        x++;
                    }
                } else {
                    if (length != 0) {
                        if (length >= 7)
                            found.add(new Run(startX, y, startX + (length - 1), y));
                        length = 0;
                    }
                }
            }
            if (length != 0) {
                if (length >= 7)
                    found.add(new Run(startX, y, startX + (length - 1), y));
                length = 0;
            }
        }
        for (int x = 0; x != w; x++) {
            int length = 0;
            int startY = 0;
            for (int y = 0; y != h; y++) {
                if (tiles[x][y] == 1) {
                    startY = y;
                    length = 1;
                    while ((y + 1) < h && tiles[x][y + 1] == 1) {
                        length++;
                        y++;
                    }
                } else {
                    if (length != 0) {
                        if (length >= 7)
                            found.add(new Run(x, startY, x, startY + (length - 1)));
                        length = 0;
                    }
                }
            }
            if (length != 0) {
                if (length >= 7)
                    found.add(new Run(x, startY, x, startY + (length - 1)));
                length = 0;
            }
        }

        for (Run r : found) {
            if (r.type) {
                for (int y = r.startY; y <= r.endY; y++) {
                    tiles[r.startX][y] = 0;
                    for (int i = 0; i != 4; i++)
                        p.add(new Particle(r.startX * previousTileSize, y * previousTileSize));
                }
            } else {
                for (int x = r.startX; x <= r.endX; x++) {
                    tiles[x][r.startY] = 0;
                    for (int i = 0; i != 4; i++)
                        p.add(new Particle(x * previousTileSize, r.startY * previousTileSize));
                }
            }
        }
    }

    public void clear() {
        tiles = new int[w][h];
        highlight = new int[w][h];
    }
}

class Run {
    int startX, startY;
    int endX, endY;
    boolean type;

    public Run(int startX, int startY, int endX, int endY) {
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
        if (startX == endX)
            type = true;
        else if (startY == endY)
            type = false;
    }
}
