import java.util.*;
import java.io.*;

public class ChessGameDriver {
	private static Scanner in = new Scanner(System.in);

	public static void main(String[] args) {
		System.out.print("Enter the first player's name: ");
		String p1 = in.nextLine();
		System.out.print("Enter the second player's name: ");
		String p2 = in.nextLine();
		ChessGame c = new ChessGame(p1, p2);
		c.displayGame();
		System.out.println("press q to exit");
		System.out.println("Enter a command, ie c4,e6");
		String d = in.nextLine();
		boolean color = true; // white
		while (!d.equals("q")) {
			if (d.length() != 5) {
				System.out.println("please format it in the following format: c6,e4");
			} else {
				char g = d.charAt(0);
				int h = (int) d.charAt(1) - (int) '0';
				char i = d.charAt(3);
				int j = (int) d.charAt(4) - (int) '0';
				int converted1 = 0;
				int converted2 = 0;
				switch (g) {
				case 'a':
					converted1 = 0;
					break;
				case 'b':
					converted1 = 1;
					break;
				case 'c':
					converted1 = 2;
					break;
				case 'd':
					converted1 = 3;
					break;
				case 'e':
					converted1 = 4;
					break;
				case 'f':
					converted1 = 5;
					break;
				case 'g':
					converted1 = 6;
					break;
				case 'h':
					converted1 = 7;
					break;
				default:
					converted1 = -1;
					System.out.println("ERROR, INVALID INPUT FOR LETTER ON X-AXIS");
				}
				switch (i) {
				case 'a':
					converted2 = 0;
					break;
				case 'b':
					converted2 = 1;
					break;
				case 'c':
					converted2 = 2;
					break;
				case 'd':
					converted2 = 3;
					break;
				case 'e':
					converted2 = 4;
					break;
				case 'f':
					converted2 = 5;
					break;
				case 'g':
					converted2 = 6;
					break;
				case 'h':
					converted2 = 7;
					break;
				default:
					converted2 = -1;
					System.out.println("ERROR, INVALID INPUT FOR LETTER ON Y-AXIS");
				}
				if (converted1 >= 0 && converted2 >= 0) {
					Coord c1 = new Coord(converted1, h);
					Coord c2 = new Coord(converted2, j);
					if (c.getPiece(c1) == null) {
						System.out.println("no piece to move");
					} else {
						if (c.getPiece(c1).getBooleanColor() != color) {
							System.out.println("Move a piece of your color, please");
						} else {
							if (c.getPiece(c2) != null) {
								// System.out.println("try attack");
								if (c.attack(c1, c2)) {
									// don't do anything ( so turn is not ended)
								} else {
									color = !color; // attack didnt go through,
													// invalid move
								}
							} else {
								c.movePiece(c1, c2);
							}
							c.displayGame();
							color = !color;
						}
					}
				}
			}
			System.out.println((color) ? "white" : "black" + " player,Enter a command, ie c4,e6");
			d = in.nextLine();

		}
	}
}
