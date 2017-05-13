
/**
 * 
 * @author Amitav & Kevin 
 * Period 3
 */
/*
 * This class holds information about the color and strength of a light
 */
public class LightColor {

	private Vector diffuseColor;
	private Vector ambientColor;
	private float ambientBrightness, diffuseBrightness;

	/**
	 * Creates a new Light Color
	 * @param ambientColor The color of the ambient light
	 * @param diffuseColor The color of the diffuse light
	 * @param ambientBrightness The strength of the ambient light
	 * @param diffuseBrightness The strength of the diffuse light
	 */
	public LightColor(Vector ambientColor, Vector diffuseColor, float ambientBrightness,
			float diffuseBrightness) {
		this.diffuseColor = diffuseColor;
		this.ambientColor = ambientColor;
		this.ambientBrightness = ambientBrightness;
		this.diffuseBrightness = diffuseBrightness;
	}

	/**
	 * Gets the ambient brightness
	 * @return the float ambientBrightness
	 */
	public float getAmbientBrightness() {
		return ambientBrightness;
	}

	/**
	 * Gets the ambient color
	 * @return the ambientColor Vector
	 */
	public Vector getAmbientColor() {
		return ambientColor;
	}

	/**
	 * Gets the diffuse brightness
	 * @return the diffuseBrightness float
	 */
	public float getDiffuseBrightness() {
		return diffuseBrightness;
	}

	/**
	 * Gets the diffused color
	 * @return the diffuseColor Vector
	 */
	public Vector getDiffuseColor() {
		return diffuseColor;
	}




}
