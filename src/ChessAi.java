
import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

/**
 * 
 * @author Amitav & Kevin Period 3
 */
/**
 * 
 * A modified version of
 * {@link https://github.com/austinmorgan/chess/blob/master/src/chess/Move.java}
 * <b> The computer AI which checks all possible moves the computer could make
 * and picks the best one, very slow
 */
public class ChessAi extends Thread{

	Piece[][] pieces;
	private ChessGame chessGame;

	boolean whiteCheck;
	boolean blackCheck;
	boolean checking;

	// Locations of kings for calculating check(mate)
	Point whiteKing;
	Point blackKing;
	// Location of threat to a king
	Point threat = new Point(0, 0);

	// for abstraction purposes
	boolean white = true;
	boolean black = false;

	// for moves
	int xpos_start, ypos_start, xpos_end, ypos_end;

	// whose turn it is
	// true = white, player turn ; false = black, computer turn
	SwapBool turn;

	public ChessAi(ChessGame chessGame, SwapBool turn) {
		this.turn = turn;
		this.pieces = chessGame.getBoard().getBoard();
		// System.out.println("Peices " + pieces);
		this.chessGame = chessGame;
		whiteCheck = false;
		blackCheck = false;
		checking = false;
	}

	private Piece pieceAt(Coord c) {
		return pieces[Board.getSize() - 1 - c.getY()][c.getX()];
	}

	/**
	 * This function handles the AI's turn. Most of the calculation takes place
	 * in the Move class, but this function uses the functions of that class to
	 * generate am ArrayList of possible moves, sort them from highest to lowest
	 * moveValue, and then select one of the most valuable moves to pass through
	 * Move.performMove(), and ultimately through the movePiece() function.
	 */
	public void AI() {
		
		ArrayList<Move> moves = new ArrayList<Move>();
//		Piece[][] a = chessGame.getBoard().getBoard();
//		for(int i = 0; i < a.length; i++){
//			for(int j =0 ; j< a.length; j++){
//				Piece p = a[i][j];
//				if(p==null){
//					System.out.print("    ||    ");
//				}
//				else{
//					System.out.print(p.getColor()+","+p.getName());
//				}
//			}
//			System.out.println();
//		}System.out.println("\n");
//		System.out.println(chessGame.getBoard().pieceAt(new Coord(5,6)));
//		System.out.println(chessGame.squareIsTargeted(true, new Coord(4,7)));
		
		if (chessGame.check(turn.getBool())) {
			for (int xpos_start = 0; xpos_start <= 7; xpos_start++) {
				for (int ypos_start = 0; ypos_start <= 7; ypos_start++) {
					Coord start = new Coord(xpos_start, ypos_start);
					Piece startPiece = pieceAt(start);
					if (startPiece == null) {
						continue;
					}
					for (int xpos_end = 0; xpos_end <= 7; xpos_end++) {
						for (int ypos_end = 0; ypos_end <= 7; ypos_end++) {

							Coord end = new Coord(xpos_end, ypos_end);
							Piece endPiece = pieceAt(end);
							if (startPiece.getBooleanColor() == turn.getBool() && chessGame.movePiece(start, end)) {
								if (!chessGame.check(false)) {
									moves.add(new Move(xpos_start, ypos_start, xpos_end, ypos_end, 1, pieces));
								} 
								chessGame.revertMove(start, end, endPiece);
							}
						}
					}
				}
				Thread.yield();
			}
		} else {
			for (int xpos_start = 0; xpos_start <= 7; xpos_start++) {
				for (int ypos_start = 0; ypos_start <= 7; ypos_start++) {
					Coord start = new Coord(xpos_start, ypos_start);
					Piece startPiece = pieceAt(start);
					if (startPiece == null) {
						continue;
					}
					for (int xpos_end = 0; xpos_end <= 7; xpos_end++) {
						for (int ypos_end = 0; ypos_end <= 7; ypos_end++) {

							Coord end = new Coord(xpos_end, ypos_end);
							Piece endPiece = pieceAt(end);
							if (startPiece.getBooleanColor() == turn.getBool() && chessGame.movePiece(start, end)) {
								if (chessGame.check(false)) {
									blackCheck = true;
								} else {
									blackCheck = false;
								}
								if (!blackCheck) {
									moves.add(new Move(xpos_start, ypos_start, xpos_end, ypos_end, 1, pieces));
								}
								chessGame.revertMove(start, end, endPiece);
							}
						}
					}
				}
			}
		}

		Collections.sort(moves, new Comparator<Move>() {
			@Override
			public int compare(Move move1, Move move2) {
				return move2.moveValue - move1.moveValue;
			}
		});
		System.out.println(moves.toString());

		PickMove(moves);

	}

	private void PickMove(ArrayList<Move> moves) {
		
		Random random = new Random();

		if (moves.size() >= 2) {
			Move m = moves.get(random.nextInt(2));
			chessGame.run(new Coord(m.xpos_current, m.ypos_current), new Coord(m.xpos_new, m.ypos_new), turn);
			System.out.println("Attempting to move " + new Coord(m.xpos_current, m.ypos_current) + " to "
					+ new Coord(m.xpos_new, m.ypos_new));
		} else if (moves.size() == 1) {
			Move m = moves.get(0);
			chessGame.run(new Coord(m.xpos_current, m.ypos_current), new Coord(m.xpos_new, m.ypos_new), turn);
			System.out.println("Attempting to move " + new Coord(m.xpos_current, m.ypos_current) + " to "
					+ new Coord(m.xpos_new, m.ypos_new));
		} else {
			System.out.println("No moves possible, skipping turn");
		}

	}

	@Override
	public void run() {
		AI();
	}

}