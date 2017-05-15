package modmuss50.HardCoreMapRest;

import net.minecraft.src.GuiButton;
import net.minecraft.src.GuiSelectWorld;

import java.lang.reflect.Field;
import java.util.List;

public class GuiTweaker {

	public static int BUTTON_ID = 405;

	public static CustomButton button;

	public static void onGuiInit(GuiSelectWorld gui) {
			List<GuiButton> buttonList = getButtonList(gui);
			if(buttonList.contains(button)){
				return;
			}

			// Width - 40 - (2 x 4) for spaces
			int width = (gui.width - 48) / 3;
			int yPosition = 1;

			int xPosition = (gui.width / 2) + (width / 2) + 4;

			button = new CustomButton(BUTTON_ID, xPosition, yPosition, width, 20, "Create From Template");
			buttonList.add(button);
	}

	private static List<GuiButton> getButtonList(GuiSelectWorld gui){
		try {
			Field field = gui.getClass().getSuperclass().getDeclaredField("controlList");
			field.setAccessible(true);
			return (List<GuiButton>) field.get(gui);
		} catch (NoSuchFieldException e) {
			//e.printStackTrace();
		} catch (IllegalAccessException e) {
			//e.printStackTrace();
		}
		try {
			Field field = gui.getClass().getSuperclass().getDeclaredField("s");
			field.setAccessible(true);
			return (List<GuiButton>) field.get(gui);
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}

//	private static void setButtonList(GuiSelectWorld gui, List<GuiButton> list){
//		try {
//			Field field = gui.getClass().getSuperclass().getDeclaredField("controlList");
//			field.setAccessible(true);
//			field.set(gui, list);
//
//		} catch (NoSuchFieldException | IllegalAccessException e) {
//			e.printStackTrace();
//		}
//
//	}
}
