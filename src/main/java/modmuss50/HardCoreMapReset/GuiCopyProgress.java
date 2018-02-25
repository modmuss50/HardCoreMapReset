package modmuss50.HardCoreMapReset;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;

import java.awt.*;

public class GuiCopyProgress extends GuiScreen {

	public static final CopyProgress progress = new CopyProgress();
	Thread copyThread;
	private boolean started = false;

	public GuiCopyProgress(Thread thread) {
		this.copyThread = thread;
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		this.drawDefaultBackground();
		//super.drawScreen(mouseX, mouseY, partialTicks);
		if(!started){
			started = true;
			Minecraft.getMinecraft().addScheduledTask(() -> copyThread.start());
		}

		this.drawCenteredString(this.fontRenderer, "Copying map..", this.width / 2, 20, -1);
		this.drawCenteredString(this.fontRenderer, progress.getStage(), this.width / 2, 60, -1);

		int barWidth = 300;
		int barHeight = 15;
		int borderSize = 2;

		if(progress.getStep() != 0){
			drawBetterBox(this.width / 2 - (barWidth / 2), this.height /2 , barHeight + borderSize, barWidth + borderSize, -1315861);
			drawBetterBox(this.width / 2 - (barWidth / 2) + borderSize, this.height /2 + borderSize , barHeight - borderSize, (barWidth - borderSize) * (progress.getStep()) / (progress.getSteps()), Color.GREEN.darker().getRGB());
			this.drawCenteredString(this.fontRenderer, progress.getStep() + "/" + progress.getSteps() + " (" + (progress.getStep() * 100) / progress.getSteps() + "%)", this.width / 2, this.height /2 + borderSize + 3, -1);
		}
	}

	public void drawBetterBox(int x, int y, int heigh, int width, int color){
		Gui.drawRect(x, y, x + width, y + heigh, color);
	}
}
