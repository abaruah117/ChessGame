
/**
 * 
 * @author Amitav & Kevin 
 * Period 3
 */
/*
 * The King class represents a King chess piece 
 */
public class King extends Piece {
	public static void main(String[] args) {
		King b = new King(true, new Coord(0, 0));
		System.out.println(b.legalMove(new Coord(0, 2)));
		System.out.println(b.getClass().getName().equals("King"));
	}
	private boolean hasMoved;
	private boolean beenInCheck;

	/**
	 * Creates a new King
	 * @param color true -> white, false -> black
	 * @param pos The position 
	 */
	public King(boolean color, Coord pos) {
		super("king", color, pos);
		hasMoved = false;
		beenInCheck = false;
	}
	/**
	 * Gets if the king has been in check
	 * @return If the king has been in check
	 */
	public boolean check(){
		return beenInCheck;
	}
	/**
	 * Sets that the king has been in check
	 */
	public void checked(){
		beenInCheck = true;
	}
	
	/**
	 * Gets if the king has moved
	 * @return If the king has moved
	 */
	public boolean hasMoved(){
		return hasMoved;
	}
	
	/**
	 * Checks legality of a move to Coord c
	 * @param Coord c the Coord to check if legal move
	 */
	@Override
	public boolean legalMove(Coord c) {
		Coord pos = this.getCoord();
		return (pos.isDiagonalAdjacent(c))
				|| Math.abs(pos.getX() - c.getX()) == 0 && Math.abs(pos.getY() - c.getY()) == 1
				|| Math.abs(pos.getX() - c.getX()) == 1 && Math.abs(pos.getY() - c.getY()) == 0;

	}
	
	/**
	 * changes hasMoved to true
	 */
	public void updateMove(){
		hasMoved = true;
	}

}
