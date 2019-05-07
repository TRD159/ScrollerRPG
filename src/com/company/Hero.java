package com.company;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Hero extends Mover {

    ArrayList<BufferedImage> sprites;
    int index;

    CharID id;

    boolean up, down, left, right;

    ArrayList<Bject> bjects;

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
        bjects = new ArrayList<>();

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
        double xChange = 0;
        double yChange = 0;

        if(up)
            yChange-=2;
        else if(down)
            yChange+=2;

        if(right)
            xChange+=2;
        else if(left)
            xChange-=2;

        if(yChange != 0) {
            if(xChange != 0) {
                super.move((int)((xChange)/Math.sqrt(2)), (int)((yChange)/Math.sqrt(2)));
                for(Bject b: bjects) {
                    if(contactWith(b) && b instanceof Collision) {
                        super.move((int)(-(xChange)/Math.sqrt(2)), (int)(-(yChange)/Math.sqrt(2)));
                    }
                }
            } else {
                super.move(0, (int)yChange);
                for (Bject b: bjects) {
                    if(contactWith(b) && b instanceof Collision) {
                        super.move(0, (int)yChange * -1);
                    }
                }
            }
            return;
        }

        if(xChange != 0) {
            super.move((int)xChange, 0);
            for (Bject b: bjects) {
                if(contactWith(b) && b instanceof Collision) {
                    super.move((int)xChange * -1, 0);
                }
            }
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

    public ArrayList<Bject> getBjects() {
        return bjects;
    }

    public void setBjects(ArrayList<Bject> bjects) {
        this.bjects = bjects;
    }
}
