package modmuss50.HardCoreMapRest;

import net.minecraft.world.storage.SaveFormatComparator;

public class TemplateSaveFormat extends SaveFormatComparator {

    private String author;

    public TemplateSaveFormat(SaveFormatComparator other, String author) {
        super(other.getFileName(), other.getDisplayName(), other.getLastTimePlayed(), other.func_154336_c(),
              other.getEnumGameType(), other.requiresConversion(), other.isHardcoreModeEnabled(),
              other.getCheatsEnabled());
        this.author = author;
    }

    public String getAuthor() {
        return author;
    }
}
