
/**
 * 
 * @author Amitav & Kevin 
 * Period 3
 */
/*
 * A class representing a Rook chess piece.
 */
public class Rook extends Piece {
	private boolean hasMoved;
	/**
	 * Initializes the rook to a Piece with a given color and position, and 
	 * initializes hasMoved to false
	 * @param color the boolean color of the piece (true = white, black = false)
	 * @param pos the position Coord
	 */
	public Rook(boolean color, Coord pos){
		super("rook",color, pos);
		hasMoved = false;
	}
	/**
	 * Checks if this rook hasMoved
	 * @return if this rook has moved
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
		return ((Math.abs(this.getCoord().getX()-c.getX())!=0)&&(Math.abs(this.getCoord().getY()-c.getY())==0))||
				((Math.abs(this.getCoord().getX()-c.getX())==0)&&(Math.abs(this.getCoord().getY()-c.getY())!=0));
	}
	/**
	 * Changes hasMoved to true
	 */
	public void updateMove(){
		hasMoved = true;
	}
	

}
