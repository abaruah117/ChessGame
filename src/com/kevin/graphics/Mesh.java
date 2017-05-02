package com.kevin.graphics;

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
		// Matrix normalMatrix = transform.inverse().transpose();
		for (int i = 0; i < model.getVerticies().size(); i++) {
			model.getVertex(i).resetNormal();
			;
			model.getVertex(i).transformNormal(transform);
		}
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
