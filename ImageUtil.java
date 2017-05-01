package com.kevinp.graphics;
import java.awt.Color;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.util.Arrays;

import javax.swing.*;



public class ImageUtil {
	
	public static BufferedImage grayScale(BufferedImage img) {
		return grayScale(img, 4);
	}
	
	public static BufferedImage grayScale(BufferedImage img, int type) {
		BufferedImage output = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_3BYTE_BGR);
		for(int x = 0; x < img.getWidth(); x++) {
			for(int y = 0; y < img.getHeight(); y++){
				
				Color c = new Color(img.getRGB(x, y));
				float avg;
				switch(type) {
				case 1: avg = c.getRed(); break;
				case 2: avg = c.getBlue(); break;
				case 3: avg = c.getGreen(); break;
				default: avg = (c.getRed() + c.getGreen() + c.getBlue())/3f;
				}
				
				//System.out.println(c.getRed() + c.getGreen() + c.getBlue());
				output.setRGB(x, y, (new Color(avg/255f, avg/255f, avg/255f).getRGB()));
			}
		}
		return output;
	}
	
	public static void showImage(BufferedImage img) {
		showImage(img, "");
	}
	
	public static void showImage(BufferedImage img, String title) {
		JFrame frame = new JFrame();
		frame.add(new JLabel(new ImageIcon(img)));
		frame.setTitle(title);
		frame.pack();
		frame.setVisible(true);
	}
	
	public static int averageColor(int r, int g, int b) {
		return (r+g+b)/3;
	}
	
	public static int averageColor(int RGB) {
		Color c = new Color(RGB);
		return averageColor(c.getRed(), c.getGreen(), c.getBlue());
	}
	
//	public static BufferedImage edgeDetection(BufferedImage img) {
//		BufferedImage redBand = singleBandEdgeDetection(grayScale(img, 1));
//		BufferedImage blueBand = singleBandEdgeDetection(grayScale(img, 2));
//		BufferedImage greenBand = singleBandEdgeDetection(grayScale(img, 3));
//		
//		BufferedImage output1 = new BufferedImage(redBand.getWidth(), redBand.getHeight(), BufferedImage.TYPE_3BYTE_BGR );
//		BufferedImage output2 = new BufferedImage(redBand.getWidth(), redBand.getHeight(), BufferedImage.TYPE_3BYTE_BGR );
//		
//		for(int x = 0; x < redBand.getWidth(); x++) {
//			for(int y=0; y< redBand.getHeight();y++) {
//				int red = redBand.getRGB(x, y) & 0xFF;
//				int blue = blueBand.getRGB(x, y) & 0xFF;
//				int green = greenBand.getRGB(x, y) & 0xFF;
//				int combined1 = (int) Math.sqrt(red * red + blue * blue + green * green);
//				int combined2 = (red + blue + green)/3;
//				//System.out.println(combined);
//				combined1 = combined1 > 255 ? 255 : combined1;
//				combined2 = combined2 > 255 ? 255 : combined2;
//				output1.setRGB(x, y, (new Color(combined1, combined1, combined1).getRGB()));
//				output2.setRGB(x, y, (new Color(combined2, combined2, combined2).getRGB()));
//			}
//		}
//		
//		showImage(rotate(img, 90), "Original");
//		//showImage(rotate(grayScale(img), 90), "GreyScale");
//		showImage(rotate(singleBandEdgeDetection(grayScale(img)), 90), "GrayScale detection");
//		//showImage(rotate(output1, 90), "Seperate Bands, sqrt combine");
//		//showImage(rotate(output2, 90), "Seperate Bands, average combine");
//
//		
//		return output1;
//	}
	
	public static void edgeTrace(float[][][] edgeData, float threshHold) {
		float[][] mag = edgeData[0];
		float[][] dir = edgeData[1];
		
		System.out.println(Arrays.toString(dir));
		
		int width = mag[0].length;
		int height = mag.length;
		
		for(int x = 0; x < width; x++) {
			for(int y =0; y < height; y++) {
				float angle = dir[x][y];
				float dx = (float) Math.cos(angle);
				dx = (-.5f < dx && dx < .5f) ? 0 : 1;
				float dy = (float) Math.sin(angle);
				dy = (-.5f < dy && dy < .5f) ? 0 : 1;
				float nextMag;
				try {
				nextMag = mag[(int)dx][(int)dy];
				} catch (IndexOutOfBoundsException e) {
					continue;
				}
				if(Math.abs(nextMag - mag[x][y]) <= threshHold) {
					mag[(int)dx][(int)dy] = mag[x][y];
				}
				
			}
		}
		
		BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
		
		for(int x = 0; x < width; x++) {
			for(int y = 0; y < height; y++) {
				int magni = (int) (mag[x][y]/3);
				img.setRGB(x, y, (new Color(magni, magni, magni).getRGB()));
			}
		}
		
		showImage(img);
		
	}
	
	public static float[][][] singleBandEdgeDetection(BufferedImage img) {
		
		int width = img.getWidth();
		int height = img.getHeight();
		
		float[][][] output = new float[img.getWidth()][img.getHeight()][2];
		
		
		Matrix mX = new Matrix(new float[][] {{ -1,  0,  1 },
	            							  { -2,  0,  2 },
	            							  { -1,  0,  1 }});
		
		Matrix mY = new Matrix(new float[][] {{ 1,  2,  1 },
											  { 0,  0,  0 },
											  { -1, -2, -1}});
		
		for(int x = 1; x < width - 1; x++) {
			for(int y = 1; y < height - 1; y++) {
				
				Matrix pixels = new Matrix(new float[][] {{ img.getRGB(x-1, y-1)& 0xFF,  img.getRGB(x+0, y+1)& 0xFF,  img.getRGB(x+1, y+1)& 0xFF },
						  								 {  img.getRGB(x-1, y+0)& 0xFF,  img.getRGB(x+0, y+0)& 0xFF,  img.getRGB(x+1, y+0)& 0xFF },
						  								 {  img.getRGB(x-1, y+1)& 0xFF,  img.getRGB(x+0, y-1)& 0xFF,  img.getRGB(x+1, y-1)& 0xFF}});
				
         
                int gradX = 0, gradY = 0;
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                    	gradX += pixels.getValue(i, j) * mX.getValue(i, j);
                    	gradY += pixels.getValue(i, j) * mY.getValue(i, j);
                    }
                }
                
                int mag = gradX + gradY;
                
//				mag = mag < 0 ? 0 : (mag > 255 ? 255 : mag);
//				mag = 255 - mag;
				
				output[x][y][0] = mag;
				output[x][y][1] = (float) Math.atan2(gradY, gradX);
				
			}
		}
		

		return output;
		
	}
	
	public static BufferedImage rotate(BufferedImage img, float degrees) {
	    AffineTransform transform = new AffineTransform();
	    transform.rotate(degrees * Math.PI / 180d, img.getWidth()/2, img.getHeight()/2);
	    AffineTransformOp op = new AffineTransformOp(transform, AffineTransformOp.TYPE_BILINEAR);
	    img = op.filter(img, null);
	    return img;
	}
	
	

}