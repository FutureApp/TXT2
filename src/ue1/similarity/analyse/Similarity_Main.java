package ue1.similarity.analyse;

import java.util.ArrayList;
import java.util.HashMap;

import xgeneral.modules.Encoding;
import xgeneral.modules.SystemMessage;

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
		String pathMatrixExport = "./temp/matrixExport.txt";
		String pathListExport = "./temp/listExport.txt";
		SimilarityAnalyzer programm = new SimilarityAnalyzer(dirInput, pathMatrixExport, pathListExport);
		ArrayList<DocumentSignatureGramm> allSigis = programm.generateDocumentSignature();
		programm.saveSignatures(allSigis);
		HashMap<String, DocumentSignatureGramm> generateHashMapOfSigis = programm.generateHashMapOfSigis(allSigis);
		MyMatrix simiMatrix = programm.generateSimilarityMatrix(allSigis);
		
		System.out.println(simiMatrix.matrixToString());
		programm.checkSimiliartiy(simiMatrix, generateHashMapOfSigis);
		System.out.println("Resulst");
		System.out.println(simiMatrix.matrixToString());
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
