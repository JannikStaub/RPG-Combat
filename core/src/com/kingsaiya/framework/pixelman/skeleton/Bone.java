package com.kingsaiya.framework.pixelman.skeleton;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class Bone {

	private final String name;
	private final ArrayList<Bone> childBones = new ArrayList<Bone>();
	private final Vector2 root = new Vector2();
	private final float maxLength;

	private Bone parentRoot = null;
	private int textureMapping = -1;
	private boolean textureXflipped = false;
	private Texture texture;
	private int z = 0;

	public Bone(final String name, final float x, final float y, final float maxLength, int z) {
		this.name = name;
		this.root.set(x, y);
		this.maxLength = maxLength;
		this.z = z;
	}

	public Bone(final String name, final float x, final float y, int z) {
		this.name = name;
		this.root.set(x, y);
		this.maxLength = root.len();
		this.z = z;
	}

	private void setParentRoot(final Bone parentRoot) {
		this.parentRoot = parentRoot;
	}

	public void addChildBone(Bone childBone) {
		childBones.add(childBone);
		childBone.setParentRoot(this);
	}

	public Vector2 getRoot() {
		return root;
	}

	public Bone getParentRoot() {
		return parentRoot;
	}

	public ArrayList<Bone> getChildBones() {
		return childBones;
	}

	public float getMaxLength() {
		return maxLength;
	}

	public Vector2 getOffsetToSkeletonRoot(Vector2 offset) {
		offset.add(root.x, root.y);
		if (parentRoot != null) {
			parentRoot.getOffsetToSkeletonRoot(offset);
		}
		return offset;
	}

	public void setTextureMapping(Texture texture, int textureMapping) {
		setTextureMapping(texture, textureMapping, false);
	}

	public void setTextureMapping(Texture texture, int textureMapping, boolean flipped) {
		this.texture = texture;
		this.textureMapping = textureMapping;
		this.textureXflipped = flipped;
	}

	public Texture getTexture() {
		return texture;
	}

	public int getTextureMapping() {
		return textureMapping;
	}

	public boolean isTextureXflipped() {
		return textureXflipped;
	}

	public String getName() {
		return name;
	}

	public void setZ(int z) {
		this.z = z;
	}

	public int getZ() {
		return z;
	}
}
