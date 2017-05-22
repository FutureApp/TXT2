package ue1.sentiment.analyse;

import xgeneral.modules.SymboleClazz.SentiLabel;

public class ExperimentCounter {

	double pospos, negneg = 0;
	double negpos, posneg = 0;
	
	
	double tp,tn,fp,fn = 0;

	double neutral = 0;
	double total = 0;

	String category = "";

	public ExperimentCounter() {
		super();
		category = "total";
	}

	public ExperimentCounter(String category) {
		super();
		this.category = category;
	}

	public double calcPrecision() {
		double precision = 0;
		precision = (getTruePos() / (getTruePos() + getFalsePos()));
		return precision;
	}

	public double calcRecall() {
		double recall = 0;
		recall = (getTruePos() / (getTruePos() + getFalseNeg()));
		return recall;
	}

	public void measureItem(SentiLabel trueLabel, SentiLabel experimentLabel) {
		SentiLabel labelToCheck = experimentLabel;

		// IF Neutral then count it as positive. Hopefully it's right !
		if (experimentLabel.compareTo(SentiLabel.neutral) == 0) {
			labelToCheck = SentiLabel.pos;
			addNeutral();
		}

		// If true-Label and check-label are same
		if (trueLabel.compareTo(labelToCheck) == 0) {

			// True Positive.
			if (labelToCheck.compareTo(SentiLabel.pos) == 0){
				pospos++;
				tp++;
			}
				
			// False Positive.
			else if (labelToCheck.compareTo(SentiLabel.neg) == 0){
				negneg++;
				
			}

			// Hmm something wrong ;)
			else {
				System.out.println("ERROR ");
				System.exit(1);
			}
		}
		// if they don't
		else if (trueLabel.compareTo(labelToCheck) != 0) {

			// If trueLabel is POS
			if (trueLabel.compareTo(SentiLabel.pos) == 0) {

				// False Negative
				if (labelToCheck.compareTo(SentiLabel.neg) == 0) {
					addOneFalseNeg();

					// What case is that. =)
				} else {
					System.out.println("ERROR ");
					System.exit(1);
				}
				// If trueLabel is NEG
			} else if (trueLabel.compareTo(SentiLabel.neg) == 0) {
				// False Postives.
				if (labelToCheck.compareTo(SentiLabel.pos) == 0) {
					addOneFalsePos();

					// What case is that. =)
				} else {
					System.out.println("ERROR ");
					System.exit(1);
				}
				// What case is that. =)
			} else {
				System.out.println("ERROR ");
				System.exit(1);
			}

		}
		// What case is that. =)
		else {
			System.out.println("ERROR ");
			System.exit(1);
		}

	}

	public double addNeutral() {
		neutral++;
		return neutral;
	}

	public double addOneRealPos() {
		pospos++;
		total++;
		return pospos;
	}

	public double addOneRealNeg() {
		negneg++;
		total++;
		return negneg;
	}

	public double addOneFalsePos() {
		negpos++;
		total++;
		return negpos;
	}

	public double addOneFalseNeg() {
		posneg++;
		total++;
		return posneg;
	}

	public double getTruePos() {
		return pospos;
	}

	public double getRealNeg() {
		return negneg;
	}

	public double getFalsePos() {
		return negpos;
	}

	public double getFalseNeg() {
		return posneg;
	}

	public double getTotal() {
		return total;
	}

	public Double getNeutral() {
		return neutral;
	}

	public String getCategory() {
		return category;
	}
}
