

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Engine {

	private static final int WIDTH = 512, HEIGHT = 512;
	private static final String TITLE = "Cs Thingy yay";
	private static final String resPath = "res";

	private Board board;
	private Display display;
	private Renderer renderer;
	private Camera camera = new Camera(WIDTH, HEIGHT, 160, 320, -200, 200,
			new Matrix().rotationXMatrix(0));

	Vector ambientColor = new Vector(1, 1, 1);
	Vector diffuseColor = new Vector(1, 1, 1);
	Vector specularColor = new Vector(1, 2, 1);
	Vector lightPosition = new Vector(0, 400, 0f);
	LightColor lightColor1 = new LightColor(ambientColor, diffuseColor, .04f, 2f);
	Light light1 = new Light(lightPosition, lightColor1);

	public static void main(String[] args) {
		Engine e = new Engine(WIDTH, HEIGHT, TITLE);
		e.run();
	}

	public Engine(int width, int height, String title) {
		renderer = new Renderer(width, height);
		display = new Display(renderer, title);
		display.getDisplay().addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseClicked(MouseEvent e) {
				board.onClick(new Vector(e.getX(), e.getY()));
			}
		});
		ModelLoader.init(resPath);
		Matrix pawnMatrix = Matrix.multiply(new Matrix().scalingMatrix(.5f, 1.7f, .5f), new Matrix().translationMatrix(-30, 0, -25), new Matrix().rotationXMatrix(90));
		ModelLoader.loadModel("pawn", pawnMatrix);
		Matrix squareMatrix = new Matrix().scalingMatrix(
				Board.getTileSize() / 2, 1, Board.getTileSize());
		ModelLoader.loadModel("blackSquare", squareMatrix, false);
		ModelLoader.loadModel("whiteSquare", squareMatrix, false);
		board = new Board();
	}

	public void run() {

		// OBJModel model = new OBJModel("res/vayne");

		 renderer.addLight(light1);
		renderer.getGraphics().setColor(Color.BLACK);

		Time.init();

		Matrix trans = new Matrix().translationMatrix(0, 100, -200);
		Matrix rotX = new Matrix().rotationXMatrix(-45);
		Matrix rotY = new Matrix().rotationYMatrix(90);
		Matrix totalTransformation = Matrix.multiply(trans, rotX, rotY);
		//Vertex v1 = new Vertex(0, 0, -200).multiply(Matrix.multiply(camera.getProjectionTransform()));
		//Vertex v2 = new Vertex(40, 40, 5).multiply(Matrix.multiply(camera.getProjectionTransform(), totalTransformation));
		//System.out.println(v1.persectiveDevide());
		//System.out.println(Physics.testVector.persectiveDevide());
		while (true) {

			Time.update();

			renderer.getGraphics().drawString("FPS: " + Time.getLastFrames(),
					10, 15);



			 Mesh m = ModelLoader.getModel("pawn").getMesh(camera, totalTransformation);
			// totalTransformation);

			// renderer.drawMesh(m, camera);
			
			
			renderer.drawBoard(board, camera, totalTransformation);
			renderer.drawMesh(m, camera);
			//System.out.println(v1.persectiveDevide());
			//System.out.println(v2.persectiveDevide());
			//System.exit(0);
			//Edge e = new Edge(v2.persectiveDevide(), v1.persectiveDevide());
			//System.out.println("asd" + v2);
			//System.exit(0);
			//renderer.drawLine(v1.multiply(Matrix.multiply(camera.getCameraMatrix(), camera.getPerspectiveMatrix()))persectiveDevide(), Physics.testVector.persectiveDevide());
			
			display.swapBuffers();
			display.clear(Color.GRAY);

		}

	}

}