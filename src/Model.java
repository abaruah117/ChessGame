
/**
 * 
 * @author Kevin
 * The model class represents an model which should only be loaded from the ModelLoader class
 */
public class Model {

	private OBJModel objModel;
	private Matrix modelMatrix;
	private String name;
	private Canvas texture;
	
	/**
	 * Creates a copy of another model
	 * @param other  The other model
	 */
	public Model(Model other) {
		this(other.getObjModel(), other.getModelMatrix(), other.getTexture(), other.name);
	}
	
	/**
	 * 	Models should not be created, instead they should me loaded and accessed through the ModelLoader class <b>
	 *  Creates a model given an obj model <b>
	 *  The model Matrix must not affect vertex normals, meaning it can only be a translation, or a scale by the same factor on all axises
	 * @param objModel The OBJModel to load in this model
	 * @param modelMatrix A Matrix to transform the obj model from its file state which should not change as the game progresses and must follow the above conditions
	 * @param texture A texture if nessisary for the model, otherwise null
	 * @param name The name of the model
	 */
	public Model(OBJModel objModel, Matrix modelMatrix, Canvas texture, String name) {
		this.objModel = objModel;
		this.modelMatrix = modelMatrix;
		this.name = name;
		this.texture = texture;
	}

	/**
	 * Gets a mesh version of this model
	 * @param camera The scene camera
	 * @param otherTransforms The transformations to be applied to the model, after the model matrix. This does not need to follow the constructor conditions
	 * @return A mesh of this Model transformed by the matrix
	 */
	public Mesh getMesh(Camera camera, Matrix otherTransforms) {
		
		return new Mesh(objModel, camera.getProjectionTransform(), Matrix.multiply(otherTransforms, modelMatrix), texture);
	}

	/**
	 * 
	 * @return The model matrix of the model
	 */
	public Matrix getModelMatrix() {
		return modelMatrix;
	}

	/**
	 * 
	 * @return The name of this model
	 */
	public String getName() {
		return name;
	}

	/**
	 * 
	 * @return The objmodel of thie model
	 */
	public OBJModel getObjModel() {
		return objModel;
	}

	/**
	 * 
	 * @return The texture of this model, or null if there is no texture
	 */
	public Canvas getTexture() {
		return texture;
	}

	


	
}
