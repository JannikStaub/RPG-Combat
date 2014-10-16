package com.kingsaiya.game.combat.skill.costs;

import com.kingsaiya.framework.entitysystem.entity.Entity;

public interface ISkillCostType {

	public boolean isPossible(Entity controlledUnit, Entity focusTarget);

	public void consume(Entity controlledUnit, Entity focusTarget);

}
