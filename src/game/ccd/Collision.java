package game.ccd;

public class Collision {
	public Vec position;
	public CCDLine line;
	
	public float side;
	public float tValue;
	
	public Collision(Vec position, CCDLine line, float side, float tValue) {
		this.position = position;
		this.line = line;
		
		this.side = side;
		this.tValue = tValue;
	}
}
