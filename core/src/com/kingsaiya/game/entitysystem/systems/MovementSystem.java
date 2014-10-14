package com.kingsaiya.game.entitysystem.systems;

import java.util.ArrayList;

import com.kingsaiya.framework.entitysystem.entity.Entity;
import com.kingsaiya.framework.entitysystem.eventsystem.AbstractEventListener;
import com.kingsaiya.framework.entitysystem.eventsystem.EntityEventSystem;
import com.kingsaiya.framework.entitysystem.system.AbstractEntitySystem;
import com.kingsaiya.game.combat.dummy.FastMath;
import com.kingsaiya.game.combat.dummy.TimeTool;
import com.kingsaiya.game.combat.dummy.Vector2f;
import com.kingsaiya.game.entitysystem.components.MovementComponent;
import com.kingsaiya.game.entitysystem.events.MovementEventAtTargetPosition;
import com.kingsaiya.game.entitysystem.events.MovementEventDirectControl;
import com.kingsaiya.game.entitysystem.events.MovementEventNewTargetPosition;

public class MovementSystem extends AbstractEntitySystem {

	private ArrayList<Entity> entitys = new ArrayList<Entity>();

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

		eventSystem.registerListener(new AbstractEventListener<MovementEventAtTargetPosition>() {
			@Override
			protected void onEvent(MovementEventAtTargetPosition event) {
				final MovementComponent movementComponent = event.getEntity().getEntityComponent(MovementComponent.class);
				if (movementComponent != null) {
					float movementX = FastMath.rand.nextFloat() * 200 - 100f;
					float movementY = FastMath.rand.nextFloat() * 200 - 100f;
					if (movementX == 0 && movementY == 0) {
						System.out.println("no movement");
					}
					// eventSystem.dropEvent(new
					// MovementEventNewTargetPosition(event.getEntity(),
					// movementComponent.getPosition().x + movementX,
					// movementComponent.getPosition().y + movementY));
				}
			}
		});

		eventSystem.registerListener(new AbstractEventListener<MovementEventDirectControl>() {
			@Override
			protected void onEvent(MovementEventDirectControl event) {
				final MovementComponent movementComponent = event.getEntity().getEntityComponent(MovementComponent.class);
				if (movementComponent != null) {

					double lookDirectionChange = event.getLookDirectionChange();
					double turnDirectionChange = event.getTurnDirectionChange();
					double forwardMotion = event.getForwardMotion();
					double strafeMotion = event.getStrafeMotion();

					// look around TODO
					movementComponent.getDirection().rotateAroundOrigin((float) (lookDirectionChange * 0.01), true);

					// turn character
					movementComponent.getDirection().rotateAroundOrigin((float) (turnDirectionChange * 0.01), true);

					if (!movementComponent.isMovementBlocked(TimeTool.getGameTick())) {
						float movementEndX = movementComponent.getPosition().x;
						float movementEndY = movementComponent.getPosition().y;

						// move forward
						if (Math.abs(forwardMotion) > 0.1) {
							float forwardSpeed = (float) forwardMotion * (forwardMotion < 0 ? 0.1f : 0.3f);
							movementEndX += movementComponent.getDirection().x * forwardSpeed;
							movementEndY += movementComponent.getDirection().y * forwardSpeed;
						}

						// strafe
						if (Math.abs(strafeMotion) > 0.1) {
							float strafeSpeed = (float) strafeMotion * 0.1f;
							movementEndX += movementComponent.getDirection().y * strafeSpeed;
							movementEndY -= movementComponent.getDirection().x * strafeSpeed;
						}

						movementEndX = Math.max(-90, Math.min(movementEndX, 90));
						movementEndY = Math.max(-70, Math.min(movementEndY, 70));
						movementComponent.setPosition(movementEndX, movementEndY);
						movementComponent.setTargetPosition(movementEndX, movementEndY);
					}
				}
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
}
