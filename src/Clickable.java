

public class Clickable {
    
    private Vector topLeft, topRight, botLeft, botRight;
    private float slopeLeft, slopeRight, slopeTop, slopeBot;
    
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
	
	private float getMinX(float y) {
		return (y - botLeft.getY()) * slopeLeft + botLeft.getX();
	}

	private float getMaxX(float y) {
		return (y - botRight.getY()) * slopeRight + botRight.getX();
	}
	
	private float getMinY(float x) {
		return (x - botLeft.getX()) * slopeBot + botLeft.getY();
	}
	
	private float getMaxY(float x) {
		return (x - topLeft.getX()) * slopeTop + topLeft.getY();
	}

	public Vector getTopLeft() {
		return topLeft;
	}

	public Vector getTopRight() {
		return topRight;
	}

	public Vector getBotLeft() {
		return botLeft;
	}

	public Vector getBotRight() {
		return botRight;
	}
	
}