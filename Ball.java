public class Ball extends GameComponent {
    private int x, y, dx, dy;

    public Ball(int x, int y, int width, int height, int dx, int dy) { 
        super(x, y, width, height, dx, dy);
        this.x = x;
        this.y = y;
        this.dx = dx;
        this.dy = dy;
    }

    public int getX(){ 
        return x; 
    }

    public int getY(){ 
        return y; 
    }

    public void changeDir(boolean isVertical){
        if(isVertical) setDy(-getDy());
        else setDx(-getDx());
    }

    public void move() {
        this.getRect().translate(dx, dy);
        x+=dx;
        y+=dy;
    }
}
