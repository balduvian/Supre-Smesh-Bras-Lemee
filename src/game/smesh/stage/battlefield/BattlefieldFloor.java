package game.smesh.stage.battlefield;

import com.ebet.cnge.engine.Camera;
import game.ccd.LineLoop;
import game.ccd.Vec;
import game.smesh.animation.Sprite;
import game.smesh.stage.StageElement;

import javax.sound.sampled.Line;

import static game.assets.Game_Assets.*;

public class BattlefieldFloor extends StageElement {
	public BattlefieldFloor(float x, float y) {
		super(x, y, new Sprite(battlefield_floor, 1826, 1273),
			new LineLoop[] {
				new LineLoop(new Vec[] {
					new Vec(70, 1273),
					new Vec(121, 1061),
					new Vec(118, 940),
					new Vec(295, 941),
					new Vec(647, 784),
					new Vec(803, 454),
					new Vec(1064, 508),
					new Vec(1327, 179),
					new Vec(1360, 287),
					new Vec(1629, 177),
					new Vec(1700, 27),
					new Vec(2017, 353),
					new Vec(2061, 522),
					new Vec(2150, 526),
					new Vec(2196, 384),
					new Vec(2267, 343),
					new Vec(2263, 123),
					new Vec(2327, 4),
					new Vec(2391, 123),
					new Vec(2386, 334),
					new Vec(2532, 301),
					new Vec(2695, 649),
					new Vec(2841, 579),
					new Vec(2894, 421),
					new Vec(2942, 571),
					new Vec(3126, 756),
					new Vec(3220, 725),
					new Vec(3464, 960),
					new Vec(3544, 962),
					new Vec(3576, 987),
					new Vec(3596, 1273),
				}, true)
			}
		);
	}
	
	@Override
	public void update() {
		/* battlefield floor does nothing */
	}
}
