package modmuss50.HardCoreMapRest;

import com.google.gson.stream.JsonReader;
import net.minecraft.client.AnvilConverterException;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.chunk.storage.AnvilSaveConverter;
import net.minecraft.world.storage.SaveFormatComparator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
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
            ResourceLocation thumbnail = null;
            try
            {
                File mapsFolder = new File(Minecraft.getMinecraft().mcDataDir, "maps");
                File mapFolder = new File(mapsFolder, save.getFileName());
                File authorFile = new File(mapFolder, "author.txt");
                JsonReader reader = new JsonReader(new FileReader(authorFile));

                reader.beginObject();
                reader.skipValue();
                author = reader.nextString();
                reader.skipValue();
                // TODO prepend save folder
                thumbnail = new ResourceLocation(reader.nextString());
                reader.endObject();

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