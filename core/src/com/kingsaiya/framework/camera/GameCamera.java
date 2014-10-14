package com.kingsaiya.framework.camera;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Rectangle;
import com.kingsaiya.game.combat.dummy.Vector2f;

public class GameCamera extends OrthographicCamera {
	// cached variables for performance
	private boolean update = true;
	private Rectangle worldViewBounds = new Rectangle();

	public GameCamera(final float viewportWidth, final float viewportHeight) {
		setToOrtho(true, viewportWidth, viewportHeight);
		super.update();
	}

	@Override
	public void update() {
		if (update) {
			super.update();

			float width = viewportWidth * zoom;
			float height = viewportHeight * zoom;
			worldViewBounds.set(position.x - width * 0.5f, position.y - height * 0.5f, width, height);

			update = false;
		}
	}

	public void move(final float xOffset, final float yOffset) {
		if (xOffset != 0 || yOffset != 0) {
			translate(xOffset, yOffset);
			update = true;
		}
	}

	public void setPosition(final Vector2f position) {
		setPosition(position.x, position.y);
	}

	public void setPosition(final float x, final float y) {
		if (x != position.x || y != position.y) {
			position.x = x;
			position.y = y;
			update = true;
		}
	}

	public void setZoom(float zoom) {
		if (zoom != this.zoom) {
			this.zoom = zoom;
			update = true;
		}
	}

	public void adjustZoom(float offset) {
		if (offset != 0) {
			this.zoom = Math.max(0.01f, zoom - offset);
			update = true;
		}
	}

	public float getScreenToWorldCoordinateX(int x) {
		return position.x + (x - viewportWidth * 0.5f) * zoom;
	}

	public float getScreenToWorldCoordinateY(int y) {
		return position.y + (y - viewportHeight * 0.5f) * zoom;
	}

	public float getZoomed(final float distance) {
		return distance * zoom;
	}

	public float getZoom() {
		return zoom;
	}

	public float getLeftScreenWorldX() {
		update();
		return worldViewBounds.x;
	}

	public float getRightScreenWorldX() {
		update();
		return worldViewBounds.x + worldViewBounds.width;
	}

	public float getTopScreenWorldY() {
		update();
		return worldViewBounds.y;
	}

	public float getBottomScreenWorldY() {
		update();
		return worldViewBounds.y + worldViewBounds.height;
	}

	public float getWorldX() {
		return position.x;
	}

	public float getWorldY() {
		return position.y;
	}

	public float getScreenWorldWidth() {
		update();
		return worldViewBounds.width;
	}

	public float getScreenWorldHeight() {
		update();
		return worldViewBounds.height;
	}

	public Rectangle getWorldViewBounds() {
		update();
		return worldViewBounds;
	}

	public boolean isVisible(float x, float y, float width, float height) {
		if (x + width >= getLeftScreenWorldX() && x < getRightScreenWorldX()) {
			if (y + height >= getTopScreenWorldY() && y < getBottomScreenWorldY()) {
				return true;
			}
		}

		return false;
	}

	public boolean isVisible(int x, int y, int width, int height) {
		if (x + width >= getLeftScreenWorldX() && x < getRightScreenWorldX()) {
			if (y + height >= getTopScreenWorldY() && y < getBottomScreenWorldY()) {
				return true;
			}
		}

		return false;
	}

}
