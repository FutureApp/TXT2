package ue1.sentiment.analyse;

import xgeneral.modules.SymboleClazz.SentiLabel;

public class Labler {

	public static final String posLabel = "__label__pos";
	public static final String negLabel = "__label__neg";

	public static SentiLabel detLabel(String label) {
		SentiLabel labelOfLabel;
		switch (label) {
		case posLabel:
			labelOfLabel = SentiLabel.pos;
			break;
		case negLabel:
			labelOfLabel = SentiLabel.neg;
			break;
		default:
			labelOfLabel = SentiLabel.unknown;
			break;
		}
		return labelOfLabel;
	}

	public static Boolean isLabelnKnown(SentiLabel label) {
		return ((label == SentiLabel.unknown) ? false : true);
	}
}
