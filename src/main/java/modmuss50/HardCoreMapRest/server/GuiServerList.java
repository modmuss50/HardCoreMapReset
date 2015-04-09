package modmuss50.HardCoreMapRest.server;

import cpw.mods.fml.client.FMLClientHandler;
import modmuss50.HardCoreMapRest.GuiConformation;
import modmuss50.HardCoreMapRest.ToggleButton;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;

import java.util.ArrayList;
import java.util.List;

public class GuiServerList extends GuiScreen {
	public ArrayList<String> maps = new ArrayList<String>();
	public ArrayList<String> selectedMaps = new ArrayList<String>();
	public int yes = 1;
	public int no = 2;
	public GuiButton yesButton;

	public GuiServerList(List<String> mapNames) {
		for (String map : mapNames) {
			maps.add(map);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void initGui() {
		int id = 0;
		for (String map : maps) {
			this.buttonList.add(new ToggleButton(id, this.width / 2 - 100, (22 * id) + 30, map));
			id++;
		}

		yes = id;
		yesButton = new GuiButton(yes, 10, this.height - 38, "Reset maps");
		this.buttonList.add(yesButton);
		id++;
		no = id;
		this.buttonList.add(new GuiButton(id, this.width - 10 - 200, this.height - 38, "Cancel"));
	}

	@Override
	public void actionPerformed(GuiButton guiButton) {
		selectedMaps.clear();
		for (Object button : this.buttonList) {
			if (button instanceof ToggleButton) {
				if (((ToggleButton) button).isOn) {
					selectedMaps.add(((ToggleButton) button).displayString);
				}
			}
		}
		if (selectedMaps.isEmpty()) {
			yesButton.enabled = false;
		} else {
			yesButton.enabled = true;
		}
		if (guiButton.enabled && guiButton.id == yes) {
			Minecraft.getMinecraft().displayGuiScreen(new GuiConformation(selectedMaps));
		}
		if (guiButton.enabled && guiButton.id == no) {
			this.onGuiClosed();
			FMLClientHandler.instance().showGuiScreen(null);
		}

	}

	@Override
	public void drawScreen(int x, int y, float f) {
		//this.drawDefaultBackground();
		selectedMaps.clear();
		for (Object button : this.buttonList) {
			if (button instanceof ToggleButton) {
				if (((ToggleButton) button).isOn) {
					selectedMaps.add(((ToggleButton) button).displayString);
				}
			}
		}
		if (selectedMaps.isEmpty()) {
			yesButton.enabled = false;
		} else {
			yesButton.enabled = true;
		}
		yesButton.displayString = "Reset " + selectedMaps.size() + " maps";
		this.drawCenteredString(this.fontRendererObj, "Select the maps you want to reset.", this.width / 2, 10, 0xFFFFFF);
		super.drawScreen(x, y, f);
	}

	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}
}