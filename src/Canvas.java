import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * 
 * @author Kevin Palani
 *The canvas class represents an image, with methods to change or get individual pixels
 */
public class Canvas {

	private BufferedImage image;
	private int width, height;
	private Graphics g;

	/**
	 * Creates a new Canvas with the given width and height
	 * @param width The width of the canvas
	 * @param height the height of the canvas
	 */
	public Canvas(int width, int height) {
		this.width = width;
		this.height = height;
		image = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
		g = image.createGraphics();
	}

	/**
	 * Loads an image from a file path, throws an exception if the file does not exist
	 * @param filePath The file path to load the image from
	 */
	public Canvas(String filePath) {
		File f = new File(filePath);
		if (!f.exists()) {
			throw new IllegalArgumentException("No file exists at "
					+ f.getAbsolutePath());
		}
		try {
			image = ImageIO.read(f);
			width = image.getWidth();
			height = image.getHeight();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Makes sure a given value is within some bounds
	 * @param value The value
	 * @param min The minimum (inclusive)
	 * @param max The maximum (inclusive)
	 * @return The original value, unless it was out of bounds, then it is rounded down to the nearest bound
	 */
	public int clamp(float value, float min, float max) {
		float maxClamp = (int) (value >= max ? max : value);

		return (int) (maxClamp <= min ? min : maxClamp);
	}

	/**
	 * Draws a pixel at a location on the canvas
	 * @param x The X cord
	 * @param y The Y cord
	 * @param c The color to draw the pixel
	 */
	public void drawPixel(int x, int y, Color c) {
		try {
			image.setRGB(x, y, c.getRGB());
		} catch (ArrayIndexOutOfBoundsException e) {

		}
	}

	/**
	 * Draws a pixel at a location on the canvas
	 * @param x The X cord
	 * @param y The Y cord
	 * @param r The Red value of the color
	 * @param g The Green value of the color
	 * @param b The Blue value of the color
	 */
	public void drawPixel(int x, int y, int r, int g, int b) {
		// System.out.println("Red: " + r + " Green: " + g + " Blue: " + b);
		try {
			image.setRGB(x, y, (new Color(clamp(r, 0, 255), clamp(g, 0, 255),
					clamp(b, 0, 255))).getRGB());
		} catch (ArrayIndexOutOfBoundsException e) {

		} catch (java.lang.IllegalArgumentException e) {

		}
	}

	/**
	 * Draws a pixel at a location on the canvas
	 * @param x The X cord
	 * @param y The Y cord
	 * @param color A vector representing the color
	 */
	public void drawPixel(int x, int y, Vector color) {
		drawPixel(x, y, (int) color.getX(), (int) color.getY(),
				(int) color.getZ());
	}

	/**
	 * @return The graphics object for the underlying Buffered Image
	 */
	public Graphics getGraphics() {
		return g;
	}

	/**
	 * @return The height of the canvas
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * @return The underlying BufferedImage
	 */
	public BufferedImage getImage() {
		return image;
	}

	/**
	 * Gets the pixel color at a specific coordinate
	 * @param u The X/U cord
	 * @param v The Y/V cord
	 * @return The color of the pixel
	 */
	public Color getPixelAt(int u, int v) {
		try {
			return new Color(image.getRGB(clamp(u, 0, width - 1),
					clamp(v, 0, height - 1)));
		} catch (Exception e) {
			System.out.println("U: " + u + " V: " + v + " size: "
					+ image.getWidth() + " x " + image.getHeight());
			throw e;
		}
	}

	/**
	 * Gets the pixel color at a specific coordinate
	 * @param textureCord A vector representing the cord
	 * @return The color of the pixel
	 */
	public Color getPixelAt(Vector textureCord) {
		Vector scaledCord = textureCord.scale(width, height, 1);
		return getPixelAt((int) scaledCord.getX(),
				height - (int) scaledCord.getY());
	}

	/**
	 * @return The Width of the canvas
	 */
	public int getWidth() {
		return width;
	}

}
