import javax.swing.*;

public class Main {
	    public static void main(String[] args) {
	        SwingUtilities.invokeLater(new Runnable() {
	            @Override
	            public void run() {
	                Board wnd = new Board("Atari");
					// System.out.println(wnd);
	                wnd.setVisible(true);
	            }
	        });
	    }
	}