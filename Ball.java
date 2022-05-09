public class Ball extends GameComponent {

    public Ball(int x, int y, int width, int height, int dx, int dy) { 
        super(x, y, width, height, dx, dy);
    }

    public void changeDir(boolean isVertical){
        if(isVertical) setDy(-getDy());
        else setDx(-getDx());
    }

    public void move() {
        this.getRect().translate(getDx(), getDy());
        // x+=dx;
        // y+=dy;
    }
}
