package modmuss50.HardCoreMapRest;

import net.minecraft.src.SaveFormatComparator;


public class TemplateSaveFormat extends SaveFormatComparator {

    private String author;

    public TemplateSaveFormat(SaveFormatComparator other, String author) {
        super(other.getFileName(), other.getDisplayName(), other.getLastTimePlayed(), 0,
              other.getGameType(), other.requiresConversion(), other.isHardcoreModeEnabled());
        this.author = author;
    }

    public String getAuthor() {
        return author;
    }


}
