package com.kingsaiya.framework.tools;

import java.io.Externalizable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamClass;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.kingsaiya.framework.pixelman.skeleton.SkeletonAnimationStep;

public class FileTool {
	public static final String ABSOLUTE_ANIMATIONS_PATH = "animations/";

	public static <T extends Externalizable> void saveExternalExternalizableFile(final T file, final String fileName) {
		FileOutputStream fout = null;
		ObjectOutputStream oout = null;
		try {
			try {
				final File F = Gdx.files.absolute(ABSOLUTE_ANIMATIONS_PATH + fileName).file();
				F.createNewFile();
				fout = new FileOutputStream(F);
				oout = new ObjectOutputStream(fout);
				file.writeExternal(oout);
			} finally {
				if (oout != null) {
					oout.flush();
					oout.close();
				}
				if (fout != null) {
					fout.flush();
					fout.close();
				}
			}

		} catch (final Exception e) {
			System.err.println("Error writing to file");
			e.printStackTrace();
		}
	}

	public static <T extends Externalizable> T loadExternalExtenalizableFile(final String fileName, final Class<? extends T> fileClass) {
		return loadExternalExtenalizableFile(Gdx.files.internal(fileName), fileClass);
	}

	public static <T extends Externalizable> T loadExternalExtenalizableFile(final FileHandle F, final Class<? extends T> fileClass) {
		T file;
		try {
			file = fileClass.newInstance();
		} catch (final InstantiationException e) {
			e.printStackTrace();
			return null;
		} catch (final IllegalAccessException e) {
			e.printStackTrace();
			return null;
		}

		InputStream fin = null;
		HackedObjectInputStream inputStream = null;
		try {
			try {
				fin = F.read();
				inputStream = new HackedObjectInputStream(fin);
				file.readExternal(inputStream);
			} catch (final FileNotFoundException e) {
				e.printStackTrace();
			} catch (final ClassNotFoundException e) {
				e.printStackTrace();
			} finally {
				if (inputStream != null) {
					inputStream.close();
				}
				if (fin != null) {

					fin.close();
				}
			}
		} catch (final IOException e) {
			e.printStackTrace();
		}
		return file;
	}

	public static class HackedObjectInputStream extends ObjectInputStream {

		public HackedObjectInputStream(InputStream in) throws IOException {
			super(in);
		}

		@Override
		protected ObjectStreamClass readClassDescriptor() throws IOException, ClassNotFoundException {
			ObjectStreamClass resultClassDescriptor = super.readClassDescriptor();

			if (resultClassDescriptor.getName().equals("com.pixelmen.game.animation.SkeletonAnimationStep"))
				resultClassDescriptor = ObjectStreamClass.lookup(SkeletonAnimationStep.class);

			return resultClassDescriptor;
		}
	}
}
