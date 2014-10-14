package com.kingsaiya.framework.animation;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Animation<T extends AnimationStep> implements Externalizable {
	private int lengthInFrames;

	private ArrayList<T> animationSteps = new ArrayList<T>();
	private boolean sorted = true;

	public Animation() {
		// constructor for serialization
	}

	public Animation(int frames) {
		this.lengthInFrames = frames;
	}

	public void addAnimationStep(T step) {
		if (step.getFrame() < 0 || step.getFrame() >= lengthInFrames) {
			System.err.println("cannot add step outside framebounds 0 - " + lengthInFrames);
			return;
		}
		animationSteps.add(step);
		sorted = false;
	}

	public void removeAnimationStep(int s) {
		animationSteps.remove(s);
		sorted = false;
	}

	public ArrayList<T> getAnimationSteps() {
		if (!sorted) {
			Collections.sort(animationSteps, new Comparator<AnimationStep>() {
				@Override
				public int compare(AnimationStep o1, AnimationStep o2) {
					return o1.getFrame() - o2.getFrame();
				}
			});
			sorted = true;
		}

		return animationSteps;
	}

	public int getLengthInFrames() {
		return lengthInFrames;
	}

	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
		out.writeInt(lengthInFrames);
		ArrayList<T> animationSteps = getAnimationSteps();
		out.writeInt(animationSteps.size());
		for (T step : animationSteps) {
			out.writeObject(step);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
		lengthInFrames = in.readInt();
		int animationStepAmount = in.readInt();
		animationSteps.clear();
		for (int s = 0; s < animationStepAmount; s++) {
			animationSteps.add((T) in.readObject());
		}
	}
}
