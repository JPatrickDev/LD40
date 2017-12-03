package me.jack.LD40.level.tile;

import org.newdawn.slick.Image;
import org.newdawn.slick.ImageBuffer;

/**
 * Created by Jack on 02/12/2017.
 */
public class Shape {

    private int w, h;
    public int selectX = 0;
    public int selectY = 0;
    private int[][] shape;

    public static Shape[] shapes;

    static {
        shapes = new Shape[14];

        // XX
        // XX
        shapes[0] = new Shape(2, 2);
        shapes[0].setShape(new int[][]{{1, 1}, {1, 1}});

        //X
        //X
        //XX
        shapes[1] = new Shape(2, 3);
        shapes[1].setShape(new int[][]{{1, 1, 1}, {0, 0, 1}});

        //X
        //X
        //X
        //X
        shapes[2] = new Shape(1, 4);
        shapes[2].setShape(new int[][]{{1, 1, 1, 1}});

        // X
        // X
        //XX
        shapes[3] = new Shape(2, 3);
        shapes[3].setShape(new int[][]{{0, 0, 1}, {1, 1, 1}});
        shapes[3].setSelectPos(1, 0);

        //XX
        //X
        //X
        shapes[4] = new Shape(2, 3);
        shapes[4].setShape(new int[][]{{1, 1, 1}, {1, 0, 0}});
        ;

        //XX
        // X
        // X
        shapes[5] = new Shape(2, 3);
        shapes[5].setShape(new int[][]{{1, 0, 0}, {1, 1, 1}});


        // X
        //XXX
        shapes[6] = new Shape(3, 2);
        shapes[6].setShape(new int[][]{{0, 1}, {1, 1}, {0, 1}});
        shapes[6].setSelectPos(1, 0);

        //XXX
        // X
        shapes[7] = new Shape(3, 2);
        shapes[7].setShape(new int[][]{{1, 0}, {1, 1}, {1, 0}});


        //  X
        //XXX
        shapes[8] = new Shape(3, 2);
        shapes[8].setShape(new int[][]{{0, 1}, {0, 1}, {1, 1}});
        shapes[8].setSelectPos(0, 1);

        //XXX
        //  X
        shapes[9] = new Shape(3, 2);
        shapes[9].setShape(new int[][]{{1, 0}, {1, 0}, {1, 1}});

        //X
        //XXX
        shapes[10] = new Shape(3, 2);
        shapes[10].setShape(new int[][]{{1, 1}, {0, 1}, {0, 1}});

        //XXX
        //X
        shapes[11] = new Shape(3,2);
        shapes[11].setShape(new int[][]{{1, 1}, {1, 0}, {1, 0}});

        //X
        //XX
        //X
        shapes[12] = new Shape(2, 3);
        shapes[12].setShape(new int[][]{{1,1,1},{0,1,0}});

        // X
        //XX
        // X
        shapes[13] = new Shape(2, 3);
        shapes[13].setShape(new int[][]{{0,1,0},{1,1,1}});
        shapes[13].setSelectPos(0, 1);
    }

    private void setSelectPos(int x, int y) {
        this.selectX = x;
        this.selectY = y;
    }

    public Shape(int w, int h) {
        this.w = w;
        this.h = h;
        shape = new int[w][h];
    }


    public Image getPreview(int tileSize) {
        ImageBuffer buffer = new ImageBuffer(this.w, this.h);
        for (int x = 0; x != this.w; x++) {
            for (int y = 0; y != this.h; y++) {
                if (shape[x][y] == 1)
                    buffer.setRGBA(x, y, 255, 0, 0, 255);
            }
        }
        Image i = buffer.getImage();
        i.setFilter(Image.FILTER_NEAREST);
        return i.getScaledCopy(this.w * tileSize, this.h * tileSize);
    }

    public int[][] getShape() {
        return shape;
    }

    public int getW() {
        return w;
    }

    public int getH() {
        return h;
    }

    public void setShape(int[][] shape) {
        this.shape = shape;
    }
}
