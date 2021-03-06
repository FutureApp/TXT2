package ue2.ass;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import ue1.kSkipN.newpack.TeiP5;
import ue1.kSkipN.newpack.TeiP5Loader;
import xgeneral.modules.SymboleClazz;
import xgeneral.modules.SystemMessage;

public class UE_MainProcess {
	int wCounter = 0;
	int uniqueWords = 0;

	/**
	 * Reads the Tei file
	 * @param pathTotp5FileLocation Location of the file as string.
	 * @return The TeiP5 object.
	 */
	public TeiP5 readFile(String pathTotp5FileLocation) {
		System.out.println("Start loading");
		File tp5File = new File(pathTotp5FileLocation);
		TeiP5 loadTei5Document = TeiP5Loader.loadTei5Document(tp5File);
		System.out.println("Loading successful");
		return loadTei5Document;
	}

	/**
	 * Abstracts all para of the file
	 * @param file file where to extract fromm
	 * @return the paras.
	 */
	public NodeList abstractsParagraphs(TeiP5 file) {
		NodeList paragraphs = file.getDocument().getElementsByTagName("p");
		return paragraphs;
	}

	/**
	 * Calcs the neva combination
	 * @param wordsInPara the paragraphs
	 * @return the neva com.
	 */
	private ArrayList<ArrayList<String>> abstarctsCombinationOfNEVA(ArrayList<ArrayList<Node>> wordsInPara) {
		int bcounter = 0;
		ArrayList<ArrayList<String>> result = new ArrayList<>();
		for (int i = 0; i < wordsInPara.size(); i++) {
			System.out.println("in");

			ArrayList<Node> para = wordsInPara.get(i);
			System.out.println(para.size());
			ArrayList<String> partialResult = new ArrayList<>();
			for (int j = 0; j < para.size(); j++) {
				System.out.println("in2");
				Node word = para.get(j);
				NamedNodeMap attOfWord = word.getAttributes();
				String lemmaOfWord = attOfWord.getNamedItem("lemma").getNodeValue();
				String typeOfWord = attOfWord.getNamedItem("type").getNodeValue();

				String keyWord = lemmaOfWord.toLowerCase() + SymboleClazz.SPECIAL_UNDERSYM + typeOfWord.toLowerCase();

				if (neededLemmaBasedNEVA(typeOfWord)) {
					partialResult.add(keyWord);
				}

				System.out.println(i + " " + j + ":" + keyWord);
			}
			// DEV STOP
			if (bcounter == -2)
				break;
			else
				bcounter++;

			result.add(partialResult);
		}
		return result;
	}

	/**
	 * Filter if neva conform
	 * @param typeOfWord POS
	 * @return true if conform.
	 */
	private Boolean neededLemmaBasedNEVA(String typeOfWord) {
		Boolean okay = false;
		String partLow = typeOfWord.toLowerCase();

		if (partLow.startsWith("v")) {
			okay = true;
		} else if (partLow.startsWith("nn")) {
			okay = true;
		} else if (partLow.startsWith("ne")) {
			okay = true;
		} else if (partLow.startsWith("adj")) {
			okay = true;
			// not needed
		} else {
			okay = false;
		}
		return okay;
	}

	/**
	 * Abstracs the AWF conforms
	 * @param wordsInPara the specific word
	 * @return the AWF
	 */
	private ArrayList<ArrayList<String>> abstarctsCombinationOfAWF(ArrayList<ArrayList<Node>> wordsInPara) {
		int bcounter = 0;
		ArrayList<ArrayList<String>> result = new ArrayList<>();
		for (int i = 0; i < wordsInPara.size(); i++) {
			System.out.println("in");

			ArrayList<Node> para = wordsInPara.get(i);
			System.out.println(para.size());
			ArrayList<String> partialResult = new ArrayList<>();
			for (int j = 0; j < para.size(); j++) {
				System.out.println("in2");
				Node word = para.get(j);
				NamedNodeMap attOfWord = word.getAttributes();
				String lemmaOfWord = attOfWord.getNamedItem("lemma").getNodeValue();
				String typeOfWord = attOfWord.getNamedItem("type").getNodeValue();

				String keyWord = lemmaOfWord.toLowerCase() + SymboleClazz.SPECIAL_UNDERSYM + typeOfWord.toLowerCase();
				partialResult.add(keyWord);
				System.out.println(i + " " + j + ":" + keyWord);
			}
			// DEV STOP
			if (bcounter == -2)
				break;
			else
				bcounter++;

			result.add(partialResult);
		}
		return result;
	}

	/**
	 * The abstractor
	 * @param teiP5 the doc
	 * @param highestTagOfClustering highest cluster (p)
	 * @param smallestTagOfClustering smallest cluster (w)
	 * @return
	 */
	public ArrayList<ArrayList<Node>> abstractsNeededInfos(TeiP5 teiP5, String highestTagOfClustering,
			String smallestTagOfClustering) {
		ArrayList<ArrayList<Node>> docResult = new ArrayList<>();
		NodeList hCluster = teiP5.getDocument().getElementsByTagName("p");

		for (int i = 0; i < hCluster.getLength(); i++) {
			Node paragraphs = hCluster.item(i);
			ArrayList<Node> abstractsW = abstractsW(new ArrayList<>(), paragraphs, smallestTagOfClustering);
			docResult.add(abstractsW);
		}
		System.out.println("hCluster " + hCluster.getLength());
		System.out.println("abstractsW " + docResult.size());
		System.out.println("wCounter " + wCounter);
		System.out.println("END");
		return docResult;
	}

	/**
	 * Abstracts all Nodes with special-tag name recursively.
	 * 
	 * @param result
	 *            List containing all nodes with special-tag.
	 * @param paragraphs
	 *            The highest common-root element to cluster.
	 * @param smallesTag
	 *            The tag of smallest Clustering
	 * @return List of nodes, which matches the required special-tag.
	 */
	private ArrayList<Node> abstractsW(ArrayList<Node> result, Node paragraphs, String smallesTag) {

		if (paragraphs.getNodeName().equals(smallesTag)) {
			result.add(paragraphs);
			wCounter++;
		}
		if (paragraphs.hasChildNodes()) {
			NodeList childNodes = paragraphs.getChildNodes();
			for (int i = 0; i < childNodes.getLength(); i++) {
				Node item = childNodes.item(i);
				abstractsW(result, item, smallesTag);
			}
		}
		return result;
	}

	/**
	 * Generates the entry based on.
	 * @param wordsInPara POS
	 * @param condition Con
	 * @return
	 */
	public ArrayList<ArrayList<String>> generateEntry(ArrayList<ArrayList<Node>> wordsInPara, int condition) {
		ArrayList<ArrayList<String>> result = new ArrayList<>();
		switch (condition) {
		case 0:
			result = abstarctsCombinationOfAWF(wordsInPara);
			break;
		case 1:
			result = abstarctsCombinationOfNEVA(wordsInPara);
			break;
		case 2:
			result = abstarctsCombinationOfNNNE(wordsInPara);
			break;
		default:
			break;
		}
		System.out.println(result);
		return result;
	}

	/**
	 * Abs. the NNNE 
	 * @param wordsInPara word in para
	 * @return 
	 */
	private ArrayList<ArrayList<String>> abstarctsCombinationOfNNNE(ArrayList<ArrayList<Node>> wordsInPara) {
		int bcounter = 0;
		ArrayList<ArrayList<String>> result = new ArrayList<>();
		for (int i = 0; i < wordsInPara.size(); i++) {
			System.out.println("in");

			ArrayList<Node> para = wordsInPara.get(i);
			System.out.println(para.size());
			ArrayList<String> partialResult = new ArrayList<>();
			for (int j = 0; j < para.size(); j++) {
				System.out.println("in2");
				Node word = para.get(j);
				NamedNodeMap attOfWord = word.getAttributes();
				String lemmaOfWord = attOfWord.getNamedItem("lemma").getNodeValue();
				String typeOfWord = attOfWord.getNamedItem("type").getNodeValue();

				String keyWord = lemmaOfWord.toLowerCase() + SymboleClazz.SPECIAL_UNDERSYM + typeOfWord.toLowerCase();

				if (neededLemmaBasedNNNE(typeOfWord)) {
					partialResult.add(keyWord);
				}

				System.out.println(i + " " + j + ":" + keyWord);
			}
			// DEV STOP
			if (bcounter == -2)
				break;
			else
				bcounter++;

			result.add(partialResult);
		}
		return result;
	}

	/**
	 * Filter for nnne
	 * @param typeOfWord the POS 
	 * @return true if nnne conform
	 */
	private boolean neededLemmaBasedNNNE(String typeOfWord) {
		Boolean okay = false;
		String partLow = typeOfWord.toLowerCase();
		 if (partLow.startsWith("nn")) {
			okay = true;
		} else if (partLow.startsWith("ne")) {
			okay = true;
		} else {
			okay = false;
		}
		return okay;
	}

	/**
	 * Generate the hash entrys
	 * @param entrysOfParagraphs
	 * @return
	 */
	public HashMap<String, Integer> generateEntryHash(ArrayList<ArrayList<String>> entrysOfParagraphs) {
		HashMap<String, Integer> result = new HashMap<>();
		int posIndex = 0;
		for (ArrayList<String> paragraphs : entrysOfParagraphs) {
			for (String key : paragraphs) {
				if (result.containsKey(key)) {
				} else {
					result.put(key, posIndex);
					uniqueWords++;
					posIndex++;
				}
			}
		}
		return result;
	}
}
