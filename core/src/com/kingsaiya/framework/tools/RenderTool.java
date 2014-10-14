package com.kingsaiya.framework.tools;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFont.TextBounds;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class RenderTool {

	private static Texture uiTexture;
	private static BitmapFont uiFont;

	private static final TextBounds textBounds = new TextBounds();

	public static void initialize(final Texture uiTexture, final BitmapFont uiFont) {
		RenderTool.uiTexture = uiTexture;
		RenderTool.uiFont = uiFont;
	}

	public static Texture getUITexture() {
		return uiTexture;
	}

	public static BitmapFont getUIFont() {
		return uiFont;
	}

	public static void renderText(final String caption, final float x, final float y, final float size, final SpriteBatch spriteBatch) {
		uiFont.setScale(size);
		uiFont.draw(spriteBatch, caption, x, y);
	}

	public static void renderTextHorizontalCentered(final String caption, final float x, final float y, final float size, final float parentWidth,
			final SpriteBatch spriteBatch) {
		uiFont.setScale(size);
		uiFont.getBounds(caption, textBounds);
		uiFont.draw(spriteBatch, caption, x + (parentWidth - textBounds.width) / 2, y);
	}

	public static void renderTextVerticalCentered(final String caption, final float x, final float y, final float size, final float parentHeight,
			final SpriteBatch spriteBatch) {
		uiFont.setScale(size);
		uiFont.getBounds(caption, textBounds);
		uiFont.draw(spriteBatch, caption, x, y + (parentHeight - textBounds.height) / 2);
	}

	public static void renderTextCentered(final String caption, final float x, final float y, final float size, final float parentWidth,
			final float parentHeight, final SpriteBatch spriteBatch) {
		uiFont.setScale(size);
		uiFont.getBounds(caption, textBounds);
		uiFont.draw(spriteBatch, caption, x + (parentWidth - textBounds.width) / 2, y + (parentHeight - textBounds.height) / 2);
	}

	public static void renderNinePatch(final Texture texture, final int sx, final int sy, final int swidth, final int sheight, final int topInset,
			final int leftInset, final int rightInset, final int bottomInset, final float tx, final float ty, final float twidth, final float theight,
			final SpriteBatch spriteBatch) {

		// top
		if (topInset > 0) {
			if (leftInset > 0) {
				spriteBatch.draw(texture, tx, ty, leftInset, topInset, sx, sy, leftInset, topInset, false, true);
			}
			if (rightInset > 0) {
				spriteBatch.draw(texture, tx + twidth - rightInset, ty, rightInset, topInset, sx + swidth - rightInset, sy, rightInset, topInset, false, true);
			}
			spriteBatch.draw(texture, tx + leftInset, ty, twidth - leftInset - rightInset, topInset, sx + leftInset, sy, swidth - leftInset - rightInset,
					topInset, false, true);
		}

		// center
		if (leftInset > 0) {
			spriteBatch.draw(texture, tx, ty + topInset, leftInset, theight - topInset - bottomInset, sx, sy + topInset, leftInset, sheight - topInset
					- bottomInset, false, true);
		}
		if (rightInset > 0) {
			spriteBatch.draw(texture, tx + twidth - rightInset, ty + topInset, rightInset, theight - topInset - bottomInset, sx + swidth - rightInset, sy
					+ topInset, rightInset, sheight - topInset - bottomInset, false, true);
		}
		spriteBatch.draw(texture, tx + leftInset, ty + topInset, twidth - leftInset - rightInset, theight - topInset - bottomInset, sx + leftInset, sy
				+ topInset, swidth - leftInset - rightInset, sheight - topInset - bottomInset, false, true);

		// bottom
		if (bottomInset > 0) {
			if (leftInset > 0) {
				spriteBatch.draw(texture, tx, ty + theight - bottomInset, leftInset, bottomInset, sx, sy + sheight - bottomInset, leftInset, bottomInset,
						false, true);
			}
			if (rightInset > 0) {
				spriteBatch.draw(texture, tx + twidth - rightInset, ty + theight - bottomInset, rightInset, bottomInset, sx + swidth - rightInset, sy + sheight
						- bottomInset, rightInset, bottomInset, false, true);
			}
			spriteBatch.draw(texture, tx + leftInset, ty + theight - bottomInset, twidth - leftInset - rightInset, bottomInset, sx + leftInset, sy + sheight
					- bottomInset, swidth - leftInset - rightInset, bottomInset, false, true);
		}
	}

}
