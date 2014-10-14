package com.kingsaiya.game.entitysystem.components;

import com.badlogic.gdx.graphics.Texture;
import com.kingsaiya.framework.entitysystem.entity.AbstractEntityComponent;
import com.kingsaiya.framework.pixelman.skeleton.Skeleton;

public class SkeletonComponent extends AbstractEntityComponent {

	private final Skeleton skeleton;

	public SkeletonComponent(final Texture texture) {
		skeleton = new Skeleton(0, 0, texture);
	}

	public Skeleton getSkeleton() {
		return skeleton;
	}
}
