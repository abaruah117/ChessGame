package com.kevin.graphics;

import java.awt.Color;

public class Engine {

	private static final int WIDTH = 512, HEIGHT = 512;
	private static final String TITLE = "Cs Thingy yay";
	private static final String resPath = "res";

	private Display display;
	private Renderer renderer;

	Vector ambientColor = new Vector(1, 1, 1);
	Vector diffuseColor = new Vector(1, 1, 1);
	Vector specularColor = new Vector(1, 2, 1);
	Vector lightPosition = new Vector(0, -100, 0f);
	LightColor lightColor1 = new LightColor(ambientColor, diffuseColor,
			specularColor, .4f, 4f, 1);
	Light light1 = new Light(lightPosition, lightColor1);

	public static void main(String[] args) {
		Engine e = new Engine(WIDTH, HEIGHT, TITLE);
		e.run();
	}

	public Engine(int width, int height, String title) {
		renderer = new Renderer(width, height);
		display = new Display(renderer, title);
		ModelLoader.init(resPath);
		Matrix pawnMatrix = new Matrix().rotationXMatrix(90);
		ModelLoader.loadModel("pawn", pawnMatrix);
		Matrix squareMatrix = new Matrix().scalingMatrix(Board.getTileSize()/2, 1, Board.getTileSize());
		ModelLoader.loadModel("blackSquare", squareMatrix, false);
		ModelLoader.loadModel("whiteSquare", squareMatrix, false);
	}

	public void run() {

		// OBJModel model = new OBJModel("res/vayne");

		//renderer.addLight(light1);
		renderer.getGraphics().setColor(Color.BLACK);

		Camera camera = new Camera(WIDTH, HEIGHT, 160, 320, -200, 200,
				new Matrix().rotationXMatrix(0));

		Time.init();
		Board board = new Board();
		while (true) {

			Time.update();

			renderer.getGraphics().drawString("FPS: " + Time.getLastFrames(),
					10, 15);

			Matrix rotX = new Matrix()
					.rotationXMatrix(-45);

			Matrix trans = new Matrix().translationMatrix(0, 100, -200);

			Matrix totalTransformation = Matrix.multiply(trans, rotX);

			Mesh m = ModelLoader.getModel("blackSquare").getMesh(camera,
					totalTransformation);

			//renderer.drawMesh(m, camera);

			renderer.drawBoard(board, camera, totalTransformation);
			
			display.swapBuffers();
			display.clear(Color.GRAY);

		}

	}

}
