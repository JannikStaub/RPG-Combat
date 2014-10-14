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
import com.kingsaiya.game.combat.dummy.TimeTool;
import com.kingsaiya.game.entitysystem.components.AnimationComponent;
import com.kingsaiya.game.entitysystem.components.MovementComponent;
import com.kingsaiya.game.entitysystem.events.AttackEvent;
import com.kingsaiya.game.entitysystem.events.EventEntityDiedEvent;
import com.kingsaiya.game.entitysystem.events.MovementEventAtTargetPosition;
import com.kingsaiya.game.entitysystem.events.MovementEventDirectControl;
import com.kingsaiya.game.entitysystem.events.MovementEventNewTargetPosition;

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
				final AnimationComponent animationComponent = event.getEntity().getEntityComponent(AnimationComponent.class);
				if (animationComponent != null) {
					final String animationDirection = getAnimationDirectionString(event.getEntity());
					final Animation<SkeletonAnimationStep> walk_animation = getAnimation(FileTool.ABSOLUTE_ANIMATIONS_PATH + "human_stand_01_"
							+ animationDirection + ".anim");
					final SkeletonAnimationHandler skeletonAnimationHandler = (SkeletonAnimationHandler) animationComponent.getAnimationHandler();
					skeletonAnimationHandler.setLoop(true);
					skeletonAnimationHandler.setAnimation(walk_animation);
					System.out.println("set stand animation");
				}
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

						Animation<SkeletonAnimationStep> animation;
						if (Math.abs(event.getForwardMotion()) > Math.abs(event.getStrafeMotion() * 0.5)) {
							if (event.getForwardMotion() > 0.1) {
								animation = getAnimation(FileTool.ABSOLUTE_ANIMATIONS_PATH + "human_walk_01_" + animationDirection + ".anim");
							} else if (event.getForwardMotion() < 0.1) {
								animation = getAnimation(FileTool.ABSOLUTE_ANIMATIONS_PATH + "human_walkbackwards_01_" + animationDirection + ".anim");
							} else {
								animation = getAnimation(FileTool.ABSOLUTE_ANIMATIONS_PATH + "human_stand_01_" + animationDirection + ".anim");
							}
						} else {
							if (Math.abs(event.getStrafeMotion()) > 0.1) {
								animation = getAnimation(FileTool.ABSOLUTE_ANIMATIONS_PATH + "human_strafe_01_" + animationDirection + ".anim");
							} else {
								animation = getAnimation(FileTool.ABSOLUTE_ANIMATIONS_PATH + "human_stand_01_" + animationDirection + ".anim");
							}
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

		final Animation<SkeletonAnimationStep> animation = FileTool.loadExternalExtenalizableFile(Gdx.files.absolute(filePath).file(), Animation.class);
		if (animation != null) {
			loadedAnimations.put(filePath, animation);
		} else {
			System.out.println("Animation not found " + filePath);
		}
		return animation;
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
