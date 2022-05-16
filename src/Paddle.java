import java.awt.*;
import java.awt.event.KeyEvent;

public class Paddle extends GameComponent{

    public Paddle(int x, int y, int width, int height) {
        super(x, y, width, height, 0, 0);
    }

    public int getX(){
        return getX();
    }

    public void mouseMove() {
        int x = (int)MouseInfo.getPointerInfo().getLocation().getX();
        if (x+Game.PADDLE_WIDTH> Board.WIDTH) {
            return;
        }
        else {
            this.getRect().setLocation(x, this.getRect().y);
        }
    }

    public void buttonMove(int i) {
        if (this.getRect().x+100 + 20 > Board.WIDTH || this.getRect().x - 20 < 0) {
            return;
        }
        else if (i == 0) {
            this.getRect().setLocation(this.getRect().x-20, this.getRect().y);
        }
        else if (i == 1) {
            this.getRect().setLocation(this.getRect().x+20, this.getRect().y);
        }
    }


    public void keyPressed(KeyEvent e){
        int x =  getX();
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT) {

            
            this.getRect().setLocation(x, this.getRect().y);
        }

        if (key == KeyEvent.VK_RIGHT) {

            
            this.getRect().setLocation(x, this.getRect().y);
        }
    }

    public void keyReleased(KeyEvent e) {
        int x = getX();
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT) {

            
            this.getRect().setLocation(x, this.getRect().y);
        }

        if (key == KeyEvent.VK_RIGHT) {

            
            this.getRect().setLocation(x, this.getRect().y);
        }
    }
    

}
