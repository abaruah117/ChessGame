import java.util.ArrayList;

public class Coord {
	public static void main(String[] args) {
		System.out.println(surrounding(new Coord(1,1)).toString());
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

}
