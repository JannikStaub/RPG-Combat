package com.kingsaiya.framework.entitysystem.eventsystem;

import com.kingsaiya.framework.entitysystem.entity.Entity;

public class EntityInitializedEvent extends EntityEvent {

	private final Entity entity;

	public EntityInitializedEvent(final Entity entity) {
		this.entity = entity;
		entity.registerEntityComponents();
	}

	public Entity getEntity() {
		return entity;
	}
}
