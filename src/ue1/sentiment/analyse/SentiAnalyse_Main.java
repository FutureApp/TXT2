package ue1.sentiment.analyse;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import xgeneral.modules.Checker;
import xgeneral.modules.Encoding;
import xgeneral.modules.SymboleClazz.SentiLabel;
import xgeneral.modules.SystemMessage;
import xgeneral.modules.Writer;

public class SentiAnalyse_Main {

	static String[] arg;
	static String encoding = Encoding.getDefaultEncoding();
	static final int minArguments = 2;
	static final int maxArguments = 3;
	static final String exportDir = "./";

	/**
	 * Entry-point of application.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		String pathToLexiconFile;
		String pathToInputFile;
//		pathToLexiconFile = "./task/01Task01/lexikon.txt";
//		pathToInputFile = "./task/01Task01/Sentiment_All.txt";
		arg = args;
		validateAmountOfGivenInput();
		ArrayList<String> checkedArguments = check(arg);

		// Mapping
		pathToLexiconFile = checkedArguments.get(0);
		pathToInputFile = checkedArguments.get(1);
		Boolean binaryExecution = returnTrueIfBinary(checkedArguments.get(2));
		/*
		 * System.out.println(pathToLexiconFile);
		 * System.out.println(pathToInputFile);
		 * System.out.println(binaryExecution);
		 */
		// Processing
		System.out.println("-- Processing --");
		System.out.println("-- Loading senti-list --");
		SentiWordNetDemo sentiwordnet = new SentiWordNetDemo(pathToLexiconFile);
		System.out.println("-- Loading complete --");
		System.out.println("-- Starting analysis --");
		SentiAnalyse analyse = new SentiAnalyse(sentiwordnet, pathToInputFile);
		ArrayList<FourFieldTable> exCounter = analyse.runAnalysis(binaryExecution);
		System.out.println("-- Analysis finished --");
		System.out.println("");
		System.out.println("-- Print results -- ");
		for (FourFieldTable fourFieldTable : exCounter) {
			System.out.println("****----****");
			System.out.println(fourFieldTable.toString());
		}

		for (FourFieldTable fourFieldTable : exCounter) {
			exportResults(fourFieldTable, exportDir);
		}
		System.out.println();
		System.out.println();
		System.out.println("-- Programm finished --");

	}

	private static void exportResults(FourFieldTable fourFieldTable, String exportDirectory) {
		String catagorySpecific = detFilePrefix(fourFieldTable.getCategory());
		File exportFile = new File(exportDirectory + "/results/" + "category_" + catagorySpecific + ".txt");
		Writer.delAndWrite(exportFile, fourFieldTable.toString());
		System.out.println("Export of category [" + catagorySpecific + "] finished. Location <"
				+ exportFile.getAbsolutePath() + ">");
	}

	private static String detFilePrefix(SentiLabel category) {
		String result = "";
		switch (category) {
		case pos:
			result = "positiv";
			break;
		case neg:
			result = "negativ";
			break;
		default:
			result = "";
			break;
		}
		return result;

	}

	private static Boolean returnTrueIfBinary(String string) {
		Boolean binaryExecution = (string.compareTo("bin") == 0) ? true : false;
		return binaryExecution;
	}

	@Deprecated
	public static double getPolarity(ExameSentence sentence, SentiWordNetDemo sentiwordnet) {
		return sentiwordnet.getPolarity(sentence);
	}

	public static ArrayList<ExameSentence> createExameSentenceList(String pathToInputFile) {
		ArrayList<ExameSentence> result = new ArrayList<>();
		BufferedReader csv = null;
		int missedImport = 0;
		int lineNumber = 0;
		try {
			String line;
			csv = new BufferedReader(new FileReader(pathToInputFile));
			while ((line = csv.readLine()) != null) {
				// System.out.println(lineNumber + " " + line);
				List<String> split = Arrays.asList(line.split(" "));
				if (split.size() >= 2) {
					SentiLabel entryLabel = Labler.detLabel(split.get(0));
					if (Labler.isLabelnKnown(entryLabel)) {
						ExameSentence sentence = new ExameSentence(entryLabel);
						sentence.addAllWords(split.subList(1, split.size()));
						// System.out.println("Original: " + line);
						// System.out.println("Sentence: " +
						// sentence.getSentenceAsString());
						// result.add(sentence);
					} else {
						missedImport++;
						SystemMessage.wMessage("SOFT Problem -- Resource at line " + lineNumber
								+ " doesn't fit to the requirements (Formate not accepted). Couln't determine the specific label for the sentence. "
								+ "The ressource at this point will be dismissed(passed) and will not have any impact at the test-step.");

						SystemMessage.wMessage("Resource (content): <" + line + ">");
					}
				} else {
					missedImport++;
					SystemMessage.wMessage("HARD Problem -- " + "Resource at line " + lineNumber
							+ " doesn't fit to the requirements (Formate not accepted). "
							+ "The resources at this point will be dismissed(passed) and will not have any impact at the test-step.");
					SystemMessage.wMessage("Resource (content): <" + line + ">");
				}
				lineNumber++;
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (csv != null) {
				try {
					csv.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		System.out.println("Resources found: " + lineNumber);
		System.out.println("Resources corrupted: " + missedImport);
		System.out.println("-- Finished:  Import of ressorces --");
		return result;
	}

	private static ArrayList<String> check(String[] arg) {
		ArrayList<String> arguments = new ArrayList<>();
		String pathToLexicon = arg[0];
		String pathToTestInput = arg[1];

		if (!Checker.fileExists(pathToLexicon)) {
			SystemMessage.eMessage("The lexicon doesn't exist at the given location. Given Location: " + pathToLexicon);
		}
		if (!Checker.fileExists(pathToTestInput))
			SystemMessage
					.eMessage("The input-file doesn't exist at the given location. Given Location: " + pathToTestInput);

		String binaryExecutionKeyword = (arg.length > minArguments) ? arg[2] : "bin";

		if ((binaryExecutionKeyword.toLowerCase().compareTo("bin") == 0)
				|| (binaryExecutionKeyword.toLowerCase().compareTo("mul") == 0)) {
			// Perfect do nothing
		} else {
			SystemMessage
					.eMessage("Don't recognize the 2 Argument (optional one). Given <" + binaryExecutionKeyword + ">");
			SystemMessage.eMessage("Supported Arguments: <bin|mul>");
			System.exit(1);
		}

		arguments.add(pathToLexicon);
		arguments.add(pathToTestInput);
		arguments.add(binaryExecutionKeyword);
		return arguments;
	}

	/**
	 * Checks if the amount of the given input matches the requirements. If okay
	 * then pass else print usage() and terminate program with exit-code 2.
	 */
	public static void validateAmountOfGivenInput() {
		if (arg.length < minArguments || arg.length > maxArguments) {
			if (arg.length < minArguments)
				SystemMessage.eMessage("More input is needed");
			if (arg.length > maxArguments)
				SystemMessage.eMessage("Less input is needed");

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
		System.out.println("java -jar <name of jar>.jar <path to lexicon> <path to test-input> [optional: <bin|mul>]");
	}
}
