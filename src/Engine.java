import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

public class Engine {

	private static final int WIDTH = 512 , HEIGHT = 512 ;
	private static final String TITLE = "Cs Thingy yay";
	private static final String resPath = "res";
	// private int gameState = 0; /** 0 = no select, 1 = 1 square, 2 = 2
	// squares*/
	private ArrayList<Coord> selected; // Game state now the size of the array
	private ChessGame chessGame;

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
	Vector lightPosition = new Vector(0, 10, -100); // positive X is right, +Y
													// is up,
	LightColor lightColor1 = new LightColor(ambientColor, diffuseColor, .5f,
			100f);

	Light light1 = new Light(lightPosition, lightColor1);

	public Engine(int width, int height, String title) {
		renderer = new Renderer(width, height);
		display = new Display(renderer, title);

		display.getDisplay().addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				Coord sel = board.onClick(new Vector(e.getX(), e.getY()));
				if (sel == null) {
					return;
				}
				System.out.println(sel.toString());
				if (selected.size() == 0) {
					selected.add(sel);
				} else if (selected.size() == 1) {
					if (selected.get(0).equals(sel)) {
						selected.remove(0);
					} else {
						selected.add(sel);
					}
				}

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

		new Matrix().scalingMatrix(.7f, .7f, .7f),
				new Matrix().rotationXMatrix(90));
		ModelLoader.loadModel("Bishop", pawnMatrix, false);
		ModelLoader.loadModel("King", pawnMatrix, false);
		ModelLoader.loadModel("Knight", pawnMatrix, false);
		ModelLoader.loadModel("Pawn", pawnMatrix, false);
		ModelLoader.loadModel("Queen", pawnMatrix, false);
		ModelLoader.loadModel("Rook", pawnMatrix, false);
		ModelLoader.loadModel("pawn", pawnMatrix, false);

		Matrix squareMatrix = Matrix.multiply(
				new Matrix().rotationYMatrix(90),
				new Matrix().scalingMatrix(Board.getTileSize() / 2, 1,
						Board.getTileSize()));
		ModelLoader.loadModel("blackSquare", squareMatrix, false);
		ModelLoader.loadModel("whiteSquare", squareMatrix, false);

		selected = new ArrayList<Coord>();
		board = new Board();
		// String player1 =
		// JOptionPane.showInputDialog("Enter the first player's name: ");
		// String player2 =
		// JOptionPane.showInputDialog("Enter the second player's name: ");
		chessGame = new ChessGame("asd", "asd", board);
	}

	public void run() {

		renderer.getGraphics().setColor(Color.BLACK);

		Time.init();

		Matrix trans = new Matrix().translationMatrix(10, 100, -200);
		Matrix rotX = new Matrix().rotationXMatrix(-40);
		Matrix totalTransformation = Matrix.multiply(trans, rotX);

		renderer.addLight(light1);

		SwapBool color = new SwapBool(true);
		while (true) {
			Time.update();

			renderer.getGraphics().drawString("FPS: " + Time.getLastFrames(),
					10, 15);

			if (selected.size() == 2) {
				chessGame.run(selected.get(0), selected.get(1), color);
				selected.clear();
			}
			
			renderer.drawBoard(board, camera, totalTransformation);

			display.swapBuffers();
			display.clear(Color.LIGHT_GRAY);

		}

	}

}
