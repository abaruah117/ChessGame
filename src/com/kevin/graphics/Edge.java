package com.kevin.graphics;

public class Edge {

	private Vertex bot, top;
	private double dxBdy, dzBdy, yPreStep;
	private float currentX, currentZ, currentY;
	private float minY, maxY, minX, maxX, minZ, maxZ;

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

	public void step() {
		currentX += dxBdy;
		// currentX = clamp(currentX, minX, maxX);
		currentZ += dzBdy;
		// currentZ = clamp(currentZ, minZ, maxZ);
		currentY++;
	}

	public float clamp(float value, float min, float max) {
		float maxClamp = (value > max ? max : value);
		return (maxClamp < min ? min : maxClamp);
	}

	public float getXfromEq(float y) {
		return (float) ((y - bot.getPos().getY()) * dxBdy + bot.getPos().getX());
	}

	public float getMinY() {
		return minY;
	}

	public float getMaxY() {
		return maxY;
	}

	public Vertex getBot() {
		return bot;
	}

	public Vertex getTop() {
		return top;
	}

	public float getCurrentX() {
		return currentX;
	}

	public float getCurrentZ() {
		return currentZ;
	}

	public float getCurrentY() {
		return currentY;
	}

	public float getMinZ() {
		return minZ;
	}

	public float getMaxZ() {
		return maxZ;
	}

	public float getMinX() {
		return minX;
	}

	public float getMaxX() {
		return maxX;
	}

}
