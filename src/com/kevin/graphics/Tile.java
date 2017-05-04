package com.kevin.graphics;

public class Tile extends Model implements Clickable{
	
	private Vector pos;
	private boolean isWhite;
	private static final Vector size = new Vector(Board.getTileSize(), Board.getTileSize(), Board.getTileSize());
	
	public Tile(Model other, Vector pos, boolean isWhite) {
		super(other);
		this.pos = pos;
		this.isWhite = isWhite;
	}
	
	public boolean contains(Vector point) {
		return false;
	}

	@Override
	public boolean isClicked(Vector point) {
		return false;
	}
	
	
}
