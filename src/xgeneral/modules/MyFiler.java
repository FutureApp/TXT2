package xgeneral.modules;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.commons.io.FileUtils;

public class MyFiler {

	public static String readFile(String location) {
		String result = "";
		try {
			result = FileUtils.readFileToString(new File(location), Encoding.getDefaultEncoding());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * Counts all lines in a file.
	 * 
	 * Source:
	 * https://stackoverflow.com/questions/1277880/how-can-i-get-the-count-of-
	 * line-in-a-file-in-an-efficient-way
	 * 
	 * @param file
	 *            The file
	 * @return Number of lines in the file
	 */
	public static Integer countLinesOverBuffer(File file) {
		int lines = 0;
		Integer BUFFER_SIZE = 8 * 1024;

		FileInputStream fis = null;
		try {
			fis = new FileInputStream(file);
			byte[] buffer = new byte[BUFFER_SIZE]; // BUFFER_SIZE = 8 * 1024
			int read;

			while ((read = fis.read(buffer)) != -1) {
				for (int i = 0; i < read; i++) {
					if (buffer[i] == '\n')
						lines++;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				fis.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return lines;
	}

	/**
	 * Counts all lines in specific file. Count over stream.
	 * 
	 * @param file
	 *            The file.
	 * @return Number of lines.
	 */
	public static long countLinesInJava8(File file) {
		long lines = 0;
		try {
			lines = Files.lines(Paths.get(file.getAbsolutePath())).count();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return lines;
	}
}
