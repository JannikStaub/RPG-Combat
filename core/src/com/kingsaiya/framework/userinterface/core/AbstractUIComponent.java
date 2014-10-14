package com.kingsaiya.framework.userinterface.core;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class AbstractUIComponent {
	protected final String widgetname;
	private final ArrayList<AbstractUIComponent> childComponents = new ArrayList<AbstractUIComponent>();

	protected float x = 0;
	protected float y = 0;
	protected float width = 0;
	protected float height = 0;

	protected boolean pickable = true;
	protected boolean hidden = false;

	public AbstractUIComponent(final String widgetname, final float x, final float y, final float width, final float height) {
		this.widgetname = widgetname;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	public void renderWithChilds(final SpriteBatch spriteBatch, final float offsetX, final float offsetY) {

		render(spriteBatch, offsetX, offsetY);

		for (int c = 0; c < childComponents.size(); c++) {
			childComponents.get(c).renderWithChilds(spriteBatch, x + offsetX, y + offsetY);
		}
	}

	protected abstract void render(final SpriteBatch spriteBatch, final float offsetX, final float offsetY);

	public String getWidgetName() {
		return widgetname;
	}

	public float getX() {
		return x;
	}

	public void setX(final float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(final float y) {
		this.y = y;
	}

	public float getWidth() {
		return width;
	}

	public void setWidth(final float width) {
		this.width = width;
	}

	public float getHeight() {
		return height;
	}

	public void setHeight(final float height) {
		this.height = height;
	}

	public void setHidden(final boolean hidden) {
		this.hidden = hidden;
	}

	public boolean isHidden() {
		return hidden;
	}

	public void setPickable(final boolean pickable) {
		this.pickable = pickable;
	}

	public boolean isPickable() {
		return pickable;
	}

	public void addChildComponent(final AbstractUIComponent component) {
		if (!childComponents.contains(component)) {
			childComponents.add(component);
		}
	}

	public AbstractUIComponent updateMouseOver(final float mouseX, final float mouseY) {
		AbstractUIComponent mouseoverComponent = pickable ? this : null;
		for (int uic = 0; uic < childComponents.size(); uic++) {
			final AbstractUIComponent component = childComponents.get(uic);
			if (x + component.getX() < mouseX && y + component.getY() < mouseY && x + component.getX() + component.getWidth() > mouseX
					&& y + component.getY() + component.getHeight() > mouseY) {
				mouseoverComponent = component.updateMouseOver(mouseX - x, mouseY - y);
			}
		}
		return mouseoverComponent;
	}

	public AbstractUIComponent findWidget(final String widgetname) {
		for (int c = 0; c < childComponents.size(); c++) {
			final AbstractUIComponent component = childComponents.get(c);
			if (component.getWidgetName().equals(widgetname)) {
				return component;
			}
			final AbstractUIComponent childWidget = component.findWidget(widgetname);
			if (childWidget != null) {
				return childWidget;
			}
		}
		return null;
	}

	public void removeAllChilds() {
		childComponents.clear();
	}

	public void removeChild(AbstractUIComponent child) {
		childComponents.remove(child);
	}

	public ArrayList<AbstractUIComponent> getChilds() {
		return childComponents;
	}
}
