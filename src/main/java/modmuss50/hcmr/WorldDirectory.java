package modmuss50.hcmr;

import com.google.common.base.Charsets;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.io.FileUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Optional;

public class WorldDirectory extends WorldInfo {

	public static Gson GSON = new GsonBuilder().setPrettyPrinting().create();

	public static WorldDirectory loadDir(File inputDir) throws IOException {
		WorldInfo.AuthorData authorData = null;
		File authorFile = new File(inputDir, "info.json");
		if (!authorFile.exists()) {
			authorData = new WorldInfo.AuthorData();
			FileUtils.writeStringToFile(authorFile, GSON.toJson(authorData), Charsets.UTF_8);
		} else {
			authorData = GSON.fromJson(FileUtils.readFileToString(authorFile, Charsets.UTF_8), WorldInfo.AuthorData.class);
		}
		WorldDirectory worldDirectory = new WorldDirectory();
		//TODO get better worldName
		worldDirectory.name = inputDir.getName();
		worldDirectory.author = authorData;
		worldDirectory.saveFile = inputDir;

		return worldDirectory;
	}

	private String name;
	private AuthorData author;
	private File saveFile;

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
		if(getAuthorData() == null || getAuthorData().thumbnail == null){
			return null;
		}
		File iconFile = new File(getSaveFile(), getAuthorData().thumbnail);
		if (!iconFile.exists()) {
			return null;
		}
		try {
			return ImageIO.read(iconFile);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public void copy(GuiMapList mapList) {
		ResetMaps.copyDirWorld(this, mapList.folderString, mapList);
	}

	@Override
	public Optional<String> valid() {
		if(new File(getSaveFile(), "level.dat").exists()){
			return Optional.empty();
		}
		return Optional.of("level.dat not found!");
	}
}
