package com.kingsaiya.game.combat.skill.costs;

import com.kingsaiya.game.combat.unit.Unit;

public interface ISkillCostType {

	public boolean isPossible(Unit controlledUnit, Unit focusTarget);

	public void consume(Unit controlledUnit, Unit focusTarget);

}
