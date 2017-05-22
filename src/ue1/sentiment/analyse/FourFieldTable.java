package ue1.sentiment.analyse;

import xgeneral.modules.SymboleClazz.SentiLabel;

public class FourFieldTable {

	double tp, fn, fp, tn = 0;
	SentiLabel category;

	public FourFieldTable(SentiLabel category) {
		super();
		this.category = category;
	}

	public void add(SentiLabel trueValue, SentiLabel experimentValue) {
		if (category.compareTo(trueValue) == 0) {
			if (category.compareTo(experimentValue) == 0) {
				tp++;
//				System.out.println(category + " " + trueValue + " " + experimentValue + " tp|" + tp);
			} else {
				fn++;
//				System.out.println(category + " " + trueValue + " " + experimentValue + " fn|" + fn);
			}
		} else {
			if (category.compareTo(experimentValue) == 0) {
				fp++;
//				System.out.println(category + " " + trueValue + " " + experimentValue + " fp|" + fp);

			} else {
				tn++;
//				System.out.println(category + " " + trueValue + " " + experimentValue + " tn|" + tn);
			}
		}
	}

	public double getTotal() {
		return tp + fn + fp + tn;
	}

	public double getPrecision() {
		double precision = tp / (tp + fp);
		return precision;
	}

	public double getRecall() {
		double recall = tp / (tp + fn);
		return recall;
	}

	public double getTp() {
		return tp;
	}

	public void setTp(double tp) {
		this.tp = tp;
	}

	public double getFn() {
		return fn;
	}

	public void setFn(double fn) {
		this.fn = fn;
	}

	public double getFp() {
		return fp;
	}

	public void setFp(double fp) {
		this.fp = fp;
	}

	public double getTn() {
		return tn;
	}

	public void setTn(double tn) {
		this.tn = tn;
	}

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
