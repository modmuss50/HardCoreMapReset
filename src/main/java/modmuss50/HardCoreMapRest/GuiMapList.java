package modmuss50.HardCoreMapRest;

import net.minecraft.client.AnvilConverterException;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.*;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ChatAllowedCharacters;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.storage.ISaveFormat;
import net.minecraft.world.storage.SaveFormatComparator;
import org.apache.commons.lang3.text.WordUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class GuiMapList extends GuiScreen {
	private static final Logger logger = LogManager.getLogger();
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

		try
		{
			initSaveList();
		}
		catch (AnvilConverterException anvilconverterexception)
		{
			logger.error("Couldn't load level list", anvilconverterexception);
			this.mc.displayGuiScreen(new GuiErrorScreen("Unable to load worlds", anvilconverterexception.getMessage()));
			return;
		}

		loadSaveThumbnails();

		this.nameField = new GuiTextField(0, this.fontRendererObj, this.width / 2 - 100, 45, 200, 20);
		this.nameField.setFocused(true);
		this.nameField.setText(I18n.format("selectWorld.newWorld"));
		sanitizeFolderName();

		this.mapList = new MapList();

		this.buttonList.add(this.createButton = new GuiButton(CREATE_BUTTON_ID, this.width / 2 - 155, this.height - 28, 150, 20, I18n.format("selectWorld.create")));
		this.buttonList.add(new GuiButton(CANCEL_BUTTON_ID, this.width / 2 + 5, this.height - 28, 150, 20, I18n.format("gui.cancel")));
		this.createButton.enabled = false;
	}

	private void loadSaveThumbnails() {
		for (Object save_obj : saveList)
		{
			TemplateSaveFormat save = (TemplateSaveFormat)save_obj;
			if(save.getThumbnail() != null){
				save.setTexture(loadTexture(save.getThumbnail()));
			} else {
				try {
					Random rand = new Random();
					int randomNum = rand.nextInt((4 - 0) + 1) + 0;
					save.setTexture(loadTexture(ImageIO.read(GuiMapList.class.getResourceAsStream("/assets/minecraft/textures/gui/title/background/panorama_" + randomNum + ".png"))));
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private void initSaveList() throws AnvilConverterException {
		this.saveList = MapReset.saveLoader.getSaveList();
		Collections.sort(this.saveList);
		this.selectedSlot = -1;
	}

	@Override
	public void actionPerformed(GuiButton guiButton) {
		if (guiButton.id == CANCEL_BUTTON_ID)
		{
			Minecraft.getMinecraft().displayGuiScreen(parent);
		}
		else if (guiButton.id == CREATE_BUTTON_ID)
		{
			createMap();
			Minecraft.getMinecraft().displayGuiScreen(parent);
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
			this.actionPerformed((GuiButton)this.buttonList.get(0));
		}

		if (this.nameField.isFocused())
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
		this.drawCenteredString(this.fontRendererObj, I18n.format("gui.hardcoremapreset.create_title"), this.width / 2, 20, -1);
		this.drawString(this.fontRendererObj, I18n.format("selectWorld.enterName"), this.width / 2 - 100, 35, -6250336);
		this.drawString(this.fontRendererObj, I18n.format("selectWorld.resultFolder") + " " + this.folderString, this.width / 2 - 100, 68, -6250336);
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
		protected void elementClicked(int slot, boolean doubleClicked, int mouseX, int mouseY) {
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

		@Override
		public int getListWidth() {
			return GuiMapList.this.width - 40;
		}

		@Override
		protected int getScrollBarX() {
			return (this.width / 2) + (this.getListWidth() / 2) - 6;
		}

		@Override
		protected void drawSlot(int slot, int x, int y, int slotHeight, int mouseX, int mouseY) {
			TemplateSaveFormat saveFormat = (TemplateSaveFormat)GuiMapList.this.saveList.get(slot);

			String displayName = saveFormat.getDisplayName();
			String author = saveFormat.getAuthor();
			String by = I18n.format("gui.hardcoremapreset.by");
			String topLine = displayName + ", " + EnumChatFormatting.ITALIC + EnumChatFormatting.BOLD + by + EnumChatFormatting.RESET + ": " + author;

			String folder = saveFormat.getFileName();
			String date = GuiMapList.this.dateFormat.format(new Date(saveFormat.getLastTimePlayed()));
			String middleLine = folder + " (" + date + ")";

			String mode = WordUtils.capitalize(saveFormat.getEnumGameType().getName());
			String cheats = saveFormat.getCheatsEnabled() ? I18n.format("selectWorld.cheats") : "";
			String bottomLine = mode + ", " + cheats;

			GuiMapList.this.drawString(GuiMapList.this.fontRendererObj, topLine,    x + 34, y + 1,       16777215);
			GuiMapList.this.drawString(GuiMapList.this.fontRendererObj, middleLine, x + 34, y + 12,      8421504);
			GuiMapList.this.drawString(GuiMapList.this.fontRendererObj, bottomLine, x + 34, y + 12 + 10, 8421504);

			GL11.glBindTexture(GL11.GL_TEXTURE_2D, saveFormat.getTexture());
			Tessellator tessellator = Tessellator.getInstance();
			WorldRenderer worldrenderer = tessellator.getWorldRenderer();
			worldrenderer.startDrawingQuads();
			worldrenderer.setColorOpaque(255, 255, 255);
			worldrenderer.addVertexWithUV(x, y + 32, zLevel, 0, 1);
			worldrenderer.addVertexWithUV(x + 32, y + 32, zLevel, 1, 1);
			worldrenderer.addVertexWithUV(x + 32, y, zLevel, 1, 0);
			worldrenderer.addVertexWithUV(x, y, zLevel, 0, 0);
			tessellator.draw();
		}
	}

	private void createMap() {
		SaveFormatComparator saveformatcomparator = (SaveFormatComparator)GuiMapList.this.saveList.get(this.selectedSlot);
		ResetMaps.copymap(saveformatcomparator.getFileName(), this.folderString);
		Minecraft.getMinecraft().getSaveLoader().renameWorld(this.folderString, this.nameField.getText().trim());
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

	@Override
	public void onGuiClosed() {
		for (Object save_obj : saveList) {
			TemplateSaveFormat save = (TemplateSaveFormat)save_obj;
			int textureID = save.getTexture();
			GL11.glDeleteTextures(textureID);
		}
	}
}
