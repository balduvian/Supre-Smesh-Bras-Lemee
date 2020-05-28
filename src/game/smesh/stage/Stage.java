package game.smesh.stage;

import com.ebet.cnge.engine.Camera;
import com.ebet.cnge.engine.Texture;
import com.ebet.cnge.engine.Transform;
import game.ccd.LineLoop;
import game.ccd.Vec;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static game.assets.Game_Assets.rect;
import static game.assets.Game_Assets.texture_shader;

abstract public class Stage {
	private Texture background;
	
	private ArrayList<StageElement> stageElements;
	
	/* outside this range are the blast zones */
	private float width;
	private float height;
	
	private Vec[] playerSpawns;
	
	public Stage(Texture background, float width, float height, Vec[] playerSpawns, StageElement[] stageElements) {
		this.background = background;
		
		this.width = width;
		this.height = height;
		
		this.playerSpawns = playerSpawns;
		
		this.stageElements = new ArrayList<>(Arrays.asList(stageElements));
	}
	
	abstract public void update();
	
	public float getCenterX() {
		return width / 2;
	}
	
	public float getCenterY() {
		return height / 2;
	}
	
	public void render(Camera camera) {
		/* render background */
		var parallax = 0.4f;
		var offsetX = (camera.getCenterX() - getCenterX()) * parallax;
		var offsetY = (camera.getCenterY() - getCenterY()) * parallax;
		
		background.bind();
		texture_shader.enable(Transform.matrify(offsetX, offsetY, 5, 5, 2.5f, 2.5f), camera.get_projection_view());
		rect.render();
		
		/* render elements */
		for (var element : stageElements) {
			element.render(camera);
		}
	}
	
	public Vec getPlayerSpawn(int playerIndex) {
		return playerSpawns[playerIndex];
	}
	
	public int getNumElements() {
		return stageElements.size();
	}
	
	public StageElement getStageElement(int index) {
		return stageElements.get(index);
	}
}
