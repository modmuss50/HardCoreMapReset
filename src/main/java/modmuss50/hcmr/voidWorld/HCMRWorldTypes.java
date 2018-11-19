package modmuss50.hcmr.voidWorld;

import net.minecraft.client.Minecraft;
import net.minecraft.world.DimensionType;
import net.minecraft.world.GameType;
import net.minecraft.world.WorldSettings;
import net.minecraftforge.common.DimensionManager;

import javax.annotation.Nullable;
import java.io.File;
import java.util.Random;

public class HCMRWorldTypes {

	public static VoidWorldType VOID_TYPE;

	@Nullable
	public static File structure;

	//TODO config all of this
	public static void init(){
		VOID_TYPE = new VoidWorldType();
		//VOID_TYPE.canBeCreated() //TODO AT this

		//TODO make the void world default

		//Replaces the world provider with one that uses an exact spawn location
		DimensionManager.unregisterDimension(0);
		DimensionManager.registerDimension(0, DimensionType.register("Overworld", "", 0, WorldProviderVoid.class, true));
	}

	public static void createNewVoidWorld(String folderName, String worldName, File structure_input){
		Minecraft minecraft = Minecraft.getMinecraft();
		minecraft.displayGuiScreen(null); //Close the gui
		long seed = (new Random()).nextLong();
		VOID_TYPE.onGUICreateWorldPress();

		structure = structure_input; //Sets the structure file so the world generator can be used to palce this at a later date.

		//New world setup for a void world
		WorldSettings worldsettings = new WorldSettings(seed, GameType.SURVIVAL, false, false, VOID_TYPE);
		minecraft.launchIntegratedServer(folderName, worldName, worldsettings);
	}

}
