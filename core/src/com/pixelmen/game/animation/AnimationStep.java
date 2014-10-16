package com.pixelmen.game.animation;

import java.io.Externalizable;

public abstract class AnimationStep implements Externalizable {

	protected int frame;

	public AnimationStep() {
		// constructor for serialization
	}

	public AnimationStep(final int frame) {
		this.frame = frame;
	}

	public int getFrame() {
		return frame;
	}

	public void setFrame(int frame) {
		this.frame = frame;
	}

}
