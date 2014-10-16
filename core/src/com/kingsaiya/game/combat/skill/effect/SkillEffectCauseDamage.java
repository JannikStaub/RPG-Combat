package com.kingsaiya.game.combat.skill.effect;

import com.kingsaiya.framework.entitysystem.entity.Entity;
import com.kingsaiya.game.entitysystem.components.HealthComponent;

public class SkillEffectCauseDamage implements ISkillEffectType {

	private int damage;

	public SkillEffectCauseDamage(int damage) {
		this.damage = damage;
	}

	@Override
	public void execute(Entity controlledUnit, Entity focusTarget) {
		HealthComponent healthComponent = focusTarget.getEntityComponent(HealthComponent.class);
		if (healthComponent != null) {
			healthComponent.adjustCurrentHitpoints(-damage);
		}
	}

}
