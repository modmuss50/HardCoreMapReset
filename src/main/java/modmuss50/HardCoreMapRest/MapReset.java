package modmuss50.HardCoreMapRest;

import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;
import modmuss50.HardCoreMapRest.proxy.CommonProxy;
import modmuss50.HardCoreMapRest.server.CommandHCMR;
import modmuss50.HardCoreMapRest.server.PacketOpen;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Mod;

import java.io.File;

@Mod(modid = "hardcoremapreset", name = "HardcoreMapReset", version = "2.1.0", acceptableRemoteVersions = "*")
public class MapReset {
	
	@SidedProxy(clientSide = "modmuss50.HardCoreMapRest.proxy.ClientProxy", serverSide = "modmuss50.HardCoreMapRest.proxy.CommonProxy")
	public static CommonProxy proxy;

	@Mod.Instance
	public static MapReset INSTANCE;

	public static final SimpleNetworkWrapper networkWrapper = NetworkRegistry.INSTANCE.newSimpleChannel("hardcoremapreset");
	public static TemplateSaveLoader saveLoader;

	public static boolean isDevVersion = false;
    public static boolean showCreateWorld;

    Configuration config;

	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		proxy.preinit();
        config = new Configuration(new File(event.getModConfigurationDirectory(), "HardcoreMapReset.cfg"));
        reLoadConfig();
	}

    public void reLoadConfig(){
        config.load();
        showCreateWorld = config.get(Configuration.CATEGORY_GENERAL, "Show Create World Button", true).getBoolean();
        config.save();
    }


	@Mod.EventHandler
	public void load(FMLInitializationEvent event) {
		proxy.init();
	}

	@Mod.EventHandler
	public void serverInit(FMLServerStartingEvent event) {
		if(isDevVersion){
			event.registerServerCommand(new CommandHCMR());
		}
	}
}
