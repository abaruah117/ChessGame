
/**
 * 
 * @author Kevin Palani
 * A class used to calculate if the used has clicked inside a quadralateral
 */
public class Clickable {
    
    private Vector topLeft, topRight, botLeft, botRight;
    private float slopeLeft, slopeRight, slopeTop, slopeBot;
    
    /**
     * Creates a new Clickable
     * @param topLeft The top left cord
     * @param topRight The top right cord
     * @param botLeft The bot left cordd
     * @param botRight The bot right cord
     */
	public Clickable(Vector topLeft, Vector topRight, Vector botLeft,
			Vector botRight) {
		this.topLeft = topLeft;
		this.topRight = topRight;
		this.botLeft = botLeft;
		this.botRight = botRight;
		
//		System.out.println("topleft " + topLeft );
//		System.out.println("topRight " + topRight );
//		System.out.println("botLeft " + botLeft );
//		System.out.println("botRight " + botRight );
//		System.exit(0);
		slopeTop = (topLeft.getY() - topRight.getY())/(topLeft.getX() - topRight.getX());
		slopeBot = (botLeft.getY() - botRight.getY())/(botLeft.getX() - botRight.getX());
		slopeLeft = (botLeft.getX() - topLeft.getX())/(botLeft.getY() - topLeft.getY());
		slopeRight = (botRight.getX() - topRight.getX())/(botRight.getY() - topRight.getY());
	}
    
	/**
	 * Gets the right most side of the quad at a given Y
	 * @param y The Y value 
	 * @return the maximum X at the Y
	 */
	private float getMaxX(float y) {
		return (y - botRight.getY()) * slopeRight + botRight.getX();
	}
	
	/**
	 * Gets the top most side of the quad at a given X
	 * @param x The X value 
	 * @return the maximum Y at the X
	 */
	private float getMaxY(float x) {
		return (x - topLeft.getX()) * slopeTop + topLeft.getY();
	}

	/**
	 * Gets the left most side of the quad at a given Y
	 * @param y The Y value 
	 * @return the minimum X at the Y
	 */
	private float getMinX(float y) {
		return (y - botLeft.getY()) * slopeLeft + botLeft.getX();
	}
	
	/**
	 * Gets the bottom most side of the quad at a given X
	 * @param x The X value 
	 * @return the minimum Y at the X
	 */
	private float getMinY(float x) {
		return (x - botLeft.getX()) * slopeBot + botLeft.getY();
	}

	/**
	 * Tests if a point is inside the quad, including the top and right sides, excluding the bot and left sides
	 * @param point The point to test
	 * @return If the point is inside the quad
	 */
	public boolean testClick(Vector point) {
		float x = point.getX();
		float y = point.getY();
//		System.out.println("Clicked at " + x + ", " + y);
//		System.out.println("Bounds:");
//		System.out.println("minX: " + getMinX(y));
//		System.out.println("maxX: " + getMaxX(y));
//		System.out.println("minY: " + getMinY(x));
//		System.out.println("maxY: " + getMaxY(x));
		return x > getMinX(y) && x <= getMaxX(y) && y > getMinY(x) && y <= getMaxY(x);
	}
	
}