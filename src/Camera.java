
/**
 * 
 * @author Amitav & Kevin 
 * Period 3
 */
/*
 * The camera class combines the matricies to project the points onto the screen
 */
public class Camera {
	private Matrix cameraMatrix, invCameraMatrix;
	private Vector pos;
	private Matrix perspectiveMatrix, invPerspectiveMatrix;
	private Matrix screenMatrix, invScreenMatrix;
	private float nearCliping;
	private Matrix projectionTransform;
	private int screenWidth, screenHeight;
	
	/**
	 * A constructor that creates a new camera object
	 * @param screenWidth The width of the screen
	 * @param screenHeight The height of the screen
	 * @param right The distance from the center to the right in the world space
	 * @param top The distance from the center to the top in the world space
	 * @param nearClipping The distance from the camera to the closest point in the world where an object can be drawn
	 * @param farClipping The distance from the camera to the farthest point in the world where an object can be drawn
	 * @param transform A matrix that has the cameras position and oritentaion
	 */
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
	
	/**
	 * A method that gets the camera matrix
	 * @return The matrix that represents the cameras position and orientation
	 */
	public Matrix getCameraMatrix() {
		return cameraMatrix;
	}
	
	/**
	 * A method that gets the inverse of the camera matrix
	 * @return The inverse of the camera matrix
	 */
	public Matrix getInvCameraMatrix() {
		return invCameraMatrix;
	}

	/**
	 * A method that gets the inverse perspective matrix
	 * @return The inverse the perspective matrix, which can be used to go from normalized screen space to clipping space
	 */
	public Matrix getInvPerspectiveMatrix() {
		return invPerspectiveMatrix;
	}


	/**
	 * A method that gets the inverse of the screen matrix
	 * @return The inverse of the screen matrix
	 */
	public Matrix getInvScreenMatrix() {
		return invScreenMatrix;
	}

	/**
	 * A method that gets the distance from the camera to the near clipping plane
	 * @return The distance from the camera to the near clipping plane
	 */
	public float getNearCliping() {
		return nearCliping;
	}

	/**
	 * A method to get the perspective matrix
	 * @return The perspective matrix which goes from clipping space to normalized screen space
	 */
	public Matrix getPerspectiveMatrix() {
		return perspectiveMatrix;
	}

	/**
	 * A method to get the position vector
	 * @return A Vector - the position of the camera
	 */
	public Vector getPos() {
		return pos;
	}

	/**
	 * A method to get the projectiontransform matrix
	 * @return The total projection transform, which can go from world space to screen space
	 */
	public Matrix getProjectionTransform() {
		return projectionTransform;
	}
	
	/**
	 * A method to get the height of the screen
	 * @return The height of the screen
	 */

	public int getScreenHeight() {
		return screenHeight;
	}

	/**
	 * A method to get the screen matrix
	 * @return The screen matrix, which goes from normalized screen space to screen space
	 */
	public Matrix getScreenMatrix() {
		return screenMatrix;
	}

	/**
	 * A method to get the screen width
	 * @return The width of the screen
	 */
	public int getScreenWidth() {
		return screenWidth;
	}

	/**
	 * A method which transforms the camera by by a Matrix
	 * @param t The matrix to transform it by
	 */
	public void Transform(Matrix t) {
		invCameraMatrix = invCameraMatrix.mul(t);
		cameraMatrix = invCameraMatrix.inverse();
	}
	
	
	
	
	

}
