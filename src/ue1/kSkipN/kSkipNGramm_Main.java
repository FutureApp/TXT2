package ue1.kSkipN;

import java.io.File;

import xgeneral.modules.Encoding;
import xgeneral.modules.Pather;
import xgeneral.modules.SystemMessage;

public class kSkipNGramm_Main {

	static String[] arg;
	static String encoding = Encoding.getDefaultEncoding();

	/**
	 * Entry-point of application.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		arg = args;
		validateInput();
		executeSkipNGramm();
		
		
		
		
		
	}

	private static void executeSkipNGramm() {
		String pathToFileOfContent = 
		Integer ngramms = 2; 
		Integer kgramms = 0;
		File locationToSave = new File(Pather.toTestTask);
		File fileOfContent = new File()
		
		
		
	}

	/**
	 * Checks if the amount of the given input matches the requirements. If okay
	 * then pass else print usage() and terminate program with exit-code 2.
	 */
	public static void validateInput() {
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
