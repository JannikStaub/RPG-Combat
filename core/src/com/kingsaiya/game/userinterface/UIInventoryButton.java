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
		RenderTool.renderNinePatch(RenderTool.getUITexture(), 64, 0, 28, 28, 2, 2, 2, 2, x + offsetX, y + offsetY, width, height,
				spriteBatch);

	}

}
