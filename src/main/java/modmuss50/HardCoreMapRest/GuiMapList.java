package modmuss50.HardCoreMapRest;

import net.minecraft.client.AnvilConverterException;
import net.minecraft.client.gui.*;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.WordUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class GuiMapList extends GuiScreen {
	private static final Logger logger = LogManager.getLogger();
	private static final int CREATE_BUTTON_ID = 0;
	private static final int CANCEL_BUTTON_ID = 1;
	public GuiScreen parent;
	private List saveList;
	private GuiButton createButton;
	private GuiTextField nameField;
	private MapList mapList;
	private SimpleDateFormat dateFormat = new SimpleDateFormat();;
	private int selectedSlot;

	public GuiMapList(GuiScreen parent) {
		this.parent = parent;
	}

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

		this.nameField = new GuiTextField(this.fontRendererObj, this.width / 2 - 100, 45, 200, 20);
		this.nameField.setFocused(true);
		this.nameField.setText(I18n.format("selectWorld.newWorld"));

		this.mapList = new MapList();

		this.buttonList.add(this.createButton = new GuiButton(CREATE_BUTTON_ID, this.width / 2 - 155, this.height - 28, 150, 20, I18n.format("selectWorld.create")));
		this.buttonList.add(new GuiButton(CANCEL_BUTTON_ID, this.width / 2 + 5, this.height - 28, 150, 20, I18n.format("gui.cancel")));
		this.createButton.enabled = false;
	}

	private void initSaveList() throws AnvilConverterException {
		this.saveList = MapReset.saveLoader.getSaveList();
		Collections.sort(this.saveList);
		this.selectedSlot = -1;
	}

	@Override
	public void actionPerformed(GuiButton guiButton) {
	}

	@Override
	public void drawScreen(int x, int y, float f) {
		this.drawDefaultBackground();
		mapList.drawScreen(x, y, f);
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

		}

		@Override
		protected boolean isSelected(int slot) {
			return false;
		}

		@Override
		protected void drawBackground() {
			GuiMapList.this.drawDefaultBackground();
		}

		@Override
		protected void drawSlot(int slot, int x, int y, int slotHeight, Tessellator tessellator, int mouseX, int mouseY) {
			TemplateSaveFormat saveFormat = (TemplateSaveFormat)GuiMapList.this.saveList.get(slot);

			String displayName = saveFormat.getDisplayName();
			String author = saveFormat.getAuthor();
			String by = I18n.format("gui.hardcoremapreset.by");
			String topLine = displayName + ", " + by + ": " + author;

			String folder = saveFormat.getFileName();
			String date = GuiMapList.this.dateFormat.format(new Date(saveFormat.getLastTimePlayed()));
			String middleLine = folder + " (" + date + ")";

			String mode = WordUtils.capitalize(saveFormat.getEnumGameType().getName());
			String cheats = saveFormat.getCheatsEnabled() ? I18n.format("selectWorld.cheats") : "";
			String bottomLine = mode + ", " + cheats;

			GuiMapList.this.drawString(GuiMapList.this.fontRendererObj, topLine,    x + 2, y + 1,       16777215);
			GuiMapList.this.drawString(GuiMapList.this.fontRendererObj, middleLine, x + 2, y + 12,      8421504);
			GuiMapList.this.drawString(GuiMapList.this.fontRendererObj, bottomLine, x + 2, y + 12 + 10, 8421504);

			ResourceLocation thumbnail = saveFormat.getThumbnail();
		}
	}
}
