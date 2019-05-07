package com.company;

import java.awt.*;

public class Bject implements Cloneable {
    Rectangle rect;

    public Bject() {
        rect = new Rectangle();
    }

    public Bject(Rectangle rect) {
        this.rect = rect;
    }
    public Bject(int x, int y, int w, int h) {
        rect = new Rectangle(x, y, w, h);
    }

    public void setRect(Rectangle rect) {
        this.rect = rect;
    }
    public Rectangle getRect() {
        return rect;
    }

    public boolean contactWith(Bject b) {
        return rect.intersects(b.getRect());
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        Bject b = (Bject)super.clone();
        b.rect = new Rectangle(rect.x, rect.y, rect.width, rect.height);

        return b;
    }
}
