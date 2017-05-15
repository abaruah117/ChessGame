import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

/**
 * 
 * @author Amitav & Kevin Period 3
 */
/**
 * 
 * A modified version of {@link https://github.com/austinmorgan/chess/blob/master/src/chess/Move.java} <b>
 * Represents a potential move by the computer, with methods to calculate its value
 *
 */
public class Move  {

	int xpos_current, ypos_current, xpos_new, ypos_new;
	int moveValue;
	int futureTurns;
	Piece temp = null;
	private Piece[][] pieces;
	private ChessGame game;

	private final int MAX_DEPTH = 1;
	
	/**
	 * @param a
	 *            The piece's current x position.
	 * @param b
	 *            The piece's current y position.
	 * @param c
	 *            The x-coordinate of a potential move.
	 * @param d
	 *            The y-coordinate of a potential move.
	 * @param fT
	 *            How maypos_new moves in the future are we? Caps at 3.
	 */
	public Move(int a, int b, int c, int d, int fT, ChessGame game, Piece[][] pieces) {
		if(a == c && b == d) {
			return; //I added this, supposed to not move a piece to the same spot cuz it crashes
		}
		this.game = game;
		this.pieces = pieces;
		xpos_current = a;
		ypos_current = b;
		xpos_new = c;
		ypos_new =  d ;
		futureTurns = fT;
		if(pieceAt(new Coord(xpos_current, ypos_current)) == null) {
			System.err.println("NULL STARTING MOVE???");
		}
		//System.out.println("Found a new move at " + xpos_current + ", " + ypos_current + " with a piece " + pieces[xpos_current][ypos_current]);
		moveValue = (int) calculateMoveValue(0);

	}
	/**
	 * Recusivly calculates a move value by finding out how valuable the move would be in the current board state, then repeats the process to see how valuable the move would be in the future if the move was acutally done
	 * @param depth How many steps into the future the chess ai has searched
	 * @return How valuable the move would be based on the current board state and the potential future board state
	 */
	private float calculateMoveValue(int depth) {

		
	
		
		float power = capture() + danger();
	
		if(depth > MAX_DEPTH) {
			return power;
		}
		
		ArrayList<Float> futureMoves = new ArrayList<Float>();

		if (pieceAt(xpos_new, ypos_new) != null) {
			temp = pieceAt(xpos_new, ypos_new);
		}

		//System.out.println("Setting the game stuff");
		Piece currPiece = pieceAt(xpos_current, ypos_current);
		//System.out.println(currPiece);
		setPiece(xpos_new, ypos_new, currPiece);
		setPiece(xpos_current, ypos_current, null);
		//System.out.println("Done setting the stuff");
		
		
		for (int xpos_other = 0; xpos_other <= 7; xpos_other++) {
			for (int ypos_other = 0; ypos_other <= 7; ypos_other++) {
				//System.out.println("Checking a future calc " + depth);
//				if ((pieceAt(xpos_new, ypos_new) != null)
//						&& ((pieceAt(xpos_other, ypos_other) != null) && (pieceAt(xpos_new, ypos_new).getBooleanColor() != pieceAt(xpos_other, ypos_other).getBooleanColor()))
//						&& (pieceAt(xpos_new, ypos_new).legalMove((new Coord(xpos_other, ypos_other))))) { OLD CHECK
					Piece p = pieceAt(xpos_other, ypos_other);
					//System.out.println(pieceAt(xpos_new, ypos_new));
					if(game.movePiece(new Coord(xpos_new, ypos_new), new Coord(xpos_other, ypos_other))) {
						
					//futureMoves.add(new Move(xpos_new, ypos_new, xpos_other, ypos_other, futureTurns + 1, pieces));
					//System.out.println("The future calc was psooble " + depth);
					int temp_xpos_current = xpos_current;
					int temp_ypos_current = ypos_current;
					int temp_xpos_new = xpos_new;
					int temp_ypos_new = ypos_new;
					int temp_moveValue = moveValue;
					int temp_futureTurns = futureTurns;
					Piece temp_tempPiece = temp;
					
					xpos_current = xpos_new;
					ypos_current = ypos_new;
					xpos_new = xpos_other;
					ypos_new = ypos_other;
					float calc = calculateMoveValue(depth+1);
					//System.out.println(calc);
					futureMoves.add(calc);
					
					xpos_current = temp_xpos_current;
					ypos_current = temp_ypos_current;
					xpos_new = temp_xpos_new;
					ypos_new = temp_ypos_new;
					moveValue = temp_moveValue;
					futureTurns = temp_futureTurns;
					temp = temp_tempPiece;
					game.revertMove(new Coord(xpos_new, ypos_new), new Coord(xpos_other, ypos_other), p);
					
				}
			}
		}

		Collections.sort(futureMoves, new Comparator<Float>() {


			@Override
			public int compare(Float arg0, Float arg1) {
				// TODO Auto-generated method stub
				return (int) (arg1*1000-arg0*1000);
			}
		});
		//System.out.println((futureMoves));

		//pieces[xpos_current][ypos_current] = pieces[xpos_new][ypos_new];
		setPiece(xpos_current, ypos_current, pieceAt(xpos_new, ypos_new));
		//pieces[xpos_new][ypos_new] = temp;
		setPiece(xpos_new, ypos_new, temp);
		if (futureMoves.size() > 0) {
			power +=  futureMoves.get(0);
		}
		
		return depth == 0 ? power : power/2;
		
	}



	/**
	 * Uses Piece.getPointVal to determine how valuable the move would be. More
	 * valuable pieces have higher typeno.
	 * 
	 * @return value of a capturable piece, if a ypos_new is present
	 */
	private int capture() {

		if (pieceAt(xpos_new, ypos_new) != null) {
			return pieceAt(xpos_new, ypos_new).getPointVal() + 1;
		} else {
			return 0;
		}

	}

	/**
	 * Gets the piece from the board at a specific coord
	 * @param c The coord to get the piece from 
	 * @return The piece
	 */
	private Piece pieceAt(Coord c) {
		//return pieces[Board.getSize() - 1 - c.getY()][c.getX()];
		return game.getPiece(c);
	}
	
	/**
	 * Gets the piece from the board at a specific x and y coord
	 * @param x The x coord
	 * @param y The y coord 
	 * @return The piece
	 */
	private Piece pieceAt(int x, int y) {
		
		return pieceAt(new Coord(x, y));
	}
	
	/**
	 * Sets the piece at a specific coord 
	 * @param c the coord to set the piece at
	 * @param p The piece to set the coord to
	 */
	private void setPiece(Coord c, Piece p) {
		//pieces[Board.getSize() - 1 - c.getY()][c.getX()] = p;	
		game.setPiece(c, p);
	}
	
	/**
	 * Sets the piece at a specific x and y coord 
	 * @param x The x coord
	 * @param y The y coord
	 * @param p The piece to set the x and y coord to
	 */
	private void setPiece(int x, int y, Piece p) {
		setPiece(new Coord(x, y), p);
	}

	/**
	 * A bit simplistic and ignores the danger of moving out of the way from
	 * protecting a valuable piece, but what it does do is simply let the AI
	 * know that the move will end in a valuable piece being captured.
	 * 
	 * @return the value of an endangered piece
	 */
	private int danger() {

		boolean danger = false;

		if (pieceAt(xpos_new, ypos_new) != null) {
			temp = pieceAt(xpos_new, ypos_new);
		}

		//pieces[xpos_new][ypos_new] = pieces[xpos_current][ypos_current];
		setPiece(xpos_new, ypos_new, pieceAt(xpos_current, ypos_current));
		//pieces[xpos_current][ypos_current] = null;
		setPiece(xpos_current, ypos_current, null);

		for (int xpos_other = 0; xpos_other <= 7; xpos_other++) {
			for (int ypos_other = 0; ypos_other <= 7; ypos_other++) {
				if(xpos_other == xpos_new && ypos_other == ypos_new) {
					continue; //I added this, supposed to not allowed a piece to move to the same spot cuz it like crashed
				}
				
				if ((pieceAt(xpos_other, ypos_other) != null) && pieceAt(xpos_new, ypos_new) != null && ((pieceAt(xpos_other, ypos_other).getBooleanColor() != pieceAt(xpos_new, ypos_new).getBooleanColor()))
						&& (pieceAt(xpos_other, ypos_other).legalMove(new Coord(xpos_new, ypos_new)))) {
					danger = true;
					break;
				}
			}
		}

		//pieces[xpos_current][ypos_current] = pieces[xpos_new][ypos_new];
		setPiece(xpos_current, ypos_current, pieceAt(xpos_new, ypos_new));
		//pieces[xpos_new][ypos_new] = temp;
		setPiece(xpos_new, ypos_new, temp);

		if (danger) {
			return 0 - pieceAt(xpos_current, ypos_current).getPointVal();
		} else {
			return 0;
		}

	}
	public String toString(){
		return this.xpos_current + ","+this.ypos_current+ " moves to "+this.xpos_new +","+ this.ypos_new+ "VALUE: "+this.moveValue;
	}

}