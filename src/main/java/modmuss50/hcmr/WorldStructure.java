package modmuss50.hcmr;

import com.google.common.base.Charsets;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import modmuss50.hcmr.voidWorld.HCMRWorldTypes;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiCreateWorld;
import org.apache.commons.io.FileUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Optional;

public class WorldStructure extends WorldInfo {

	public static Gson GSON = new GsonBuilder().setPrettyPrinting().create();
	private String name;
	private AuthorData author;
	private File structureFile;


	public static WorldStructure loadStructure(File inputDir) throws IOException {
		WorldInfo.AuthorData authorData = null;
		File authorFile = new File(inputDir, "info.json");
		if (!authorFile.exists()) {
			authorData = new WorldInfo.AuthorData();
			FileUtils.writeStringToFile(authorFile, GSON.toJson(authorData), Charsets.UTF_8);
		} else {
			authorData = GSON.fromJson(FileUtils.readFileToString(authorFile, Charsets.UTF_8), WorldInfo.AuthorData.class);
		}
		WorldStructure worldDirectory = new WorldStructure();
		//TODO get better worldName
		worldDirectory.name = inputDir.getName();
		worldDirectory.author = authorData;
		worldDirectory.structureFile = inputDir;

		return worldDirectory;
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
		return structureFile;
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
		String name = mapList.nameField.getText();
		String folderName = GuiCreateWorld.getUncollidingSaveDirName(Minecraft.getMinecraft().getSaveLoader(), name);
		HCMRWorldTypes.createNewVoidWorld(folderName, name, structureFile);
	}

	@Override
	public Optional<String> valid() {
		if(HardCoreMapReset.voidWorldGeneration){
			return Optional.empty();
		}
		return Optional.of("Enable void world generation in config!");
	}
}
