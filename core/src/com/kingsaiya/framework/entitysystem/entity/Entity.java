package com.kingsaiya.framework.entitysystem.entity;

import java.util.HashMap;

public abstract class Entity {

	private HashMap<Class<? extends AbstractEntityComponent>, AbstractEntityComponent> components = new HashMap<Class<? extends AbstractEntityComponent>, AbstractEntityComponent>();

	public void registerEntityComponents() {
		registerEntityComponents(components);
	}

	protected abstract void registerEntityComponents(HashMap<Class<? extends AbstractEntityComponent>, AbstractEntityComponent> components);

	@SuppressWarnings("unchecked")
	public <T extends AbstractEntityComponent> T getEntityComponent(Class<T> componentClass) {
		return (T) components.get(componentClass);
	}

}
