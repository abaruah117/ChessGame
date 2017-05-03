
public class King extends Piece {
	public King(boolean color, Coord pos) {
		super("king", color, pos);
	}

	@Override
	public boolean legalMove(Coord c) {
		Coord pos = this.getCoord();
		return (pos.isDiagonalAdjacent(c))
				|| Math.abs(pos.getX() - c.getX()) == 0 && Math.abs(pos.getY() - c.getY()) == 1
				|| Math.abs(pos.getX() - c.getX()) == 1 && Math.abs(pos.getY() - c.getY()) == 0;

	}

	public static void main(String[] args) {
		King b = new King(true, new Coord(0, 0));
		System.out.println(b.legalMove(new Coord(0, 2)));
		System.out.println(b.getClass().getName().equals("King"));
	}

}
