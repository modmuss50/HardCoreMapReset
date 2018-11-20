package modmuss50.hcmr;

import modmuss50.hcmr.proxy.CommonProxy;
import modmuss50.hcmr.voidWorld.HCMRWorldTypes;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;

import java.io.File;

@Mod(modid = "hardcoremapreset", name = "HardcoreMapReset", version = "@MODVERSION@", acceptableRemoteVersions = "*", dependencies = "required-after:reborncore")
public class HardCoreMapReset {

	@SidedProxy(clientSide = "modmuss50.hcmr.proxy.ClientProxy", serverSide = "modmuss50.hcmr.proxy.CommonProxy")
	public static CommonProxy proxy;

	@Mod.Instance
	public static HardCoreMapReset INSTANCE;

	public static final SimpleNetworkWrapper networkWrapper = NetworkRegistry.INSTANCE.newSimpleChannel("hardcoremapreset");

	public static boolean showCreateWorld;
	public static boolean voidWorldGeneration;
	public static String markerBlcok;

	Configuration config;

	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		proxy.preinit();
		config = new Configuration(new File(event.getModConfigurationDirectory(), "HardcoreMapReset.cfg"));
		reLoadConfig();
	}

	public void reLoadConfig() {
		config.load();
		showCreateWorld = config.get(Configuration.CATEGORY_GENERAL, "Show Create World Button", true).getBoolean();
		voidWorldGeneration = config.get(Configuration.CATEGORY_GENERAL, "Enabled Void World Generation (Required for structure based temapltes)", false).getBoolean();
		markerBlcok = config.get(Configuration.CATEGORY_GENERAL, "Block to be used as spawn marker", "minecraft:diamond_block").getString();
		config.save();
	}


	@Mod.EventHandler
	public void load(FMLInitializationEvent event) {
		proxy.init();
		if(voidWorldGeneration){
			HCMRWorldTypes.init();
		}
	}
}
