
/**
 * 
 * @author Amitav & Kevin 
 * Period 3
 */
/**
 * A class to represent a Vector
 */
public class Vector {

	private float x, y, z, w;
	/**
	 * Initializes a 2D vector
	 * @param x the x value
	 * @param y the y value
	 */
	public Vector(float x, float y) {
		this(x, y, 0);
	}
	/**
	 * Initializes a 3D Vector with w  = 1
	 * @param x the x value
	 * @param y the y value
	 * @param z the z value
	 */
	public Vector(float x, float y, float z) {
		this(x, y, z, 1);
	}
	/**
	 * Initializes a 3D Vector with a new w
	 * @param x the x value
	 * @param y the y value
	 * @param z the z value
	 * @param w the w value
	 */
	public Vector(float x, float y, float z, float w) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
	}
	/**
	 * Adds a float r to all components of the Vector
	 * @param r the float to add to the Vector
	 * @return a new Vector with the components incremented by r
	 */
	public Vector add(float r) {
		return new Vector(x + r, y + r, z + r, w + r);
	}
	
	/**
	 * Adds a new Vector to this Vector
	 * @param v the Vector to add
	 * @return a new Vector that has components incremented by Vector v's respective components
	 */
	public Vector add(Vector v) {
		return new Vector(x + v.getX(), y + v.getY(), z + v.getZ(), w
				+ v.getW());
	}
	/**
	 * Copies this vector
	 * @return a new Vector with the same values
	 */
	public Vector copyOf() {
		return new Vector(x, y, z, w);
	}

	/**
	 * Cross multiples this vector by another vector
	 * @param v the Vector to cross multiply this Vector by
	 * @return a new Vector that is the product of the cross multiplication
	 */
	public Vector cross(Vector v) {
		return new Vector(y * v.z - z * v.y, z * v.x - x * v.z, x * v.y - y
				* v.x, 1);
	}
	
	/**
	 * Divides this Vector by a float 
	 * @param r the number to divide the Vector by
	 * @return a new Vector with the components divided by float
	 */
	public Vector devide(float r) {
		return new Vector(x / r, y / r, z / r, w / r);
	}
	
	/**
	 * Calculates the dot product of this and another Vector
	 * @param v the other Vector to be multiplied	
	 * @return the dot product of this and Vector v
	 */
	public float dot(Vector v) {
		return x * v.x + y + v.y + z * v.z;
	}
	
	/**
	 * Checks if this Vector equals another Object obj
	 * @param obj the object to check for equality
	 * @return if they are equal by comparing all 4 components 
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Vector other = (Vector) obj;
		if (Float.floatToIntBits(w) != Float.floatToIntBits(other.w))
			return false;
		if (Float.floatToIntBits(x) != Float.floatToIntBits(other.x))
			return false;
		if (Float.floatToIntBits(y) != Float.floatToIntBits(other.y))
			return false;
		if (Float.floatToIntBits(z) != Float.floatToIntBits(other.z))
			return false;
		return true;
	}
	
	/**
	 * Gets the Coord representing a Vector 
	 * @return the Coord representation of the x and y values
	 */
	public Coord getCoord(){
		return new Coord((int)x,(int)y);
	}
	
	/**
	 * Gets the w value
	 * @return float w
	 */
	public float getW() {
		return w;
	}
	
	/**
	 * Gets the x value, converted if x = 45
	 * @return float x, converted
	 */
	public float getX() {
		return x%45==0?x+.01f:x;
	}
	
	/**
	 * Gets the y value, converted if y = 45
	 * @return float y, converted
	 */
	public float getY() {
		return y%45==0?y+.01f:y;
	}
	
	/**
	 * Gets the z value, converted if z = 45
	 * @return float z, converted
	 */
	public float getZ() {
		return z%45==0?z+.01f:z;
	}
	
	/**
	 * Gets the length of the vector
	 * @return float length of the Vector
	 */
	public float length() {
		return (float) Math.sqrt(x * x + y * y + z * z);
	}
	
	/**
	 * @Precondition matrix has 4 rows, 4 columns or more.
	 * Multiplies this Vector by a Matrix m
	 * @param m the Matrix to multiply this Vector by
	 * @return a new Vector with multiplied components
	 */
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
	
	/**
	 * Multiplies this Vector by a float r
	 * @param r the multiplication factor
	 * @return a new Vector with multiplied components
	 */
	public Vector multiply(float r) {
		return new Vector(x * r, y * r, z * r, w * r);
	}
	
	/**
	 * Multiplies this Vector by a new Vector v 
	 * @param r the multiplication Vector to multiply this Vector by 
	 * @return a new Vector with multiplied components
	 */
	public Vector multiply(Vector v) {
		return new Vector(x * v.x, y * v.y, z * v.z);
	}
	
	/**
	 * Normalizes this vector by dividing it by the length of this vector
	 * @return a new Vector with divided components 
	 */
	public Vector normalize() {
		return this.devide(this.length());
	}
	
	/**
	 * Scales the Vector by 3 factors, for each component
	 * @param x the factor to multiply x by 
	 * @param y the factor to multiply y by 
	 * @param z the factor to multiply z by 
	 * @return a new Vector with scaled components
	 */
	public Vector scale(float x, float y, float z) {
		return new Vector(this.x * x, this.y * y, this.z * z);
	}
	
	/**
	 * Sets w to a new float number
	 * @param w the new float number for w 
	 */
	public void setW(float w) {
		this.w = w;
	}
	
	/**
	 * Sets x to a new float number
	 * @param x the new float number for x
	 */
	public void setX(float x) {
		this.x = x;
	}
	
	/**
	 * Sets y to a new float number
	 * @param y the new float number for y
	 */
	public void setY(float y) {
		this.y = y;
	}
	
	/**
	 * Sets z to a new float number
	 * @param z the new float number for z
	 */
	public void setZ(float z) {
		this.z = z;
	}
	
	/**
	 * Subtracts a float r from this Vector by adding negative r
	 * @param r the float to subtract from this Vector
	 * @return a new Vector with subtracted components
	 */
	public Vector subtract(float r) {
		return add(-r);
	}
	
	/**
	 * Subtracts a Vector from this Vector
	 * @param v the Vector to subtract from this Vector
	 * @return a new Vector that has subtracted components
	 */
	public Vector subtract(Vector v) {
		return new Vector(x - v.getX(), y - v.getY(), z - v.getZ(), w
				- v.getW());
	}

	/**
	 * A toString method that returns information about the Vector's components
	 * @return a String containing the x, y, z, and w components. 
	 */
	@Override
	public String toString() {
		return "Vector [x=" + x + ", y=" + y + ", z=" + z + ", w=" + w + "]";
	}

}
