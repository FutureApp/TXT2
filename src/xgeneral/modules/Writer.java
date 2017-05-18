package xgeneral.modules;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map.Entry;

import org.apache.commons.io.FileUtils;

public class Writer {

	/**
	 * Add lines to the given File
	 * 
	 * @param file
	 *            The file itself.
	 * @param content
	 *            The content.
	 */
	public static void addContentToFile(File file, String content) {
		try {
			FileUtils.write(file, content + System.lineSeparator(), Encoding.getDefaultEncoding(), true);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Removes a given file and recreates it. After recreating, the content will
	 * be added to the file.
	 * 
	 * @param file
	 *            The File itself.
	 * @param content
	 *            The content.
	 */
	public static void delAndWrite(File file, String content) {
		if (file.exists())
			file.delete();
		try {
			FileUtils.write(file, content + System.lineSeparator(), Encoding.getDefaultEncoding(), true);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void removeIfNeeded(File fileToSave) {
		if (fileToSave.exists()) {
			SystemMessage.wMessage(
					"Result-file exists. File will be overwritten. File <" + fileToSave.getAbsolutePath() + ">");
			try {
				FileUtils.forceDelete(fileToSave);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Writes content of a hashMap to File.
	 * 
	 * @param file
	 *            The location to save. If exits then delte the File.
	 * @param map
	 *            The map which contains the content.
	 */
	public static void delAndWriteHash(File file, HashMap<String, Double> map) {
		System.out.println("delAndWrite");
		if (file.exists())
			file.delete();
		for (Entry<String, Double> element : map.entrySet()) {
			String between = element.getKey();
			String containing = element.getValue().toString();
			String content = between + " " + containing;
			System.out.println(content);
			addContentToFile(file, content);
		}
	}
}
