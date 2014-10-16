package com.kingsaiya.game.entitysystem.events.movement;

import com.kingsaiya.framework.entitysystem.entity.Entity;
import com.kingsaiya.framework.entitysystem.eventsystem.EntityEvent;

public class MovementEventDirectControl extends EntityEvent {

	private final Entity entity;
	private final boolean walkUp;
	private final boolean walkLeft;
	private final boolean walkRight;
	private final boolean walkDown;

	public MovementEventDirectControl(Entity entity, boolean walkUp, boolean walkLeft, boolean walkRight, boolean walkDown) {
		this.entity = entity;
		this.walkUp = walkUp;
		this.walkLeft = walkLeft;
		this.walkRight = walkRight;
		this.walkDown = walkDown;
	}

	public Entity getEntity() {
		return entity;
	}

	public boolean isWalkUp() {
		return walkUp;
	}

	public boolean isWalkLeft() {
		return walkLeft;
	}

	public boolean isWalkRight() {
		return walkRight;
	}

	public boolean isWalkDown() {
		return walkDown;
	}
}
