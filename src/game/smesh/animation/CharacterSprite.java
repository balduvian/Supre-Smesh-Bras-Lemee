package game.smesh.animation;

import com.ebet.cnge.engine.Texture;
import game.smesh.HitBox;

public class CharacterSprite extends Sprite {
	private HitBox[] hitBoxes;
	
	/**
	 * @param hitBoxes a list of Hitboxes for this sprite with offsets relative image origin
	 */
	public CharacterSprite(Texture texture, int centerX, int centerY, HitBox[] hitBoxes) {
		super(texture, centerX, centerY);
		
		this.hitBoxes = hitBoxes;
		var numHitBoxes = getNumHitBoxes();
		
		/* adjust hitboxes so that their offests are based off the origin of this sprite */
		for (var i = 0; i < numHitBoxes; ++i) {
			hitBoxes[i].setOffsetOrigin(this.centerX, this.centerY);
		}
	}
	
	public int getNumHitBoxes() {
		return hitBoxes.length;
	}
	
	public HitBox getHitBox(int index) {
		return hitBoxes[index];
	}
	
	public HitBox[] copyHitBoxes() {
		var num = getNumHitBoxes();
		
		var ret = new HitBox[num];
		
		for (var i = 0; i < num; ++i)
			ret[i] = hitBoxes[i].copy();
		
		return ret;
	}
}
