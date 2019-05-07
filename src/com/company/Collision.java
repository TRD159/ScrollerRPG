package com.company;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class Collision extends Bject {
    BufferedImage img;

    public Collision() {
    }

    public Collision(Rectangle rect, String fileName) {
        super(rect);
        try {
            img = ImageIO.read(new File(fileName));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Collision(Rectangle rect) {
        super(rect);
        img = new BufferedImage(rect.width, rect.height, BufferedImage.TYPE_INT_ARGB);
        img.getGraphics().fillRect(0, 0, img.getWidth(), img.getHeight());
    }

    public Collision(int x, int y, int w, int h) {
        super(x, y, w, h);
        img = new BufferedImage(rect.width, rect.height, BufferedImage.TYPE_INT_ARGB);
        img.getGraphics().fillRect(0, 0, img.getWidth(), img.getHeight());
    }

    public Collision(int x, int y, int w, int h, String fileName) {
        super(x, y, w, h);
        try {
            img = ImageIO.read(new File(fileName));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public BufferedImage getImg() {
        return img;
    }
}
