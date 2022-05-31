import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;
import javax.swing.*;

public class Board extends JFrame {
    private Game game;
    private Timer timer;
    private JButton startButton = new JButton("START");
    public static final int WIDTH = 750, HEIGHT = 600, REFRESH = 60;

    private JPanel panel = new JPanel() {
        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            panel.getToolkit().sync();
            game.drawStuff(g);
        }
    };

    public Board(String string) {
        super(string);
        game = new Game();
        setUpStuff();
    }

    public void moveMouse() {
        Point panelLocation = panel.getLocation();
        Point mouseLocation = MouseInfo.getPointerInfo().getLocation();
        // panelLocation has to be converted to absolute location rather than location
        // relative to panel
        SwingUtilities.convertPointFromScreen(panelLocation, panel);
        int panelX = (int) Math.round(panelLocation.getX());
        int mouseX = (int) Math.round(mouseLocation.getX());
        // offsets the mouse location with the panel location so the paddle draws in the
        // correct position
        int correctedX = mouseX + panelX;
        Rectangle rect = game.paddle.getRect();
        // if the mouse position is outside of bounds, then move paddle to edge of
        // screen
        if (correctedX + Game.PADDLE_WIDTH >= Board.WIDTH) {
            rect.setLocation(Board.WIDTH - Game.PADDLE_WIDTH, rect.y);
            return;
        }
        if (correctedX == 0) {
            rect.setLocation(0, rect.y);
            return;
        }
        rect.setLocation(correctedX, rect.y);
    }

    private void setUpStuff() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        panel.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.add(panel);
        timer = new Timer(REFRESH, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                moveMouse();
                game.update();
                panel.repaint();
            }
        });
        timer.start();
        panel.setBackground(Color.BLACK);
        this.setVisible(true);
        panel.requestFocusInWindow();
        addKeys(panel);
        this.pack();

    }

    private void addKeys(JPanel panel) {
        panel.getInputMap().put(KeyStroke.getKeyStroke("RIGHT"), "rt_key");
        panel.getInputMap().put(KeyStroke.getKeyStroke("LEFT"), "lt_key");
        panel.getInputMap().put(KeyStroke.getKeyStroke("released RIGHT"), "rt_key_r");
        panel.getInputMap().put(KeyStroke.getKeyStroke("released LEFT"), "lt_key_r");
        panel.getInputMap().put(KeyStroke.getKeyStroke("SPACE"), "space");
        panel.getInputMap().put(KeyStroke.getKeyStroke("ESCAPE"), "esc");

        panel.getActionMap().put("esc", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.escapeHit(e);
                // System.out.println("escape hit");
            }
        });
        panel.getActionMap().put("space", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.spaceHit(e);
                // System.out.println("space hit");
            }
        });
        panel.getActionMap().put("rt_key_r", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // System.out.println("released the right arrow");
            }
        });
        panel.getActionMap().put("rt_key", new AbstractAction() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // System.out.println("hit the right arrow");
                // game.rtHit(e);
            }
        });
        panel.getActionMap().put("lt_key", new AbstractAction() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // System.out.println("hit the left arrow");
                game.ltHit(e);
            }
        });

    }

}
