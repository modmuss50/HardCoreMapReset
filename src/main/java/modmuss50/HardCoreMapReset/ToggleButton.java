package modmuss50.HardCoreMapReset;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.OpenGlHelper;
import org.lwjgl.opengl.GL11;

import java.awt.*;

public class ToggleButton extends GuiButton {

    public boolean isOn = false;

    public ToggleButton(int id, int xPosition, int yPosition, String displayString) {
        super(id, xPosition, yPosition, displayString);
    }

    public ToggleButton(int id, int xPosition, int yPosition, int width, int height, String displayString) {
        super(id, xPosition, yPosition, width, height, displayString);
    }

    @Override
    public void drawButton(Minecraft minecraft, int x, int y) {
        if (this.visible) {
            FontRenderer fontrenderer = minecraft.fontRendererObj;
            minecraft.getTextureManager().bindTexture(buttonTextures);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            this.hovered = x >= this.xPosition && y >= this.yPosition && x < this.xPosition + this.width && y < this.yPosition + this.height;
            boolean bool = this.hovered;
            if (isOn) {
                bool = true;
            }
            int k = this.getHoverState(bool);
            GL11.glEnable(GL11.GL_BLEND);
            OpenGlHelper.glBlendFunc(770, 771, 1, 0);
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            this.drawTexturedModalRect(this.xPosition, this.yPosition, 0, 46 + k * 20, this.width / 2, this.height);
            this.drawTexturedModalRect(this.xPosition + this.width / 2, this.yPosition, 200 - this.width / 2, 46 + k * 20, this.width / 2, this.height);
            this.mouseDragged(minecraft, x, y);
            int l = 14737632;

            if (packedFGColour != 0) {
                l = packedFGColour;
            } else if (!this.enabled) {
                l = 10526880;
            } else if (this.isOn) {
                l = Color.green.getRGB();
            } else if (this.hovered) {
                l = 16777120;
            }

            this.drawCenteredString(fontrenderer, this.displayString, this.xPosition + this.width / 2, this.yPosition + (this.height - 8) / 2, l);

            if (isOn) {
                this.drawCenteredString(fontrenderer, "X", this.xPosition + this.width / 2 - (this.width / 2) - 10, this.yPosition + (this.height - 8) / 2, Color.red.getRGB());
            }
        }
    }


    @Override
    /**
     * Returns true if the mouse has been pressed on this control. Equivalent of MouseListener.mousePressed(MouseEvent
     * e).
     */
    public boolean mousePressed(Minecraft minecraft, int x, int y) {
        if (this.enabled && this.visible && x >= this.xPosition && y >= this.yPosition && x < this.xPosition + this.width && y < this.yPosition + this.height) {
            this.isOn = !this.isOn;
            return true;
        }
        return false;
    }
}
