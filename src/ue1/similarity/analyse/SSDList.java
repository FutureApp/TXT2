package ue1.similarity.analyse;

import java.util.ArrayList;

public class SSDList {

	ArrayList<SSDTupele> list;
	String colHeader1, colHeader2, colHeader3;

	public SSDList(ArrayList<SSDTupele> list, String colHeader1, String colHeader2, String colHeader3) {
		super();
		this.list = list;
		this.colHeader1 = colHeader1;
		this.colHeader2 = colHeader2;
		this.colHeader3 = colHeader3;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(colHeader1 + "|" + colHeader2 + "|" + colHeader3);
		builder.append(System.lineSeparator());
		for (SSDTupele ssdTupele : list) {
			builder.append(ssdTupele.toStringForList() + System.lineSeparator());
		}

		return builder.toString();
	}

}
