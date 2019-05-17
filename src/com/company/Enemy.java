package com.company;

import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Enemy extends Mover {
    public static final int GOOMBA = 1, KOOPA = 2;

    ArrayList<BufferedImage> sprites;
    ArrayList<Bject> bjects;

    boolean arial = false;

    double yAccel = 0;
    double speed;
    double jumps;

    //Hero hero;

    boolean targeting = false;

    boolean right = false;
    boolean left = false;

    int type;

    double hp;

    int index;

    double velocity = 0;

    public Enemy() {
    }

    public Enemy(Rectangle2D.Double rect) {
        super(rect);
    }

    public Enemy(int x, int y, int w, int h, ImageManager man, int t) {
        super(x, y, w, h);
        //hero = hr;

        type = t;

        bjects = new ArrayList<>();

        sprites = new ArrayList<>();
        switch (t) {
            case GOOMBA:
                hp = 25;
                speed = 1.5;
                jumps = 0;
                sprites.add(man.getImage("Goomba0"));
                sprites.add(man.getImage("Goomba1"));
                break;

        }
    }

    public void move() {
        double xChange = 0;
        double yChange = 0;

        arial = true;
        for (Bject b : bjects) {
            if ((b.rect.y - (rect.y + rect.height) <= 1 && b.rect.x < (rect.x + rect.width) && b.rect.x + b.rect.width > rect.x)) {
                arial = false;
                break;
            }
        }
        if(arial) {
            yChange += (yAccel = (yAccel > 5) ? 5 : yAccel + 0.25);
            //System.out.println(yAccel);
        }

        //System.out.println(bjects.size());

        if(right)
            xChange+=speed;
        else if(left)
            xChange-=speed;

        velocity = Math.sqrt(Math.pow(xChange, 2) + Math.pow(yChange, 2));
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
            fall: for (; yChange > 0; yChange-= 0.25) {
                super.move(0, 0.25);
                for (Bject b : bjects) {
                    if (contactWith(b)) {
                        super.move(0, -0.25);
                        yChange = 0;
                        yAccel = 0;
                        arial = false;
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
                        yAccel = 0;
                        arial = false;
                        break jumping;
                    }
                }
            }
        }

        if(arial) {
            for (Bject b : bjects) {
                if (Math.abs(b.rect.y - (rect.y + rect.height)) < 1 && b.rect.x < (rect.x + rect.width) && b.rect.x + b.rect.width > rect.x) {
                    //System.out.println("contact");
                    arial = false;
                    yAccel = 0;
                }
            }
        }
    }

    public void update(Hero hero, int f) {
        double hMidx = hero.rect.x + hero.rect.width/2;
        double tMidx = rect.x + rect.width/2;

        double hMidy = hero.rect.y + hero.rect.height/2;
        double tMidy = rect.y + rect.height/2;
        if(type != GOOMBA) {
            targeting = Math.abs((hero.rect.x + hero.rect.width / 2) - (rect.x + rect.width / 2)) < 200
                    && Math.abs((hero.rect.y + hero.rect.height / 2) - (rect.y + rect.height / 2)) < 150;
        } else {
            targeting = Math.abs((hero.rect.x + hero.rect.width / 2) - (rect.x + rect.width / 2)) < 200
                    && Math.abs((hero.rect.y + hero.rect.height / 2) - (rect.y + rect.height / 2)) < 25;
        }
        if(!targeting) {
            if(f == 0) {
                double dir = Math.random() * 150;

                if (dir < 40) {
                    left = true;
                    right = false;
                } else if (dir > 110) {
                    left = false;
                    right = true;
                } else {
                    left = false;
                    right = false;
                }
            }
        } else {
            if(hMidx < tMidx) {
                left = true;
                right = false;
            } else if(hMidx > tMidx) {
                right = true;
                left = false;
            }
        }


        move();

        int i = f % 2;
        index = i;
    }

    public double getHp() {
        return hp;
    }

    public void setHp(double hp) {
        this.hp = hp;
    }

    public BufferedImage getImage() {
        return sprites.get(index);
    }

    public ArrayList<Bject> getBjects() {
        return bjects;
    }

    public void setBjects(ArrayList<Bject> bjects) {
        this.bjects = bjects;
    }
}
