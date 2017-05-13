
/**
 * 
 * @author Kevin
 *	This class represents a light, which is used to calculate scene lighting
 */
public class Light {

	private Vector position;
	private Vector diffuseColor;
	private Vector ambientColor;
	private float ambientBrightness, diffuseBrightness;
	private LightColor lightColor;

	/**
	 * Creates a new light object
	 * @param position The position of the light
	 * @param lightColor The Color of the light
	 */
	public Light(Vector position, LightColor lightColor) {
		this.lightColor = lightColor;
		this.position = position;
		this.diffuseColor = lightColor.getDiffuseColor();
		this.ambientColor = lightColor.getAmbientColor();
		this.ambientBrightness = lightColor.getAmbientBrightness();
		this.diffuseBrightness = lightColor.getDiffuseBrightness();


	}


	/**
	 * The ambient brightness lights all pixels equally to represents light that hes been reflected many times
	 * @return The strength of the ambient light
	 */
	public float getAmbientBrightness() {
		return ambientBrightness;
	}


	/**
	 *Gets the ambientColor Vector
	 * @return A vector that represents the color of the ambient light
	 */
	public Vector getAmbientColor() {
		return ambientColor;
	}
	
	/**
	 * Diffuse light represents light that hits the object without being bounces off anything else
	 * @return the float diffuseBrightness
	 */
	public float getDiffuseBrightness() {
		return diffuseBrightness;
	}

	/**
	 * Gets the diffused color Vector
	 * @return A Vector that represents the color of the diffused light
	 */
	public Vector getDiffuseColor() {
		return diffuseColor;
	}

	/**
	 * Gets the light's position Vector
	 * @return The position of the light
	 */
	public Vector getPosition() {
		return position;
	}

	/**
	 * Returns a new light of the original light except moved
	 * @param transform The matrix to transform the light by
	 * @return A new light in the new position
	 */
	public Light project(Matrix transform) {
		return new Light(position.muliply(transform), lightColor);
	}

	/**
	 * Sets position to a new position
	 * @param position - the new position
	 */
	public void setPosition(Vector position) {
		this.position = position;
	}

}
