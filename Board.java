import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class Board extends JFrame{
    private Game game = new Game();
    public static final int WIDTH = 750, HEIGHT = 600, REFRESH = 100;

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
		addKeys(panel);
	}
	/**
	 * Proper way to acquire keystrokes in an application.  
	 * This method sets up the mapping which associates a Keystroke (you
	 * can Google Java KeyStroke API or examples) with an "action command" String 
	 * 
	 * The second part maps the action command String with an Action.
	 * I have shown you two ways this can be done.  You can write the code
	 * you want executed in the actionPerformed method or you can call the
	 * method from within the actionPerformed method call.
	 */
	private void addKeys(JPanel panel) {
		// this connects keystroke with a command
		//panel.getInputMap().put(KeyStroke.getKeyStroke("UP"), "up_key");
		panel.getInputMap().put(KeyStroke.getKeyStroke("RIGHT"), "rt_key");
		panel.getInputMap().put(KeyStroke.getKeyStroke("LEFT"), "lt_key");
		//panel.getInputMap().put(KeyStroke.getKeyStroke("released UP"), "up_key_r");
		panel.getInputMap().put(KeyStroke.getKeyStroke("released RIGHT"), "rt_key_r");
		panel.getInputMap().put(KeyStroke.getKeyStroke("released LEFT"), "lt_key_r");
		
		
		panel.getActionMap().put("lt_key_r", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("released the left arrow");
				
			}
		});
		panel.getActionMap().put("rt_key_r", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("released the right arrow");
			}
		});
		
		panel.getActionMap().put("rt_key",new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("hit the right arrow");
				game.rtHit(e);
			}
		});
		panel.getActionMap().put("lt_key",new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("hit the left arrow");
				game.ltHit(e);
			}
		});
	}
    
}






