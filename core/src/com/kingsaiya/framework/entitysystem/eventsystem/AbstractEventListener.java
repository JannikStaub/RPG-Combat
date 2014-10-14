package com.kingsaiya.framework.entitysystem.eventsystem;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public abstract class AbstractEventListener<T extends EntityEvent> {

	private Type t = ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];

	public final void handleEvent(final EntityEvent event) {
		if (t == event.getClass()) {
			onEvent((T) event);
		}
	}

	protected abstract void onEvent(T event);

}
