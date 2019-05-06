package com.company;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Hero extends Mover {

    ArrayList<BufferedImage> sprites;
    int index;

    CharID id;

    boolean up, down, left, right;

    public void setIndex(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    public Hero(int x, int y, int w, int h, CharID id, ImageManager man) {
        super(x, y, w, h);
        this.id = id;

        sprites = new ArrayList<>();

        if(id == CharID.CharTest) {
            for(int i = 0; i < 9; i++) {
                sprites.add(man.getImage("Jim" + i));
            }
        }
        up = false;
        down = false;
        left = false;
        right = false;
    }

    public void move() {
        int xChange = 0;
        int yChange = 0;

        if(up)
            yChange--;
        else if(down)
            yChange++;

        if(right)
            xChange++;
        else if(left)
            xChange--;

        if(yChange != 0) {
            if(xChange != 0) {
                super.move((int)((xChange * 5)/Math.sqrt(2)), (int)((yChange * 5)/Math.sqrt(2)));
            } else {
                super.move(0, yChange * 5);
            }
            return;
        }

        if(xChange != 0) {
            super.move(xChange * 5, 0);
        }
    }


    public void update(int f) {
        move();
        int i = f % 35;
        if (i % 2 == 0) {
            if(up)
                setIndex(6);
            if(down)
                setIndex(0);
            if(left)
                setIndex(4);
            if(right)
                setIndex(2);
        } else {
            if(up)
                setIndex(7);
            if(down)
                setIndex(1);
            if(left)
                setIndex(5);
            if(right)
                setIndex(3);
        }
    }
    public BufferedImage getImage(int i) {
        return sprites.get(i % 9);
    }
}
