
public class Bishop extends Piece {
	public Bishop(boolean color,Coord pos){
		super("bishop", color,pos);
	}

	@Override
	public boolean legalMove(Coord c) {
		int diffX = Math.abs(this.getCoord().getX()-c.getX());
		int diffY = Math.abs(this.getCoord().getY()-c.getY());
		return diffX==diffY;
	}


}
