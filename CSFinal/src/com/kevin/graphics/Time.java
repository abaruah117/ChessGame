package com.kevin.graphics;

public class Time {

	private static long startTime;
	private static long lastTime;
	private static long totalTime;
	private static long frameTime;
	private static long deltaTime;
	private static int frames;
	private static int lastFrames;

	public static void init() {
		startTime = System.nanoTime();
		lastTime = System.nanoTime();
	}

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

	public static long getTotalTime() {
		return totalTime;
	}

	public static long getDeltaTime() {
		return deltaTime;
	}

	public static int getLastFrames() {
		return lastFrames;
	}

}
