package ue1.kSkipN.old;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import xgeneral.modules.Encoding;
import xgeneral.modules.Pather;
import xgeneral.modules.SymboleClazz;
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
		Integer ngramms = 3;
		Integer kgramms = 4;

		File fileOfContent = new File(pathToContent);
		TeiP5 teiData = TeiP5Loader.loadTei5Document(fileOfContent);
		ArrayList<ArrayList<String>> extractS = teiData.extractS();
		HashMap<String, ArrayList<ArrayList<Integer>>> indexHashMap = BitShit.generateNeededIndexList(ngramms, kgramms);
		ArrayList<HashSet<ArrayList<String>>> grammsOfAllSentences = new ArrayList<>();
		indexHashMap.forEach((a, b) -> System.out.println(a));
		// for each sentence
		for (int i = 0; i < extractS.size(); i++) {
			ArrayList<String> sentence = extractS.get(i);
			// Subliste generieren - geht
			ArrayList<List<String>> sublists = generateSublists(ngramms, kgramms, sentence);

			// berechne für jede subliste die gramms
			HashSet<ArrayList<String>> sentenceGramms = new HashSet<>();
			for (int j = 0; j < sublists.size(); j++) {
				List<String> item = sublists.get(j);
				int n = ngramms;
				int k = item.size() - n;
				ArrayList<ArrayList<Integer>> indexList = indexHashMap.get(n + "," + k);
				ArrayList<ArrayList<String>> generateNGramm = generateNGramm(item, indexList);
				for (ArrayList<String> oneGrammOfSentence : generateNGramm) {
					sentenceGramms.add(oneGrammOfSentence);
				}
			}
			grammsOfAllSentences.add(sentenceGramms);

			List<String> sentenceGrammList = new ArrayList<>();
			for (ArrayList<String> e : sentenceGramms) {
				StringBuilder builder = new StringBuilder();
				for (String grammComponent : e) {
					System.out.println("Gramm com:" + grammComponent);
					builder.append(
							grammComponent.substring(0, grammComponent.indexOf(SymboleClazz.SPECIAL_DELIM)) + ",");
					;
				}
				builder.setCharAt(builder.length() - 1, ')');
				builder.insert(0, '(');
				System.out.println("Builder-> " + builder);
				sentenceGrammList.add(builder.toString());
			}
			Collections.sort(sentenceGrammList);
			System.out.println("Size: "+sentenceGrammList.size());
			System.out.println("----");
		}
	}

	private static ArrayList<ArrayList<String>> generateNGramm(List<String> item,
			ArrayList<ArrayList<Integer>> indexList) {

		ArrayList<ArrayList<String>> gramms = new ArrayList<>();
		for (int i = 0; i < indexList.size(); i++) {
			ArrayList<Integer> indexes = indexList.get(i);
			ArrayList<String> gramm = new ArrayList<>();
			for (int j = 0; j < indexes.size(); j++) {
				Integer index = indexes.get(j);
				gramm.add(item.get(index));
			}
			gramms.add(gramm);
		}
		System.out.println(gramms);
		return gramms;
	}

	private static ArrayList<List<String>> generateSublists(Integer ngramms, Integer kgramms,
			ArrayList<String> sentence) {

		ArrayList<String> uniqueWords = new ArrayList<>();
		// Tomake every word unique
		for (int i = 0; i < sentence.size(); i++) {
			String word = sentence.get(i);
			String newWord = word + SymboleClazz.SPECIAL_DELIM + i;
			uniqueWords.add(newWord);
		}

		ArrayList<List<String>> resultList = new ArrayList<>();
		for (int i = ngramms; i <= ngramms + kgramms; i++) {
			ArrayList<List<String>> tempList = new ArrayList<>();
			for (int j = 0; j <= uniqueWords.size() - i; j++) {
				List<String> subList = uniqueWords.subList(j, j + i);
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
