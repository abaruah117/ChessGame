package com.kevin.graphics;

import java.awt.Color;

public class Engine {

	private static final int WIDTH = 512, HEIGHT = 512;
	private static final String TITLE = "Cs Thingy yay";

	private Display display;
	private Renderer renderer;

	public static void main(String[] args) {
		Engine e = new Engine(WIDTH, HEIGHT, TITLE);
		e.run();
	}

	public Engine(int width, int height, String title) {
		renderer = new Renderer(width, height);
		display = new Display(renderer, title);
	}

	@SuppressWarnings("unused")
	public void run() {

		OBJModel model = new OBJModel("res/vayne");
		// OBJModel model = new OBJModel("res/crate");

		Vector ambientColor = new Vector(1, 1, 1);
		Vector diffuseColor = new Vector(1, 1, 1);
		Vector specularColor = new Vector(1, 2, 1);
		Vector lightPosition = new Vector(-10, 50, -50f);
		LightColor lightColor1 = new LightColor(ambientColor, diffuseColor,
				specularColor, .4f, 4f, 1);
		Light light1 = new Light(lightPosition, lightColor1);
		renderer.addLight(light1);
		renderer.getGraphics().setColor(Color.BLACK);

		Camera camera = new Camera(new Matrix().identityMatrix(), 1);

		Time.init();
		
		while (true) {

			Time.update();
			
			renderer.getGraphics().drawString("FPS: " + Time.getLastFrames(),
					10, 15);
			Matrix screen = new Matrix().screenMatrix(WIDTH, HEIGHT);
			Matrix rotX = new Matrix()
					.rotationXMatrix(Time.getTotalTime() / 50000000f);
			Matrix rotY = new Matrix()
					.rotationYMatrix(Time.getTotalTime() / 50000000f);
			Matrix proj = new Matrix().orthoMatrix(20, 20, 1f, 500f);
			Matrix proj2 = new Matrix().projectionMatrix(200, 200, -100, 100);
			Matrix rot90 = new Matrix().rotationXMatrix(90);
			Matrix trans = new Matrix().translationMatrix(0, 100, -100);

			Matrix totalProjection = Matrix.multiply(screen, proj2);
			Matrix totalTransformation = Matrix.multiply(trans, rotY, rot90);
			Matrix finalM = Matrix.multiply(totalProjection,
					totalTransformation);
			Mesh m = new Mesh(model, totalProjection, totalTransformation,
					new Canvas("res/Arclight_Vayne.png"));
			// Mesh m = new Mesh(model, finalM, new Canvas("res/crate_1.jpg"));

			renderer.drawMesh(m, camera);

			display.swapBuffers();
			display.clear(Color.GRAY);

		}

	}

}
