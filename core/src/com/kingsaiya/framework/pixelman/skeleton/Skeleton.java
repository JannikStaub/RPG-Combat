package com.kingsaiya.framework.pixelman.skeleton;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Skeleton {

	private static final BoneTextureMapping spine_north_TextureRegion = new BoneTextureMapping(new Rectangle(0, 0, 5, 8), 2.5f, 6.5f, 180);
	private static final BoneTextureMapping spine_east_TextureRegion = new BoneTextureMapping(new Rectangle(5, 0, 3, 8), 1.5f, 6.5f, 180);
	private static final BoneTextureMapping spine_south_TextureRegion = new BoneTextureMapping(new Rectangle(0, 8, 5, 8), 2.5f, 6.5f, 180);
	private static final BoneTextureMapping spine_west_TextureRegion = new BoneTextureMapping(new Rectangle(5, 8, 3, 8), 1.5f, 6.5f, 180);
	private static final BoneTextureMapping armTextureRegion = new BoneTextureMapping(new Rectangle(8, 0, 2, 6), 1, 1, 0);
	private static final BoneTextureMapping legTextureRegion = new BoneTextureMapping(new Rectangle(8, 8, 2, 6), 1, 1, 0);
	private static final BoneTextureMapping head_south_TextureRegion = new BoneTextureMapping(new Rectangle(11, 0, 5, 5), 2.5f, 2.5f, 180);
	private static final BoneTextureMapping head_north_TextureRegion = new BoneTextureMapping(new Rectangle(11, 6, 5, 5), 2.5f, 2.5f, 180);
	private static final BoneTextureMapping head_west_TextureRegion = new BoneTextureMapping(new Rectangle(11, 11, 5, 5), 2.5f, 2.5f, 180);

	private final Texture texture;
	private final BoneTextureMapping[] textureMappings;

	private final Vector2 position = new Vector2();
	private final Bone rootNode;
	private final ArrayList<Bone> bones = new ArrayList<Bone>();

	private boolean drawBones = false;

	public Skeleton(final float x, final float y, final Texture texture) {
		this.texture = texture;
		position.set(x, y);

		textureMappings = new BoneTextureMapping[9];
		textureMappings[0] = spine_north_TextureRegion;
		textureMappings[1] = spine_east_TextureRegion;
		textureMappings[2] = spine_south_TextureRegion;
		textureMappings[3] = spine_west_TextureRegion;
		textureMappings[4] = armTextureRegion;
		textureMappings[5] = legTextureRegion;
		textureMappings[6] = head_south_TextureRegion;
		textureMappings[7] = head_north_TextureRegion;
		textureMappings[8] = head_west_TextureRegion;

		// generate skeleton
		this.rootNode = new Bone("hip", 0, 0, 0);
		reset();
	}

	public void reset() {
		bones.clear();

		rootNode.getRoot().set(0, 0);
		rootNode.getChildBones().clear();
		bones.add(rootNode);

		// hip
		final Bone leftHipBone = new Bone("left_hip", 1, -0.5f, 0);
		rootNode.addChildBone(leftHipBone);
		bones.add(leftHipBone);
		final Bone rightHipBone = new Bone("right_hip", -1, -0.5f, 0);
		rootNode.addChildBone(rightHipBone);
		bones.add(rightHipBone);

		// legs
		final Bone leftLegBone = new Bone("left_leg", 1, -6f, -1);
		leftLegBone.setTextureMapping(texture, 5);
		leftHipBone.addChildBone(leftLegBone);
		bones.add(leftLegBone);
		final Bone rightLegBone = new Bone("right_leg", -1, -6f, -1);
		rightLegBone.setTextureMapping(texture, 5, true);
		rightHipBone.addChildBone(rightLegBone);
		bones.add(rightLegBone);

		// spine
		final Bone spineBone = new Bone("neck", 0, 3.5f, 0);
		spineBone.setTextureMapping(texture, 0);
		rootNode.addChildBone(spineBone);
		bones.add(spineBone);

		// head
		final Bone headBone = new Bone("head", 0, 3, 0);
		headBone.setTextureMapping(texture, 6);
		spineBone.addChildBone(headBone);
		bones.add(headBone);

		// shoulders
		final Bone leftShoulderBone = new Bone("left_shoulder", 2, 0.25f, 0);
		spineBone.addChildBone(leftShoulderBone);
		bones.add(leftShoulderBone);
		final Bone rightShoulderBone = new Bone("right_shoulder", -2, 0.25f, 0);
		spineBone.addChildBone(rightShoulderBone);
		bones.add(rightShoulderBone);

		// arms
		final Bone leftArmBone = new Bone("left_arm", 4, -1, 0);
		leftArmBone.setTextureMapping(texture, 4);
		leftShoulderBone.addChildBone(leftArmBone);
		bones.add(leftArmBone);
		final Bone rightArmBone = new Bone("right_arm", -4, -1, 0);
		rightArmBone.setTextureMapping(texture, 4, true);
		rightShoulderBone.addChildBone(rightArmBone);
		bones.add(rightArmBone);
	}

	public void render(final SpriteBatch spriteBatch, final ShapeRenderer shapeRenderer) {

		// draw stuff
		spriteBatch.begin();
		int zIndex = -5;
		while (renderBoneWithChilds(rootNode, position.x * 32, position.y * 32, zIndex, 3, spriteBatch)) {
			zIndex++;
		}
		spriteBatch.end();

		if (drawBones) {
			shapeRenderer.begin(ShapeType.Line);
			shapeRenderer.setColor(Color.GREEN);
			drawBoneWithChilds(rootNode, position.x * 32, position.y * 32, shapeRenderer);
			shapeRenderer.end();
		}
	}

	private void drawBoneWithChilds(final Bone bone, final float relativeX, final float relativeY, final ShapeRenderer shapeRenderer) {

		final Vector2 root = bone.getRoot();
		shapeRenderer.circle(root.x + relativeX, root.y + relativeY, 1);

		if (bone != rootNode) {
			shapeRenderer.line(relativeX, relativeY, root.x + relativeX, root.y + relativeY);
		}

		final ArrayList<Bone> childBones = bone.getChildBones();
		for (final Bone childBone : childBones) {
			drawBoneWithChilds(childBone, root.x + relativeX, root.y + relativeY, shapeRenderer);
		}

	}

	private void renderBone(final Bone bone, final float relativeX, final float relativeY, final float scale, final SpriteBatch spriteBatch) {

		final BoneTextureMapping textureMapping = bone.getTextureMapping() >= 0 ? textureMappings[bone.getTextureMapping()] : null;
		if (textureMapping != null) {
			final Vector2 root = bone.getRoot();
			final Rectangle textureRegion = textureMapping.getTextureRegion();
			final float yScale = bone.getRoot().len() / bone.getMaxLength();
			final float rotation = bone.getRoot().angle() + 90 + textureMapping.getRotation();

			spriteBatch.draw(bone.getTexture(), relativeX + (root.x - textureMapping.getRootX()) * scale, relativeY + (root.y - textureMapping.getRootY())
					* scale, textureMapping.getRootX() * scale, textureMapping.getRootY() * scale, textureMapping.getTextureRegion().width * scale,
					textureMapping.getTextureRegion().height * scale, 1f, yScale, rotation, (int) textureRegion.x, (int) textureRegion.y,
					(int) textureRegion.width, (int) textureRegion.height, bone.isTextureXflipped(), false);
		}
	}

	private boolean renderBoneWithChilds(final Bone bone, final float relativeX, final float relativeY, final int zIndex, final float scale,
			final SpriteBatch spriteBatch) {
		boolean hasMoreZLayers = bone.getZ() > zIndex;
		if (bone.getZ() == zIndex) {
			renderBone(bone, relativeX, relativeY, scale, spriteBatch);
		}

		final ArrayList<Bone> childBones = bone.getChildBones();
		for (final Bone childBone : childBones) {
			hasMoreZLayers |= renderBoneWithChilds(childBone, bone.getRoot().x * scale + relativeX, bone.getRoot().y * scale + relativeY, zIndex, scale,
					spriteBatch);
		}
		return hasMoreZLayers;
	}

	public Vector2 getPosition() {
		return position;
	}

	public Bone pickBone(final float x, final float y) {
		return pickBone(x, y, null);
	}

	public Bone pickBone(float x, float y, Bone ignoreBone) {
		return pickBoneIncludeChilds(x - position.x, y - position.y, rootNode, ignoreBone);
	}

	public Bone pickBoneIncludeChilds(final float x, final float y, final Bone bone, Bone ignoreBone) {

		if (bone != ignoreBone && bone.getRoot().dst2(x, y) <= 2) {
			return bone;
		}

		final ArrayList<Bone> childBones = bone.getChildBones();
		for (final Bone childBone : childBones) {
			final Bone pickedBone = pickBoneIncludeChilds(x - bone.getRoot().x, y - bone.getRoot().y, childBone, ignoreBone);
			if (pickedBone != null) {
				return pickedBone;
			}
		}

		if (bone == ignoreBone && bone.getRoot().dst2(x, y) <= 2) {
			return bone;
		}

		return null;
	}

	public Bone getRootBone() {
		return rootNode;
	}

	public Bone getBone(final String boneName, Bone startBone) {
		if (startBone == null) {
			startBone = rootNode;
		}
		if (startBone.getName().equals(boneName)) {
			return startBone;
		}
		final ArrayList<Bone> childBones = startBone.getChildBones();
		for (final Bone childBone : childBones) {
			final Bone pickedBone = getBone(boneName, childBone);
			if (pickedBone != null) {
				return pickedBone;
			}
		}
		return null;
	}

	public boolean isDrawBones() {
		return drawBones;
	}

	public void setDrawBones(boolean drawBones) {
		this.drawBones = drawBones;
	}

	public int getTextureMappingAmount() {
		return textureMappings.length;
	}

}
