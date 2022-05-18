import java.util.*;
import javax.imageio.ImageIO;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
// import java.awt.image.BufferedImage;
import java.io.File;

public class Game {
    private Paddle paddle;
    private ArrayList<GameComponent> bricks;
    private ArrayList<Integer> actions;
    private ArrayList<Integer> removed;
    private Image[] imgs;
    private Ball ball;
    private int score;
    private int lives;
    // private NewSoundPlayer player;
    private SoundPlayer player;
    private Image gameOver;
    private BufferedImage gameOverImg;
    public static final int BRICK_WIDTH = 70;
    public static final int BRICK_HEIGHT = 40;
    public static final int PADDLE_WIDTH = 100;
    public static final int PADDLE_HEIGHT = 20;
    public static final int BALL_WIDTH = 10;
    public static final int BALL_HEIGHT = 10;
    public static final int SPEED_CAP = 25;
    public static final int SPEED_INCREMENT = 1;
    public static final Font FONT_LARGE = new Font("Times New Roman", Font.BOLD, 40);
    private Board board;

    public Game(Board board) {
        this.board = board;
        // player = new NewSoundPlayer();
        bricks = new ArrayList<>();
        removed = new ArrayList<>();
        actions = new ArrayList<>();
        imgs = new Image[4];
        try{imgs[0] = ImageIO.read(new File("./images/purple_brick.png"));}
        catch(IOException e){e.printStackTrace();}
        try{imgs[1] = ImageIO.read(new File("./images/yellow_brick.png"));}
        catch(IOException e){e.printStackTrace();}
        try{imgs[2] = ImageIO.read(new File("./images/green_brick.png"));}
        catch(IOException e){e.printStackTrace();}
        try{imgs[3] = ImageIO.read(new File("./images/red_brick.png"));}
        catch(IOException e){e.printStackTrace();}
        paddle = new Paddle(300, 500, PADDLE_WIDTH, PADDLE_HEIGHT);
        ball = new Ball(300, 450, BALL_WIDTH, BALL_HEIGHT, 10, -10);
        lives = 2;
        score = 0;
        ArrayList<String> fileList = new ArrayList<String>();
        try{gameOverImg = ImageIO.read(new File("./images/gameOver.png"));}
        catch(IOException e){e.printStackTrace();}
        // fileList.add("./sounds/" + "d_e1m2 (1).wav");
        // player.loadFiles(fileList);
        // player.play(0);
        // player.loop();
        // player.makeSound();==
        player = new SoundPlayer();

        //player.play(0, 66000000);
        player.play(1, 0);
        //player.loop();

        // loadImages();
        respawnBricks();


    }

    public void loadImages(Graphics g) {
        g.drawString("GAME OVER", 2, 3);
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        Image gameOver = null;
        try {    
            gameOver =  ImageIO.read(new File("./images/GameOver.jpeg"));
            g.drawImage(gameOver, 0, 0, 750, 600, null);
            
        }
        
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void drawGame(Graphics g) {
        drawStuff(g);
    }

    public void drawBackground(Graphics g) {
        Image img = null;
        try{img = ImageIO.read(new File("./images/space-background.png"));}
        catch(IOException e){e.printStackTrace();}
        g.drawImage(img, 0, 0, 750, 600, null);
        if(lives==0){
           
            g.drawImage(gameOverImg, 0, 0, 750, 600, null);

        }
    }


    private void gameEnd(Graphics g){
            g.setColor(Color.RED);
            g.setFont(FONT_LARGE);
            g.drawString("Game Over",300, 320);
    }

    public void drawStuff(Graphics g) {
        drawBackground(g);
        if(lives == 0){
            return;
        }
        paddle.draw(g, Color.GREEN);
        paddle.updatePaddle(g);
        ball.draw(g, Color.WHITE);
        ball.updateBall(g);
        for(int i = 0; i < bricks.size(); i++){
            bricks.get(i).draw(g);
            int height = bricks.get(i).getRect().y;
            Image img = null;
            if(height == 0 * (BRICK_HEIGHT + 30)){ img = imgs[0]; }
            else if(height == 1 * (BRICK_HEIGHT + 30)){ img = imgs[1]; }
            else if(height == 2 * (BRICK_HEIGHT + 30)){ img = imgs[2]; }
            else{ img = imgs[3]; }
            g.drawImage(img,bricks.get(i).getRect().x, bricks.get(i).getRect().y, BRICK_WIDTH, BRICK_HEIGHT,null);
        }
        g.setColor(Color.WHITE);
        g.drawString("Lives: " + lives, 550, 550);
        g.drawString("Score: " + score, 650, 550);
        if (lives == 0) {
            Image img = gameOverImg;
            g.drawImage(img, 0, 0, 750, 600, null);
            try {
                g.drawString("Game Over",500, 520);
                System.out.print("Check point!");
                Thread.sleep(2000);
                System.exit(0);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void moveObjects() {
        paddle.mouseMove();
        ball.move();
        // if (actions.size() > 0) {
        // paddle.buttonMove(actions.remove(0));
        // }
        // ball.move();
    }

    public void update() {
        moveObjects();
        checkCollisions();
        respawnBricks();
    }

    public void respawnBricks() {
        if (bricks.size() == 0) {
            Color color = null;
            Color[] colors = new Color[] { Color.RED, Color.YELLOW, Color.GREEN, Color.CYAN };
            for (int r = 0; r < 4; r++) {
                for (int c = 0; c < 10; c++) {
                    color = colors[r];
                     bricks.add(new GameComponent(c * (BRICK_WIDTH + 5), r * (BRICK_HEIGHT + 30), BRICK_WIDTH, BRICK_HEIGHT, color));
                }
             }
            //for (int i = 0; i < 5; i++) {
            //    bricks.add(new GameComponent(i * (BRICK_WIDTH + 5) + 300, 40, BRICK_WIDTH, BRICK_HEIGHT, Color.PINK));
            //}
        }
        System.out.println(bricks.size());
        // bricks.add(new GameComponent(200, 50, BRICK_WIDTH, BRICK_HEIGHT, Color.PINK));
    }

    public void checkCollisions() {
        if (checkWalls()) {
            player.play(2, 0);
            return;
        }
        if (ball.touching(paddle)) {
            ball.bounced(paddle.getRect());
            player.play(2, 0);
            return;
        }
        if (ball.getRect().y + ball.getRect().height >= 505) {
            System.out.println("hit the bottom");
            
            player.play(1, 0);
            try {
                lives--;
                board.repaint();
               // Thread.sleep(3000);
                int randX = ((int) (Math.random() * Board.WIDTH - 99)) + 100;
                double rand = Math.random();
                int xv = (rand > 0.5) ? -10 : 10;
                ball = new Ball(randX, 300, BALL_WIDTH, BALL_HEIGHT, xv, 10);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        int idx = 0;
        while (idx < bricks.size()) {
            GameComponent brick = bricks.get(idx);
            Rectangle brickRect = brick.getRect();
            Rectangle ballRect = ball.getRect();
            if (!brick.touching(ball)) {
                idx++;
                continue;
            }
            // first one is checking the bottom, the second one is checking the top
            boolean ballIntersectingVert = ballRect.y == brickRect.y + brickRect.height
                    || ballRect.y + ballRect.height == brickRect.y;
            // boolean ballIntersectingHoriz = ballRect.x <= brickRect.x + Game.BRICK_WIDTH || ballRect.x + ballRect.width >= brickRect.x;
            // ball.changeDX(ballIntersectingHoriz);
            // ball.changeDY(ballIntersectingVert);
            ball.changeDir(ballIntersectingVert);
            System.out.println(ballIntersectingVert);
            player.play(2, 0);
            System.out.println("hit the brick");
            removed.add(idx);
            bricks.remove(idx);
            score += 100;
            if (Math.abs(ball.getDx()) < SPEED_CAP || Math.abs(ball.getDy()) < SPEED_CAP) {
                if (ball.getDx() > 0 || ball.getDy() > 0) {
                    ball.setDx(ball.getDx() + SPEED_INCREMENT);
                    return;
                }
                if (ball.getDx() < 0 || ball.getDy() < 0) {
                    ball.setDx(ball.getDx() - SPEED_INCREMENT);
                    return;
                }
            }
        }
    }

    public boolean checkWalls() {
        if (ball.getRect().x + BALL_WIDTH >= Board.WIDTH || ball.getRect().x <= 0) {
            ball.changeDir(false);
            return true;
        } else if (ball.getRect().y + BALL_HEIGHT <= 0 || ball.getRect().y >= Board.HEIGHT) {
            ball.changeDir(true);
            return true;
        }
        return false;
    }

    public void ltHit(ActionEvent e) {
        actions.add(0);
    }

    public void leftReleased(ActionEvent e) {
        System.out.println("Released Left!!");
    }

    public void rtHit(ActionEvent e) {
        actions.add(1);
    }

    public void rightReleased(ActionEvent e) {
        System.out.println("Released Right!!");
    }

}
