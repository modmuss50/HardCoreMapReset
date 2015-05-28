package modmuss50.HardCoreMapRest;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiSelectWorld;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.client.event.GuiScreenEvent;

import java.util.ArrayList;
import java.util.List;

public class GuiTweaker {

	public static int BUTTON_ID = 405;

	@SubscribeEvent()
	public void onGuiInit(GuiScreenEvent.InitGuiEvent.Post evt) {
		if (evt.gui instanceof GuiSelectWorld) {
			List<GuiButton> buttonList = evt.buttonList;
			ArrayList<Integer> buttonIDList = new ArrayList<Integer>();

			// Width - 40 - (2 x 4) for spaces
			int width = (evt.gui.width - 48) / 3;
			int yPosition = 1;

			boolean used = false;
			for (GuiButton button : buttonList) {
				buttonIDList.add(button.id);
				// 3 - create
				if (button.id == 3) {
					button.width = width;
					button.xPosition = (evt.gui.width / 2) - (width / 2);
					yPosition = button.yPosition;
				}
				// 1 - select
				else if (button.id == 1) {
					button.width = width;
					button.xPosition = (evt.gui.width / 2) - (width / 2) - 4 - width;
				}
				else if (button.id == BUTTON_ID) {
					used = true;
				}
			}

			if (used) {
				while (used) {
					BUTTON_ID += 1;
					if (buttonIDList.indexOf(BUTTON_ID) < 0) {
						used = false;
					}
				}
			}
			int xPosition = (evt.gui.width / 2) + (width / 2) + 4;
			GuiButton button = new GuiButton(BUTTON_ID, xPosition, yPosition, width, 20, I18n.format("gui.hardcoremapreset.create_button"));
			buttonList.add(button);
		}
	}

	@SubscribeEvent
	public void onActionPerformed(GuiScreenEvent.ActionPerformedEvent evt) {
		if (evt.gui instanceof GuiSelectWorld) {
			if (evt.button.id == BUTTON_ID) {
				Minecraft.getMinecraft().displayGuiScreen(new GuiMapList(evt.gui));
			}
		}
	}
}
