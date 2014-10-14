package com.kingsaiya.framework.pixelman.skeleton;

import com.badlogic.gdx.math.Rectangle;

public class BoneTextureMapping {
	private Rectangle textureRegion;
	private float rootX;
	private float rootY;
	private float rotation;

	public BoneTextureMapping(final Rectangle textureRegion, float rootX, float rootY, float rotation) {
		this.textureRegion = textureRegion;
		this.rootX = rootX;
		this.rootY = rootY;
		this.rotation = rotation;
	}

	public Rectangle getTextureRegion() {
		return textureRegion;
	}

	public float getRootX() {
		return rootX;
	}

	public float getRootY() {
		return rootY;
	}

	public float getRotation() {
		return rotation;
	}
}
