import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Paddle extends GameComponent {
    private BufferedImage image;

    public Paddle(int x, int y, int width, int height) {
        super(x, y, width, height, 0, 0);
        try {
            image = ImageIO.read(new File("./images/paddle.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void mouseMove(Point panelLocation) { 
        long mouseX = Math.round(MouseInfo.getPointerInfo().getLocation().getX());
        int correctedX = (int) Math.round(mouseX - panelLocation.getX());
        if (correctedX + Game.PADDLE_WIDTH >= Board.WIDTH) {
            this.getRect().setLocation(Board.WIDTH - Game.PADDLE_WIDTH, 0);
            return;
        }
        this.getRect().setLocation(correctedX, this.getRect().y);
        // int x = (int) MouseInfo.getPointerInfo().getLocation().getX();
        // if (x + Game.PADDLE_WIDTH > Board.WIDTH) {
        // return;
        // } else {
        // this.getRect().setLocation(x, this.getRect().y);
        // }
    }

    public void updatePaddle(Graphics g) {
        g.drawImage(image, getRect().x, getRect().y, getRect().width, getRect().height, null);
    }

    public void buttonMove(int i) {
        if (this.getRect().x + 100 + 20 > Board.WIDTH || this.getRect().x - 20 < 0) {
            return;
        } else if (i == 0) {
            this.getRect().setLocation(this.getRect().x - 20, this.getRect().y);
        } else if (i == 1) {
            this.getRect().setLocation(this.getRect().x + 20, this.getRect().y);
        }
    }

}
