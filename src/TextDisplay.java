import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;


public class TextDisplay {
	
	private ArrayList<String> text;
	private ArrayList<Float> life;
	private Graphics g;
	private int width;
	
	public TextDisplay(Graphics g, int width) {
		text = new ArrayList<String>();
		life = new ArrayList<Float>();
		this.g = g;
		this.width = width;
	}
	
	public void add(String s, float life) {
		text.add(0, s);
		this.life.add(0, life);
	}
	
	public void add(String s) {
		add(s, 10);
	}

	public ArrayList<String> getText() {
		return text;
	}
	
	public void draw() {
		Color c = g.getColor();
		g.setColor(Color.red);
		for(int i = 0; i < text.size(); i++) {
			g.drawString(text.get(i), width/2 - text.get(i).length()*g.getFont().getSize()/4, (int) (80 - 7 * life.get(i)+ i * g.getFont().getSize()*2));
		}
		g.setColor(c);
	}
	
	public void update() {
		for(int i = 0; i < life.size(); i++) {
			life.set(i, life.get(i) - Time.getDeltaTime()/1000000000f);
			if(life.get(i) < 0) {
				text.remove(i);
				life.remove(i);
			}
		}
	
	}

}
