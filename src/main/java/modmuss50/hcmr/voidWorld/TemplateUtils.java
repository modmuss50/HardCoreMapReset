package modmuss50.hcmr.voidWorld;

import modmuss50.hcmr.HardCoreMapReset;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.structure.template.Template;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import org.apache.commons.lang3.tuple.Pair;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;

public class TemplateUtils {

	public static Template read(File file) {
		NBTTagCompound tagCompound;
		try {
			try(FileInputStream input = new FileInputStream(file)) {
				tagCompound = CompressedStreamTools.readCompressed(input);
			}
		} catch (IOException e) {
			throw new RuntimeException("Failed to read " + file.getAbsolutePath(), e);
		}

		Template template = new Template();
		template.read(tagCompound);
		return template;
	}

	public static Pair<Template, BlockPos> getTemplateWithSpawn(Template template){
		List<Template.BlockInfo> blocks = getBlocks(template);

		BlockPos pos = BlockPos.ORIGIN;
		for(Template.BlockInfo blockInfo : blocks){
			if(blockInfo.blockState.getBlock() == Blocks.DIAMOND_BLOCK){
				pos = blockInfo.pos;
			}
		}

		//Removes all the diamond blocks
		blocks.removeIf(blockInfo -> blockInfo.blockState.getBlock() == Blocks.DIAMOND_BLOCK);

		return Pair.of(template, pos);
	}

	public static boolean isMarker(Template.BlockInfo blockInfo){
		return blockInfo.blockState.getBlock().getRegistryName().equals(new ResourceLocation(HardCoreMapReset.markerBlcok));
	}


	public static List<Template.BlockInfo> getBlocks(Template template){
		Field field = ReflectionHelper.findField(template.getClass(), "blocks", "field_186270_a");
		field.setAccessible(true);
		try {
			return (List<Template.BlockInfo>) field.get(template);
		} catch (IllegalAccessException e) {
			throw new RuntimeException("Failed to get field", e);
		}
	}

}
