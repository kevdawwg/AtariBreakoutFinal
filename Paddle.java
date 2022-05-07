import java.awt.*;


public class Paddle extends GameComponent{
    private Color c;

    public Paddle(int x, int y, int width, int height) {
        super(x, y, width, height, 0, 0);
    }


    public boolean paddleCollide(GameComponent o) {
        return this.getRect().intersects(o.getRect());
    }

    public void move() {
        // this.rect.setLocation(x, this.rect.y);
        this.getRect().setLocation((int)MouseInfo.getPointerInfo().getLocation().getX(), this.getRect().y);
    }

}
