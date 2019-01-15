package modmuss50.hcmr;

import reborncore.RebornCore;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Optional;

public abstract class WorldInfo {

	public abstract String getName();

	public abstract AuthorData getAuthorData();

	public abstract File getSaveFile();

	public abstract BufferedImage getIconImage();

	public abstract void copy(GuiMapList mapList);

	public abstract Optional<String> valid();

	public static WorldInfo load(File inputFile) {
		if (inputFile.isDirectory()) {
			File levelData = new File(inputFile, "level.dat");
			File variations = new File(inputFile, "variations.json");
			File structure = new File(inputFile, WorldStructure.getStructureFileName(inputFile));
			if(variations.exists()){
				try {
					return WorldVariations.loadDir(inputFile);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}else if(levelData.exists()){
				try {
					return WorldDirectory.loadDir(inputFile);
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else if (structure.exists()){
				try {
					return WorldStructure.loadStructure(inputFile);
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {
				RebornCore.logHelper.error(inputFile.getName() + " is not supported!");
				return null;
			}

		}
		if(inputFile.getName().endsWith(".zip")){
			return WorldZip.loadZip(inputFile);
		}
		RebornCore.logHelper.error(inputFile.getName() + " is not a valid template file!");
		return null;
	}

	public static class AuthorData {
		public String author = "No Author";
		public String thumbnail = "icon.png";
		public String description = "";
		public int sort = 0;
	}
}
