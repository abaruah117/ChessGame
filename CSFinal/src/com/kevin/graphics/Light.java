package com.kevin.graphics;

public class Light {

	private Vector position;
	private Vector diffuseColor;
	private Vector ambientColor;
	private Vector specularColor;
	private float ambientBrightness, diffuseBrightness, specularBrightness;
	private Renderer depthMaper;
	private boolean doShadows = false;

	public Light(Vector position, LightColor lightColor) {
		this.position = position;
		this.diffuseColor = lightColor.getDiffuseColor();
		this.ambientColor = lightColor.getAmbientColor();
		this.ambientBrightness = lightColor.getAmbientBrightness();
		this.diffuseBrightness = lightColor.getDiffuseBrightness();
//		this.doShadows = doShadows;
		this.specularColor = lightColor.getSpecularColor();
		this.specularBrightness = lightColor.getSpecularBrightness();

	}

	// public void depthMapMesh(Mesh m) {
	// depthMaper.drawMesh(m, new Camera(position ));
	// }

	public Vector getSpecularColor() {
		return specularColor;
	}

	public void setSpecularColor(Vector specularColor) {
		this.specularColor = specularColor;
	}

	public float getSpecularBrightness() {
		return specularBrightness;
	}

	public void setSpecularBrightness(float specularBrightness) {
		this.specularBrightness = specularBrightness;
	}

	public Vector getPosition() {
		return position;
	}

	public float getDiffuseBrightness() {
		return diffuseBrightness;
	}

	public void setDiffuseBrightness(float diffuseBrightness) {
		this.diffuseBrightness = diffuseBrightness;
	}

	public void setPosition(Vector position) {
		this.position = position;
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

	public Renderer getDepthMaper() {
		return depthMaper;
	}

	public void setDepthMaper(Renderer depthMaper) {
		this.depthMaper = depthMaper;
	}

	public boolean isDoShadows() {
		return doShadows;
	}

	public void setDoShadows(boolean doShadows) {
		this.doShadows = doShadows;
	}

}
