package modmuss50.HardCoreMapRest;

import net.minecraft.client.Minecraft;
import net.minecraft.src.GuiButton;
import net.minecraft.src.GuiMainMenu;
import net.minecraft.src.ModLoader;

/**
 * Created by modmuss50 on 15/05/2017.
 */
public class CustomButton extends GuiButton {
	public CustomButton(int par1, int par2, int par3, String par4Str) {
		super(par1, par2, par3, par4Str);
	}

	public CustomButton(int par1, int par2, int par3, int par4, int par5, String par6Str) {
		super(par1, par2, par3, par4, par5, par6Str);
	}

	@Override
	public boolean mousePressed(Minecraft par1Minecraft, int par2, int par3) {
		if(super.mousePressed(par1Minecraft, par2, par3)){
			ModLoader.getMinecraftInstance().displayGuiScreen(new GuiMapList(new GuiMainMenu()));
			return true;
		}
		return false;
	}
}
