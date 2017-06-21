package ue2.xxx;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;

import javax.swing.text.AbstractDocument.LeafElement;

import xgeneral.modules.MyoWriter;
import xgeneral.modules.SystemMessage;

public class ARFF_Exporter {

	HashMap<String, Integer> headersByKey;
	TreeMap<Integer, String> headersByIndex;
	Integer listSize;

	ArrayList<ArrayList<Boolean>> dataList = new ArrayList<>();

	public ARFF_Exporter(HashMap<String, Integer> headersByKey) {
		super();
		this.headersByKey = headersByKey;
		this.headersByIndex = generateHeaderyByIndex(headersByKey);
		this.listSize = headersByKey.size();
	}

	private TreeMap<Integer, String> generateHeaderyByIndex(HashMap<String, Integer> headersByKey2) {
		TreeMap<Integer, String> result = new TreeMap<Integer, String>();
		headersByKey2.forEach((k, v) -> {
			result.put(v, k);
		});
		return result;
	}

	public ArrayList<Boolean> attachDataToList(ArrayList<String> paragraph) {

		ArrayList<Boolean> result = initArrayAllFalse();
		HashMap<String, Integer> paraAsHash = genreateParagraphHash(paragraph);
		paraAsHash.forEach((k, v) -> {
			if (headersByKey.containsKey(k)) {
				Integer posIndexOfEntry = headersByKey.get(k);
				result.set(posIndexOfEntry, true);

			} else {
				SystemMessage.eMessage("Something at x213 went wrong");
				System.exit(1);
			}
		});
		dataList.add(result);
		System.out.println(result);
		return result;
	}

	private ArrayList<Boolean> initArrayAllFalse() {
		ArrayList<Boolean> result = new ArrayList<>();
		for (int i = 0; i < listSize; i++) {
			result.add(false);
		}
		return result;
	}

	private HashMap<String, Integer> genreateParagraphHash(ArrayList<String> paragraph) {
		HashMap<String, Integer> result = new HashMap<>();
		for (String key : paragraph) {
			if (result.containsKey(key)) {
				result.put(key, result.get(key) + 1);
			} else {
				result.put(key, 1);
			}
		}
		return result;
	}

	public String exportAttributesToString() {
		StringBuilder builder = new StringBuilder();
		headersByIndex.forEach((posInList, lemmaPlusPart) -> {
			if (lemmaPlusPart.contains("'")) {
				System.out.println("Warning <'>");
			}
			builder.append("@attribute ");
			builder.append("'");
			builder.append(lemmaPlusPart);
			builder.append("'");
			builder.append(" { t}");
			builder.append(System.lineSeparator());
		});
		return builder.toString();
	}

	public String exportDatalistToString() {
		String resString = "";
		StringBuilder result = new StringBuilder();
		StringBuilder parRes = new StringBuilder();
		System.out.println("Data-List " + dataList.size());
		result.append("@data" + System.lineSeparator());
		for (int i = 0; i < dataList.size(); i++) {
			parRes = new StringBuilder();
			ArrayList<Boolean> dataListEntry = dataList.get(i);
			for (Boolean dataBool : dataListEntry) {
				if (dataBool)
					parRes.append(",t");
				else
					parRes.append(",?");
			}

			result.append(parRes.subSequence(1, parRes.length()) + System.lineSeparator());
			String ratioTVsF = ratioTVsF(dataListEntry);
			System.out.println(i + " " + dataListEntry.size() + " " + ratioTVsF);
		}
		// result.append("@data");
		// result.append(System.lineSeparator());
		// String resString = "";
		// for (int i = 0; i < dataList.size(); i++) {
		// ArrayList<Boolean> paraBolList = dataList.get(i);
		// String partResult = "" + i +" ";
		// for (int j = 0; j < paraBolList.size(); j++) {
		// Boolean item = paraBolList.get(j);
		// if (item == false)
		// partResult += " ?,";
		// else
		// partResult += " t,";
		// }
		// partResult = partResult.substring(1, partResult.length() - 1);
		// System.out.println(paraBolList);
		// System.out.println(partResult);
		// try {
		// Thread.sleep(3000);
		// } catch (InterruptedException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// resString += partResult +System.lineSeparator();
		// }
		// for (int i = 0; i < dataList.size(); i++) {
		// ArrayList<Boolean> paraBolList = dataList.get(i);
		// StringBuilder partResult = new StringBuilder(" " + i + " " +
		// dataList.get(i).size());
		// for (Boolean item : paraBolList) {
		// if (item == false)
		// partResult.append(" ?,");
		// else
		// partResult.append(" t,");
		// }
		// result.append(partResult.subSequence(1, partResult.length() - 1) +
		// System.lineSeparator());
		// System.out.println(dataList.get(i));
		// System.out.println(partResult);
		// }
		resString = result.toString();
		return resString;

	}

	private String ratioTVsF(ArrayList<Boolean> dataListEntry) {
		String result = "";
		int t = 0;
		int notT = 0;
		for (Boolean boolean1 : dataListEntry) {
			if (boolean1)
				t++;
			else
				notT++;
		}
		result = "True: " + t + " " + "notTrue: " + notT;
		return result;
	}

	public void exportMeToFile(String pathOfFile) {
		MyoWriter.writeToLog(pathOfFile, "@relation myTest");
		MyoWriter.addToLog(pathOfFile, exportAttributesToString()  + exportDatalistToString());
	}
}
