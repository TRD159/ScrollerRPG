package com.company;

import java.awt.*;

public class Screen extends Bject{
    public Screen() {
        super(0, 0, 400, 400);
    }
    public Screen(int x, int y, int w, int h) {
        super(x, y, w, h);
    }

    public void mX(int i) {
        int x = (int)getRect().getX();
        setRect(new Rectangle(x + i, getRect().y, getRect().width, getRect().height));
    }

    public void mY(int i) {
        int y = getRect().y;
        setRect(new Rectangle(getRect().x, y + i, getRect().width, getRect().height));
    }

    public boolean isInScreen(Bject b) {
        return contactWith(b);
    }
}
