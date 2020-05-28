package game.smesh;

public class Units {
	public static final float PIXELS_PER_UNIT = 64.0f;
	
	public static final int FRAME_RATE = 60;
	
	public static final boolean RIGHT = true;
	public static final boolean LEFT = false;
	
	/**
	 * @param units the amount of units travelled per second in this speed
	 * @return a speed equal to this many uints per second
	 */
	public static final float unitsPerSecond(float units) {
		return units / FRAME_RATE;
	}
	
	/**
	 * @param units the amount of units travelled per second per second in this acceleration
	 * @return an acceleration equal to this many uints per second squared
	 */
	public static final float unitsPerSecondSquared(float units) {
		return units / (FRAME_RATE * FRAME_RATE);
	}
}
