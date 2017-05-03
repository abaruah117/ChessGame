package com.kevin.graphics;

public class Camera {
	
	private Matrix transform;
	private Matrix invTransform;
	private Vector pos;
	private float nearCliping;
	private Matrix projectionTransform;
	
	public Camera(int screenWidth, int screenHeight, float right, float top, float nearClipping, float farClipping, Matrix transform) {
		this.transform = transform;
		pos = new Vector(0, 0, 0);
		pos = pos.muliply(transform);
		invTransform = transform.inverse();
		this.nearCliping = nearClipping;
		Matrix screen = new Matrix().screenMatrix(screenWidth, screenHeight);

		Matrix normalizedProjection = new Matrix().projectionMatrix(right, top, nearClipping, farClipping);

		this.projectionTransform = Matrix.multiply(invTransform, screen, normalizedProjection);
	}
	
	public Vector getPos() {
		return pos;
	}

	public void Transform(Matrix t) {
		transform = transform.mul(t);
		invTransform = transform.inverse();
	}
	
	public Matrix getTransform() {
		return transform;
	}
	public void setTransform(Matrix transform) {
		this.transform = transform;
	}
	public Matrix getInvTransform() {
		return invTransform;
	}
	public void setInvTransform(Matrix invTransform) {
		this.invTransform = invTransform;
	}

	/**
	 * @return the nearCliping
	 */
	public float getNearCliping() {
		return nearCliping;
	}

	/**
	 * @param nearCliping the nearCliping to set
	 */
	public void setNearCliping(float nearCliping) {
		this.nearCliping = nearCliping;
	}

	public Matrix getProjectionTransform() {
		return projectionTransform;
	}
	
	
	

}
