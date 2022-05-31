import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Ball extends GameComponent {
    private BufferedImage image;

    public Ball(int x, int y, int width, int height, int dx, int dy) {
        super(x, y, width, height, dx, dy);
        try {
            image = ImageIO.read(new File("./images/ball.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void changeDir(boolean isVertical) {
        if (isVertical)
            this.setDy(-this.getDy());
        else
            this.setDx(-this.getDx());
    }

    public void updateBall(Graphics g) {
        g.drawImage(image, getRect().x, getRect().y, getRect().width, getRect().height, null);
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
        this.setDx(Math.round(normalized));
        this.setDy(-this.getDy());
    }
}
