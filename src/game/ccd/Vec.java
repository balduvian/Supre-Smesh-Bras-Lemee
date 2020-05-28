package game.ccd;

public class Vec {
	public float x;
	public float y;
	
	public Vec(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public Vec(Vec other) {
		x = other.x;
		y = other.y;
	}
	
	public void set(Vec other) {
		this.x = other.x;
		this.y = other.y;
	}
	
	public void set(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public Vec copy() {
		return new Vec(this);
	}
	
	public float length() {
		return (float)Math.sqrt(x * x + y * y);
	}
	
	public float lengthSquared() {
		return x * x + y * y;
	}
	
	public Vec normalize() {
		var len = length();
		x /= len;
		y /= len;
		return this;
	}
	
	public Vec add(Vec other) {
		x += other.x;
		y += other.y;
		return this;
	}
	
	public Vec sub(Vec other) {
		x -= other.x;
		y -= other.y;
		return this;
	}
	
	public Vec sub(float x, float y) {
		this.x -= x;
		this.y -= y;
		return this;
	}
	
	public Vec mul(float scale) {
		x *= scale;
		y *= scale;
		return this;
	}
	
	public Vec div(float scale) {
		x /= scale;
		y /= scale;
		return this;
	}
	
	public float dot(Vec other) {
		return (x * other.x) + (y * other.y);
	}
	
	public Vec project(Vec onto) {
		Vec temp = new Vec(onto);
		set(temp.mul(dot(onto) / onto.lengthSquared()));
		return this;
	}
	
	public Vec rotate(float angle) {
		var tempX = x * Math.cos(angle) - y * Math.sin(angle);
		y = (float)(x * Math.sin(angle) + y * Math.cos(angle));
		x = (float)tempX;
		return this;
	}
	
	public float angle() {
		return (float)Math.atan2(y, x);
	}
	
	public Vec center() {
		x /= 2;
		y /= 2;
		return this;
	}
	
	public Vec setLength(float l) {
		var len = length();
		x *= l / len;
		y *= l / len;
		return this;
	}
	
	public String toString() {
		return "<" + x + ", " + y + ">";
	}
}