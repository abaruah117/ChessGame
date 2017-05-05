
public class Pawn extends Piece {
	public static void main(String[] args) {
		Pawn b = new Pawn(false, new Coord(2, 6));
		System.out.println(b.pawnAttack(new Coord(3,5)));
		System.out.println(b.legalMove(new Coord(2, 4)));
		System.out.println(b.legalMove(new Coord(2, 1)));
		System.out.println(b.legalMove(new Coord(2, 0)));

	}

	private boolean promote = false;

	public Pawn(boolean color, Coord pos) {
		super("pawn", color, pos);
		if (color && pos.getY() == 7) {
			promote = true;
		}
		if (!color && pos.getY() == 0) {
			promote = true;
		}
	}

	@Override
	public boolean legalMove(Coord c) {
		int x1 = this.getCoord().getX();
		int x2 = c.getX();
		int y1 = this.getCoord().getY();
		int y2 = c.getY();
		int diffX = x2 - x1;
		int diffY = y2 - y1;
		if (!this.getBooleanColor()) {
			diffY *= -1;// bc black moves down (negative change)
		}
		if (y1 == 6 || y1 == 1) {
			boolean legal = (diffY == 1 || (diffY == 2) && diffX == 0);
			if (legal) {
				if (this.getBooleanColor() && c.getY() == 7) {
					promote = true;
				}
				if (!this.getBooleanColor() && c.getY() == 0) {
					promote = true;
				}
			}
			return legal;
		} else {
			boolean legal = (diffY == 1) && diffX==0;
			if (legal) {
				if (this.getBooleanColor() && c.getY() == 7) {
					promote = true;
				}
				if (!this.getBooleanColor() && c.getY() == 0) {
					promote = true;
				}
			}
			return legal;
		}

	}

	public boolean pawnAttack(Coord c2) {
		Coord pos = this.getCoord();
		int diffX = Math.abs(c2.getX() - pos.getX());
		int diffY = c2.getY() - pos.getY();
		if(!getBooleanColor()){
			diffY*=-1;
		}
		return (diffX==1) && diffY == 1;

	}

	public boolean promote() {
		return promote;
	}

}
