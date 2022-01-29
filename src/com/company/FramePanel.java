package com.company;
import javax.swing.JPanel;
import java.awt.*;

public class FramePanel extends JPanel implements Runnable{
    final int originalTileSize = 16; //16x16 tile
    final int scale = 2;

    final int tileSize = originalTileSize * scale;
    final int maxScreenCol = 36;
    final int maxScreenRow = 24;
    final int screenWidth = tileSize * maxScreenCol; // 760px
    final int screenHeight = tileSize * maxScreenRow; // 576px

    int FPS = 60;

    KeyHandler keyH = new KeyHandler();
    Thread gameThread;

    //set players defualt position
    int playerX = 100;
    int playerY = 100;
    int playerSpeed = 4;

    public FramePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }

    public  void startGameThread(){
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
//
//        while (gameThread != null) {
//
//            double drawInterval = 1000000000/FPS; //0.0166666 seconds
//            double nextDrawTime = System.nanoTime() + drawInterval;
//
//            update();
//            repaint();
//
//            try {
//                double remainingTime = nextDrawTime - System.nanoTime();
//                remainingTime = remainingTime/1000000;
//                if(remainingTime < 0){
//                    remainingTime = 0;
//                }
//                Thread.sleep((long) remainingTime);
//                nextDrawTime += drawInterval;
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }

        // delta/accumulator loop (a different type of loop)
        double drawInterval = 1000000000/FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0;

        while (gameThread != null) {
            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;

            if(delta >= 1){
                update();
                repaint();
                delta--;
            }

            if(timer >= 1000000000) {
                System.out.println("FPS" + drawCount);
                drawCount = 0;
                timer = 0;
            }


        }



    }
        public void update() {
            if(keyH.upPressed == true){
                playerY -= playerSpeed;
            }
            else if(keyH.downPressed == true){
                playerY += playerSpeed;
            }
            else if(keyH.leftPressed == true){
                playerX -= playerSpeed;
            }
            else if(keyH.rightPressed == true){
                playerX += playerSpeed;
            }
        }

        public void paintComponent(Graphics g) {
            super.paintComponent(g);

            Graphics2D g2 = (Graphics2D)g;

            g2.setColor(Color.white);

            g2.fillRect(playerX,playerY, tileSize, tileSize);

            g2.dispose();


        }



    }



