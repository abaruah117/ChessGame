
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

public class Engine {

	private static final int WIDTH = 512, HEIGHT = 512;
	private static final String TITLE = "Cs Thingy yay";
	private static final String resPath = "res";
	private static int gameState = 0; /** 0 = no select, 1 = 1 square, 2 = 2 squares*/

	public static void main(String[] args) {
		Engine e = new Engine(WIDTH, HEIGHT, TITLE);
		e.run();
	}
	private Board board;
	private Display display;
	private Renderer renderer;

	private Camera camera = new Camera(WIDTH, HEIGHT, 160, 320, -200, 200,
			new Matrix().rotationXMatrix(0));
	Vector ambientColor = new Vector(1, 1, 1);
	Vector diffuseColor = new Vector(1, 1, 1);
	Vector lightPosition = new Vector(0, 0, 0f);
	LightColor lightColor1 = new LightColor(ambientColor, diffuseColor, 1f,
			.5f);

	Light light1 = new Light(lightPosition, lightColor1);

	public Engine(int width, int height, String title) {
		renderer = new Renderer(width, height);
		display = new Display(renderer, title);
		
		

		
		display.getDisplay().addMouseListener(new MouseListener() {


			@Override
			public void mouseClicked(MouseEvent e) {
				board.onClick(new Vector(e.getX(), e.getY()));
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub

			}
		});
		
		ModelLoader.init(resPath);
		
		Matrix pawnMatrix = Matrix.multiply(

				new Matrix().translationMatrix(-13.5f, 100, -106),
				new Matrix().rotationXMatrix(90+44), new Matrix().scalingMatrix(3f, 3f, 3f));
		ModelLoader.loadModel("pawn", pawnMatrix, false);
		Matrix squareMatrix = new Matrix().scalingMatrix(
				Board.getTileSize() / 2, 1, Board.getTileSize());
		ModelLoader.loadModel("blackSquare", squareMatrix, false);
		ModelLoader.loadModel("whiteSquare", squareMatrix, false);
		//ModelLoader.loadModel("testSquare", new Matrix().identityMatrix(), false);
		board = new Board();
		String player1 = JOptionPane.showInputDialog("Enter the first player's name: ");
		String player2 = JOptionPane.showInputDialog("Enter the second player's name: ");
		ChessGame c = new ChessGame(player1, player2,board);
	}

	public void run() {

//		OBJModel model = new OBJModel("oldRes/crate");

		renderer.getGraphics().setColor(Color.BLACK);

		Time.init();

		Matrix trans = new Matrix().translationMatrix(10, 100, -200);
		Matrix rotX = new Matrix().rotationXMatrix(-44);
		Matrix rotY = new Matrix().rotationYMatrix(90);
		Matrix totalTransformation = Matrix.multiply(trans, rotX, rotY);
		
//		Matrix rotX2 = new Matrix().rotationXMatrix(90);
//		Matrix trans2 = new Matrix().translationMatrix(0, 100, -100);
//		Matrix scale = new Matrix().scalingMatrix(20, 10,20);
//		Matrix testTranformation = Matrix.multiply(trans, rotX2, scale);
		
		//renderer.addLight(light1);
		//Canvas text = new Canvas("oldRes/crate_1.jpg");
		Canvas text = null;
//		for(Vertex v:ModelLoader.getModel("testSquare").getMesh(camera, testTranformation).getModel().getVerticies()) {
//			System.out.println(v.getNormal());
//		}
		boolean color = true;
		while (true) {
			Time.update();

			renderer.getGraphics().drawString("FPS: " + Time.getLastFrames(),
					10, 15);

			//Mesh m = new Model(model, testTranformation, text, null).getMesh(
			//		camera, new Matrix().identityMatrix());
			Mesh m1 = ModelLoader.getModel("pawn").getMesh(camera, new Matrix().identityMatrix());

			 renderer.drawBoard(board, camera, totalTransformation);
			renderer.drawMesh(m1, camera);

			// Edge e = new Edge(v2.persectiveDevide(), v1.persectiveDevide());

			display.swapBuffers();
			display.clear(Color.GRAY);

		}
		
	}

}
