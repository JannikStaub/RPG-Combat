package com.kingsaiya.game.entitysystem.components;

import com.kingsaiya.framework.entitysystem.entity.AbstractEntityComponent;

public class HealthComponent extends AbstractEntityComponent {

	private int maxHitpoints = 100;
	private int currentHitpoints = maxHitpoints;

	private int maxEndurance = 100;
	private int currentEndurance = maxHitpoints;

	public void adjustCurrentHitpoints(int change) {
		currentHitpoints = Math.max(0, Math.min(currentHitpoints + change, maxHitpoints));
	}

	public void setCurrentHitpoints(int currentHitpoints) {
		this.currentHitpoints = currentHitpoints;
	}

	public int getMaxHitpoints() {
		return maxHitpoints;
	}

	public int getCurrentHitpoints() {
		return currentHitpoints;
	}

	public void adjustCurrentEndurance(int change) {
		currentEndurance = Math.max(0, Math.min(currentEndurance + change, maxEndurance));
	}

	public void setCurrentEndurance(int currentEndurance) {
		this.currentEndurance = currentEndurance;
	}

	public int getMaxEndurance() {
		return maxEndurance;
	}

	public int getCurrentEndurance() {
		return currentEndurance;
	}

	public boolean isDead() {
		return currentHitpoints <= 0;
	}

}
