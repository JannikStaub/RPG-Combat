package com.kingsaiya.game.entitysystem.events.movement;

import com.kingsaiya.framework.entitysystem.entity.Entity;
import com.kingsaiya.framework.entitysystem.eventsystem.EntityEvent;

public class MovementEventAtTargetPosition extends EntityEvent {

	private final Entity entity;

	public MovementEventAtTargetPosition(final Entity entity) {
		this.entity = entity;
	}

	public Entity getEntity() {
		return entity;
	}

}
