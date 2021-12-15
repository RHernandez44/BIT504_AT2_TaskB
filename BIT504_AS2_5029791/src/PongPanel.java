import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.Font;

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
	// Game initialised variable used in the update() method
	GameState gameState = GameState.Initialising;
	// Win State 
	private final static int POINTS_TO_WIN = 5;  
	int player1Score = 0, player2Score = 0;
	Player gameWinner;
	
	// Constructor
	public PongPanel() {
		
		setBackground(BACKGROUND_COLOUR);
		Timer timer = new Timer(TIMER_DELAY, this);
        timer.start();	// Creates timer loop to call actionPerformed()
        addKeyListener(this);
        setFocusable(true);		
	}
	
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
            	checkPaddleBounce();
            	checkWin();
                break;
           }
           case GameOver: {
               break;
           }
       }
   }

	public void createObjects() { // Creates Balls & Paddles

		// Ball Variable
		ball = new Ball(getWidth(),getHeight());
		// Paddles
		paddle1 = new Paddle(Player.One, getWidth(), getHeight());
		paddle2 = new Paddle(Player.Two, getWidth(), getHeight());
	}
	
	public void addScore(Player player) { // Adds +1 to given playerScore
		
		if (player == player.One)
			player1Score++;
		if (player == player.Two)
			player2Score++;
	}
	
	public void checkWin() {
	
		if (player1Score >= POINTS_TO_WIN) {
		gameWinner = Player.One;
		gameState = GameState.GameOver;
		} else if (player2Score >= POINTS_TO_WIN) {
			gameWinner = Player.Two;
			gameState = GameState.GameOver;
		}
		
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
			addScore(Player.Two);
			resetBall();
		} else if (ball.getxPosition() >= getWidth() - ball.getWidth()) {
			// Hit right side of screen
			ball.setxVelocity(-ball.getxVelocity());
			addScore(Player.One);
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
	
	public void checkPaddleBounce() {
		
		if(ball.getxVelocity() < 0 && ball.getRectangle().intersects(paddle1.getRectangle())) {
	          ball.setxVelocity(BALL_MOVEMENT_SPEED);
	      }
		
	    if(ball.getxVelocity() > 0 && ball.getRectangle().intersects(paddle2.getRectangle())) {
	          ball.setxVelocity(-BALL_MOVEMENT_SPEED);
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
            paddle2.setyVelocity(-2);
       } else if(event.getKeyCode() == KeyEvent.VK_DOWN) {
            paddle2.setyVelocity(2);
        }
		if(event.getKeyCode() == KeyEvent.VK_W) { // Paddle1 Up/Down
			paddle1.setyVelocity(-2);
		} else if(event.getKeyCode() == KeyEvent.VK_S) {
			paddle1.setyVelocity(2);
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
	
	@Override
	 public void paintComponent(Graphics g) { // Draws a shape
	     super.paintComponent(g);
	     paintDottedLine(g);
	     if (gameState != GameState.Initialising) {
	    	 paintSprite(g, ball);
	    	 paintSprite(g, paddle1);
	    	 paintSprite(g, paddle2);
	    	 paintScores(g);
	    	 paintWinner(g);
	     }
	 }
	
	private final static int WINNER_TEXT_X = 200;
	private final static int WINNER_TEXT_Y = 200;
	private final static int WINNER_FONT_SIZE = 40;
	private final static String WINNER_FONT_FAMILY = "Serif";
	private final static String WINNER_TEXT = "WIN!";

	private void paintWinner(Graphics g) {

		if(gameWinner != null) {
			Font winnerFont = new Font(WINNER_FONT_FAMILY, Font.BOLD, WINNER_FONT_SIZE);
			g.setFont(winnerFont);
			int xPosition = getWidth() / 2;
			if(gameWinner == Player.One) {
				xPosition -= WINNER_TEXT_X;
			} else if(gameWinner == Player.Two) {
				xPosition += WINNER_TEXT_X;
			}
			g.drawString(WINNER_TEXT, xPosition, WINNER_TEXT_Y);
		}
	}

	private void paintScores(Graphics g) {
         int xPadding = 100;
         int yPadding = 100;
         int fontSize = 50; 
         Font scoreFont = new Font("Serif", Font.BOLD, fontSize);
         String leftScore = Integer.toString(player1Score);
         String rightScore = Integer.toString(player2Score);
         g.setFont(scoreFont);
         g.drawString(leftScore, xPadding, yPadding);
        g.drawString(rightScore, getWidth()-xPadding, yPadding);
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
