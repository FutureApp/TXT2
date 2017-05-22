package xgeneral.modules;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.io.FileUtils;
import org.xml.sax.SAXException;

import ue1.sentiment.analyse.ExperimentCounter;

public class aHotSection {
	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
		
		ExperimentCounter counter = new ExperimentCounter(); 
		
		
		counter.addOneRealPos();
		counter.addOneRealPos();
		counter. addOneRealNeg();
		System.out.println(counter.getTruePos());
		System.out.println(counter.getRealNeg());
		System.out.println(counter.getTotal());
		

	}

	public static int NumberOfSetBits(int i) {
		// Java: use >>> instead of >>
		// C or C++: use uint32_t
		i = i - ((i >>> 1) & 0x55555555);
		i = (i & 0x33333333) + ((i >>> 2) & 0x33333333);
		return (((i + (i >>> 4)) & 0x0F0F0F0F) * 0x01010101) >>> 24;
	}
}