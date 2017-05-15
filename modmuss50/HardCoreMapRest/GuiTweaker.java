package modmuss50.HardCoreMapRest;

import net.minecraft.src.GuiButton;
import net.minecraft.src.GuiSelectWorld;

import java.util.ArrayList;
import java.util.List;

public class GuiTweaker {

	public static int BUTTON_ID = 405;

	public static CustomButton button;

	public static void onGuiInit(GuiSelectWorld gui) {
			List<GuiButton> buttonList = gui.getButtonList();
			if(buttonList.contains(button)){
				return;
			}
			ArrayList<Integer> buttonIDList = new ArrayList<Integer>();

			// Width - 40 - (2 x 4) for spaces
			int width = (gui.width - 48) / 3;
			int yPosition = 1;

			int xPosition = (gui.width / 2) + (width / 2) + 4;

			button = new CustomButton(BUTTON_ID, xPosition, yPosition, width, 20, "Create From Template");
			buttonList.add(button);
	}
}
