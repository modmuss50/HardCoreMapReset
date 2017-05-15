import modmuss50.HardCoreMapRest.GuiTweaker;
import modmuss50.HardCoreMapRest.ResetMaps;
import modmuss50.HardCoreMapRest.TemplateSaveLoader;
import net.minecraft.client.Minecraft;
import net.minecraft.src.BaseMod;
import net.minecraft.src.GuiScreen;
import net.minecraft.src.GuiSelectWorld;
import net.minecraft.src.ModLoader;

import java.io.File;

public class mod_HardCoreMapReset extends BaseMod {

	@Override
	public String getVersion() {
		return "1";
	}

	@Override
	public void load() {
		ModLoader.setInGUIHook(this, true, false);
		ResetMaps.saveLoader = new TemplateSaveLoader(new File(ModLoader.getMinecraftInstance().mcDataDir, "maps"));
	}

	GuiScreen lastScreen;

	@Override
	public boolean onTickInGUI(float tick, Minecraft game, GuiScreen gui) {
		if(gui != null){
			if(gui != lastScreen){
				if(gui instanceof GuiSelectWorld){
					GuiTweaker.onGuiInit((GuiSelectWorld) gui);
				}
				lastScreen = gui;
			}
		}
		return true;
	}
}
