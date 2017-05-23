package ue1.similarity.analyse;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import xgeneral.modules.SymboleClazz;
import xgeneral.modules.SystemMessage;

public class AdvanceTeiP5 {

	Document content;

	/**
	 * Constructor.
	 * 
	 * @param document
	 *            The TEI-P5 document.
	 */
	public AdvanceTeiP5(Document document) {
		super();
		this.content = document;
	}

	/**
	 * Extracts all lemmas in sentence boundaries.
	 * 
	 * @return List of lemmas for a sentence.
	 */
	public ArrayList<ArrayList<String>> extractEachLemmaBySentence() {
		ArrayList<ArrayList<String>> result = new ArrayList<>();
		NodeList nList = content.getElementsByTagName("s");
		for (int temp = 0; temp < nList.getLength(); temp++) {
			NodeList childNodes = nList.item(temp).getChildNodes();
			ArrayList<String> sentence = new ArrayList<>();
			for (int i = 0; i < childNodes.getLength(); i++) {
				Node node = childNodes.item(i);
				if (node.getNodeName().equals("w") && (childNodes.item(i).getNodeType() == Node.ELEMENT_NODE)) {
					Element elem = (Element) childNodes.item(i);
					if (elem.hasAttribute("lemma"))
						sentence.add(elem.getAttribute("lemma"));
					else
						SystemMessage.eMessage("Lemma not found");
				}
			}
			result.add(sentence);
		}
		return result;
	}

	/**
	 * Extracts all lemmas in sentence boundaries.
	 * 
	 * @param myContent
	 *            The document where to abstract from.
	 * @return Sentences.
	 */
	public ArrayList<ArrayList<String>> extractEachLemmaBySentence(Document myContent) {
		ArrayList<ArrayList<String>> result = new ArrayList<>();
		NodeList nList = myContent.getElementsByTagName("s");
		for (int temp = 0; temp < nList.getLength(); temp++) {
			NodeList childNodes = nList.item(temp).getChildNodes();
			ArrayList<String> sentence = new ArrayList<>();
			for (int i = 0; i < childNodes.getLength(); i++) {
				Node node = childNodes.item(i);
				if (node.getNodeName().equals("w") && (childNodes.item(i).getNodeType() == Node.ELEMENT_NODE)) {
					Element elem = (Element) childNodes.item(i);
					if (elem.hasAttribute("lemma"))
						sentence.add(elem.getAttribute("lemma"));
					else
						SystemMessage.eMessage("Lemma not found");
				}
			}
			result.add(sentence);
		}
		return result;
	}

	/**
	 * Generates n-gramms.
	 * 
	 * @param ngramm
	 *            number of n.
	 * @param content
	 *            content where to generate the n-grams from.
	 * @return list of n-gramms.
	 */
	public List<List<String>> createNGramm(int ngramm, ArrayList<String> content) {
		List<List<String>> result = new ArrayList<>();
		if (ngramm <= 0) {
			SystemMessage.wMessage(
					"Given value for n-gramm is to low. Value for n should be higher then 0. Given <" + ngramm + ">");
			System.exit(0);
		} else {
			for (int i = 0; i < content.size(); i++) {
				int index = i + ngramm - 1;
				if (index < content.size() && index >= 0) {
					List<String> subList = (List<String>) content.subList(i, i + ngramm);
					result.add(subList);
				}
			}
		}
		System.out.println(result);
		return result;
	}

	/**
	 * Retunrs only specific elements <s-tagers> and normalize the values.
	 * @return Normalized sentences with normalized words.
	 */
	public ArrayList<ArrayList<String>> returnOnlySElementsAndNoramlize() {
		ArrayList<ArrayList<String>> result = new ArrayList<>();
		NodeList nList = content.getElementsByTagName("s");
		for (int temp = 0; temp < nList.getLength(); temp++) {
			NodeList childNodes = nList.item(temp).getChildNodes();
			ArrayList<String> sentence = new ArrayList<>();
			for (int i = 0; i < childNodes.getLength(); i++) {
				Node node = childNodes.item(i);
				if (node.getNodeName().equals("w") && (childNodes.item(i).getNodeType() == Node.ELEMENT_NODE)) {
					Element elem = (Element) childNodes.item(i);
					if (elem.hasAttribute("lemma"))
						sentence.add(elem.getAttribute("lemma"));
					else
						SystemMessage.eMessage("Lemma not found");
				}
			}
			result.add(sentence);
		}
		return result;

	}

	/**
	 * Creates the k-skip-n-gramms.
	 * @param n Gramm-size.
	 * @param k Value of skip.
	 */
	@Deprecated
	public void generatekSkipnGramm(Integer n, Integer k) {
		ArrayList<ArrayList<String>> allSen = extractEachLemmaBySentence(content);
		System.out.println(allSen);

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
}