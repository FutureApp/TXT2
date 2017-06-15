package ue1.kSkipN.newpack;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.io.FileUtils;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import xgeneral.modules.Encoding;
import xgeneral.modules.SystemMessage;

public class TeiP5Loader {

	/**
	 * Loads an tei5 document. For TeiP5 {@link TeiP5}
	 * 
	 * @param file
	 *            File of document
	 * @return TeiP5 Object.
	 */
	public static TeiP5 loadTei5Document(File file) {
		Document doc = null;
		System.out.println(file.getAbsolutePath());
		if (!checkIfFileExits(file)) {
			printError("File not found <" + file.getAbsolutePath() + ">");
		} else {
			String content = loadFileInput(file);
			if (content == null) {
				printError("Execution stopped");
				System.exit(1);
			} else {
				doc = createDoc(file);
			}
		}
		if (doc == null) {
			printError("Couldn't read and creat a Tei5-Object. Execution will stop.");
			System.exit(1);
		}
		System.out.println("Document will be returned");
		return new TeiP5(doc);
	}

	/**
	 * Creates the document.
	 * 
	 * @param file
	 *            File of document
	 * @return Content of file as document..
	 */
	private static Document createDoc(File file) {
		Document doc = null;
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			doc = dBuilder.parse(file);
			doc.getDocumentElement().normalize();
			System.out.println("!");
		} catch (SAXException | IOException | ParserConfigurationException e) {
			e.printStackTrace();
		}
		return doc;
	}

	/**
	 * Loads the content of a file into a string.
	 * 
	 * @param file
	 *            The file.
	 * @return A String which contains the content of the file.
	 */
	private static String loadFileInput(File file) {
		String readFileToString = null;
		try {
			readFileToString = FileUtils.readFileToString(file, Encoding.getDefaultEncoding());
		} catch (IOException e) {
			e.printStackTrace();
			printError("Couldn't read from Tei5Document. Location <" + file.getAbsolutePath() + ">");
		}
		return readFileToString;
	}

	/**
	 * Check if the file exits.
	 * 
	 * @param file
	 *            The file to check.
	 * @return True if exits.
	 */
	private static Boolean checkIfFileExits(File file) {
		return file.exists() ? true : false;
	}

	/**
	 * Print an error message.
	 * 
	 * @param content
	 *            The error message.
	 */
	private static void printError(String content) {
		SystemMessage.eMessage(content);
	}
}
