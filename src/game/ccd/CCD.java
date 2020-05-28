package game.ccd;

import org.joml.Vector2f;

import static game.ccd.Util.*;

public class CCD {
	public static final float CCD_SKEK = 0.000001f;
	
	public static Collision test(LineLoop player, CCDLine movement, IEnvironmentGather gather) {
		var closestT = Float.MAX_VALUE;
		var collision = (Collision)null;
		
		var flippedMovement = movement.copy().flipEnd();
		
		LineLoop loop;
		Vec loopOffset = new Vec(0, 0);
		
		while ((loop = gather.gather(loopOffset)) != null) {
			for (var i = 0; i < player.getNumPoints(); ++i) {
				var point = player.getPoint(i);
				
				for (var j = 0; j < loop.getNumLines(); ++j) {
					var tempCollision = collide(movement.copy().add(point), point, loop.getLine(j).copy().add(loopOffset), closestT);
					
					if (tempCollision != null) {
						collision = tempCollision;
						closestT = collision.tValue;
					}
				}
			}
			
			/*
			for (var i = 0; i < player.getNumLines(); ++i) {
				for (var j = 0; j < loop.getNumPoints(); ++j) {
					// distance from the player movement origin to the point in the environment
					var offset = loop.getPoint(j).copy().sub(flippedMovement.start).add(loopOffset);
					
					var tempCollision = collide(flippedMovement.copy().add(offset), offset, player.getLine(i), closestT);
					
					if (tempCollision != null) {
						collision = tempCollision;
						closestT = collision.tValue;
					}
				}
			}*/
		}
		
		return collision;
	}
	
	private static Collision collide(CCDLine pointOffsetMovement, Vec pointOffset, CCDLine line, float closestT) {
		var lineVector = line.toVector();
		
		var lineAngle = lineVector.angle();
		var lineWidth = lineVector.length();
		
		var relativeStart = pointOffsetMovement.start.copy().sub(line.start).rotate(-lineAngle);
		var   relativeEnd = pointOffsetMovement.end.copy().sub(line.start).rotate(-lineAngle);

		var moveT = invInterp(relativeStart.y, relativeEnd.y, 0.f);
		
		/* only collide at the closest wall */
		if (moveT < closestT) {
			/* test if movement intersects with wall */
			var collisionX = interp(relativeStart.x, relativeEnd.x, moveT);
			
			var intersection = inclusiveRange(0.f, collisionX, lineWidth) && inclusiveRange(0.f, moveT, 1.f);
			
			/* if movement intersects with wall */
			if (intersection) {
				var finalPosition = pointOffsetMovement.end.copy().sub(line.start).project(lineVector).add(line.start);
				
				var side = Math.signum(relativeStart.y);
				
				var ccdSkekAdd = new Vec(0, CCD_SKEK * side).rotate(lineAngle);
				
				return new Collision(finalPosition.sub(pointOffset).add(ccdSkekAdd), line, side, moveT);
			}
		}
		
		return null;
	}
}
