import java.awt.Color;

public class Ball extends Sprite { // Extends to SuperClass

	// Class Var
	private int BALL_HEIGHT, BALL_WIDTH = 25;
	private Color BALL_COLOUR = Color.white;

	public Ball(int panelWidth, int panelHeight) {
       setWidth(BALL_WIDTH);
       setHeight(BALL_HEIGHT);
       setColour(BALL_COLOUR);
       setInitialPosition(panelWidth / 2 - (getWidth() / 2), panelHeight / 2 - (getHeight() / 2));
       resetToInitialPosition();
   }
	
	
	
}
