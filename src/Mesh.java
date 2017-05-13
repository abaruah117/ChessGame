/**
 * 
 * @author Kevin
 *	The Mesh class represents an object to be drawn on screen at a particular location
 */
public class Mesh {

	private OBJModel model;
	private Matrix transform;
	private Canvas texture;

	/**
	 * Creates a new Mesh from the object, and transforms the normals accordingly
	 * @param model An OBJ model to create the mesh from
	 * @param projectionTransform The projection transform to draw the mesh
	 * @param transform The transform that represents the position/rotation/size of the mesh
	 * @param texture A texture to be drawn on the mesh, or null if none needed
	 */
	public Mesh(OBJModel model, Matrix projectionTransform, Matrix transform,
			Canvas texture) {
		this.model = model;
		if (transform == null) {
			transform = new Matrix().identityMatrix();
		}
		this.transform = Matrix.multiply(projectionTransform, transform);
		this.texture = texture;

		Matrix normalMatrix = transform.inverse().transpose();

		for (int i = 0; i < model.getVerticies().size(); i++) {

			model.getVertex(i).resetNormal();

			model.getVertex(i).transformNormal(normalMatrix);
			model.getVertex(i).normalizeNormal();

		}

	}

	/**
	 * 
	 * @return The OBJ Model of this mesh
	 */
	public OBJModel getModel() {
		return model;
	}

	/**
	 * 
	 * @return The texture of this mesh, null means no texture
	 */
	public Canvas getTexture() {
		return texture;
	}

	/**
	 * 
	 * @return The transform of the mesh
	 */
	public Matrix getTransform() {
		return transform;
	}

	/**
	 * Changes the texture of this Mesh
	 * @param texture The new texture
	 */
	public void setTexture(Canvas texture) {
		this.texture = texture;
	}

}
