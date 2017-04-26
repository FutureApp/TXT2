package xgeneral.modules;

/**
 * The messaging System.
 * 
 * @author Michael Czaja
 *
 */
public class SystemMessage {

	/**
	 * Prints an error-message.
	 * 
	 * @param textOfMessage
	 */
	public static void eMessage(String textOfMessage) {
		String prefix = "ERROR - ";
		String message = prefix + textOfMessage;
		System.err.println(message);
	}

	/**
	 * Prints a warning Message.
	 * 
	 * @param textOfMessage
	 */
	public static void wMessage(String textOfMessage) {
		String prefix = "WARNING - ";
		String message = prefix + textOfMessage;
		System.err.println(message);
	}

	/**
	 * Prints all given Values.
	 * 
	 * @param arg
	 *            Values
	 */
	public static void allArguments(String arg[]) {
		for (int i = 0; i < arg.length; i++) {
			eMessage("Argument " + (i + 1) + " <" + arg[i] + ">");

		}
	}
}
