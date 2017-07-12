package downloader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.ConnectException;
import java.net.MalformedURLException;
import java.net.SocketException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.apache.commons.io.FileUtils;

import xgeneral.modules.Encoding;

public class MainDownloaderPics {

	public static void main(String[] args) throws ConnectException {

		int broken = 0;
		int key = 0;

		String linkList = "C:/Users/admin/Desktop/temp/gleim/downloads/cat/cats01.txt";
		String dirToSave = "C:/Users/admin/Desktop/temp/gleim/downloads/cat/catdown";

		// linkList = args[0];
		// dirToSave = args[1];

		String outPutDirPrep = dirToSave;
		checkAndCreateNewDir(dirToSave);

		String dataFileName = dirToSave.substring(dirToSave.lastIndexOf("/") + 1);
		List<String> readLines = null;
		try {
			readLines = FileUtils.readLines(new File(linkList), Encoding.getDefaultEncoding());
		} catch (IOException e1) {
			System.out.println("Sim EL");

		}
		for (int i = 0; i < readLines.size(); i++) {
			int counter = 0;
			String link = readLines.get(i);
			String linkFileName = link.substring(link.lastIndexOf("/"));
			String fileFormate = ".jpg";

			if (linkFileName.contains("."))
				fileFormate = linkFileName.substring(linkFileName.lastIndexOf("."));

			try (InputStream in = new URL(link).openStream()) {
				String trueFileName = dataFileName + "_" + i + fileFormate;
				String trueFileLocation = dirToSave + "/" + trueFileName;
				File outputFile = new File(trueFileLocation);

				delelteFileOrDir(outputFile);
				Files.copy(in, Paths.get(outputFile.getAbsolutePath()));
			} catch (Exception e) {
				broken++;
			}

			if (i % 10 == 0) {
				System.out.println(i + " / " + readLines.size());
			}
		}
		System.out.println("Finished");
		System.out.println("Result --- ");
		System.out.println("Total -" + readLines.size());
		System.out.println("Working - " + (readLines.size() - broken));
		System.out.println("Broken -" + broken);
		System.out.println("----");
	}

	private static void checkAndCreateNewDir(String outPutDirFinal) {
		File file = new File(outPutDirFinal);
		delelteFileOrDir(file);
		file.mkdirs();
		System.out.println("Dir created: <" + file.getAbsolutePath() + ">");
	}

	private static void delelteFileOrDir(File dir) {
		if (dir.exists()) {
			if (dir.isDirectory())
				try {
					FileUtils.deleteDirectory(dir);
				} catch (IOException e) {
					System.out.println("Some E1");
				}
			else if (dir.isFile())
				FileUtils.deleteQuietly(dir);
			else
				System.out.println("Something un-caught happens");
		}
	}
}
