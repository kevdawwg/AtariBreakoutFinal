import java.awt.*;

public class Paddle extends GameComponent{
    private Color c;

    public Paddle(int x, int y, int width, int height) {
        super(x, y, width, height, 0, 0);
    }

    public void move() {
        int x = (int)MouseInfo.getPointerInfo().getLocation().getX();
        if (x+100> Board.WIDTH) {
            return;
        }
        else {
            this.getRect().setLocation(x, this.getRect().y);
        }

    }

}
