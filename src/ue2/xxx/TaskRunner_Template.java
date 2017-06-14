package ue2.xxx;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

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

		UE_MainProcess main = new UE_MainProcess();
		TeiP5 readFile = main.readFile(
				"C:/Users/mcz/Desktop/temp/un/Franz_Kafka_In_der_Strafkolonie/Franz_Kafka_In_der_Strafkolonie.tei");
		main.abstractsNeededInfos(readFile, "p", "w");


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
