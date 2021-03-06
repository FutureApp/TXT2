package ue1.sentiment.analyse;
//    Copyright 2013 Petter Törnberg

//
//    This demo code has been kindly provided by Petter Törnberg <pettert@chalmers.se>
//    for the SentiWordNet website.
//
//    This program is free software: you can redistribute it and/or modify
//    it under the terms of the GNU General Public License as published by
//    the Free Software Foundation, either version 3 of the License, or
//    (at your option) any later version.
//
//    This program is distributed in the hope that it will be useful,
//    but WITHOUT ANY WARRANTY; without even the implied warranty of
//    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//    GNU General Public License for more details.
//
//    You should have received a copy of the GNU General Public License
//    along with this program.  If not, see <http://www.gnu.org/licenses/>.

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * Source: http://sentiwordnet.isti.cnr.it/code/SentiWordNetDemoCode.java
 *
 * Additional logic was added.
 */
public class SentiWordNetDemo {

	private Map<String, Double> dictionary;
	private ArrayList<String> allPoSkeys;

	/**
	 * Constructs a look-up table by loading a specific lexicon to det. the
	 * polarity of a given word.
	 * 
	 * @param pathToSWN
	 *            Path to the lexicon
	 */
	public SentiWordNetDemo(String pathToSWN) {
		// This is our main dictionary representation
		dictionary = new HashMap<String, Double>();
		allPoSkeys = new ArrayList<>();

		// From String to list of doubles.
		HashMap<String, HashMap<Integer, Double>> tempDictionary = new HashMap<String, HashMap<Integer, Double>>();
		HashSet<String> posKeys = new HashSet<>();
		BufferedReader csv = null;
		try {
			csv = new BufferedReader(new FileReader(pathToSWN));
			int lineNumber = 0;

			String line;
			while ((line = csv.readLine()) != null) {
				lineNumber++;

				// If it's a comment, skip this line.
				if (!line.trim().startsWith("#")) {
					// We use tab separation
					String[] data = line.split("\t");
					String wordTypeMarker = data[0];
					posKeys.add(wordTypeMarker);

					// Example line:
					// POS ID PosS NegS SynsetTerm#sensenumber Desc
					// a 00009618 0.5 0.25 spartan#4 austere#3 ascetical#2
					// ascetic#2 practicing great self-denial;...etc

					// Is it a valid line? Otherwise, through exception.
					if (data.length != 6) {
						throw new IllegalArgumentException("Incorrect tabulation format in file, line: " + lineNumber);
					}

					// Calculate synset score as score = PosS - NegS
					Double synsetScore = Double.parseDouble(data[2]) - Double.parseDouble(data[3]);

					// Get all Synset terms
					String[] synTermsSplit = data[4].split(" ");

					// Go through all terms of current synset.
					for (String synTermSplit : synTermsSplit) {
						// Get synterm and synterm rank
						String[] synTermAndRank = synTermSplit.split("#");
						String synTerm = synTermAndRank[0] + "#" + wordTypeMarker;

						// System.out.println("-- "+ synTerm+" --");
						int synTermRank = Integer.parseInt(synTermAndRank[1]);
						// What we get here is a map of the type:
						// term -> {score of synset#1, score of synset#2...}

						// Add map to term if it doesn't have one
						if (!tempDictionary.containsKey(synTerm)) {
							tempDictionary.put(synTerm, new HashMap<Integer, Double>());
						}

						// Add synset link to synterm
						tempDictionary.get(synTerm).put(synTermRank, synsetScore);
					}
				}
			}

			posKeys.forEach(a -> allPoSkeys.add(a));

			// Go through all the terms.
			for (Map.Entry<String, HashMap<Integer, Double>> entry : tempDictionary.entrySet()) {
				String word = entry.getKey();
				Map<Integer, Double> synSetScoreMap = entry.getValue();

				// Calculate weighted average. Weigh the synsets according to
				// their rank.
				// Score= 1/2*first + 1/3*second + 1/4*third ..... etc.
				// Sum = 1/1 + 1/2 + 1/3 ...
				double score = 0.0;
				double sum = 0.0;
				for (Map.Entry<Integer, Double> setScore : synSetScoreMap.entrySet()) {
					score += setScore.getValue() / (double) setScore.getKey();
					sum += 1.0 / (double) setScore.getKey();
				}
				score /= sum;

				dictionary.put(word, score);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (csv != null) {
				try {
					csv.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * Returns the polarity for a specific word#pos pair.
	 * 
	 * @param word
	 *            The word.
	 * @param pos
	 *            The POS
	 * @return Polarity between -1(bad) and +1 (good)
	 */
	public double extract(String word, String pos) {
		return dictionary.get(word + "#" + pos);
	}

	public double getPolarity(ExameSentence sentence) {
		System.out.println("IN");
		System.out.println(sentence.getSentenceAsString());
		return 0;
	}

	/**
	 * Returns all tracked POS.
	 * 
	 * @return List of possible POS
	 */
	public ArrayList<String> getAllPossiblePOSkeys() {
		return allPoSkeys;
	}

	/**
	 * Extracts the polarity over all word#pos maps. This will be done by : (1)
	 * extract all values for each word#pos map. (2) sum the values (3) divide
	 * the sum by the number of word#pos maps.
	 * 
	 * @param cleanWord
	 *            The word
	 * @return Polarity between -1(bad) to 1 (good)
	 */
	public Double extractOverAllPolarity(String cleanWord) {
		ArrayList<Double> resultList = new ArrayList<>();
		Double resultPolarity = 0d;
		double polarity = 0;
		double counter = 0;

		for (String POSkey : allPoSkeys) {
			String key = cleanWord + "#" + POSkey;
			if (dictionary.containsKey(key)) {
				// System.out.println("Contains " + key);
				Double double1 = dictionary.get(key);
				resultList.add(double1);
				polarity += double1;
				counter++;
			} else {
				// System.out.println("Not Containing " + key);
			}
		}

		if (counter >= 1) {
			resultPolarity = polarity / counter;
		}
		// System.out.println("Word :" + cleanWord + " -- result:" +
		// resultPolarity);
		return resultPolarity;
	}
}