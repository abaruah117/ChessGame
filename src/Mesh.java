

public class Mesh {

	private OBJModel model;
	private Matrix transform;
	private Canvas texture;

	public Mesh(OBJModel model, Matrix projectionTransform, Matrix transform,
			Canvas texture) {
		this.model = model;
		if (transform == null) {
			transform = new Matrix().identityMatrix();
		}
		this.transform = Matrix.multiply(projectionTransform, transform);
		this.texture = texture;
		
		Matrix normalMatrix = transform.inverse().transpose();
		//System.out.println(transform + "\n");
		//System.out.println(normalMatrix + "\n");
		for (int i = 0; i < model.getVerticies().size(); i++) {
			//System.out.println("Vertex pre reset: " + model.getVertex(i).getNormal());
			model.getVertex(i).resetNormal();
			//System.out.println("Vertex after reset " + model.getVertex(i).getNormal());
			//System.out.println(model.getVertex(i).getNormal());
			
			model.getVertex(i).transformNormal(normalMatrix);
			model.getVertex(i).normalizeNormal();
			//System.out.println("Vertex after transform " + model.getVertex(i).getNormal().normalize());
			//System.out.println();
		}
		//System.exit(0);
	}

	public OBJModel getModel() {
		return model;
	}

	public void setModel(OBJModel model) {
		this.model = model;
	}

	public Matrix getTransform() {
		return transform;
	}

	public void setTransform(Matrix transform) {
		this.transform = transform;
	}

	public Canvas getTexture() {
		return texture;
	}

	public void setTexture(Canvas texture) {
		this.texture = texture;
	}

}
