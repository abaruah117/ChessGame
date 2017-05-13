import java.util.Arrays;
import java.util.Comparator;


/**
 * 
 * @author Amitav & Kevin 
 * Period 3
 */
/*
 * A class used to calculate if the used has clicked inside a quadrilateral
 */
public class Clickable {
    
    private Vector topLeft, topRight, botLeft, botRight;
    private float slopeLeft, slopeRight, slopeTop, slopeBot;
    
    /**
     * Constructor that creates a new Clickable
     * @param topLeft The top left coord
     * @param topRight The top right coord
     * @param botLeft The bot left coord
     * @param botRight The bot right coord
     */
	public Clickable(Vector topLeft, Vector topRight, Vector botLeft,
			Vector botRight) {
		
		Vector[] sortingY = new Vector[]{topLeft, topRight, botLeft, botRight};
		Arrays.sort(sortingY, new Comparator<Vector>() {

			@Override
			public int compare(Vector o1, Vector o2) {
				return (int) -(o1.getY() - o2.getY());
			}
			
		});
		
		Comparator<Vector> xCompare = new Comparator<Vector>() {

			@Override
			public int compare(Vector o1, Vector o2) {
				return (int) -(o1.getX() - o2.getX());
			}
		};
		
		Vector[] sortingXTop = new Vector[]{sortingY[0], sortingY[1]};
		Vector[] sortingXBot = new Vector[]{sortingY[2], sortingY[3]};
		Arrays.sort(sortingXTop, xCompare);
		Arrays.sort(sortingXBot, xCompare);
		this.topLeft = sortingXTop[1];
		this.topRight = sortingXTop[0];
		this.botLeft = sortingXBot[1];
		this.botRight = sortingXBot[0];
		slopeTop = (this.topLeft.getY() - this.topRight.getY())/(this.topLeft.getX() - this.topRight.getX());
		slopeBot = (this.botLeft.getY() - this.botRight.getY())/(this.botLeft.getX() - this.botRight.getX());
		slopeLeft = (this.botLeft.getX() - this.topLeft.getX())/(this.botLeft.getY() - this.topLeft.getY());
		slopeRight = (this.botRight.getX() - this.topRight.getX())/(this.botRight.getY() - this.topRight.getY());
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
		return x > getMinX(y) && x <= getMaxX(y) && y > getMinY(x) && y <= getMaxY(x);
	}
	
}