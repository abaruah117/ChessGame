/**
 * 
 * @author Amitav & Kevin 
 * Period 3
 */
/*
 * A class representing time
 */

public class Time {

	private static long startTime;
	private static long lastTime;
	private static long totalTime;
	private static long frameTime;
	private static long deltaTime;
	private static int frames;
	private static int lastFrames;
	
	/**
	 * Gets the change in time
	 * @return deltaTime, long
	 */
	public static long getDeltaTime() {
		return deltaTime;
	}
	
	/**
	 * Gets the lastFrame
	 * @return int lastFrames
	 */
	public static int getLastFrames() {
		return lastFrames;
	}
	/**
	 * Gets the total time
	 * @return long totalTime
	 */
	public static long getTotalTime() {
		return totalTime;
	}
	
	/**
	 * Initializes start and last time
	 */
	public static void init() {
		startTime = System.nanoTime();
		lastTime = System.nanoTime();
	}
	/**
	 * Updates various instance variables regarding time and 
	 * frame rate
	 */
	public static void update() {
		lastTime = startTime;
		startTime = System.nanoTime();
		deltaTime = startTime - lastTime;
		frameTime += deltaTime;
		totalTime += deltaTime;
		frames++;
		if (frameTime >= 1000000000) {
			frameTime %= 1000000000;
			lastFrames = frames;
			frames = 0;
		}
	}

}
