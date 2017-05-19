package ue1.sentiment.analyse;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import xgeneral.modules.Encoding;
import xgeneral.modules.SymboleClazz.SentiLabel;
import xgeneral.modules.SystemMessage;

public class TaskRunner_Template {

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
		check(arg);

		// Mapping

//		String pathToInputFile = "./task/01Task01/test.txt";
		String pathToInputFile = "./task/01Task01/Sentiment_All.txt";
		String pathToPrioFile = "./task/01Task01/lexikon.txt";
		// Processing
		System.out.println("-- Processing --");
		System.out.println("-- Loading senti-list --");
		SentiWordNetDemoCode sentiwordnet = new SentiWordNetDemoCode(pathToPrioFile);
		System.out.println("-- Loading complete --");
		System.out.println("-- Starting analysis --");
		SentiAnalyse analyse = new SentiAnalyse(sentiwordnet, pathToInputFile);
		analyse.runAnalysis();
		System.out.println("-- Analysis finished --");
		//ArrayList<ExameSentence> createExameSentenceList = createExameSentenceList(pathToInputFile);
		// System.out.println(createExameSentenceList.size());
		//
		// for (int i = 0; i < createExameSentenceList.size(); i++) {
		// ExameSentence sentence = createExameSentenceList.get(i);
		// getPolarity(sentence,sentiwordnet);
		// }		
		
	}

	private static double getPolarity(ExameSentence sentence, SentiWordNetDemoCode sentiwordnet) {
		return sentiwordnet.getPolarity(sentence);
	}

	private static ArrayList<ExameSentence> createExameSentenceList(String pathToInputFile) {
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
//						System.out.println("Original: " + line);
//						System.out.println("Sentence: " + sentence.getSentenceAsString());
//						result.add(sentence);
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

	private static void check(String[] args) {
		// TODO Auto-generated method stub

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
