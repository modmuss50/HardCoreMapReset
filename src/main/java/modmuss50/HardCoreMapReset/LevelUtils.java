package modmuss50.HardCoreMapReset;

import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;

import java.io.*;

public class LevelUtils {

	public static NBTTagCompound readLevel(InputStream stream) throws IOException {
		NBTTagCompound nbt = CompressedStreamTools.readCompressed(stream);
		return nbt;
	}

	public static void writeLevel(NBTTagCompound tagCompound, OutputStream stream) throws IOException {
		CompressedStreamTools.writeCompressed(tagCompound, stream);
	}

	public static void updateLastPlayed(File file) throws IOException {
		FileInputStream fileInputStream = new FileInputStream(file);
		NBTTagCompound tagCompound = readLevel(fileInputStream);
		tagCompound.getCompoundTag("Data").setLong("LastPlayed", MinecraftServer.getCurrentTimeMillis());
		FileOutputStream fileOutputStream = new FileOutputStream(file);
		writeLevel(tagCompound, fileOutputStream);
	}
}
