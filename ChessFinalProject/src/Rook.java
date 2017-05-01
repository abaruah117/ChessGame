

public class Rook extends Piece {
	public Rook(boolean color, Coord pos){
		super("rook",color, pos);
	}

	@Override
	public boolean legalMove(Coord c) {
		return ((Math.abs(this.getCoord().getX()-c.getX())!=0)&&(Math.abs(this.getCoord().getY()-c.getY())==0))||
				((Math.abs(this.getCoord().getX()-c.getX())==0)&&(Math.abs(this.getCoord().getY()-c.getY())!=0));
	}
	public static void main(String[] args) {
		Rook b = new Rook(true, new Coord(4,0));
		System.out.println(b.legalMove(new Coord(4,7)));
	}
	

}
