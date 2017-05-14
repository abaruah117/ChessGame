/**
 * 
 * @author Amitav & Kevin 
 * Period 3
 */
/**
 * A class that functions as a boolean object with a method to invert it
 */

public class SwapBool {
	private boolean color;
	/**
	 * Initializes the boolean instance variable to a given boolean
	 * @param b the new boolean value
	 */
	public SwapBool(boolean b){
		color = b;
	}
	/**
	 * Gets the boolean value
	 * @return the boolean instance variable color
	 */
	public boolean getBool(){
		return color;
	}
	/**
	 * Inverts the color boolean value (true -> false and vice versa)
	 */
	public void swap(){
		color = !color;
	}
	/**
	 * A toString method that returns info about the boolean color
	 * @return a String that just has the boolean value
	 */
	public String toString(){
		return ""+color;
	}

}
