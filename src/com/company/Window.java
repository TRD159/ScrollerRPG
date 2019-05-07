package com.company;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.*;

public class Window extends JFrame implements Runnable {

    int UPS;
    double TBU;
    int frame = 0;

    BufferedImage background;

    Screen s;

    int anchorX, anchorY;

    Bject test, e;

    ImageManager man;

    Hero h;

    Set<Bject> bjects;

    public Window(String title, int UPS) throws HeadlessException {
        super(title);

        this.UPS = UPS;
        TBU = 1000.0/UPS;

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setSize(600, 500);

        background = new BufferedImage(4000, 1600, BufferedImage.TYPE_INT_ARGB);
        for(int x = 0; x < background.getWidth(); x += 200) {
            for(int y = 0; y < background.getHeight(); y += 200) {
                try {
                    background.getGraphics().drawImage(ImageIO.read(new File("BetaTile.png")), x, y, null);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        s = new Screen((background.getWidth() - getWidth())/2, (background.getHeight() - getHeight())/2, getWidth(), getHeight());

        bjects = new HashSet<>();

        man = new ImageManager();
        man.loadImages("Images.txt");

        h = new Hero(background.getWidth()/2, background.getHeight()/2, 18, 15, CharID.CharTest, man);

        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_UP:
                        h.up = true;
                        break;
                    case KeyEvent.VK_DOWN:
                        h.down = true;
                        break;
                    case KeyEvent.VK_LEFT:
                        h.left = true;
                        break;
                    case KeyEvent.VK_RIGHT:
                        h.right = true;
                        break;
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_UP:
                        h.up = false;
                        break;
                    case KeyEvent.VK_DOWN:
                        h.down = false;
                        break;
                    case KeyEvent.VK_LEFT:
                        h.left = false;
                        break;
                    case KeyEvent.VK_RIGHT:
                        h.right = false;
                        break;
                }
            }
        });

        anchorX = s.getRect().x;
        anchorY = s.getRect().y;

        test = new Collision(1000, 500, 500, 500);
        e = new Collision(2000, 250, 300, 50);

        addBjects(new ArrayList<Bject>() {
            {
                add(test);
                add(e);
            }
        });

        setVisible(true);

        Thread t = new Thread(this);
        t.start();
    }

    private void addBjects(Bject b) {
        bjects.add(b);
        h.setBjects(new ArrayList<>() {
            {
                try {
                    for (Bject c : bjects) add((Bject)c.clone());
                } catch (Exception e) {
                    e.printStackTrace();
                    System.exit(0);
                }
            }
        });
    }

    private void addBjects(ArrayList<Bject> b) {
        bjects.addAll(b);
        h.setBjects(new ArrayList<>() {
            {
                try {
                    for (Bject c : bjects) add((Bject)c.clone());
                } catch (Exception e) {
                    e.printStackTrace();
                    System.exit(0);
                }
            }
        });
    }

    @Override
    public void run() {
        long start = System.nanoTime();
        long uDone = 0;

        while (true) {
            long uNeed = (long)(((System.nanoTime() - start)/1000000)/TBU);
            boolean repaint = false;
            for(; uDone < uNeed; uDone++) {
                update();
                repaint = true;
            }
            if(repaint) {
                repaint();
            }
        }
    }

    public void update() {
        frame = (frame + 1) % 35;
        h.update(frame);
        scroll(h.rect.x - s.rect.x, h.rect.y - s.rect.y);
    }

    private void scroll(int rX, int rY) {
        //System.out.println(rX + " " + rY);

        int bufferX = getWidth()/5;
        int bufferY = getHeight()/5;

        if(rX < bufferX) {
            if(h.left && s.getRect().x > 0) {
                if(h.up ^ h.down) {
                    s.mX((int)(-5 * Math.sqrt(2)));
                } else {
                    s.mX(-5);
                }
            }
        }
        if(rX > getWidth() - bufferX) {
            if(h.right && s.rect.x + s.rect.width < background.getWidth()) {
                if(h.up ^ h.down) {
                    s.mX((int)(5 * Math.sqrt(2)));
                } else {
                    s.mX(5);
                }
            }
        }

        if(rY < bufferY) {
            if(h.up && s.rect.y > 0) {
                if(h.left ^ h.right) {
                    s.mY((int)(-5 * Math.sqrt(2)));
                } else {
                    s.mY(-5);
                }
            }
        }
        if(rY > getHeight() - bufferY) {
            if(h.down && s.rect.y + s.rect.height < background.getHeight()) {
                if(h.left ^ h.right) {
                    s.mY((int)(5 * Math.sqrt(2)));
                } else {
                    s.mY(5);
                }
            }
        }
    }

    public void paint(Graphics g) {
        BufferedImage game = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
        int x = (int)((s.getRect().x - anchorX) * 0.6 + anchorX);
        int y = (int)((s.getRect().y - anchorY) * 0.6 + anchorY);

        Graphics g2 = game.getGraphics();
        g2.drawImage(background.getSubimage(x, y, getWidth(), getHeight()), 0, 0, null);

        for(Bject b: bjects) {
            if(s.isInScreen(b)) {
                if(b instanceof Collision) {
                    g2.drawImage(((Collision) b).getImg(), b.rect.x - s.rect.x, b.rect.y - s.rect.y, null);
                } else {
                    g2.fillRect(b.getRect().x - s.getRect().x, b.getRect().y - s.getRect().y, b.getRect().width, b.getRect().height);
                }
            }
        }

        if(s.isInScreen(h)) {
            g2.drawImage(h.getImage(h.index), h.rect.x - s.rect.x, h.rect.y - s.rect.y, null);
        }
        g.drawImage(game, 0, 0, null);
    }
}
