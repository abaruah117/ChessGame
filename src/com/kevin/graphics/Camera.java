package com.kevin.graphics;

public class Camera {
	
	private Matrix cameraMatrix, invCameraMatrix;
	private Vector pos;
	private Matrix perspectiveMatrix, invPerspectiveMatrix;
	private Matrix screenMatrix, invScreenMatrix;
	private float nearCliping;
	private Matrix projectionTransform;
	private int screenWidth, screenHeight;
	
	public Camera(int screenWidth, int screenHeight, float right, float top, float nearClipping, float farClipping, Matrix transform) {
		this.nearCliping = nearClipping;
		this.screenHeight = screenHeight;
		this.screenWidth = screenWidth;
		
		pos = new Vector(0, 0, 0);
		cameraMatrix = transform.inverse();
		invCameraMatrix = transform;
		
		screenMatrix = new Matrix().screenMatrix(screenWidth, screenHeight);
		invScreenMatrix = screenMatrix.inverse();
		
		perspectiveMatrix = new Matrix().projectionMatrix(right, top, nearClipping, farClipping);
		invPerspectiveMatrix = perspectiveMatrix.inverse();
		
		this.projectionTransform = Matrix.multiply(screenMatrix, perspectiveMatrix, cameraMatrix);
	}
	
	public Vector screenToWorldPos(Vector screenCord) {
		float x = (2*screenCord.getX())/screenWidth - 1;
		float y = 1 - (2*screenCord.getY())/screenHeight;
		Vector cameraCords = new Vector(x, y, -1f, 1f).muliply(projectionTransform.inverse());
		cameraCords.setZ(-1f);
		cameraCords.setW(1f);
		return null;
	}
	
	public Vector getPos() {
		return pos;
	}

	public void Transform(Matrix t) {
		invCameraMatrix = invCameraMatrix.mul(t);
		cameraMatrix = invCameraMatrix.inverse();
	}


	public Matrix getCameraMatrix() {
		return cameraMatrix;
	}

	public Matrix getInvCameraMatrix() {
		return invCameraMatrix;
	}

	public Matrix getPerspectiveMatrix() {
		return perspectiveMatrix;
	}

	public Matrix getInvPerspectiveMatrix() {
		return invPerspectiveMatrix;
	}

	public Matrix getScreenMatrix() {
		return screenMatrix;
	}

	public float getNearCliping() {
		return nearCliping;
	}

	public int getScreenWidth() {
		return screenWidth;
	}

	public int getScreenHeight() {
		return screenHeight;
	}

	public Matrix getProjectionTransform() {
		return projectionTransform;
	}

	public Matrix getInvScreenMatrix() {
		return invScreenMatrix;
	}
	
	
	
	
	

}
