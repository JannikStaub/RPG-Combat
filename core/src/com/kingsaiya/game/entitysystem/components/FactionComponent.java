package com.kingsaiya.game.entitysystem.components;

import com.kingsaiya.framework.entitysystem.entity.AbstractEntityComponent;

public class FactionComponent extends AbstractEntityComponent {

	private int teamNumber = -1;

	public void setTeamNumber(int teamNumber) {
		this.teamNumber = teamNumber;
	}

	public int getTeamNumber() {
		return teamNumber;
	}

	public boolean isEnemy(int targetFaction) {
		if (teamNumber == -1 || targetFaction == -1) {
			return true;
		}
		return teamNumber != targetFaction;
	}
}
