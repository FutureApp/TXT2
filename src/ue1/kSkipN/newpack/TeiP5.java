package ue1.kSkipN.newpack;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import xgeneral.modules.SystemMessage;

public class TeiP5 {

	Document content;

	/**
	 * Constructor.
	 * 
	 * @param document
	 *            The TEI-P5 document.
	 */
	public TeiP5(Document document) {
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
}