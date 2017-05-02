package com.kevin.graphics;

import java.awt.Color;
import java.util.ArrayList;

public class Renderer extends Canvas {

	private Canvas texture;
	private ArrayList<Light> lights;

	float[][] depthBuffer;

	private boolean isMain = true;

	public Renderer(int width, int height) {
		super(width, height);
		lights = new ArrayList<Light>();
		depthBuffer = new float[height][width];
		clearDepthBuffer();
	}

	public void drawMesh(Mesh m, Camera camera) {
		setTexture(m.getTexture());
		OBJModel model = m.getModel();

		for (int i = 0; i < model.VertexCount(); i += 3) {

			Vertex v1 = model.getVertex(i + 0).multiply(m.getTransform())
					.persectiveDevide();
			Vertex v2 = model.getVertex(i + 1).multiply(m.getTransform())
					.persectiveDevide();
			Vertex v3 = model.getVertex(i + 2).multiply(m.getTransform())
					.persectiveDevide();

			drawTriangle(v1, v2, v3, camera);

		}

	}

	public void drawTriangle(Vertex v1, Vertex v2, Vertex v3, Camera camera) {
		// System.out.println("In draw triangle");
		Vertex top = v1;
		Vertex mid = v2;
		Vertex bot = v3;
		if (bot.getPos().getY() > mid.getPos().getY()) {
			Vertex temp = bot;
			bot = mid;
			mid = temp;
		}
		if (mid.getPos().getY() > top.getPos().getY()) {
			Vertex temp = mid;
			mid = top;
			top = temp;
		}
		if (bot.getPos().getY() > mid.getPos().getY()) {
			Vertex temp = bot;
			bot = mid;
			mid = temp;
		}

		Edge e1 = new Edge(bot, mid);
		Edge e2 = new Edge(mid, top);
		Edge e3 = new Edge(bot, top);

		@SuppressWarnings("unused")
		boolean isLeftTriangle = mid.getPos().getX() < e3.getXfromEq(mid
				.getPos().getY());
		// System.out.println("finished precalc");
		// System.out.println(left);

		for (int y = (int) Math.ceil(e1.getMinY()); y < Math.ceil(e1.getMaxY()); y++) {

			if (y < 0 || y >= getHeight()) {
				continue;
			}

			int min;
			int max;
			Edge left;
			Edge right;
			if (e1.getCurrentX() < e3.getCurrentX()) {
				min = (int) Math.ceil(e1.getCurrentX());
				max = (int) Math.ceil(e3.getCurrentX());
				left = e1;
				right = e3;
			} else {
				min = (int) Math.ceil(e3.getCurrentX());
				max = (int) Math.ceil(e1.getCurrentX());
				left = e3;
				right = e1;
			}
			drawLine(bot, mid, top, left, right, min, max, y, camera);

			e1.step();
			e3.step();
		}
		// System.out.println("finished triangle p1");

		for (int y = (int) Math.ceil(e2.getMinY()); y < Math.ceil(e2.getMaxY()); y++) {

			if (y < 0 || y >= getHeight()) {
				continue;
			}
			int min;
			int max;
			Edge left;
			Edge right;
			if (e2.getCurrentX() < e3.getCurrentX()) {
				min = (int) Math.ceil(e2.getCurrentX());
				max = (int) Math.ceil(e3.getCurrentX());
				left = e2;
				right = e3;
			} else {
				min = (int) Math.ceil(e3.getCurrentX());
				max = (int) Math.ceil(e2.getCurrentX());
				left = e3;
				right = e2;
			}
			drawLine(bot, mid, top, left, right, min, max, y, camera);

			e2.step();
			e3.step();
		}
		// System.out.println("Finished triangle p2");

	}

	public void drawLine(Vertex bot, Vertex mid, Vertex top, Edge left,
			Edge right, int minX, int maxX, int y, Camera camera) {

		if (maxX - minX > getWidth()) {
			// System.out.println("SkippingX");
			return;
		}
		for (int x = (int) Math.ceil(minX); x < Math.ceil(maxX); x++) {

			if (x < 0 || x >= getWidth()) {
				continue;
			}

			float z = lerp(left.getCurrentZ(), right.getCurrentZ(), (x - minX)
					/ (maxX - minX));

			Vector position = new Vector(x, y, z);

			float distance = camera.getPos().subtract(position).length();

			if (distance < depthBuffer[y][x] || distance < camera.getNearCliping()) {
				continue;
			}

			Vector f1 = bot.getPos().subtract(position);
			Vector f2 = mid.getPos().subtract(position);
			Vector f3 = top.getPos().subtract(position);
			Vector surfaceNormal = bot.getPos().subtract(mid.getPos())
					.cross(top.getPos().subtract(mid.getPos()));
			float area = surfaceNormal.length();
			float area1 = f2.cross(f3).length() / area;
			float area2 = f3.cross(f1).length() / area;
			float area3 = f1.cross(f2).length() / area;

			Vector objectColor;
			if (bot.getTextureCord() == null || mid.getTextureCord() == null
					|| top.getTextureCord() == null || texture == null) {
				// this.drawPixel(x, y, Color.WHITE);
				objectColor = new Vector(210, 105, 30);
			} else {
				Vector textureCord = bot
						.getTextureCord()
						.multiply(area1)
						.add(mid.getTextureCord().multiply(area2)
								.add(top.getTextureCord().multiply(area3)));
				Color c = texture.getPixelAt(textureCord);
				objectColor = new Vector(c.getRed(), c.getGreen(), c.getBlue());
			}
			Vector interpolatedNormal = bot
					.getNormal()
					.multiply(area1)
					.add(mid.getNormal().multiply(area2)
							.add(top.getNormal().multiply(area3)));
			for (Light light : lights) {
				Vector ambient = light.getAmbientColor().multiply(
						light.getAmbientBrightness());
				Vector lightDir = light.getPosition().subtract(position)
						.normalize();

				Vector diffuse = new Vector(0, 0, 0);
				Vector specular = new Vector(0, 0, 0);

				float diffuseStrength = Math.max(interpolatedNormal.normalize()
						.dot(lightDir), 0);
				// System.out.println(normal.dot(lightDir));
				diffuse = light.getDiffuseColor().normalize()
						.multiply(light.getDiffuseBrightness())
						.devide(lightDir.length() * lightDir.length())
						.multiply(diffuseStrength);

				float specularStrength = (float) Math.pow(
						diffuse.devide(diffuseStrength).dot(
								camera.getPos().subtract(position)),
						light.getSpecularBrightness());
				// System.out.println(specularStrength);
				specular = light.getSpecularColor().multiply(
						Math.max(specularStrength, 0));
				objectColor = (ambient.add(diffuse.add(specular)))
						.multiply(objectColor);

			}

			this.drawPixel(x, y, objectColor);
			depthBuffer[y][x] = distance;

		}
	}

	public int clamp(float value, float max) {
		return (int) (value > max ? max : value);
	}

	public float lerp(float min, float max, float t) {
		return t * (max - min) + min;
	}

	public void clearDepthBuffer() {
		for (int y = 0; y < depthBuffer.length; y++) {
			for (int x = 0; x < depthBuffer[0].length; x++) {
				depthBuffer[y][x] = -Float.MAX_VALUE;
			}
		}

	}

	public Canvas getTexture() {
		return texture;
	}

	public void setTexture(Canvas texture) {
		this.texture = texture;
	}

	public void addLight(Light light) {
		lights.add(light);
	}

	public float[][] getDepthBuffer() {
		return depthBuffer;
	}

	public void setDepthBuffer(float[][] depthBuffer) {
		this.depthBuffer = depthBuffer;
	}

	public boolean isMain() {
		return isMain;
	}

	public void setMain(boolean isMain) {
		this.isMain = isMain;
	}

}
