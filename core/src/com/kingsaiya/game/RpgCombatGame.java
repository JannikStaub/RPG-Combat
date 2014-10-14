package com.kingsaiya.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Matrix4;
import com.kingsaiya.framework.camera.GameCamera;
import com.kingsaiya.framework.entitysystem.eventsystem.EntityEventSystem;
import com.kingsaiya.framework.tools.RenderTool;
import com.kingsaiya.framework.userinterface.core.UserInterface;
import com.kingsaiya.game.combat.dummy.TimeTool;
import com.kingsaiya.game.combat.unit.Unit;
import com.kingsaiya.game.entitysystem.systems.AnimationSystem;
import com.kingsaiya.game.entitysystem.systems.MovementSystem;
import com.kingsaiya.game.input.MyInputProcessor;
import com.kingsaiya.game.map.GameMap;
import com.kingsaiya.game.userinterface.UIActionSlot;
import com.kingsaiya.game.userinterface.UIHudPlayerStatusPanel;
import com.kingsaiya.game.userinterface.UIWindow;

public class RpgCombatGame extends ApplicationAdapter {

	private ShapeRenderer shapeRenderer;
	private SpriteBatch spriteBatch;
	private GameCamera camera;
	private Matrix4 uiProjectionMatrix;

	private final UserInterface userInterface = new UserInterface();

	private final EntityEventSystem eventSystem = new EntityEventSystem();
	private final MovementSystem movementSystem = new MovementSystem(eventSystem);
	private final AnimationSystem animationSystem = new AnimationSystem(eventSystem);

	private final GameMap gameMap = new GameMap(20, 15);
	private final Unit player = new Unit();
	private final Unit enemy = new Unit();

	private MyInputProcessor inputProcessor;

	@Override
	public void create() {

		final Texture uiTexture = new Texture(Gdx.files.internal("core_ui.png"));
		final BitmapFont uiFont = new BitmapFont(true);
		RenderTool.initialize(uiTexture, uiFont);

		shapeRenderer = new ShapeRenderer();
		spriteBatch = new SpriteBatch();
		camera = new GameCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		uiProjectionMatrix = new Matrix4(camera.combined);

		inputProcessor = new MyInputProcessor();
		inputProcessor.setUserInterface(userInterface);
		inputProcessor.setGameMap(gameMap);
		inputProcessor.setControlledUnit(player);
		Gdx.input.setInputProcessor(inputProcessor);

		player.getPosition().set(10, 8);
		enemy.getPosition().set(10, 12);
		player.setFocusTarget(enemy);

		userInterface.addUIComponent(new UIWindow("testWindow", 100, 100, 300, 200, userInterface));
		for (int slot = 0; slot < 6; slot++) {
			final int x = (Gdx.graphics.getWidth() - 6 * 66) / 2;
			final int y = (Gdx.graphics.getHeight() - 66);
			userInterface.addUIComponent(new UIActionSlot("action_slot_" + (slot + 1), x + slot * 66, y, 64, 64));
		}

		final UIHudPlayerStatusPanel player_status_panel = new UIHudPlayerStatusPanel("hud_player_status_panel", 0, 0, 160, 60);
		player_status_panel.setUnit(player);
		userInterface.addUIComponent(player_status_panel);

		final UIHudPlayerStatusPanel focus_target_status_panel = new UIHudPlayerStatusPanel("hud_focus_target_status_panel", Gdx.graphics.getWidth() - 170, 0,
				160, 60);
		focus_target_status_panel.setUnit(enemy);
		userInterface.addUIComponent(focus_target_status_panel);
	}

	@Override
	public void render() {
		inputProcessor.update();

		player.update();
		enemy.update();

		eventSystem.update();
		movementSystem.update();
		animationSystem.update();

		Gdx.gl.glClearColor(inputProcessor.isAdjustControlsMode() ? 0.1f : 0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		Gdx.gl.glEnable(GL20.GL_BLEND);

		camera.setPosition(player.getPosition().x * 32, player.getPosition().y * 32);
		camera.update();

		shapeRenderer.setProjectionMatrix(camera.combined);
		shapeRenderer.begin(ShapeType.Filled);

		for (int y = 0; y < gameMap.getMapHeight(); y++) {
			for (int x = 0; x < gameMap.getMapWidth(); x++) {
				if (gameMap.getMapField(x, y).isCollision()) {
					shapeRenderer.setColor(Color.MAROON);
				} else {
					shapeRenderer.setColor(Color.GRAY);
				}
				shapeRenderer.rect(x * 32, y * 32, 32, 32);
			}
		}

		drawUnit(enemy, Color.RED);
		drawUnit(player, Color.BLUE);

		shapeRenderer.end();

		// shapeRenderer.begin(ShapeType.Line);
		// shapeRenderer.setColor(Color.GREEN);
		// shapeRenderer.circle(Gdx.graphics.getWidth() / 2,
		// Gdx.graphics.getHeight() / 2, 5);
		// if (inputProcessor.getAcclerometerYDiff() <
		// -MyInputProcessor.MOVEMENT_BORDER_Y) {
		// shapeRenderer.line(Gdx.graphics.getWidth() / 2,
		// Gdx.graphics.getHeight() / 2, Gdx.graphics.getWidth() / 2 + 50,
		// Gdx.graphics.getHeight() / 2);
		// } else if (inputProcessor.getAcclerometerYDiff() >
		// MyInputProcessor.MOVEMENT_BORDER_Y) {
		// shapeRenderer.line(Gdx.graphics.getWidth() / 2,
		// Gdx.graphics.getHeight() / 2, Gdx.graphics.getWidth() / 2 - 50,
		// Gdx.graphics.getHeight() / 2);
		// }
		// if (inputProcessor.getAcclerometerXDiff() <
		// -MyInputProcessor.MOVEMENT_BORDER_X) {
		// shapeRenderer.line(Gdx.graphics.getWidth() / 2,
		// Gdx.graphics.getHeight() / 2, Gdx.graphics.getWidth() / 2,
		// Gdx.graphics.getHeight() / 2 - 50);
		// } else if (inputProcessor.getAcclerometerXDiff() >
		// MyInputProcessor.MOVEMENT_BORDER_X) {
		// shapeRenderer.line(Gdx.graphics.getWidth() / 2,
		// Gdx.graphics.getHeight() / 2, Gdx.graphics.getWidth() / 2,
		// Gdx.graphics.getHeight() / 2 + 50);
		// }
		// shapeRenderer.end();

		spriteBatch.setProjectionMatrix(uiProjectionMatrix);
		spriteBatch.begin();
		userInterface.render(spriteBatch);
		spriteBatch.end();

		TimeTool.increaseGameTick();
	}

	private void drawUnit(final Unit unit, Color color) {
		if (unit == player.getFocusTarget()) {
			shapeRenderer.setColor(1, 1, 1, 0.5f);
			shapeRenderer.setColor(Color.GREEN);
			shapeRenderer.circle(unit.getPosition().x * 32, unit.getPosition().y * 32, 12);
			shapeRenderer.setColor(1, 1, 1, 1);
		}

		shapeRenderer.setColor(Color.BLACK);
		shapeRenderer.circle(unit.getPosition().x * 32, unit.getPosition().y * 32, 10);

		if (unit.getCurrentHitpoints() > 0) {
			shapeRenderer.setColor(color);
		} else {
			shapeRenderer.setColor(Color.GRAY);
		}
		shapeRenderer.circle(unit.getPosition().x * 32, unit.getPosition().y * 32, 9);
	}
}
