package modmuss50.HardCoreMapReset;

import com.google.common.base.Charsets;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.minecraft.nbt.NBTTagCompound;
import org.apache.commons.io.IOUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class WorldZip extends WorldInfo {

	private static Gson GSON = new GsonBuilder().setPrettyPrinting().create();

	public static WorldZip loadZip(File input) {
		try {
			ZipFile zipFile = new ZipFile(input);

			AuthorData authorData = new AuthorData();
			ZipEntry aDataEntry = zipFile.getEntry("info.json");
			if(aDataEntry != null){
				InputStream aDataIS = zipFile.getInputStream(aDataEntry);
				String aDataJson = IOUtils.toString(aDataIS, Charsets.UTF_8);
				aDataIS.close();
				authorData = GSON.fromJson(aDataJson, WorldInfo.AuthorData.class);
			}

			InputStream levelIS = zipFile.getInputStream(zipFile.getEntry("level.dat"));
			NBTTagCompound levelDat = LevelUtils.readLevel(levelIS).getCompoundTag("Data");
			levelIS.close();

			String worldName = levelDat.getString("LevelName");
			return new WorldZip(worldName, authorData, input, zipFile);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}


	private String name;
	private AuthorData author;
	private File saveFile;
	private ZipFile zipFile;

	public WorldZip(String name, AuthorData author, File saveFile, ZipFile zipFile) {
		this.name = name;
		this.author = author;
		this.saveFile = saveFile;
		this.zipFile = zipFile;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public AuthorData getAuthorData() {
		return author;
	}

	@Override
	public File getSaveFile() {
		return saveFile;
	}

	@Override
	public BufferedImage getIconImage() {
		try {
			InputStream iconIS = zipFile.getInputStream(zipFile.getEntry(getAuthorData().thumbnail));
			BufferedImage bufferedImage = ImageIO.read(iconIS);
			iconIS.close();
			return bufferedImage;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public void copy(GuiMapList mapList) {
		ResetMaps.copyZipWorld(this, mapList.folderString, mapList);
	}

	@Override
	public boolean valid() {
		return true; //TODO check the contents of the file?
	}

	public ZipFile getZipFile() {
		return zipFile;
	}
}
