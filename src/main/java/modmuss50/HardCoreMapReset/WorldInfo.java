package modmuss50.HardCoreMapReset;

import reborncore.RebornCore;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public abstract class WorldInfo {

	public abstract String getName();

	public abstract AuthorData getAuthorData();

	public abstract File getSaveFile();

	public abstract BufferedImage getIconImage();

	public abstract void copy(GuiMapList mapList);

	public static WorldInfo load(File inputFile) {
		if (inputFile.isDirectory()) {
			try {
				return WorldDirectory.loadDir(inputFile);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if(inputFile.getName().endsWith(".zip")){
			return WorldZip.loadZip(inputFile);
		}
		RebornCore.logHelper.error(inputFile.getName() + " is not supported!");
		return null;
	}

	public static class AuthorData {
		public String author = "No Author";
		public String thumbnail = "icon.png";
		public String description = "No Description set";
	}
}
