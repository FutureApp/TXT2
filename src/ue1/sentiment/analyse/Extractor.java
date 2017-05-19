package ue1.sentiment.analyse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import xgeneral.modules.SymboleClazz.SentiLabel;

public class Extractor {

	public static SentiLabel getLabel(String line) {
		String[] split = line.split(" ");
		SentiLabel label = SentiLabel.unknown;
		if (split.length >= 2) {
			label = Labler.detLabel(split[0]);
		} else {

		}
		return label;
	}

	public static double getSentencePolarity(String line, SentiWordNetDemoCode sentiList) {
		String[] split = line.split(" ");
		if (split.length >= 2) {
			List<String> asList = Arrays.asList(split).subList(1, split.length);
			ArrayList<Double> polarityList = new ArrayList<>();
			
			for (int i = 0; i < asList.size(); i++) {
				String word = asList.get(i);
				Double wordPolarity = getWordPolarity(word, sentiList);
				polarityList.add(wordPolarity);
				System.out.println(sentiList.getAllPossiblePOSkeys());
			}
		
		} else {
			System.out.println("Error");
			System.exit(1);
		}

		return 0d;
	}

	
	
	private static Double getWordPolarity(String word, SentiWordNetDemoCode sentiList) {
		String cleanWord = word.replaceAll("[^a-zA-Z0-9]+", "");
		sentiList.extractOverAllPolarity(cleanWord);
		return 0d;
	}
}
