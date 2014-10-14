package com.kingsaiya.game.userinterface;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.kingsaiya.framework.tools.RenderTool;
import com.kingsaiya.framework.userinterface.core.AbstractUIComponent;
import com.kingsaiya.game.combat.unit.Unit;
import com.kingsaiya.game.userinterface.UIProgressbar.ProgressbarType;

public class UIHudPlayerStatusPanel extends AbstractUIComponent {

	private UIProgressbar healthProgressBar;
	private UIProgressbar manaProgressBar;
	private Unit unit;

	public UIHudPlayerStatusPanel(String widgetname, float x, float y, float width, float height) {
		super(widgetname, x, y, width, height);

		healthProgressBar = new UIProgressbar("health_progressbar", 35, 3, width - 35, 15, ProgressbarType.HEALTH);
		addChildComponent(healthProgressBar);

		manaProgressBar = new UIProgressbar("mana_progressbar", 35, 23, width - 35, 15, ProgressbarType.MANA);
		addChildComponent(manaProgressBar);
	}

	public void setUnit(final Unit unit) {
		this.unit = unit;
	}

	@Override
	public void render(SpriteBatch spriteBatch, float offsetX, float offsetY) {
		spriteBatch.setColor(1, 1, 1, 0.8f);
		RenderTool.renderNinePatch(RenderTool.getUITexture(), 32, 32, 32, 32, 9, 9, 9, 9, x + offsetX, y + offsetY + 1, 50, 19, spriteBatch);
		RenderTool.renderNinePatch(RenderTool.getUITexture(), 32, 32, 32, 32, 9, 9, 9, 9, x + offsetX, y + offsetY + 21, 50, 19, spriteBatch);
		spriteBatch.setColor(1, 1, 1, 1f);

		RenderTool.renderTextVerticalCentered("HP:", x + offsetX + 5, y + offsetY, 1f, 20, spriteBatch);
		RenderTool.renderTextVerticalCentered("MP:", x + offsetX + 5, y + offsetY + 20, 1f, 20, spriteBatch);

		healthProgressBar.setMaxProgress(unit.getMaxHitpoints());
		healthProgressBar.setProgress(unit.getCurrentHitpoints());
		healthProgressBar.setCaption(unit.getCurrentHitpoints() + " / " + unit.getMaxHitpoints());

		manaProgressBar.setMaxProgress(unit.getMaxMana());
		manaProgressBar.setProgress(unit.getCurrentMana());
		manaProgressBar.setCaption(unit.getCurrentMana() + " / " + unit.getMaxMana());
	}

}
