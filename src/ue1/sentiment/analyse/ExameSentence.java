package ue1.sentiment.analyse;

import java.util.ArrayList;
import java.util.List;

import xgeneral.modules.SymboleClazz;
import xgeneral.modules.SymboleClazz.SentiLabel;

public class ExameSentence {

	SymboleClazz.SentiLabel label;
	ArrayList<String> words = new ArrayList<>();

	public ExameSentence(SentiLabel label) {
		super();
		this.label = label;
	}

	/**
	 * Add a word to the sentence. Sentence is list containing words.
	 * 
	 * @param word
	 *            The word to add.
	 */
	public void addWordToSentence(String word) {
		words.add(normalize(word));
	}

	/**
	 * Normalized a specific word.
	 * 
	 * @param word
	 *            Word to normalize.
	 * @return Word which is normalized.
	 */
	private String normalize(String word) {
		return word.replaceAll("[^a-zA-Z0-9]+", "");
	}


	/**
	 * Add all words to the sentence in normalized form.
	 * 
	 * @param list
	 *            List containing the words.
	 */
	public void addAllWords(List<String> list) {
		for (String word : list) {
			addWordToSentence(word);
		}
	}

	public String getSentenceAsString() {
		StringBuffer buffer = new StringBuffer();
		for (String word : words) {
			buffer.append(word + " ");
		}
		return buffer.toString();

	}
}
