package com.kingsaiya.game.combat.skill.condition;

import com.kingsaiya.game.combat.unit.Unit;

public interface ISkillConditionType {

	public boolean isPossible(Unit controlledUnit, Unit focusTarget);
}
