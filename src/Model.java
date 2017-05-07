

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


	public OBJModel getObjModel() {
		return objModel;
	}

	public void setObjModel(OBJModel objModel) {
		this.objModel = objModel;
	}

	public Matrix getModelMatrix() {
		return modelMatrix;
	}

	public void setModelMatrix(Matrix transform) {
		this.modelMatrix = transform;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Canvas getTexture() {
		return texture;
	}

	public void setTexture(Canvas texture) {
		this.texture = texture;
	}
}
