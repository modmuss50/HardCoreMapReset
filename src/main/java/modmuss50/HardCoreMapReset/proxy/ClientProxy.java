package modmuss50.HardCoreMapReset.proxy;

import modmuss50.HardCoreMapReset.GuiTweaker;
import modmuss50.HardCoreMapReset.MapReset;
import modmuss50.HardCoreMapReset.TemplateSaveLoader;
import modmuss50.HardCoreMapReset.server.PacketOpen;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;

import java.io.File;

public class ClientProxy extends CommonProxy {

    @Override
    public void preinit() {
        super.preinit();
        MapReset.networkWrapper.registerMessage(PacketOpen.class, PacketOpen.class, 0, Side.CLIENT);
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
