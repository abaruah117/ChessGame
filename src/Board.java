

public class Board {
	
	private static final int SIZE =8;
	private static final int TILE_SIZE = 20;
	private Tile[][] boardColor = new Tile[SIZE][SIZE];

	public Board() {
		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {
				if ((i + j) % 2 == 0) {
					boardColor[i][j] = new Tile(ModelLoader.getModel("whiteSquare"), new Vector(SIZE - i-1,j), true);
				} else {
					boardColor[i][j] = new Tile(ModelLoader.getModel("blackSquare"), new Vector(SIZE -i-1,j), false);
				}
			}
		}
//		initBoard();
//		initPieceArrayLists();

	}
	
	public void onClick(Vector point) {
		
		for(int y = 0; y < boardColor.length; y++) {
			for(int x = 0; x < boardColor[y].length; x++) {
				if(boardColor[y][x].getCollider() != null && boardColor[y][x].getCollider().testClick(point)) {
					boardColor[y][x].onClick();
				}
			}
		}
		
	}

	public Tile[][] getBoardColor() {
		return boardColor;
	}

	public static int getTileSize() {
		return TILE_SIZE;
	}

	public static int getSize() {
		return SIZE;
	}

	
}
