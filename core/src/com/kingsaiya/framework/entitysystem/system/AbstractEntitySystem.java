package com.kingsaiya.framework.entitysystem.system;

import com.kingsaiya.framework.entitysystem.entity.Entity;
import com.kingsaiya.framework.entitysystem.eventsystem.AbstractEventListener;
import com.kingsaiya.framework.entitysystem.eventsystem.EntityEventSystem;
import com.kingsaiya.framework.entitysystem.eventsystem.EntityInitializedEvent;

public abstract class AbstractEntitySystem {

	protected final EntityEventSystem eventSystem;

	public AbstractEntitySystem(final EntityEventSystem eventSystem) {
		this.eventSystem = eventSystem;

		eventSystem.registerListener(new AbstractEventListener<EntityInitializedEvent>() {
			@Override
			protected void onEvent(EntityInitializedEvent event) {
				handleNewEntity(event.getEntity());
			}
		});

	}

	protected abstract void handleNewEntity(Entity entity);

	public abstract void update();

}
