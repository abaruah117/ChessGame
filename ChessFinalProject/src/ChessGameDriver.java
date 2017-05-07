import java.util.*;
import java.io.*;

public class ChessGameDriver {
	private static Scanner in = new Scanner(System.in);

	public static void main(String[] args) {
		System.out.print("Enter the first player's name: ");
		String player1 = in.nextLine();
		System.out.print("Enter the second player's name: ");
		String player2 = in.nextLine();
		ChessGame c = new ChessGame(player1, player2);
		c.displayGame();
		System.out.println("press q to exit");
		String d = "";
		boolean color = true; // white
		while (!d.equals("q")) {
	
			if (c.check(color)) {
				if (c.checkMate(color)) {
					System.out.println("Checkmate: " + ((!color) ? "white" : "black") + " wins");
					break;
				}
				System.out.println(((color) ? "white" : "black") + " king is in check");
				System.out.println(((color) ? "white" : "black") + " player,Enter a command, ie c4,e6: ");
				d = in.nextLine();
				if(d.equals("q")){
					break;
				}
				else if (d.length() != 5) {
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
							Piece p1 = c.getPiece(c1);
							if (p1.getBooleanColor() != color) {
								System.out.println("Move a piece of your color, please");
							} else {
								if (c.getPiece(c2) != null) {
									Piece p2 = c.getPiece(c2);
									if (c.attack(c1, c2) != null) {
										if (c.check(color)) {
											System.out.println("STILL IN CHECK, INVALID");
											color = !color;
											c.revertMove(c1, c2, p2);
										}
									} else {
										color = !color;
									}
								} else {
									if (!c.movePiece(c1, c2)) {
										color = !color;
									} else {
										if (c.check(color)) {
											System.out.println("STILL IN CHECK, INVALID");
											color = !color;
											c.movePiece(c2, c1);
										}
									}
								}
								c.displayGame();
								color = !color;
							}
						}
					}
				}
			}
			System.out.println(((color) ? "white" : "black") + " player,Enter a command, ie c4,e6: ");
			d = in.nextLine();
			// }
			if(d.equals("q")){
				break;
			}
			else if (d.equals("skip")) {
				color = !color;
			} 
			else if(d.equals("reset")){
				c = new ChessGame(player1, player2);
				c.displayGame();
				continue;
			}else if (d.length() != 5) {
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
								if (c.attack(c1, c2) == null) {
									color = !color;
									
								} else {
									c.displayGame();
									color = !color;
								}
							} else {
								if (!c.movePiece(c1, c2)) {
								} else {
									c.displayGame();
									color = !color;
								}
							}

						}
					}
				}
			}

		}
	}
}
