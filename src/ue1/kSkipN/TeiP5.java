package ue1.kSkipN;

import org.w3c.dom.Document;

public class TeiP5 {

	Document content;

	public TeiP5(Document content) {
		super();
		this.content = content;
		System.out.println(content.getDocumentElement().getNodeName());
	}

	public void process() {

	}

}
