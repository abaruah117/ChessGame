/**
 * 
 * @author Amitav & Kevin 
 * Period 3
 */
/*
 * A class representing a Queen chesspiece
 */
public class Queen extends Piece {
	public Queen(boolean color, Coord pos) {
		super("queen", color, pos);
	}
	
	/**
	 * Checks legality of a move to Coord c
	 * @param Coord c the Coord to check if legal move
	 */
	@Override
	public boolean legalMove(Coord c) {
		int diffX = Math.abs(this.getCoord().getX() - c.getX());
		int diffY = Math.abs(this.getCoord().getY() - c.getY());
		boolean diag = (diffX == diffY);
		boolean horizontal = (diffY==0)&&(diffX>0);
		boolean vertical = (diffX==0)&&(diffY>0);
		return diag||horizontal||vertical;
	}



}
