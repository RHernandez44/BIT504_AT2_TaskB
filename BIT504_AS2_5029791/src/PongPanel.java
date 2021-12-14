import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.Stroke;
import java.awt.Graphics2D;
import java.awt.BasicStroke;
import javax.swing.JPanel;
import javax.swing.Timer;

public class PongPanel extends JPanel implements ActionListener, KeyListener {
	
	// Constants
	private final static Color BACKGROUND_COLOUR = Color.BLACK;
	private final static int TIMER_DELAY = 5;
	// Ball object
	Ball ball;
	Paddle paddle1;
	Paddle paddle2;
	private final static int BALL_MOVEMENT_SPEED = 2;
	
	// Constructor
	public PongPanel() {
		
		setBackground(BACKGROUND_COLOUR);
		Timer timer = new Timer(TIMER_DELAY, this);
        timer.start();	// Creates timer loop to call actionPerformed()
        addKeyListener(this);
        setFocusable(true);		
	}
	
	// Ball object velocities
	public void moveObject(Sprite obj) {
		
		// Increases x/y pos according to its velocity
		obj.setxPosition(obj.getxPosition() + obj.getxVelocity(), getWidth());
		obj.setyPosition(obj.getyPosition() + obj.getyVelocity(), getHeight());		
	}
	
	public void checkWallBounce() {

		if ( ball.getxPosition() <= 0) {
			// Hit LH side screen
			ball.setxVelocity(-ball.getxVelocity());
			resetBall();
		} else if (ball.getxPosition() >= getWidth() - ball.getWidth()) {
			// Hit right side of screen
			ball.setxVelocity(-ball.getxVelocity());
			resetBall();
		}

		if (ball.getyPosition() <= 0 ) {
			// Hit bottom of screen
			ball.setyVelocity(-ball.getyVelocity());
		} else if (ball.getyPosition() >= getHeight() - ball.getHeight()) {
			// hit top
			ball.setyVelocity(-ball.getyVelocity());	    	   
		}
	}
		
	public void resetBall() {
		
		ball.resetToInitialPosition();
		
	}
	
	// Key press Methods
	@Override
	public void keyTyped(KeyEvent event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent event) {
		if(event.getKeyCode() == KeyEvent.VK_UP) { // Paddle2 Up/Down
            paddle2.setyVelocity(-1);
       } else if(event.getKeyCode() == KeyEvent.VK_DOWN) {
            paddle2.setyVelocity(1);
        }
		if(event.getKeyCode() == KeyEvent.VK_W) { // Paddle1 Up/Down
			paddle1.setyVelocity(-1);
		} else if(event.getKeyCode() == KeyEvent.VK_S) {
			paddle1.setyVelocity(1);
		}
	}

	@Override
	public void keyReleased(KeyEvent event) {
		if(event.getKeyCode() == KeyEvent.VK_UP || event.getKeyCode() == KeyEvent.VK_DOWN) { // Paddle2 Up/Down
            paddle2.setyVelocity(0);
        }
		if(event.getKeyCode() == KeyEvent.VK_W || event.getKeyCode() == KeyEvent.VK_S) { // Paddle1 Up/Down
			paddle1.setyVelocity(0);
		}
	}

	@Override
	public void actionPerformed(ActionEvent event) {

		update();
		repaint();
		// Good practice to update and repaint
		
	}
	
	public void createObjects() { // Creates Balls & Paddles
		
		// Ball Variable
		ball = new Ball(getWidth(),getHeight());
		// Paddles
		paddle1 = new Paddle(Player.One, getWidth(), getHeight());
		paddle2 = new Paddle(Player.Two, getWidth(), getHeight());
	}
	
	// Game initialised variable used in the update() method
	GameState gameState = GameState.Initialising;
	
	private void update() {
        switch(gameState) {
            case Initialising: {
               createObjects();
               gameState = GameState.Playing;
               ball.setxVelocity(BALL_MOVEMENT_SPEED);
               ball.setyVelocity(BALL_MOVEMENT_SPEED);
                break;
            }
            case Playing: {
            	moveObject(paddle2);
            	moveObject(paddle1);
            	moveObject(ball); // Move Ball
            	checkWallBounce(); // Check bounce
                break;
           }
           case GameOver: {
               break;
           }
       }
   }
	
	@Override
	 public void paintComponent(Graphics g) { // Draws a shape
	     super.paintComponent(g);
	     paintDottedLine(g);
	     if (gameState != GameState.Initialising) {
	    	 paintSprite(g, ball);
	    	 paintSprite(g, paddle1);
	    	 paintSprite(g, paddle2);
	    	 
	     }
	 }
	
	private void paintSprite(Graphics g, Sprite sprite) {
		g.setColor(sprite.getColour());
		g.fillRect(sprite.getxPosition(), sprite.getyPosition(), sprite.getWidth(), sprite.getHeight());
	}
	
	private void paintDottedLine(Graphics g) { // Paints dotted line 
		Graphics2D g2d = (Graphics2D) g.create();
		Stroke dashed = new BasicStroke(3, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{9}, 0);
		g2d.setStroke(dashed);
		g2d.setPaint(Color.WHITE);
		g2d.drawLine(getWidth() / 2, 0, getWidth() / 2, getHeight());
		g2d.dispose();
	}
}
