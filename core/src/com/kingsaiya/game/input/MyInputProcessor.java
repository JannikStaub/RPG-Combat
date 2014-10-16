package com.kingsaiya.game.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.kingsaiya.framework.entitysystem.entity.Entity;
import com.kingsaiya.framework.entitysystem.eventsystem.EntityEventSystem;
import com.kingsaiya.framework.userinterface.core.UserInterface;
import com.kingsaiya.game.entitysystem.events.movement.MovementEventDirectControl;
import com.kingsaiya.game.map.GameMap;

public class MyInputProcessor implements InputProcessor {

	public static final float MOVEMENT_BORDER_X = 1f;
	public static final float MOVEMENT_BORDER_Y = 0.8f;
	public static final float MOVEMENT_SPEED = 2.75f;

	private UserInterface userInterface;
	private EntityEventSystem eventSystem;
	private GameMap gameMap;
	private Entity controlledUnit;

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

	boolean isWalking = false;

	public MyInputProcessor(final EntityEventSystem eventSystem) {
		this.eventSystem = eventSystem;
		baseAcclerometerX = Gdx.input.getAccelerometerZ() < 0 ? 10 - Gdx.input.getAccelerometerZ() : Gdx.input.getAccelerometerX();
		baseAcclerometerY = Gdx.input.getAccelerometerY();
	}

	public void update() {
		updateAcclerometer();

		boolean walkUp = upKeyPressed || acclerometerXDiff > MOVEMENT_BORDER_X;
		boolean walkDown = downKeyPressed || acclerometerXDiff < -MOVEMENT_BORDER_X;
		boolean walkLeft = leftKeyPressed || acclerometerYDiff > MOVEMENT_BORDER_Y;
		boolean walkRight = rightKeyPressed || acclerometerYDiff < -MOVEMENT_BORDER_Y;

		boolean isNowWalking = walkUp || walkDown || walkLeft || walkRight;
		if (isNowWalking || isWalking) {
			eventSystem.dropEvent(new MovementEventDirectControl(controlledUnit, walkUp, walkLeft, walkRight, walkDown));
			isWalking = isNowWalking;
		}
	}

	private void updateAcclerometer() {
		if (isAdjustControlsMode()) {
			baseAcclerometerX = Gdx.input.getAccelerometerZ() < 0 ? 10 - Gdx.input.getAccelerometerZ() : Gdx.input.getAccelerometerX();
			baseAcclerometerY = Gdx.input.getAccelerometerY();

			acclerometerXDiff = 0;
			acclerometerYDiff = 0;
		} else {
			acclerometerXDiff = baseAcclerometerX - (Gdx.input.getAccelerometerZ() < 0 ? 10 - Gdx.input.getAccelerometerZ() : Gdx.input.getAccelerometerX());
			acclerometerYDiff = baseAcclerometerY - Gdx.input.getAccelerometerY();
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

	public void setControlledUnit(Entity controlledUnit) {
		this.controlledUnit = controlledUnit;
	}

}
