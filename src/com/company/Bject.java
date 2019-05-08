package com.company;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public class Bject implements Cloneable {
    Rectangle2D.Double rect;

    public Bject() {
        rect = new Rectangle2D.Double();
    }

    public Bject(Rectangle2D.Double rect) {
        this.rect = rect;
    }
    public Bject(int x, int y, int w, int h) {
        rect = new Rectangle2D.Double(x, y, w, h);
    }

    public void setRect(Rectangle2D.Double rect) {
        this.rect = rect;
    }
    public Rectangle2D.Double getRect() {
        return rect;
    }

    public boolean contactWith(Bject b) {
        return rect.intersects(b.getRect());
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        Bject b = (Bject)super.clone();
        b.rect = new Rectangle2D.Double(rect.x, rect.y, rect.width, rect.height);

        return b;
    }
}
