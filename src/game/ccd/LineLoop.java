package game.ccd;

import javax.sound.sampled.Line;

public class LineLoop {
	private int numLines;
	private boolean connected;
	private Vec[] points;
	
	public LineLoop(Vec[] points, boolean connected) {
		numLines = connected ? points.length : points.length - 1;
		
		this.connected = connected;
		this.points = points;
	}
	
	public int getNumLines() {
		return numLines;
	}
	
	public int getNumPoints() {
		return points.length;
	}
	
	public CCDLine getLine(int lineIndex) {
		var nextIndex = connected ?
			                (lineIndex + 1) % numLines :
							lineIndex + 1;
		
		return new CCDLine(points[lineIndex], points[nextIndex]);
	}
	
	public Vec getPoint(int pointIndex) {
		return points[pointIndex];
	}
}
