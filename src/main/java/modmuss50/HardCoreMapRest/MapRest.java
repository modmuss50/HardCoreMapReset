package modmuss50.HardCoreMapRest;


import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

import java.io.File;


@Mod(modid = "hardcoremapreset", name = "HardcoreMapReset", version = "1.0.0")
public class MapRest {


    public static boolean keepOldMaps = true;

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
