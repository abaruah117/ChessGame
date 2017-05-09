/**
 * @author Amitav Baruah
 * Period 3
 */

public class SwapBool {
	private boolean color;
	public SwapBool(boolean b){
		color = b;
	}
	public void swap(){
		color = !color;
	}
	public boolean getBool(){
		return color;
	}
	public String toString(){
		return ""+color;
	}

}
