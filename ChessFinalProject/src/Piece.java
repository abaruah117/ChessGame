import java.util.*;
import java.io.*;

public abstract class Piece {
	private String name;
	private int pointVal;
	private boolean color; // black = false, white = true
	private Coord pos;
	public Piece() {
		name = "";
		pointVal = 0;
		color = true;
		pos = null;
	}

	public Piece(String name, boolean color, Coord c) {
		this.name = name.toUpperCase();
		this.color = color;
		pos = c;
		switch (this.name) {
		case "KING":
			pointVal = Integer.MAX_VALUE;
			break;
		case "QUEEN":
			pointVal = 9;
			break;
		case "ROOK":
			pointVal = 5;
			break;
		case "PAWN":
			pointVal = 1;
			break;
		case "BISHOP":
			pointVal = 3;
			break;
		case "KNIGHT":
			pointVal = 3;
			break;
		default:
			pointVal = 0;
			System.out.println("PIECE NAME INVALID");
		}

	}

	public String getName() {
		return name;
	}

	public void setName(String s) {
		name = s;
	}

	public int getPointVal() {
		return pointVal;
	}

	public void setPointVal(int i) {
		pointVal = i;
	}
	/**
	 * 
	 * @return false if black, true if white
	 */
	public boolean getBooleanColor(){
		return color;
	}
	public String getColor() {
		if (color) {
			return "WHITE";
		} else {
			return "BLACK";
		}
	}

	public void setColor(boolean b) {
		this.color = b;
	}
	public Coord getCoord(){
		return pos;
	}
	public void setCoord(Coord c){
		pos = c;
	}

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
	public int compareTo(Piece other){
		return this.getPointVal() - other.getPointVal();
	}
	public boolean equals(Piece other){
		return this.getName()==other.getName() && this.getBooleanColor() == other.getBooleanColor()
				&& this.getCoord() == other.getCoord();
	}

	public String toString() {
		String color = "color: ";
		if (this.color) {
			color += "white";
		} else {
			color += "black";
		}
		return "piece: " + getName() + " value: " + getPointVal() + " " + color+" "+pos.toString()+" ";
	}

	public abstract boolean legalMove(Coord c);


}
