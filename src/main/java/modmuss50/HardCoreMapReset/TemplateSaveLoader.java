package modmuss50.HardCoreMapReset;

import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import net.minecraft.client.AnvilConverterException;
import net.minecraft.client.Minecraft;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.world.chunk.storage.AnvilSaveConverter;
import net.minecraft.world.storage.WorldSummary;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class TemplateSaveLoader extends AnvilSaveConverter {

	public HashMap<WorldSummary, String> authorList = new HashMap<WorldSummary, String>();


	public TemplateSaveLoader(File save_folder, DataFixer fixer) {
		super(save_folder, fixer);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<WorldSummary> getSaveList() throws AnvilConverterException {
		authorList.clear();
		List saves = super.getSaveList();
		for (Object save_obj : saves) {
			WorldSummary save = (WorldSummary) save_obj;
			String author = "Unknown";
			try {
				File mapsFolder = new File(Minecraft.getMinecraft().mcDataDir, "maps");
				File mapFolder = new File(mapsFolder, save.getFileName());
				File authorFile = new File(mapFolder, "info.json");
				if (!authorFile.exists()) {
					BufferedWriter writer = new BufferedWriter(new FileWriter(authorFile));
					writer.write("{");
					writer.newLine();
					writer.write("  \"author\" : \"" + Minecraft.getMinecraft().getSession().getUsername() + "\",");
					writer.write("}");
					writer.close();
				}
				JsonReader reader = new JsonReader(new FileReader(authorFile));

				String key = "";
				while (reader.hasNext()) {
					JsonToken type = reader.peek();
					if (type == JsonToken.BEGIN_OBJECT) {
						reader.beginObject();
					} else if (type == JsonToken.END_OBJECT) {
						reader.endObject();
					} else if (type == JsonToken.NAME) {
						key = reader.nextName();
					} else if (type == JsonToken.STRING) {
						if (key.compareTo("author") == 0) {
							author = reader.nextString();
						}
					}
				}

				reader.close();
			} catch (FileNotFoundException e) {
				// TODO print warning
			} catch (IOException e) {
				// TODO print warning
			}
			authorList.put(save, author);
		}

		return saves;
	}
}