
/**
 * 
 * @author Kevin Palani
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
	 * @return the diffuseColor
	 */
	public Vector getDiffuseColor() {
		return diffuseColor;
	}

	/**
	 * @return the ambientColor
	 */
	public Vector getAmbientColor() {
		return ambientColor;
	}

	/**
	 * @return the ambientBrightness
	 */
	public float getAmbientBrightness() {
		return ambientBrightness;
	}

	/**
	 * @return the diffuseBrightness
	 */
	public float getDiffuseBrightness() {
		return diffuseBrightness;
	}




}
