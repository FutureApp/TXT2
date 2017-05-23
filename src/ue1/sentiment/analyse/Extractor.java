package ue1.sentiment.analyse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import xgeneral.modules.SymboleClazz.SentiLabel;

public class Extractor {

	/**
	 * Converts a line of an document into a specific label if possible. By default the method will return the unkown label
	 * {@see #SentiLabel}
	 * @param line The line.
	 * @return Label if possible. At least the unkown-label.
	 */
	public static SentiLabel getLabel(String line) {
		String[] split = line.split(" ");
		SentiLabel label = SentiLabel.unknown;
		if (split.length >= 2) {
			label = Labler.detLabel(split[0]);
		} else {

		}
		return label;
	}

	/**
	 * Det the polarity of a sentence. This is done by det the polarity of a single word.
	 * @param line Line which contains the sentence which should be investigated.
	 * @param sentiList The lexicon to look up polaritys.
	 * @return Value between -1 and 1, which describes the polarity of the sentence.
	 */
	public static double getSentencePolarity(String line, SentiWordNetDemo sentiList) {
		String[] split = line.split(" ");
		double counter = 0;
		double polaritySum = 0;
		double resultPolarity = 0;
		List<String> asList = Arrays.asList(split).subList(1, split.length);
		ArrayList<Double> polarityList = new ArrayList<>();

		if (split.length >= 2) {

//			System.out.println("Original sentence " + asList);
			for (int i = 0; i < asList.size(); i++) {
				String word = asList.get(i);
				Double wordPolarity = getWordPolarity(word, sentiList);
//				System.out.println("IN: " + word + " Out" + wordPolarity);
				polaritySum += wordPolarity;
				polarityList.add(wordPolarity);
//				System.out.println("Add  " + wordPolarity);
				counter++;
			}
//			System.out.println("-----");

		} else {
			System.out.println("Error");
			System.exit(1);
		}

		if (counter >= 1)
			resultPolarity = polaritySum / counter;
		else
			resultPolarity = 0;

//		System.out.println("Real Array" + asList);
//		System.out.println("Polarity Array" + polarityList);
//		System.out.println("Polartiy Sum " + polaritySum);
//		System.out.println("Polartiy  " + resultPolarity);
		return resultPolarity;
	}

	/**
	 * Returns the polarity of a word. This is done by det. the polarity over all word@PoS which could be looked up at the lexicon.
	 * @param word The word, itself
	 * @param sentiList The lexicon
	 * @return The polarity of the word.
	 */
	private static Double getWordPolarity(String word, SentiWordNetDemo sentiList) {
		String cleanWord = word.replaceAll("[^a-zA-Z0-9]+", "");
		Double polarityOfCleanWordOverAllPOS = sentiList.extractOverAllPolarity(cleanWord);
		return polarityOfCleanWordOverAllPOS;
	}
}
