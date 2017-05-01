
public class Queen extends Piece {
	public Queen(boolean color, Coord pos) {
		super("queen", color, pos);
	}

	@Override
	public boolean legalMove(Coord c) {
		int diffX = Math.abs(this.getCoord().getX() - c.getX());
		int diffY = Math.abs(this.getCoord().getY() - c.getY());
		boolean diag = (diffX == diffY);
		boolean horizontal = (diffY==0)&&(diffX>0);
		boolean vertical = (diffX==0)&&(diffY>0);
		return false;
	}



}
