import java.awt.*;

public class Paddle {
    private Rectangle rect;

    public Paddle(int x, int y, int width, int height) {
        rect = new Rectangle(x, y, width, height);
    }

    public void draw(Graphics g) {
        Graphics g2 = (Graphics2D) g;
        g2.setColor(Color.WHITE);
        ((Graphics2D) g2).fill(rect);
    }

    public boolean paddleCollide(GameComponent o) {
        return this.rect.intersects(o.getRect());
    }
}
