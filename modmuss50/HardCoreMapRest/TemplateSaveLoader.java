package modmuss50.HardCoreMapRest;

import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import net.minecraft.src.AnvilSaveConverter;
import net.minecraft.src.ModLoader;
import net.minecraft.src.SaveFormatComparator;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class TemplateSaveLoader extends AnvilSaveConverter
{
    public TemplateSaveLoader(File save_folder)
    {
        super(save_folder);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List getSaveList() {

        List saves = super.getSaveList();
        ArrayList template_saves = new ArrayList();
        for (Object save_obj : saves)
        {
            SaveFormatComparator save = (SaveFormatComparator)save_obj;
            String author = "Unknown";
            try
            {
                File mapsFolder = new File(ModLoader.getMinecraftInstance().mcDataDir, "maps");
                File mapFolder = new File(mapsFolder, save.getFileName());
                File authorFile = new File(mapFolder, "info.json");
				if(!authorFile.exists()){
					BufferedWriter writer = new BufferedWriter(new FileWriter(authorFile));
					writer.write("{");
					writer.newLine();
					writer.write("  \"author\" : \"" + "Player" + "\",");
					writer.newLine();
					writer.write("  \"thumbnail\" : \"thumbnail.png\"");
					writer.newLine();
					writer.write("}");
					writer.close();
				}
                JsonReader reader = new JsonReader(new FileReader(authorFile));

                String key = "";
                while (reader.hasNext()) {
                    JsonToken type = reader.peek();
                    if (type == JsonToken.BEGIN_OBJECT) {
                        reader.beginObject();
                    }
                    else if (type == JsonToken.END_OBJECT) {
                        reader.endObject();
                    }
                    else if (type == JsonToken.NAME) {
                        key = reader.nextName();
                    }
                    else if (type == JsonToken.STRING) {
                        if (key.compareTo("author") == 0) {
                            author = reader.nextString();
                        }
                    }
                }

                reader.close();
            }
            catch (FileNotFoundException e)
            {
                // TODO print warning
            }
            catch (IOException e)
            {
                // TODO print warning
            }
            template_saves.add(new TemplateSaveFormat(save, author));
        }

        return template_saves;
    }
}
