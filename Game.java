import java.util.*;
import java.awt.*;
public class Game {
    private Paddle paddle;
    ArrayList<GameComponent> bricks;
    ArrayList<Integer> xcoords;
    ArrayList<Ball> balls;
    private Ball ball;
    private static final int brickWidth = 70;
    private static final int brickHeight = 40;
    private int score = 0;
    private int lives;
//testing Karan's git, small comment, ignore this
    public Game() {
        paddle = new Paddle(300, 500, 100, 20);
        ball = new Ball(300, 450, 10, 10, 10, -10);
        bricks = new ArrayList<>();
        Color color = null;
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 4; j++) {
                switch(j) {
                    case 0:
                        color = Color.RED;
                        break;
                    case 1:
                        color = Color.YELLOW;
                        break;
                    case 2:
                        color= Color.GREEN;
                        break;
                    default:
                        color = Color.BLUE;
                }
                bricks.add(new GameComponent(i * (brickWidth + 10), j * (brickHeight + 10), brickWidth, brickHeight, color));
            }
        }
        System.out.println(bricks.size());
        // y+i*SegmentSize
    }

    public void drawGame(Graphics g) {
        paddle.draw(g, Color.GREEN);
        drawStuff(g);
    }

    public void drawStuff (Graphics g) {
        for (GameComponent gc : bricks) {
            gc.draw(g);
        }
        
        ball.draw(g, Color.WHITE);
    }

    public void update() {
        paddle.move();
        moveStuff();
        // System.out.println("dx: " + ball.getDx() + "dy: " + ball.getDy());
        checkCollisions();
    }

    public void moveStuff() {
        ball.move();
    }
    
    public void checkCollisions() { //error here
        if(checkWalls()) return;
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
        }
        if (ball.getRect().y > Board.HEIGHT) {
            balls.remove(ball);
            lives--;
            System.out.println("lives " + lives);
            try {
                Thread.sleep(3000);
                int randX = ((int) (Math.random() * 500)) + 100;
                ball = new Ball(randX, 300, 10, 10, 10, -10);
                balls.add(ball);
            }
            catch(Exception e) {
                e.printStackTrace();
            }
        } 
    }

    public boolean checkWalls(){
        if(ball.getX() >= 800 || ball.getX() <= 0) {
            ball.changeDir(false);
            return true;
        }
        else if (ball.getY() <= 0 || ball.getY() >= 600){
            ball.changeDir(true);
            return true;
        } 
        return false;
    }

    public void addCoord(int x) {
        xcoords.add(x);
    }
}
