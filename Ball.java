import java.awt.Rectangle;

public class Ball extends GameComponent {

    public Ball(int x, int y, int width, int height, int dx, int dy) {
        super(x, y, width, height, dx, dy);
    }

    public void changeDir(boolean isVertical) {
        if (isVertical) this.setDy(-this.getDy());
        else this.setDx(-this.getDx());
    }

    public void move() {
        this.getRect().translate(this.getDx(), this.getDy());
    }

    // method used to calculate exit velocity of ball relative to
    // the distance between the ball and the center of the paddle
    public void bounced(Rectangle paddleRect) {
        int paddleMidpoint = paddleRect.x + paddleRect.width / 2;
        int ballMidpoint = getRect().x + getRect().width / 2;
        double distanceFromCenter = Math.abs(paddleMidpoint - ballMidpoint);
        double normalized = ((distanceFromCenter - paddleRect.width / 2) / (-paddleRect.width / 2)) * 20 - 10;
        // makes sure the calculation isn't biased towards one direction
        int sign = getDx() < 0 ? -1 : 1;
        double newDx = normalized * sign;
        setDx((int) newDx);
        setDy(-getDy());
    }
}
