import java.util.*;
import java.util.concurrent.SynchronousQueue;
import java.io.*;

public class ChessGame {

	private ChessGamePlayers players;
	private Board gameboard = new Board();
	private ArrayList<Piece> blackRemoved = new ArrayList<Piece>();
	private ArrayList<Piece> whiteRemoved = new ArrayList<Piece>();
	private ArrayList<Piece> whitePieces = new ArrayList<Piece>();

	private ArrayList<Piece> blackPieces = new ArrayList<Piece>();

	/**
	 * 
	 * @param a
	 *            ChessGamePlayers
	 * @param b
	 *            Current Board
	 * @param c
	 *            whitePieces
	 * @param d
	 *            blackPieces
	 * @param e
	 *            whiteRemoved
	 * @param f
	 *            blackRemoved
	 */
	public ChessGame(ChessGamePlayers a, Board b, ArrayList<Piece> c, ArrayList<Piece> d, ArrayList<Piece> e,
			ArrayList<Piece> f) {
		players = a;
		gameboard = new Board(b.getBoard(), c, d);
		this.whitePieces = gameboard.getWhitePieces();
		this.blackPieces = gameboard.getBlackPieces();
		whiteRemoved = e;
		blackRemoved = f;

	}

	public ChessGame(String p1, String p2) {
		players = new ChessGamePlayers(p1, p2);
		whitePieces = gameboard.getWhitePieces();
		blackPieces = gameboard.getBlackPieces();
	}

	/**
	 * Piece p1 attacks p2
	 * 
	 * @param p1
	 * @param p2
	 */
	public Piece attack(Coord c1, Coord c2) {
		boolean valid = targets(c1, c2);
		if (valid) {
			Piece p1 = gameboard.pieceAt(c1);
			Piece p2 = gameboard.pieceAt(c2);
			if (p1.getClass().getName().equalsIgnoreCase("King")) {
				if (squareIsTargeted(!p1.getBooleanColor(), c2)) {
					return null;
				} else {
					if (p2.getBooleanColor()) {
						whiteRemoved.add(p2);
						whitePieces.remove(p2);
					} else {
						blackRemoved.add(p2);
						blackPieces.remove(p2);
					}
					gameboard.movePiece(c1, c2);
					return p2;

				}
			} else {
				if (p2.getBooleanColor()) {
					whiteRemoved.add(p2);
					whitePieces.remove(p2);
				} else {
					blackRemoved.add(p2);
					blackPieces.remove(p2);
				}
				gameboard.movePiece(c1, c2);
				return p2;
			}
		}
		return null;
	}

	/**
	 * Checks if the King of color (passed in as a parameter) is in check
	 * 
	 * @param color
	 * @return
	 */
	public boolean check(boolean color) {
		Coord c = null;
		if (color) {
			for (Piece p : whitePieces) {
				if (p.getClass().getName().equals("King")) {
					c = p.getCoord();
					break;
				}
			}
		} else {
			for (Piece p : blackPieces) {
				if (p.getClass().getName().equals("King")) {
					c = p.getCoord();
					break;
				}
			}
		}
		if (squareIsTargeted(!color, c)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Checks if the King of color (passed in as a parameter) is checkmated
	 * 
	 * @param color
	 * @return
	 */
	public boolean checkMate(boolean color) {
		Coord c = null;
		Piece king = null;
		boolean lose = false;
		if (color) {
			for (Piece p : whitePieces) {
				if (p.getClass().getName().equals("King")) {
					c = p.getCoord();
					king = p;
					break;
				}
			}
		} else {
			for (Piece p : blackPieces) {
				if (p.getClass().getName().equals("King")) {
					c = p.getCoord();
					king = p;
					break;
				}
			}
		}
		if (check(color)) {
			lose = true;
			System.out.println("KING: " + c.toString());
			ArrayList<Coord> surr = Coord.surrounding(c);
			for (Coord d : surr) {
//				System.out.println("surrounding coordinate: " + d.toString());
//				System.out.println("free square: " + d.toString() + " " + !squareIsTargeted(!color, d));
//				System.out.println("occupied square: " + d.toString() + " " + gameboard.pieceAt(d));
//				System.out.println("Attacking square " + d.toString() + " "
//						+ (gameboard.pieceAt(d).getBooleanColor() != king.getBooleanColor()));
				if (!squareIsTargeted(!color, d) && gameboard.pieceAt(d) == null) {
					System.out.println("king can move");
					lose = false;
					return false;
				}
				if (!squareIsTargeted(!color, d) && gameboard.pieceAt(d) != null
						&& gameboard.pieceAt(d).getBooleanColor() != king.getBooleanColor()) {
					System.out.println("king can attack");
					lose = false;
					return false;
				}
			}
			ArrayList<Piece> pieces;
			if (color) {
				pieces = whitePieces;
			} else {
				pieces = blackPieces;
			}
			for (Piece p : pieces) {
				ChessGame cg = new ChessGame(this.getChessPlayers(), this.getBoard(), this.getWhitePieces(),
						this.getBlackPieces(), this.getWhiteRemoved(), this.getBlackRemoved());
				Coord pos = p.getCoord();
				ArrayList<Coord> possible = new ArrayList<Coord>();
				for (int i = 0; i < Board.getSize(); i++) {
					for (int j = 0; j < Board.getSize(); j++) {
						Coord newPos = new Coord(i, j);
						possible.add(newPos);
					}
				}
				for (Coord d : possible) {
					cg.movePiece(pos, d);
					if (!cg.check(color)) {
						return false;
					}
				}
			}
		}

		return lose;
	}

	public void displayGame() {
		System.out.println(
				"GAME: " + players.getWhitePlayerName() + "(WHITE) vs " + players.getBlackPlayerName() + "(BLACK)");
		gameboard.displayBoard();
	}

	public ArrayList<Piece> getBlackPieces() {
		return this.blackPieces;
	}

	public ArrayList<Piece> getBlackRemoved() {
		return this.blackRemoved;
	}

	public Board getBoard() {
		return gameboard;
	}

	/**
	 * 
	 * @return ChessGamePlayers object
	 */
	public ChessGamePlayers getChessPlayers() {
		return players;
	}

	public Piece getPiece(Coord c) {
		return gameboard.pieceAt(c);
	}

	public String getPlayers() {
		return players.toString();
	}

	public ArrayList<Piece> getWhitePieces() {
		return this.whitePieces;
	}

	public ArrayList<Piece> getWhiteRemoved() {
		return this.whiteRemoved;
	}

	public boolean movePiece(Coord cStart, Coord cFinal) {
		if (!obstruct(cStart, cFinal)) {
			return gameboard.movePiece(cStart, cFinal);
		} else {
			return false;
		}

	}

	/**
	 * Precondition: Piece p1 exists at Coord c1
	 * 
	 * @param c1
	 * @param c2
	 * @return
	 */
	public boolean obstruct(Coord c1, Coord c2) {
		if (gameboard.pieceAt(c1) == null) {
			return false;
		}
		Piece p1 = gameboard.pieceAt(c1);
		if (p1.getClass().getName().equalsIgnoreCase("Knight")) {
			return false;
		}
		int x1 = c1.getX();
		int x2 = c2.getX();
		int y1 = c1.getY();
		int y2 = c2.getY();
		int incX = 0;
		int incY = 0;
		if (x2 - x1 > 0) {
			incX = 1;
		} else if (x2 - x1 < 0) {
			incX = -1;
		}
		if (y2 - y1 > 0) {
			incY = 1;
		} else if (y2 - y1 < 0) {
			incY = -1;
		}
		for (int x = x1 + incX, y = y1 + incY; x != x2 || y != y2; x += incX, y += incY) {
			if (y < 0) {
				break;
			}
			if (gameboard.pieceAt(new Coord(x, y)) != null) {
				return true;
			}
		}
		return false;
	}

	public void revertMove(Coord cStart, Coord cFinal, Piece p) {
		gameboard.setPiece(cStart, gameboard.pieceAt(cFinal));
		gameboard.setPiece(cFinal, p);
	}

	public void setPlayers(String p1, String p2) {
		players.changeWhitePlayerName(p1);
		players.changeBlackPlayerName(p2);
	}

	/**
	 * 
	 * @param color
	 *            the color that is being checked whether it targets the given
	 *            square
	 * @param c2
	 *            the Coord referring to the square
	 * @return
	 */
	public boolean squareIsTargeted(boolean color, Coord c2) {
		boolean targeted = false;
		if (c2 == null) {
			System.out.println("Error, null coord");
			return false;
		}
		Piece p2 = null;
		if(gameboard.pieceAt(c2)!=null){
			p2 = gameboard.pieceAt(c2);
		}
		if (color) {
			for (Piece p : whitePieces) {
				if ((p.getClass().getName().equalsIgnoreCase("Pawn") && ((Pawn) p).pawnAttack(c2))) {
					targeted = true;
					break;
				}
				if (p.legalMove(c2)&&!p.equals(p2)) {
					boolean obstruction = obstruct(p.getCoord(), c2);
					if (obstruction) {
						targeted = false;
					} else {
						targeted = true;
						break;
					}
				}
			}
		} else {
			for (Piece p : blackPieces) {
				if ((p.getClass().getName().equalsIgnoreCase("Pawn") && ((Pawn) p).pawnAttack(c2))) {
					targeted = true;
					break;
				}
				if (p.legalMove(c2)&&!p.equals(p2)) {
					boolean obstruction = obstruct(p.getCoord(), c2);
					if (obstruction) {
						targeted = false;
					} else {
						targeted = true;
						break;
					}
				}
			}
		}
		return targeted;
	}

	/**
	 * 
	 * @param color
	 *            the color to check if in stalemate
	 * @return true if it is a stalemate
	 */
	public boolean staleMate(boolean color) {
		boolean noMoves = true;
		ArrayList<Piece> pieces;
		if (color) {
			pieces = whitePieces;
		} else {
			pieces = blackPieces;
		}
		for (Piece p : pieces) {
			ChessGame cg = new ChessGame(this.getChessPlayers(), this.getBoard(), this.getWhitePieces(),
					this.getBlackPieces(), this.getWhiteRemoved(), this.getBlackRemoved());
			Coord pos = p.getCoord();
			ArrayList<Coord> possible = new ArrayList<Coord>();
			for (int i = 0; i < Board.getSize(); i++) {
				for (int j = 0; j < Board.getSize(); j++) {
					Coord newPos = new Coord(i, j);
					possible.add(newPos);
				}
			}
			for (Coord d : possible) {
				if (cg.movePiece(pos, d)) {
					if (!cg.check(color)) {
						return false;
					}
				}
			}
		}
		return !check(color) && noMoves;
	}

	public boolean targets(Coord c1, Coord c2) {
		Piece p1 = gameboard.pieceAt(c1);
		Piece p2 = gameboard.pieceAt(c2);
		if (p1 == null || p2 == null) {
			String nullPiece = "";
			if (p1 == null) {
				if (p2 == null) {
					nullPiece += "p1,p2";
				} else {
					nullPiece += "p1";
				}
			} else if (p2 == null) {
				nullPiece += "p2";
			}
			System.out.println("Piece not valid: " + nullPiece);
			return false;
		} else if (p1.getBooleanColor() == p2.getBooleanColor()) {
			System.out.println("Same color pieces, invalid");
			return false;
		}
		boolean legal = p1.legalMove(c2);
		boolean obstruction = obstruct(c1, c2);
		boolean pawnAttack = false;
		if (p1.getClass().getName().equalsIgnoreCase("Pawn")) {
			pawnAttack = ((Pawn) p1).pawnAttack(c2);
		}
		boolean targets = pawnAttack || ((!obstruction) && legal);
		return targets;
	}

	public static void main(String[] args) {
		ChessGame a = new ChessGame("rip", "rip");
		a.movePiece(new Coord(4, 1), new Coord(4, 2));
		a.movePiece(new Coord(3, 0), new Coord(6, 3));
		a.movePiece(new Coord(6, 3), new Coord(3, 6));
		a.displayGame();
		System.out.println(a.check(false));
		System.out.println(a.checkMate(false));
	}
}
