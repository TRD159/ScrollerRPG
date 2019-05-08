package com.company;

import java.awt.geom.Rectangle2D;

public class Bject2D implements Cloneable{
    Rectangle2D.Double rect;

    public Bject2D() {
        rect = new Rectangle2D.Double();
    }

    public Bject2D(Rectangle2D.Double rect) {
        this.rect = rect;
    }

    public Bject2D(double x, double y, double w, double h) {
        rect = new Rectangle2D.Double(x, y, w, h);
    }

    public void setRect(Rectangle2D.Double rect) {
        this.rect = rect;
    }
    public Rectangle2D.Double getRect() {
        return rect;
    }

    public boolean contactWith(Bject2D b) {
        return rect.intersects(b.getRect());
    }
    public boolean contactWith(Bject b) {
        return rect.intersects(b.getRect());
    }
}
