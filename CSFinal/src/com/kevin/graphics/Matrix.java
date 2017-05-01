package com.kevin.graphics;

import javax.management.RuntimeErrorException;

public class Matrix {

	private float[][] mat;



	public Matrix() {
		mat = new float[4][4];
	}

	public Matrix(float[][] mat) {
		this.mat = mat;
	}

	public Matrix mul(Matrix other) {

		float[][] ans = new float[4][4];

		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				for (int k = 0; k < 4; k++) {
					ans[i][j] += mat[i][k] * other.mat[k][j];
				}
			}
		}

		return new Matrix(ans);
	}

	public static Matrix multiply(Matrix... mats) {
		Matrix ans = new Matrix();
		ans.identityMatrix();
		for (Matrix m : mats) {
			ans = ans.mul(m);
		}
		return ans;
	}

	public Matrix identityMatrix() {
		mat = new float[][] { { 1, 0, 0, 0 }, { 0, 1, 0, 0 }, { 0, 0, 1, 0 },
				{ 0, 0, 0, 1 } };
		return this;
	}

	public Matrix projectionMatrix(float r, float t, float n, float f) {
		mat = new float[][] { { n / r, 0, 0, 0 }, { 0, n / t, 0, 0 },
				{ 0, 0, -(f + n) / (f - n), (-2 * f * n) / (f - n) },
				{ 0, 0, -1, 1 } };
		return this;
	}

	public Matrix orthoMatrix(float w, float h, float n, float f) {
		mat = new float[][] { { 1 / w, 0, 0, 0 }, { 0, 1 / h, 0, 0 },
				{ 0, 0, -2 / (f - n), -(f + n) / (f - n) }, { 0, 0, -1, 1 } };
		return this;
	}

	public Matrix screenMatrix(float width, float height) {
		mat = new float[][] { { width / 2, 0, 0, width / 2 },
				{ 0, -height / 2, 0, height / 2 }, { 0, 0, 1, 0 },
				{ 0, 0, 0, 1 } };
		return this;
	}

	public Matrix scalingMatrix(float sx, float sy, float sz) {
		mat = new float[][] { { sx, 0, 0, 0 }, { 0, sy, 0, 0 },
				{ 0, 0, sz, 0 }, { 0, 0, 0, 1 } };
		return this;
	}

	public Matrix translationMatrix(float tx, float ty, float tz) {
		mat = new float[][] { { 1, 0, 0, tx }, { 0, 1, 0, ty },
				{ 0, 0, 1, tz }, { 0, 0, 0, 1 } };
		return this;
	}

	public Matrix rotationXMatrix(float theta) {
		float c = (float) Math.cos(Math.toRadians(theta));
		float s = (float) Math.sin(Math.toRadians(theta));
		mat = new float[][] { { 1, 0, 0, 0 }, { 0, c, -s, 0 }, { 0, -s, c, 0 },
				{ 0, 0, 0, 1 } };
		return this;
	}

	public Matrix rotationYMatrix(float theta) {
		float c = (float) Math.cos(Math.toRadians(theta));
		float s = (float) Math.sin(Math.toRadians(theta));
		mat = new float[][] { { c, 0, s, 0 }, { 0, 1, 0, 0 }, { -s, 0, c, 0 },
				{ 0, 0, 0, 1 } };
		return this;
	}

	public Matrix rotationZMatrix(float theta) {
		float c = (float) Math.cos(Math.toRadians(theta));
		float s = (float) Math.sin(Math.toRadians(theta));
		mat = new float[][] { { c, -s, 0, 0 }, { s, c, 0, 0 }, { 0, 0, 1, 0 },
				{ 0, 0, 0, 1 } };
		return this;
	}

	public Matrix inverse() {
		float[][] a = mat;
		float s0 = a[0][0] * a[1][1] - a[1][0] * a[0][1];
		float s1 = a[0][0] * a[1][2] - a[1][0] * a[0][2];
		float s2 = a[0][0] * a[1][3] - a[1][0] * a[0][3];
		float s3 = a[0][1] * a[1][2] - a[1][1] * a[0][2];
		float s4 = a[0][1] * a[1][3] - a[1][1] * a[0][3];
		float s5 = a[0][2] * a[1][3] - a[1][2] * a[0][3];

		float c5 = a[2][2] * a[3][3] - a[3][2] * a[2][3];
		float c4 = a[2][1] * a[3][3] - a[3][1] * a[2][3];
		float c3 = a[2][1] * a[3][2] - a[3][1] * a[2][2];
		float c2 = a[2][0] * a[3][3] - a[3][0] * a[2][3];
		float c1 = a[2][0] * a[3][2] - a[3][0] * a[2][2];
		float c0 = a[2][0] * a[3][1] - a[3][0] * a[2][1];

		float det = (s0 * c5 - s1 * c4 + s2 * c3 + s3 * c2 - s4 * c1 + s5 * c0);
		if (det == 0) {
			throw new RuntimeErrorException(new Error(
					"Can not find inverse of matrix, 0 determinant"));
		}
		float invdet = 1 / det;

		float[][] b = new float[4][4];

		b[0][0] = (a[1][1] * c5 - a[1][2] * c4 + a[1][3] * c3) * invdet;
		b[0][1] = (-a[0][1] * c5 + a[0][2] * c4 - a[0][3] * c3) * invdet;
		b[0][2] = (a[3][1] * s5 - a[3][2] * s4 + a[3][3] * s3) * invdet;
		b[0][3] = (-a[2][1] * s5 + a[2][2] * s4 - a[2][3] * s3) * invdet;

		b[1][0] = (-a[1][0] * c5 + a[1][2] * c2 - a[1][3] * c1) * invdet;
		b[1][1] = (a[0][0] * c5 - a[0][2] * c2 + a[0][3] * c1) * invdet;
		b[1][2] = (-a[3][0] * s5 + a[3][2] * s2 - a[3][3] * s1) * invdet;
		b[1][3] = (a[2][0] * s5 - a[2][2] * s2 + a[2][3] * s1) * invdet;

		b[2][0] = (a[1][0] * c4 - a[1][1] * c2 + a[1][3] * c0) * invdet;
		b[2][1] = (-a[0][0] * c4 + a[0][1] * c2 - a[0][3] * c0) * invdet;
		b[2][2] = (a[3][0] * s4 - a[3][1] * s2 + a[3][3] * s0) * invdet;
		b[2][3] = (-a[2][0] * s4 + a[2][1] * s2 - a[2][3] * s0) * invdet;

		b[3][0] = (-a[1][0] * c3 + a[1][1] * c1 - a[1][2] * c0) * invdet;
		b[3][1] = (a[0][0] * c3 - a[0][1] * c1 + a[0][2] * c0) * invdet;
		b[3][2] = (-a[3][0] * s3 + a[3][1] * s1 - a[3][2] * s0) * invdet;
		b[3][3] = (a[2][0] * s3 - a[2][1] * s1 + a[2][2] * s0) * invdet;

		return new Matrix(b);
	}
	
	public Matrix transpose() {
		float[][] newMat = new float[4][4];
		for(int i = 0; i < 4; i++) {
			for(int j = 0; j < 4; j++) {
				newMat[i][j] = mat[j][i];
			}
		}
		return new Matrix(newMat);
	}

	public float[][] getMat() {
		return mat;
	}
	
}

