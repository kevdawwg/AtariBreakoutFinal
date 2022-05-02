
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class Board extends JFrame {
    private Game game = new Game();
    public int WIDTH = 800, HEIGHT = 600, REFRESH = 100;

    private JPanel panel = new JPanel() {
		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			game.drawGame(g);
			panel.getToolkit().sync();
		}
	};
    private Timer timer;

    public Board(String string) {
		super(string);
		setUpStuff();
	}

    private void setUpStuff() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		panel.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		this.add(panel);
		this.pack();
		timer = new Timer(REFRESH, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				game.update();
				panel.repaint();
			}
		});
		timer.start();
		panel.setBackground(Color.BLACK);
		this.setVisible(true);
		panel.requestFocusInWindow();
	}

    
}






