package com.kingsaiya.game.entitysystem.systems;

import java.util.ArrayList;

import com.kingsaiya.framework.entitysystem.entity.Entity;
import com.kingsaiya.framework.entitysystem.eventsystem.AbstractEventListener;
import com.kingsaiya.framework.entitysystem.eventsystem.EntityEventSystem;
import com.kingsaiya.framework.entitysystem.system.AbstractEntitySystem;
import com.kingsaiya.game.combat.dummy.FastMath;
import com.kingsaiya.game.combat.dummy.Vector2f;
import com.kingsaiya.game.entitysystem.components.FactionComponent;
import com.kingsaiya.game.entitysystem.components.HealthComponent;
import com.kingsaiya.game.entitysystem.components.MovementComponent;
import com.kingsaiya.game.entitysystem.events.AttackEvent;
import com.kingsaiya.game.entitysystem.events.EventDamageTaken;
import com.kingsaiya.game.entitysystem.events.EventEnduranceChanged;
import com.kingsaiya.game.entitysystem.events.EventEntityDiedEvent;

public class AttackSystem extends AbstractEntitySystem {

	private static final int enduranceTickIntervall = 10;

	private final ArrayList<Entity> entitys = new ArrayList<Entity>();
	private int enduranceTick = 0;

	public AttackSystem(final EntityEventSystem eventSystem) {
		super(eventSystem);

		eventSystem.registerListener(new AbstractEventListener<AttackEvent>() {
			@Override
			protected void onEvent(final AttackEvent event) {
				final MovementComponent movementComponent = event.entity.getEntityComponent(MovementComponent.class);
				if (movementComponent != null) {
					movementComponent.setMovementBlocked(event.attackDurationInGameTicks);
				}

				final Vector2f a = movementComponent.getPosition();
				final Vector2f b = new Vector2f(movementComponent.getDirection());
				b.rotateAroundOrigin(FastMath.HALF_PI / 2, true);
				b.multLocal(20).addLocal(a);
				final Vector2f c = new Vector2f(movementComponent.getDirection());
				c.rotateAroundOrigin(FastMath.HALF_PI / 2, false);
				c.multLocal(20).addLocal(a);

				FactionComponent factionComponent = event.entity.getEntityComponent(FactionComponent.class);
				for (final Entity entity : entitys) {
					if (entity != event.entity) {
						FactionComponent factionComponent2 = entity.getEntityComponent(FactionComponent.class);
						if (factionComponent2 == null || factionComponent.isEnemy(factionComponent2.getTeamNumber())) {
							final MovementComponent movementComponent2 = entity.getEntityComponent(MovementComponent.class);
							if (isInTriangle2(movementComponent2.getPosition(), a, b, c)) {
								eventSystem.dropEvent(new EventDamageTaken(entity, event.damage, event.entity));
								eventSystem.dropEvent(new EventEnduranceChanged(entity, -5));
							}
						}
					}
				}
			}
		});
		eventSystem.registerListener(new AbstractEventListener<EventDamageTaken>() {
			@Override
			protected void onEvent(final EventDamageTaken event) {
				final HealthComponent healthComponent = event.getEntity().getEntityComponent(HealthComponent.class);
				if (healthComponent != null) {
					if (!healthComponent.isDead()) {
						healthComponent.adjustCurrentHitpoints(-event.getChange());
						if (healthComponent.isDead()) {
							eventSystem.dropEvent(new EventEntityDiedEvent(event.getEntity()));
						}
					}
				}
			}
		});
		eventSystem.registerListener(new AbstractEventListener<EventEnduranceChanged>() {
			@Override
			protected void onEvent(final EventEnduranceChanged event) {
				final HealthComponent healthComponent = event.getEntity().getEntityComponent(HealthComponent.class);
				if (healthComponent != null) {
					healthComponent.adjustCurrentEndurance(event.getChange());
				}
			}
		});
	}

	@Override
	protected void handleNewEntity(final Entity entity) {
		final HealthComponent healthComponent = entity.getEntityComponent(HealthComponent.class);
		if (healthComponent != null) {
			entitys.add(entity);
		}
	}

	public boolean isInTriangle(final Vector2f checkPoint, final Vector2f a, final Vector2f b, final Vector2f c) {
		final float x1 = a.x, y1 = a.y;
		final float x2 = b.x, y2 = b.y;
		final float x3 = c.x, y3 = c.y;

		// no need to divide by 2.0 here, since it is not necessary in the
		// equation
		final float ABC = Math.abs(x1 * (y2 - y3) + x2 * (y3 - y1) + x3 * (y1 - y2));
		final float ABP = Math.abs(x1 * (y2 - checkPoint.y) + x2 * (checkPoint.y - y1) + checkPoint.x * (y1 - y2));
		final float APC = Math.abs(x1 * (checkPoint.y - y3) + checkPoint.x * (y3 - y1) + x3 * (y1 - checkPoint.y));
		final float PBC = Math.abs(checkPoint.x * (y2 - y3) + x2 * (y3 - checkPoint.y) + x3 * (checkPoint.y - y2));

		final boolean isInTriangle = ABP + APC + PBC == ABC;
		return isInTriangle;
	}

	public boolean isInTriangle2(final Vector2f checkPoint, final Vector2f pt1, final Vector2f pt2, final Vector2f pt3) {
		float x3 = pt3.x;
		float y3 = pt3.y;
		float y23 = pt2.y - y3;
		float x32 = x3 - pt2.x;
		float y31 = y3 - pt1.y;
		float x13 = pt1.x - x3;
		float det = y23 * x13 - x32 * y31;
		float minD = Math.min(det, 0);
		float maxD = Math.max(det, 0);

		float dx = checkPoint.x - x3;
		float dy = checkPoint.y - y3;
		float a = y23 * dx + x32 * dy;
		if (a < minD || a > maxD)
			return false;
		float b = y31 * dx + x13 * dy;
		if (b < minD || b > maxD)
			return false;
		float c = det - a - b;
		if (c < minD || c > maxD)
			return false;
		return true;
	}

	@Override
	public void update() {
		if (enduranceTick++ == enduranceTickIntervall) {
			enduranceTick = 0;
			for (final Entity entity : entitys) {
				final HealthComponent healthComponent = entity.getEntityComponent(HealthComponent.class);
				if (!healthComponent.isDead()) {
					eventSystem.dropEvent(new EventEnduranceChanged(entity, 1));
				}
			}
		}
	}

}
