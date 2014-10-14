package com.kingsaiya.game.entitysystem.events;

import com.kingsaiya.framework.entitysystem.entity.Entity;
import com.kingsaiya.framework.entitysystem.eventsystem.EntityEvent;

public class MovementEventNewTargetPosition extends EntityEvent {

	private final Entity entity;
	private final float x;
	private final float y;

	public MovementEventNewTargetPosition(final Entity entity, final float x, final float y) {
		this.entity = entity;
		this.x = x;
		this.y = y;
	}

	public Entity getEntity() {
		return entity;
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

}
