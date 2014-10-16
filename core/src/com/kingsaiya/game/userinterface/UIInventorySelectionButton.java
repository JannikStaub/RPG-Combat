package com.kingsaiya.game.userinterface;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.kingsaiya.framework.tools.RenderTool;
import com.kingsaiya.framework.userinterface.core.AbstractUIComponent;

public class UIInventorySelectionButton extends AbstractUIComponent {
	String caption;

	public UIInventorySelectionButton(String widgetname, float x, float y, float width, float height, String caption) {
		super(widgetname, x, y, width, height);
		this.caption = caption;
	}

	@Override
	protected void render(SpriteBatch spriteBatch, float offsetX, float offsetY) {
		RenderTool.renderNinePatch(RenderTool.getUITexture(), 32, 16, 32, 16, 2, 2, 2, 2, x + offsetX, y + offsetY, width, height,
				spriteBatch);
		RenderTool.renderTextCentered(caption, x + offsetX, y + offsetY, 1f, Color.YELLOW, width, height, spriteBatch);

	}

}
