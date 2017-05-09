
/**
 * 
 * @author Kevin Palani
 *A class that represents a side of an object in 3d, with methods to move along it
 */
public class Edge {

	private Vertex bot, top;
	private double dxBdy, dzBdy, yPreStep;
	private float currentX, currentZ, currentY;
	private float minY, maxY, minX, maxX, minZ, maxZ;

	/**
	 * Creates a new Edge object, with the step starting point at the bottom
	 * @param bot The top vertex
	 * @param top The bottom vertex
	 */
	public Edge(Vertex bot, Vertex top) {
		this.bot = bot;
		this.top = top;
		minY = (float) Math.ceil(bot.getPos().getY());
		maxY = (float) Math.ceil(top.getPos().getY());
		minX = (float) Math.ceil(Math.min(bot.getPos().getX(), top.getPos()
				.getX()));
		maxX = (float) Math.ceil(Math.max(bot.getPos().getX(), top.getPos()
				.getX()));
		minZ = (float) Math.ceil(Math.min(bot.getPos().getZ(), top.getPos()
				.getZ()));
		maxZ = (float) Math.ceil(Math.max(bot.getPos().getZ(), top.getPos()
				.getZ()));
		double dy = top.getPos().getY() - bot.getPos().getY();
		double dx = top.getPos().getX() - bot.getPos().getX();
		double dz = top.getPos().getZ() - bot.getPos().getZ();
		yPreStep = minY - bot.getPos().getY();
		dxBdy = dx / dy;
		dzBdy = dz / dy;
		currentX = (float) (bot.getPos().getX() + yPreStep * dxBdy);
		currentZ = (float) (bot.getPos().getZ() + yPreStep * dzBdy);
		currentY = (float) (bot.getPos().getY() + yPreStep);
	}

	/**
	 * Makes sure a given value is within some bounds
	 * @param value The value
	 * @param min The minimum (inclusive)
	 * @param max The maximum (inclusive)
	 * @return The original value, unless it was out of bounds, then it is rounded down to the nearest bound
	 */
	public float clamp(float value, float min, float max) {
		float maxClamp = (value > max ? max : value);
		return (maxClamp < min ? min : maxClamp);
	}

	/**
	 * @return The bottom vertex
	 */
	public Vertex getBot() {
		return bot;
	}

	/**
	 * 
	 * @return The current X value based on the current step
	 */
	public float getCurrentX() {
		return currentX;
	}

	/**
	 * @return The current Y value based on the current step
	 */
	public float getCurrentY() {
		return currentY;
	}

	/**
	 * @return The current Z value based on the current step
	 */
	public float getCurrentZ() {
		return currentZ;
	}

	/**
	 * 
	 * @return The maximum X value that can be stepped to
	 */
	public float getMaxX() {
		return maxX;
	}

	/**
	 * 
	 * @return The maximum Y value that can be stepped to
	 */
	public float getMaxY() {
		return maxY;
	}

	/**
	 * 
	 * @return The maximum Z value that can be stepped to
	 */
	public float getMaxZ() {
		return maxZ;
	}

	/**
	 * 
	 * @return The minimum X value that can be stepped to
	 */
	public float getMinX() {
		return minX;
	}

	/**
	 * 
	 * @return The minimum Y value that can be stepped to
	 */
	public float getMinY() {
		return minY;
	}

	/**
	 * 
	 * @return The minimum Z value that can be stepped to
	 */
	public float getMinZ() {
		return minZ;
	}

	/**
	 * 
	 * @return The top vertex
	 */
	public Vertex getTop() {
		return top;
	}

	/**
	 * The x value given a y value without stepping across the edge
	 * @param y The y value
	 * @return The X value
	 */
	public float getXfromEq(float y) {
		return (float) ((y - bot.getPos().getY()) * dxBdy + bot.getPos().getX());
	}

	/**
	 * Moves 1 up across the Y, and moves the correct distance across X and Z
	 */
	public void step() {
		currentX += dxBdy;
		// currentX = clamp(currentX, minX, maxX);
		currentZ += dzBdy;
		// currentZ = clamp(currentZ, minZ, maxZ);
		currentY++;
	}

}
