package com.kingsaiya.game.userinterface;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.kingsaiya.framework.tools.RenderTool;
import com.kingsaiya.framework.userinterface.core.AbstractUIComponent;

public class UIWindowCloseButton extends AbstractUIComponent {

	public UIWindowCloseButton(String widgetname, float x, float y, float width, float height) {
		super(widgetname, x, y, width, height);
	}

	@Override
	public void render(SpriteBatch spriteBatch, float offsetX, float offsetY) {
		RenderTool.renderNinePatch(RenderTool.getUITexture(), 64, 0, 28, 28, 0, 0, 0, 0, x + offsetX, y + offsetY, width, height, spriteBatch);
	}
}
