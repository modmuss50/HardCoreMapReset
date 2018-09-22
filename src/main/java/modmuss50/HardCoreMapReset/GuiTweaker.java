package modmuss50.HardCoreMapReset;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiWorldSelection;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.fml.client.config.GuiButtonExt;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;
import java.util.List;

public class GuiTweaker {

	public static int BUTTON_ID = 405;

	@SubscribeEvent()
	public void onGuiInit(GuiScreenEvent.InitGuiEvent.Post evt) {
		if (evt.getGui() instanceof GuiWorldSelection) {
			MapReset.INSTANCE.reLoadConfig();
			List<GuiButton> buttonList = evt.getButtonList();
			ArrayList<Integer> buttonIDList = new ArrayList<Integer>();

			// Width - 40 - (2 x 4) for spaces
			int width = (evt.getGui().width - 48) / 3;
			int yPosition = 1;

			boolean newWorldButton = MapReset.showCreateWorld;

			boolean used = false;
			for (GuiButton button : buttonList) {
				buttonIDList.add(button.id);
				// 3 - create
				if (button.id == 3) {
					button.width = width;
					button.x = (evt.getGui().width / 2) - (width / 2);
					yPosition = button.y;
					if (!newWorldButton) {
						button.visible = false;
					}
				}
				// 1 - select
				else if (button.id == 1) {
					button.width = width;
					button.x = (evt.getGui().width / 2) - (width / 2) - 4 - width;
					if (!newWorldButton) {
						button.x = (evt.getGui().width / 2) - 4 - width;
					}
				} else if (button.id == BUTTON_ID) {
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
			int xPosition = (evt.getGui().width / 2) + (width / 2) + 4;
			if (!newWorldButton) {
				xPosition = (evt.getGui().width / 2);
			}
			GuiButton button = new GuiButtonExt(BUTTON_ID, xPosition, yPosition, width, 20, I18n.format("gui.hardcoremapreset.create_button"));
			buttonList.add(button);
		}
	}

	@SubscribeEvent
	public void onActionPerformed(GuiScreenEvent.ActionPerformedEvent evt) {
		if (evt.getGui() instanceof GuiWorldSelection) {
			if (evt.getButton().id == BUTTON_ID) {
				Minecraft.getMinecraft().displayGuiScreen(new GuiMapList(evt.getGui()));
			}
		}
	}
}
