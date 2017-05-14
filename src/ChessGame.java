import java.util.*;
import javax.swing.JOptionPane;

/**
 * 
 * @author Amitav & Kevin 
 * Period 3
 */
/*
 * The chess game class contains all the methods to play chess 
 * Each method deals with a specific event in chess
 */
public class ChessGame {
	public static void main(String[] args) {
		System.out.println("XD");
	}
	private ChessGamePlayers players;
	private Board gameboard;
	private ArrayList<Piece> blackRemoved = new ArrayList<Piece>();

	private ArrayList<Piece> whiteRemoved = new ArrayList<Piece>();

	private ArrayList<Piece> whitePieces = new ArrayList<Piece>();

	private ArrayList<Piece> blackPieces = new ArrayList<Piece>();

	/**
	 * A constructor to initialize a ChessGame based off existing objects pertaining to the ChessGame
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

	/**
	 * Creates a new chess game
	 * @param p1 The name of player one
	 * @param p2 The name of player two
	 * @param b The board that this game is being played on
	 */
	public ChessGame(String p1, String p2, Board b) {
		players = new ChessGamePlayers(p1, p2);
		gameboard = b;
		whitePieces = gameboard.getWhitePieces();
		blackPieces = gameboard.getBlackPieces();
	}

	/**
	 * Attempts to have the piece at coord1 attack the piece at coord2 
	 * Moves the pieces if successful
	 * 
	 * @param p1 The coord of the attacker
	 * @param p2 The coord of where the attacker is attacking
	 * @return The piece that was killed, or null if nothing was killed
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
	 * 
	 * @param color
	 *            Player attempting to castle
	 * @param c
	 *            Coordinate of King's movement
	 * @return true if legal castle (and pieces moved), false if invalid castle
	 */
	public boolean castle(boolean color, Coord c) {
		if (c.getX() != 2 && c.getX() != 6) {
			return false;
		}
		int outerbound, y;
		if (c.getX() == 2) {
			outerbound = 0;
		} else {
			outerbound = 7;
		}
		if (color) {
			y = 0;
		} else {
			y = 7;
		}
		if (!(gameboard.pieceAt(new Coord(outerbound, y)) instanceof Rook)) {
			System.out.println("NO ROOK THO");
			return false;
		} else if (((Rook) (gameboard.pieceAt(new Coord(outerbound, y)))).hasMoved()) {
			System.out.println("ROOK HAS MOVED THO");
			return false;
		}
		if (!(gameboard.pieceAt(new Coord(4, y)) instanceof King)
				|| ((King) gameboard.pieceAt(new Coord(4, y))).hasMoved()
				|| ((King) gameboard.pieceAt(new Coord(4, y))).check()) {
			return false;
		}
		// POSTCONDITION: ROOK AND KING HAVENT MOVED AND KING HASNT BEEN IN
		// CHEK
		ArrayList<Coord> onTheWay = new ArrayList<Coord>();
		if (outerbound < 4) {
			for (int i = outerbound + 1; i < 4; i++) {
				onTheWay.add(new Coord(i, y));
			}
		} else {
			for (int i = 5; i < outerbound; i++) {
				onTheWay.add(new Coord(i, y));
			}
		}
		for (Coord c1 : onTheWay) {
			if (squareIsTargeted(!color, c1) || gameboard.pieceAt(c1) != null) {
				return false;
			}
		}
		Piece rook = gameboard.pieceAt(new Coord(outerbound, y));
		Piece king = gameboard.pieceAt(new Coord(4, y));
		gameboard.setPiece(new Coord(4, y), null);
		gameboard.setPiece(new Coord(outerbound, y), null);
		gameboard.setPiece(c, king);
		int newX = 4 - (c.getX() - outerbound - 1) / 2;
		System.out.println(newX + " x " + outerbound + " outer " + c.getX());
		gameboard.setPiece(new Coord(newX, y), rook);
		return true;
	}

	/**
	 * Checks if the King of color (passed in as a parameter) is in check
	 * 
	 * @param color The color of the king to check
	 * @return True if the king of that color is in check, false otherwise
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
			Piece p = gameboard.pieceAt(c);
			if (p != null) {
				((King) p).checked();
			}
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Checks if the King of color (passed in as a parameter) is checkmated or
	 * stalemated
	 * 
	 * @param color
	 * @return true if the king is checkmated or is a stalemate, false otherwise
	 */
	public boolean checkMate(boolean color) {
		Coord c = null;
		Piece king = null;
		boolean lose = false;
		boolean stale = false;
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
			ArrayList<Coord> surr = Coord.surrounding(c);
			for (Coord d : surr) {
				if (!squareIsTargeted(!color, d) && gameboard.pieceAt(d) == null) {
					lose = false;
					return false;
				}
				if (!squareIsTargeted(!color, d) && gameboard.pieceAt(d) != null
						&& gameboard.pieceAt(d).getBooleanColor() != king.getBooleanColor()) {
					lose = false;
					return false;
				}
			}
			ArrayList<Piece> pieces;
			if (!color) {
				pieces = whitePieces;
			} else {
				pieces = blackPieces;
			}
			Iterator<Piece> ab = pieces.iterator();
			ArrayList<Piece> pieceToKill = new ArrayList<Piece>();
			while (ab.hasNext()) {
				Piece p = ab.next();
				Coord pos = p.getCoord();
				if (targets(pos, c)) {
					pieceToKill.add(p);
				}
			}
			if (pieceToKill.size() > 1) {
				return true;
			}
			// Postcondition: only one piece targeting
			boolean kill = true;
			for (Piece p : pieceToKill) {
				Coord d = p.getCoord();
				if (!squareIsTargeted(!color, d)) {
					kill = false;
				}
			}
			if (!kill) {
				return false;
			}
			ArrayList<Coord> between = new ArrayList<Coord>();
			for (Piece p : pieceToKill) {
				Coord d = p.getCoord();
				between = Coord.squaresBetween(d, c);
			}
			if (color) {
				pieces = whitePieces;
			} else {
				pieces = blackPieces;
			}
			for (Piece p : pieces) {
				for (Coord d : between) {
					if (targets(p.getCoord(), d)) {
						return false;
					}
				}
			}
			return true;
		} else {
			stale = staleMate(color);
		}
		if (stale) {
			gameboard.getTextDisplay().add("GAME IS OVER, DRAW");
			return true;
		}
		return lose;
	}

	/**
	 * Gets the black pieces
	 * @return An array list containing all the black pieces
	 */ 
	public ArrayList<Piece> getBlackPieces() {
		return this.blackPieces;
	}

	/**
	 * Gets the dead/removed black pieces
	 * @return An array list containing all the dead black pieces
	 */
	public ArrayList<Piece> getBlackRemoved() {
		return this.blackRemoved;
	}

	/**
	 * Gets the ChessGame's board
	 * @return The board the game is being played on
	 */
	public Board getBoard() {
		return gameboard;
	}

	/**
	 * Gets the ChessGamePlayers
	 * @return ChessGamePlayers object
	 */
	public ChessGamePlayers getChessPlayers() {
		return players;
	}

	/**
	 * Gets the piece at a specific Coord
	 * @param c The Coord
	 * @return The piece at that Coord
	 */
	public Piece getPiece(Coord c) {
		return gameboard.pieceAt(c);
	}

	/**
	 * Get the player's names
	 * @return A string of the players names
	 */
	public String getPlayers() {
		return players.toString();
	}

	/**
	 * Get the white pieces
	 * @return An array list of all the white pieces
	 */
	public ArrayList<Piece> getWhitePieces() {
		return this.whitePieces;
	}

	/**
	 * Get the dead white pieces
	 * @return An array list of all the dead white pieces
	 */
	public ArrayList<Piece> getWhiteRemoved() {
		return this.whiteRemoved;
	}

	/**
	 * Attempts to move a piece from one spot to another, and checks for obstruction
	 * @param cStart The Coord of the Piece attempting to move
	 * @param cFinal The Coord of the spot which it is trying to move to
	 * @return true if the piece could and did move, false otherwise
	 */
	public boolean movePiece(Coord cStart, Coord cFinal) {
		if (!obstruct(cStart, cFinal)) {
			return gameboard.movePiece(cStart, cFinal);
		} else {
			return false;
		}

	}

	/**
	 * Checks to see if there is another piece between the piece at Coord one and a parameter, Coord two
	 * @param c1 The Coord at which the piece is
	 * @param c2 The Coord to check to see if there is anything in between
	 * @return If there is a piece in between the two coords
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

	/**
	 * Promotes the piece at a coord by giving the user a pop up box to choose the desired piece from
	 * @param c The coord which to try and promote
	 * @return If the promotion was successful
	 */
	public boolean promote(Coord c) {
		Piece p = gameboard.pieceAt(c);
		if (p == null) {
			return false;
		} else if (!(p instanceof Pawn)) {
			return false;
		}
		Pawn pawn = (Pawn) p;
		boolean color = pawn.getBooleanColor();
		if (!pawn.promote()) {
			return false;
		} else {// legal promotion
			Piece[] choices = { new Rook(color, c), new Knight(color, c), new Bishop(color, c), new Queen(color, c) };
			int rc = JOptionPane.showOptionDialog(null, "Choose a piece to promote your pawn to: ", "Promotion",
					JOptionPane.WARNING_MESSAGE, 0, null, choices, null);
			Piece pNew = choices[rc];
			if (color) {
				whitePieces.remove(p);
				whitePieces.add(pNew);
			} else {
				blackPieces.remove(p);
				blackPieces.add(pNew);
			}
			gameboard.setPiece(c, pNew);
			return true;
		}
	}

	/**
	 * Undoes a move
	 * @param cStart The position of the piece before the move
	 * @param cFinal The position of the piece after the move
	 * @param p The piece that was in the final spot before
 	 */
	public void revertMove(Coord cStart, Coord cFinal, Piece p) {
		gameboard.setPiece(cStart, gameboard.pieceAt(cFinal));
		gameboard.setPiece(cFinal, p);
		if(p != null) { 
			if (p.getBooleanColor()) {
				whitePieces.add(p);
			} else {
				blackPieces.add(p);
			}
		}
	}

	/**
	 * This method runs an entire turn and checks all necessary options and conditions to simulate a Chess game
	 * @param c1 The coord of the selected piece
	 * @param c2 The location where the piece is trying to go to
	 * @param color
	 *            whose turn it is
	 */
	public void run(Coord c1, Coord c2, SwapBool color) {
		if (check(color.getBool())) {
			if (checkMate(color.getBool())) {
				String b = (!color.getBool()) ? (this.getChessPlayers().getWhitePlayerName() + " wins")
						: "The computer wins";
				String[] choices = { "GG WP " + b };
				JOptionPane.showOptionDialog(null, "GAME OVER", "GameManager", JOptionPane.WARNING_MESSAGE, 0, null,
						choices, null);
				System.exit(0);
			}
			gameboard.getTextDisplay().add(((color.getBool()) ? "white" : "black") + " king is in check");
			if (getPiece(c1) == null) {
				gameboard.getTextDisplay().add("No piece to move", 5);
			} else {
				Piece p1 = getPiece(c1);
				if (p1.getBooleanColor() != color.getBool()) {
					gameboard.getTextDisplay().add("Move a piece of your color, please", 5);
				} else { 
					if (getPiece(c2) != null) {
						Piece p2 = getPiece(c2);
						if (attack(c1, c2) != null) {
							if (check(color.getBool())) {
								gameboard.getTextDisplay().add("Still in check!");
								revertMove(c1, c2, p2);
							} else {
								color.swap();
								promote(c2);
								gameboard.getTextDisplay().clear();
								gameboard.getTextDisplay().add(color.getBool() ? "Your turn" : "Computer turn");
								
							}
						}
					} else {
						if (!movePiece(c1, c2)) {
							gameboard.getTextDisplay().add("Invalid move", 5);
						} else {
							if (check(color.getBool())) {
								gameboard.getTextDisplay().add("Still in check!");
								revertMove(c1, c2, null);
							} else {
								promote(c2);
								color.swap();
								gameboard.getTextDisplay().clear();
								gameboard.getTextDisplay().add(color.getBool() ? "Your turn" : "Computer turn");
								
							}
						}
					}

				}
			}

		} else {
			if (getPiece(c1) == null) {
				gameboard.getTextDisplay().add("No piece to move");
			} else {
				Piece p = getPiece(c1);
				if (p.getBooleanColor() != color.getBool()) {
					gameboard.getTextDisplay().add("Move a piece of your color, please");
				} else {
					if (p instanceof King) {
						if (!p.legalMove(c2)) {
						} else if (!castle(color.getBool(), c2)) {
							gameboard.getTextDisplay().add("Invalid castle");
						} else {
							color.swap();
							gameboard.getTextDisplay().clear();
							gameboard.getTextDisplay().add(color.getBool() ? "Your turn" : "Computer turn");
						}
					} else if (getPiece(c2) != null) {
						Piece rek = attack(c1, c2);
						if (rek == null) {
						} else {
							if (check(color.getBool())) {
								revertMove(c1, c2, rek);
								gameboard.getTextDisplay().add("Invalid, king would be in check");
							} else {
								promote(c2);
								color.swap();
								gameboard.getTextDisplay().clear();
								gameboard.getTextDisplay().add(color.getBool() ? "Your turn" : "Computer turn");
							}
						}
					} else {
						if (!movePiece(c1, c2)) {
							gameboard.getTextDisplay().add("Invalid move");
						} else {

							if (check(color.getBool())) {
								revertMove(c1, c2, null);
							} else {
								promote(c2);
								color.swap();
								gameboard.getTextDisplay().clear();
								gameboard.getTextDisplay().add(color.getBool() ? "Your turn" : "Computer turn");
							}
							//TODO swap turn here?
						}
					}

				}

			}
		}

	}
	


	/**
	 * Changes the names of the players
	 * @param p1 The new player one name
	 * @param p2 The new player two name
	 */
	public void setPlayers(String p1, String p2) {
		players.changeWhitePlayerName(p1);
		players.changeBlackPlayerName(p2);
	}

	/**
	 * Checks if a square/position is targeted by a color (white/black)
	 * @param color
	 *            the boolean referring to a color that is being checked whether it targets the given
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
		if (gameboard.pieceAt(c2) != null) {
			p2 = gameboard.pieceAt(c2);
		}
		if (color) {
			for (Piece p : whitePieces) {
				if ((p.getClass().getName().equalsIgnoreCase("Pawn") && ((Pawn) p).pawnAttack(c2))) {
					targeted = true;
					break;
				}
				if (p.legalMove(c2) && (p2 == null || (p2 != null && !p.equals(p2)))) {
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
				if (p.legalMove(c2) && (p2 == null || (p2 != null && !p.equals(p2)))) {
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
	 * Checks for a stalemate
	 * @param color
	 *            the boolean color to check if in stalemate
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
	/**
	 * Checks if the piece at c1 targets the piece at c2
	 * @param c1 the Coord of the first Piece
	 * @param c2 the Coord of the second Piece
	 * @return boolean, if the piece at c1 targets the piece at c2 true, else false
	 */
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
		if (p1 instanceof Pawn) {
			pawnAttack = ((Pawn) p1).pawnAttack(c2);
			return pawnAttack;
		}
		boolean targets = ((!obstruction) && legal);
		return targets;
	}

}
