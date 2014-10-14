package com.kingsaiya.game.map;

public class GameMap {
	private MapField[] mapFields;
	private int mapWidth;
	private int mapHeight;

	public GameMap(final int width, final int height) {
		mapWidth = width;
		mapHeight = height;
		mapFields = new MapField[mapWidth * mapHeight];

		int fieldIndex = 0;
		for (int y = 0; y < mapHeight; y++) {
			for (int x = 0; x < mapWidth; x++) {
				boolean wall = x == 0 || y == 0 || x == mapWidth - 1 || y == mapHeight - 1;
				mapFields[fieldIndex++] = wall ? MapField.COLLISION_MAPFIELD : MapField.EMPTY_MAPFIELD;
			}
		}
	}

	public int getMapWidth() {
		return mapWidth;
	}

	public int getMapHeight() {
		return mapHeight;
	}

	public MapField getMapField(int x, int y) {
		return mapFields[x + y * mapWidth];
	}

}
