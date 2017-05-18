package ue1.kSkipN.old;

import java.util.ArrayList;

public class BitShit2 {

	public static void main(String[] args) {

		int n = 2;
		int k = 1;

		int startNumber = generateStartNumber(n);
		int endNumber = generateEndNumber(n, k);

		System.out.println("Start " + startNumber);
		System.out.println("End " + endNumber);

		System.out.println("Start as: " + returnXAsString(startNumber));
		System.out.println("End as: " + returnXAsString(endNumber));

		ArrayList<ArrayList<Integer>> indexList = generateIndexList(startNumber, endNumber, n);
		System.out.println(indexList);
	}

	private static ArrayList<ArrayList<Integer>> generateIndexList(int startNumber, int endNumber, int n) {
		ArrayList<ArrayList<Integer>> list = new ArrayList<>();

		for (int number = startNumber; number <= endNumber; number++) {

			ArrayList<Integer> indexList = generateList(number);
			if(indexList.size()==n)
				list.add(indexList);
		}
		return list;
	}

	private static ArrayList<Integer> generateList(int number) {
		ArrayList<Integer> result = new ArrayList<>();
		String valueAsString = new StringBuilder(Integer.toBinaryString(number)).reverse().toString() ;
		System.out.println("String "+valueAsString);
		for (int i = 0; i < valueAsString.length(); i++) {
			if (valueAsString.charAt(i) == '1');
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
