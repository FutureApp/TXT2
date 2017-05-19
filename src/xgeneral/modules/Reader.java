package xgeneral.modules;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

public class Reader {

	public static String readFile(String location) {
		String result = "";
		try {
			result = FileUtils.readFileToString(new File(location), Encoding.getDefaultEncoding());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
}
