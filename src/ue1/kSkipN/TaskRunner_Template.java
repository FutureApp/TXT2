package ue1.kSkipN;

import xgeneral.modules.Encoding;
import xgeneral.modules.SystemMessage;

public class TaskRunner_Template {

	static String[] arg;
	static String encoding = Encoding.getDefaultEncoding();

	/**
	 * Entry-point of application.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		arg = args;
		validateAmountOfGivenInput();
	}

	/**
	 * Checks if the amount of the given input matches the requirements. If okay
	 * then pass else print usage() and terminate program with exit-code 2.
	 */
	public static void validateAmountOfGivenInput() {
		if (arg.length < 3) {
			SystemMessage.eMessage("More input is needed");
			System.out.println();
			for (int i = 0; i < arg.length; i++) {
				System.out.printf("Argument %d: %s", i, arg[i]);
				System.out.println();
			}
			usage();
			System.exit(2);
		}
	}

	/**
	 * Prints usage
	 */
	private static void usage() {
		System.out.println("---------- Usage ----------");
		System.out.println("java -jar <name of jar>.jar ");
	}
}
