package com.company;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public class Screen extends Bject{
    public Screen() {
        super(0, 0, 400, 400);
    }
    public Screen(int x, int y, int w, int h) {
        super(x, y, w, h);
    }

    public void mX(double i) {
        double x = (int)getRect().getX();
        setRect(new Rectangle2D.Double(x + i, getRect().y, getRect().width, getRect().height));
    }

    public void mY(double i) {
        double y = getRect().y;
        setRect(new Rectangle2D.Double(getRect().x, y + i, getRect().width, getRect().height));
    }

    public boolean isInScreen(Bject b) {
        return contactWith(b);
    }
}
