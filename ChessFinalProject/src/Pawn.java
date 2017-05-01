
public class Pawn extends Piece {
	private boolean promote = false;
	public Pawn(boolean color,Coord pos){
		super("pawn", color,pos);
		if(color&&pos.getY()==7){
			promote = true;
		}
		if(!color && pos.getY()==0){
			promote = true;
		}
	}

	@Override
	public boolean legalMove(Coord c) {
		if(this.getCoord().getY()==6||this.getCoord().getY()==1){
			boolean legal = ((c.getY()-this.getCoord().getY())==1||(c.getY()-this.getCoord().getY())==2)&&c.getX()==this.getCoord().getX();
			if(legal){
				if(this.getBooleanColor()&&c.getY()==7){
					promote = true;
				}
				if(!this.getBooleanColor()&&c.getY()==0){
					promote = true;
				}
			}
			return legal;
		}
		else{
			boolean legal =  ((c.getY()-this.getCoord().getY())==1)&&c.getX()==this.getCoord().getX();
			if(legal){
				if(this.getBooleanColor()&&c.getY()==7){
					promote = true;
				}
				if(!this.getBooleanColor()&&c.getY()==0){
					promote = true;
				}
			}
			return legal;
		}
		
		
	}
	public boolean pawnAttack(Piece p2){
		Coord pos = this.getCoord();
		if(pos.getY()>=p2.getCoord().getY()){
			return false;
		}
		else{
			return (Math.abs(pos.getY()-p2.getCoord().getY())==1)&&(Math.abs(pos.getX()-p2.getCoord().getX())==1);
		}
		
	}
	public boolean promote(){
		return promote;
	}
	public static void main(String[] args) {
		Pawn b = new Pawn(true, new Coord(2,6));
		System.out.println(b.legalMove(new Coord(2,7)));
		System.out.println(b.promote());
		System.out.println(b.pawnAttack(new Pawn(false, new Coord(3,7))));
		
	}



}
