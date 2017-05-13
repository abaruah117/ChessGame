

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 * 
 * @author Amitav & Kevin 
 * Period 3
 */
/*
 *A class that deals with the display and drawing, using Swing
 */
public class Display {

	private Canvas buffer;
	private BufferedImage screen;
	private Graphics2D screenG;
	private Graphics2D bufferG;
	private JFrame window;
	private JLabel display;
	
	/**
	 * Creates a new Display with a Canvas to draw from
	 * @param target The Image to draw from
	 * @param title The title of the Window
	 */
	public Display(Canvas target, String title) {
		buffer = target;
		screen = new BufferedImage(buffer.getWidth(), buffer.getHeight(), BufferedImage.TYPE_3BYTE_BGR);
		screenG = screen.createGraphics();
		bufferG = buffer.getImage().createGraphics();
		window = new JFrame(title);
		window.setLocationRelativeTo(null);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setSize(buffer.getWidth(), buffer.getHeight());
		//frame.setResizable(false);
		display = new JLabel(new ImageIcon(screen));
		window.add(display);
		window.pack();
		//window.setResizable(false);
		window.setVisible(true);
	}
	
	/**
	 * Clears the window and any depth buffers, must be called once per frame
	 * @param clearColor The color to clear the screen with
	 */
	public void clear(Color clearColor) {

		 Color prevColor = bufferG.getColor();
		 bufferG.setColor(clearColor);
		 bufferG.fillRect(0, 0, buffer.getWidth(), buffer.getHeight());
		 bufferG.setColor(prevColor);
		 if(buffer instanceof Renderer) {
			 ((Renderer)(buffer)).clearDepthBuffer();
		 }
	}
	
	/**
	 * Gets the display
	 * @return The JLabel that holds the image
	 */
	public JLabel getDisplay() {
		return display;
	}

	/**
	 * Gets the JFrame window
	 * @return The JFrame window
	 */
	public JFrame getWindow() {
		return window;
	}

	/**
	 * Draws the image from the buffer onto the screen, must be called once per frame
	 */
	public void swapBuffers() {
		screenG.drawImage(buffer.getImage(), 0, 0, null);
		display.repaint();
	}
	
	
}
