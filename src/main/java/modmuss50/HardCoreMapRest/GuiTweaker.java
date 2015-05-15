package modmuss50.HardCoreMapRest;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiSelectWorld;
import net.minecraftforge.client.event.GuiScreenEvent;

import java.util.List;

public class GuiTweaker {

	public static final int BUTTON_ID = 405;

	@SubscribeEvent()
	public void onGuiInit(GuiScreenEvent.InitGuiEvent.Post evt) {
		if (evt.gui instanceof GuiSelectWorld) {
			List<GuiButton> buttonList = evt.buttonList;
			GuiButton button = new GuiButton(BUTTON_ID, 1, 1, 150, 20, "Create From Template");
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
