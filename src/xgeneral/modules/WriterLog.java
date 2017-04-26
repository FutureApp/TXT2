package xgeneral.modules;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map.Entry;

import org.apache.commons.io.FileUtils;

public class WriterLog {

	public static void addContentToFile(String fileName, String content) {
		File file = new File("log/" + fileName);
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
	 * @param fileName
	 *            Name of File
	 * @param content
	 *            The content.
	 */
	public static void delAndWrite(String fileName, String content) {
		File file = new File("log/" + fileName);
		if (file.exists())
			file.delete();
		try {
			FileUtils.write(file, content + System.lineSeparator(), Encoding.getDefaultEncoding(), true);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Writes content of a hashMap to File.
	 * 
	 * @param fileName
	 *            Name of the file.
	 * @param map
	 *            The map which contains the content.
	 */
	public static void delAndWriteHash(String fileName, HashMap<String, Double> map) {
		System.out.println("delAndWrite");
		File file = new File("log/" + fileName);
		if (file.exists())
			file.delete();
		for (Entry<String, Double> element : map.entrySet()) {
			String between = element.getKey();
			String containing = element.getValue().toString();
			String content = between + " " + containing;
			System.out.println(content);
			addContentToFile(fileName, content);
		}
	}
}
