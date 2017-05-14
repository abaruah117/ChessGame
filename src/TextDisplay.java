import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

/**
 * 
 * @author Amitav & Kevin 
 * Period 3
 */
/**
 * A class to display text to the screen which moves and dissapears as time passes
 */
public class TextDisplay {
	
	private ArrayList<String> text;
	private ArrayList<Float> life;
	private Graphics g;
	private int width;
	/**
	 * Initializes the Graphics instance variable and the width of the display
	 * @param g the Graphics object
	 * @param width the integer width
	 */
	public TextDisplay(Graphics g, int width) {
		text = new ArrayList<String>();
		life = new ArrayList<Float>();
		this.g = g;
		this.width = width;
	}
	/**
	 * Adds a string to displayed text
	 * @param s the String to be added
	 */
	public void add(String s) {
		add(s, 10);
	}
	/**
	 * Adds a String to be displayed for a certain amount of time
	 * @param s the String to be added
	 * @param life the float representing time
	 */
	public void add(String s, float life) {
		text.add(0, s);
		this.life.add(0, life);
	}
	/**
	 * Draws the text onto the screen, and moves it down based on its life
	 */
	public void draw() {
		Color c = g.getColor();
		g.setColor(Color.red);
		for(int i = 0; i < text.size(); i++) {
			g.drawString(text.get(i), width/2 - text.get(i).length()*g.getFont().getSize()/4, (int) (80 - 7 * life.get(i)+ i * g.getFont().getSize()*2));
		}
		g.setColor(c);
	}
	/**
	 * Gets the text
	 * @return ArrayList<String> text
	 */
	public ArrayList<String> getText() {
		return text;
	}
	/**
	 * Updates the textDisplay with changes to text and the float ArrayList life (functions as a timed message)
	 */
	public void update() {
		for(int i = 0; i < life.size(); i++) {
			life.set(i, life.get(i) - Time.getDeltaTime()/1000000000f);
			if(life.get(i) < 0) {
				text.remove(i);
				life.remove(i);
			}
		}
	
	}
	
	/**
	 * Clears all texts and their timer
	 */
	public void clear() {
		life.clear();
		text.clear();
	}

}
