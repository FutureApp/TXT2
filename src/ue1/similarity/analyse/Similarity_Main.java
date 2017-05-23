package ue1.similarity.analyse;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import xgeneral.modules.Encoding;
import xgeneral.modules.SystemMessage;
import xgeneral.modules.Writer;

public class Similarity_Main {

	private static int min = 3;
	static String[] arg;
	static String encoding = Encoding.getDefaultEncoding();
	private static int max = 3;

	/**
	 * Entry-point of application.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		arg = args;
		validateAmountOfGivenInput();
		check(arg);

		// String dirInput = "./temp/res/src";
		// String pathMatrixExport = "./result/matrixExport.txt";
		// String pathListExport = "./result/listExport.txt";

		String dirInput = arg[0];
		String pathMatrixExport = arg[1];
		String pathListExport = arg[2];

		SimilarityAnalyzer programm = new SimilarityAnalyzer(dirInput, pathMatrixExport, pathListExport);
		ArrayList<DocumentSignatureGramm> allSigis = programm.generateDocumentSignature();
		programm.saveSignatures(allSigis);
		HashMap<String, DocumentSignatureGramm> generateHashMapOfSigis = programm.generateHashMapOfSigis(allSigis);
		MyMatrix simiMatrix = programm.generateSimilarityMatrix(allSigis);

		programm.checkSimiliartiy(simiMatrix, generateHashMapOfSigis);
		ArrayList<SSDTupele> detHighestSimiliForDocs = programm.detHighestSimiliForDocs(simiMatrix);
		SSDList highestSimiliList = new SSDList(detHighestSimiliForDocs, "doc 1", "highest match with doc2",
				"Similarity");
		
		System.out.println("-- Results --");
		System.out.println(highestSimiliList.toString());
		System.out.println(simiMatrix.matrixToString());

		System.out.println("Exporting results -- starting");
		File exportMatrixFile = new File(pathMatrixExport);
		File exportListFile = new File(pathListExport);

		Writer.delAndWrite(exportMatrixFile, simiMatrix.matrixToString());
		Writer.delAndWrite(exportListFile, highestSimiliList.toString());

		System.out.println("Exporting results -- finished");
		System.out.println();
		System.out.println("Location of matrix -> " + exportMatrixFile.getAbsolutePath());
		System.out.println("Location of list -> " + exportListFile.getAbsolutePath());

		PrintByeBye();

	}

	/**
	 * Prin bye bye
	 * 
	 */
	private static void PrintByeBye() {
		System.out.println("-- Programm finished --");
	}

	/**
	 * Checks if input is okay
	 * @param arg The input.
	 */
	private static void check(String[] arg) {
		String pathDir = arg[0];
		String pathToMatrix = arg[1];
		String pathToList = arg[2];
		File dir = new File(pathDir);
		File fileToMatrix = new File(pathToMatrix);
		File fileToList = new File(pathToList);
		if (dir.isDirectory()) {

		} else {
			SystemMessage.eMessage("Argument 0 doesn't point to a directory. Given <" + pathDir + ">");
			SystemMessage.eMessage("Execution stopped.");
			System.exit(1);
		}
		if (fileToMatrix.exists()) {
			SystemMessage.wMessage("Argument 1 points to an already existing file. This file will be overwritten. <"
					+ fileToMatrix.getAbsolutePath() + ">");
		}
		if (fileToList.exists()) {
			SystemMessage.wMessage("Argument 2 points to an already existing file. This file will be overwritten.<"
					+ fileToList.getAbsolutePath() + ">");
		}

	}

	/**
	 * Checks if the amount of the given input matches the requirements. If okay
	 * then pass else print usage() and terminate program with exit-code 2.
	 */
	public static void validateAmountOfGivenInput() {
		if (arg.length < min || arg.length > max) {
			if (arg.length < min)
				SystemMessage.eMessage("More input is required");
			if (arg.length > max)
				SystemMessage.eMessage("Less input is required");
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
		System.out.println(
				"java -jar <name of jar>.jar <Path to Input-directory(collection)> <Path where to export the matrix> <Path where to export the list> ");
	}
}
