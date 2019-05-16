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

    double anchorX, anchorY;

    Bject test, e;

    ImageManager man;

    Hero h;

    Set<Bject> bjects;

    boolean jump = true;
    boolean typed = true;

    StringBuilder selected;

    BufferedImage men;

    ArrayList<Enemy> n;

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

        men = new BufferedImage(100, 200, BufferedImage.TYPE_INT_ARGB);

        selected = new StringBuilder();

        h = new Hero(background.getWidth()/2, background.getHeight()/2, 48, 54, CharID.CharTest, man, 2.0);


        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);
                /*if(typed) {
                    char c = Character.toUpperCase(e.getKeyChar());
                    if (c >= 'A' && c <= 'Z') {
                        selected.append(c);
                        System.out.println(selected);
                    }
                    typed = false;
                }*/
            }

            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_SPACE:
                        if(jump) {
                            h.jump();
                            jump = false;
                        }
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
                    case KeyEvent.VK_SPACE:
                        jump = true;
                        break;
                    case KeyEvent.VK_LEFT:
                        h.left = false;
                        break;
                    case KeyEvent.VK_RIGHT:
                        h.right = false;
                        break;
                }

                /*char c = Character.toUpperCase(e.getKeyChar());
                if (c >= 'A' && c <= 'Z') {
                    typed = true;
                }*/
                //char c = Character.toUpperCase((char)e.getKeyCode());
            }
        });

        anchorX = s.getRect().x;
        anchorY = s.getRect().y;

        test = new Collision(0, 1200, 4000, 500);
        e = new Collision(2000, 250, 300, 50);

        n = new ArrayList<>();




        for(int i = 0; i < 8; i++) {
            n.add(new Enemy((int)(Math.random() * background.getWidth()), (int)(Math.random() * background.getHeight()), 50, 50, man, Enemy.GOOMBA));
            loop: while (true) {
                for (Bject b : bjects) {
                    if (n.get(i).contactWith(b)) {
                        n.set(i, new Enemy((int)(Math.random() * background.getWidth()), (int)(Math.random() * background.getHeight()), 50, 50, man, Enemy.GOOMBA));
                        continue loop;
                    }
                }
                break;
            }
        }

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

        for(int j = 0; j < n.size(); j++) {
            n.get(j).setBjects(new ArrayList<>() {
                {
                    try {
                        for(Bject c: bjects) add((Bject)c.clone());
                    } catch (Exception e) {
                        e.printStackTrace();
                        System.exit(0);
                    }
                }
            });
        }

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

        for(int j = 0; j < n.size(); j++) {
            n.get(j).setBjects(new ArrayList<>() {
                {
                    try {
                        for(Bject c: bjects) add((Bject)c.clone());
                    } catch (Exception e) {
                        e.printStackTrace();
                        System.exit(0);
                    }
                }
            });
            System.out.println(j);
        }

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
        for(Enemy en: n) {
            en.update(h, frame);
        }
    }

    private void scroll(double rX, double rY) {
        //System.out.println(rX + " " + rY);

        int bufferX = getWidth()/2;
        int bufferY = getHeight()/5;

        if(rX < bufferX) {
            if(h.left && s.getRect().x > 0) {
                if(h.up ^ h.down) {
                    s.mX((int)(-h.speed * Math.sqrt(2)));
                } else {
                    s.mX(-h.speed);
                }
            }
        }
        if(rX > getWidth() - bufferX) {
            if(h.right && s.rect.x + s.rect.width < background.getWidth()) {
                if(h.up ^ h.down) {
                    s.mX((int)(h.speed * Math.sqrt(2)));
                } else {
                    s.mX(h.speed);
                }
            }
        }

        if(rY > getHeight() - bufferY) {
            if(s.rect.y + s.rect.height < background.getHeight()) {
                double diff = rY - (getHeight() - bufferY);
                s.mY((int)diff);
            }
        }
        if(rY < bufferY) {
            if(s.rect.y > 0) {
                double diff = rY - bufferY;
                s.mY((int)diff);
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
                    g2.drawImage(((Collision) b).getImg(), (int)(b.rect.x - s.rect.x), (int)(b.rect.y - s.rect.y), null);
                } else {
                    g2.fillRect((int)(b.getRect().x - s.getRect().x), (int)(b.getRect().y - s.getRect().y), (int)(b.getRect().width), (int)(b.getRect().height));
                }
            }
        }

        if(s.isInScreen(h)) {
            g2.drawImage(h.getImage(h.index), (int)(h.rect.x - s.rect.x), (int)(h.rect.y - s.rect.y), null);
        }

        for(Enemy en: n) {
            if(s.isInScreen(en)) {
                g2.drawImage(en.getImage(), (int)(en.rect.x - s.rect.x), (int)(en.rect.y - s.rect.y), null);
            }
        }

        //g2.drawImage(man.getImage("Menu"), 5, 100, 145, 175, 0, 0, 100, 60, null);

        g.drawImage(game, 0, 0, null);
    }
}
