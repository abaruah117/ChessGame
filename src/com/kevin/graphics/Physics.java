package com.kevin.graphics;

public class Physics {

	public static Vertex testVector = new Vertex(new Vector(0,0,0));
	private static final Vector offset = new Vector(8, 31);
	
	public static void rayCast(Camera c, Vector point, Board b) {
		//System.out.println(point.subtract(offset));
		//System.exit(0);
		point = point.subtract(offset);
		point = point.muliply(c.getInvScreenMatrix());
		point.setZ(-1f);
		point.setW(1);
		//System.out.println(point + "\n");
		point = point.muliply(c.getInvPerspectiveMatrix());
		point.setZ(-1f);
		point.setW(0);
		point = point.muliply(c.getInvCameraMatrix());
		point = point.normalize();
		System.out.println(point);
		testVector = new Vertex(point);
		Vector normal = new Vector(0, 0, 1);
		Vector origin = new Vector(b.getTileSize() * b.getSize() /2f, 100, b.getTileSize() * b.getSize() /2f);
	}
	
}
