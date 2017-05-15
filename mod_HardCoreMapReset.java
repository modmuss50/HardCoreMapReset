import modmuss50.HardCoreMapRest.ResetMaps;
import modmuss50.HardCoreMapRest.TemplateSaveLoader;
import net.minecraft.src.BaseMod;
import net.minecraft.src.ModLoader;

import java.io.File;

public class mod_HardCoreMapReset extends BaseMod {

	@Override
	public String getVersion() {
		return "1";
	}

	@Override
	public void load() {
		ResetMaps.saveLoader = new TemplateSaveLoader(new File(ModLoader.getMinecraftInstance().mcDataDir, "maps"));
	}

}
