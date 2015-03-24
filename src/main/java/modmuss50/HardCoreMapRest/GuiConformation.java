package modmuss50.HardCoreMapRest;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiSelectWorld;

import java.util.ArrayList;
import java.util.List;


public class GuiConformation extends GuiScreen {
	public GuiScreen parent;
	public ArrayList<String> maps = new ArrayList<String>();

	public GuiConformation(List<String> mapNames) {
		maps.addAll(mapNames);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void initGui() {
		this.buttonList.add(new GuiButton(1, 10, this.height - 38, "Yes"));
		this.buttonList.add(new GuiButton(2, this.width - 10 - 200, this.height - 38, "No"));
	}

	@Override
	public void actionPerformed(GuiButton button) {
		if (button.enabled && button.id == 1) {
			for (String name : maps) {
				ResetMaps.resetmap(name);
			}
			Minecraft.getMinecraft().displayGuiScreen(new GuiSelectWorld(new GuiMainMenu()));
		}
		if (button.enabled && button.id == 2) {
			Minecraft.getMinecraft().displayGuiScreen(new GuiSelectWorld(new GuiMainMenu()));
		}

	}

	@Override
	public void drawScreen(int x, int y, float f) {
		this.drawDefaultBackground();
		this.drawCenteredString(this.fontRendererObj, "Are you sure you want to reset " + maps.size() + " map(s)?", this.width / 2, 40, 0xFFFFFF);
		this.drawCenteredString(this.fontRendererObj, "You will loose all of your progress and worlds.", this.width / 2, 70, 0xFFFFFF);
		this.drawCenteredString(this.fontRendererObj, "Your save(s) will be reset back to default(s).", this.width / 2, 80, 0xFFFFFF);
		this.drawCenteredString(this.fontRendererObj, "THIS CANNOT BE UNDONE! ", this.width / 2, this.height - 50, 0xff0000);
		drawScreen2(x, y, f);
		super.drawScreen(x, y, f);
	}

	public void drawScreen2(int p_73863_1_, int p_73863_2_, float p_73863_3_) {

	}

	public void setParent(GuiScreen parent) {
		this.parent = parent;
	}
}
