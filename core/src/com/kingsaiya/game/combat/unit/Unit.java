package com.kingsaiya.game.combat.unit;

import com.kingsaiya.game.combat.dummy.Vector2f;
import com.kingsaiya.game.combat.system.CombatSystem;

public class Unit {

	private final Vector2f position = new Vector2f();
	private final Vector2f direction = new Vector2f();

	private int maxHitpoints = 100;
	private int currentHitpoints = maxHitpoints;

	private int maxMana = 100;
	private int currentMana = maxMana;

	private int maxEndurance = 100;
	private int currentEndurance = maxEndurance;

	private CombatSystem combatSystem;

	private Unit focusTarget;

	public void update() {
		getCreateCombatSystem().update(focusTarget);
	}

	public CombatSystem getCreateCombatSystem() {
		if (combatSystem == null) {
			combatSystem = new CombatSystem(this);
		}
		return combatSystem;
	}

	public Vector2f getPosition() {
		return position;
	}

	public Vector2f getDirection() {
		return direction;
	}

	public int getCurrentHitpoints() {
		return currentHitpoints;
	}

	public void adjustCurrentHitpoints(int change) {
		currentHitpoints = Math.max(0, Math.min(currentHitpoints + change, maxHitpoints));
	}

	public int getMaxHitpoints() {
		return maxHitpoints;
	}

	public int getCurrentMana() {
		return currentMana;
	}

	public void adjustCurrentMana(int change) {
		currentMana = Math.max(0, Math.min(currentMana + change, maxMana));
	}

	public int getMaxMana() {
		return maxMana;
	}

	public int getCurrentEndurance() {
		return currentEndurance;
	}

	public void adjustCurrentEndurance(int change) {
		currentEndurance = Math.max(0, Math.min(currentEndurance + change, maxEndurance));
	}

	public int getMaxEndurance() {
		return maxEndurance;
	}

	public Unit getFocusTarget() {
		return focusTarget;
	}

	public void setFocusTarget(Unit focusTarget) {
		this.focusTarget = focusTarget;
	}
}
