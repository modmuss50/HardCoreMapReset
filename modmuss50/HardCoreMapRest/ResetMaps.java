package modmuss50.HardCoreMapRest;


import net.minecraft.client.Minecraft;
import net.minecraft.src.ModLoader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class ResetMaps {
	public static TemplateSaveLoader saveLoader;

	public static void copyDirectory(File sourceLocation, File targetLocation) throws IOException {
		if (sourceLocation.isDirectory()) {
			if (!targetLocation.exists()) {
				targetLocation.mkdir();
			}

			String[] children = sourceLocation.list();
			for (int i = 0; i < children.length; i++) {
				copyDirectory(new File(sourceLocation, children[i]),
						new File(targetLocation, children[i]));
			}
		} else {

			InputStream in = new FileInputStream(sourceLocation);
			OutputStream out = new FileOutputStream(targetLocation);

			// Copy the bits from instream to outstream
			byte[] buf = new byte[1024];
			int len;
			while ((len = in.read(buf)) > 0) {
				out.write(buf, 0, len);
			}
			in.close();
			out.close();
		}
	}

	public static void deleteFolder(File folder) {
		File[] files = folder.listFiles();
		if (files != null) { //some JVMs return null for empty dirs
			for (File f : files) {
				if (f.isDirectory()) {
					deleteFolder(f);
				} else {
					f.delete();
				}
			}
		}
		folder.delete();
	}

	public static void resetmap(String name) {
		Minecraft mc = ModLoader.getMinecraftInstance();
		File saveDir = new File(mc.mcDataDir, "saves");
		File backupDir = new File(mc.mcDataDir, "maps");
		File oldDir = new File(saveDir, name);
		File newDir = new File(backupDir, name);
		if (oldDir.exists())
			deleteFolder(oldDir);
		oldDir.mkdir();
		try {
			copyDirectory(newDir, oldDir);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void copymap(String from, String to)
	{
		Minecraft mc = ModLoader.getMinecraftInstance();
		File saveDir = new File(mc.mcDataDir, "saves");
		File backupDir = new File(mc.mcDataDir, "maps");
		File oldDir = new File(saveDir, to);
		File newDir = new File(backupDir, from);
		if (oldDir.exists())
		{
			// TODO
			System.err.println("TODO");
			return;
		}
		oldDir.mkdir();
		try
		{
			copyDirectory(newDir, oldDir);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}
