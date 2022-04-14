package com.company;
import javax.swing.JPanel;
import java.awt.*;
import java.awt.geom.Rectangle2D;

public class FramePanel extends JPanel implements Runnable{
    final int originalTileSize = 16; //16x16 tile
    final int scale = 2;

    final int tileSize = originalTileSize * scale;
    final int maxScreenCol = 18;
    final int maxScreenRow = 14;
    final int screenWidth = tileSize * maxScreenCol; // 760px
    final int screenHeight = tileSize * maxScreenRow; // 576px

    // play boolean
    private boolean play;
    private int playCounter = 0;
    private int textLocation;
    private boolean collision = false;

    int FPS = 60;

    KeyHandler keyH = new KeyHandler();
    Thread gameThread;

    //set players default position
    int playerX = screenWidth/2 - tileSize;
    int playerY = screenHeight/2 + (3 * tileSize)  ;
    int playerSpeed = 4;

    // Ball Movement Speed
    private int redBallDx = 6;        // increment amount (x axis)
    private int redBallDy = 6;        // increment amount (y axis)
    private int greenBallDx = 3;        // increment amount (x axis)
    private int greenBallDy = 3;        // increment amount (y axis)
    private int blueBallDx = 8;        // increment amount (x axis)
    private int blueBallDy = 8;        // increment amount (y axis)
    private int yellowBallDx = 5;        // increment amount (x axis)
    private int yellowBallDy = 5;        // increment amount (y axis)

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
    private int blueBallY = 200;        // y position
    private int blueBallRadius = 7;    // ball radius

    private int yellowBallX = screenWidth / 3/4;        // x position
    private int yellowBallY = screenHeight - 14;        // y position
    private int yellowBallRadius = 7;    // ball radius

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
    // Key Down action + Player Boundary restriction
        public void update() {
            if(keyH.spacePressed == true){
                play = true;
                playCounter ++;
            }
            if(keyH.upPressed == true && playerY > 0){
                playerY -= playerSpeed;
            }
            else if(keyH.downPressed == true && playerY < screenHeight - 2 * originalTileSize){
                playerY += playerSpeed;
            }
            else if(keyH.leftPressed == true && playerX > 0){
                playerX -= playerSpeed;
            }
            else if(keyH.rightPressed == true && playerX < screenWidth - 2 * originalTileSize){
                playerX += playerSpeed;
            }

        }

        public void paintComponent(Graphics g) {
            super.paintComponent(g);

            // Player
            Graphics2D player = (Graphics2D)g;

            player.setColor(Color.white);

            player.fillRect(playerX,playerY, tileSize, tileSize);

            // start text
            if(play == false && collision == false) {
                g.setColor(Color.green);
                g.setFont(new Font("serif", Font.BOLD, 30));
                g.drawString("Press (SPACE) to Start", textLocation * 3 / 2, textLocation * 5 / 2);
            }
            // text position
                textLocation = screenHeight / 5;
                if(play) {
                    textLocation = 10000;
                } else if(play = false){
                    textLocation = screenHeight / 5;
                }

            if(play) {
               // Red Ball
                Graphics2D redBall = (Graphics2D) g;
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
                if (redBallX + radius == playerX + originalTileSize && redBallY + radius == playerY + originalTileSize)
                    System.out.println("red kills u");

                //

                // Green Ball
                Graphics2D greenBall = (Graphics2D) g;
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
                if (greenBallX + greenBallRadius == playerX + originalTileSize && greenBallY + greenBallRadius == playerY + originalTileSize)
                    System.out.println("green has koed ya");

                // Blue Ball
                Graphics2D blueBall = (Graphics2D) g;
                blueBall.setColor(Color.blue);

                // Adjust ball position
                blueBallX += blueBallDx;
                blueBall.fillOval(blueBallX - blueBallRadius, blueBallY - blueBallRadius, blueBallRadius * 2, blueBallRadius * 2);

                if (blueBallX < blueBallRadius) blueBallDx = Math.abs(blueBallDx);
                if (blueBallX > getWidth() - blueBallRadius) blueBallDx = -Math.abs(blueBallDx);
                if (blueBallY < blueBallRadius) blueBallDy = Math.abs(blueBallDy);
                if (blueBallY > getHeight() - blueBallRadius) blueBallDy = -Math.abs(blueBallDy);
                if(blueBallX + blueBallRadius == playerX + originalTileSize && playerY == blueBallY) System.out.println("blue ball has hit");

                // Yellow Ball
                Graphics2D yellowBall = (Graphics2D) g;
                yellowBall.setColor(Color.yellow);

                // Adjust ball position
                yellowBallY += yellowBallDy;
                yellowBall.fillRect(yellowBallX - yellowBallRadius, yellowBallY - yellowBallRadius, yellowBallRadius * 8, yellowBallRadius * 2);
                yellowBall.fillRect((yellowBallX + 400) - yellowBallRadius, yellowBallY - yellowBallRadius, yellowBallRadius * 8, yellowBallRadius * 2);

                if (yellowBallY < yellowBallRadius) yellowBallDy = Math.abs(yellowBallDy);
                if (yellowBallY > getHeight() - yellowBallRadius) yellowBallDy = -Math.abs(yellowBallDy);
                if (yellowBallX < yellowBallRadius) yellowBallDx = Math.abs(yellowBallDx);
                if (yellowBallX > getWidth() - yellowBallRadius) yellowBallDx = -Math.abs(yellowBallDx);
                if (yellowBallY + yellowBallRadius == playerX + originalTileSize && playerX == yellowBallY) System.out.println("yellow ball has hit");

                player.dispose();
                redBall.dispose();
                greenBall.dispose();
                blueBall.dispose();
                yellowBall.dispose();
            }

            // Square Hit Boxes
            Rectangle playerRect = new Rectangle(playerX,playerY, tileSize, tileSize);
            Rectangle greenBallRect = new Rectangle(greenBallX - greenBallRadius, greenBallY - greenBallRadius, greenBallRadius * 2, greenBallRadius * 2);
            Rectangle redBallRect = new Rectangle(redBallX - radius, redBallY - radius, radius * 2, radius * 2);
            Rectangle blueBallRect = new Rectangle(blueBallX - blueBallRadius, blueBallY - blueBallRadius, blueBallRadius * 2, blueBallRadius * 2);
            Rectangle yellowBallRect = new Rectangle(yellowBallX - yellowBallRadius, yellowBallY - yellowBallRadius, yellowBallRadius * 8, yellowBallRadius * 2);
            Rectangle yellowBallRect2 = new Rectangle(            (yellowBallX + 400) - yellowBallRadius, yellowBallY - yellowBallRadius, yellowBallRadius * 8, yellowBallRadius * 2);

            // Boundary logic
            if(playerRect.intersects(redBallRect) || playerRect.intersects(greenBallRect) || playerRect.intersects(blueBallRect) || playerRect.intersects(yellowBallRect) || playerRect.intersects(yellowBallRect2)) {
                collision = true;
            } else {
                collision = false;
            }
                if(collision) {
                    play = false;

                    // Game Over Text
                    System.out.println("collision");
                    g.setColor(Color.orange);
                    g.setFont(new Font("serif", Font.BOLD, 100));
                    g.drawString("Game Over!", 25, 120);

                    g.setColor(Color.white);
                    g.setFont(new Font("serif",Font.BOLD, 30));
                    g.drawString("Press (SPACE) to Restart", textLocation*3/2 - 10, textLocation* 5/2);

                }

                if(playCounter > 0){
                    // Position Resets
                    // Player Reset
                    playerX = screenWidth/2 - tileSize;
                    playerY = screenHeight/2 + (3 * tileSize);
                    // Red Ball Reset
                    redBallX = 500;
                    redBallY = 0;
                    // Green Ball Reset
                    greenBallX = 0;
                    greenBallY = 250;
                    // Blue Ball Reset
                    blueBallX = 250;
                    blueBallY = 200;
                    // Yellow Ball Reset
                    yellowBallX = screenWidth / 3/4;
                    yellowBallY = screenHeight - yellowBallRadius - yellowBallRadius;
                    // play Counter Reset
                    playCounter = 0;
                }

            player.dispose();

        }
    }



