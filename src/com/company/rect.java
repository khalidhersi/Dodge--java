package com.company;

import java.awt.Rectangle;

public class rect {
    int x;
    int y;
    boolean right = false;

    public rect(int startX, int startY){
        x = startX;
        y = startY;
    }

    public void move(){
        if (x == 700)
            right = true;
        if (right)
            x--;
        else
            x++;
    }

    public Rectangle bounds(){
            return new Rectangle(x, y, 100, 50);
    }
}


