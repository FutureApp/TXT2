package ue1.kSkipN;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.security.auth.Subject;

import xgeneral.modules.Encoding;
import xgeneral.modules.Pather;
import xgeneral.modules.SystemMessage;

public class kSkipNGramm_Main {

	static String[] arg;
	static String encoding = Encoding.getDefaultEncoding();

	/**
	 * Entry-point of application.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		arg = args;
		validateInput();
		executeSkipNGramm();

	}

	private static void executeSkipNGramm() {
		String pathToContent = Pather.toDemoData;
		Integer ngramms = 2;
		Integer kgramms = 1;
		File locationToSave = new File(Pather.toTestTask);

		File fileOfContent = new File(pathToContent);
		TeiP5 teiData = TeiP5Loader.loadTei5Document(fileOfContent);
		ArrayList<ArrayList<String>> extractS = teiData.extractS();

		// for each sentence
		for (int i = 0; i < extractS.size(); i++) {
			ArrayList<String> sentence = extractS.get(i);
			
			// Subliste generieren - geht
			ArrayList<List<String>> generateSublists = generateSublists(ngramms, kgramms, sentence);

			// berechne für jede subliste die gramms
			// generate der indexe geht nicht! TODO
			
			
			
			for (int j = 0; j < generateSublists.size(); j++) {
				calcGramm(ngramms, generateSublists.get(j));
				
			}
		}
	}

	private static void calcGramm(Integer ngramms, List<String> list) {
		int kgramm = list.size() - ngramms;
		ArrayList<ArrayList<Integer>> indexes = getIndexes(ngramms,kgramm);
		System.out.println(indexes);
	}

	private static ArrayList<ArrayList<Integer>> getIndexes(Integer ngramms, int kgramm) {
		IndexListGenerator generator = new IndexListGenerator(ngramms, kgramm);
		return generator.indexList;
	}

	private static ArrayList<List<String>> generateSublists(Integer ngramms, Integer kgramms, ArrayList<String> sentence) {
		ArrayList<List<String>> resultList = new ArrayList<>();
		for (int i = ngramms; i <= ngramms + kgramms; i++) {
			ArrayList<List<String>> tempList  = new ArrayList<>();
			for (int j = 0; j <= sentence.size() - i; j++) {
				List<String> subList = sentence.subList(j, j+i);
				tempList.add(subList);
			}
			resultList.addAll(tempList);
		}
		return resultList;
	}

	/**
	 * Checks if the amount of the given input matches the requirements. If okay
	 * then pass else print usage() and terminate program with exit-code 2.
	 */
	public static void validateInput() {
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
