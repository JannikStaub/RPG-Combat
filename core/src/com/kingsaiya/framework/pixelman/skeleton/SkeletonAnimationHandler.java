package com.kingsaiya.framework.pixelman.skeleton;

import java.util.ArrayList;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.kingsaiya.framework.animation.Animation;
import com.kingsaiya.framework.animation.AnimationHandler;

public class SkeletonAnimationHandler extends AnimationHandler<SkeletonAnimationStep> {

	private Skeleton skeleton;

	public SkeletonAnimationHandler(Animation<SkeletonAnimationStep> animation, final Skeleton skeleton) {
		super(animation);
		this.skeleton = skeleton;
	}

	@Override
	protected void applyAnimationStep(SkeletonAnimationStep currentStep, SkeletonAnimationStep nextStep, int frame) {

		float stepLength = nextStep.getFrame() - currentStep.getFrame();
		float currentProgress = 0;
		if (stepLength > 0) {
			currentProgress = Math.min((frame - currentStep.getFrame()) / stepLength, 1f);
		}

		Bone rootBone = skeleton.getRootBone();
		applyAnimationToBone(rootBone, currentStep, nextStep, currentProgress);
	}

	private void applyAnimationToBone(Bone bone, SkeletonAnimationStep currentStep, SkeletonAnimationStep nextStep, float factor) {
		BoneData boneData = currentStep.getBoneData(bone.getName());
		bone.getRoot().set(boneData.x, boneData.y);
		bone.setZ(boneData.z);
		BoneData nextBoneData = nextStep.getBoneData(bone.getName());
		bone.getRoot().interpolate(new Vector2(nextBoneData.x, nextBoneData.y), factor, Interpolation.linear);
		bone.setTextureMapping(bone.getTexture(), boneData.textureMapping, boneData.xFlipped);
		ArrayList<Bone> childBones = bone.getChildBones();
		for (Bone childBone : childBones) {
			applyAnimationToBone(childBone, currentStep, nextStep, factor);
		}
	}

}
