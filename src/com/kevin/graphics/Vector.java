package com.kevin.graphics;

public class Vector {

	private float x, y, z, w;

	public Vector(float x, float y, float z, float w) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
	}

	public Vector(float x, float y, float z) {
		this(x, y, z, 1);
	}

	public Vector(float x, float y) {
		this(x, y, 0);
	}

	public Vector muliply(Matrix m) {
		float[][] mat = m.getMat();
		float nX = mat[0][0] * x + mat[0][1] * y + mat[0][2] * z + mat[0][3]
				* w;
		float nY = mat[1][0] * x + mat[1][1] * y + mat[1][2] * z + mat[1][3]
				* w;
		float nZ = mat[2][0] * x + mat[2][1] * y + mat[2][2] * z + mat[2][3]
				* w;
		float nW = mat[3][0] * x + mat[3][1] * y + mat[3][2] * z + mat[3][3]
				* w;
		return new Vector(nX, nY, nZ, nW);
	}

	public Vector normalize() {
		return this.devide(this.length());
	}

	public Vector scale(float x, float y, float z) {
		return new Vector(this.x * x, this.y * y, this.z * z);
	}

	public Vector multiply(float r) {
		return new Vector(x * r, y * r, z * r, w * r);
	}

	public Vector multiply(Vector v) {
		return new Vector(x * v.x, y * v.y, z * v.z);
	}

	public Vector devide(float r) {
		return new Vector(x / r, y / r, z / r, w / r);
	}

	public Vector add(float r) {
		return new Vector(x + r, y + r, z + r, w + r);
	}

	public Vector subtract(float r) {
		return add(-r);
	}

	public Vector add(Vector v) {
		return new Vector(x + v.getX(), y + v.getY(), z + v.getZ(), w
				+ v.getW());
	}

	public Vector subtract(Vector v) {
		return new Vector(x - v.getX(), y - v.getY(), z - v.getZ(), w
				- v.getW());
	}

	public Vector cross(Vector v) {
		return new Vector(y * v.z - z * v.y, z * v.x - x * v.z, x * v.y - y
				* v.x, 1);
	}

	public float dot(Vector v) {
		return x * v.x + y + v.y + z * v.z;
	}

	public float length() {
		return (float) Math.sqrt(x * x + y * y + z * z);
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public float getZ() {
		return z;
	}

	public void setZ(float z) {
		this.z = z;
	}

	public float getW() {
		return w;
	}

	public void setW(float w) {
		this.w = w;
	}

	public Vector copyOf() {
		return new Vector(x, y, z, w);
	}

	@Override
	public String toString() {
		return "Vector [x=" + x + ", y=" + y + ", z=" + z + ", w=" + w + "]";
	}

}
