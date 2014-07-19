package modmuss50.HardCoreMapRest;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;

import java.io.File;


@Mod(modid = "hardcoremapreset", name = "HardcoreMapReset", version = "1.0.0")
public class MapRest {


    @Mod.EventHandler
    public void load(FMLInitializationEvent event) {
        File backupDir = new File(Minecraft.getMinecraft().mcDataDir, "maps");
        if(!backupDir.exists())
            backupDir.mkdir();

        if (FMLCommonHandler.instance().getSide().isClient()){
            MinecraftForge.EVENT_BUS.register(new GuiTweaker());
        }
    }


}
