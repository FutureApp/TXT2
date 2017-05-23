package ue1.similarity.analyse;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Formatter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import ue1.kSkipN.newpack.BitShift;
import ue1.kSkipN.newpack.TeiP5;
import ue1.kSkipN.newpack.TeiP5Loader;
import xgeneral.modules.Checker;
import xgeneral.modules.Encoding;
import xgeneral.modules.SymboleClazz;
import xgeneral.modules.SystemMessage;
import xgeneral.modules.Writer;

public class kSkipNGramm {

	int nGramm;
	int kGramm;
	String pathForLoading;
	private HashMap<String, ArrayList<ArrayList<Integer>>> indexHashMap;

	public kSkipNGramm(int nGramm, int kGramm, String pathForLoading,
			HashMap<String, ArrayList<ArrayList<Integer>>> indexHashMap) {
		super();
		this.nGramm = nGramm;
		this.kGramm = kGramm;
		this.pathForLoading = pathForLoading;
		this.indexHashMap = indexHashMap;
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
	 * @return
	 */
	public ArrayList<List<String>> executeSkipNGramm() {
		String pathToContent = pathForLoading;
		Integer ngramms = nGramm;
		Integer kgramms = kGramm;

		File fileOfContent = new File(pathToContent);
		TeiP5 teiData = TeiP5Loader.loadTei5Document(fileOfContent);
		ArrayList<ArrayList<String>> extractS = teiData.extractEachLemmaBySentence();
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
					// System.out.println("Gramm com:" + grammComponent);
					builder.append(
							grammComponent.substring(0, grammComponent.indexOf(SymboleClazz.SPECIAL_DELIM)) + ",");
				}
				builder.setCharAt(builder.length() - 1, ')');
				builder.insert(0, '(');
				// System.out.println("Builder-> " + builder);
				sentenceGrammList.add(builder.toString());
			}

			Collections.sort(sentenceGrammList);
			// Each sentence to the document-gramms.
			grammsOfAllSentences.add(sentenceGrammList);
			// Sorts the list
			// System.out.println("Size: " + sentenceGrammList.size());
			// System.out.println("----");
			// System.out.println(sentenceGramms);
			// System.out.println(grammsOfAllSentences);

		}

		String extractedFileName = extracFileName(pathForLoading);

		File fileToExport = new File("./grammResult/gramms_" + extractedFileName);
		saveResults(fileToExport, grammsOfAllSentences, kgramms, ngramms);
		return grammsOfAllSentences;
	}

	private String extracFileName(String pathToFile) {
		String fileName = new File(pathToFile).getName();

		return fileName;

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
	public void saveResults(File fileToSave, ArrayList<List<String>> grammsOfAllSentences, Integer kgramms,
			Integer ngramms) {

		Writer.removeIfNeeded(fileToSave);
		if (grammsOfAllSentences.size() == 0 || grammsOfAllSentences.size() == 1) {
			singleExport(fileToSave, grammsOfAllSentences, kgramms, ngramms);
		} else {
			multiExport(fileToSave, grammsOfAllSentences, kgramms, ngramms);
		}
		System.out.println("Export finished <" + fileToSave.getAbsolutePath() + ">");
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
	public void singleExport(File fileToSave, ArrayList<List<String>> grammsOfAllSentences, Integer kgramms,
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
	public void multiExport(File fileToSave, ArrayList<List<String>> grammsOfAllSentences, Integer kgramms,
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

		// Exporting
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
	public ArrayList<ArrayList<String>> generateNGramm(List<String> item, ArrayList<ArrayList<Integer>> indexList) {

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
	 * is det. by a specific window-size. (Tokenizing the sentence)
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
	public ArrayList<List<String>> generateSublists(Integer ngramms, Integer kgramms, ArrayList<String> sentence) {

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
	 * Abstracts the document-structure based
	 * 
	 * @param executeSkipNGramm
	 *            The n-Gramms
	 * @param fileName
	 *            Name of the file,
	 * @return The doc-signature.
	 */
	public DocumentSignatureGramm generateGrammMap(ArrayList<List<String>> executeSkipNGramm, String fileName) {
		HashMap<String, Integer> map = new HashMap<>();
		DocumentSignatureGramm docSigi = new DocumentSignatureGramm(map, fileName);

		for (List<String> list : executeSkipNGramm) {
			for (String gramm : list) {
				docSigi.addGramm(gramm);
			}
		}
		return docSigi;
	}

}