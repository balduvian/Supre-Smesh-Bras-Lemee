package game.smesh.stage;

import com.ebet.cnge.engine.Camera;
import com.ebet.cnge.engine.Texture;
import game.ccd.LineLoop;
import game.ccd.Vec;
import game.smesh.animation.Animation;
import game.smesh.animation.Sprite;

import static game.assets.Game_Assets.rect;
import static game.assets.Game_Assets.texture_shader;
import static game.smesh.Units.PIXELS_PER_UNIT;

abstract public class StageElement {
	private Sprite sprite;
	
	protected LineLoop[] lineLoops;
	protected float x, y;
	
	abstract public void update();
	
	public void render(Camera camera) {
		sprite.getTexture().bind();
		texture_shader.enable(sprite.getModel(x, y), camera.get_projection_view());
		rect.render();
	}
	
	public StageElement(float x, float y, Sprite sprite, LineLoop[] lineLoops) {
		this.x = x;
		this.y = y;
		
		this.sprite = sprite;
		
		this.lineLoops = lineLoops;
		
		/* scale the positions of the lineloops */
		for (var loop : lineLoops) {
			var numPoints = loop.getNumPoints();
			
			for (var i = 0; i < numPoints; ++i) {
				var point = loop.getPoint(i);
				
				point.div(PIXELS_PER_UNIT).sub(sprite.getCenterX(), sprite.getCenterY());
			}
		}
	}
	
	public int getNumLineLoops() {
		return lineLoops.length;
	}
	
	public LineLoop getLineLoop(int index) {
		return lineLoops[index];
	}
	
	public float getX() {
		return x;
	}
	
	public float getY() {
		return y;
	}
}
