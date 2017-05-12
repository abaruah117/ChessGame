
/**
 * 
 * @author Kevin
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
	 * Creates a new camera object
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
	 * 
	 * @return The matrix that represents the cameras position and orientation
	 */
	public Matrix getCameraMatrix() {
		return cameraMatrix;
	}
	
	/**
	 * 
	 * @return The inverse of the camera matrix
	 */
	public Matrix getInvCameraMatrix() {
		return invCameraMatrix;
	}

	/**
	 * 
	 * @return The inverse the perspective matrix, which can be used to go from normalized screen space to clipping space
	 */
	public Matrix getInvPerspectiveMatrix() {
		return invPerspectiveMatrix;
	}


	/**
	 * 
	 * @return The inverse of the screen matrix
	 */
	public Matrix getInvScreenMatrix() {
		return invScreenMatrix;
	}

	/**
	 * 
	 * @return The distance from the camera to the near clipping plane
	 */
	public float getNearCliping() {
		return nearCliping;
	}

	/**
	 * 
	 * @return The perspective matrix which goes from clipping space to normalized screen space
	 */
	public Matrix getPerspectiveMatrix() {
		return perspectiveMatrix;
	}

	/**
	 * 
	 * @return The position of the camera
	 */
	public Vector getPos() {
		return pos;
	}

	/**
	 * 
	 * @return The total projection transform, which can go from world space to screen space
	 */
	public Matrix getProjectionTransform() {
		return projectionTransform;
	}
	
	/**
	 * 
	 * @return The height of the screen
	 */

	public int getScreenHeight() {
		return screenHeight;
	}

	/**
	 * 
	 * @return The screen matrix, which goes from normalized screen space to screen space
	 */
	public Matrix getScreenMatrix() {
		return screenMatrix;
	}

	/**
	 * 
	 * @return The width of the screen
	 */
	public int getScreenWidth() {
		return screenWidth;
	}

	/**
	 * Transforms the camera
	 * @param t The matrix to transform it by
	 */
	public void Transform(Matrix t) {
		invCameraMatrix = invCameraMatrix.mul(t);
		cameraMatrix = invCameraMatrix.inverse();
	}
	
	
	
	
	

}
