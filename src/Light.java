

public class Light {

	private Vector position;
	private Vector diffuseColor;
	private Vector ambientColor;
	private float ambientBrightness, diffuseBrightness;
	private Renderer depthMaper;
	private boolean doShadows = false;
	private LightColor lightColor;

	public Light(Vector position, LightColor lightColor) {
		this.lightColor = lightColor;
		this.position = position;
		this.diffuseColor = lightColor.getDiffuseColor();
		this.ambientColor = lightColor.getAmbientColor();
		this.ambientBrightness = lightColor.getAmbientBrightness();
		this.diffuseBrightness = lightColor.getDiffuseBrightness();
//		this.doShadows = doShadows;

	}

	// public void depthMapMesh(Mesh m) {
	// depthMaper.drawMesh(m, new Camera(position ));
	// }

	public float getAmbientBrightness() {
		return ambientBrightness;
	}



	public Vector getAmbientColor() {
		return ambientColor;
	}

	public Renderer getDepthMaper() {
		return depthMaper;
	}

	public float getDiffuseBrightness() {
		return diffuseBrightness;
	}

	public Vector getDiffuseColor() {
		return diffuseColor;
	}

	public Vector getPosition() {
		return position;
	}

	public boolean isDoShadows() {
		return doShadows;
	}

	public Light project(Matrix transform) {
		return new Light(position.muliply(transform), lightColor);
	}

	public void setAmbientBrightness(float ambientBrightness) {
		this.ambientBrightness = ambientBrightness;
	}

	public void setAmbientColor(Vector ambientColor) {
		this.ambientColor = ambientColor;
	}

	public void setDepthMaper(Renderer depthMaper) {
		this.depthMaper = depthMaper;
	}

	public void setDiffuseBrightness(float diffuseBrightness) {
		this.diffuseBrightness = diffuseBrightness;
	}

	public void setDiffuseColor(Vector diffuseColor) {
		this.diffuseColor = diffuseColor;
	}

	public void setDoShadows(boolean doShadows) {
		this.doShadows = doShadows;
	}

	public void setPosition(Vector position) {
		this.position = position;
	}

}
