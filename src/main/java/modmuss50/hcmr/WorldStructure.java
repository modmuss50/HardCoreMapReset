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
	private File dir;


	public static WorldStructure loadStructure(File inputDir) throws IOException {
		StrctureInfo strctureInfo;
		File authorFile = new File(inputDir, "info.json");
		if (!authorFile.exists()) {
			strctureInfo = new StrctureInfo();
			FileUtils.writeStringToFile(authorFile, GSON.toJson(strctureInfo), Charsets.UTF_8);
		} else {
			strctureInfo = GSON.fromJson(FileUtils.readFileToString(authorFile, Charsets.UTF_8), StrctureInfo.class);
		}
		File structureFile = new File(inputDir, strctureInfo.structureFile);
		WorldStructure structure = new WorldStructure();
		structure.name = inputDir.getName();
		structure.author = strctureInfo;
		structure.structureFile = structureFile;
		structure.dir = inputDir;

		return structure;
	}

	public static String getStructureFileName(File dir){
		File authorFile = new File(dir, "info.json");
		if(!authorFile.exists()){
			return "structure.nbt";
		}
		try {
			return GSON.fromJson(FileUtils.readFileToString(authorFile, Charsets.UTF_8), StrctureInfo.class).structureFile;
		} catch (IOException e) {
			e.printStackTrace();
			return "structure.nbt";
		}
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
		File iconFile = new File(dir, getAuthorData().thumbnail);
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

	public static class StrctureInfo extends WorldInfo.AuthorData {
		public String structureFile = "structure.nbt";
	}
}
