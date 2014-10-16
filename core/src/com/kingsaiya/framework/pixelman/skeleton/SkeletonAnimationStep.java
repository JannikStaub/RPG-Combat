package com.kingsaiya.framework.pixelman.skeleton;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;

import com.pixelmen.game.animation.AnimationStep;

public class SkeletonAnimationStep extends AnimationStep implements Externalizable {
	private static final long serialVersionUID = -403250971215465050L;

	private final HashMap<String, BoneData> boneData = new HashMap<String, BoneData>();

	public SkeletonAnimationStep() {
		// constructor for serialization
	}

	public SkeletonAnimationStep(final int frame, final Skeleton skeleton) {
		super(frame);
		saveBonePositions(skeleton.getRootBone());
	}

	private void saveBonePositions(final Bone bone) {
		boneData.put(bone.getName(), new BoneData(bone.getRoot().x, bone.getRoot().y, bone.getZ(), bone.getTextureMapping(), bone.isTextureXflipped()));
		final ArrayList<Bone> childBones = bone.getChildBones();
		for (final Bone childBone : childBones) {
			saveBonePositions(childBone);
		}
	}

	public BoneData getBoneData(final String boneName) {
		return boneData.get(boneName);
	}

	@Override
	public void writeExternal(final ObjectOutput out) throws IOException {
		out.writeInt(frame);
		final Set<Entry<String, BoneData>> bonePositionSet = boneData.entrySet();
		out.writeInt(bonePositionSet.size());
		for (final Entry<String, BoneData> entry : bonePositionSet) {
			out.writeUTF(entry.getKey());
			out.writeFloat(entry.getValue().x);
			out.writeFloat(entry.getValue().y);
			out.writeInt(entry.getValue().z);
			out.writeInt(entry.getValue().textureMapping);
			out.writeBoolean(entry.getValue().xFlipped);
		}
	}

	@Override
	public void readExternal(final ObjectInput in) throws IOException, ClassNotFoundException {
		frame = in.readInt();
		boneData.clear();
		int boneAmount = in.readInt();
		for (int b = 0; b < boneAmount; b++) {
			String boneName = in.readUTF();
			boneData.put(boneName, new BoneData(in.readFloat(), in.readFloat(), in.readInt(), in.readInt(), in.readBoolean()));
		}
	}

	public HashMap<String, BoneData> getBoneDataMap() {
		return boneData;
	}

}
