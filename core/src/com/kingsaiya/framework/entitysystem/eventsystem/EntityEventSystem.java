package com.kingsaiya.framework.entitysystem.eventsystem;

import java.util.ArrayList;

public class EntityEventSystem {

	private final ArrayList<AbstractEventListener> registeredListeners = new ArrayList<AbstractEventListener>();

	private final ArrayList<EntityEvent> eventQueue = new ArrayList<EntityEvent>();

	public void dropEvent(final EntityEvent event) {
		eventQueue.add(event);
	}

	public void update() {
		for (int e = eventQueue.size(); e-- > 0;) {
			final EntityEvent event = eventQueue.remove(e);
			executeEvent(event);
		}
	}

	public void executeEvent(final EntityEvent event) {
		for (final AbstractEventListener listener : registeredListeners) {
			listener.handleEvent(event);
		}
	}

	public void registerListener(final AbstractEventListener listener) {
		registeredListeners.add(listener);
	}
}
