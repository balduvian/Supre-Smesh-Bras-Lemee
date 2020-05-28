package game.smesh.animation;

public class Animation {
	/* time is measured in frames */
	private int[] times;
	private Sprite[] sprites;
	
	private int frameIndex;
	private int timer;
	
	public Animation(int[] times, Sprite[] sprites) {
		this.times = times;
		this.sprites = sprites;
		
		reset();
	}
	
	public void reset() {
		frameIndex = 0;
		timer = 0;
	}
	
	public void update() {
		/* frame time of 0 means it doesn't update */
		if (times[frameIndex] != 0) {
			++timer;
			
			/* switch to next frame */
			if (timer == times[frameIndex]) {
				timer = 0;
				++frameIndex;
				frameIndex %= sprites.length;
			}
		}
	}
	
	public Sprite getSprite() {
		return sprites[frameIndex];
	}
}
