package ue1.sentiment.analyse;

import xgeneral.modules.SymboleClazz.SentiLabel;

public class FourFieldTable {

	double tp, fn, fp, tn = 0;
	SentiLabel category;

	public FourFieldTable(SentiLabel category) {
		super();
		this.category = category;
	}

	/**
	 * Add the required items to the object.
	 * 
	 * @param trueValue
	 *            Label which is given by Test-data.
	 * @param experimentValue
	 *            Label which is given by our calc.
	 */
	public void add(SentiLabel trueValue, SentiLabel experimentValue) {
		if (category.compareTo(trueValue) == 0) {
			if (category.compareTo(experimentValue) == 0) {
				tp++;
				// System.out.println(category + " " + trueValue + " " +
				// experimentValue + " tp|" + tp);
			} else {
				fn++;
				// System.out.println(category + " " + trueValue + " " +
				// experimentValue + " fn|" + fn);
			}
		} else {
			if (category.compareTo(experimentValue) == 0) {
				fp++;
				// System.out.println(category + " " + trueValue + " " +
				// experimentValue + " fp|" + fp);

			} else {
				tn++;
				// System.out.println(category + " " + trueValue + " " +
				// experimentValue + " tn|" + tn);
			}
		}
	}

	/**
	 * Returns the total-value
	 * 
	 * @return Value of total.
	 */
	public double getTotal() {
		return tp + fn + fp + tn;
	}

	/**
	 * Returns the precision.
	 * 
	 * @return Precision of this 4-field-table.
	 */
	public double getPrecision() {
		double precision = tp / (tp + fp);
		return precision;
	}

	/**
	 * Returns the recall.
	 * 
	 * @return Recall of this 4-field-table.
	 */

	public double getRecall() {
		double recall = tp / (tp + fn);
		return recall;
	}

	/**
	 * Returns value of True Positives
	 * 
	 * @return Amount of True - Positives.
	 */
	public double getTp() {
		return tp;
	}

	/**
	 * Returns value of False Negatives
	 * 
	 * @return Amount of False - Negatives.
	 */
	public double getFn() {
		return fn;
	}

	/**
	 * Returns value of False Positives
	 * 
	 * @return Amount of False - Positives.
	 */
	public double getFp() {
		return fp;
	}

	/**
	 * Returns value of True Negatives
	 * 
	 * @return Amount of True - Negatives.
	 */
	public double getTn() {
		return tn;
	}

	/**
	 * Returns the category of this 4-field-table
	 * 
	 * @return The category.
	 */
	public SentiLabel getCategory() {
		return category;
	}

	@Override
	public String toString() {
		StringBuffer string = new StringBuffer();
		string.append("Category: " + category + System.lineSeparator());
		string.append("TP: " + getTp() + System.lineSeparator());
		string.append("FN: " + getFn() + System.lineSeparator());
		string.append("FP: " + getFp() + System.lineSeparator());
		string.append("TN: " + getTn() + System.lineSeparator());
		string.append("Total: " + getTotal() + System.lineSeparator());
		string.append(System.lineSeparator());
		string.append("Precision: " + getPrecision() + System.lineSeparator());
		string.append("Recall: " + getRecall() + System.lineSeparator());
		return string.toString();
	}
}
