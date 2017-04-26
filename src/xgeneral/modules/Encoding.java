package xgeneral.modules;

public class Encoding {

	private static  String defaultEncoding="iso-8859-1";
	
	public static Boolean isEncodingSupported(String encoding) {
		Boolean isSupported = false;

		switch (encoding.toLowerCase()) {
		case "utf-8":
			isSupported = true;
			break;
		case "iso-8859-1":
			isSupported = true;
			break;
		case "utf-16":
			isSupported = true;
			break;
		default:
			SystemMessage.wMessage("Enconding didn't match and is not supported. Given <" + encoding
					+ "> Default will be used <"+defaultEncoding+">");
			break;
		}
		return isSupported;
	}
	
	public static String getDefaultEncoding(){
		return defaultEncoding;
	}
}
