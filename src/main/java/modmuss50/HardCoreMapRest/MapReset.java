package modmuss50.HardCoreMapRest;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;
import modmuss50.HardCoreMapRest.server.CommandHCMR;
import modmuss50.HardCoreMapRest.server.PacketOpen;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;

import java.io.File;


@Mod(modid = "hardcoremapreset", name = "HardcoreMapReset", version = "1.2.1", acceptableRemoteVersions = "*")
public class MapReset {

	@Mod.Instance
	public static MapReset INSTANCE;

	public static final SimpleNetworkWrapper networkWrapper = NetworkRegistry.INSTANCE.newSimpleChannel("hardcoremapreset");

	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		networkWrapper.registerMessage(PacketOpen.class, PacketOpen.class, 0, Side.CLIENT);
	}

	@Mod.EventHandler
	public void load(FMLInitializationEvent event) {
		File backupDir = new File(Minecraft.getMinecraft().mcDataDir, "maps");
		if (!backupDir.exists())
			backupDir.mkdir();

		if (FMLCommonHandler.instance().getSide().isClient()) {
			MinecraftForge.EVENT_BUS.register(new GuiTweaker());
		}
	}

	@Mod.EventHandler
	public void serverInit(FMLServerStartingEvent event) {
		event.registerServerCommand(new CommandHCMR());
	}


}
