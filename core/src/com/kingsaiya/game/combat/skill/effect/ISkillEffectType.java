package com.kingsaiya.game.combat.skill.effect;

import com.kingsaiya.game.combat.unit.Unit;

public interface ISkillEffectType {

	public void execute(Unit controlledUnit, Unit focusTarget);

}
