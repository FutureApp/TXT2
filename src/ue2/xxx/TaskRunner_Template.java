package ue2.xxx;

import java.util.ArrayList;

import org.w3c.dom.Node;

import ue1.kSkipN.newpack.TeiP5;
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
String fileLoc = "C:/Users/mcz/Desktop/temp/text";
		UE_MainProcess main = new UE_MainProcess();
		TeiP5 readFile = main.readFile(
				fileLoc);
		
		ArrayList<ArrayList<Node>> wordsInPara = main.abstractsNeededInfos(readFile, "p", "w");
		int condition= 0;
		main.generateEntry(wordsInPara,condition);


	}

	/**
	 * Checks if the amount of the given input matches the requirements. If okay
	 * then pass else print usage() and terminate program with exit-code 2.
	 */
	public static void validateAmountOfGivenInput() {
		if (arg.length < 0) {
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
