/**
 * 
 * @author Amitav & Kevin 
 * Period 3
 */
/*
 * A class representing a tile on the chess board
 */

public class Tile extends Model{
	
	private Vector pos;
	private boolean isWhite;
	private Clickable collider;
	/**
	 * Initializes a tile with a Model, Vector, and color (white or not)
	 * @param other the Model
	 * @param pos the Vector
	 * @param isWhite boolean representing color
	 */
	public Tile(Model other, Vector pos, boolean isWhite) {
		super(other);
		this.pos = pos;
		this.isWhite = isWhite;
	}
	
	/**
	 * Gets the Clickable bounding box for this tile
	 * @return the Clickable collider
	 */
	public Clickable getCollider() {
		return collider;
	}

	/**
	 * Gets the position Vector
	 * @return the pos Vector
	 */
	public Vector getPos() {
		return pos;
	}
	/**
	 * Gets the boolean representing tileColor
	 * @return if the Tile isWhite
	 */
	public boolean isWhite() {
		return isWhite;
	}
	
	/**
	 * Gets the Coord of the Tile
	 * @return the Coord of the position Vector
	 */
	public Coord onClick() {
		return pos.getCoord();
	}
	
	/**
	 * Sets the collider to a new Clickable 
	 * @param collider the new Clickable
	 */
	public void setCollider(Clickable collider) {
		this.collider = collider;
	}
	
	/**
	 * Sets the isWhite (representing tile color) to a new boolean
	 * @param isWhite the new boolean
	 */
	public void setWhite(boolean isWhite) {
		this.isWhite = isWhite;
	}
	



	
	
}
