package com.kingsaiya.game.userinterface;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.kingsaiya.framework.tools.RenderTool;
import com.kingsaiya.framework.userinterface.core.AbstractUIComponent;

public class UIInventoryButton extends AbstractUIComponent {

	public UIInventoryButton(String widgetname, float x, float y, float width, float height) {
		super(widgetname, x, y, width, height);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void render(SpriteBatch spriteBatch, float offsetX, float offsetY) {
		RenderTool.renderNinePatch(RenderTool.getUITexture(), 32, 16, 32, 16, 2, 2, 2, 2, x + offsetX, y + offsetY, width, height, spriteBatch);
		RenderTool.renderNinePatch(RenderTool.getUITexture(), 93, 0, 25, 19, 0, 0, 0, 0, x + offsetX + (width - 25) / 2, y + offsetY + (height - 19) / 2, 25,
				19, spriteBatch);

	}

}
