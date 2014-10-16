package com.kingsaiya.game.entitysystem.systems;

import java.util.ArrayList;
import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.kingsaiya.framework.animation.Animation;
import com.kingsaiya.framework.entitysystem.entity.Entity;
import com.kingsaiya.framework.entitysystem.eventsystem.AbstractEventListener;
import com.kingsaiya.framework.entitysystem.eventsystem.EntityEventSystem;
import com.kingsaiya.framework.entitysystem.system.AbstractEntitySystem;
import com.kingsaiya.framework.pixelman.skeleton.SkeletonAnimationHandler;
import com.kingsaiya.framework.pixelman.skeleton.SkeletonAnimationStep;
import com.kingsaiya.framework.tools.FileTool;
import com.kingsaiya.framework.tools.TimeTool;
import com.kingsaiya.game.entitysystem.components.AnimationComponent;
import com.kingsaiya.game.entitysystem.components.MovementComponent;
import com.kingsaiya.game.entitysystem.events.AttackEvent;
import com.kingsaiya.game.entitysystem.events.entity.EventEntityDiedEvent;
import com.kingsaiya.game.entitysystem.events.movement.MovementEventAtTargetPosition;
import com.kingsaiya.game.entitysystem.events.movement.MovementEventDirectControl;
import com.kingsaiya.game.entitysystem.events.movement.MovementEventNewTargetPosition;

public class AnimationSystem extends AbstractEntitySystem {

	private final ArrayList<Entity> entitys = new ArrayList<Entity>();

	private final HashMap<String, Animation<SkeletonAnimationStep>> loadedAnimations = new HashMap<String, Animation<SkeletonAnimationStep>>();

	public AnimationSystem(final EntityEventSystem eventSystem) {
		super(eventSystem);

		eventSystem.registerListener(new AbstractEventListener<MovementEventNewTargetPosition>() {
			@Override
			protected void onEvent(final MovementEventNewTargetPosition event) {
				final AnimationComponent animationComponent = event.getEntity().getEntityComponent(AnimationComponent.class);
				if (animationComponent != null) {
					final String animationDirection = getAnimationDirectionString(event.getEntity());
					final Animation<SkeletonAnimationStep> walk_animation = getAnimation(FileTool.ABSOLUTE_ANIMATIONS_PATH + "human_walk_01_"
							+ animationDirection + ".anim");
					final SkeletonAnimationHandler skeletonAnimationHandler = (SkeletonAnimationHandler) animationComponent.getAnimationHandler();
					skeletonAnimationHandler.setLoop(true);
					skeletonAnimationHandler.setAnimation(walk_animation);
					System.out.println("set walk animation");
				}
			}

		});

		eventSystem.registerListener(new AbstractEventListener<MovementEventAtTargetPosition>() {
			@Override
			protected void onEvent(final MovementEventAtTargetPosition event) {
				setStandingAnimation(event.getEntity());
			}

		});

		eventSystem.registerListener(new AbstractEventListener<MovementEventDirectControl>() {
			@Override
			protected void onEvent(final MovementEventDirectControl event) {
				final AnimationComponent animationComponent = event.getEntity().getEntityComponent(AnimationComponent.class);
				if (animationComponent != null) {
					MovementComponent movementComponent = event.getEntity().getEntityComponent(MovementComponent.class);
					if (!movementComponent.isMovementBlocked(TimeTool.getGameTick())) {
						final String animationDirection = getAnimationDirectionString(event.getEntity());

						boolean hasMovement = event.isWalkUp() || event.isWalkLeft() || event.isWalkRight() || event.isWalkDown();
						Animation<SkeletonAnimationStep> animation;
						if (hasMovement) {
							animation = getAnimation(FileTool.ABSOLUTE_ANIMATIONS_PATH + "human_walk_01_" + animationDirection + ".anim");
						} else {
							animation = getAnimation(FileTool.ABSOLUTE_ANIMATIONS_PATH + "human_stand_01_" + animationDirection + ".anim");
						}

						final SkeletonAnimationHandler skeletonAnimationHandler = (SkeletonAnimationHandler) animationComponent.getAnimationHandler();
						skeletonAnimationHandler.setLoop(true);
						skeletonAnimationHandler.setAnimation(animation);
					}
				}
			}
		});

		eventSystem.registerListener(new AbstractEventListener<AttackEvent>() {
			@Override
			protected void onEvent(AttackEvent event) {
				final AnimationComponent animationComponent = event.entity.getEntityComponent(AnimationComponent.class);
				if (animationComponent != null) {
					final SkeletonAnimationHandler skeletonAnimationHandler = (SkeletonAnimationHandler) animationComponent.getAnimationHandler();
					skeletonAnimationHandler.setLoop(false);
					final String animationDirection = getAnimationDirectionString(event.entity);
					skeletonAnimationHandler.setAnimation(getAnimation(FileTool.ABSOLUTE_ANIMATIONS_PATH + "human_punch_01_" + animationDirection + ".anim"));
					skeletonAnimationHandler.setFrame(0);
				}
			}
		});

		eventSystem.registerListener(new AbstractEventListener<EventEntityDiedEvent>() {
			@Override
			protected void onEvent(EventEntityDiedEvent event) {
				final AnimationComponent animationComponent = event.entity.getEntityComponent(AnimationComponent.class);
				if (animationComponent != null) {
					final SkeletonAnimationHandler skeletonAnimationHandler = (SkeletonAnimationHandler) animationComponent.getAnimationHandler();
					skeletonAnimationHandler.setLoop(false);
					final String animationDirection = getAnimationDirectionString(event.entity);
					skeletonAnimationHandler.setAnimation(getAnimation(FileTool.ABSOLUTE_ANIMATIONS_PATH + "human_death_01_" + animationDirection + ".anim"));
					skeletonAnimationHandler.setFrame(0);
				}
			}
		});
	}

	private Animation<SkeletonAnimationStep> getAnimation(final String filePath) {
		if (loadedAnimations.containsKey(filePath)) {
			return loadedAnimations.get(filePath);
		}

		@SuppressWarnings("unchecked")
		final Animation<SkeletonAnimationStep> animation = FileTool.loadExternalExtenalizableFile(Gdx.files.internal(filePath), Animation.class);
		if (animation != null) {
			loadedAnimations.put(filePath, animation);
		} else {
			System.out.println("Animation not found " + filePath);
		}
		return animation;
	}

	private void setStandingAnimation(Entity entity) {
		final AnimationComponent animationComponent = entity.getEntityComponent(AnimationComponent.class);
		if (animationComponent != null) {
			final String animationDirection = getAnimationDirectionString(entity);
			final Animation<SkeletonAnimationStep> walk_animation = getAnimation(FileTool.ABSOLUTE_ANIMATIONS_PATH + "human_stand_01_" + animationDirection
					+ ".anim");
			final SkeletonAnimationHandler skeletonAnimationHandler = (SkeletonAnimationHandler) animationComponent.getAnimationHandler();
			skeletonAnimationHandler.setLoop(true);
			skeletonAnimationHandler.setAnimation(walk_animation);
			System.out.println("set stand animation");
		}
	}

	private String getAnimationDirectionString(final Entity entity) {
		final MovementComponent movementComponent = entity.getEntityComponent(MovementComponent.class);
		if (movementComponent != null) {
			return movementComponent.getMovementDirectionType().key;
		}
		return "";
	}

	@Override
	protected void handleNewEntity(final Entity entity) {
		final AnimationComponent animationComponent = entity.getEntityComponent(AnimationComponent.class);
		if (animationComponent != null) {
			entitys.add(entity);
			setStandingAnimation(entity);
		}
	}

	@Override
	public void update() {
		for (final Entity entity : entitys) {
			final AnimationComponent animationComponent = entity.getEntityComponent(AnimationComponent.class);
			animationComponent.getAnimationHandler().update(1);
		}
	}
}
