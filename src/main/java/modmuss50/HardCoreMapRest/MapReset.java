package modmuss50.HardCoreMapRest;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import modmuss50.HardCoreMapRest.server.CommandHCMR;
import modmuss50.HardCoreMapRest.server.packets.ChannelHandler;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;

import java.io.File;


@Mod(modid = "hardcoremapreset", name = "HardcoreMapReset", version = "1.2.1", acceptableRemoteVersions="*")
public class MapReset {

	@Mod.Instance
	public static MapReset INSTANCE;


	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		ChannelHandler.setChannels(NetworkRegistry.INSTANCE.newChannel("HardcoreMapReset", new ChannelHandler()));
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
