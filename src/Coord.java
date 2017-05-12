import java.util.ArrayList;

public class Coord {
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

	private int x;

	private int y;

	public Coord() {
		x = 0;
		y = 0;
	}
	
	public Vector toVector() {
		return new Vector(x, y);
	}

	public Coord(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public boolean isDiagonalAdjacent(Coord other) {
		return (Math.abs(this.getY() - other.getY()) == 1) && (Math.abs(this.getX() - other.getX()) == 1);
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public String toString() {
		return getX() + "," + getY();
	}

	public int vertDistance(Coord other) {
		return other.getY() - this.getY();
	}
	public int xDistance(Coord other) {
		return other.getX() - this.getX();
	}

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
	public static void main(String[] args) {
		System.out.println(Coord.squaresBetween(new Coord(0,0), new Coord(4,4)));
	}
	

}
