package com.company;

import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Enemy extends Mover {
    ArrayList<BufferedImage> sprites;

    public Enemy() {
    }

    public Enemy(Rectangle2D.Double rect) {
        super(rect);
    }

    public Enemy(int x, int y, int w, int h) {
        super(x, y, w, h);
    }
}
