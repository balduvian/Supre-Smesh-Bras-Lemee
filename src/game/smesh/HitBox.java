package game.smesh;

import game.assets.Game_Assets;

import static game.smesh.Units.PIXELS_PER_UNIT;

public class HitBox extends Box {
	
	public float knockbackPercent;
	
	public HitBox(int offsetX, int offsetY, int width, int height) {
		super(offsetX / PIXELS_PER_UNIT, offsetY / PIXELS_PER_UNIT, width / PIXELS_PER_UNIT, height / PIXELS_PER_UNIT);
		
		knockbackPercent = 1.0f;
	}
	
	public HitBox(int offsetX, int offsetY, int width, int height, float knockbackPercent) {
		super(offsetX / PIXELS_PER_UNIT, offsetY / PIXELS_PER_UNIT, width / PIXELS_PER_UNIT, height / PIXELS_PER_UNIT);
		
		this.knockbackPercent = knockbackPercent;
	}
	
	public HitBox(HitBox other) {
		super(other);
	}
	
	public HitBox copy() {
		return new HitBox(this);
	}
	
	@Override
	void customRender(float[] model, float[] projectionView) {
		Game_Assets.color_shader.enable(model, projectionView);
		Game_Assets.color_shader.give(0, 0, 1, 0.5f);
	}
}
