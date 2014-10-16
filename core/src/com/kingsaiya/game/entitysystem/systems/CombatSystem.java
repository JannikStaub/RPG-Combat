package com.kingsaiya.game.entitysystem.systems;

import java.util.ArrayList;

import com.kingsaiya.framework.entitysystem.entity.Entity;
import com.kingsaiya.framework.entitysystem.eventsystem.EntityEventSystem;
import com.kingsaiya.framework.entitysystem.system.AbstractEntitySystem;
import com.kingsaiya.framework.tools.TimeTool;
import com.kingsaiya.game.combat.skill.AutoMeleeAttack;
import com.kingsaiya.game.combat.skill.Skill;
import com.kingsaiya.game.combat.skill.condition.ISkillConditionType;
import com.kingsaiya.game.combat.skill.costs.ISkillCostType;
import com.kingsaiya.game.combat.skill.effect.ISkillEffectType;
import com.kingsaiya.game.entitysystem.components.CombatComponent;
import com.kingsaiya.game.entitysystem.components.MovementComponent;
import com.kingsaiya.game.entitysystem.events.AttackEvent;

public class CombatSystem extends AbstractEntitySystem {

	private final ArrayList<Entity> entitys = new ArrayList<Entity>();

	public CombatSystem(EntityEventSystem eventSystem) {
		super(eventSystem);
	}

	@Override
	protected void handleNewEntity(Entity entity) {
		final CombatComponent combatComponent = entity.getEntityComponent(CombatComponent.class);
		if (combatComponent != null) {
			entitys.add(entity);
		}
	}

	@Override
	public void update() {
		for (final Entity entity : entitys) {

			CombatComponent combatComponent = entity.getEntityComponent(CombatComponent.class);
			// check that no skill is currently channeled
			if (!updateSkillChanneling(combatComponent)) {

				// has target in focus
				Entity focusTarget = combatComponent.getFocusTarget();
				if (focusTarget != null) {

					// check timing
					if (combatComponent.getNextAutoAttackTime() <= TimeTool.getGameTick()) {

						MovementComponent movementComponent = entity.getEntityComponent(MovementComponent.class);
						MovementComponent targetMovementComponent = focusTarget.getEntityComponent(MovementComponent.class);
						if (movementComponent != null && targetMovementComponent != null) {

							// is in range
							if (movementComponent.getPosition().distanceSquared(targetMovementComponent.getPosition()) <= MEELE_ATTACK_RANGE) {

								// update auto combat
								Skill autoAttack = combatComponent.getNextAutoAttackSkill();
								if (autoAttack != null) {
									combatComponent.setNextAutoAttackSkill(null);
								} else {
									autoAttack = AutoMeleeAttack.generateAutoMeleeAttack(entity, focusTarget);
								}

								// use skill costs
								for (final ISkillCostType skillCost : autoAttack.getSkillCosts()) {
									skillCost.consume(entity, focusTarget);
								}

								// trigger effects
								for (final ISkillEffectType skillEffect : autoAttack.getSkillEffects()) {
									skillEffect.execute(entity, focusTarget);
								}

								eventSystem.dropEvent(new AttackEvent(entity, 0, autoAttack.getCooldown()));

								combatComponent.setNextAutoAttackTime(TimeTool.getGameTick() + autoAttack.getCooldown());
							}
						}
					}
				}
			}

		}
	}

	private static final int MEELE_ATTACK_RANGE = 2;

	public boolean useSkill(final Entity entity, final Skill skill, final Entity focusTarget) {

		// check if skill conditions are met
		for (final ISkillConditionType skillCondition : skill.getSkillConditions()) {
			if (!skillCondition.isPossible(entity, focusTarget)) {
				return false;
			}
		}

		// check if skill costs are available
		for (final ISkillCostType skillCost : skill.getSkillCosts()) {
			if (!skillCost.isPossible(entity, focusTarget)) {
				return false;
			}
		}

		CombatComponent combatComponent = entity.getEntityComponent(CombatComponent.class);

		// skill will be executed!
		switch (skill.getCastType()) {
		case AutoAttack:
			combatComponent.setNextAutoAttackSkill(skill);
			break;
		case Channeled:
			// set channeling skill
			combatComponent.setCurrentlyChannelingSkill(skill);
			combatComponent.setChannelingStartTime(TimeTool.getGameTick());

			// continue downwards! no break!
		case Instant:
			// use skill costs
			for (final ISkillCostType skillCost : skill.getSkillCosts()) {
				skillCost.consume(entity, focusTarget);
			}

			// trigger effects
			for (final ISkillEffectType skillEffect : skill.getSkillEffects()) {
				skillEffect.execute(entity, focusTarget);
			}
			break;
		default:
			System.err.println("ERROR unhandled casttype " + skill.getCastType() + "!");
			return false;
		}

		return true;
	}

	public boolean updateSkillChanneling(CombatComponent combatComponent) {
		Skill currentlyChannelingSkill = combatComponent.getCurrentlyChannelingSkill();
		if (currentlyChannelingSkill != null) {
			// update channeling skill
			if (combatComponent.getChannelingStartTime() + currentlyChannelingSkill.getSkillDuration() > TimeTool.getGameTick()) {
				combatComponent.setCurrentlyChannelingSkill(null);
				combatComponent.setChannelingStartTime(-1);
				return false;
			} else {
				return true;
			}
		}
		return false;
	}
}
