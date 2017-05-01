package com.kevin.graphics;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Canvas {

	private BufferedImage image;
	private int width, height;
	private Graphics g;

	public Canvas(int width, int height) {
		this.width = width;
		this.height = height;
		image = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
		g = image.createGraphics();
	}

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

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

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

	public Color getPixelAt(Vector textureCord) {
		Vector scaledCord = textureCord.scale(width, height, 1);
		return getPixelAt((int) scaledCord.getX(),
				height - (int) scaledCord.getY());
	}

	public int clamp(float value, float min, float max) {
		float maxClamp = (int) (value > max ? max : value);
		return (int) (maxClamp < min ? min : maxClamp);
	}

	public void drawPixel(int x, int y, int r, int g, int b) {
		// System.out.println("Red: " + r + " Green: " + g + " Blue: " + b);
		try {
			image.setRGB(x, y, (new Color(clamp(r, 0, 255), clamp(g, 0, 255),
					clamp(b, 0, 255))).getRGB());
		} catch (ArrayIndexOutOfBoundsException e) {

		} catch (java.lang.IllegalArgumentException e) {

		}
	}

	public void drawPixel(int x, int y, Color c) {
		try {
			image.setRGB(x, y, c.getRGB());
		} catch (ArrayIndexOutOfBoundsException e) {

		}
	}

	public void drawPixel(int x, int y, Vector color) {
		drawPixel(x, y, (int) color.getX(), (int) color.getY(),
				(int) color.getZ());
	}

	public BufferedImage getImage() {
		return image;
	}

	public Graphics getGraphics() {
		return g;
	}

}
