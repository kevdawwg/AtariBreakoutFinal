import java.util.*;
import java.awt.*;
public class Game {
    private Paddle paddle;
    ArrayList<GameComponent> bricks;
    private Ball ball;
    private int score;
    private int lives;
    private static final int BRICK_WIDTH = 70;
    private static final int BRICK_HEIGHT = 40;
    private static final int PADDLE_WIDTH = 100;
    private static final int PADDLE_HEIGHT = 20;
    private static final int BALL_WIDTH = 10;
    private static final int BALL_HEIGHT = 10;
    
    

    public Game() {
        bricks = new ArrayList();
        paddle = new Paddle(300, 500, PADDLE_WIDTH, PADDLE_HEIGHT);
        ball = new Ball(300, 450, BALL_WIDTH, BALL_HEIGHT, 10, -10);
        lives = 3;
        score = 0;
        Color color = null;
        Color[] colors = new Color[] {Color.RED, Color.YELLOW, Color.GREEN, Color.BLUE};
        for (int r = 0; r < 4; r++) {
            for (int c = 0; c < 10; c++) {
                color = colors[r];
                bricks.add(new GameComponent(c * (BRICK_WIDTH + 10), r * (BRICK_HEIGHT + 10), BRICK_WIDTH, BRICK_HEIGHT, color));
            }
        }
        System.out.println(bricks.size());
        // y+i*SegmentSize
    }

    public void drawGame(Graphics g) {
        drawStuff(g);
    }
    
    public void drawStuff (Graphics g) {
        paddle.draw(g, Color.GREEN);
        ball.draw(g, Color.WHITE);
        for (GameComponent gc : bricks) {
            gc.draw(g);
        }
        g.setColor(Color.WHITE);
        g.drawString("Score: " + score, 700, 550);
        
        
    }

    public void update() {
        paddle.move();
        ball.move();
        // System.out.println("dx: " + ball.getDx() + "dy: " + ball.getDy());
        checkCollisions();
    }
    
    public void checkCollisions() { //error here
        if(checkWalls()) return;
        if (ball.isIntersecting(paddle)) {
            ball.changeDir(true);
        }
        if (ball.getRect().y+ball.getRect().height >= Board.HEIGHT) {
            System.out.println("hit the bottom");
            try {
                lives--;
                Thread.sleep(3000);
                int randX = ((int) (Math.random() * Board.WIDTH-99)) + 100;
                double rand = Math.random();
                int xv = (rand > 0.5) ? -10 : 10;
                ball = new Ball(randX, 300, BALL_WIDTH, BALL_HEIGHT, xv, 10);
            }
            catch(Exception e) {
                e.printStackTrace();
            }
            System.out.println("lives " + lives);
        } 
        int idx = 0;
        while(idx < bricks.size()) {
            Rectangle brickRect = bricks.get(idx).getRect();
            Rectangle ballRect = ball.getRect();
            if(!brickRect.intersects(ballRect)) {
                idx++;
                continue;
            }
            boolean ballIntersectingVert = ballRect.y <= brickRect.y+brickRect.height || ballRect.y+ballRect.height >= brickRect.y;
            ball.changeDir(ballIntersectingVert);
            bricks.remove(idx);
            score += 100;
            // ball.setDx(ball.getDx()*10);
            // ball.setDy(ball.getDy()*10);
            // System.out.println(ball.getDx() + " " + ball.getDy());
        }
    }

    public boolean checkWalls(){
        if(ball.getRect().x + BALL_WIDTH >= Board.WIDTH || ball.getRect().x <= 0) {
            ball.changeDir(false);
            return true;
        }
        else if (ball.getRect().y + BALL_HEIGHT <= 0 || ball.getRect().y >= Board.HEIGHT){
            ball.changeDir(true);
            return true;
        } 
        return false;
    }

}
