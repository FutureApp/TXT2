package ue2.ass;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;

import xgeneral.modules.MyoWriter;
import xgeneral.modules.SystemMessage;

public class ARFF_Exporter {

	HashMap<String, Integer> headersByKey;
	TreeMap<Integer, String> headersByIndex;
	Integer listSize;

	ArrayList<ArrayList<Boolean>> dataList = new ArrayList<>();

	/**
	 * cons
	 * @param headersByKey Header by keys
	 */
	public ARFF_Exporter(HashMap<String, Integer> headersByKey) {
		super();
		this.headersByKey = headersByKey;
		this.headersByIndex = generateHeaderyByIndex(headersByKey);
		this.listSize = headersByKey.size();
	}

	/**
	 * Sorted map
	 * @param headersByKey2 hash-map to retrun sorted
	 * @return the sorted Map
	 */
	private TreeMap<Integer, String> generateHeaderyByIndex(HashMap<String, Integer> headersByKey2) {
		TreeMap<Integer, String> result = new TreeMap<Integer, String>();
		headersByKey2.forEach((k, v) -> {
			result.put(v, k);
		});
		return result;
	}

	/**
	 * Inserts new data
	 * @param paragraph paragraps-info to add
	 * @return The result.
	 */
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

	/**
	 * Inits the an false array
	 * @return The false array.
	 */
	private ArrayList<Boolean> initArrayAllFalse() {
		ArrayList<Boolean> result = new ArrayList<>();
		for (int i = 0; i < listSize; i++) {
			result.add(false);
		}
		return result;
	}

	/**
	 * Generates the Hashmap based on the paragraph info
	 * @param paragraph The pragraph
	 * @return The Result of calc.
	 */
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

	/**
	 * Exports the @att of of all att.
	 * @return the atts
	 */
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

	/**
	 * Exportes the datalist to string
	 * @return the data-list entrys
	 */
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
		resString = result.toString();
		return resString;

	}

	/**
	 * Returns the ration of ture and false
	 * @param dataListEntry The datalist where the t and f are based.
	 * @return the ratio.
	 */
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

	/**
	 * Exports the information of this class to a file.
	 * @param pathOfDir the Path to the dir where to save.
	 */
	public void exportMeToFile(String pathOfDir) {
		MyoWriter.writeToLog(pathOfDir, "@relation myTest");
		MyoWriter.addToLog(pathOfDir, exportAttributesToString() + exportDatalistToString());
	}
}
