package modmuss50.hcmr;

import com.google.common.base.Charsets;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.minecraft.client.Minecraft;
import org.apache.commons.io.FileUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Optional;

public class WorldVariations extends WorldInfo {

	public static Gson GSON = new GsonBuilder().setPrettyPrinting().create();

	WorldInfo.AuthorData info;
	File directory;

	public static WorldVariations loadDir(File dir) throws IOException {
		File variations = new File(dir, "variations.json");
		if(!variations.exists()){
			throw new RuntimeException("variations.json not found");
		}

		WorldVariations worldVariations = new WorldVariations();
		worldVariations.directory = dir;

		if (!variations.exists()) {
			worldVariations.info = new WorldInfo.AuthorData();
			FileUtils.writeStringToFile(variations, GSON.toJson(worldVariations.info), Charsets.UTF_8);
		} else {
			worldVariations.info = GSON.fromJson(FileUtils.readFileToString(variations, Charsets.UTF_8), WorldInfo.AuthorData.class);
		}

		return worldVariations;
	}

	@Override
	public String getName() {
		return directory.getName();
	}

	@Override
	public AuthorData getAuthorData() {
		return info;
	}

	@Override
	public File getSaveFile() {
		return directory;
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
		Minecraft.getMinecraft().displayGuiScreen(new GuiMapList(mapList, directory));
	}

	@Override
	public Optional<String> valid() {
		return Optional.empty();
	}
}
