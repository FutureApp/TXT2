package xgeneral.modules;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

public class aHotSection {
	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
	}

	public static int NumberOfSetBits(int i) {
		// Java: use >>> instead of >>
		// C or C++: use uint32_t
		i = i - ((i >>> 1) & 0x55555555);
		i = (i & 0x33333333) + ((i >>> 2) & 0x33333333);
		return (((i + (i >>> 4)) & 0x0F0F0F0F) * 0x01010101) >>> 24;
	}
}