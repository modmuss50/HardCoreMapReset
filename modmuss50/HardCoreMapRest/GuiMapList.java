package modmuss50.HardCoreMapRest;

import net.minecraft.src.*;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class GuiMapList extends GuiScreen {
	private static final int CREATE_BUTTON_ID = 0;
	private static final int CANCEL_BUTTON_ID = 1;
	public GuiScreen parent;
	private List saveList;
	private GuiButton createButton;
	private GuiTextField nameField;
	private MapList mapList;
	private SimpleDateFormat dateFormat = new SimpleDateFormat();
	private int selectedSlot;
	private String folderString;
	private static final String[] portNames = new String[] {"CON", "COM", "PRN", "AUX", "CLOCK$", "NUL", "COM1", "COM2", "COM3", "COM4", "COM5", "COM6", "COM7", "COM8", "COM9", "LPT1", "LPT2", "LPT3", "LPT4", "LPT5", "LPT6", "LPT7", "LPT8", "LPT9"};

	public GuiMapList(GuiScreen parent) {
		this.parent = parent;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void initGui() {
		super.initGui();
		initSaveList();
		StringTranslate translate = StringTranslate.getInstance();
		this.nameField = new GuiTextField(this.fontRenderer, this.width / 2 - 100, 45, 200, 20);
		this.nameField.setFocused(true);
		this.nameField.setText(translate.translateKey("selectWorld.newWorld"));
		sanitizeFolderName();

		this.mapList = new MapList();

		this.controlList.add(this.createButton = new GuiButton(CREATE_BUTTON_ID, this.width / 2 - 155, this.height - 28, 150, 20, translate.translateKey("selectWorld.create")));
		this.controlList.add(new GuiButton(CANCEL_BUTTON_ID, this.width / 2 + 5, this.height - 28, 150, 20, translate.translateKey("gui.cancel")));
		this.createButton.enabled = false;
	}


	private void initSaveList() {
		this.saveList = ResetMaps.saveLoader.getSaveList();
		Collections.sort(this.saveList);
		this.selectedSlot = -1;
	}

	@Override
	public void actionPerformed(GuiButton guiButton) {
		if (guiButton.id == CANCEL_BUTTON_ID)
		{
			ModLoader.getMinecraftInstance().displayGuiScreen(parent);
		}
		else if (guiButton.id == CREATE_BUTTON_ID)
		{
			createMap();
			ModLoader.getMinecraftInstance().displayGuiScreen(new GuiSelectWorld(new GuiMainMenu()));
            //FMLClientHandler.instance().tryLoadExistingWorld(new GuiSelectWorld(this), this.folderString, this.nameField.getText().trim());
		}
		else
		{
			mapList.actionPerformed(guiButton);
		}
	}

	@Override
	protected void keyTyped(char keyChar, int keyCode)
	{
		if (keyCode == 28 || keyCode == 156)
		{
			this.actionPerformed((GuiButton)this.controlList.get(0));
		}

		if (this.nameField.getIsFocused())
		{
			this.nameField.textboxKeyTyped(keyChar, keyCode);
		}

		this.sanitizeFolderName();
	}

	// TODO Cleanup
	public void sanitizeFolderName()
	{
		ISaveFormat saveLoader = this.mc.getSaveLoader();
		this.folderString = this.nameField.getText().trim();
		this.folderString = this.folderString.replaceAll("[\\./\"]", "_");
		char[] achar = ChatAllowedCharacters.allowedCharactersArray;
		int i = achar.length;

		for (int j = 0; j < i; ++j)
		{
			char c0 = achar[j];
			this.folderString = this.folderString.replace(c0, '_');
		}

		String[] astring = GuiMapList.portNames;
		i = astring.length;

		for (int j = 0; j < i; ++j)
		{
			String s0 = astring[j];
			if (this.folderString.equalsIgnoreCase(s0))
			{
				this.folderString = "_" + this.folderString + "_";
			}
		}

		while (saveLoader.getWorldInfo(this.folderString) != null)
		{
			this.folderString = this.folderString + "-";
		}
	}

	@Override
	public void drawScreen(int x, int y, float f) {
		mapList.drawScreen(x, y, f);
		nameField.drawTextBox();
		StringTranslate translate = StringTranslate.getInstance();
		this.drawCenteredString(this.fontRenderer, translate.translateKey("gui.hardcoremapreset.create_title"), this.width / 2, 20, -1);
		this.drawString(this.fontRenderer, translate.translateKey("selectWorld.enterName"), this.width / 2 - 100, 35, -6250336);
		this.drawString(this.fontRenderer, translate.translateKey("selectWorld.resultFolder") + " " + this.folderString, this.width / 2 - 100, 68, -6250336);
		super.drawScreen(x, y, f);
	}

	private class MapList extends GuiSlot {
		public MapList() {
			super(GuiMapList.this.mc, GuiMapList.this.width, GuiMapList.this.height, 78, GuiMapList.this.height - 32, 36);
		}

		@Override
		protected int getSize() {
			return GuiMapList.this.saveList.size();
		}

		@Override
		protected void elementClicked(int slot, boolean doubleClicked) {
			GuiMapList.this.selectedSlot = slot;
			GuiMapList.this.createButton.enabled = true;

			if (doubleClicked)
			{
				GuiMapList.this.createMap();
			}
		}

		@Override
		protected boolean isSelected(int slot) {
			return (slot == GuiMapList.this.selectedSlot);
		}

		@Override
		protected void drawBackground() {
			GuiMapList.this.drawDefaultBackground();
		}

//		@Override
//		public int getListWidth() {
//			return GuiMapList.this.width - 40;
//		}
//
//		@Override
//		protected int getScrollBarX() {
//			return (this.width / 2) + (this.getListWidth() / 2) - 6;
//		}

		@Override
		protected void drawSlot(int slot, int x, int y, int slotHeight, Tessellator tessellator) {
			TemplateSaveFormat saveFormat = (TemplateSaveFormat)GuiMapList.this.saveList.get(slot);
			StringTranslate translate = StringTranslate.getInstance();
			String displayName = saveFormat.getDisplayName();
			String author = saveFormat.getAuthor();
			String by = translate.translateKey("gui.hardcoremapreset.by");
			String topLine = displayName + ", " + by + ": " + author;

			String folder = saveFormat.getFileName();
			String date = GuiMapList.this.dateFormat.format(new Date(saveFormat.getLastTimePlayed()));
			String middleLine = folder + " (" + date + ")";

			String mode = "";
			String cheats = "";
			String bottomLine = mode + ", " + cheats;

			GuiMapList.this.drawString(GuiMapList.this.fontRenderer, topLine,    x + 34, y + 1,       16777215);
			GuiMapList.this.drawString(GuiMapList.this.fontRenderer, middleLine, x + 34, y + 12,      8421504);
			GuiMapList.this.drawString(GuiMapList.this.fontRenderer, bottomLine, x + 34, y + 12 + 10, 8421504);


		}
	}

	private void createMap() {
		SaveFormatComparator saveformatcomparator = (SaveFormatComparator)GuiMapList.this.saveList.get(this.selectedSlot);
		ResetMaps.copymap(saveformatcomparator.getFileName(), this.folderString);
		ModLoader.getMinecraftInstance().getSaveLoader().renameWorld(this.folderString, this.nameField.getText().trim());
	}

	public static int loadTexture(BufferedImage image) {
		int[] pixels = new int[image.getWidth() * image.getHeight()];
		image.getRGB(0, 0, image.getWidth(), image.getHeight(), pixels, 0, image.getWidth());

		ByteBuffer buffer = BufferUtils.createByteBuffer(image.getWidth() * image.getHeight() * 4);

		for (int y = 0; y < image.getHeight(); y++) {
			for (int x = 0; x < image.getWidth(); x++) {
				int pixel = pixels[y * image.getWidth() + x];
				buffer.put((byte) ((pixel >> 16) & 0xFF));
				buffer.put((byte) ((pixel >> 8) & 0xFF));
				buffer.put((byte) (pixel & 0xFF));
				buffer.put((byte) ((pixel >> 24) & 0xFF));
			}
		}

		buffer.flip();

		int textureID = GL11.glGenTextures();
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureID);

		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL12.GL_CLAMP_TO_EDGE);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL12.GL_CLAMP_TO_EDGE);

		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);

		GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA8, image.getWidth(), image.getHeight(), 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, buffer);

		return textureID;
	}

}
