package modmuss50.hcmr.proxy;

import modmuss50.hcmr.GuiTweaker;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;

public class ClientProxy extends CommonProxy {

	@Override
	public void preinit() {
		super.preinit();
	}

	@Override
	public void init() {
		super.init();
		if (FMLCommonHandler.instance().getSide().isClient()) {
			MinecraftForge.EVENT_BUS.register(new GuiTweaker());
		}
	}
}
