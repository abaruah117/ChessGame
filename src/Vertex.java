
/**
 * 
 * @author Amitav & Kevin 
 * Period 3
 */
/**
 * A class which simulates vertices (points of an object) in 3D with vertex information needed to render a scene
 *
 */
public class Vertex {

	private Vector pos;
	private Vector textureCord;
	private Vector originalNormal;
	private Vector tempNormal;
	/**
	 * Initializes the position vector in 2D
	 * @param x the x value of the Vector
	 * @param y the y value of the Vector
	 */
	public Vertex(float x, float y) {
		pos = new Vector(x, y);
	}
	/**
	 * Initializes the position vector in 3D
	 * @param x the x value of the Vector
	 * @param y the y value of the Vector
	 * @param z
	 */
	public Vertex(float x, float y, float z) {
		pos = new Vector(x, y, z);
	}
	/**
	 * Initializes the position Vector
	 * @param pos the Vector 
	 */
	public Vertex(Vector pos) {
		this(pos, null, null);
	}
	/**
	 * Initializes the Vectors position, texture, and normal, and makes a copy to store in tempNormal <b>
	 * The temp normal is used to do any transformations which would change the normal, and leaves the original normal untouched
	 * @param pos the position Vector
	 * @param textureCord the texture Vector
	 * @param normal the normal Vector
	 */
	public Vertex(Vector pos, Vector textureCord, Vector normal) {
		this.pos = pos;
		this.textureCord = textureCord;
		this.originalNormal = normal;
		tempNormal = (originalNormal == null) ? null : originalNormal.copyOf();
	}
	
	/**
	 * Adds a vector to the original normal vector
	 * @param normal the Vector to be added
	 */
	public void addToNormal(Vector normal) {
		originalNormal = originalNormal.add(normal);
	}
	
	/**
	 * Gets the temporary normal vecto
	 * @return the normal vector
	 */
	public Vector getNormal() {
		return tempNormal;
	}
	
	/**
	 * Gets the originalNormal vector
	 * @return the actual normal Vector (original)
	 */
	public Vector getOriginalNormal() {
		return this.originalNormal;
	}
	/**
	 * Gets the position Vector
	 * @return pos the position Vector
	 */
	public Vector getPos() {
		return pos;
	}
	/**
	 * Gets the texture Vector
	 * @return textureCord Vector
	 */
	public Vector getTextureCord() {
		return textureCord;
	}
	
	/**
	 * Multiplies this Vertex by a Matrix m by multiplying the position by the Matrix
	 * @param m the Matrix to multiply by
	 * @return a new Vertex with the multiplied position Vector
	 */
	public Vertex multiply(Matrix m) {
		return new Vertex(pos.muliply(m), textureCord, tempNormal);
	}
	/**
	 * Normalizes the original normal vector
	 */
	public void normalizeNormal() {
		originalNormal = originalNormal.normalize();
	}
	
	/**
	 * Perspective divides the vertex to convert it from homogeneous coordinates to normalized screen coords. <b> The W value should be one, and has no more meaingfull value
	 * @return A new Vertex 3D Vertex that has perspective correctly accounted for
	 */
	public Vertex perspectiveDevide() {
		Vector v = pos.devide(pos.getW());
		return new Vertex(v, textureCord, tempNormal);
	}
	/**
	 * Resets the tempNormal to a copy of original normal. Should be done before transforming it if pervious transformations should not affect this
	 */
	public void resetNormal() {
		tempNormal = originalNormal.copyOf();
	}
	/**
	 * Sets tempNormal to a new Vector 
	 * @param normal the new Vector
	 */
	public void setNormal(Vector normal) {
		this.tempNormal = normal;
	}
	
	/**
	 * Sets the original normal to a new Vector
	 * @param normal the new Vector
	 */
	public void setOriginalNormal(Vector normal) {
		this.originalNormal = normal;
	}
	
	/**
	 * Sets the position to a new Vector
	 * @param pos the new Vector
	 */
	public void setPos(Vector pos) {
		this.pos = pos;
	}
	
	/**
	 * Sets the textureCord vector, should be 2D and corresponds to a texture from another image
	 * @param color the new Vector to set to.
	 */
	public Vertex setTextureCord(Vector color) {
		this.textureCord = color;
		return this;
	}
	/**
	 * A toString method that returns information about the Vertex
	 * @return a String with the position and textureCord Vectors. 
	 */
	@Override
	public String toString() {
		return "Vertex [pos=" + pos + ", color=" + textureCord + "]";
	}
	
	/**
	 * Multiplies the tempNormal Vector by a Matrix. The original normal will not be touched
	 * @param m the Matrix to be multiplied by
	 * @return a Vertex with the multiplied tempNormal
	 */
	public Vertex transformNormal(Matrix m) {
		tempNormal = tempNormal.muliply(m);
		return this;
	}



}
