package com.kevin.graphics;

public class LightColor {

	private Vector diffuseColor;
	private Vector ambientColor;
	private Vector specularColor;
	private float ambientBrightness, diffuseBrightness, specularBrightness;

	public LightColor(Vector ambientColor, Vector diffuseColor,
			Vector specularColor, float ambientBrightness,
			float diffuseBrightness, float specularBrightness) {
		this.diffuseColor = diffuseColor;
		this.ambientColor = ambientColor;
		this.specularColor = specularColor;
		this.ambientBrightness = ambientBrightness;
		this.diffuseBrightness = diffuseBrightness;
		this.specularBrightness = specularBrightness;
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

	public Vector getSpecularColor() {
		return specularColor;
	}

	public void setSpecularColor(Vector specularColor) {
		this.specularColor = specularColor;
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

	public float getSpecularBrightness() {
		return specularBrightness;
	}

	public void setSpecularBrightness(float specularBrightness) {
		this.specularBrightness = specularBrightness;
	}

}
