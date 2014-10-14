package com.kingsaiya.game.combat.skill.effect;

import com.kingsaiya.game.combat.unit.Unit;

public class SkillEffectCauseDamage implements ISkillEffectType {

	private int damage;

	public SkillEffectCauseDamage(int damage) {
		this.damage = damage;
	}

	@Override
	public void execute(Unit controlledUnit, Unit focusTarget) {
		focusTarget.adjustCurrentHitpoints(-damage);
	}

}
