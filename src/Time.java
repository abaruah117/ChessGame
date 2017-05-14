/**
 * 
 * @author Amitav & Kevin 
 * Period 3
 */
/**
 * A class that handles game times
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
	 * Gets the time that has passed since the last frame
	 * @return deltaTime, long
	 */
	public static long getDeltaTime() {
		return deltaTime;
	}
	
	/**
	 * Gets the amount of frames that has passed in the last second
	 * @return int lastFrames
	 */
	public static int getLastFrames() {
		return lastFrames;
	}
	/**
	 * Gets the time since the Time class was initializes
	 * @return long totalTime
	 */
	public static long getTotalTime() {
		return totalTime;
	}
	
	/**
	 * Initializes the time class
	 */
	public static void init() {
		startTime = System.nanoTime();
		lastTime = System.nanoTime();
	}
	/**
	 * Updates all the times and frame rates, must be called once per frame
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
