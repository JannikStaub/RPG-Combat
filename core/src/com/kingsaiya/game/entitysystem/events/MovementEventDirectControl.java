package com.kingsaiya.game.entitysystem.events;

import com.kingsaiya.framework.entitysystem.entity.Entity;
import com.kingsaiya.framework.entitysystem.eventsystem.EntityEvent;

public class MovementEventDirectControl extends EntityEvent {

	private final Entity entity;
	private final double lookDirectionChange;
	private final double turnDirectionChange;
	private final double forwardMotion;
	private final double strafeMotion;

	public MovementEventDirectControl(Entity entity, double lookDirectionChange, double turnDirectionChange, double forwardMotion, double strafeMotion) {
		this.entity = entity;
		this.lookDirectionChange = lookDirectionChange;
		this.turnDirectionChange = turnDirectionChange;
		this.forwardMotion = forwardMotion;
		this.strafeMotion = strafeMotion;
	}

	public Entity getEntity() {
		return entity;
	}

	public double getLookDirectionChange() {
		return lookDirectionChange;
	}

	public double getTurnDirectionChange() {
		return turnDirectionChange;
	}

	public double getForwardMotion() {
		return forwardMotion;
	}

	public double getStrafeMotion() {
		return strafeMotion;
	}
}
