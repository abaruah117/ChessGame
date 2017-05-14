/**
 * 
 * @author Amitav & Kevin 
 * Period 3
 */
/*
 * An abstract class representing a Piece in a ChessGame
 */

public abstract class Piece {
	private String name;
	private int pointVal;
	private boolean color; // black = false, white = true
	private Coord pos;
	/**
	 * Default constructor initializes name to an empty string
	 * point value to zero, color to true (white), and a null 
	 * position Coord
	 */
	public Piece() {
		name = "";
		pointVal = 0;
		color = true;
		pos = null;
	}
	/**
	 * Initializes the name, color, and position to given values as well as the 
	 * point value based off the name.
	 * @param name the Piece name
	 * @param color the boolean color
	 * @param c the position Coord
	 */
	public Piece(String name, boolean color, Coord c) {
		this.name = name.toUpperCase();
		this.color = color;
		pos = c;
		switch (this.name.toUpperCase()) {
		case "KING":
			pointVal = 5;
			break;
		case "QUEEN":
			pointVal = 4;
			break;
		case "ROOK":
			pointVal = 1;
			break;
		case "PAWN":
			pointVal = 0;
			break;
		case "BISHOP":
			pointVal = 3;
			break;
		case "KNIGHT":
			pointVal = 2;
			break;
		default:
			pointVal = -1;
			System.out.println("PIECE NAME INVALID");
		}

	}
	/**
	 * Compares Pieces using point values
	 * @param other the Piece to compare to
	 * @return this pointValue - other's pointValue
	 */
	public int compareTo(Piece other){
		return this.getPointVal() - other.getPointVal();
	}
	/**
	 * Checks if two Pieces are equal
	 * @param other the Piece to compare this Piece to
	 * @return true if they are equal in name, color, and position, false otherwise
	 */
	public boolean equals(Piece other){
		return this.getName()==other.getName() && this.getBooleanColor() == other.getBooleanColor()
				&& this.getCoord().equals(other.getCoord());
	}

	/**
	 * A method to get the boolean value representing the piece color
	 * @return false if black, true if white
	 */
	public boolean getBooleanColor(){
		return color;
	}
	/**
	 * Gets the color as a String 
	 * @return a String representing the boolean value of the Piece (true -> white, false -> black)
	 */
	public String getColor() {
		if (color) {
			return "WHITE";
		} else {
			return "BLACK";
		}
	}
	/**
	 * Gets the position
	 * @return the position Coord
	 */
	public Coord getCoord(){
		return pos;
	}
	/**
	 * Gets the Piece name
	 * @return the name String
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Gets the pointValue
	 * @return the integer pointValue
	 */
	public int getPointVal() {
		return pointVal;
	}
	/**
	 * Gets info about this Piece
	 * @return a String with the Piece's name, value, color and position
	 */
	public String info(){
		String color = "color: ";
		if (this.color) {
			color += "white";
		} else {
			color += "black";
		}
		return "piece: " + getName() + " value: " + getPointVal() + " " + color+" "+pos.toString()+" ";
	}
	/**
	 * Checks legality of a move to Coord c
	 * @param Coord c the Coord to check if legal move
	 */
	public abstract boolean legalMove(Coord c);
	/**
	 * Sets the boolean color instance variable to a new value
	 * @param b the new boolean value
	 */
	public void setColor(boolean b) {
		this.color = b;
	}
	
	/**
	 * Sets the boolean color to a new value based off the String
	 * @param s the String representing the color (white/black)
	 */
	public void setColor(String s) {
		String c = s.toLowerCase().trim();
		if (c.equals("white")) {
			setColor(true);
		} else if (c.equals("black")) {
			setColor(false);
		} else {
			System.out.println("INVALID COLOR INPUT: " + s);
		}
	}
	/**
	 * Sets position to a new Coord
	 * @param c the new position Coord
	 */
	public void setCoord(Coord c){
		pos = c;
	}
	/**
	 * Sets name to a new String
	 * @param s the new Piece name
	 */
	public void setName(String s) {
		name = s;
	}
	/**
	 * Sets point value to a new point value
	 * @param i the new integer point value
	 */
	public void setPointVal(int i) {
		pointVal = i;
	}	
	
	/**
	 * a toString method that returns information about the name, color, and position
	 * @return a String with the Piece's name, color, and position Coord
	 */
	public String toString() {
		return this.getName()+" color: "+((this.getBooleanColor())?"white":"black")+" Coord: "+this.getCoord().toString();
	}


}
