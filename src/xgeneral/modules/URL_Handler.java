package xgeneral.modules;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import xgeneral.modules.SystemMessage;

/**
 * The URL-Handler.
 * @author mcz
 *
 */
public class URL_Handler {
	
	/**
	 * Visits the given URL and returns the content/response.
	 * @param URL URL to visit.
	 * @return The response as an document after visiting the URL. 
	 */
	public static Document getContentOf(String URL) {
		Document doc = null;
		try {
			 doc = Jsoup.connect(URL).get();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if(doc == null) SystemMessage.eMessage("Couldn't establish connection to <"+URL+">");
		return doc;
	}
}
