package com.kingsaiya.game.entitysystem.components;

import com.kingsaiya.framework.animation.AnimationHandler;
import com.kingsaiya.framework.entitysystem.entity.AbstractEntityComponent;

@SuppressWarnings("rawtypes")
public class AnimationComponent extends AbstractEntityComponent {

	private AnimationHandler animationHandler;

	public AnimationComponent(final AnimationHandler animationHandler) {
		this.animationHandler = animationHandler;
	}

	public AnimationHandler getAnimationHandler() {
		return animationHandler;
	}

	public static enum AnimationDirection {
		NORTH("north"), EAST("east"), SOUTH("south"), WEST("west");

		public final String key;

		private AnimationDirection(String key) {
			this.key = key;
		}
	}
}
