package modmuss50.HardCoreMapRest;

import net.minecraft.world.storage.SaveFormatComparator;

import java.awt.image.BufferedImage;

public class TemplateSaveFormat extends SaveFormatComparator {

    private String author;
    private BufferedImage thumbnail;
    private int textureID;

    public TemplateSaveFormat(SaveFormatComparator other, String author, BufferedImage thumbnail) {
        super(other.getFileName(), other.getDisplayName(), other.getLastTimePlayed(), other.func_154336_c(),
              other.getEnumGameType(), other.requiresConversion(), other.isHardcoreModeEnabled(),
              other.getCheatsEnabled());
        this.author = author;
        this.thumbnail = thumbnail;
        this.textureID = -1;
    }

    public String getAuthor() {
        return author;
    }

    public BufferedImage getThumbnail() { return thumbnail; }

    public void setTexture(int textureID) {
        this.textureID = textureID;
    }

    public int getTexture() {
        return textureID;
    }
}
