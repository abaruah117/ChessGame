

public class Tile extends Model{
	
	private Vector pos;
	private boolean isWhite;
	private Clickable collider;
	
	public Tile(Model other, Vector pos, boolean isWhite) {
		super(other);
		this.pos = pos;
		this.isWhite = isWhite;
	}
	
	public void onClick() {
		System.out.println("Tile at " + pos + " has been clicked");
	}

	public Clickable getCollider() {
		return collider;
	}

	public void setCollider(Clickable collider) {
		this.collider = collider;
	}

	public boolean isWhite() {
		return isWhite;
	}

	public void setWhite(boolean isWhite) {
		this.isWhite = isWhite;
	}
	



	
	
}
