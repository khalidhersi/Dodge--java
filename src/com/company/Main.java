package com.company;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {
	// write your code here
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("Dodge");

        FramePanel framePanel = new FramePanel();
        window.add(framePanel);

        window.pack();


        window.setLocationRelativeTo(null);
        window.setVisible(true);

        framePanel.startGameThread();















    }
}
