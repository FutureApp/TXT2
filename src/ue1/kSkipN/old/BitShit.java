package ue1.kSkipN.old;

import java.util.ArrayList;
import java.util.HashMap;

public class BitShit {

	public static ArrayList<ArrayList<Integer>> generateIndexList(int n, int k) {
		int startNumber = generateStartNumber(n);
		int endNumber = generateEndNumber(n, k);
		return generateIndexLists(startNumber, endNumber, n);
	}

	public static HashMap<String, ArrayList<ArrayList<Integer>>> generateNeededIndexList(int n, int k) {
		HashMap<String, ArrayList<ArrayList<Integer>>> result = new HashMap<>();
		for (int i = 0; i <= k; i++) {
			ArrayList<ArrayList<Integer>> indexList = generateIndexList(n, i);
			String key = n + "," + i;
			result.put(key, indexList);
			System.out.println("PUT: "+key +" "+indexList);
			
		}
		return result;
	}

	private static ArrayList<ArrayList<Integer>> generateIndexLists(int startNumber, int endNumber, int n) {
		ArrayList<ArrayList<Integer>> result = new ArrayList<>();
		for (int i = startNumber; i <= endNumber; i++) {
			String reverse = new StringBuilder(Integer.toBinaryString(i)).reverse().toString();
			ArrayList<Integer> tempResult = new ArrayList<>();

			for (int j = 0; j < reverse.length(); j++) {
				if (reverse.charAt(j) == '1')
					tempResult.add(j);

			}
			if (n == tempResult.size())
				result.add(tempResult);
		}
		return result;
	}

	private static ArrayList<ArrayList<Integer>> generateIndexList(int startNumber, int endNumber, int n) {
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
		String valueAsString = new StringBuilder(Integer.toBinaryString(number)).reverse().toString();
		System.out.println("String " + valueAsString);
		for (int i = 0; i < valueAsString.length(); i++) {
			if (valueAsString.charAt(i) == '1')
				;
			result.add(i);
		}
		return result;
	}

	private static String returnXAsString(int number) {
		return Integer.toBinaryString(number);
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

	// private static int calcStartIndex(int n) {
	// String ones = "";
	// for (int i = 0; i < n; i++) {
	// ones+="1";
	// }
	// return Integer.parseInt(ones,2);
	// }
	// private static String returnXOnes(int x) {
	// String ones = "";
	// for (int i = 0; i < x; i++) {
	// ones+="1";
	// }
	// return ones;
	// }
	//
	// private static int calcEndIndex(int numberOf1,int numberOfBits) {
	// String ones = returnXOnes(numberOf1);
	// for (int i = numberOf1; i < n; i++) {
	// ones+="1";
	// }
	// return Integer.parseInt(ones,2);
	// }
}
