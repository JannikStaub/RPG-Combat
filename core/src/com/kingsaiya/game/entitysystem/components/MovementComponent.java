package com.kingsaiya.game.entitysystem.components;

import com.kingsaiya.framework.entitysystem.entity.AbstractEntityComponent;
import com.kingsaiya.framework.tools.FastMath;
import com.kingsaiya.framework.tools.TimeTool;
import com.kingsaiya.framework.tools.Vector2f;
import com.kingsaiya.game.entitysystem.components.AnimationComponent.AnimationDirection;

public class MovementComponent extends AbstractEntityComponent {

	private final float speed = 1f;
	private final Vector2f position = new Vector2f();
	private final Vector2f targetPosition = new Vector2f();
	private final Vector2f direction = new Vector2f(0, 1);

	private AnimationDirection animationDirectionType = AnimationDirection.SOUTH;
	private boolean isMoving = false;

	private long movementBlockTime = 0;

	public boolean isMoving() {
		return isMoving;
	}

	public float getSpeed() {
		return speed;
	}

	public Vector2f getPosition() {
		return position;
	}

	public Vector2f getTargetPosition() {
		return targetPosition;
	}

	public AnimationDirection getMovementDirectionType() {
		float rotation = (direction.getAngle() + FastMath.PI) * FastMath.RAD_TO_DEG;
		if (rotation < 45 || rotation > 315) {
			animationDirectionType = AnimationDirection.WEST;
		} else if (rotation < 135) {
			animationDirectionType = AnimationDirection.SOUTH;
		} else if (rotation < 225) {
			animationDirectionType = AnimationDirection.EAST;
		} else {
			animationDirectionType = AnimationDirection.NORTH;
		}
		return animationDirectionType;
	}

	public void setTargetPosition(final float x, final float y) {
		targetPosition.set(x, y);
		isMoving = position.x != targetPosition.x || position.y != targetPosition.y;
	}

	public void setPosition(final Vector2f position) {
		this.setPosition(position.x, position.y);
	}

	public void setPosition(final float x, final float y) {
		position.set(x, y);
		isMoving = position.x != targetPosition.x || position.y != targetPosition.y;
	}

	public Vector2f getDirection() {
		return direction;
	}

	public Vector2f getDirectionToTarget(Vector2f returnValue) {
		return returnValue.set(targetPosition).subtractLocal(position).normalizeLocal();
	}

	public void setMovementBlocked(int blockedGameTicks) {
		movementBlockTime = TimeTool.getGameTick() + blockedGameTicks;
	}

	public boolean isMovementBlocked(long currentGameTick) {
		return movementBlockTime >= currentGameTick;
	}

}
