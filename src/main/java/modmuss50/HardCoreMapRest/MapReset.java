package modmuss50.HardCoreMapRest;


import me.modmuss50.mods.core.client.BaseModGui;
import me.modmuss50.mods.core.mod.ModRegistry;
import me.modmuss50.mods.lib.mod.IMod;
import modmuss50.HardCoreMapRest.server.CommandHCMR;
import modmuss50.HardCoreMapRest.server.PacketOpen;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;


import java.io.File;


@Mod(modid = "hardcoremapreset", name = "HardcoreMapReset", version = "2.0.0")
public class MapReset implements IMod {


    public static boolean keepOldMaps = true;

    @Mod.EventHandler
    public void load(FMLInitializationEvent event) {
        ModRegistry.registerMod(this);
        File backupDir = new File(Minecraft.getMinecraft().mcDataDir, "maps");
        if(!backupDir.exists())
            backupDir.mkdir();

        if (FMLCommonHandler.instance().getSide().isClient()){
            MinecraftForge.EVENT_BUS.register(new GuiTweaker());
        }
    }


    @Override
    public BaseModGui settingsScreen() {
        return null;
    }

    public String modId() {
        return "hardcoremapreset";
    }

    public String modName() {
        return "HardcoreMapReset";
    }

    public String modVersion() {
        return "2.0.0";
    }

    public String recomenedMinecraftVeriosion() {
        return Loader.MC_VERSION;
    }

    public void enable() {
        System.out.println("Starting HCMR");
    }

    public void disable() {

    }


	public static final SimpleNetworkWrapper networkWrapper = NetworkRegistry.INSTANCE.newSimpleChannel("hardcoremapreset");

	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		networkWrapper.registerMessage(PacketOpen.class, PacketOpen.class, 0, Side.CLIENT);
	}



	@Mod.EventHandler
	public void serverInit(FMLServerStartingEvent event) {
		event.registerServerCommand(new CommandHCMR());
	}



}
