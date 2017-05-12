
/**
 * 
 * @author Amitav
 *
 *The Knight class represents a Knight chess piece
 */
public class Knight extends Piece {
	public Knight(boolean color, Coord pos) {
		super("knight", color, pos);
	}

	
	/**
	 * Checks if the knight can move to a spot
	 */
	@Override
	public boolean legalMove(Coord c) {
		int diffY = this.getCoord().getY() - c.getY();
		int diffX = this.getCoord().getX() - c.getX();
		return (Math.abs(diffY) == 2 && Math.abs(diffX) == 1) || (Math.abs(diffY) == 1 && Math.abs(diffX) == 2);
	}

}
