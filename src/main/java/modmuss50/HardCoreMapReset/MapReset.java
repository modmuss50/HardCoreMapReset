package modmuss50.HardCoreMapReset;

import modmuss50.HardCoreMapReset.proxy.CommonProxy;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import reborncore.common.IModInfo;

import java.io.File;

@Mod(modid = "hardcoremapreset", name = "HardcoreMapReset", version = "@MODVERSION@", acceptableRemoteVersions = "*", dependencies = "required-after:reborncore")
public class MapReset implements IModInfo {

	@SidedProxy(clientSide = "modmuss50.HardCoreMapReset.proxy.ClientProxy", serverSide = "modmuss50.HardCoreMapReset.proxy.CommonProxy")
	public static CommonProxy proxy;

	@Mod.Instance
	public static MapReset INSTANCE;

	public static final SimpleNetworkWrapper networkWrapper = NetworkRegistry.INSTANCE.newSimpleChannel("hardcoremapreset");

	public static boolean showCreateWorld;

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
		config.save();
	}


	@Mod.EventHandler
	public void load(FMLInitializationEvent event) {
		proxy.init();
	}

	@Override
	public String MOD_NAME() {
		return "HardcoreMapReset";
	}

	@Override
	public String MOD_ID() {
		return "hardcoremapreset";
	}

	@Override
	public String MOD_VERSION() {
		return "@MODVERSION@";
	}

	@Override
	public String MOD_DEPENDENCIES() {
		return "required-after:reborncore";
	}
}
