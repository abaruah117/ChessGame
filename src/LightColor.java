

public class LightColor {

	private Vector diffuseColor;
	private Vector ambientColor;
	private float ambientBrightness, diffuseBrightness;

	public LightColor(Vector ambientColor, Vector diffuseColor, float ambientBrightness,
			float diffuseBrightness) {
		this.diffuseColor = diffuseColor;
		this.ambientColor = ambientColor;
		this.ambientBrightness = ambientBrightness;
		this.diffuseBrightness = diffuseBrightness;
	}

	public Vector getDiffuseColor() {
		return diffuseColor;
	}

	public void setDiffuseColor(Vector diffuseColor) {
		this.diffuseColor = diffuseColor;
	}

	public Vector getAmbientColor() {
		return ambientColor;
	}

	public void setAmbientColor(Vector ambientColor) {
		this.ambientColor = ambientColor;
	}

	public float getAmbientBrightness() {
		return ambientBrightness;
	}

	public void setAmbientBrightness(float ambientBrightness) {
		this.ambientBrightness = ambientBrightness;
	}

	public float getDiffuseBrightness() {
		return diffuseBrightness;
	}

	public void setDiffuseBrightness(float diffuseBrightness) {
		this.diffuseBrightness = diffuseBrightness;
	}


}