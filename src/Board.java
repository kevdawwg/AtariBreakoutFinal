import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class Board extends JFrame{
    private Game game;// = new Game();
    public static final int WIDTH = 750, HEIGHT = 600, REFRESH = 60;

    private JPanel panel = new JPanel() {
		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			panel.getToolkit().sync();
			game.drawStuff(g);
		}
	};
    private Timer timer;

    public Board(String string) {
		super(string);
		game = new Game(this);
		setUpStuff();
	}

    private void setUpStuff() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		panel.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		this.add(panel);
		System.out.println(this);
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
		addKeys(panel);
		this.pack();
		
	}

	private void addKeys(JPanel panel) {
		panel.getInputMap().put(KeyStroke.getKeyStroke("RIGHT"), "rt_key");
		panel.getInputMap().put(KeyStroke.getKeyStroke("LEFT"), "lt_key");
		panel.getInputMap().put(KeyStroke.getKeyStroke("released RIGHT"), "rt_key_r");
		panel.getInputMap().put(KeyStroke.getKeyStroke("released LEFT"), "lt_key_r");
		panel.getInputMap().put(KeyStroke.getKeyStroke("SPACE"), "space");
		
		panel.getActionMap().put("space", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				game.spaceHit(e);
				// System.out.println("space hit");
			}
		});
		panel.getActionMap().put("lt_key_r", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// System.out.println("released the left arrow");
				
			}
		});
		panel.getActionMap().put("rt_key_r", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// System.out.println("released the right arrow");
			}
		});
		
		panel.getActionMap().put("rt_key",new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// System.out.println("hit the right arrow");
				game.rtHit(e);
			}
		});
		panel.getActionMap().put("lt_key",new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// System.out.println("hit the left arrow");
				game.ltHit(e);
			}
		});
	}
    
}






