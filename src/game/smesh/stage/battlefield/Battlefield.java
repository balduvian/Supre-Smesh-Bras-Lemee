package game.smesh.stage.battlefield;

import com.ebet.cnge.engine.Texture;
import game.ccd.Vec;
import game.smesh.stage.Stage;
import game.smesh.stage.StageElement;

import static game.assets.Game_Assets.sky;

public class Battlefield extends Stage {
	public static final float WIDTH = 115;
	public static final float HEIGHT = 64;
	
	public static final float CENTER = WIDTH / 2;
	public static final float FLOOR_HEIGHT = 32;
	
	public Battlefield() {
		super(sky, WIDTH, HEIGHT,
		new Vec[] {
			new Vec(CENTER - 16, FLOOR_HEIGHT),
			new Vec(CENTER - 8, FLOOR_HEIGHT),
			new Vec(CENTER + 8, FLOOR_HEIGHT),
			new Vec(CENTER + 16, FLOOR_HEIGHT),
		},
		new StageElement[] {
			new BattlefieldFloor(CENTER, FLOOR_HEIGHT),
		});
	}
	
	@Override
	public void update() {
	
	}
}
