package modmuss50.HardCoreMapRest;

import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;
import modmuss50.HardCoreMapRest.server.CommandHCMR;
import modmuss50.HardCoreMapRest.server.PacketOpen;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;

import java.io.File;


@Mod(modid = "hardcoremapreset", name = "HardcoreMapReset", version = "1.8-2.0.0", acceptableRemoteVersions = "*")
public class MapReset {

	@Mod.Instance
	public static MapReset INSTANCE;

	public static final SimpleNetworkWrapper networkWrapper = NetworkRegistry.INSTANCE.newSimpleChannel("hardcoremapreset");
	public static TemplateSaveLoader saveLoader;

	public static boolean isDevVersion = false;

	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		networkWrapper.registerMessage(PacketOpen.class, PacketOpen.class, 0, Side.CLIENT);
	}

	@Mod.EventHandler
	public void load(FMLInitializationEvent event) {
		saveLoader = new TemplateSaveLoader(new File(Minecraft.getMinecraft().mcDataDir, "maps"));

				if (FMLCommonHandler.instance().getSide().isClient()) {
			MinecraftForge.EVENT_BUS.register(new GuiTweaker());
		}
	}

	@Mod.EventHandler
	public void serverInit(FMLServerStartingEvent event) {
		if(isDevVersion){
			event.registerServerCommand(new CommandHCMR());
		}
	}


}
