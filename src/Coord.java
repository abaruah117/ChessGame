import java.util.ArrayList;

/**
 * 
 * @author Amitav
 *Represents a point in 2d, as well as methods to compare coords
 */
public class Coord {
	
	
	private int x;

	private int y;

	/**
	 * Creates a new coord with value (0,0)
	 */
	public Coord() {
		x = 0;
		y = 0;
	}
	
	/**
	 * Creates a new coord with value (x,y)
	 * @param x The X value of the coord
	 * @param y The Y value of the coord
	 */
	public Coord(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	
	/**
	 * Gets a list of the coords in between two coords
	 * @param c1 The first coord
	 * @param c2 The second coord
	 * @return An ArrayList of coords in between the two coords
	 */
	public static ArrayList<Coord> squaresBetween(Coord c1, Coord c2){
		ArrayList<Coord> squares = new ArrayList<Coord>();
		int x1 = c1.getX();
		int x2 = c2.getX();
		int y1 = c1.getY();
		int y2 = c2.getY();
		int incX = 0;
		int incY = 0;
		if (x2 - x1 > 0) {
			incX = 1;
		} else if (x2 - x1 < 0) {
			incX = -1;
		}
		if (y2 - y1 > 0) {
			incY = 1;
		} else if (y2 - y1 < 0) {
			incY = -1;
		}
		for (int x = x1 + incX, y = y1 + incY; x != x2 || y != y2; x += incX, y += incY) {
			squares.add(new Coord(x,y));
		}
		return squares;
	}
	
	/**
	 * Returns an array list of coords surrounding a coord
	 * @param c The center coord
	 * @return An array list of coords around the center coord
	 */
	public static ArrayList<Coord> surrounding(Coord c) {
		ArrayList<Coord> out = new ArrayList<Coord>();
		int x = c.getX();
		int y = c.getY();
		if (x > 0) {
			out.add(new Coord(x - 1, y));
			if (y > 0) {
				out.add(new Coord(x - 1, y - 1));
			}
			if (y < 7) {
				out.add(new Coord(x - 1, y + 1));
				
			}
		}
		if( x < 7){
			out.add(new Coord(x+1,y));
			if(y<7){
				out.add(new Coord(x+1,y+1));
			}
			if(y > 0){
				out.add(new Coord(x+1,y-1));
			}
		}
		if (y > 0) {
			out.add(new Coord(x,y-1));
		}
		if(y<7){
			out.add(new Coord(x,y+1));
		}
		return out;

	}

	/**
	 * Converts a coord into a Vector
	 * @return A vector with the x and y having the same values as this coord
	 */
	public Vector toVector() {
		return new Vector(x, y);
	}
	

	/**
	 * 
	 * @return The X value of the coord
	 */
	public int getX() {
		return x;
	}

	/**
	 * 
	 * @return The Y value of the coord
	 */
	public int getY() {
		return y;
	}

	/**
	 * Checks if another coord is at the diagonals of this coord
	 * @param other The other coord
	 * @return If the other coord is at the diagonals of this coord
	 */
	public boolean isDiagonalAdjacent(Coord other) {
		return (Math.abs(this.getY() - other.getY()) == 1) && (Math.abs(this.getX() - other.getX()) == 1);
	}

	/**
	 * Sets the X value of this coord
	 * @param x The X value to set
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * Sets the Y value of this coord
	 * @param y The Y value to set
	 */
	public void setY(int y) {
		this.y = y;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return getX() + "," + getY();
	}

	/**
	 * Gets the vertical distance between this coord and another
	 * @param other The other coord
	 * @return The vertical distance between this coord and the other coord
	 */
	public int vertDistance(Coord other) {
		return other.getY() - this.getY();
	}
	
	/**
	 * Gets the horizontal distance between this coord and another
	 * @param other The other coord
	 * @return The horizontal distance between this coord and the other coord
	 */
	public int xDistance(Coord other) {
		return other.getX() - this.getX();
	}

	
	/**
	 * Checks if this coord has the same values as another coord
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Coord))
			return false;
		Coord other = (Coord) obj;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}	

	

}
