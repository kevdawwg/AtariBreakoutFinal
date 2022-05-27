import java.awt.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

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

    public void updateBall(Graphics g) {
        Image img = null;
        try{img = ImageIO.read(new File("./images/ball.png"));}
        catch(IOException e){e.printStackTrace();}
        g.drawImage(img, getRect().x-5, getRect().y-5, getRect().width+10, getRect().height+10, null);
    }

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
