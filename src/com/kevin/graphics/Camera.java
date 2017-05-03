package com.kevin.graphics;

public class Camera {
	
	private Matrix transform;
	private Matrix invTransform;
	private Vector pos;
	private Matrix viewMatrix;
	private Matrix projectionMatrix;
	private Matrix 
	private float nearCliping;
	private Matrix projectionTransform;
	private int screenWidth, screenHeight;
	
	public Camera(int screenWidth, int screenHeight, float right, float top, float nearClipping, float farClipping, Matrix transform) {
		this.transform = transform;
		pos = new Vector(0, 0, 0);
		invTransform = transform.inverse();
		this.nearCliping = nearClipping;
		this.screenHeight = screenHeight;
		this.screenWidth = screenWidth;
		screenMatrix = new Matrix().screenMatrix(screenWidth, screenHeight);

		Matrix normalizedProjection = new Matrix().projectionMatrix(right, top, nearClipping, farClipping);

		this.projectionTransform = Matrix.multiply(invTransform, screenMatrix, normalizedProjection);
	}
	
	public Vector screenToWorldPos(Vector screenCord) {
		float x = (2*screenCord.getX())/screenWidth - 1;
		float y = 1 - (2*screenCord.getY())/screenHeight;
		Vector cameraCords = new Vector(x, y, -1f, 1f).muliply(projectionTransform.inverse());
		cameraCords.setZ(-1f);
		cameraCord.setW(1f);
		
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

	public int getScreenWidth() {
		return screenWidth;
	}

	public void setScreenWidth(int screenWidth) {
		this.screenWidth = screenWidth;
	}

	public int getScreenHeight() {
		return screenHeight;
	}

	public void setScreenHeight(int screenHeight) {
		this.screenHeight = screenHeight;
	}
	
	
	

}
