package com.kingsaiya.game.entitysystem.events;

import com.kingsaiya.framework.entitysystem.entity.Entity;
import com.kingsaiya.framework.entitysystem.eventsystem.EntityEvent;

public class EventEntityDiedEvent extends EntityEvent {

	public final Entity entity;

	public EventEntityDiedEvent(final Entity entity) {
		this.entity = entity;
	}

}
