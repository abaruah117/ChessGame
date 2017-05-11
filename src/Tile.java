

public class Tile extends Model{
	
	private Vector pos;
	private boolean isWhite;
	private Clickable collider;
	
	public Tile(Model other, Vector pos, boolean isWhite) {
		super(other);
		this.pos = pos;
		this.isWhite = isWhite;
	}
	
	public Clickable getCollider() {
		return collider;
	}

	public boolean isWhite() {
		return isWhite;
	}

	public Coord onClick() {
		//System.out.println("Tile at " + pos + " has been clicked");
		return pos.getCoord();
	}

	public void setCollider(Clickable collider) {
		this.collider = collider;
	}

	public void setWhite(boolean isWhite) {
		this.isWhite = isWhite;
	}

	/**
	 * @return the pos
	 */
	public Vector getPos() {
		return pos;
	}
	



	
	
}
