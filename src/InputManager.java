import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;


public class InputManager implements MouseListener, MouseMotionListener{

	private ArrayList<Coord> selected;
	private Board board;
	private Vector lastPos = new Vector(-1, -1, -1);
	private Vector rotations = new Vector(-65, 0, 0);
	private final float rotXScale = .1f;
	
	public InputManager(Board board, ArrayList<Coord> selected) {
		this.selected = selected;
		this.board = board;
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		
		if(e.getButton() == 1) {
		Coord sel = board.onClick(new Vector(e.getX(), e.getY()));
		if (sel == null) {
			return;
		}
		System.out.println(sel.toString());
		if (selected.size() == 0) {
			selected.add(sel);
		} else if (selected.size() == 1) {
			if (selected.get(0).equals(sel)) {
				selected.remove(0);
			} else {
				selected.add(sel);
			}
		}
		} else if(e.getButton() == 2) {
			rotations = new Vector(-65, 0, 0);
		}
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stubS
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		lastPos = new Vector(-1, -1, -1);
	}

	public ArrayList<Coord> getSelected() {
		return selected;
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		if(lastPos.getX() == -1) {
			lastPos.setX(e.getY());
		} else {
			int deltaX = (int) (e.getY() - lastPos.getX());
			//rotX += -deltaX * rotXScale;
			rotations.setX((float) (rotations.getX() - (deltaX * rotXScale * Math.cos(Math.toRadians(rotations.getY())))));
			rotations.setZ((float) (rotations.getZ() - (deltaX * rotXScale * Math.sin(Math.toRadians(rotations.getY())))));
			lastPos.setX(e.getY());
		}
		
		if(lastPos.getY() == -1) {
			lastPos.setY(e.getX());
		} else {
			int deltaY = (int) (e.getX() - lastPos.getY());
			//rotX += -deltaX * rotXScale;
			rotations.setY((float) (rotations.getY() - (deltaY * rotXScale )));
			lastPos.setY(e.getX());
		}
		
		
		
		
	}

	@Override
	public void mouseMoved(MouseEvent paramMouseEvent) {
		// TODO Auto-generated method stub
		
	}

	public Vector getRotations() {
		return rotations;
	}


}
