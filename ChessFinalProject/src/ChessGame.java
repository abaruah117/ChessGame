import java.util.*;
import java.io.*;

public class ChessGame {
	private ChessGamePlayers players;
	private Board gameboard = new Board();
	private ArrayList<Piece> blackRemoved = new ArrayList<Piece>();
	private ArrayList<Piece> whiteRemoved = new ArrayList<Piece>();
	private ArrayList<Piece> whitePieces =new ArrayList<Piece>();
	private ArrayList<Piece> blackPieces  = new ArrayList<Piece>();
	public ChessGame(String p1, String p2) {
		players = new ChessGamePlayers(p1, p2);
		whitePieces = gameboard.getWhitePieces();
		blackPieces = gameboard.getBlackPieces();
	}
	/**
	 * 
	 * @param a ChessGamePlayers
	 * @param b Current Board
	 * @param c whitePieces
	 * @param d blackPieces
	 * @param e whiteRemoved
	 * @param f blackRemoved
	 */
	public ChessGame(ChessGamePlayers a, Board b, ArrayList<Piece> c, ArrayList<Piece> d, ArrayList<Piece> e, ArrayList<Piece> f){
		players = a;
		gameboard = new Board(b.getBoard(),c,d);
		whiteRemoved = e;
		blackRemoved = f;
		
	}

	public void setPlayers(String p1, String p2) {
		players.changeWhitePlayerName(p1);
		players.changeBlackPlayerName(p2);
	}

	public String getPlayers() {
		return players.toString();
	}
	/**
	 * 
	 * @return ChessGamePlayers object
	 */
	public ChessGamePlayers getChessPlayers(){
		return players;
	}

	public void displayGame() {
		System.out.println("GAME: "+players.getWhitePlayerName()+"(WHITE) vs "+players.getBlackPlayerName()+"(BLACK)");
		gameboard.displayBoard();
	}
	public boolean movePiece(Coord cStart, Coord cFinal){
		return gameboard.movePiece(cStart,cFinal);
	}
	public Piece getPiece(Coord c){
		return gameboard.pieceAt(c);
	}
	/**
	 * 
	 * @param color the color that is being checked whether it targets the given square
	 * @param c2 the Coord referring to the square
	 * @return
	 */
	public boolean squareIsTargeted(boolean color, Coord c2){
		boolean targeted = false;
		if(c2==null){
			System.out.println("Error, null coord");
			return false;
		}
		if(color){
			for(Piece p: whitePieces){
				if((p.getClass().getName().equalsIgnoreCase("Pawn")&& ((Pawn)p).pawnAttack(c2))){
					targeted = true;
					break;
				}
				if(p.legalMove(c2)){
					boolean obstruction = false;
					int x1 = p.getCoord().getX();
					int x2 = c2.getX();
					int y1 = p.getCoord().getY();
					int y2 = c2.getY();
					for (int x = x1, y = y1; x < x2
							|| y < y2; x += (x2 - x) * (1 / Math.abs(x2 - x)), y += (y2 - y) * (1 / Math.abs(y2 - y))) {
						if (gameboard.pieceAt(new Coord(x, y)) != null) {
							obstruction = true;
							break;
						}
					}
					if(obstruction){
						targeted = false;
					}
					else{
						targeted = true;
						break;
					}
				}
			}
		}
		return targeted;
	}
	/**
	 * Checks if the King of color (passed in as a parameter) is checked
	 * @param color
	 * @return
	 */
	public boolean check(boolean color){
		Coord c = null;
		boolean lose = false;
		if(color){
			for(Piece p: whitePieces){
				if(p.getClass().getName().equals("King")){
					c = p.getCoord();
					break;
				}
			}
		}
		else{
			for(Piece p: blackPieces){
				if(p.getClass().getName().equals("King")){
					c = p.getCoord();
					break;
				}
			}
		}
		if(squareIsTargeted(!color, c)){
			return true;
		}
		else{
			return false;
		}
	}
	/**
	 * Checks if the King of color (passed in as a parameter) is checkmated 
	 * @param color
	 * @return
	 */
	public boolean checkMate(boolean color){
		Coord c = null;
		boolean lose = false;
		if(color){
			for(Piece p: whitePieces){
				if(p.getClass().getName().equals("King")){
					c = p.getCoord();
					break;
				}
			}
		}
		else{
			for(Piece p: blackPieces){
				if(p.getClass().getName().equals("King")){
					c = p.getCoord();
					break;
				}
			}
		}
		if(squareIsTargeted(!color, c)){
			lose = true; 
			System.out.println("KING TARGETED: "+!color + " "+c.toString());
			ArrayList<Coord> surr = Coord.surrounding(c);
			for(Coord d: surr){
				if(!squareIsTargeted(!color, d)){
					lose = false;
				}
			}
			lose = !lose;
		}
		return lose;
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
		boolean obstruction = false;
		boolean pawnAttack = false;
		boolean knight = false;
		if(p1.getClass().getName().equalsIgnoreCase("Knight")){
			knight = true;
		}
		if (p1.getClass().getName().equalsIgnoreCase("Pawn")) {
			pawnAttack = ((Pawn) p1).pawnAttack(c2);
		} else {
			int x1 = c1.getX();
			int x2 = c2.getX();
			int y1 = c1.getY();
			int y2 = c2.getY();
			for (int x = x1, y = y1; x < x2
					|| y < y2; x += (x2 - x) * (1 / Math.abs(x2 - x)), y += (y2 - y) * (1 / Math.abs(y2 - y))) {
				if (gameboard.pieceAt(new Coord(x, y)) != null) {
					obstruction = true;
					break;
				}
			}
		}
		boolean targets = pawnAttack || ((!obstruction||knight )&& legal);
		return targets;
	}

	/**
	 * Piece p1 attacks p2
	 * 
	 * @param p1
	 * @param p2
	 */
	public boolean attack(Coord c1, Coord c2) {
		boolean valid = targets(c1, c2);
		if (valid) {
			Piece p1 = gameboard.pieceAt(c1);
			Piece p2 = gameboard.pieceAt(c2);
			if (p2.getBooleanColor()) {
				whiteRemoved.add(p2);
				whitePieces.remove(p2);
			} else {
				blackRemoved.add(p2);
				blackPieces.remove(p2);
			}
			gameboard.movePiece(c1,c2);
		}
		return valid;
	}
	public ArrayList<Piece> getBlackPieces(){
		return this.blackPieces;
	}
	public ArrayList<Piece> getWhitePieces(){
		return this.whitePieces;
	}
	public ArrayList<Piece> getWhiteRemoved(){
		return this.whiteRemoved;
	}
	public ArrayList<Piece> getBlackRemoved(){
		return this.blackRemoved;
	}

	public static void main(String[] args) {
		ChessGame c = new ChessGame("Ami", "Mikey");
		c.gameboard = new Board();
		c.displayGame();
		System.out.println(c.checkMate(true));
	}
}
