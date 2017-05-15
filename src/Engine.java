import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;

import javax.swing.JOptionPane;

/**
 * 
 * @author Amitav & Kevin 
 * Period 3
 */
/**
 * The Engine class contains the main method and the main game loop to run the
 * game
 */
public class Engine {

	private static final int WIDTH = 512, HEIGHT = 512;
	private static final String TITLE = "3D Chess";
	private static final String resPath = "res";

	private ArrayList<Coord> selected;
	private ChessGame chessGame;
	private ChessAi AI;
	private InputManager inputManager;
	private Board board;
	private Display display;
	private boolean AIisRunning = false;

	private Renderer renderer;
	private Camera camera = new Camera(WIDTH, HEIGHT, 160, 320, -200, 200,
			new Matrix().identityMatrix());
	private SwapBool turn = new SwapBool(true);

	Vector ambientColor = new Vector(1, 1, 1);
	Vector diffuseColor = new Vector(1, 1, 1);

	Vector lightPosition = new Vector(0, 10, -100);

	LightColor lightColor1 = new LightColor(ambientColor, diffuseColor, .5f,
			100f);

	Light light1 = new Light(lightPosition, lightColor1);

	/**
	 * 
	 * Main method
	 */
	public static void main(String[] args) {
		Engine e = new Engine(WIDTH, HEIGHT, TITLE);
		e.run();
	}

	/**
	 * Default constructor creates a new Engine which handles the game, and
	 * loads all the models required, as well as initializes everything required
	 * 
	 * @param width
	 *            The width of the screen to be created
	 * @param height
	 *            The height of the screen to be created
	 * @param title
	 *            The title of the screen to be created
	 */
	public Engine(int width, int height, String title) {

		ModelLoader.init(resPath);
		renderer = new Renderer(width, height);
		display = new Display(renderer, title);

		Matrix pawnMatrix = Matrix.multiply(new Matrix().rotationXMatrix(0),

		new Matrix().scalingMatrix(.7f, .7f, .7f));
		ModelLoader.loadModel("Bishop", pawnMatrix, false);
		ModelLoader.loadModel("King", pawnMatrix, false);
		ModelLoader.loadModel("Knight", pawnMatrix, false);
		ModelLoader.loadModel("Pawn", pawnMatrix, false);
		ModelLoader.loadModel("Queen", pawnMatrix, false);
		ModelLoader.loadModel("Rook", pawnMatrix, false);

		ModelLoader.loadTexture("blackSquareSelected");
		ModelLoader.loadTexture("whiteSquareSelected");

		Matrix squareMatrix = Matrix.multiply(
				new Matrix().rotationYMatrix(90),
				new Matrix().scalingMatrix(Board.getTileSize() / 2, 1,
						Board.getTileSize()));
		ModelLoader.loadModel("blackSquare", squareMatrix, false);
		ModelLoader.loadModel("whiteSquare", squareMatrix, false);

		selected = new ArrayList<Coord>();
		board = new Board(renderer);

		inputManager = new InputManager(board, selected);
		display.getDisplay().addMouseListener(inputManager);
		display.getDisplay().addMouseMotionListener(inputManager);
		display.getDisplay().addMouseWheelListener(inputManager);

		String player1 = JOptionPane
				.showInputDialog("Enter the first player's name: ");
		chessGame = new ChessGame(player1, "Computer", board);
		renderer.getGraphics().setColor(Color.BLACK);
		renderer.getGraphics().setFont(new Font("Arial", 0, 20));
		AI = new ChessAi(chessGame, turn);
		Time.init();

		renderer.addLight(light1);
	}

	/**
	 * Holds the main game loop. Updates the game every frame
	 */
	public void run() {

		while (true) {
			// float boardTilt = -5 * Time.getTotalTime()/1000000000f;// Dont
			// use 45

			Matrix boardAlign = new Matrix().translationMatrix(
					Board.getTileSize() / 2, 0, Board.getTileSize() / 2f);
			Vector transVector = new Vector(10, 0, -200
					+ inputManager.getZoom());
			Matrix trans = new Matrix().translationMatrix(transVector.getX(),
					transVector.getY() + 3, transVector.getZ());

			Matrix rotX = new Matrix().rotationXMatrix(inputManager
					.getRotations().getX());
			Matrix rotY = new Matrix().rotationYMatrix(inputManager
					.getRotations().getY());
			Matrix rotZ = new Matrix().rotationZMatrix(inputManager
					.getRotations().getZ());

			Matrix boardMatrix = Matrix.multiply(trans, rotY, rotZ, rotX,
					boardAlign);

			Time.update();
			board.getTextDisplay().update();
			board.getTextDisplay().draw();

			renderer.getGraphics().drawString("FPS: " + Time.getLastFrames(),
					10, 20);
			//System.out.println(AIisRunning);
			if (selected.size() == 2 && turn.getBool()) {
				chessGame.run(selected.get(0), selected.get(1), turn);
				selected.clear();
				AI = new ChessAi(chessGame, turn);
			} else if (AIisRunning && turn.getBool()) {
				AIisRunning = false;
			} else if (!turn.getBool() && !AIisRunning) {

				System.out.println("Running AI");
				board.getTextDisplay().add("Computer turn");
				AI = new ChessAi(chessGame, turn);

				AIisRunning = true;
				AI.start();

			}

			renderer.drawBoardPieces(board, camera, transVector,
					inputManager.getRotations());
			renderer.drawBoardTiles(board, boardMatrix, camera, selected);

			display.swapBuffers();
			display.clear(Color.lightGray);

		}

	}

}
