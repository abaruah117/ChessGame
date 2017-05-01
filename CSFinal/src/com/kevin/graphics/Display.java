package com.kevin.graphics;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Display {

	private Canvas buffer;
	private BufferedImage screen;
	private Graphics2D screenG;
	private Graphics2D bufferG;
	private JFrame display;
	
	public Display(Canvas target, String title) {
		buffer = target;
		screen = new BufferedImage(buffer.getWidth(), buffer.getHeight(), BufferedImage.TYPE_3BYTE_BGR);
		screenG = screen.createGraphics();
		bufferG = buffer.getImage().createGraphics();
		display = new JFrame(title);
		display.setLocationRelativeTo(null);
		display.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		display.setSize(buffer.getWidth(), buffer.getHeight());
		//frame.setResizable(false);
		display.add(new JLabel(new ImageIcon(screen)));
		display.pack();
		display.setVisible(true);
	}
	
	public void swapBuffers() {
		screenG.drawImage(buffer.getImage(), 0, 0, null);
		display.repaint();
	}
	
	public void clear(Color clearColor) {

		 Color prevColor = bufferG.getColor();
		 bufferG.setColor(clearColor);
		 bufferG.fillRect(0, 0, buffer.getWidth(), buffer.getHeight());
		 bufferG.setColor(prevColor);
		 if(buffer instanceof Renderer) {
			 ((Renderer)(buffer)).clearDepthBuffer();
		 }
	}
	
	
}
