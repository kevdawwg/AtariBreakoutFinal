import java.util.*;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.File;

public class Game {
    public Point panelLocation;
    public Paddle paddle;
    private ArrayList<GameComponent> bricks;
    private ArrayList<Integer> actions;
    private ArrayList<Integer> removed;
    private BufferedImage[] imgs;
    private Ball ball;
    private int score;
    private int lives;
    private SoundPlayer player;
    private BufferedImage background = null;
    private BufferedImage gameOverImg;
    private BufferedImage gameStartImg;
    private int brickRespawns = 0;
    private GameComponent startScreen;
    private boolean gameStart = false;
    public static final int START_WIDTH = 200;
    public static final int START_HEIGHT = 150;
    public static final int BRICK_WIDTH = 70;
    public static final int BRICK_HEIGHT = 40;
    public static final int PADDLE_WIDTH = 100;
    public static final int PADDLE_HEIGHT = 20;
    public static final int BALL_WIDTH = 20;
    public static final int BALL_HEIGHT = 20;
    public static final int BRICK_SPACE = 30;
    public static final int SPEED_CAP = 25;
    public static final int SPEED_INCREMENT = 1;
    public static final Font FONT_LARGE = new Font("Times New Roman", Font.BOLD, 40);

    public Game() {
        bricks = new ArrayList<>();
        removed = new ArrayList<>();
        actions = new ArrayList<>();
        imgs = new BufferedImage[4];
        paddle = new Paddle(300, 500, PADDLE_WIDTH, PADDLE_HEIGHT);
        ball = new Ball(300, 450, BALL_WIDTH, BALL_HEIGHT, 10, -10);
        lives = 1;
        score = 0;
        player = new SoundPlayer();
        player.play(0, 66000000);
        player.loop();
        loadImages();
        respawnBricks();
    }

    public void moveObjects() {
        // paddle.mouseMove(panelLocation);
        ball.move();
        // if (actions.size() > 0) {
        // paddle.buttonMove(actions.remove(0));
        // }
        // ball.move();
    }

    public void update() {
        // System.out.println(actions.size());
        if (actions.size() > 0 && actions.get(0) == 2) {
            gameStart = true;
            actions.remove(0);
            if (lives <= 0) {
                score = 0;
                lives = 3;
                bricks = new ArrayList<>();
                respawnBricks();
                try {
                    Thread.sleep(1000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                ball.setDx(10);
                ball.setDy(10);
            }
        }

        if (lives == 0 && actions.size() > 0 && actions.get(0) == 3) {
            System.out.println(actions.size());
            try {
                actions.remove(0);
                System.out.println("exiting");
                System.out.println("After " + actions.size());
                System.exit(0);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (gameStart) {
            respawnBricks();
            moveObjects();
            checkCollisions();
        }
    }

    public void loadImages() {
        try {
            gameOverImg = ImageIO.read(new File("./images/gameOver.png"));
            gameStartImg = ImageIO.read(new File("./images/retroBackground.jpeg"));
            background = ImageIO.read(new File("./images/space-background.png"));
            imgs[0] = ImageIO.read(new File("./images/purple_brick.png"));
            imgs[1] = ImageIO.read(new File("./images/yellow_brick.png"));
            imgs[2] = ImageIO.read(new File("./images/green_brick.png"));
            imgs[3] = ImageIO.read(new File("./images/red_brick.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void drawBackground(Graphics g) {
        if (lives == 0) {
            g.drawImage(gameOverImg, 0, 0, 750, 600, null);
            g.setColor(Color.RED);
            g.setFont(FONT_LARGE);
            g.drawString("Escape - exit", 450, 450);
            g.setColor(Color.GREEN);
            g.setFont(FONT_LARGE);
            g.drawString("Space - play again", 50, 450);
            return;
        }
        if (!gameStart)
            g.drawImage(gameStartImg, 0, 0, 750, 600, null);
        else
            g.drawImage(background, 0, 0, 750, 600, null);
    }

    public void drawStuff(Graphics g) {
        drawBackground(g);
        if (lives <= 0) {
            gameStart = false;
            ball.setDx(0);
            ball.setDy(0);
            return;
        }
        if (!gameStart) {
            g.setColor(Color.PINK);
            g.setFont(FONT_LARGE);
            g.drawString("Press space", 270, 450);
        }
        if (gameStart) {
            paddle.updatePaddle(g);
            ball.updateBall(g);
            for (int i = 0; i < bricks.size(); i++) {
                bricks.get(i).draw(g);
                int height = bricks.get(i).getRect().y;
                int index = (height - 30) / (BRICK_HEIGHT + BRICK_SPACE);
                // int index = height / ((BRICK_HEIGHT + BRICK_SPACE) + 30);
                Image img = imgs[index];
                g.drawImage(img, bricks.get(i).getRect().x, bricks.get(i).getRect().y, BRICK_WIDTH, BRICK_HEIGHT, null);
            }
            g.setColor(Color.WHITE);
            g.drawString("Lives: " + lives, 550, 550);
            g.drawString("Score: " + score, 650, 550);
        }
    }

    public void respawnBricks() {
        if (bricks.size() == 0) {
            brickRespawns++;
            int randX = ((int) (Math.random() * Board.WIDTH - 99)) + 100;
            double rand = Math.random();
            int xv = (rand > 0.5) ? -10 : 10;
            ball = new Ball(randX, 400, BALL_WIDTH, BALL_HEIGHT, xv, -10);
            try {
                Thread.sleep(2000);
                player.play(3, 0);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (brickRespawns > 0)
                player.play(3, 0);
            Color[] colors = new Color[] { Color.RED, Color.YELLOW, Color.GREEN, Color.CYAN };
            for (int r = 0; r < 4; r++) {
                Color color = colors[r];
                for (int c = 0; c < 10; c++) {
                    bricks.add(new GameComponent(c * (BRICK_WIDTH + 5), r * (BRICK_HEIGHT + BRICK_SPACE) + 30,
                            BRICK_WIDTH, BRICK_HEIGHT, color));
                }
            }
        }
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
        if (ball.getRect().y + ball.getRect().height >= 520) {
            System.out.println("hit the bottom");
            player.play(1, 0);
            try {
                lives--;
                Thread.sleep(3000);
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

            boolean ballIntersectingVert = ballRect.y >= brickRect.y + brickRect.height
                    || ballRect.y + ballRect.height <= brickRect.y;
            ball.changeDir(ballIntersectingVert);
            player.play(2, 0);
            removed.add(idx);
            bricks.remove(idx);
            score += 100;
            if (Math.abs(ball.getDx()) < SPEED_CAP || Math.abs(ball.getDx()) < SPEED_CAP) {
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

    public void rtHit(ActionEvent e) {
        actions.add(1);
    }

    public void spaceHit(ActionEvent e) {
        actions.add(2);
    }

    public void escapeHit(ActionEvent e) {
        actions.add(3);
    }

}
