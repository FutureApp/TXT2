package ue2.ass;

import java.util.ArrayList;
import java.util.HashMap;

import org.w3c.dom.Node;

import ue1.kSkipN.newpack.TeiP5;
import xgeneral.modules.Encoding;
import xgeneral.modules.SystemMessage;

public class TaskRunner_Ass {

	static String[] arg;
	static String encoding = Encoding.getDefaultEncoding();

	/**
	 * Entry-point of application.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		arg = args;
		validateAmountOfGivenInput();

		// Init
		String fileLoc = "C:/Users/admin/Desktop/temp/Franz_Kafka_In_der_Strafkolonie/alo.tei";
		String outPutLoc = "./output.atff";
		String firstClusteringTag = "p";
		String secondClusteringTag = "w";
		int condition = 2;

		// From user
		firstClusteringTag = args[0];
		secondClusteringTag = args[1];
		condition = myParseCase(args[2]);
		fileLoc = args[3];
		outPutLoc = args[4];

		// Start of pre -processing
		UE_MainProcess main = new UE_MainProcess();
		TeiP5 readFile = main.readFile(fileLoc);

		// Processing
		ArrayList<ArrayList<Node>> wordsInPara = main.abstractsNeededInfos(readFile, firstClusteringTag,
				secondClusteringTag);

		ArrayList<ArrayList<String>> entrysOfParagraphs = main.generateEntry(wordsInPara, condition);
		HashMap<String, Integer> generateEntryHash = main.generateEntryHash(entrysOfParagraphs);

		// Export
		ARFF_Exporter arffDoc = new ARFF_Exporter(generateEntryHash);
		ArrayList<String> trashParagraphs = new ArrayList<>();
		for (int i = 0; i < entrysOfParagraphs.size(); i++) {
			ArrayList<String> paragraph = entrysOfParagraphs.get(i);
			if (paragraph.size() <= 0) {
				String trashLine = i + " " + paragraph.toString();
				trashParagraphs.add(trashLine);
			} else {
				arffDoc.attachDataToList(paragraph);
			}
		}
		// The real export.
		arffDoc.exportMeToFile(outPutLoc);

		// End-Mess
		System.out.println(main.uniqueWords);
		System.out.println("Paragraphs " + wordsInPara.size());
		System.out.println("Paragraphs words " + entrysOfParagraphs.size());
		System.out.println("UniqureWord:" + main.uniqueWords);
		System.out.println(trashParagraphs);
		System.out.println(trashParagraphs.size());
	}

	private static int myParseCase(String caseNum) {
		int result = 0;
		switch (caseNum) {
		case "0":
			result = 0;
			break;
		case "1":
			result = 1;
			break;
		case "2":
			result = 2;
			break;

		default:
			SystemMessage.wMessage("Case unknown. Use default -- 0");
			result = 0;
			break;
		}

		return result;
	}

	/**
	 * Checks if the amount of the given input matches the requirements. If okay
	 * then pass else print usage() and terminate program with exit-code 2.
	 */
	public static void validateAmountOfGivenInput() {
		if (arg.length < 0) {
			SystemMessage.eMessage("More input is needed");
			System.out.println();
			for (int i = 0; i < arg.length; i++) {
				System.out.printf("Argument %d: %s", i, arg[i]);
				System.out.println();
			}
			usage();
			System.exit(2);
		}
	}

	/**
	 * Prints usage
	 */
	private static void usage() {
		System.out.println("---------- Usage ----------");
		System.out.println("java -jar <name of jar>.jar ");
	}
}
