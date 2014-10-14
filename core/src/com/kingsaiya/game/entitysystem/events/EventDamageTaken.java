package com.kingsaiya.game.entitysystem.events;

import com.kingsaiya.framework.entitysystem.entity.Entity;
import com.kingsaiya.framework.entitysystem.eventsystem.EntityEvent;

public class EventDamageTaken extends EntityEvent {

	private final Entity entity;
	private final int change;
	private final Entity damageSource;

	public EventDamageTaken(final Entity entity, final int change, final Entity damageSource) {
		this.entity = entity;
		this.change = change;
		this.damageSource = damageSource;
	}

	public Entity getEntity() {
		return entity;
	}

	public int getChange() {
		return change;
	}

	public Entity getDamageSource() {
		return damageSource;
	}
}
