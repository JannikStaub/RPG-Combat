package com.kingsaiya.game.combat.skill;

import java.util.ArrayList;

import com.kingsaiya.game.combat.skill.condition.ISkillConditionType;
import com.kingsaiya.game.combat.skill.costs.ISkillCostType;
import com.kingsaiya.game.combat.skill.effect.ISkillEffectType;

public class Skill {

	private final SkillCastType castType;
	private final int skillDuration;
	private final int backswingDuration;
	private final int cooldown;
	private final ArrayList<ISkillCostType> skillCosts = new ArrayList<ISkillCostType>();
	private final ArrayList<ISkillConditionType> skillConditions = new ArrayList<ISkillConditionType>();
	private final ArrayList<ISkillEffectType> skillEffects = new ArrayList<ISkillEffectType>();

	public Skill(final SkillCastType castType, final int skillDuration, final int backswingDuration, final int cooldown) {
		this.castType = castType;
		this.skillDuration = skillDuration;
		this.backswingDuration = backswingDuration;
		this.cooldown = cooldown;
	}

	public SkillCastType getCastType() {
		return castType;
	}

	public int getSkillDuration() {
		return skillDuration;
	}

	public int getBackswingDuration() {
		return backswingDuration;
	}

	public int getCooldown() {
		return cooldown;
	}

	public ArrayList<ISkillCostType> getSkillCosts() {
		return skillCosts;
	}

	public void addSkillCostType(final ISkillCostType costType) {
		skillCosts.add(costType);
	}

	public ArrayList<ISkillConditionType> getSkillConditions() {
		return skillConditions;
	}

	public void addSkillCondition(final ISkillConditionType conditionType) {
		skillConditions.add(conditionType);
	}

	public ArrayList<ISkillEffectType> getSkillEffects() {
		return skillEffects;
	}

	public void addSkillEffectType(final ISkillEffectType costType) {
		skillEffects.add(costType);
	}

}
