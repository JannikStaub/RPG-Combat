package com.kingsaiya.game.map;

public class MapField {

	public static final MapField COLLISION_MAPFIELD = new MapField(true);
	public static final MapField EMPTY_MAPFIELD = new MapField(false);

	private boolean collision;

	public MapField(boolean collision) {
		this.collision = collision;
	}

	public boolean isCollision() {
		return collision;
	}

}
