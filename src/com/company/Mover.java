package com.company;

import java.awt.*;

public class Mover extends Bject {
    public Mover() {
    }

    public Mover(Rectangle rect) {
        super(rect);
    }

    public Mover(int x, int y, int w, int h) {
        super(x, y, w, h);
    }

    public void move(int x, int y) {
        setRect(new Rectangle(getRect().x + x, getRect().y + y, getRect().width, getRect().height));
    }
}
