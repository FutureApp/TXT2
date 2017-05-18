package ue1.kSkipN.newpack;

import java.util.ArrayList;
import java.util.HashMap;

public class BitShift {

	/**
	 * Generates a specific index-list.
	 * 
	 * @param n
	 *            n-gramm size
	 * @param k
	 *            Value of skip k
	 * @return Index-list.
	 */
	public static ArrayList<ArrayList<Integer>> generateIndexList(int n, int k) {
		int startNumber = generateStartNumber(n);
		int endNumber = generateEndNumber(n, k);
		return generateIndexLists(startNumber, endNumber, n);
	}

	/**
	 * Generates the look-up-hashmap with the following structure: [ key ||
	 * value ] -> [n,k || [[index0,index1,...],[index01,index02,...],...]
	 * 
	 * @param n
	 *            n-gramm size.
	 * @param k
	 *            k-skip value.
	 * @return Table containing look up-hashes.
	 */
	public static HashMap<String, ArrayList<ArrayList<Integer>>> generateNeededIndexList(int n, int k) {
		HashMap<String, ArrayList<ArrayList<Integer>>> result = new HashMap<>();
		for (int i = 0; i <= k; i++) {
			ArrayList<ArrayList<Integer>> indexList = generateIndexList(n, i);
			String key = n + "," + i;
			result.put(key, indexList);
			System.out.println("PUT: " + key + " " + indexList);

		}
		return result;
	}

	/**
	 * Generates the look up table. Usable to look up indexes to generate
	 * n-gramms.
	 * 
	 * @param startNumber
	 *            Start-index.
	 * @param endNumber
	 *            End-index.
	 * @param n
	 *            Window-size
	 * @return List of list which contains index-values for access n-gramms.
	 */
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

	/**
	 * Calcs the value of the end-index. Used to det. the last value for
	 * calculation.
	 * 
	 * @param n
	 *            n-gramm size.
	 * @param k
	 *            Value of skip k.
	 * @return Highest possible value for the indexes.
	 */
	private static int generateEndNumber(int n, int k) {
		String result = generateX_1(n);
		for (int i = n; i < (n + k); i++) {
			result += "0";
		}
		return Integer.parseInt(result, 2);
	}

	/**
	 * Calcs the value of the first-index.
	 * 
	 * @param n
	 *            n-gramm size.
	 * @return Lowest possible value for the indexes.
	 */
	private static int generateStartNumber(int n) {
		String ones = generateX_1(n);

		return Integer.parseInt(ones, 2);
	}

	/**
	 * Generates a string containing x one's x= 4 -> '1111'
	 * 
	 * @param x
	 *            Amount of '1'
	 * @return String of x '1'
	 */
	private static String generateX_1(int x) {
		String ones = "";
		for (int i = 0; i < x; i++) {
			ones += "1";
		}
		return ones;
	}
}
