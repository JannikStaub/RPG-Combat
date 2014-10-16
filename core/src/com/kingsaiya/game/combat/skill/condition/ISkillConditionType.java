package com.kingsaiya.game.combat.skill.condition;

import com.kingsaiya.framework.entitysystem.entity.Entity;

public interface ISkillConditionType {

	public boolean isPossible(Entity controlledUnit, Entity focusTarget);
}
