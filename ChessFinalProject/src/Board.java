import java.util.ArrayList;
import java.util.StringJoiner;
public class Board {
	private static final int SIZE = 8;
	private Piece[][] board = new Piece[SIZE][SIZE];

	private ArrayList<Piece> whitePieces = new ArrayList<Piece>();
	private ArrayList<Piece> blackPieces = new ArrayList<Piece>();

	private static boolean[][] boardColor = new boolean[SIZE][SIZE];// black =
																	// false ,
																	// white =
																	// true
	private String center(String s, int size){
		String out = String.format("%"+size+"s%s%"+size+"s", "",s,"");
	    float mid = (out.length()/2);
	    float start = mid - (size/2);
	    float end = start + size; 
	    return out.substring((int)start, (int)end);
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

	private String displayColor(boolean color) {
		if (color) {
			return "WHITE";
		} else {
			return "BLACK";
		}
	}

	private boolean checkValid(Coord c) {
		if (c.getX() < 0 || c.getX() >= SIZE || c.getY() < 0 || c.getY() >= SIZE) {
			return false;
		}
		return true;
	}

	private boolean checkPiece(Coord c) {
		if (board[SIZE - 1 - c.getY()][c.getX()] == null) {
			return false;
		} else {
			return true;
		}
	}

	private void errorInvalidCoord(Coord c) {
		System.out.println("Your coordinate " + c.toString() + " is invalid");
	}

	private void errorNoPiece(Coord c) {
		System.out.println("There is no piece at " + c.toString());
	}

	private void illegalMove(Piece p, Coord c) {
		System.out.println("The " + p.getName() + " at " + p.getCoord().toString() + " cannot move to " + c.toString());
	}
	
	public Board() {
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[0].length; j++) {
				if ((i + j) % 2 == 0) {
					boardColor[i][j] = true;
				}
			}
		}
		initBoard();
		initPieceArrayLists();

	}
	/**
	* @precondition Piece[][] p is same size as 8*8
	*/
	public Board(Piece[][] p, ArrayList<Piece> whitePieces, ArrayList<Piece> blackPieces){
		for(int i = 0 ; i < p.length; i++){
			for(int j = 0; j < p[0].length ; j++){
				board[i][j]= p[i][j];
			}
		}
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[0].length; j++) {
				if ((i + j) % 2 == 0) {
					boardColor[i][j] = true;
				}
			}
		}
		this.whitePieces = whitePieces;
		this.blackPieces = blackPieces;
	}
	
	public ArrayList<Piece> getWhitePieces() {
		return whitePieces;
	}

	public ArrayList<Piece> getBlackPieces() {
		return blackPieces;
	}
	public Piece[][] getBoard(){
		return board;
	}
	/**
	 * displays boardColor if null & Piece info in the format pieceColor
	 * pieceName
	 */
	public void displayBoard() {
		StringJoiner splitJoiner = new StringJoiner("+", "|", "|");
		for (int index = 0; index < board[0].length; index++) {
			splitJoiner.add(String.format("%-17s", "").replace(" ", "-"));
		}
		String lineSplit = splitJoiner.toString();
		for (int i = 0; i < board.length; i++) {
			StringJoiner sj = new StringJoiner(" | ", "| ", " |");
			for (int j = 0; j < board[0].length; j++) {
				if (board[i][j] == null) {
					//sj.add(StringUtils.center(displayColor(boardColor[i][j]).toLowerCase() + " empty,",15));
					//sj.add(String.format("%-15s", displayColor(boardColor[i][j]).toLowerCase() + " empty,"));
					sj.add(center(displayColor(boardColor[i][j]).toLowerCase() + " empty",15));
				} else {
					//sj.add(String.format("%-15s", board[i][j].getColor() + " " + board[i][j].getName()));
					sj.add(center(board[i][j].getColor() + " " + board[i][j].getName(),15));
				}
			}
			
			System.out.println("  "+lineSplit);
			System.out.println((SIZE-i-1)+" "+sj.toString());
		}
		System.out.println("  "+lineSplit);
		for (int index = 0; index < board[0].length; index++) {
			System.out.printf("%-18s","         "+ (char)((int)'A'+index));
		}System.out.println();
	}

	public void setPiece(Coord c, Piece p) {
		if (!checkValid(c)) {
			errorInvalidCoord(c);
			return;
		}
		board[SIZE - 1 - c.getY()][c.getX()] = p;
		p.setCoord(c);
	}

	public boolean movePiece(Coord cStart, Coord cFinal) {
		if (!checkValid(cStart)) {
			errorInvalidCoord(cStart);
			return false;
		} else if (!checkValid(cFinal)) {
			errorInvalidCoord(cFinal);
			return false;
		}
		if (pieceAt(cStart) == null) {
			errorNoPiece(cStart);
			return false;
		}
		Piece p = pieceAt(cStart);
		if (p.getName().equalsIgnoreCase("pawn")) {
			Piece p2 = pieceAt(cFinal);
			if (p2 != null && ((Pawn) p).pawnAttack(cFinal)) {

			}
		} else if (!p.legalMove(cFinal)) {
			illegalMove(p, cFinal);
			return false ;
		}
		setPiece(cFinal, p);
		board[SIZE - 1 - cStart.getY()][cStart.getX()] = null;
		return true;
	}

	public boolean getSquareBooleanColor(Coord c) {
		return boardColor[SIZE - 1 - c.getY()][c.getX()];
	}

	public Piece pieceAt(Coord c) {
		if (!checkValid(c)) {
			errorInvalidCoord(c);
			return null;
		} else {
			return board[SIZE - 1 - c.getY()][c.getX()];
		}
	}

	public static void main(String[] args) {
		Board b = new Board();
		b.displayBoard();
		System.out.println(b.pieceAt(new Coord(4, 4)));
		System.out.println(b.getSquareBooleanColor(new Coord(0, 7)));
		System.out.println(b.getSquareBooleanColor(new Coord(0, 0)));
		System.out.println(b.pieceAt(new Coord(0, 0)));
	}

}
