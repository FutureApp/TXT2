package ue1.similarity.analyse;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

public class MyMatrix {

	ArrayList<ArrayList<Double>> matrix;

	HashMap<Integer, String> indexByInteger;
	HashMap<String, Integer> indexByName;

	/**
	 * Constructor.
	 * @param matrix The matrix 
	 * @param index The index-specification of the matrix.
	 */
	public MyMatrix(ArrayList<ArrayList<Double>> matrix, HashMap<Integer, String> index) {
		super();
		this.matrix = matrix;
		this.indexByInteger = index;
		this.indexByName = invert(index);
	}

	/**
	 * Converts key|value map to a value|key map.
	 * 
	 * @param index
	 *            The specific map.
	 * @return The inverted map.
	 */
	private HashMap<String, Integer> invert(HashMap<Integer, String> index) {
		HashMap<String, Integer> invert = new HashMap<>();
		for (Entry<Integer, String> elem : index.entrySet()) {
			invert.put(elem.getValue(), elem.getKey());
		}
		return invert;
	}

	/**
	 * Returns the name of the file, corresponding to the index.
	 * @return The name of the index(col).
	 */
	public String indexMapByIntegerToString() {
		StringBuilder builder = new StringBuilder();
		for (Entry<Integer, String> arrayList : indexByInteger.entrySet()) {
			builder.append(arrayList.getKey() + " " + arrayList.getValue() + System.lineSeparator());
		}

		return builder.toString();

	}

	/**
	 * Returns the size of matrix.
	 * @return Size of matrix.
	 */
	public Integer size() {
		return matrix.size();
	}

	/**
	 * Returns the index by String.
	 * @return The the index det by string.
	 */
	public String indexMapByStringToInteger() {
		StringBuilder builder = new StringBuilder();
		for (Entry<String, Integer> elem : indexByName.entrySet()) {
			builder.append(elem.getKey() + " " + elem.getValue() + System.lineSeparator());
		}
		return builder.toString();

	}

	/**
	 * Returns the matrix in string representatoin.
	 * @return The Matrix as a string.
	 */
	public String matrixToString() {
		StringBuilder cols = new StringBuilder();
		cols.append("          ");

		for (int i = 0; i < matrix.size(); i++) {
			cols.append(indexByInteger.get(i) + "  ");
		}
		cols.append(System.lineSeparator());
		for (int i = 0; i < matrix.size(); i++) {
			StringBuilder rowBuilder = new StringBuilder();
			for (int j = 0; j < matrix.size(); j++) {

				Double element = getElementBy(i, j);
				String rowName = (j == 0) ? indexByInteger.get(i) : "";
				String elementAsString = new DecimalFormat("#.####").format(element);
				rowBuilder.append(rowName + " " + elementAsString + " ");
			}
			cols.append(rowBuilder + System.lineSeparator());
		}
		return cols.toString();
	}

	public double getElementBy(Integer x, Integer y) {
		return matrix.get(x).get(y);
	}

	public double setElementBy(Integer x, Integer y, Double newValue) {
		return matrix.get(x).set(y, newValue);
	}
}
