package modmuss50.hcmr.voidWorld;

import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.structure.template.PlacementSettings;
import net.minecraft.world.gen.structure.template.Template;
import org.apache.commons.lang3.tuple.Pair;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;

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
		BlockPos pos = template.getDataBlocks(BlockPos.ORIGIN, new PlacementSettings()).entrySet()
			.stream()
			.filter(entry -> entry.getValue().equals("SPAWN_POINT"))
			.map(Map.Entry::getKey).findFirst()
			.orElse(BlockPos.ORIGIN);
		return Pair.of(template, pos);
	}

}
