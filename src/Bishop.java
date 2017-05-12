

/**
 * 
 * @author Amitav
 *A class that represents a bishop chess peice
 */
public class Bishop extends Piece {
	public Bishop(boolean color,Coord pos){
		super("bishop", color,pos);
	}

	/**
	 * Checks to see if the bishop is allowed to move to a spot, which are diagonal spots
	 */
	@Override
	public boolean legalMove(Coord c) {
		int diffX = Math.abs(this.getCoord().getX()-c.getX());
		int diffY = Math.abs(this.getCoord().getY()-c.getY());
		return diffX==diffY;
	}


}
