package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

    public class Paint extends JPanel implements ActionListener {
        rect rect1;
        rect rect2;
        Timer time;
        boolean collision = false;

        public Paint() {
            rect1 = new rect(0, 75);
            rect2 = new rect(700, 75);
            time = new Timer(5, this);
            time.start();
        }

        public void actionPerformed(ActionEvent e) {
            rect1.move();
            rect2.move();
            checkCollision();
            repaint();
        }

        public void paint(Graphics g) {
            super.paint(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setColor(Color.BLACK);
            g2d.fillRect(rect1.x, rect1.y, 100, 50);
            g2d.setColor(Color.RED);
            g2d.fillRect(rect2.x, rect2.y, 100, 50);
            if (collision)
                g2d.drawString("COLLISION", 350, 50);
        }

        public void checkCollision() {
            Rectangle r1 = rect1.bounds();
            Rectangle r2 = rect2.bounds();

            if (r1.intersects(r2))
                collision = true;
            else
                collision = false;
        }
    }

