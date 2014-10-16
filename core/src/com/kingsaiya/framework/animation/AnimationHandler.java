package com.kingsaiya.framework.animation;

import java.util.ArrayList;

import com.pixelmen.game.animation.AnimationStep;

public abstract class AnimationHandler<T extends AnimationStep> {

	private Animation<T> animation;

	private boolean loop = false;
	private int currentFrame = 0;

	public void restart() {
		currentFrame = 0;
	}

	public void setFrame(int currentFrame) {
		this.currentFrame = currentFrame;
	}

	public void setAnimation(Animation<T> animation) {
		this.animation = animation;
	}

	public void update(int framesPassed) {
		if (animation != null) {
			currentFrame += framesPassed;
			if (loop) {
				currentFrame = currentFrame % animation.getLengthInFrames();
			} else {
				currentFrame = Math.min(currentFrame, animation.getLengthInFrames() - 1);
			}

			final ArrayList<T> animationSteps = animation.getAnimationSteps();
			T currentStep = animationSteps.get(0);
			T nextStep = currentStep;

			if (animationSteps.size() > 1) {
				for (int s = 0; s < animationSteps.size(); s++) {
					T animationStep = animationSteps.get(s);
					if (animationStep.getFrame() <= currentFrame) {
						currentStep = animationStep;
					}
					if (animationStep.getFrame() > nextStep.getFrame()) {
						nextStep = animationSteps.get(s);
					}
					if (nextStep.getFrame() > currentFrame) {
						break;
					}

				}
			}

			applyAnimationStep(currentStep, nextStep, currentFrame);
		}
	}

	protected abstract void applyAnimationStep(T currentStep, T nextStep, int frame);

	public void setLoop(final boolean loop) {
		this.loop = loop;
	}

	public boolean isLoop() {
		return loop;
	}

	public int getCurrentFrame() {
		return currentFrame;
	}
}
