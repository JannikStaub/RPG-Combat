package com.kingsaiya.game.entitysystem.components;

import com.kingsaiya.framework.entitysystem.entity.AbstractEntityComponent;
import com.kingsaiya.framework.entitysystem.entity.Entity;
import com.kingsaiya.game.combat.skill.Skill;

public class CombatComponent extends AbstractEntityComponent {

	// channeling
	private Skill currentlyChannelingSkill = null;
	private long channelingStartTime = -1;

	// auto attack
	private Skill nextAutoAttackSkill = null;
	private long nextAutoAttackTime = -1;

	// focus target
	private Entity focusTarget = null;

	public Skill getCurrentlyChannelingSkill() {
		return currentlyChannelingSkill;
	}

	public void setCurrentlyChannelingSkill(Skill currentlyChannelingSkill) {
		this.currentlyChannelingSkill = currentlyChannelingSkill;
	}

	public long getChannelingStartTime() {
		return channelingStartTime;
	}

	public void setChannelingStartTime(long channelingStartTime) {
		this.channelingStartTime = channelingStartTime;
	}

	public Skill getNextAutoAttackSkill() {
		return nextAutoAttackSkill;
	}

	public void setNextAutoAttackSkill(Skill nextAutoAttackSkill) {
		this.nextAutoAttackSkill = nextAutoAttackSkill;
	}

	public long getNextAutoAttackTime() {
		return nextAutoAttackTime;
	}

	public void setNextAutoAttackTime(long nextAutoAttackTime) {
		this.nextAutoAttackTime = nextAutoAttackTime;
	}

	public Entity getFocusTarget() {
		return focusTarget;
	}

	public void setFocusTarget(Entity focusTarget) {
		this.focusTarget = focusTarget;
	}
}
