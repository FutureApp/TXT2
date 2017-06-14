package ue2.xxx;

import java.io.File;
import java.util.ArrayList;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import scala.collection.parallel.ParIterableLike.Foreach;
import ue1.kSkipN.newpack.TeiP5;
import ue1.kSkipN.newpack.TeiP5Loader;
import xgeneral.modules.SystemMessage;

public class UE_MainProcess {
	int wCounter = 0;

	public TeiP5 readFile(String pathTotp5FileLocation) {
		File tp5File = new File(pathTotp5FileLocation);
		TeiP5 loadTei5Document = TeiP5Loader.loadTei5Document(tp5File);
		return loadTei5Document;

	}

	public NodeList abstractsParagraphs(TeiP5 file) {
		NodeList paragraphs = file.getDocument().getElementsByTagName("p");
		return paragraphs;
	}

	/**
	 * * Abstracts the required Information, which are needed for further
	 * processing. The return-value depends on 3 standard,
	 * 
	 * Cases ---->
	 * 
	 * 0: Returns the connections-result for < ALL WORDSFORMS >
	 * 
	 * 1: Returns the connections-result for < ALL N.E.V.A. >
	 * 
	 * 2: Returns the connections-result for < ALL N.E. >
	 * 
	 * @param nodeList
	 *            List of nodes.
	 * @param i
	 *            Case-number
	 */
	public void abstractsInformationsForEachParagraphs(Document doc, int i) {
		ArrayList<ArrayList<String>> result = new ArrayList<>();
		switch (i) {
		case 0:
			result = abstarctsCombinationOfAWF(null);
			break;

		default:
			break;
		}

	}

	private ArrayList<ArrayList<String>> abstarctsCombinationOfAWF(NodeList nodeList) {
		Integer breakCondition = 0;
		for (int temp = 0; temp < nodeList.getLength(); temp++) {
			Node childNode = nodeList.item(temp);
			NodeList childNodes = childNode.getChildNodes();
			for (int i = 0; i < childNodes.getLength(); i++) {
				Node node = childNodes.item(i);
				if (node.getNodeName().equals("w"))
					System.out.println("w");
			}

			if (breakCondition == 1)
				break;
			else
				breakCondition++;

		}
		return null;
	}

	public void abstractsNeededInfos(TeiP5 teiP5, String highestTagOfClustering, String smallestTagOfClustering) {
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
}
