
/**
 * 
 * @author Amitav
 * The King class represents a King chess piece 
 */
public class King extends Piece {
	private boolean hasMoved;
	private boolean beenInCheck;
	public static void main(String[] args) {
		King b = new King(true, new Coord(0, 0));
		System.out.println(b.legalMove(new Coord(0, 2)));
		System.out.println(b.getClass().getName().equals("King"));
	}

	/**
	 * Creates a new King
	 * @param color true -> white, false
	 * @param pos The position 
	 */
	public King(boolean color, Coord pos) {
		super("king", color, pos);
		hasMoved = false;
		beenInCheck = false;
	}
	/**
	 * changes hasMoved to true
	 */
	public void updateMove(){
		hasMoved = true;
	}
	/**
	 * 
	 * @return If the king has moevd
	 */
	public boolean hasMoved(){
		return hasMoved;
	}
	
	/**
	 * Sets that the king has been in check
	 */
	public void checked(){
		beenInCheck = true;
	}
	
	/**
	 * 
	 * @return If the king has been in check
	 */
	public boolean check(){
		return beenInCheck;
	}
	
	/**
	 * Checks if the king can move to a cord
	 */
	@Override
	public boolean legalMove(Coord c) {
		Coord pos = this.getCoord();
		return (pos.isDiagonalAdjacent(c))
				|| Math.abs(pos.getX() - c.getX()) == 0 && Math.abs(pos.getY() - c.getY()) == 1
				|| Math.abs(pos.getX() - c.getX()) == 1 && Math.abs(pos.getY() - c.getY()) == 0;

	}

}
