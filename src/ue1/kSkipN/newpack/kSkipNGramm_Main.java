package ue1.kSkipN.newpack;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Formatter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import xgeneral.modules.Checker;
import xgeneral.modules.Encoding;
import xgeneral.modules.Pather;
import xgeneral.modules.SymboleClazz;
import xgeneral.modules.SystemMessage;
import xgeneral.modules.Writer;

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
		check(arg);

		// The user-input
		int nGramm = Integer.parseInt(arg[3]);
		int kGramm = Integer.parseInt(arg[2]);
		String pathForLoading = arg[0];
		String pathForSaving = arg[1];

		// The core
		executeSkipNGramm(nGramm, kGramm, pathForLoading, pathForSaving);

		// Finish message =)
		byeByeMessage(pathForSaving);

	}

	/**
	 * Time to say good bye!
	 * 
	 * @param pathForSaving
	 *            Path to file which contains the results.
	 */
	private static void byeByeMessage(String pathForSaving) {
		File file = new File(pathForSaving);
		System.out.println();
		System.out.println("Execution hast finished!!!" + System.lineSeparator() + "Take a look at the results: <"
				+ file.getAbsolutePath() + ">");
	}

	/**
	 * The core of execution
	 * 
	 * @param ngramm
	 *            Number for n.
	 * @param kgramm
	 *            Number for k.
	 * @param pathForLoading
	 *            Path to file where to load from (File need to be TEI5 conform)
	 * @param pathForSaving
	 *            Path to file where to save the results.
	 */
	private static void executeSkipNGramm(Integer ngramm, Integer kgramm, String pathForLoading, String pathForSaving) {
		String pathToContent = pathForLoading;
		Integer ngramms = ngramm;
		Integer kgramms = kgramm;
		File fileToSave = new File(pathForSaving);

		File fileOfContent = new File(pathToContent);
		TeiP5 teiData = TeiP5Loader.loadTei5Document(fileOfContent);
		ArrayList<ArrayList<String>> extractS = teiData.extractEachLemmaBySentence();
		HashMap<String, ArrayList<ArrayList<Integer>>> indexHashMap = BitShift.generateNeededIndexList(ngramms,
				kgramms);
		ArrayList<List<String>> grammsOfAllSentences = new ArrayList<>();
		indexHashMap.forEach((a, b) -> System.out.println(a));

		// for each sentence
		for (int i = 0; i < extractS.size(); i++) {
			ArrayList<String> sentence = extractS.get(i);
			// Generates all sentence-tokens.
			ArrayList<List<String>> sublists = generateSublists(ngramms, kgramms, sentence);

			// Generates all gramms- for the specific sentence and window-size.
			HashSet<ArrayList<String>> sentenceGramms = new HashSet<>();
			for (int j = 0; j < sublists.size(); j++) {
				List<String> item = sublists.get(j);
				int n = ngramms;
				int k = item.size() - n;

				// Look up the indices end generate the k,n-gramm.
				ArrayList<ArrayList<Integer>> indexList = indexHashMap.get(n + "," + k);
				ArrayList<ArrayList<String>> generateNGramm = generateNGramm(item, indexList);

				// Add every gramm to the sentence-gramm list.
				for (ArrayList<String> oneGrammOfSentence : generateNGramm) {
					sentenceGramms.add(oneGrammOfSentence);
				}
			}

			// Remove identifier to show the particular word.
			List<String> sentenceGrammList = new ArrayList<>();
			for (ArrayList<String> e : sentenceGramms) {
				StringBuilder builder = new StringBuilder();
				for (String grammComponent : e) {
					System.out.println("Gramm com:" + grammComponent);
					builder.append(
							grammComponent.substring(0, grammComponent.indexOf(SymboleClazz.SPECIAL_DELIM)) + ",");
				}
				builder.setCharAt(builder.length() - 1, ')');
				builder.insert(0, '(');
				System.out.println("Builder-> " + builder);
				sentenceGrammList.add(builder.toString());
			}

			Collections.sort(sentenceGrammList);
			// Each sentence to the document-gramms.
			grammsOfAllSentences.add(sentenceGrammList);

			// Sorts the list
			System.out.println("Size: " + sentenceGrammList.size());
			System.out.println("----");
			System.out.println(sentenceGramms);
			System.out.println(grammsOfAllSentences);
			saveResults(fileToSave, grammsOfAllSentences, kgramms, ngramms);
		}
	}

	/**
	 * Saves the result.
	 * 
	 * @param fileToSave
	 *            File to save to
	 * @param grammsOfAllSentences
	 *            All gramms.
	 * @param kgramms
	 *            Number of k-gramms.
	 * @param ngramms
	 *            Number of n-gramms.
	 */
	private static void saveResults(File fileToSave, ArrayList<List<String>> grammsOfAllSentences, Integer kgramms,
			Integer ngramms) {

		Writer.removeIfNeeded(fileToSave);
		if (grammsOfAllSentences.size() == 0 || grammsOfAllSentences.size() == 1) {
			singleExport(fileToSave, grammsOfAllSentences, kgramms, ngramms);
		} else {
			multiExport(fileToSave, grammsOfAllSentences, kgramms, ngramms);
		}
	}

	/**
	 * This method will export the result, if the document has only one
	 * sentence. The export(style) is similar to the export-example (worksheet).
	 * If the document has more then one sentence when use
	 * {@link #multiExport(File, ArrayList, Integer, Integer)}
	 * 
	 * @param fileToSave
	 *            File to save to.
	 * @param grammsOfAllSentences
	 *            All gramms which were found in particular document. Size
	 *            simliar to 0 or 1.
	 * @param kgramms
	 *            Number k-gramm
	 * @param ngramms
	 *            Number n-gramm.
	 */
	private static void singleExport(File fileToSave, ArrayList<List<String>> grammsOfAllSentences, Integer kgramms,
			Integer ngramms) {
		Formatter formatter = new Formatter();

		int counter = 0;
		for (List<String> list : grammsOfAllSentences) {
			counter = list.size();
			for (String gramm : list) {
				Writer.addContentToFile(fileToSave, gramm);
			}
			Writer.addContentToFile(fileToSave, SymboleClazz.CONTENT_MYEND);
		}
		String endMessage = formatter.format(" %d %d-skip %d-Gramms  found.", counter, kgramms, ngramms).toString();
		Writer.addContentToFile(fileToSave, endMessage);
		formatter.close();
	}

	/**
	 * This method will export the result in a proper way. GET best results, if
	 * the result has more then 1 sentence.
	 * 
	 * @param fileToSave
	 *            File to save to.
	 * @param grammsOfAllSentences
	 *            All gramms which were found in particular document.
	 * @param kgramms
	 *            Number k-gramm
	 * @param ngramms
	 *            Number n-gramm.
	 */
	private static void multiExport(File fileToSave, ArrayList<List<String>> grammsOfAllSentences, Integer kgramms,
			Integer ngramms) {
		int totalNumberOfGrammsInDocument = 0;
		for (int i = 0; i < grammsOfAllSentences.size(); i++) {
			Formatter formatter = new Formatter();
			List<String> sentenceList = grammsOfAllSentences.get(i);
			StringBuilder stringBuilder = new StringBuilder();

			int sentenceNumber = i + 1;
			stringBuilder.append("sentence " + sentenceNumber + ": ");
			stringBuilder.append(sentenceList.toString());
			stringBuilder.append(System.lineSeparator());

			stringBuilder.append("sentence " + sentenceNumber + " (info) :");
			String endMessage = formatter.format(" %d %d-skip %d-Gramms  found.", sentenceList.size(), kgramms, ngramms)
					.toString();

			stringBuilder.append(endMessage + System.lineSeparator());
			stringBuilder.append(SymboleClazz.CONTENT_MYEND + System.lineSeparator());
			totalNumberOfGrammsInDocument += sentenceList.size();
			Writer.addContentToFile(fileToSave, stringBuilder.toString());
			formatter.close();
		}
		Formatter formatter = new Formatter();
		String endLine = formatter.format("Total number in document: %d %d-skip %d-Gramms found.",
				totalNumberOfGrammsInDocument, kgramms, ngramms).toString();
		formatter.close();

		Writer.addContentToFile(fileToSave, SymboleClazz.CONTENT_MYEND);
		Writer.addContentToFile(fileToSave, SymboleClazz.CONTENT_MYEND);
		Writer.addContentToFile(fileToSave, endLine);
	}

	/**
	 * Generates the n-gramms
	 * 
	 * @param item
	 *            Tokenz to build the n-gramms.
	 * @param indexList
	 *            Index list.
	 * @return List of n-gramms.
	 */
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

	/**
	 * Generates all need sublists for a particular sentence. Sublists generated
	 * is det. by a specific window-size. (Tokenizing the sentce
	 * 
	 * @param ngramms
	 *            Size of n-gramm window.
	 * 
	 * @param kgramms
	 *            Size of k-gramm window.
	 * @param sentence
	 *            The particular sentence
	 * @return The sentence tokenized by window-length.
	 */
	private static ArrayList<List<String>> generateSublists(Integer ngramms, Integer kgramms,
			ArrayList<String> sentence) {

		// Make every word unique. (Because Hashmap)
		ArrayList<String> uniqueWords = new ArrayList<>();
		for (int i = 0; i < sentence.size(); i++) {
			String word = sentence.get(i);
			String newWord = word + SymboleClazz.SPECIAL_DELIM + i;
			uniqueWords.add(newWord);
		}

		// The result-list generated by the unique-word-list.
		ArrayList<List<String>> resultList = new ArrayList<>();
		for (int i = ngramms; i <= ngramms + kgramms; i++) {
			ArrayList<List<String>> tempList = new ArrayList<>();
			for (int j = 0; j <= uniqueWords.size() - i; j++) {
				List<String> subList = uniqueWords.subList(j, j + i);
				tempList.add(subList);
			}
			resultList.addAll(tempList);
		}
		System.out.println("ResultList: " + resultList);
		return resultList;
	}

	/**
	 * Checks if the amount of the given input matches the requirements. If okay
	 * then pass else print usage() and terminate program with exit-code 2.
	 */
	private static void validateInput() {
		if (arg.length < 4) {
			SystemMessage.eMessage("More input is needed");
			System.out.println();
			System.out.println("GIVEN: ");
			for (int i = 0; i < arg.length; i++) {
				System.out.printf("Argument %d: %s", i, arg[i]);
				System.out.println();
			}
			usage();
			System.exit(2);
		}
	}

	/**
	 * Checks if the input is correctly.
	 * 
	 * @param checkArg
	 *            The input.
	 */
	private static void check(String[] checkArg) {

		// CHECK first Argument
		File loadFile = new File(checkArg[0]);
		if (!loadFile.exists()) {
			SystemMessage.eMessage("File couldn't be found. Given path: <" + loadFile.getAbsolutePath() + ">");
			SystemMessage.eMessage("Execution will stop.");
			usage();
			System.exit(2);
		}

		// CHECK 2 Argument
		File saveFile = new File(checkArg[1]);
		if (saveFile.isDirectory()) {
			SystemMessage.eMessage("File is directory. Given path: <" + loadFile.getAbsolutePath() + ">");
			SystemMessage.eMessage("Execution will stop.");
			usage();
			System.exit(2);
		} else if (saveFile.exists()) {
			SystemMessage.wMessage(
					"File already exits. File will be replaced. Given path: <" + loadFile.getAbsolutePath() + ">");
		}

		// CHECK 3 Argument
		if (Checker.isNumeric(checkArg[2])) {
			int parseInt = Integer.parseInt(checkArg[2]);
			if (!(parseInt >= 0)) {
				SystemMessage.eMessage("Number for k must be biggern then 0. Given :<" + checkArg[2] + ">");
				SystemMessage.eMessage("Execution will stop.");
				System.exit(2);

			}
		} else {
			SystemMessage.eMessage("Parameter for k must be a number. Given :<" + checkArg[2] + ">");
			SystemMessage.eMessage("Execution will stop.");
			System.exit(2);

		}
		// CHECK 4 Argument
		if (Checker.isNumeric(checkArg[3])) {
			int n = Integer.parseInt(checkArg[3]);
			if (!(n > 0)) {
				SystemMessage.eMessage("Number for n must be equal or biggern then 0. Given :<" + checkArg[3] + ">");
				SystemMessage.eMessage("Execution will stop.");
				System.exit(2);
			}
		} else {
			SystemMessage.eMessage("Number for n must be a number. Given :<" + checkArg[3] + ">");
			SystemMessage.eMessage("Execution will stop.");
			System.exit(2);

		}
	}

	/**
	 * Prints usage
	 */
	private static void usage() {
		System.out.println("---------- Usage ----------");
		System.out.println("java -jar <name of jar>.jar <Input-TEI> <Output-File> <k> <n> ");
	}
}
