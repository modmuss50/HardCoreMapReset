package modmuss50.HardCoreMapReset.proxy;

import modmuss50.HardCoreMapReset.GuiTweaker;
import modmuss50.HardCoreMapReset.MapReset;
import modmuss50.HardCoreMapReset.TemplateSaveLoader;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;

import java.io.File;

public class ClientProxy extends CommonProxy {

    @Override
    public void preinit() {
        super.preinit();
    }

    @Override
    public void init() {
        super.init();
        MapReset.saveLoader = new TemplateSaveLoader(new File(Minecraft.getMinecraft().mcDataDir, "maps"));

        if (FMLCommonHandler.instance().getSide().isClient()) {
            MinecraftForge.EVENT_BUS.register(new GuiTweaker());
        }
    }
}
