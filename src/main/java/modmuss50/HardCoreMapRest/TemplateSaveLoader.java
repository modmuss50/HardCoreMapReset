package modmuss50.HardCoreMapRest;

import net.minecraft.client.AnvilConverterException;
import net.minecraft.client.Minecraft;
import net.minecraft.world.chunk.storage.AnvilSaveConverter;
import net.minecraft.world.storage.SaveFormatComparator;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileNotFoundException;
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
            String author;
            try {
                File mapsFolder = new File(Minecraft.getMinecraft().mcDataDir, "maps");
                File mapFolder = new File(mapsFolder, save.getFileName());
                File authorFile = new File(mapFolder, "author.txt");
                author = FileUtils.readFileToString(authorFile);
            } catch (FileNotFoundException e) {
                author = "Unknown";
            } catch (IOException e) {
                author = "Unknown";
            }
            template_saves.add(new TemplateSaveFormat(save, author));
        }

        return template_saves;
    }
}