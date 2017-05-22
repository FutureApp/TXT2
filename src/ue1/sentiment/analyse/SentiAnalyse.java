package ue1.sentiment.analyse;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import xgeneral.modules.MyFiler;
import xgeneral.modules.MyPrinter;
import xgeneral.modules.SymboleClazz.SentiLabel;

public class SentiAnalyse {

	SentiWordNetDemo sentiWordNet;
	String pathToInputFile;

	public SentiAnalyse(SentiWordNetDemo sentiWordNet, String pathToInputFile) {
		super();
		this.sentiWordNet = sentiWordNet;
		this.pathToInputFile = pathToInputFile;
	}

	public ArrayList<FourFieldTable> runAnalysis(Boolean binaryRun) {
		ArrayList<FourFieldTable> tabels = new ArrayList<>();
		FourFieldTable posTable = new FourFieldTable(SentiLabel.pos);
		FourFieldTable negTable = new FourFieldTable(SentiLabel.neg);
		Integer currentLines = 0;
		long linesInDocument = MyFiler.countLinesInJava8(new File(pathToInputFile));
		try (BufferedReader br = new BufferedReader(new FileReader(pathToInputFile))) {
			String line;
			while ((line = br.readLine()) != null) {
				MyPrinter.printProgress(currentLines, linesInDocument, 10000);

				SentiLabel realLabel = Extractor.getLabel(line);
				double sentencePolarity = Extractor.getSentencePolarity(line, sentiWordNet);

				SentiLabel experimentLabel = calcLabelForPolarity(sentencePolarity);

				if (binaryRun) {
					if (experimentLabel.compareTo(SentiLabel.neutral) == 0) {
						experimentLabel = SentiLabel.pos;
					}
				}

				posTable.add(realLabel, experimentLabel);
				negTable.add(realLabel, experimentLabel);

				currentLines++;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		tabels.add(posTable);
		tabels.add(negTable);
		return tabels;
	}

	private SentiLabel calcLabelForPolarity(double sentencePolarity) {
		SentiLabel label = SentiLabel.unknown;
		if (sentencePolarity > 0)
			label = SentiLabel.pos;
		else if (sentencePolarity < 0)
			label = SentiLabel.neg;
		else if (sentencePolarity == 0) {
			label = SentiLabel.neutral;
		} else {
			label = SentiLabel.unknown;
		}

		return label;

	}

}
