package modmuss50.HardCoreMapReset;

import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class LevelUtils {

	public static NBTTagCompound readLevel(InputStream stream) throws IOException {
		NBTTagCompound nbt = CompressedStreamTools.readCompressed(stream);
		return nbt.getCompoundTag("Data");
	}

	public static void writeLevel(NBTTagCompound tagCompound, OutputStream stream) throws IOException {
		CompressedStreamTools.writeCompressed(tagCompound, stream);
	}
}
