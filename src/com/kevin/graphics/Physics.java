package com.kevin.graphics;

public class Physics {

	public static Vertex testVector = new Vertex(new Vector(0,0,0));
	
	public static void rayCast(Camera c, Vector point) {
		System.out.println(point);
		float x =  point.getX();
		float y =  point.getY();
		x -= c.getScreenWidth()/2f;
		x /= c.getScreenWidth()/2f;
		y -= -c.getScreenHeight()/2f;
		y /= -c.getScreenHeight()/2f;
		y += 2;
		System.out.println("x: " + x + " y: " + y);
		testVector = new Vertex(new Vector(x, y, 0)).multiply(c.getProjectionTransform());
		System.out.println(testVector.persectiveDevide());
	}
	
}
