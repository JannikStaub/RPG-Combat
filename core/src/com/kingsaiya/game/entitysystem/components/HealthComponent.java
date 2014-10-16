package com.kingsaiya.game.entitysystem.components;

import com.kingsaiya.framework.entitysystem.entity.AbstractEntityComponent;

public class HealthComponent extends AbstractEntityComponent {

	private int maxHitpoints = 100;
	private int currentHitpoints = maxHitpoints;

	private int maxMana = 100;
	private int currentMana = maxHitpoints;

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

	public void adjustCurrentMana(int change) {
		currentMana = Math.max(0, Math.min(currentMana + change, maxMana));
	}

	public void setCurrentMana(int currentMana) {
		this.currentMana = currentMana;
	}

	public int getMaxMana() {
		return maxMana;
	}

	public int getCurrentMana() {
		return currentMana;
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
