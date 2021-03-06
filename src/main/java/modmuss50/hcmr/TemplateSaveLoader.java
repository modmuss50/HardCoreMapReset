package modmuss50.hcmr;

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
		if(!save_folder.exists()){
			save_folder.mkdir();
		}
	}

	@SuppressWarnings("unchecked")
	public List<WorldInfo> getSaveList() {
		imageList.values().forEach(resourceLocation -> Minecraft.getMinecraft().getTextureManager().deleteTexture(resourceLocation));
		imageList.clear();

		List<WorldInfo> worldInfoList = new ArrayList<>();
		if(saveFolder.listFiles() == null){
			return worldInfoList;
		}
		for (File file : saveFolder.listFiles()) {
			WorldInfo worldInfo = WorldInfo.load(file);
			if(worldInfo == null){
				continue;
			}
			BufferedImage bufferedImage = worldInfo.getIconImage();
			if (bufferedImage != null) {
				DynamicTexture texture = new DynamicTexture(bufferedImage.getWidth(), bufferedImage.getHeight());
				bufferedImage.getRGB(0, 0, bufferedImage.getWidth(), bufferedImage.getHeight(), texture.getTextureData(), 0, bufferedImage.getWidth());
				texture.updateDynamicTexture();
				ResourceLocation resourceLocation = new ResourceLocation("hcmr", "icon/" + worldInfo.toString() + "/icon");
				Minecraft.getMinecraft().getTextureManager().loadTexture(resourceLocation, texture);
				imageList.put(worldInfo, resourceLocation);
			}
			worldInfoList.add(worldInfo);
		}
		return worldInfoList;
	}

}