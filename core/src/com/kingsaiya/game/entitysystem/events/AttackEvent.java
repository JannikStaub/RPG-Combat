package com.kingsaiya.game.entitysystem.events;

import com.kingsaiya.framework.entitysystem.entity.Entity;
import com.kingsaiya.framework.entitysystem.eventsystem.EntityEvent;

public class AttackEvent extends EntityEvent {

	public final Entity entity;
	public final int damage;
	public final int attackDurationInGameTicks;
	public final long attackStart;

	public AttackEvent(final Entity entity, final int damage, final int attackDurationInGameTicks) {
		this.entity = entity;
		this.damage = damage;
		this.attackDurationInGameTicks = attackDurationInGameTicks;
		this.attackStart = System.currentTimeMillis();
	}

}
