

public class Model {

	private OBJModel objModel;
	private Matrix modelMatrix;
	private String name;
	private Canvas texture;
	
	public Model(Model other) {
		this(other.getObjModel(), other.getModelMatrix(), other.getTexture(), other.name);
	}
	
	public Model(OBJModel objModel, Matrix modelMatrix, Canvas texture, String name) {
		this.objModel = objModel;
		this.modelMatrix = modelMatrix;
		this.name = name;
		this.texture = texture;
	}

	public Mesh getMesh(Camera camera, Matrix otherTransforms) {
		
		return new Mesh(objModel, camera.getProjectionTransform(), Matrix.multiply(otherTransforms, modelMatrix), texture);
	}


	public Matrix getModelMatrix() {
		return modelMatrix;
	}

	public String getName() {
		return name;
	}

	public OBJModel getObjModel() {
		return objModel;
	}

	public Canvas getTexture() {
		return texture;
	}

	public void setModelMatrix(Matrix transform) {
		this.modelMatrix = transform;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setObjModel(OBJModel objModel) {
		this.objModel = objModel;
	}

	public void setTexture(Canvas texture) {
		this.texture = texture;
	}
}
