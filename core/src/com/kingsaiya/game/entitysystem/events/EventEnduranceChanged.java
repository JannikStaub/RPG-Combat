package com.kingsaiya.game.entitysystem.events;

import com.kingsaiya.framework.entitysystem.entity.Entity;
import com.kingsaiya.framework.entitysystem.eventsystem.EntityEvent;

public class EventEnduranceChanged extends EntityEvent {

	private final Entity entity;
	private final int change;

	public EventEnduranceChanged(final Entity entity, final int change) {
		this.entity = entity;
		this.change = change;
	}

	public Entity getEntity() {
		return entity;
	}

	public int getChange() {
		return change;
	}

}
