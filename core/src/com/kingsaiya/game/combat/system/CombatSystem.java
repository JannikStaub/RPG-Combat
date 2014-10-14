package com.kingsaiya.game.combat.system;

import com.kingsaiya.game.combat.dummy.TimeTool;
import com.kingsaiya.game.combat.skill.AutoMeleeAttack;
import com.kingsaiya.game.combat.skill.Skill;
import com.kingsaiya.game.combat.skill.condition.ISkillConditionType;
import com.kingsaiya.game.combat.skill.costs.ISkillCostType;
import com.kingsaiya.game.combat.skill.effect.ISkillEffectType;
import com.kingsaiya.game.combat.unit.Unit;

public class CombatSystem {

	private static final int MEELE_ATTACK_RANGE = 2;

	private final Unit controlledUnit;

	// channeling
	private Skill currentlyChannelingSkill = null;
	private long channelingStartTime = -1;

	// auto attack
	private Skill nextAutoAttackSkill = null;
	private long nextAutoAttackTime = -1;

	public CombatSystem(final Unit controlledUnit) {
		this.controlledUnit = controlledUnit;
	}

	public boolean useSkill(final Skill skill, final Unit focusTarget) {

		// check if skill conditions are met
		for (final ISkillConditionType skillCondition : skill.getSkillConditions()) {
			if (!skillCondition.isPossible(controlledUnit, focusTarget)) {
				return false;
			}
		}

		// check if skill costs are available
		for (final ISkillCostType skillCost : skill.getSkillCosts()) {
			if (!skillCost.isPossible(controlledUnit, focusTarget)) {
				return false;
			}
		}

		// skill will be executed!
		switch (skill.getCastType()) {
		case AutoAttack:
			nextAutoAttackSkill = skill;
			break;
		case Channeled:
			// set channeling skill
			currentlyChannelingSkill = skill;
			channelingStartTime = TimeTool.getGameTick();

			// continue downwards! no break!
		case Instant:
			// use skill costs
			for (final ISkillCostType skillCost : skill.getSkillCosts()) {
				skillCost.consume(controlledUnit, focusTarget);
			}

			// trigger effects
			for (final ISkillEffectType skillEffect : skill.getSkillEffects()) {
				skillEffect.execute(controlledUnit, focusTarget);
			}
			break;
		default:
			System.err.println("ERROR unhandled casttype " + skill.getCastType() + "!");
			return false;
		}

		return true;
	}

	public void update(Unit focusTarget) {

		// check that no skill is currently channeled
		if (!isCurrentlyChannelingSkill()) {

			// has target in focus
			if (focusTarget != null) {

				// check timing
				if (nextAutoAttackTime <= TimeTool.getGameTick()) {

					// is in range
					if (controlledUnit.getPosition().distanceSquared(focusTarget.getPosition()) <= MEELE_ATTACK_RANGE) {

						// update auto combat
						Skill autoAttack;

						if (nextAutoAttackSkill != null) {
							autoAttack = nextAutoAttackSkill;
							nextAutoAttackSkill = null;
						} else {
							autoAttack = AutoMeleeAttack.generateAutoMeleeAttack(controlledUnit, focusTarget);
						}

						// use skill costs
						for (final ISkillCostType skillCost : autoAttack.getSkillCosts()) {
							skillCost.consume(controlledUnit, focusTarget);
						}

						// trigger effects
						for (final ISkillEffectType skillEffect : autoAttack.getSkillEffects()) {
							skillEffect.execute(controlledUnit, focusTarget);
							System.out.println("attack target: " + focusTarget.getCurrentHitpoints());
						}

						nextAutoAttackTime = TimeTool.getGameTick() + autoAttack.getCooldown();
					}
				}
			}
		}
	}

	public boolean isCurrentlyChannelingSkill() {
		if (currentlyChannelingSkill != null) {
			// update channeling skill
			if (channelingStartTime + currentlyChannelingSkill.getSkillDuration() > TimeTool.getGameTick()) {
				currentlyChannelingSkill = null;
				channelingStartTime = -1;

			} else {
				return true;
			}
		}
		return currentlyChannelingSkill != null;
	}
}
