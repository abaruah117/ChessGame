

public class Vector {

	private float x, y, z, w;

	public Vector(float x, float y) {
		this(x, y, 0);
	}

	public Vector(float x, float y, float z) {
		this(x, y, z, 1);
	}

	public Vector(float x, float y, float z, float w) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
	}

	public Vector add(float r) {
		return new Vector(x + r, y + r, z + r, w + r);
	}

	public Vector add(Vector v) {
		return new Vector(x + v.getX(), y + v.getY(), z + v.getZ(), w
				+ v.getW());
	}

	public Vector copyOf() {
		return new Vector(x, y, z, w);
	}

	public Vector cross(Vector v) {
		return new Vector(y * v.z - z * v.y, z * v.x - x * v.z, x * v.y - y
				* v.x, 1);
	}

	public Vector devide(float r) {
		return new Vector(x / r, y / r, z / r, w / r);
	}

	public float dot(Vector v) {
		return x * v.x + y + v.y + z * v.z;
	}
	public Coord getCoord(){
		return new Coord((int)x,(int)y);
	}
	public float getW() {
		return w;
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public float getZ() {
		return z;
	}

	public float length() {
		return (float) Math.sqrt(x * x + y * y + z * z);
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

	public Vector multiply(float r) {
		return new Vector(x * r, y * r, z * r, w * r);
	}

	public Vector multiply(Vector v) {
		return new Vector(x * v.x, y * v.y, z * v.z);
	}

	public Vector normalize() {
		return this.devide(this.length());
	}

	public Vector scale(float x, float y, float z) {
		return new Vector(this.x * x, this.y * y, this.z * z);
	}

	public void setW(float w) {
		this.w = w;
	}

	public void setX(float x) {
		this.x = x;
	}

	public void setY(float y) {
		this.y = y;
	}

	public void setZ(float z) {
		this.z = z;
	}

	public Vector subtract(float r) {
		return add(-r);
	}

	public Vector subtract(Vector v) {
		return new Vector(x - v.getX(), y - v.getY(), z - v.getZ(), w
				- v.getW());
	}

	@Override
	public String toString() {
		return "Vector [x=" + x + ", y=" + y + ", z=" + z + ", w=" + w + "]";
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Float.floatToIntBits(w);
		result = prime * result + Float.floatToIntBits(x);
		result = prime * result + Float.floatToIntBits(y);
		result = prime * result + Float.floatToIntBits(z);
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
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

}
