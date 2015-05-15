package modmuss50.HardCoreMapRest;

import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.SaveFormatComparator;

public class TemplateSaveFormat extends SaveFormatComparator {

    private String author;
    private ResourceLocation thumbnail;

    public TemplateSaveFormat(SaveFormatComparator other, String author, ResourceLocation thumbnail) {
        super(other.getFileName(), other.getDisplayName(), other.getLastTimePlayed(), other.func_154336_c(),
              other.getEnumGameType(), other.requiresConversion(), other.isHardcoreModeEnabled(),
              other.getCheatsEnabled());
        this.author = author;
        this.thumbnail = thumbnail;
    }

    public String getAuthor() {
        return author;
    }

    public ResourceLocation getThumbnail() { return thumbnail; }
}
