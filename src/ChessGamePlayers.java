import java.util.*;

/**
 * 
 * @author Amitav
 *A class the hold the players names
 */
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
	
	/**
	 * Changes the name of the black side player
	 * @param p The new name
	 */
	public void changeBlackPlayerName(String p){
		playerNames.set(0, p);
	}
	
	/**
	 * Changes the name of the white side player
	 * @param p The new name
	 */
	public void changeWhitePlayerName(String p){
		playerNames.set(0, p);
	}
	
	/**
	 * Gets the name of the black sides player
	 * @return The name
	 */
	public String getBlackPlayerName(){
		return playerNames.get(1);
	}
	
	/**
	 * Gets the name of the white sides player
	 * @return The name
	 */
	public String getWhitePlayerName(){
		return playerNames.get(0);
	}
	
	/**
	 * Returns the players as a string
	 */
	public String toString(){
		String out = "White player: " + it.next() + "\nBlack player: "+it.next();
		return out;
	}
	
	

}
