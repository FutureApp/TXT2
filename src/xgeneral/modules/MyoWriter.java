package xgeneral.modules;

import java.io.File;

public class MyoWriter {

	private static final String projectSuffix = ".log";

	public static void writeToLog(String filename, String content) {
		File file = new File(Pather.toLog + filename + projectSuffix);
		Writer.delAndWrite(file, content);
		System.out.println("Log writted to File <"+file.getAbsolutePath()+">");
	}

	public static void addToLog(String filename, String content) {
		File file = new File(Pather.toLog + filename + projectSuffix);
		Writer.addContentToFile(file, content);
	}
}
