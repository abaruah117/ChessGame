

public class Rook extends Piece {
	private boolean hasMoved;
	public static void main(String[] args) {
		Rook b = new Rook(true, new Coord(4,0));
		System.out.println(b.legalMove(new Coord(4,7)));
	}
	
	public Rook(boolean color, Coord pos){
		super("rook",color, pos);
		hasMoved = false;
	}
	/**
	 * changes hasMoved to true
	 */
	public void updateMove(){
		hasMoved = true;
	}
	public boolean hasMoved(){
		return hasMoved;
	}
	@Override
	public boolean legalMove(Coord c) {
		return ((Math.abs(this.getCoord().getX()-c.getX())!=0)&&(Math.abs(this.getCoord().getY()-c.getY())==0))||
				((Math.abs(this.getCoord().getX()-c.getX())==0)&&(Math.abs(this.getCoord().getY()-c.getY())!=0));
	}
	

}
