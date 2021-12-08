import javax.swing.JFrame;

public class Pong extends JFrame {
	
	// Constant variables
	private final static String WINDOW_TITLE = "Pong";
    private final static int WINDOW_WIDTH = 800;
    private final static int WINDOW_HEIGHT = 600;

	public static void main(String[] args) {
		
		// Pong Project
		
		javax.swing.SwingUtilities.invokeLater(new Runnable() { 
				// delay the GUI creation task until the initial thread's tasks have been completed
	           public void run() {
	                  new Pong();
	           }
	         });
	}

	public Pong() { // Creates Frame Constructor
		setTitle(WINDOW_TITLE); // Title bar text
		setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		setResizable(false); // Allows minimise & maximise window
		add(new PongPanel());
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE); // Can use HIDE_ON_CLOSE
	}
}
