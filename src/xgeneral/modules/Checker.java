package xgeneral.modules;

import java.io.File;

public class Checker {

	public static boolean isNumeric(String str) {
		try {
			Double.parseDouble(str);
		} catch (NumberFormatException nfe) {
			return false;
		}
		return true;
	}
	
	/**
	 * Checks if a file exits on the given location/path.
	 * @param pathToFile Path to file
	 * @return True - if file exists.
	 */
	public static boolean fileExists(String pathToFile){
		return new File(pathToFile).exists();
	}
}
