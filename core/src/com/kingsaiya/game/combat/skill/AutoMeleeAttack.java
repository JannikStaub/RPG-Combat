package com.kingsaiya.game.combat.skill;

import com.kingsaiya.game.combat.skill.effect.SkillEffectCauseDamage;
import com.kingsaiya.game.combat.unit.Unit;

public class AutoMeleeAttack extends Skill {

	private AutoMeleeAttack(final int skillDuration, final int backswingDuration) {
		super(SkillCastType.AutoAttack, skillDuration, backswingDuration, skillDuration + backswingDuration);
	}

	public static AutoMeleeAttack generateAutoMeleeAttack(final Unit attackingUnit, final Unit targetUnit) {
		final AutoMeleeAttack autoMeleeAttack = new AutoMeleeAttack(1000, 300);
		autoMeleeAttack.addSkillEffectType(new SkillEffectCauseDamage(1));
		return autoMeleeAttack;
	}
}
