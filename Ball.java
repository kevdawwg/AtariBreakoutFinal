import java.awt.Rectangle;

public class Ball extends GameComponent {

    public Ball(int x, int y, int width, int height, int dx, int dy) {
        super(x, y, width, height, dx, dy);
    }

    public void changeDir(boolean isVertical) {
        if (isVertical)
            this.setDy(-this.getDy());
        else
            this.setDx(-this.getDx());
    }

    public void move() {
        this.getRect().translate(this.getDx(), this.getDy());
    }

    // method used to calculate exit velocity of ball relative to
    // the distance between the ball and the center of the paddle
    public void bounced(Rectangle paddleRect) {
        float paddleMidpoint = (paddleRect.width / 2) + paddleRect.x;
        float ballMidpoint = (getRect().width / 2) + getRect().x;
        float xMax = paddleRect.width / 2;
        float xMin = -xMax;
        float dist = ballMidpoint - paddleMidpoint;
        int normalizedMax = Game.SPEED_CAP * 2;
        int normalizedMin = -Game.SPEED_CAP;
        float normalized = normalizedMax * ((dist - xMin) / (paddleRect.width)) + normalizedMin;
        setDx(Math.round(normalized));
        setDy(-getDy());
    }
}
