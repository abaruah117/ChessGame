import java.util.*;
import java.io.*;

public class ChessGame {
	ChessGamePlayers players;
	Board gameboard = new Board();
	ArrayList<Piece> blackRemoved = new ArrayList<Piece>();
	ArrayList<Piece> whiteRemoved = new ArrayList<Piece>();

	public ChessGame(String p1, String p2) {
		players = new ChessGamePlayers(p1, p2);
	}
	public void setPlayers(String p1, String p2){
		players.changeWhitePlayerName(p1);
		players.changeBlackPlayerName(p2);
	}
	public String getPlayers(){
		return players.toString();
	}

	/**
	 * Piece p1 attacks p2
	 * 
	 * @param p1
	 * @param p2
	 */
	public boolean attack(Piece p1, Piece p2) {
		if (p1 == null || p2 == null) {
			String nullPiece = "";
			if (p1 == null) {
				if(p2==null){
					nullPiece+="p1,p2";
				}
				else{
					nullPiece+="p1";
				}
			}
			else if(p2==null){
				nullPiece+="p2"; 
			}
			System.out.println("No valid piece: "+nullPiece);
			return false;
		}
		else if(p1.getBooleanColor()==p2.getBooleanColor()){
			System.out.println("Same color pieces, attack invalid");
			return false; 
		}
		boolean legal = p1.legalMove(p2.getCoord());
		boolean obstruction = false;
		boolean pawnAttack = false;
		if (p1.getName().equalsIgnoreCase("pawn")) {
			pawnAttack = ((Pawn) p1).pawnAttack(p2);
		}
		else{
			int x1 = p1.getCoord().getX();
			int x2 = p2.getCoord().getX();
			int y1 = p1.getCoord().getY();
			int y2 = p2.getCoord().getY();
			for (int x = x1, y = y1; x < x2
					|| y < y2; x += (x2 - x) * (1 / Math.abs(x2 - x)), y += (y2 - y) * (1 / Math.abs(y2 - y))) {
				if (gameboard.pieceAt(new Coord(x, y)) != null) {
					obstruction = true;
					break;
				}
			}
		}
		boolean attackComplete = pawnAttack || (!obstruction && legal);
		if (attackComplete) {
			if(p2.getBooleanColor()){
				whiteRemoved.add(p2);
			}
			else{
				blackRemoved.add(p2);
			}
			gameboard.setPiece(p2.getCoord(), p1);
		}
		return attackComplete;
	}
	public static void main(String[] args) {
		ChessGame c= new ChessGame("Ami","Mikey");
		System.out.println(c.attack(new Pawn(true,new Coord(1,2)), new Bishop(false,new Coord(0,1))));
	}
}
