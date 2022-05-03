public class Ball extends GameComponent {
    private int dx;
    private int dy;

    public Ball(int x, int y, int width, int height, int dx, int dy) { 
        super(x, y, width, height, dx, dy);
        this.dx = dx;
        this.dy = dy;
    }

    public void changeDir(boolean isBrick){
        //if(isBrick){
            if(dx == 10 && dy == 10){ dy = -10; }
            else if(dx == 10 && dy == -10){ dx = -10; }
            else if(dx == -10 && dy == -10){ dy = 10; }
            else if(dx == -10 && dy == 10){ dx = 10; }
        //}
    }
    public void move() {
        this.getRect().translate(dx, dy);
    }
}
