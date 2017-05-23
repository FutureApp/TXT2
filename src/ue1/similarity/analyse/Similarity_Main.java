package ue1.similarity.analyse;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import xgeneral.modules.Encoding;
import xgeneral.modules.SystemMessage;
import xgeneral.modules.Writer;

public class Similarity_Main {

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

		String dirInput = "./temp/res/src";
		String pathMatrixExport = "./result/matrixExport.txt";
		String pathListExport = "./result/listExport.txt";
		SimilarityAnalyzer programm = new SimilarityAnalyzer(dirInput, pathMatrixExport, pathListExport);
		ArrayList<DocumentSignatureGramm> allSigis = programm.generateDocumentSignature();
		programm.saveSignatures(allSigis);
		HashMap<String, DocumentSignatureGramm> generateHashMapOfSigis = programm.generateHashMapOfSigis(allSigis);
		MyMatrix simiMatrix = programm.generateSimilarityMatrix(allSigis);

		System.out.println(simiMatrix.matrixToString());
		programm.checkSimiliartiy(simiMatrix, generateHashMapOfSigis);
		ArrayList<SSDTupele> detHighestSimiliForDocs = programm.detHighestSimiliForDocs(simiMatrix);
		SSDList highestSimiliList = new SSDList(detHighestSimiliForDocs, "doc 1", "highest match with doc2",
				"Similarity");
		System.out.println(highestSimiliList.toString());
		System.out.println("Result");
		System.out.println(simiMatrix.matrixToString());

		System.out.println("Exporting results -- starting");
		File exportMatrixFile = new File(pathMatrixExport);
		File exportListFile = new File(pathListExport);

		Writer.delAndWrite(exportMatrixFile, simiMatrix.matrixToString());
		Writer.delAndWrite(exportListFile, highestSimiliList.toString());

		System.out.println("Exporting results -- finsihed");
		System.out.println();
		System.out.println("Location of matrix -> " + exportMatrixFile.getAbsolutePath());
		System.out.println("Location of list -> " + exportListFile.getAbsolutePath());
		
		PrintByeBye();

	}

	private static void PrintByeBye() {
		// TODO Auto-generated method stub
		
	}

	private static void check(String[] arg2) {
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
