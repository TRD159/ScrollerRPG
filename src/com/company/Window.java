package com.company;

import javax.swing.*;
import java.awt.*;

public class Window extends JFrame implements Runnable {

    double TBU = 1000.0/35;
    int frame = 0;

    public Window(String title) throws HeadlessException {
        super(title);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setSize(400, 400);

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

    }
}
