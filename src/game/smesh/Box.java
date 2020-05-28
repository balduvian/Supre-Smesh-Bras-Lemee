package game.smesh;

import com.ebet.cnge.engine.Camera;
import com.ebet.cnge.engine.Transform;

import static game.assets.Game_Assets.color_shader;
import static game.assets.Game_Assets.rect;

abstract public class Box {
	public float x, y;
	public float width, height;
	
	private float offsetX, offsetY;
	
	public Box(float offsetX, float offsetY, float width, float height) {
		this.offsetX = offsetX;
		this.offsetY = offsetY;
		this.width = width;
		this.height = height;
	}
	
	public Box(Box other) {
		this.offsetX = other.offsetX;
		this.offsetY = other.offsetY;
		this.width = other.width;
		this.height = other.height;
	}
	
	public Box setOrigin(float originX, float originY, boolean direction) {
		if (direction)
			this.x = offsetX + originX;
		else
			this.x = (originX - offsetX) - width;
		
		this.y = offsetY + originY;
		
		return this;
	}
	
	public void setOffsetOrigin(float originX, float originY) {
		this.offsetX -= originX;
		this.offsetY -= originY;
	}
	
	/**
	 * calls after matricies are decided, but before shaders are enabled
	 * enable your own shaders in this function
	 *
	 * the vao will be rendered outside this function
	 *
	 * @param model the model matrix for the box
	 * @param projectionView the projection matrix for the box
	 */
	abstract void customRender(float[] model, float[] projectionView);
	
	public void render(Camera camera) {
		var model = Transform.matrify(x, y, width, height);
		var projectionView = camera.get_projection_view();
		
		customRender(model, projectionView);
		
		rect.render();
	}
}
