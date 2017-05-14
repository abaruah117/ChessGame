import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

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

	private final int MAX_DEPTH = 2;
	
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
	public Move(int a, int b, int c, int d, int fT, Piece[][] pieces) {
		if(a == c && b == d) {
			return; //I added this, supposed to not move a piece to the same spot cuz it crashes
		}
		this.pieces = pieces;
		xpos_current = a;
		ypos_current = b;
		xpos_new = c;
		ypos_new =  d ;
		futureTurns = fT;
		//System.out.println("Found a new move at " + xpos_current + ", " + ypos_current + " with a piece " + pieces[xpos_current][ypos_current]);
		moveValue = (int) calculateMoveValue(0);

	}
	
	private float calculateMoveValue(int depth) {
		
		
		if(depth > MAX_DEPTH) {
			return 0;
		}
		
		float power = capture() + danger();
		
		if(depth > MAX_DEPTH) {
			return power;
		}
		
		ArrayList<Float> futureMoves = new ArrayList<Float>();

		if (pieceAt(xpos_new, ypos_new) != null) {
			temp = pieceAt(xpos_new, ypos_new);
		}

		//pieces[xpos_new][ypos_new] = pieces[xpos_current][ypos_current];
		setPiece(xpos_new, ypos_new, pieceAt(xpos_current, ypos_current));
		//pieces[xpos_current][ypos_current] = null;
		setPiece(xpos_current, ypos_current, null);
		for (int xpos_other = 0; xpos_other <= 7; xpos_other++) {
			for (int ypos_other = 0; ypos_other <= 7; ypos_other++) {
				if ((pieceAt(xpos_new, ypos_new) != null)
						&& ((pieceAt(xpos_other, ypos_other) != null) && (pieceAt(xpos_new, ypos_new).getBooleanColor() != pieceAt(xpos_other, ypos_other).getBooleanColor()))
						&& (pieceAt(xpos_new, ypos_new).legalMove((new Coord(xpos_other, ypos_other))))) {
					//futureMoves.add(new Move(xpos_new, ypos_new, xpos_other, ypos_other, futureTurns + 1, pieces));
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
					
					futureMoves.add(calculateMoveValue(depth+1));
					
					xpos_current = temp_xpos_current;
					ypos_current = temp_ypos_current;
					xpos_new = temp_xpos_new;
					ypos_new = temp_ypos_new;
					moveValue = temp_moveValue;
					futureTurns = temp_futureTurns;
					temp = temp_tempPiece;
					
				}
			}
		}

		Collections.sort(futureMoves, new Comparator<Float>() {


			@Override
			public int compare(Float arg0, Float arg1) {
				// TODO Auto-generated method stub
				return (int) (arg0*1000-arg1*1000);
			}
		});

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
	 * Uses Piece.typeno to determine how valuable the move would be. More
	 * valuable pieces have higher typeno.
	 * 
	 * @return value of a capturable piece, if aypos_new is present
	 */
	private int capture() {

		if (pieceAt(xpos_new, ypos_new) != null) {
			return pieceAt(xpos_new, ypos_new).getPointVal() + 1;
		} else {
			return 0;
		}

	}

//	/**
//	 * A nested version of the AI() handler itself, that determines possible
//	 * future moves and adds half of their own values to that of the original
//	 * move.
//	 * 
//	 * @return half the value of the most valuable follow-up move
//	 */
//	private int future() {
//
//		ArrayList<Integer> futureMoves = new ArrayList<Integer>();
//
//		if (pieceAt(xpos_new, ypos_new) != null) {
//			temp = pieceAt(xpos_new, ypos_new);
//		}
//
//		//pieces[xpos_new][ypos_new] = pieces[xpos_current][ypos_current];
//		setPiece(xpos_new, ypos_new, pieceAt(xpos_current, ypos_current));
//		//pieces[xpos_current][ypos_current] = null;
//		setPiece(xpos_current, ypos_current, null);
//		for (int xpos_other = 0; xpos_other <= 7; xpos_other++) {
//			for (int ypos_other = 0; ypos_other <= 7; ypos_other++) {
//				if ((pieceAt(xpos_new, ypos_new) != null)
//						&& ((pieceAt(xpos_other, ypos_other) != null) && (pieceAt(xpos_new, ypos_new).getBooleanColor() != pieceAt(xpos_other, ypos_other).getBooleanColor()))
//						&& (pieceAt(xpos_new, ypos_new).legalMove((new Coord(xpos_other, ypos_other))))) {
//					//futureMoves.add(new Move(xpos_new, ypos_new, xpos_other, ypos_other, futureTurns + 1, pieces));
//					int temp_xpos_current = xpos_current;
//					int temp_ypos_current = ypos_current;
//					int temp_xpos_new = xpos_new;
//					int temp_ypos_new = ypos_new;
//					int temp_moveValue = moveValue;
//					int temp_futureTurns = futureTurns;
//					Piece temp_tempPiece = temp;
//					
//					xpos_current = xpos_new;
//					ypos_current = ypos_new;
//					xpos_new = xpos_other;
//					ypos_new = ypos_other;
//					
//					
//				}
//			}
//		}
//
//		Collections.sort(futureMoves, new Comparator<Integer>() {
//
//
//			@Override
//			public int compare(Integer arg0, Integer arg1) {
//				// TODO Auto-generated method stub
//				return arg0-arg1;
//			}
//		});
//
//		//pieces[xpos_current][ypos_current] = pieces[xpos_new][ypos_new];
//		setPiece(xpos_current, ypos_current, pieceAt(xpos_new, ypos_new));
//		//pieces[xpos_new][ypos_new] = temp;
//		setPiece(xpos_new, ypos_new, temp);
//		if (futureMoves.size() > 0) {
//			return futureMoves.get(0) / 2;
//		} else {
//			return 0;
//		}
//
//	}
//	
	private Piece pieceAt(Coord c) {
		return pieces[Board.getSize() - 1 - c.getY()][c.getX()];
	}
	
	private Piece pieceAt(int x, int y) {
		return pieceAt(new Coord(x, y));
	}
	
	private void setPiece(Coord c, Piece p) {
		pieces[Board.getSize() - 1 - c.getY()][c.getX()] = p;	
	}
	
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

}