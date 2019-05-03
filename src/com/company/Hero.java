package com.company;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Hero extends Mover {

    ArrayList<BufferedImage> sprites;
    int index;

    CharID id;

    public Hero(int x, int y, int w, int h, CharID id, ImageManager man) {
        super(x, y, w, h);
        this.id = id;

        sprites = new ArrayList<>();

        if(id == CharID.CharTest) {
            for(int i = 0; i < 7; i++) {
                sprites.add(man.getImage("Jim" + i));
            }
        }
    }
}
