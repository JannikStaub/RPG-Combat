package com.kingsaiya.game.userinterface;

import com.kingsaiya.framework.userinterface.core.UserInterface;

public class UIInventoryWindow extends UIWindow {

	public UIInventoryWindow(String widgetname, float x, float y, float width, float height, UserInterface userInterface) {
		super(widgetname, x, y, width, height, userInterface);
		float borderInventoryButton = 5;
		float clitInventoryButton = 2;
		float maxwidthInventorywindow = width;
		float amountInventoryButton = 4;
		float widthInventoryButton = (maxwidthInventorywindow - 2 * borderInventoryButton - clitInventoryButton
				* (amountInventoryButton - 1))
				/ amountInventoryButton;
		addChildComponent(new UIInventorySelectionButton("FoodItems", borderInventoryButton, 35, widthInventoryButton, 42, "Food"));
		addChildComponent(new UIInventorySelectionButton("Weapons", borderInventoryButton + widthInventoryButton + clitInventoryButton, 35,
				widthInventoryButton, 42, "Weapons"));
		addChildComponent(new UIInventorySelectionButton("Crafting", borderInventoryButton + 2
				* (widthInventoryButton + clitInventoryButton), 35, widthInventoryButton, 42, "Crap"));
		addChildComponent(new UIInventorySelectionButton("Recepie", borderInventoryButton + 3
				* (widthInventoryButton + clitInventoryButton), 35, widthInventoryButton, 42, "Recepie"));
	}
}
