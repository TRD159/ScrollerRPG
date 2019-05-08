package com.company;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public class Mover extends Bject {
    public Mover() {
    }

    public Mover(Rectangle2D.Double rect) {
        super(rect);
    }

    public Mover(int x, int y, int w, int h) {
        super(x, y, w, h);
    }

    public void move(double x, double y) {
        setRect(new Rectangle2D.Double(getRect().x + x, getRect().y + y, getRect().width, getRect().height));
    }

    public void setCoords(double x, double y) {
        setRect(new Rectangle2D.Double(x, y, getRect().width, getRect().height));
    }
}
