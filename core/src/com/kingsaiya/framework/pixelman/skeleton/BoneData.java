package com.kingsaiya.framework.pixelman.skeleton;

public class BoneData {

	public final float x;
	public final float y;
	public final int z;
	public final int textureMapping;
	public final boolean xFlipped;

	public BoneData(float x, float y, int z, int textureMapping, boolean xFlipped) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.textureMapping = textureMapping;
		this.xFlipped = xFlipped;
	}
}