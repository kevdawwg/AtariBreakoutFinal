
import java.awt.*;

public class GameComponent {
    private Rectangle rect;
    private int dx, dy;

    public GameComponent(int x, int y, int width, int height, int dx, int dy) { 
        rect = new Rectangle(x, y, width, height);
        this.dx = dx;
        this.dy = dy;
    }    

    public GameComponent(int x, int y, int width, int height) {
        this(x, y, width, height, 0, 0);
    }

    public void setDx(int dx) {
        this.dx = dx;
    }

    public void setDy(int dy) {
        this.dy = dy;
    }

    public int getDx() {
        return this.dx;
    }

    public int getDy() {
        return this.dy;
    }
    public Rectangle getRect() {
        return this.rect;
    }

    public void draw(Graphics g, Color c) {
        Graphics g2 = (Graphics2D) g;
        g2.setColor(c);
        ((Graphics2D) g2).fill(rect);
    }

    public void move() {
        this.rect.translate(dx, dy);
    }

    public boolean isIntersecting(GameComponent object) {
        return this.rect.x == object.rect.x || this.rect.y == object.rect.y;
    }
}