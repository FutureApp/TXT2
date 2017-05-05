package xgeneral.modules;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import ue1.kSkipN.TeiP5Loader;

public class aHotSection {
	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {

		TeiP5Loader.loadTei5Document(new File("test/test"));
	}
}