package com.kingsaiya.game.entitysystem.systems;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.kingsaiya.framework.entitysystem.entity.Entity;
import com.kingsaiya.framework.entitysystem.eventsystem.AbstractEventListener;
import com.kingsaiya.framework.entitysystem.eventsystem.EntityEventSystem;
import com.kingsaiya.framework.entitysystem.system.AbstractEntitySystem;
import com.kingsaiya.framework.tools.Vector2f;
import com.kingsaiya.game.entitysystem.components.MovementComponent;
import com.kingsaiya.game.entitysystem.events.movement.MovementEventAtTargetPosition;
import com.kingsaiya.game.entitysystem.events.movement.MovementEventDirectControl;
import com.kingsaiya.game.entitysystem.events.movement.MovementEventNewTargetPosition;
import com.kingsaiya.game.map.GameMap;

public class MovementSystem extends AbstractEntitySystem {

	private ArrayList<Entity> entitys = new ArrayList<Entity>();
	private GameMap gameMap;
	private final Vector2f cachedVector = new Vector2f();

	public MovementSystem(final EntityEventSystem eventSystem) {
		super(eventSystem);

		eventSystem.registerListener(new AbstractEventListener<MovementEventNewTargetPosition>() {
			@Override
			protected void onEvent(MovementEventNewTargetPosition event) {
				final MovementComponent movementComponent = event.getEntity().getEntityComponent(MovementComponent.class);
				if (movementComponent != null) {
					movementComponent.setTargetPosition(event.getX(), event.getY());
				}
			}
		});

		eventSystem.registerListener(new AbstractEventListener<MovementEventDirectControl>() {

			@Override
			protected void onEvent(MovementEventDirectControl event) {
				final MovementComponent movementComponent = event.getEntity().getEntityComponent(MovementComponent.class);

				float xMovement = 0;
				float yMovement = 0;

				if (event.isWalkUp()) {
					yMovement = 1;
				}
				if (event.isWalkDown()) {
					yMovement = -1;
				}
				if (event.isWalkLeft()) {
					xMovement = -1;
				}
				if (event.isWalkRight()) {
					xMovement = 1;
				}

				if (xMovement != 0 || yMovement != 0) {
					movementComponent.getDirection().set(xMovement, yMovement);
				}

				float targetX = movementComponent.getPosition().x + xMovement * movementComponent.getSpeed() * Gdx.graphics.getDeltaTime();
				float targetY = movementComponent.getPosition().y + yMovement * movementComponent.getSpeed() * Gdx.graphics.getDeltaTime();
				int currentMapFieldX = (int) movementComponent.getPosition().x;
				int currentMapFieldY = (int) movementComponent.getPosition().y;
				int targetMapFieldX = (int) targetX;
				int targetMapFieldY = (int) targetY;

				// check collision when crossing mapfields
				if (gameMap != null) {
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
				}

				movementComponent.getPosition().set(targetX, targetY);
			}
		});
	}

	@Override
	protected void handleNewEntity(Entity entity) {
		final MovementComponent movementComponent = entity.getEntityComponent(MovementComponent.class);
		if (movementComponent != null) {
			entitys.add(entity);
		}
	}

	@Override
	public void update() {
		for (final Entity entity : entitys) {
			final MovementComponent movementComponent = entity.getEntityComponent(MovementComponent.class);
			if (movementComponent.isMoving()) {
				// get offset
				cachedVector.set(movementComponent.getTargetPosition()).subtractLocal(movementComponent.getPosition());

				final float distanceSquared = cachedVector.lengthSquared();

				// to direction
				cachedVector.normalizeLocal();

				// to movement distance
				cachedVector.multLocal(movementComponent.getSpeed());

				// apply movement
				final float movementDistanceSquared = cachedVector.lengthSquared();
				if (distanceSquared > movementDistanceSquared) {
					movementComponent.setPosition(movementComponent.getPosition().x + cachedVector.x, movementComponent.getPosition().y + cachedVector.y);
				} else {
					movementComponent.setPosition(movementComponent.getTargetPosition());
				}

				// if no longer moving throw atTargetPosition event
				if (!movementComponent.isMoving()) {
					eventSystem.dropEvent(new MovementEventAtTargetPosition(entity));
				}
			}
		}
	}

	public void setGameMap(GameMap gameMap) {
		this.gameMap = gameMap;
	}
}
