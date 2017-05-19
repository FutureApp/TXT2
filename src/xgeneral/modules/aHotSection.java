package xgeneral.modules;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.io.FileUtils;
import org.xml.sax.SAXException;

public class aHotSection {
	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
		HashMap<String, Integer> ab = new HashMap<>();
		List<String> readLines = FileUtils.readLines(new File("./task/01Task01/lexikon.txt"),
				Encoding.getDefaultEncoding());

		for (String string : readLines) {
			if(string.startsWith("#")) {}
			else{
				
			String[] split = string.split("\t");
			List<String> asList = Arrays.asList(split);
			ab.put(asList.get(0), 1);
			}
		}
		ab.forEach((a, b) -> {
			System.out.println(a + " " + b);
		});

	}

	public static int NumberOfSetBits(int i) {
		// Java: use >>> instead of >>
		// C or C++: use uint32_t
		i = i - ((i >>> 1) & 0x55555555);
		i = (i & 0x33333333) + ((i >>> 2) & 0x33333333);
		return (((i + (i >>> 4)) & 0x0F0F0F0F) * 0x01010101) >>> 24;
	}
}