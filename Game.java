import java.util.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class Game {
    private Paddle paddle;
    ArrayList<GameComponent> bricks;
    ArrayList<Integer> actions;
    private Ball ball;
    private int score;
    private int lives;
    private NewSoundPlayer player;
    private static final int BRICK_WIDTH = 70;
    private static final int BRICK_HEIGHT = 40;
    private static final int PADDLE_WIDTH = 100;
    private static final int PADDLE_HEIGHT = 20;
    private static final int BALL_WIDTH = 10;
    private static final int BALL_HEIGHT = 10;
    private static final int SPEED_CAP = 25;
    private static final int SPEED_INCREMENT = 5;

    public Game() {
        player = new NewSoundPlayer();
        bricks = new ArrayList<>();
        actions = new ArrayList<>();
        paddle = new Paddle(300, 500, PADDLE_WIDTH, PADDLE_HEIGHT);
        ball = new Ball(300, 450, BALL_WIDTH, BALL_HEIGHT, 10, -10);
        lives = 3;
        score = 0;
        ArrayList<String> fileList = new ArrayList<String>();
        fileList.add("./sounds/" + "d_e1m2 (1).wav");
        player.loadFiles(fileList);
        player.play(0);
        // player.loop();
        // player.makeSound();
        respawnBricks();

    }

    public void drawGame(Graphics g) {
        drawStuff(g);
    }

    public void drawStuff(Graphics g) {
        paddle.draw(g, Color.GREEN);
        ball.draw(g, Color.WHITE);
        for (GameComponent gc : bricks) {
            gc.draw(g);
        }
        g.setColor(Color.WHITE);
        g.drawString("Lives: " + lives, 550, 550);
        g.drawString("Score: " + score, 650, 550);
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
        if(lives==0){
            System.exit(0);
        }
    }

    public void respawnBricks() {
        if (bricks.size() == 0) {
            Color color = null;
            Color[] colors = new Color[] { Color.RED, Color.YELLOW, Color.GREEN, Color.BLUE };
            for (int r = 0; r < 4; r++) {
                for (int c = 0; c < 10; c++) {
                    color = colors[r];
                    bricks.add(new GameComponent(c * (BRICK_WIDTH + 5), r * (BRICK_HEIGHT + 10), BRICK_WIDTH,
                            BRICK_HEIGHT, color));
                }
            }
        }
    }

    public void checkCollisions() {
        if (checkWalls())
            return;
        if (ball.touching(paddle))
            ball.bounced(paddle.getRect());
        if (ball.getRect().y + ball.getRect().height >= Board.HEIGHT) {
            System.out.println("hit the bottom");
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
            Rectangle brickRect = bricks.get(idx).getRect();
            Rectangle ballRect = ball.getRect();
            if (!brickRect.intersects(ballRect)) {
                idx++;
                continue;
            }
            // first one is checking the bottom, the second one is checking the top
            boolean ballIntersectingVert = ballRect.y <= brickRect.y + brickRect.height
                    || ballRect.y + ballRect.height >= brickRect.y;
            ball.changeDir(ballIntersectingVert);
            bricks.remove(idx);
            score += 100;
            if (Math.abs(ball.getDx()) < SPEED_CAP || Math.abs(ball.getDy()) < SPEED_CAP) {
                if (ball.getDx() > 0) {
                    ball.setDx(ball.getDx() + SPEED_INCREMENT);
                    return;
                }
                if (ball.getDx() < 0) {
                    ball.setDx(ball.getDx() - SPEED_INCREMENT);
                    return;
                }
                if (ball.getDy() > 0) {
                    ball.setDy(ball.getDy() + SPEED_INCREMENT);
                    return;
                }
                if (ball.getDy() < 0) {
                    ball.setDy(ball.getDy() - SPEED_INCREMENT);
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
