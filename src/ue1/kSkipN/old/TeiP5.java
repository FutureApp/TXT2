package ue1.kSkipN.old;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import javax.swing.plaf.synth.SynthSpinnerUI;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import xgeneral.modules.SystemMessage;

public class TeiP5 {

	Document content;

	public TeiP5(Document content) {
		super();
		this.content = content;
	}
	

	/**
	 * Extracts sentences.
	 */
	public ArrayList<ArrayList<String>> extractS() {
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
		System.out.println(result);
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

	public List<List<String>> createKNGrammSemiWorking(int ngramm, int kgramm, ArrayList<String> content) {
		List<List<String>> result = new ArrayList<>();

		if (kgramm < 0) {
			SystemMessage.wMessage("Given k-gramm < 0.");
			System.exit(0);
		}
		
		for (int k = 0; k < kgramm + 1; k++) {
			int gramma = ngramm + k;
			List<List<String>> resulto = createNGramm(gramma, content);
			result.addAll(resulto);
			System.out.println("round "+k +" "+result);
		}
		
		System.out.println("Result ("+result.size()+")"+ result);
		return result;
	}

	
	public List<List<String>> createKNGramm(int ngramm, int kgramm, ArrayList<String> content) {
		List<List<String>> result = new ArrayList<>();

		if (kgramm < 0) {
			SystemMessage.wMessage("Given k-gramm < 0.");
			System.exit(0);
		}
		
		for (int i = ngramm + kgramm; i >= 0; i--) {
			
		}
		
		System.out.println("Result ("+result.size()+")"+ result);
		return result;
	}
	public List<List<String>>  removeSystemicEntrys(int k, String content) {
		List<List<String>> result = new ArrayList<>();
		
		
		return result;
		
	}
}
