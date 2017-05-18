package ue1.kSkipN.old;

import java.util.ArrayList;
import java.util.List;

public class IndexListGenerator {

	int n, k;
	ArrayList<ArrayList<Integer>> indexList;

	public IndexListGenerator(int n, int k) {
		super();
		this.n = n;
		this.k = k;
		indexList = generateIndexList(n, k);
	}

	public ArrayList<ArrayList<String>> getGrammByIndexList(List<String> elements) {
		ArrayList<ArrayList<String>> result = new ArrayList<>();

		for (ArrayList<Integer> iList : indexList) {
			ArrayList<String> partialResult = new ArrayList<>();
			for (int i = 0; i < iList.size(); i++) {
				partialResult.add(elements.get(iList.get(i)));
			}
			result.add(partialResult);
		}

		return result;

	}

	private static ArrayList<ArrayList<Integer>> generateIndexList(int n, int k) {
		int startNumber = generateStartNumber(n);
		int endNumber = generateEndNumber(n, k);

		ArrayList<ArrayList<Integer>> list = new ArrayList<>();

		for (int number = startNumber; number <= endNumber; number++) {
			ArrayList<Integer> indexList = generateList(number);
			if (indexList.size() == n)
				list.add(indexList);
		}
		return list;
	}

	private static ArrayList<Integer> generateList(int number) {
		ArrayList<Integer> result = new ArrayList<>();
		String valueAsString = Integer.toBinaryString(number);

		for (int i = 0; i < valueAsString.length(); i++) {
			if (valueAsString.charAt(i) == '1')
				result.add(i);
		}
		return result;
	}

	private static int generateEndNumber(int n, int k) {
		String result = generateX_1(n);
		for (int i = n; i < (n + k); i++) {
			result += "0";
		}
		return Integer.parseInt(result, 2);
	}

	private static int generateStartNumber(int n) {
		String ones = generateX_1(n);

		return Integer.parseInt(ones, 2);
	}

	private static String generateX_1(int x) {
		String ones = "";
		for (int i = 0; i < x; i++) {
			ones += "1";
		}
		return ones;
	}

	public void genereatekSkipNGramm(ArrayList<String> sentence) {
		ArrayList<ArrayList<String>> result = new ArrayList<>();
		int maxWindowSize = n + k;

		splitSentennceByWindowSize(maxWindowSize, sentence);

		// for (int i = minWindowSize; i < maxWindowSize + 1; i++) {
		// for (int x = 0; x < sentence.size() - i + 1; x++) {
		// List<String> subList = sentence.subList(x, x + minWindowSize);
		// ArrayList<ArrayList<String>> grammByIndexList =
		// getGrammByIndexList(subList);
		// System.out.print(subList + " " + grammByIndexList);
		// System.out.println();
		// }
		// }

		System.out.println(result + " " + result.size());

	}

	private ArrayList<List<String>> splitSentennceByWindowSize(int actSize, ArrayList<String> sentence) {
		ArrayList<List<String>> semiList = new ArrayList<>();
		for (int i = 0; i <= sentence.size() - actSize; i++) {
			List<String> subList = sentence.subList(i, i + actSize);
			semiList.add(subList);
		}
		return semiList;
	}
}
