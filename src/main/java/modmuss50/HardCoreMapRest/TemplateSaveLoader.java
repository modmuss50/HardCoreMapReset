package modmuss50.HardCoreMapRest;

import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import net.minecraft.client.AnvilConverterException;
import net.minecraft.client.Minecraft;
import net.minecraft.world.chunk.storage.AnvilSaveConverter;
import net.minecraft.world.storage.SaveFormatComparator;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
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
    public List getSaveList() throws AnvilConverterException
    {
        List saves = super.getSaveList();
        ArrayList template_saves = new ArrayList();
        for (Object save_obj : saves)
        {
            SaveFormatComparator save = (SaveFormatComparator)save_obj;
            String author = "Unknown";
            BufferedImage thumbnail = null;
            try
            {
                File mapsFolder = new File(Minecraft.getMinecraft().mcDataDir, "maps");
                File mapFolder = new File(mapsFolder, save.getFileName());
                File authorFile = new File(mapFolder, "info.json");
				if(!authorFile.exists()){
					BufferedWriter writer = new BufferedWriter(new FileWriter(authorFile));
					writer.write("{");
					writer.newLine();
					writer.write("  \"author\" : \"" + Minecraft.getMinecraft().getSession().getUsername() + "\",");
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
                        } else if (key.compareTo("thumbnail") == 0) {
                            File thumbnailFile = null;
                            thumbnailFile = new File(mapFolder, reader.nextString());
                            thumbnail = ImageIO.read(thumbnailFile);
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
            template_saves.add(new TemplateSaveFormat(save, author, thumbnail));
        }

        return template_saves;
    }
}