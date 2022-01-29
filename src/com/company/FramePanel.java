package com.company;
import javax.swing.JPanel;
import java.awt.*;

public class FramePanel extends JPanel implements Runnable{
    final int originalTileSize = 16; //16x16 tile
    final int scale = 2;

    final int tileSize = originalTileSize * scale;
    final int maxScreenCol = 18;
    final int maxScreenRow = 14;
    final int screenWidth = tileSize * maxScreenCol; // 760px
    final int screenHeight = tileSize * maxScreenRow; // 576px

    int FPS = 60;

    KeyHandler keyH = new KeyHandler();
    Thread gameThread;

    //set players default position
    int playerX = 100;
    int playerY = 100;
    int playerSpeed = 4;

    // Ball Movement Speed
    private int redBallDx = 6;        // increment amount (x axis)
    private int redBallDy = 6;        // increment amount (y axis)
    private int greenBallDx = 3;        // increment amount (x axis)
    private int greenBallDy = 3;        // increment amount (y axis)
    private int blueBallDx = 8;        // increment amount (x axis)
    private int blueBallDy = 200;        // increment amount (y axis)

    // Set ball default position

    // Red Ball
    private int redBallX = 500;        // x position
    private int redBallY = 0;        // y position
    private int radius = 15;    // ball radius

    // Green Ball
    private int greenBallX = 0;        // x position
    private int greenBallY = 250;        // y position
    private int greenBallRadius = 30;    // ball radius

    // Blue Ball
    private int blueBallX = 250;        // x position
    private int blueBallY = 250;        // y position
    private int blueBallRadius = 7;    // ball radius


    public FramePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }

    public void startGameThread(){
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {

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

            // Player
            Graphics2D player = (Graphics2D)g;

            player.setColor(Color.white);

            player.fillRect(playerX,playerY, tileSize, tileSize);

            //

            // Red Ball
            Graphics2D redBall = (Graphics2D)g;
            redBall.setColor(Color.red);

            // Adjust ball position
            redBallX += redBallDx;
            redBallY += redBallDy;
            redBall.fillOval(redBallX - radius, redBallY - radius, radius * 2, radius * 2);


            // Boundary logic
            if (redBallX < radius) redBallDx = Math.abs(redBallDx);
            if (redBallX > getWidth() - radius) redBallDx = -Math.abs(redBallDx);
            if (redBallY < radius) redBallDy = Math.abs(redBallDy);
            if (redBallY > getHeight() - radius) redBallDy = -Math.abs(redBallDy);

            //

            // Green Ball
            Graphics2D greenBall = (Graphics2D)g;
            greenBall.setColor(Color.green);

            // Adjust ball position
            greenBallX += greenBallDx;
            greenBallY += greenBallDy;
            greenBall.fillOval(greenBallX - greenBallRadius, greenBallY - greenBallRadius, greenBallRadius * 2, greenBallRadius * 2);


            // Boundary logic
            if (greenBallX < greenBallRadius) greenBallDx = Math.abs(greenBallDx);
            if (greenBallX > getWidth() - greenBallRadius) greenBallDx = -Math.abs(greenBallDx);
            if (greenBallY < greenBallRadius) greenBallDy = Math.abs(greenBallDy);
            if (greenBallY > getHeight() - greenBallRadius) greenBallDy = -Math.abs(greenBallDy);

            // Blue Ball
            Graphics2D blueBall = (Graphics2D)g;
            greenBall.setColor(Color.blue);

            // Adjust ball position
            blueBallX += blueBallDx;
            blueBallY = blueBallDy;
            greenBall.fillOval(blueBallX - blueBallRadius, blueBallY - blueBallRadius, blueBallRadius * 2, blueBallRadius * 2);


            // Boundary logic
            if (blueBallX < blueBallRadius) blueBallDx = Math.abs(blueBallDx);
            if (blueBallX > getWidth() - blueBallRadius) blueBallDx = -Math.abs(blueBallDx);
            if (blueBallY < blueBallRadius) blueBallDy = Math.abs(blueBallDy);
            if (blueBallY > getHeight() - blueBallRadius) blueBallDy = -Math.abs(blueBallDy);


            player.dispose();
            redBall.dispose();
            greenBall.dispose();
            blueBall.dispose();


        }



    }



