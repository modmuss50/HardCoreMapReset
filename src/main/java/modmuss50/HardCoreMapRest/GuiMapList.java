package modmuss50.HardCoreMapRest;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiSelectWorld;

import java.io.File;
import java.util.ArrayList;

public class GuiMapList extends GuiScreen {
	public GuiScreen parent;
	public ArrayList<String> maps = new ArrayList<String>();
	public ArrayList<String> selectedMaps = new ArrayList<String>();
	public int yes = 1;
	public int no = 2;
	public GuiButton yesButton;

	public GuiMapList() {
		Minecraft mc = Minecraft.getMinecraft();
		File saveDir = new File(mc.mcDataDir, "saves");
		File backupDir = new File(mc.mcDataDir, "maps");
		for (File saveFile : saveDir.listFiles()) {
			if (saveFile.isDirectory()) {
				for (File backupFile : backupDir.listFiles()) {
					if (backupFile.getName().equals(saveFile.getName())) {
						maps.add(saveFile.getName());
					}
				}
			}
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
			Minecraft.getMinecraft().displayGuiScreen(new GuiSelectWorld(new GuiMainMenu()));
		}

	}

	@Override
	public void drawScreen(int x, int y, float f) {
		this.drawDefaultBackground();
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

	public void setParent(GuiScreen parent) {
		this.parent = parent;
	}
}
