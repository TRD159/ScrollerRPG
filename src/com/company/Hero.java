package com.company;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Hero extends Mover {

    ArrayList<BufferedImage> sprites;
    int index;

    CharID id;

    boolean up, down, left, right;

    ArrayList<Bject> bjects;

    double speed;
    double falling;
    double jumps;

    double yAccel = 0;

    boolean arial = false;
    boolean jump;

    public void setIndex(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    public Hero(int x, int y, int w, int h, CharID id, ImageManager man, double speed) {
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

        this.speed = speed;

        jumps = 2;
        jump = true;
    }

    public void jump() {
        arial = true;
        yAccel = -10;
    }

    public void move() {
        double xChange = 0;
        double yChange = 0;

        for(Bject b: bjects) {
            if(contactWith(b) && b.rect.y > rect.y + rect.height)
                arial = false;
        }

        if(right)
            xChange+=speed;
        else if(left)
            xChange-=speed;

        if(arial)
            yChange += (yAccel = (yAccel > 5) ? 5 : yAccel + 0.25);

        /*
        if(xChange != 0 && yChange != 0) {
            xChange /= Math.sqrt(2);
            yChange /= Math.sqrt(2);
        }*/

        super.move(xChange, 0);
        for (Bject b: bjects) {
            if(contactWith(b)) {
                super.move(-xChange, 0);
                xChange = 0;
                break;
            }
        }

        if(yChange > 0) {
            fall: for (; yChange > 0; yChange--) {
                super.move(0, 1);
                for (Bject b : bjects) {
                    if (contactWith(b)) {
                        super.move(0, -1);
                        yChange = 0;
                        break fall;
                    }
                }
            }
        } else {
            jumping: for (; yChange < 0; yChange++) {
                super.move(0, -1);
                for (Bject b : bjects) {
                    if (contactWith(b)) {
                        super.move(0, 1);
                        yChange = 0;
                        break jumping;
                    }
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

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }
}
