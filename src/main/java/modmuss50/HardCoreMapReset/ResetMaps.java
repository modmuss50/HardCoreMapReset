package modmuss50.HardCoreMapReset;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiWorldSelection;
import org.apache.commons.io.FileUtils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class ResetMaps {
	public static void deleteFolder(File folder) {
		File[] files = folder.listFiles();
		if (files != null) { //some JVMs return null for empty dirs
			for (File f : files) {
				if (f.isDirectory()) {
					deleteFolder(f);
				} else {
					f.delete();
				}
			}
		}
		folder.delete();
	}

	public static void copyDirWorld(WorldInfo worldInfo, String to, GuiMapList mapList) {
		Minecraft mc = Minecraft.getMinecraft();
		GuiCopyProgress.progress.setStage("Evalutating files to copy");
		Thread copyThread = new Thread(() -> {
			File saveDir = new File(mc.mcDataDir, "saves");
			File target = new File(saveDir, to);
			File source = worldInfo.getSaveFile();
			if (target.exists()) {
				// TODO
				System.err.println("TODO");
				return;
			}
			target.mkdir();
			try {
				GuiCopyProgress.progress.setStage("Finding files to copy");
				GuiCopyProgress.progress.setStep(0);
				GuiCopyProgress.progress.setSteps(0);

				Set<Path> paths = Files.walk(source.toPath())
					.parallel().collect(Collectors.toSet());
				long files = paths.size();
				GuiCopyProgress.progress.setSteps((int) files);

				Path sourcePath = source.toPath();
				Path targetPath = target.toPath();
				paths.parallelStream()
					.map(Path::toFile)
					.filter(file -> !file.isDirectory())
					.forEach(file -> {
						try {
							GuiCopyProgress.progress.setStage("Copying: " + file.getName());
							GuiCopyProgress.progress.next();
							Path targetFilePath = targetPath.resolve(sourcePath.relativize(file.toPath()));
							FileUtils.copyFile(file, targetFilePath.toFile());
						} catch (IOException e) {
							e.printStackTrace();
						}
					});
			} catch (IOException e) {
				e.printStackTrace();
			}
			Minecraft.getMinecraft().getSaveLoader().renameWorld(mapList.folderString, mapList.nameField.getText().trim());
			Minecraft.getMinecraft().addScheduledTask(() -> mc.displayGuiScreen(new GuiWorldSelection(new GuiMainMenu())));

		});
		mc.displayGuiScreen(new GuiCopyProgress(copyThread));

	}

	public static void copyZipWorld(WorldInfo info, String to, GuiMapList mapList) {
		if (!(info instanceof WorldZip)) {
			throw new UnsupportedOperationException();
		}
		WorldZip worldInfo = (WorldZip) info;
		Minecraft mc = Minecraft.getMinecraft();
		GuiCopyProgress.progress.setStage("Evalutating files to extract");
		Thread copyThread = new Thread(() -> {
			File saveDir = new File(mc.mcDataDir, "saves");
			File target = new File(saveDir, to);

			if (target.exists()) {
				// TODO
				System.err.println("TODO");
				return;
			}
			target.mkdir();
			try {
				GuiCopyProgress.progress.setStage("Finding files to copy");
				GuiCopyProgress.progress.setStep(0);
				GuiCopyProgress.progress.setSteps(0);

				FileInputStream is = new FileInputStream(worldInfo.getSaveFile());
				ZipInputStream zis = new ZipInputStream(new BufferedInputStream(is));
				ZipEntry ze;

				GuiCopyProgress.progress.setSteps((int) worldInfo.getZipFile().stream().count());

				try {
					while ((ze = zis.getNextEntry()) != null) {
						GuiCopyProgress.progress.setStage("Exracting: " + ze.getName());
						GuiCopyProgress.progress.next();

						File f = new File(target.getCanonicalPath(), ze.getName());
						if (ze.isDirectory()) {
							f.mkdirs();
							continue;
						}
						f.getParentFile().mkdirs();
						OutputStream fos = new BufferedOutputStream(new FileOutputStream(f));
						try {
							try {
								final byte[] buf = new byte[1024];
								int bytesRead;
								while (-1 != (bytesRead = zis.read(buf))) {
									fos.write(buf, 0, bytesRead);
								}
							} finally {
								fos.close();
							}
						} catch (final IOException ioe) {
							f.delete();
							throw ioe;
						}
					}
				} finally {
					zis.close();
				}

			} catch (IOException e) {
				e.printStackTrace();
			}
			Minecraft.getMinecraft().getSaveLoader().renameWorld(mapList.folderString, mapList.nameField.getText().trim());
			Minecraft.getMinecraft().addScheduledTask(() -> mc.displayGuiScreen(new GuiWorldSelection(new GuiMainMenu())));

		});
		mc.displayGuiScreen(new GuiCopyProgress(copyThread));

	}
}
