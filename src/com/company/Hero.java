package com.company;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Hero extends Mover {

    ArrayList<BufferedImage> sprites;
    int index;

    CharID id;

    boolean up, down, left, right;

    ArrayList<Collision> bjects;

    double speed;
    double falling;
    double jumps;

    double yAccel = 0;

    boolean arial = false;
    boolean jump;

    double hp = 100;

    double magnitude = 0;
    double angle = 0;

    int fHit = 0;

    DScale d;

    double x, y;

    public void setIndex(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    public Hero(int x, int y, int w, int h, CharID id, ImageManager man, double speed, ArrayList<Collision> bjects) {
        super(x, y, w, h);
        this.id = id;

        sprites = new ArrayList<>();
        this.bjects = bjects;

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

        d = DScale.Basic;
    }

    public void jump() {
        if(jumps > 0) {
            yAccel = -10;
            jumps--;
        }
    }

    public void move() {
        double xChange = 0;
        double yChange = 0;

        if(fHit == -11111) {

        } else {
            /*
            arial = true;
            for (Bject b : bjects) {
                if (Math.abs(b.rect.y - (rect.y + rect.height)) < 2 && b.rect.x < (rect.x + rect.width) && b.rect.x + b.rect.width > rect.x) {
                    arial = false;
                    break;
                }
            }*/
            arial = true;
            if (arial) {
                yChange += (yAccel = (yAccel > 5) ? 5 : yAccel + 0.25);
                //System.out.println(yAccel);
            }


            if (right)
                xChange += speed;
            else if (left)
                xChange -= speed;

            magnitude = Math.sqrt(Math.pow(xChange, 2) + Math.pow(yChange, 2));
            angle = Math.atan2(yChange, xChange);
            //System.out.println(magnitude);

            if(xChange > 0) {
                for(; xChange > 0; xChange--) {
                    super.move(1, 0);
                    for (Bject b: bjects) {
                        if(contactWith(b)) {
                            super.move(-1, 0);
                            xChange = 0;
                        }
                    }
                }
            }
            super.move(xChange, 0);
            for (Bject b : bjects) {
                if (contactWith(b)) {
                    super.move(-xChange, 0);
                    xChange = 0;
                    break;
                }
            }

            if (yChange > 0) {
                fall:
                for (; yChange > 0; yChange--) {
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
                jumping:
                for (; yChange < 0; yChange++) {
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

            if (arial) {
                for (Bject b : bjects) {
                    if (b instanceof Collision && Math.abs(b.rect.y - (rect.y + rect.height)) < 2 && b.rect.x < (rect.x + rect.width) && b.rect.x + b.rect.width > rect.x) {
                        arial = false;
                        yAccel = 0;
                        jumps = 2;
                    }
                }
            }
            System.out.println(jumps);
        }

    }

    public void damage(int j, ArrayList<Enemy> n) {
        Enemy c = n.get(j);
        c.setHp(c.getHp() - (d.getScale()));
        double cent = rect.x + rect.width/2;
        double eCent = c.getRect().x + c.getRect().width/2;

        if(cent == eCent) {
            if(left) {
                x = -3;
            } else {
                x = 3;
            }
        } else if(cent < eCent) {
            x = -3;
        } else {
            x = 3;
        }
    }

    public void update(int f, ArrayList<Enemy> n) {
        move();
        for(int j = 0; j < n.size(); j++) {
            if(contactWith(n.get(j))) {
                damage(j, n);
                fHit = 31;
            }
        }
        fHit--;


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

    public ArrayList<Collision> getBjects() {
        return bjects;
    }

    public void setBjects(ArrayList<Collision> bjects) {
        this.bjects = bjects;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }
}
