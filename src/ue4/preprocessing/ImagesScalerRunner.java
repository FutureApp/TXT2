package ue4.preprocessing;

import java.util.Arrays;

public class ImagesScalerRunner {

	static String[] arg = {};

	/**
	 * Checks the size of the input.
	 * 
	 * @param arg2
	 *            The args.
	 */
	private static void checkInput(String[] arg2) {
		if (arg2.length == 3) {

		} else {
			System.out.println("Not enough input. Given: <" + Arrays.toString(arg) + ">");
			help();
		}

	}

	private static void help() {

		System.out.println("PARA 0 : source-dir");
		System.out.println("PARA 1 : result-dir");
		System.out.println("PARA 2 : image-size");
		System.exit(1);

	}

	// Checks if the number is a number and parse it if so.
	private static Integer parseAndCheckIfNumberIsCorrect(String string) {
		int myNumber = 0;
		Boolean checkIsOkay = false;
		if (checkIfInteger(string)) {
			checkIsOkay = true;
			myNumber = Integer.parseInt(string);
			System.out.println(myNumber);
		}

		if (!checkIsOkay) {
			System.out.println("Wrong input ARG 2 <" + arg[2] + ">");
		}
		return myNumber;
	}

	/**
	 * Slow- change if needed.
	 * 
	 * @param strings
	 * @return True- if integer.
	 */
	private static boolean checkIfInteger(String str) {
		boolean matches = str.matches("^[+-]?\\d+$");
		return matches;
	}

	public static void main(String[] args) {
		arg = args;

		// optional.
		// String[] input = { "C:/Users/mcz/Desktop/temp/d4/source",
		// "C:/Users/mcz/Desktop/temp/d4/com", "200" };
		// arg = input;

		checkInput(arg);
		String pathToDir = arg[0];
		String resultDir = arg[1];
		Integer resizeTo = parseAndCheckIfNumberIsCorrect(arg[2]);

		ImageScaler.recolorAndResizeImageInDir(pathToDir, resultDir, resizeTo);

	}
}
