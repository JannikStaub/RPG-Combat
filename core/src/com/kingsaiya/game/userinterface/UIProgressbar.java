package com.kingsaiya.game.userinterface;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.kingsaiya.framework.tools.RenderTool;
import com.kingsaiya.framework.userinterface.core.AbstractUIComponent;

public class UIProgressbar extends AbstractUIComponent {

	private final ProgressbarType type;
	private String caption = null;
	private float maxProgress = 1;
	private float progress = 0;

	public UIProgressbar(final String widgetname, final float x, final float y, final float width, final float height, final ProgressbarType type) {
		super(widgetname, x, y, width, height);
		this.type = type;
	}

	@Override
	public void render(final SpriteBatch spriteBatch, final float offsetX, final float offsetY) {
		RenderTool.renderNinePatch(RenderTool.getUITexture(), 32, 0, 5, 8, 1, 1, 1, 1, x + offsetX, y + offsetY, width, height, spriteBatch);

		final float progressWidth = (width - 2) * (progress / maxProgress);
		RenderTool.renderNinePatch(RenderTool.getUITexture(), 37, 2 + type.ordinal() * 4, 3, 2, 1, 1, 0, 0, x + offsetX + 1, y + offsetY + 1, width - 2,
				height - 2, spriteBatch);
		if (progressWidth > 0) {
			RenderTool.renderNinePatch(RenderTool.getUITexture(), 37, type.ordinal() * 4, 3, 2, 1, 1, 0, 0, x + offsetX + 1, y + offsetY + 1, progressWidth,
					height - 2, spriteBatch);
		}

		if (caption != null && caption.length() > 0) {
			RenderTool.renderTextCentered(caption, x + offsetX, y + offsetY, 0.8f, width, height, spriteBatch);
		}

		progress = (progress + 0.01f) % maxProgress;
	}

	public String getCaption() {
		return caption;
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}

	public float getMaxProgress() {
		return maxProgress;
	}

	public void setMaxProgress(float maxProgress) {
		this.maxProgress = maxProgress;
	}

	public float getProgress() {
		return progress;
	}

	public void setProgress(float progress) {
		this.progress = progress;
	}

	public static enum ProgressbarType {
		HEALTH, MANA, ENERGY, RAGE
	}
}
