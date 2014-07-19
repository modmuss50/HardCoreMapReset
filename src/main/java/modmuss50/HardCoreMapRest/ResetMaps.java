package modmuss50.HardCoreMapRest;


import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiSelectWorld;

import java.io.*;

public class ResetMaps {

    public static void resetMaps(){
        Minecraft mc = Minecraft.getMinecraft();
        File saveDir = new File(mc.mcDataDir, "saves");
        File backupDir = new File(mc.mcDataDir, "maps");
        deleteFolder(saveDir);
        saveDir.mkdir();
        try {
            copyDirectory(backupDir, saveDir);
        } catch (IOException e) {
            e.printStackTrace();
        }


        Minecraft.getMinecraft().displayGuiScreen(new GuiSelectWorld(new GuiMainMenu()));

    }

    public static void copyDirectory(File sourceLocation , File targetLocation) throws IOException {
        if (sourceLocation.isDirectory()) {
            if (!targetLocation.exists()) {
                targetLocation.mkdir();
            }

            String[] children = sourceLocation.list();
            for (int i=0; i<children.length; i++) {
                copyDirectory(new File(sourceLocation, children[i]),
                        new File(targetLocation, children[i]));
            }
        } else {

            InputStream in = new FileInputStream(sourceLocation);
            OutputStream out = new FileOutputStream(targetLocation);

            // Copy the bits from instream to outstream
            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            in.close();
            out.close();
        }
    }

    public static void deleteFolder(File folder) {
        File[] files = folder.listFiles();
        if(files!=null) { //some JVMs return null for empty dirs
            for(File f: files) {
                if(f.isDirectory()) {
                    deleteFolder(f);
                } else {
                    f.delete();
                }
            }
        }
        folder.delete();
    }

}
