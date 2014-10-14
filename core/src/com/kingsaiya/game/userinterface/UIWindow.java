package com.kingsaiya.game.userinterface;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.kingsaiya.framework.tools.RenderTool;
import com.kingsaiya.framework.userinterface.core.AbstractUIComponent;
import com.kingsaiya.framework.userinterface.core.UIAction;
import com.kingsaiya.framework.userinterface.core.UserInterface;
import com.kingsaiya.game.userinterface.UIProgressbar.ProgressbarType;

public class UIWindow extends AbstractUIComponent {

	private String caption = "Window";

	public UIWindow(String widgetname, float x, float y, float width, float height, final UserInterface userInterface) {
		super(widgetname, x, y, width, height);

		UIWindowCloseButton closeWindowButton = new UIWindowCloseButton(widgetname + "_close_button", width - 31, 2, 28, 28);
		userInterface.addActionListener(widgetname + "_close_button", new UIAction() {
			@Override
			public void go(AbstractUIComponent widget) {
				userInterface.removeActionListener(widget.getWidgetName());
				userInterface.removeUIComponent(UIWindow.this);
			}
		});
		addChildComponent(closeWindowButton);

		addChildComponent(new UIProgressbar(widgetname + "_progressbar1", 10, 42, 100, 16, ProgressbarType.HEALTH));
		addChildComponent(new UIProgressbar(widgetname + "_progressbar2", 10, 62, 100, 16, ProgressbarType.MANA));
		addChildComponent(new UIProgressbar(widgetname + "_progressbar3", 10, 82, 100, 16, ProgressbarType.ENERGY));
		addChildComponent(new UIProgressbar(widgetname + "_progressbar4", 10, 102, 100, 16, ProgressbarType.RAGE));
	}

	@Override
	public void render(SpriteBatch spriteBatch, float offsetX, float offsetY) {
		RenderTool.renderNinePatch(RenderTool.getUITexture(), 0, 0, 32, 17, 3, 3, 3, 3, x + offsetX, y + offsetY, width, 32, spriteBatch);
		RenderTool.renderNinePatch(RenderTool.getUITexture(), 0, 17, 32, 15, 1, 3, 3, 3, x + offsetX, y + offsetY + 32, width, height - 32, spriteBatch);

		RenderTool.renderTextVerticalCentered(caption, x + offsetX + 10, y + offsetY, 1f, 32, spriteBatch);
	}
}
