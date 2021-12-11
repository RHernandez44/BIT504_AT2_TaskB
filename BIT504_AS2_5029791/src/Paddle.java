import java.awt.Color;

public class Paddle extends Sprite {

	private final static int width = 10;
	private final static int height = 100;
	private final static int distanceFromEdge = 40;
	private final static Color colour = Color.WHITE;
	
	public Paddle(Player player, int panelWidth, int panelHeight) {
        setWidth(width);
        setHeight(height);
        setColour(colour);
        int xPos;
        if(player == Player.One) {
            xPos = distanceFromEdge;
        } else {
            xPos = panelWidth - distanceFromEdge - getWidth();
        }
        setInitialPosition(xPos, panelHeight / 2 - (getHeight() / 2));
        resetToInitialPosition();
    }
}
