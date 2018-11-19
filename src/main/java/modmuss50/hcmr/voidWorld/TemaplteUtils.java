package modmuss50.hcmr.voidWorld;

import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.structure.template.Template;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import org.apache.commons.lang3.tuple.Pair;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;

public class TemaplteUtils {

	public Template read(File file) throws IOException {
		NBTTagCompound tagCompound = CompressedStreamTools.read(file);
		Template template = new Template();
		template.read(tagCompound);
		return template;
	}

	public Pair<Template, BlockPos> getTemplateWithSpawn(Template template){

		return null; //TODO find diamond block and remove it + save the pos of it ;)
	}


	public List<Template.BlockInfo> getBlocks(Template template){
		Field field = ReflectionHelper.findField(template.getClass(), "blocks", "field_186270_a");
		field.setAccessible(true);
		try {
			return (List<Template.BlockInfo>) field.get(template);
		} catch (IllegalAccessException e) {
			throw new RuntimeException("Failed to get field", e);
		}
	}

}
