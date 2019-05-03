package com.company;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;

public class Window extends JFrame implements Runnable {

    double TBU = 1000.0/35;
    int frame = 0;

    BufferedImage background;

    Screen s;

    int anchorX, anchorY;

    Bject test;

    ImageManager man;

    Hero h;

    public Window(String title) throws HeadlessException {
        super(title);

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

        h = new Hero(background.getWidth()/2, background.getHeight()/2, 18, 15, CharID.CharTest, man);

        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_UP:
                        h.move(0, -5 );
                        break;
                    case KeyEvent.VK_DOWN:
                        h.move(0, 5);
                        break;
                    case KeyEvent.VK_LEFT:
                        h.move(-5, 0);
                        break;
                    case KeyEvent.VK_RIGHT:
                        h.move(5, 0);
                        break;
                }
            }
        });

        anchorX = s.getRect().x;
        anchorY = s.getRect().y;

        man = new ImageManager();
        man.loadImages("Images.txt");



        test = new Bject(1000, 500, 500, 500);

        setVisible(true);

        Thread t = new Thread(this);
        t.start();
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
    }

    public void paint(Graphics g) {
        BufferedImage game = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
        int x = (int)((s.getRect().x - anchorX) * 0.6 + anchorX);
        int y = (int)((s.getRect().y - anchorY) * 0.6 + anchorY);

        Graphics g2 = game.getGraphics();
        g2.drawImage(background.getSubimage(x, y, getWidth(), getHeight()), 0, 0, null);

        if(s.isInScreen(test)) {
            g2.fillRect(test.getRect().x - s.getRect().x, test.getRect().y - s.getRect().y, test.getRect().width, test.getRect().height);
        }

        if(s.isInScreen(h)) {
        }
        g.drawImage(game, 0, 0, null);
    }
}
