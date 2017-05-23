package ue1.similarity.analyse;

import java.util.HashMap;
import java.util.Map.Entry;

public class DocumentSignatureGramm {
	HashMap<String, Integer> grammMap;
	String documentName;
	Integer size;

	/**
	 * Constructor.
	 * @param grammMap  Abstracted gramms of a document.
	 * @param documentName  The name of the document.
	 */
	public DocumentSignatureGramm(HashMap<String, Integer> grammMap, String documentName) {
		super();
		this.grammMap = grammMap;
		this.documentName = documentName;
		this.size = 0;
	}

	/**
	 * Returns the signature as a string.
	 * @return The signature as string.
	 */
	public String signatureToString() {
		StringBuilder builder = new StringBuilder();
		for (Entry<String, Integer> elem : grammMap.entrySet()) {
			String key = elem.getKey();
			Integer value = elem.getValue();
			builder.append(key + " " + value + System.lineSeparator());
		}
		return builder.toString();
	}

	/**
	 * Adds a gramm to the pool
	 * @param gramm A specific gramm.
	 */
	public void addGramm(String gramm) {
		if (grammMap.containsKey(gramm)) {
			grammMap.put(gramm, (grammMap.get(gramm) + 1));
		} else {
			grammMap.put(gramm, 1);
		}
		size++;

	}

}
