package xgeneral.modules;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import ue1.kSkipN.TeiP5Loader;

public class aHotSection {
	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
		int countBit = 0;
		ArrayList numbers = new ArrayList<>();
		for (int i = 0; i < 3; i++){
			if(NumberOfSetBits(i)== 3 ){
				numbers.add(i);
				countBit++;
			}
		}
		System.out.println(numbers);
		System.out.println(countBit);
	}

	public static int NumberOfSetBits(int i) {
		// Java: use >>> instead of >>
		// C or C++: use uint32_t
		i = i - ((i >>> 1) & 0x55555555);
		i = (i & 0x33333333) + ((i >>> 2) & 0x33333333);
		return (((i + (i >>> 4)) & 0x0F0F0F0F) * 0x01010101) >>> 24;
	}
}