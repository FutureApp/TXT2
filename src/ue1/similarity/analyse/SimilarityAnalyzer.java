package ue1.similarity.analyse;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.TreeSet;

import ue1.kSkipN.newpack.BitShift;
import xgeneral.modules.Checker;
import xgeneral.modules.SystemMessage;
import xgeneral.modules.Writer;

public class SimilarityAnalyzer {

	String dirInput;
	String pathListExport;
	String pathMatrixExport;
	private int ngramms = 2;
	private int kgramms = 1;

	public SimilarityAnalyzer(String dirInput, String pathMatrixExport, String pathListExport) {
		this.dirInput = dirInput;
		this.pathListExport = pathListExport;
		this.pathMatrixExport = pathMatrixExport;
	}

	public ArrayList<DocumentSignatureGramm> generateDocumentSignature() {
		System.out.println(dirInput);
		ArrayList<File> theFiles = generateAllFiles(dirInput);
		Integer n = 2;
		Integer k = 1;
		HashMap<String, ArrayList<ArrayList<Integer>>> indexHashMap = BitShift.generateNeededIndexList(ngramms,
				kgramms);

		ArrayList<DocumentSignatureGramm> signa = new ArrayList<>();
		for (int i = 0; i < theFiles.size(); i++) {
			File activeFile = theFiles.get(i);
			kSkipNGramm grammer = new kSkipNGramm(n, k, activeFile.getAbsolutePath(), indexHashMap);
			ArrayList<List<String>> executeSkipNGramm = grammer.executeSkipNGramm();
			DocumentSignatureGramm docMap = grammer.generateGrammMap(executeSkipNGramm, activeFile.getName());
			signa.add(docMap);
		}
		System.out.println("Generate all signatures -- finished");
		return signa;
	}

	private ArrayList<File> generateAllFiles(String dir) {
		ArrayList<File> resultList = new ArrayList<>();
		File directory = new File(dir);
		System.out.println(directory.getAbsolutePath());
		File[] listFiles = directory.listFiles();
		for (File file : listFiles) {
			if (file.isFile())
				resultList.add(file);
		}
		return resultList;
	}

	public void saveSignatures(ArrayList<DocumentSignatureGramm> allSigis) {
		for (DocumentSignatureGramm sigi : allSigis) {
			File fileName = new File("signatures/signature_" + sigi.documentName);
			String signatureAsString = sigi.signatureToString();
			Writer.delAndWrite(fileName, signatureAsString);
		}

	}

	public MyMatrix generateSimilarityMatrix(ArrayList<DocumentSignatureGramm> allSigis) {
		ArrayList<ArrayList<Double>> matrix = generateEmptyDoubleMatrix(allSigis.size());
		HashMap<Integer, String> generateHeaderIndex = generateHeaderIndex(allSigis);
		MyMatrix simiMatrix = new MyMatrix(matrix, generateHeaderIndex);
		return simiMatrix;
	}

	private HashMap<Integer, String> generateHeaderIndex(ArrayList<DocumentSignatureGramm> allSigis) {
		HashMap<Integer, String> indexMap = new HashMap<>();
		Integer index = 0;
		for (DocumentSignatureGramm documentSignatureGramm : allSigis) {
			String key = documentSignatureGramm.documentName;
			indexMap.put(index, key);
			index++;
		}
		return indexMap;
	}

	private ArrayList<ArrayList<Double>> generateEmptyDoubleMatrix(int size) {
		ArrayList<ArrayList<Double>> matrix = new ArrayList<>();
		for (int i = 0; i < size; i++) {
			ArrayList<Double> col = new ArrayList<>();
			for (int j = 0; j < size; j++) {
				col.add(0d);
			}
			matrix.add(col);
		}
		return matrix;
		// TODO Auto-generated method stub

	}

	public MyMatrix checkSimilsiartiy(MyMatrix matrix) {
		System.out.println("Start");
		System.out.println("Index By Integer");
		System.out.println(matrix.indexMapByIntegerToString());
		System.out.println("Index By String");
		System.out.println(matrix.indexMapByStringToInteger());
		System.out.println("Matrix");
		System.out.println(matrix.matrixToString());

		System.out.println("Size " + matrix.size());

		for (int i = 0; i < matrix.size(); i++) {
			for (int j = 0; j < matrix.size(); j++) {

			}
		}

		Double double1 = matrix.getElementBy(0, 0);
		matrix.setElementBy(0, 0, 1d);
		matrix.setElementBy(0, 1, matrix.getElementBy(0, 0) + 2d);
		System.out.println(matrix.getElementBy(0, 1));
		System.out.println(double1);

		return matrix;
	}

	public HashMap<String, DocumentSignatureGramm> generateHashMapOfSigis(ArrayList<DocumentSignatureGramm> allSigis) {
		HashMap<String, DocumentSignatureGramm> map = new HashMap<>();

		for (DocumentSignatureGramm documentSignatureGramm : allSigis) {
			if (map.containsKey(documentSignatureGramm.documentName)) {
				SystemMessage.eMessage("Terrible Misstake");
				System.exit(1);
			} else {
				map.put(documentSignatureGramm.documentName, documentSignatureGramm);
			}
		}
		System.out.println("Generate Hash-Entrys -- finished");
		return map;

	}

	public void checkSimiliartiy(MyMatrix matrix, HashMap<String, DocumentSignatureGramm> sigis) {
		System.out.println("Checking similarity -- starting");

		for (int i = 0; i < matrix.size(); i++) {
			String nameOfDoc1 = matrix.indexByInteger.get(i);
			DocumentSignatureGramm doc1 = sigis.get(nameOfDoc1);
			for (int j = 0; j < matrix.size(); j++) {
				String nameOfDoc2 = matrix.indexByInteger.get(j);
				DocumentSignatureGramm doc2 = sigis.get(nameOfDoc2);
				double simi = calcSimilarity(doc1, doc2);
				matrix.setElementBy(i, j, simi);
			}
		}

		System.out.println("Checking similarity -- finished");
	}

	private double calcSimilarity(DocumentSignatureGramm doc1, DocumentSignatureGramm doc2) {
		double cosineSimilarity = 0d;
		if (doc1.documentName.compareTo(doc2.documentName) == 0) {
			cosineSimilarity = -1;
		} else {
			TreeSet<String> vectorTreeSet = createVectorHashSet(doc1, doc2);
			ArrayList<Double> docVec1 = buildVector(doc1, vectorTreeSet);
			ArrayList<Double> docVec2 = buildVector(doc2, vectorTreeSet);
			cosineSimilarity = Checker.cosineSimilarity(docVec1, docVec2);
		}
		return cosineSimilarity;
	}

	private ArrayList<Double> buildVector(DocumentSignatureGramm doc, TreeSet<String> vectorTreeSet) {
		ArrayList<Double> docAsVector = new ArrayList<>();

		for (String key : vectorTreeSet) {
			if (doc.grammMap.containsKey(key)) {
				docAsVector.add((double) doc.grammMap.get(key));
			} else {
				docAsVector.add(0d);
			}
		}
		return docAsVector;

	}

	private TreeSet<String> createVectorHashSet(DocumentSignatureGramm doc1, DocumentSignatureGramm doc2) {
		HashSet<String> helpSet = new HashSet<>();
		for (Entry<String, Integer> elem1 : doc1.grammMap.entrySet()) {
			helpSet.add(elem1.getKey());
		}
		for (Entry<String, Integer> elem2 : doc2.grammMap.entrySet()) {
			helpSet.add(elem2.getKey());

		}
		TreeSet<String> vectorSet = new TreeSet<>();
		vectorSet.addAll(helpSet);
		return vectorSet;
	}

	public ArrayList<SSDTupele> detHighestSimiliForDocs(MyMatrix matrix) {
		ArrayList<SSDTupele> result = new ArrayList<>();
		for (int i = 0; i < matrix.size(); i++) {
			String doc1AsString = matrix.indexByInteger.get(i);
			double highestSimili = -1d;
			int index = -1;

			for (int j = 0; j < matrix.size(); j++) {
				double currentValue = matrix.getElementBy(i, j);
				if (currentValue >= highestSimili) {
					highestSimili = currentValue;
					index = j;
				}

			}
			if (index == -1) {
				SystemMessage.eMessage("Error00123");
			}

			String doc2AsString = matrix.indexByInteger.get(index);
			SSDTupele tuple = new SSDTupele(doc1AsString, doc2AsString, highestSimili);
			result.add(tuple);
		}
		return result;

	}
}
