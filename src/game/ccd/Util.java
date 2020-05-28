package game.ccd;

public class Util {
	public static float interp(float start, float end, float along) {
		return (end - start) * along + start;
	}
	
	public static float invInterp(float start, float end, float value) {
		return (value - start) / (end - start);
	}
	
	public static boolean inclusiveRange(float low, float value, float high) {
		return (value >= low) && (value <= high);
	}
	
	public static boolean inclusiveRange(int low, int value, int high) {
		return (value >= low) && (value <= high);
	}
	
	public static boolean exclusiveRange(int low, int value, int high) {
		return (value > low) && (value < high);
	}
}
