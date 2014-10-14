package com.kingsaiya.game.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.kingsaiya.framework.userinterface.core.UserInterface;
import com.kingsaiya.game.combat.dummy.Vector2f;
import com.kingsaiya.game.combat.unit.Unit;
import com.kingsaiya.game.map.GameMap;

public class MyInputProcessor implements InputProcessor {

	public static final float MOVEMENT_BORDER_X = 1f;
	public static final float MOVEMENT_BORDER_Y = 0.8f;
	public static final float MOVEMENT_SPEED = 2.75f;

	private UserInterface userInterface;
	private GameMap gameMap;
	private Unit controlledUnit;

	// keys
	private boolean upKeyPressed = false;
	private boolean leftKeyPressed = false;
	private boolean rightKeyPressed = false;
	private boolean downKeyPressed = false;

	// acclerometer
	private boolean pointer0Pressed = false;
	private boolean pointer1Pressed = false;
	private float baseAcclerometerX;
	private float baseAcclerometerY;
	private float acclerometerXDiff = 0;
	private float acclerometerYDiff = 0;

	int xMovement = 0;
	int yMovement = 0;

	private final Vector2f cachedVector2f = new Vector2f();

	public MyInputProcessor() {
		baseAcclerometerX = Gdx.input.getAccelerometerX();
		baseAcclerometerY = Gdx.input.getAccelerometerY();
	}

	public void update() {
		xMovement = 0;
		yMovement = 0;

		updateAcclerometer();
		updateKeys();

		float targetX = controlledUnit.getPosition().x + xMovement * MOVEMENT_SPEED * Gdx.graphics.getDeltaTime();
		float targetY = controlledUnit.getPosition().y + yMovement * MOVEMENT_SPEED * Gdx.graphics.getDeltaTime();
		int currentMapFieldX = (int) controlledUnit.getPosition().x;
		int currentMapFieldY = (int) controlledUnit.getPosition().y;
		int targetMapFieldX = (int) targetX;
		int targetMapFieldY = (int) targetY;

		// check collision when crossing mapfields
		if (currentMapFieldX < targetMapFieldX) {
			if (gameMap.getMapField(targetMapFieldX, currentMapFieldY).isCollision()) {
				targetX = targetMapFieldX - 0.01f;
				targetMapFieldX = currentMapFieldX;
			}
		} else if (currentMapFieldX > targetMapFieldX) {
			if (gameMap.getMapField(targetMapFieldX, currentMapFieldY).isCollision()) {
				targetX = currentMapFieldX;
				targetMapFieldX = currentMapFieldX;
			}
		}
		if (currentMapFieldY < targetMapFieldY) {
			if (gameMap.getMapField(targetMapFieldX, targetMapFieldY).isCollision()) {
				targetY = targetMapFieldY - 0.01f;
			}
		} else if (currentMapFieldY > targetMapFieldY) {
			if (gameMap.getMapField(targetMapFieldX, targetMapFieldY).isCollision()) {
				targetY = currentMapFieldY;
			}
		}

		controlledUnit.getPosition().set(targetX, targetY);
	}

	private void updateAcclerometer() {
		if (isAdjustControlsMode()) {
			baseAcclerometerX = Gdx.input.getAccelerometerX();
			baseAcclerometerY = Gdx.input.getAccelerometerY();

			acclerometerXDiff = 0;
			acclerometerYDiff = 0;
		} else {
			acclerometerXDiff = baseAcclerometerX - Gdx.input.getAccelerometerX();
			acclerometerYDiff = baseAcclerometerY - Gdx.input.getAccelerometerY();

			if (controlledUnit != null) {
				if (acclerometerYDiff < -MOVEMENT_BORDER_Y) {
					xMovement = 1;
				} else if (acclerometerYDiff > MOVEMENT_BORDER_Y) {
					xMovement = -1;
				}

				if (acclerometerXDiff < -MOVEMENT_BORDER_X) {
					yMovement = 1;
				} else if (acclerometerXDiff > MOVEMENT_BORDER_X) {
					yMovement = -1;
				}
			}
		}
	}

	private void updateKeys() {
		if (upKeyPressed) {
			yMovement = -1;
		}
		if (downKeyPressed) {
			yMovement = 1;
		}
		if (leftKeyPressed) {
			xMovement = -1;
		}
		if (rightKeyPressed) {
			xMovement = 1;
		}
	}

	public boolean isAdjustControlsMode() {
		return pointer0Pressed && pointer1Pressed;
	}

	public float getAcclerometerXDiff() {
		return acclerometerXDiff;
	}

	public float getAcclerometerYDiff() {
		return acclerometerYDiff;
	}

	@Override
	public boolean touchDown(final int screenX, final int screenY, final int pointer, final int button) {
		if (userInterface.handleTouch(screenX, screenY, true, false)) {
			return true;
		}

		if (pointer == 0) {
			pointer0Pressed = true;
		}
		if (pointer == 1) {
			pointer1Pressed = true;
		}
		return true;
	}

	@Override
	public boolean touchUp(final int screenX, final int screenY, final int pointer, final int button) {
		if (userInterface.handleTouch(screenX, screenY, false, false)) {
			return true;
		}
		if (pointer == 0) {
			pointer0Pressed = false;
		}
		if (pointer == 1) {
			pointer1Pressed = false;
		}
		return false;
	}

	@Override
	public boolean keyDown(final int keycode) {
		switch (keycode) {
		case Keys.UP:
		case Keys.W:
			upKeyPressed = true;
			return true;
		case Keys.LEFT:
		case Keys.A:
			leftKeyPressed = true;
			return true;
		case Keys.RIGHT:
		case Keys.D:
			rightKeyPressed = true;
			return true;
		case Keys.DOWN:
		case Keys.S:
			downKeyPressed = true;
			return true;
		}
		return false;
	}

	@Override
	public boolean keyUp(final int keycode) {
		switch (keycode) {
		case Keys.UP:
		case Keys.W:
			upKeyPressed = false;
			return true;
		case Keys.LEFT:
		case Keys.A:
			leftKeyPressed = false;
			return true;
		case Keys.RIGHT:
		case Keys.D:
			rightKeyPressed = false;
			return true;
		case Keys.DOWN:
		case Keys.S:
			downKeyPressed = false;
			return true;
		}
		return false;
	}

	@Override
	public boolean keyTyped(final char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(final int screenX, final int screenY, final int pointer) {
		if (userInterface.handleTouch(screenX, screenY, true, true)) {
			return true;
		}
		return false;
	}

	@Override
	public boolean mouseMoved(final int screenX, final int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(final int amount) {
		// TODO Auto-generated method stub
		return false;
	}

	public void setUserInterface(UserInterface userInterface) {
		this.userInterface = userInterface;
	}

	public void setGameMap(GameMap gameMap) {
		this.gameMap = gameMap;
	}

	public void setControlledUnit(Unit controlledUnit) {
		this.controlledUnit = controlledUnit;
	}

}
