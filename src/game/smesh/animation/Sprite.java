package game.smesh.animation;

import com.ebet.cnge.engine.Texture;
import com.ebet.cnge.engine.Transform;
import game.smesh.HitBox;

import static game.smesh.Units.PIXELS_PER_UNIT;

public class Sprite {
	protected Texture texture;
	
	/* in world coordinates */
	protected float centerX;
	protected float centerY;
	
	protected float width;
	protected float height;
	
	/**
	 * pixel coordinates have bottom left corner at (0, 0)
	 *
	 * @param texture
	 * @param centerX the center of the sprite horizontally in pixel coordinates
	 * @param centerY the center of the sprite vertically in pixel coordinates
	 */
	public Sprite(Texture texture, int centerX, int centerY) {
		this.texture = texture;
		
		this.centerX = centerX / PIXELS_PER_UNIT;
		this.centerY = centerY / PIXELS_PER_UNIT;
		this.width = texture.width / PIXELS_PER_UNIT;
		this.height = texture.height / PIXELS_PER_UNIT;
	}
	
	/* rendering */
	
	public float[] getModel(float originX, float originY, boolean direction) {
		if (direction) {
			return Transform.matrify(originX, originY, width, height, centerX, centerY);
		} else {
			return Transform.matrifyFlipped(originX, originY, width, height, centerX, centerY);
		}
	}
	
	public float[] getModel(float originX, float originY) {
		return Transform.matrify(originX, originY, width, height, centerX, centerY);
	}
	
	public Texture getTexture() {
		return texture;
	}
	
	public float getCenterX() {
		return centerX;
	}
	
	public float getCenterY() {
		return centerY;
	}
	
	public float getWidth() {
		return width;
	}
	
	public float getHeight() {
		return height;
	}
}
