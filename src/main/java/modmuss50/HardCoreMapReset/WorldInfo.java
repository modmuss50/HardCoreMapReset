package modmuss50.HardCoreMapReset;

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
		return null;
	}

	public static class AuthorData {
		String author = "No Author";
		String thumbnail = "icon.png";
		String description = "No Description set";
	}
}
