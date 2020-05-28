package game.ccd;

public class CCDLine {
	public Vec start;
	public Vec end;
	
	public CCDLine(Vec start, Vec end) {
		this.start = new Vec(start);
		this.end = new Vec(end);
	}
	
	public CCDLine(CCDLine other) {
		this.start = new Vec(other.start);
		this.end = new Vec(other.end);
	}
	
	public CCDLine(float x0, float y0, float x1, float y1) {
		this.start = new Vec(x0, y0);
		this.end = new Vec(x1, y1);
	}
	
	public CCDLine copy() {
		return new CCDLine(this);
	}
	
	public Vec toVector() {
		return end.copy().sub(start);
	}
	
	public CCDLine add(Vec other) {
		start.add(other);
		end.add(other);
		return this;
	}
	
	public CCDLine flipEnd() {
		end.set(start.copy().mul(2).sub(end));
		
		return this;
	}
}
