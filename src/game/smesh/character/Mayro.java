package game.smesh.character;

import com.ebet.cnge.engine.Camera;
import com.ebet.cnge.engine.Util;
import com.ebet.cnge.engine.window.Input;
import game.ccd.*;
import game.smesh.animation.Animation;
import game.smesh.HitBox;
import game.smesh.animation.CharacterSprite;
import game.smesh.animation.Sprite;
import game.smesh.stage.Stage;

import javax.sound.sampled.Line;

import static game.assets.Game_Assets.*;
import static game.smesh.Units.unitsPerSecond;
import static game.smesh.Units.unitsPerSecondSquared;
import static org.lwjgl.glfw.GLFW.*;

public class Mayro {
	public static final Sprite SPRITE_NEUTRAL = new CharacterSprite(mayro_neutral, 239, 126, new HitBox[] {
		new HitBox(268, 108, 62, 85), // right leg
		new HitBox(171, 100, 68, 62), // left leg
		new HitBox(188, 145, 102, 117), // body
		new HitBox(266, 204, 65, 57), // right arm
		new HitBox(148, 185, 73, 75), // left arm
		new HitBox(163, 288, 155, 60), // head upper
		new HitBox(181, 249, 122, 46), // head lower
		new HitBox(303, 295, 60, 45), // nose
		new HitBox(184, 345, 130, 63), // hat
	});
	
	public static final Sprite SPRITE_WALK_0 = new CharacterSprite(mayro_walk0, 239, 126, new HitBox[] {
		new HitBox(199, 101, 97, 73), // legs
		new HitBox(188, 145, 102, 117), // body
		new HitBox(266, 204, 65, 57), // right arm
		new HitBox(148, 185, 73, 75), // left arm
		new HitBox(163, 288, 155, 60), // head upper
		new HitBox(181, 249, 122, 46), // head lower
		new HitBox(303, 295, 60, 45), // nose
		new HitBox(184, 345, 130, 63), // hat
	});
	
	public static final Animation ANIMATION_NEUTRAL = new Animation(new int[] {
		0,
	}, new Sprite[] {
		SPRITE_NEUTRAL
	});
	
	public static final Animation ANIMATION_WALK = new Animation(new int[] {
		6, 6
		
	}, new Sprite[] {
		SPRITE_WALK_0, SPRITE_NEUTRAL
	});
	
	/* members */
	
	private float x;
	private float y;
	
	private float walkAcceleration = unitsPerSecondSquared(48);
	private float maxWalkSpeed = unitsPerSecond(24);
	private float gravity = unitsPerSecondSquared(24);
	private float jumpSpeed = unitsPerSecond(24);
	
	private float velocityX;
	private float velocityY;
	
	private boolean direction;
	
	private Animation currentAnimation;
	private Sprite currentSprite;
	private HitBox[] currentHitBoxes;
	
	private LineLoop collisionLoop;
	
	/* constructor */
	
	public Mayro(float x, float y) {
		this.x = x;
		this.y = y;
		
		direction = true;
		
		currentAnimation = ANIMATION_WALK;
		
		collisionLoop = new LineLoop(new Vec[] {
			new Vec(0, 0),
			new Vec(1, 2),
			new Vec(0, 4),
			new Vec(-1, 2),
		}, true);
	}
	
	/* methods */
	
	public void update(Input input, Stage stage) {
		/* gravity */
		velocityY -= gravity;
		
		/* input */
		var holdLeft = input.getKeyHeld(GLFW_KEY_A);
		var holdRight = input.getKeyHeld(GLFW_KEY_D);
		var pressUp = input.getKeyPressed(GLFW_KEY_W);
		
		if (holdLeft && !holdRight) {
			/* we can't add to acceleration more than */
			/* would push us over the max walk speed */
			var minAcceleration = velocityX + maxWalkSpeed;
			if (minAcceleration < 0)
				minAcceleration = 0;
			
			velocityX -= Math.min(minAcceleration, walkAcceleration);
			
			direction = false;
			
		} else if (holdRight && !holdLeft) {
			var minAcceleration = maxWalkSpeed - velocityX;
			if (minAcceleration < 0)
				minAcceleration = 0;
			
			velocityX += Math.min(minAcceleration, walkAcceleration);
			
			direction = true;
			
		} else {
			if (velocityX < 0) {
				velocityX += walkAcceleration;
				if (velocityX > 0)
					velocityX = 0;
				
			} else if (velocityX > 0) {
				velocityX -= walkAcceleration;
				if (velocityX < 0)
					velocityX = 0;
			}
		}
		
		if (pressUp) {
			velocityY = jumpSpeed;
		}
		
		/* collision */
		var movement = new CCDLine(x, y, x + velocityX, y + velocityY);
		
		var elementCounter = new Util.Capture<Integer>().set(0);
		var loopCounter = new Util.Capture<Integer>().set(0);
		
		var debugCol = (IEnvironmentGather)(loopOffset) -> {
			if (elementCounter.get() == 0) {
				elementCounter.set(1);
				
				var element = stage.getStageElement(0);
				var loop = element.getLineLoop(0);
				var line = loop.getLine(loop.getNumLines() - 1);
				
				loopOffset.set(element.getX(), element.getY());
				
				return new LineLoop(new Vec[] { line.start, line.end }, false);
				
			} else {
				return null;
			}
		};
		
		var colGather = (IEnvironmentGather)(loopOffset) -> {
			/* skip over all elements with 0 loops */
			/* or exit out of an element and move to the next */
			while (elementCounter.get() < stage.getNumElements() && loopCounter.get() == stage.getStageElement(elementCounter.get()).getNumLineLoops()) {
				elementCounter.set(elementCounter.get() + 1);
				loopCounter.set(0);
			}
			
			if (elementCounter.get() < stage.getNumElements()) {
				var element = stage.getStageElement(elementCounter.get());
				
				var ret = element.getLineLoop(loopCounter.get());
				loopOffset.set(element.getX(), element.getY());
				
				loopCounter.set(loopCounter.get() + 1);
				
				return ret;
				
			} else {
				return null;
			}
		};
		
		var collision = CCD.test(collisionLoop, movement, colGather);
		
		/* finally do the move */
		if (collision == null) {
			x += velocityX;
			y += velocityY;
			
		} else {
			/* stop all movement in direction of wall */
			var velocity = new Vec(velocityX, velocityY);
			var wallVec = collision.line.toVector();
			
			velocity.project(wallVec);
			
			velocityX = velocity.x;
			velocityY = velocity.y;
			
			/* move to collision spot */
			x = collision.position.x;
			y = collision.position.y;
		}
		
		if (velocityX == 0) {
			currentAnimation = ANIMATION_NEUTRAL;
		} else {
			if (currentAnimation != ANIMATION_WALK) {
				ANIMATION_WALK.reset();
				currentAnimation = ANIMATION_WALK;
			}
		}
		
		/* update animation */
		currentAnimation.update();
		
		var animationSprite = currentAnimation.getSprite();
		/* if we're switching to a new sprite */
		if (animationSprite != currentSprite) {
			currentSprite = animationSprite;
			currentHitBoxes = ((CharacterSprite)animationSprite).copyHitBoxes();
		}
		
		/* update hitbox positions */
		var numHitBoxes = currentHitBoxes.length;
		for (var i = 0; i < numHitBoxes; ++i)
			currentHitBoxes[i].setOrigin(x, y, direction);
	}
	
	public float getX() {
		return x;
	}
	
	public float getY() {
		return y;
	}
	
	public void render(Camera camera) {
		/* render player */
		currentSprite.getTexture().bind();
		texture_shader.enable(currentSprite.getModel(x, y, direction), camera.get_projection_view());
		texture_shader.give(1, 1, 1, 1);
		rect.render();
		
		/* render hitboxes */
		var numHitBoxes = currentHitBoxes.length;
		for (var i = 0; i < numHitBoxes; ++i)
			currentHitBoxes[i].render(camera);
	}
}
