
public class Board {
	private Piece[][] board = new Piece[SIZE][SIZE];
	private boolean[][] boardColor = new boolean[SIZE][SIZE];// black = false ,
																// white = true;
	private static final int SIZE = 8;

	public Board() {
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[0].length; j++) {
				if ((i + j) % 2 == 0) {
					boardColor[i][j] = true;
				}
			}
		}
		initBoard();

	}

	private String displayColor(boolean color) {
		if (color) {
			return "white";
		} else {
			return "black";
		}
	}

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

	public void displayBoard() {
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[0].length; j++) {
				if (board[i][j] == null) {
					System.out.print(displayColor(boardColor[i][j]) + " ");
				} else {
					// System.out.print(board[i][j].getColor()+"
					// "+board[i][j].getName()+", ");
					System.out.print(board[i][j].getCoord().toString() + " ");
				}
			}
			System.out.println();
		}
	}
	public void setPiece(Coord c, Piece p){
		if(!checkValid(c)){
			errorInvalidCoord();
			return null;
		}
		board[c.getY()][c.getX()] = p;
		Coord pos = p.getCoord();
		board[pos.getY()][pos.getX()] = null;
	}
	public Piece pieceAt(Coord c){
		if(!checkValid(c)){
			errorInvalidCoord();
			return null;
		}
		else{
			return board[c.getY()][c.getX()];
		}
	}

	public boolean checkValid(Coord c) {
		if (c.getX() < 0 || c.getX() > SIZE || c.getY() < 0 || c.getY() > SIZE) {
			return false;
		}
		return true;
	}

	public void errorInvalidCoord() {
		System.out.println("Your coordinate is invalid");
	}

	public static void main(String[] args) {
		Board b = new Board();
		b.displayBoard();
	}

}
