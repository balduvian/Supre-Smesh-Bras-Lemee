package com.ebet.cnge.engine.window;

import static org.lwjgl.glfw.GLFW.*;

public class Input {
	public static final int NUM_KEYS = GLFW_KEY_LAST + 1;
	private int[] keys = new int[NUM_KEYS];
	
	private static final int NOT_PRESSED = 0;
	private static final int IMMEDIATE = 1;
	private static final int COUNTED = 2;
	private static final int HELD = 3;
	
	/**
	 * called directly from glfw key callback
	 *
	 * @param key
	 * @param action
	 */
	void onKey(int key, int action) {
		if (action == GLFW_PRESS)
			keys[key] = IMMEDIATE;
			
		else if (action == GLFW_RELEASE)
			keys[key] = NOT_PRESSED;
	}
	
	/**
	 * called every frame
	 *
	 * updates keys so that they move from being pressed the first
	 * frame to being counted as held on the next frame
	 */
	void update() {
		for (var i = 0; i < NUM_KEYS; ++i) {
			if (keys[i] == IMMEDIATE || keys[i] == COUNTED)
				++keys[i];
		}
	}
	
	public boolean getKeyPressed(int key) {
		return keys[key] == COUNTED;
	}
	
	public boolean getKeyHeld(int key) {
		return keys[key] == COUNTED || keys[key] == HELD;
	}
}
