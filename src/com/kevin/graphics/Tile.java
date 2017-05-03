package com.kevin.graphics;

public class Tile extends Model{
	
	private Vector pos;
	private boolean isWhite;
	
	public Tile(Model other, Vector pos, boolean isWhite) {
		super(other);
		this.pos = pos;
		this.isWhite = isWhite;
	}

	
	
	
}
