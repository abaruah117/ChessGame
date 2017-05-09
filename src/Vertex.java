

public class Vertex {

	private Vector pos;
	private Vector textureCord;
	private Vector originalNormal;
	private Vector tempNormal;
	
	public Vertex(float x, float y) {
		pos = new Vector(x, y);
	}

	public Vertex(float x, float y, float z) {
		pos = new Vector(x, y, z);
	}

	public Vertex(Vector pos) {
		this(pos, null, null);
	}

	public Vertex(Vector pos, Vector textureCord, Vector normal) {
		this.pos = pos;
		this.textureCord = textureCord;
		this.originalNormal = normal;
		tempNormal = (originalNormal == null) ? null : originalNormal.copyOf();
	}

	public void addToNormal(Vector normal) {
		originalNormal = originalNormal.add(normal);
	}

	public Vector getNormal() {
		return tempNormal;
	}

	public Vector getOriginalNormal() {
		return this.originalNormal;
	}

	public Vector getPos() {
		return pos;
	}

	public Vector getTextureCord() {
		// System.out.println(this);
		return textureCord;
	}

	public Vertex multiply(Matrix m) {
		return new Vertex(pos.muliply(m), textureCord, tempNormal);
	}

	public void normalizeNormal() {
		originalNormal = originalNormal.normalize();
	}

	public Vertex persectiveDevide() {
		Vector v = pos.devide(pos.getW());
		v.setW(pos.getW());
		return new Vertex(v, textureCord, tempNormal);
	}

	public void resetNormal() {
		tempNormal = originalNormal.copyOf();
	}

	public void setNormal(Vector normal) {
		this.tempNormal = normal;
	}
	
	public void setOriginalNormal(Vector normal) {
		this.originalNormal = normal;
	}
	public void setPos(Vector pos) {
		this.pos = pos;
	}

	public Vertex setTextureCord(Vector color) {
		this.textureCord = color;
		return this;
	}

	@Override
	public String toString() {
		return "Vertex [pos=" + pos + ", color=" + textureCord + "]";
	}

	public Vertex transformNormal(Matrix m) {
		tempNormal = tempNormal.muliply(m);
		return this;
	}



}
