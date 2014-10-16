package com.kingsaiya.game.combat.skill.effect;

import com.kingsaiya.framework.entitysystem.entity.Entity;

public interface ISkillEffectType {

	public void execute(Entity controlledUnit, Entity focusTarget);

}
