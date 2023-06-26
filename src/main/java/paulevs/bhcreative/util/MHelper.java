package paulevs.bhcreative.util;

public class MHelper {
	public static final float PI = (float) Math.PI;

	public static double clamp(double value, double min, double max) {
		return value < min ? min : value > max ? max : value;
	}
	
	public static int getColor(int r, int g, int b, int a) {
		return a << 24 | r << 16 | g << 8 | b;
	}
}
