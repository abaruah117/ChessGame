import java.util.*;
public class ChessGamePlayers {
	private ArrayList<String> playerNames = new ArrayList<String>(2);//pos 0 = white player, pos 1 = black player
	private Iterator<String> it = playerNames.iterator();
	/**
	 * Initializes names
	 * @param p1 the white player's name
	 * @param p2 the black player's name
	 */
	public ChessGamePlayers(String p1, String p2){
		playerNames.add(p1);
		playerNames.add(p2);
	}
	public void changeBlackPlayerName(String p){
		playerNames.set(0, p);
	}
	public void changeWhitePlayerName(String p){
		playerNames.set(0, p);
	}
	public String getBlackPlayerName(){
		return playerNames.get(1);
	}
	public String getWhitePlayerName(){
		return playerNames.get(0);
	}
	public String toString(){
		String out = "White player: " + it.next() + "\nBlack player: "+it.next();
		return out;
	}
	
	

}
