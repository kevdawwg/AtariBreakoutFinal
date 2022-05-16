import java.awt.*;

public class GameComponent {
    private Rectangle rect;
    private int dx, dy;
    private Color color;

    public GameComponent(int x, int y, int width, int height, int dx, int dy) {
        rect = new Rectangle(x, y, width, height);
        this.dx = dx;
        this.dy = dy;
    }

    public GameComponent(int x, int y, int width, int height, Color color) {
        this(x, y, width, height, 0, 0);
        this.color = color;
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

    public void draw(Graphics g) {
        Graphics g2 = (Graphics2D) g;
        g2.setColor(color);
        ((Graphics2D) g2).fill(rect);
    }

    public void draw(Graphics g, Color c) {
        g.setColor(c);
        draw(g);
    }

    public void move() {
        this.rect.translate(dx, dy);
    }

    public boolean touching(GameComponent object) {
        Rectangle objRect = object.rect;
        boolean vert = rect.y <= objRect.y + objRect.height && rect.y + rect.height >= objRect.y;
        boolean horiz = rect.x <= objRect.x + objRect.width && rect.x + rect.width >= objRect.x;
        return vert && horiz;
    }
}
