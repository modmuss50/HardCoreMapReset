package modmuss50.HardCoreMapReset;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.util.ResourceLocation;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TemplateSaveLoader {

	public HashMap<WorldInfo, ResourceLocation> imageList = new HashMap<>();

	public File saveFolder;

	public TemplateSaveLoader(File save_folder) {
		this.saveFolder = save_folder;
	}

	@SuppressWarnings("unchecked")
	public List<WorldInfo> getSaveList() {
		imageList.values().forEach(resourceLocation -> Minecraft.getMinecraft().getTextureManager().deleteTexture(resourceLocation));
		imageList.clear();

		List<WorldInfo> worldInfoList = new ArrayList<>();
		for (File file : saveFolder.listFiles()) {
			WorldInfo worldInfo = WorldInfo.load(file);
			String author = "Unknown";

			BufferedImage bufferedImage = worldInfo.getIconImage();
			if (bufferedImage != null) {
				DynamicTexture texture = new DynamicTexture(bufferedImage.getWidth(), bufferedImage.getHeight());
				bufferedImage.getRGB(0, 0, bufferedImage.getWidth(), bufferedImage.getHeight(), texture.getTextureData(), 0, bufferedImage.getWidth());
				texture.updateDynamicTexture();
				ResourceLocation resourceLocation = new ResourceLocation("hcmr", "icon/" + worldInfo.getSaveFile().getName() + "/icon");
				Minecraft.getMinecraft().getTextureManager().loadTexture(resourceLocation, texture);
				imageList.put(worldInfo, resourceLocation);
			}
			worldInfoList.add(worldInfo);
		}
		return worldInfoList;
	}

}