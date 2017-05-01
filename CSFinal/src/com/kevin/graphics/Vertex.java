package com.kevin.graphics;

public class Vertex {

	private Vector pos;
	private Vector textureCord;
	private Vector originalNormal;
	private Vector tempNormal;

	public Vertex(Vector pos, Vector textureCord, Vector normal) {
		this.pos = pos;
		this.textureCord = textureCord;
		this.originalNormal = normal;
		tempNormal = originalNormal.copyOf();
	}

	public Vertex(Vector pos) {
		this(pos, null, null);
	}

	public Vertex(float x, float y) {
		pos = new Vector(x, y);
	}

	public Vertex(float x, float y, float z) {
		pos = new Vector(x, y, z);
	}

	public Vertex multiply(Matrix m) {
		return new Vertex(pos.muliply(m), textureCord, tempNormal);
	}

	public Vertex persectiveDevide() {
		Vector v = pos.devide(pos.getW());
		v.setW(pos.getW());
		return new Vertex(v, textureCord, tempNormal);
	}

	public Vector getPos() {
		return pos;
	}

	public void setPos(Vector pos) {
		this.pos = pos;
	}

	@Override
	public String toString() {
		return "Vertex [pos=" + pos + ", color=" + textureCord + "]";
	}

	public Vector getTextureCord() {
		// System.out.println(this);
		return textureCord;
	}

	public Vertex setTextureCord(Vector color) {
		this.textureCord = color;
		return this;
	}

	public Vector getNormal() {
		return tempNormal;
	}

	public void setNormal(Vector normal) {
		this.tempNormal = normal;
	}

	public void addToNormal(Vector normal) {
		tempNormal = tempNormal.add(normal);
	}

	public void normalizeNormal() {
		tempNormal = tempNormal.normalize();
	}

	public Vertex transformNormal(Matrix m) {
		tempNormal = tempNormal.muliply(m);
		return this;
	}

	public void resetNormal() {
		tempNormal = originalNormal.copyOf();
	}

}
