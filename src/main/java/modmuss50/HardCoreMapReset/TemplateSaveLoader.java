package modmuss50.HardCoreMapReset;

import com.google.common.base.Charsets;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.minecraft.client.AnvilConverterException;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.world.chunk.storage.AnvilSaveConverter;
import net.minecraft.world.storage.WorldSummary;
import org.apache.commons.io.FileUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class TemplateSaveLoader extends AnvilSaveConverter {

	public HashMap<WorldSummary, String> authorList = new HashMap<>();

	public HashMap<WorldSummary, ResourceLocation> imageList = new HashMap<>();

	public static Gson GSON = new GsonBuilder().setPrettyPrinting().create();

	public TemplateSaveLoader(File save_folder, DataFixer fixer) {
		super(save_folder, fixer);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<WorldSummary> getSaveList() throws AnvilConverterException {
		authorList.clear();
		imageList.values().forEach(resourceLocation -> Minecraft.getMinecraft().getTextureManager().deleteTexture(resourceLocation));
		imageList.clear();
		List saves = super.getSaveList();
		for (Object save_obj : saves) {
			WorldSummary save = (WorldSummary) save_obj;
			String author = "Unknown";
			try {
				File mapsFolder = new File(Minecraft.getMinecraft().mcDataDir, "maps");
				File mapFolder = new File(mapsFolder, save.getFileName());
				File authorFile = new File(mapFolder, "info.json");
				String iconName = "icon.png";
				if (!authorFile.exists()) {
					FileUtils.writeStringToFile(authorFile, GSON.toJson(new AuthorData()), Charsets.UTF_8);
				} else {
					AuthorData data = GSON.fromJson(FileUtils.readFileToString(authorFile, Charsets.UTF_8), AuthorData.class);
					author = data.author;
					if(data.thumbnail != null){
						iconName = data.thumbnail;
					}
				}
				File iconFile = new File(mapFolder, iconName);
				if(iconFile.exists()){
					BufferedImage bufferedImage = ImageIO.read(iconFile);
					DynamicTexture texture = new DynamicTexture(bufferedImage.getWidth(), bufferedImage.getHeight());
					bufferedImage.getRGB(0, 0, bufferedImage.getWidth(), bufferedImage.getHeight(), texture.getTextureData(), 0, bufferedImage.getWidth());
					texture.updateDynamicTexture();
					ResourceLocation resourceLocation = new ResourceLocation("hcmr", "icon/" + save.getFileName() + "/" + iconFile.getName());
					Minecraft.getMinecraft().getTextureManager().loadTexture(resourceLocation, texture);
					imageList.put(save, resourceLocation);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			authorList.put(save, author);
		}
		return saves;
	}


	private static class AuthorData {
		String author;
		String thumbnail;
	}
}