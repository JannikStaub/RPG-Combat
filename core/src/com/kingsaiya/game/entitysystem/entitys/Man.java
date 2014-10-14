package com.kingsaiya.game.entitysystem.entitys;

import java.util.HashMap;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.kingsaiya.framework.animation.Animation;
import com.kingsaiya.framework.entitysystem.entity.AbstractEntityComponent;
import com.kingsaiya.framework.entitysystem.entity.Entity;
import com.kingsaiya.framework.pixelman.skeleton.SkeletonAnimationHandler;
import com.kingsaiya.framework.pixelman.skeleton.SkeletonAnimationStep;
import com.kingsaiya.game.entitysystem.components.AnimationComponent;
import com.kingsaiya.game.entitysystem.components.FactionComponent;
import com.kingsaiya.game.entitysystem.components.HealthComponent;
import com.kingsaiya.game.entitysystem.components.MovementComponent;
import com.kingsaiya.game.entitysystem.components.SkeletonComponent;

public class Man extends Entity {

	private SkeletonComponent skeletonComponent;
	private AnimationComponent animationComponent;
	private HealthComponent healthComponent;
	private MovementComponent movementComponent;
	private FactionComponent factionComponent;

	private Texture texture;
	private Animation<SkeletonAnimationStep> animation;

	public Man(final Texture texture, final Animation<SkeletonAnimationStep> animation) {
		this.texture = texture;
		this.animation = animation;
	}

	public void render(final SpriteBatch spriteBatch, final ShapeRenderer shapeRenderer) {
		skeletonComponent.getSkeleton().getPosition().set(movementComponent.getPosition().x, movementComponent.getPosition().y);
		skeletonComponent.getSkeleton().render(spriteBatch, shapeRenderer);
	}

	@Override
	protected void registerEntityComponents(HashMap<Class<? extends AbstractEntityComponent>, AbstractEntityComponent> components) {
		skeletonComponent = new SkeletonComponent(texture);
		components.put(SkeletonComponent.class, skeletonComponent);
		animationComponent = new AnimationComponent(new SkeletonAnimationHandler(animation, skeletonComponent.getSkeleton()));
		components.put(AnimationComponent.class, animationComponent);
		healthComponent = new HealthComponent();
		components.put(HealthComponent.class, healthComponent);
		movementComponent = new MovementComponent();
		components.put(MovementComponent.class, movementComponent);
		factionComponent = new FactionComponent();
		components.put(FactionComponent.class, factionComponent);
	}

	public MovementComponent getMovementComponent() {
		return movementComponent;
	}

	public FactionComponent getFactionComponent() {
		return factionComponent;
	}

}