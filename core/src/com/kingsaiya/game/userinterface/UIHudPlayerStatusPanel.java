package com.kingsaiya.game.userinterface;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.kingsaiya.framework.entitysystem.entity.Entity;
import com.kingsaiya.framework.tools.RenderTool;
import com.kingsaiya.framework.userinterface.core.AbstractUIComponent;
import com.kingsaiya.game.entitysystem.components.HealthComponent;
import com.kingsaiya.game.userinterface.UIProgressbar.ProgressbarType;

public class UIHudPlayerStatusPanel extends AbstractUIComponent {

	private UIProgressbar healthProgressBar;
	private UIProgressbar manaProgressBar;
	private Entity unit;
	private HealthComponent unitHealthComponent;

	public UIHudPlayerStatusPanel(String widgetname, float x, float y, float width, float height) {
		super(widgetname, x, y, width, height);

		healthProgressBar = new UIProgressbar("health_progressbar", 35, 3, width - 35, 15, ProgressbarType.HEALTH);
		addChildComponent(healthProgressBar);

		manaProgressBar = new UIProgressbar("mana_progressbar", 35, 23, width - 35, 15, ProgressbarType.MANA);
		addChildComponent(manaProgressBar);
	}

	public void setUnit(final Entity unit) {
		this.unit = unit;
		if (unit != null) {
			unitHealthComponent = unit.getEntityComponent(HealthComponent.class);
		} else {
			unitHealthComponent = null;
		}
	}

	@Override
	public void render(SpriteBatch spriteBatch, float offsetX, float offsetY) {
		spriteBatch.setColor(1, 1, 1, 0.8f);
		RenderTool
				.renderNinePatch(RenderTool.getUITexture(), 32, 32, 32, 32, 9, 9, 9, 9, x + offsetX, y + offsetY + 1, 50, 19, spriteBatch);
		RenderTool.renderNinePatch(RenderTool.getUITexture(), 32, 32, 32, 32, 9, 9, 9, 9, x + offsetX, y + offsetY + 21, 50, 19,
				spriteBatch);
		spriteBatch.setColor(1, 1, 1, 1f);

		RenderTool.renderTextVerticalCentered("HP:", x + offsetX + 5, y + offsetY, 1f, Color.WHITE, 20, spriteBatch);
		RenderTool.renderTextVerticalCentered("MP:", x + offsetX + 5, y + offsetY + 20, 1f, Color.WHITE, 20, spriteBatch);

		healthProgressBar.setMaxProgress(unitHealthComponent.getMaxHitpoints());
		healthProgressBar.setProgress(unitHealthComponent.getCurrentHitpoints());
		healthProgressBar.setCaption(unitHealthComponent.getCurrentHitpoints() + " / " + unitHealthComponent.getMaxHitpoints());

		manaProgressBar.setMaxProgress(unitHealthComponent.getMaxMana());
		manaProgressBar.setProgress(unitHealthComponent.getCurrentMana());
		manaProgressBar.setCaption(unitHealthComponent.getCurrentMana() + " / " + unitHealthComponent.getMaxMana());
	}

}
