import java.awt.*;
import java.awt.event.KeyEvent;

public class Paddle extends GameComponent{

    public Paddle(int x, int y, int width, int height) {
        super(x, y, width, height, 0, 0);
    }

    public int getX(){
        return getX();
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
    // public void move2(){
    
    // }

    // public void keyPressed(KeyEvent e){
    //     int x =  getX();
    //     int key = e.getKeyCode();

    //     if (key == KeyEvent.VK_LEFT) {

    //         dx = -1;
    //         this.getRect().setLocation(x, this.getRect().y);
    //     }

    //     if (key == KeyEvent.VK_RIGHT) {

    //         dx = 1;
    //         this.getRect().setLocation(x, this.getRect().y);
    //     }
    // }

    // public void keyReleased(KeyEvent e) {
    //     int x = getX();
    //     int key = e.getKeyCode();

    //     if (key == KeyEvent.VK_LEFT) {

    //         dx = 0;
    //         this.getRect().setLocation(x, this.getRect().y);
    //     }

    //     if (key == KeyEvent.VK_RIGHT) {

    //         dx = 0;
    //         this.getRect().setLocation(x, this.getRect().y);
    //     }
    // }
    

}
