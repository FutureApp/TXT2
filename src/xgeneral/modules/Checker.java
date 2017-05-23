package xgeneral.modules;

import java.io.File;
import java.util.ArrayList;

public class Checker {

	public static boolean isNumeric(String str) {
		try {
			Double.parseDouble(str);
		} catch (NumberFormatException nfe) {
			return false;
		}
		return true;
	}

	/**
	 * Checks if a file exits on the given location/path.
	 * 
	 * @param pathToFile
	 *            Path to file
	 * @return True - if file exists.
	 */
	public static boolean fileExists(String pathToFile) {
		return new File(pathToFile).exists();
	}

	/**
	 * Calcs the consins-similarity factor. Strongly inspired by this:
	 * https://stackoverflow.com/questions/520241/how-do-i-calculate-the-cosine-
	 * similarity-of-two-vectors User:Alphaaa
	 * 
	 * @param vectorA
	 *            Vector A
	 * @param vectorB
	 *            Vector B
	 * @return Similarity between Vec A and Vec B
	 */
	public static double cosineSimilarity(ArrayList<Double> vectorA, ArrayList<Double>  vectorB) {
		double dotProduct = 0.0;
		double normA = 0.0;
		double normB = 0.0;
		for (int i = 0; i < vectorA.size(); i++) {
			dotProduct += vectorA.get(i) * vectorB.get(i);
			normA += Math.pow(vectorA.get(i), 2);
			normB += Math.pow(vectorB.get(i), 2);
		}
		return dotProduct / (Math.sqrt(normA) * Math.sqrt(normB));
	}
}
