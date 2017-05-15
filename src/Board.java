import java.util.ArrayList;

/**
 * 
 * @author Amitav & Kevin 
 * Period 3
 */
/*
 * The board class represents the chess board, including the tiles and the game
 * pieces
 */
public class Board {
	private static int SIZE = 8;
	private static final int TILE_SIZE = 20;
	/**
	 * A method to get the size of the board
	 * 
	 * @return The size of the board
	 */
	public static int getSize() {
		return SIZE;
	}
	/**
	 * A method to get the size of each tile
	 * @return The model space width of each tile
	 */
	public static int getTileSize() {
		return TILE_SIZE;
	}
	private TextDisplay textDisplay;
	private Piece[][] board = new Piece[SIZE][SIZE];
	private Tile[][] boardColor = new Tile[SIZE][SIZE];

	private ArrayList<Piece> whitePieces = new ArrayList<Piece>();

	private ArrayList<Piece> blackPieces = new ArrayList<Piece>();

	/**
	 * A constructor that creates a new Board from a pre-started game
	 * 
	 * @param p
	 *            The 2D array of the pieces
	 * @param whitePieces
	 *            The ArrayList of the white pieces
	 * @param blackPieces
	 *            The ArrayList of the black pieces
	 * @precondition Piece[][] p is same size as SIZE * SIZE
	 */
	public Board(Piece[][] p, ArrayList<Piece> whitePieces, ArrayList<Piece> blackPieces) {
		for (int i = 0; i < p.length; i++) {
			for (int j = 0; j < p[0].length; j++) {
				board[i][j] = p[i][j];
			}
		}

		this.whitePieces = whitePieces;
		this.blackPieces = blackPieces;
	}

	/**
	 * A constructor that creates a new board object, initializes the board tiles and the game
	 * pieces
	 * 
	 * @param screen
	 *            The screen that this board will be drawn to
	 */
	public Board(Renderer screen) {
		textDisplay = new TextDisplay(screen.getGraphics(), screen.getWidth());
		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {
				if ((i + j) % 2 == 0) {
					boardColor[i][j] = new Tile(ModelLoader.getModel("blackSquare"),
							new Vector(SIZE - j - 1, SIZE - i - 1), true);
				} else {
					boardColor[i][j] = new Tile(ModelLoader.getModel("whiteSquare"),
							new Vector(SIZE - j - 1, SIZE - i - 1), false);
				}
			}
		}
		initBoard();
		initPieceArrayLists();

	}

	/**
	 * Checks if a given Coord is in the board
	 * 
	 * @param c
	 *            The Coord to check
	 * @return If the Coord is in the board
	 */
	private boolean checkValid(Coord c) {
		if (c.getX() < 0 || c.getX() >= SIZE || c.getY() < 0 || c.getY() >= SIZE) {
			return false;
		}
		return true;
	}

	/**
	 * Initializes the board with the correct pieces
	 */
	private void initBoard() {
		boolean color = false;
		int row = 0; // black side
		board[row][0] = new Rook(color, new Coord(0, SIZE - row - 1));
		board[row][1] = new Knight(color, new Coord(1, SIZE - row - 1));
		board[row][2] = new Bishop(color, new Coord(2, SIZE - row - 1));
		board[row][3] = new Queen(color, new Coord(3, SIZE - row - 1));
		board[row][4] = new King(color, new Coord(4, SIZE - row - 1));
		board[row][5] = new Bishop(color, new Coord(5, SIZE - row - 1));
		board[row][6] = new Knight(color, new Coord(6, SIZE - row - 1));
		board[row][7] = new Rook(color, new Coord(7, SIZE - row - 1));
		row = 1; // black pawn side
		for (int c = 0; c < board[0].length; c++) {
			board[row][c] = new Pawn(color, new Coord(c, SIZE - row - 1));
		}
		color = true; // white side
		row = 6; // white pawn side
		for (int c = 0; c < board[0].length; c++) {
			board[row][c] = new Pawn(color, new Coord(c, SIZE - row - 1));
		}
		// other white pieces
		row = 7;
		board[row][0] = new Rook(color, new Coord(0, SIZE - row - 1));
		board[row][1] = new Knight(color, new Coord(1, SIZE - row - 1));
		board[row][2] = new Bishop(color, new Coord(2, SIZE - row - 1));
		board[row][3] = new Queen(color, new Coord(3, SIZE - row - 1));
		board[row][4] = new King(color, new Coord(4, SIZE - row - 1));
		board[row][5] = new Bishop(color, new Coord(5, SIZE - row - 1));
		board[row][6] = new Knight(color, new Coord(6, SIZE - row - 1));
		board[row][7] = new Rook(color, new Coord(7, SIZE - row - 1));

	}

	/**
	 * Adds the pieces from the board to the correct array, white or black
	 */
	private void initPieceArrayLists() {
		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {
				if (board[i][j] != null) {
					Piece p = board[i][j];
					if (p.getBooleanColor()) {
						whitePieces.add(p);
					} else {
						blackPieces.add(p);
					}
				}
			}
		}
	}
	/**
	 * A getter method for the black pieces
	 * @return the black pieces as an ArrayList
	 */
	public ArrayList<Piece> getBlackPieces() {
		return blackPieces;
	}
	/**
	 * A getter method for the Board's array
	 * @return the 2D Piece array
	 */
	public Piece[][] getBoard() {
		return board;
	}
	/**
	 * A getter method for the Board's Tile array
	 * @return the 2D Tile array
	 */
	public Tile[][] getBoardColor() {
		return boardColor;
	}
	/**
	 * A method to get the TextDisplay instance variable
	 * @return the TextDisplay object
	 */
	public TextDisplay getTextDisplay() {
		return textDisplay;
	}

	/**
	 * A getter method for the white pieces
	 * @return the white pieces as an ArrayList
	 */
	public ArrayList<Piece> getWhitePieces() {
		return whitePieces;
	}
	/**
	 * A method to move a piece, checking for legality and updating instance variables 
	 * as needed, that returns a boolean.
	 * @param cStart the Coord of the piece to be moved
	 * @param cFinal the Coord to move to
	 * @return true if the piece moved, false for an invalid move
	 */
	public boolean movePiece(Coord cStart, Coord cFinal) {
		if (!checkValid(cStart)) {
			return false;
		} else if (!checkValid(cFinal)) {
			return false;
		}
		if (pieceAt(cStart) == null) {
			return false;
		}
		Piece p = pieceAt(cStart);
		if (p.getName().equalsIgnoreCase("pawn")) {
			Piece p2 = pieceAt(cFinal);
			if (p2 != null && ((Pawn) p).pawnAttack(cFinal)) {

			} else {
				if (p.legalMove(cFinal) && p2 == null) {
				} else {
					return false;
				}
			}
		} else if (!p.legalMove(cFinal)) {
			return false;
		}
		Piece p2 = pieceAt(cFinal);
		if (p2 != null && p2.getBooleanColor() == p.getBooleanColor()) {
			return false;
		}
	
		setPiece(cFinal, p);
		board[SIZE - 1 - cStart.getY()][cStart.getX()] = null;
		if (p.getClass().getName().equalsIgnoreCase("King")) {
			((King) p).updateMove();
		} else if (p.getClass().getName().equalsIgnoreCase("Rook")) {
			((Rook) p).updateMove();
		}
		return true;
	}
	/**
	 * A method to convert a Vector point symbolizing a mouse click to a actual Coord
	 * @param point the point that is clicked
	 * @return a Coord representing the point
	 */
	public Coord onClick(Vector point) {
		for (int y = 0; y < boardColor.length; y++) {
			for (int x = 0; x < boardColor[y].length; x++) {
				if (boardColor[y][x].getCollider() != null && boardColor[y][x].getCollider().testClick(point)) {
					return boardColor[y][x].onClick();
				}
			}
		}
		return null;

	}
	/**
	 * A method to get the Piece at a specific Coord in the Board
	 * @param c the Coord to access the piece at
	 * @return the Piece at Coord c or null if no piece
	 */
	public Piece pieceAt(Coord c) {
		if (!checkValid(c)) {
			return null;
		} else {
			return board[SIZE - 1 - c.getY()][c.getX()];
		}
	}
	/**
	 * A method to set a Piece a specific point in the Board 
	 * @param c the Coord of the location to be set
	 * @param p the Piece to set at Coord c
	 */
	public void setPiece(Coord c, Piece p) {
		if (!checkValid(c)) {
			return;
		}
		board[SIZE - 1 - c.getY()][c.getX()] = p;
		if (p != null) {
			p.setCoord(c);
		}
	}

}
