package ue1.sentiment.analyse;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import xgeneral.modules.SymboleClazz.SentiLabel;

public class SentiAnalyse {

	SentiWordNetDemoCode sentiWordNet;
	String pathToInputFile;

	public SentiAnalyse(SentiWordNetDemoCode sentiWordNet, String pathToInputFile) {
		super();
		this.sentiWordNet = sentiWordNet;
		this.pathToInputFile = pathToInputFile;
	}

	public void runAnalysis() {
		int realPos, realNeg = 0;
		int falsePos, falseNeg = 0;

		try (BufferedReader br = new BufferedReader(new FileReader(pathToInputFile))) {
			String line;
			while ((line = br.readLine()) != null) {
				SentiLabel realLabel = Extractor.getLabel(line);
				double sentencePolarity = Extractor.getSentencePolarity(line, sentiWordNet);

			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void start() {
		// TODO Auto-generated method stub

	}
}
