
import java.util.*;
import java.awt.*;
public class Game {
    private Paddle paddle;
    ArrayList<GameComponent> bricks;
    private Ball ball;
    private int brickWidth = 70;
    private int brickHeight = 40;

    public Game() {
        paddle = new Paddle(300, 500, 100, 20);
        ball = new Ball(300, 450, 10, 10, 10, -10);
        bricks = new ArrayList();
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 3; j++) {
                bricks.add(new GameComponent(i * (brickWidth + 10), j * (brickHeight + 10), brickWidth, brickHeight));
            }
        }
        System.out.println(bricks.size());
        // y+i*SegmentSize
    }

    public void drawGame(Graphics g) {
        paddle.draw(g);
        ball.draw(g, Color.WHITE);
        drawBricks(g);
    }

    public void drawBricks(Graphics g) {
        for (GameComponent gc : bricks) {
            gc.draw(g, Color.YELLOW);
        }
    }

    public void update() {
        moveStuff();
        // System.out.println("dx: " + ball.getDx() + "dy: " + ball.getDy());
        checkCollisions();
    }

    public void moveStuff() {
        ball.move();
    }

    public void checkCollisions() { //error here
        int mark;
        for (int i = 0; i < bricks.size(); i++) {
            if (ball.isIntersecting(bricks.get(i))) {
                mark = i;
                break;
            }
        }
        bricks.remove(bricks.get(mark));
    }
}
