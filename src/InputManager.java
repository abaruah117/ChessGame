import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.ArrayList;

/**
 * 
 * @author Amitav & Kevin 
 * Period 3
 */
/*
 *This class deals with getting input from the mouse and changing the game in response
 */
public class InputManager implements MouseListener, MouseMotionListener, MouseWheelListener{

	private ArrayList<Coord> selected;
	private Board board;
	private Vector lastPos = new Vector(-1, -1, -1);
	private Vector rotations = new Vector(-65, 0, 0);
	private final float rotXScale = .1f;
	private float zoom = 1;
	
	/**
	 * Creates a new inputManager
	 * @param board The board to change
	 * @param selected The array list to hold the selected positions
	 */
	public InputManager(Board board, ArrayList<Coord> selected) {
		this.selected = selected;
		this.board = board;
	}
	/**
	 * Gets the rotations Vector
	 * @return the rotations Vector
	 */
	public Vector getRotations() {
		return rotations;
	}

	/**
	 * Gets the selected Coordinates
	 * @return The array list of selected cords
	 */
	public ArrayList<Coord> getSelected() {
		return selected;
	}

	/**
	 * Selects the correct piece based on where the mouse was clicked, and resets board orientation
	 * @param MouseEvent e the mouse was clicked
	 */
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

	/**
	 * Deals with dragging the mouse, updates deltas and rotates board
	 * @param MouseEvent e the mouse's actions
	 */
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
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stubS
		
	}
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseMoved(MouseEvent paramMouseEvent) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mousePressed(MouseEvent e) {
		
	}

	/**
	 * Resets the deltas when the mouse is released
	 * @param MouseEvent e the mouse's actions
	 */
	@Override
	public void mouseReleased(MouseEvent e) {
		lastPos = new Vector(-1, -1, -1);
	}
	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		zoom = zoom - e.getWheelRotation()*2;

		
	}
	public float getZoom() {
		return zoom;
	}


}
