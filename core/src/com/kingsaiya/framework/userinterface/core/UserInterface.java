package com.kingsaiya.framework.userinterface.core;

import java.util.ArrayList;
import java.util.HashMap;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class UserInterface {
	private final ArrayList<AbstractUIComponent> uiComponents = new ArrayList<AbstractUIComponent>();

	private final HashMap<String, UIAction> actionListener = new HashMap<String, UIAction>();

	public void addUIComponent(final AbstractUIComponent component) {
		if (!uiComponents.contains(component)) {
			uiComponents.add(component);
		}
	}

	public void removeUIComponent(final AbstractUIComponent component) {
		if (uiComponents.contains(component)) {
			uiComponents.remove(component);
		}
	}

	public void addActionListener(final String widgetname, final UIAction action) {
		actionListener.put(widgetname, action);
	}

	public void removeActionListener(final String widgetname) {
		actionListener.remove(widgetname);
	}

	public void render(final SpriteBatch spriteBatch) {
		for (int c = 0; c < uiComponents.size(); c++) {
			uiComponents.get(c).render(spriteBatch, 0, 0);
		}
	}

	@SuppressWarnings("unchecked")
	public <T extends AbstractUIComponent> T findWidget(final String widgetname) {
		for (int c = 0; c < uiComponents.size(); c++) {
			final AbstractUIComponent component = uiComponents.get(c);
			if (component.getWidgetName().equals(widgetname)) {
				return (T) component;
			}
			final AbstractUIComponent childWidget = component.findWidget(widgetname);
			if (childWidget != null) {
				return (T) childWidget;
			}
		}
		return null;
	}

	public boolean handleTouch(final int x, final int y, final boolean touch, final boolean pressed) {

		AbstractUIComponent currentMouseOverComponent = null;
		for (int uic = uiComponents.size() - 1; uic >= 0; uic--) {
			final AbstractUIComponent component = uiComponents.get(uic);
			if (!component.isHidden()) {
				if (component.getX() < x && component.getY() < y && component.getX() + component.getWidth() > x && component.getY() + component.getHeight() > y) {
					currentMouseOverComponent = component.updateMouseOver(x, y);
					if (currentMouseOverComponent != null) {
						break;
					}
				}
			}
		}
		if (currentMouseOverComponent != null) {
			if (touch && !pressed) {
				if (actionListener.containsKey(currentMouseOverComponent.getWidgetName())) {
					actionListener.get(currentMouseOverComponent.getWidgetName()).go(currentMouseOverComponent);
					return true;
				}
			}
		}
		return false;
	}

	public void removeAllUIWidgets() {
		for (int uic = uiComponents.size() - 1; uic >= 0; uic--) {
			uiComponents.remove(uic).removeAllChilds();
		}
		actionListener.clear();
	}

	public void notifyLanguageChanged() {
		for (int c = 0; c < uiComponents.size(); c++) {
			notifyLanguageChanged(uiComponents.get(c));
		}
	}

	private void notifyLanguageChanged(final AbstractUIComponent component) {
		if (component instanceof IUITranslatable) {
			((IUITranslatable) component).notifyLanguageChanged();
		}

		final ArrayList<AbstractUIComponent> childs = component.getChilds();
		for (int c = 0; c < childs.size(); c++) {
			notifyLanguageChanged(childs.get(c));
		}
	}

}
