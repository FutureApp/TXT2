package xgeneral.modules;

public class UtilsStrings_SingleTone {

	
	private static final UtilsStrings_SingleTone YOURSELF = new UtilsStrings_SingleTone(); 
	
    private UtilsStrings_SingleTone() { 
    } 
         
    public static UtilsStrings_SingleTone getInstance() { 
      return YOURSELF; 
    }
    
    public String normalizeWhiteSpaces(String string){
		return string.replaceAll("\t", " ");
    }
    public String fillLeftWithWhiteSpaces(String input,Integer numberWhiteSpaces) {
    	String output = input;
    	while(output.length()<=numberWhiteSpaces) output= " "+output;
    	return output;
	}
    
    public String fillRightWithWhiteSpaces(String input,Integer numberWhiteSpaces) {
    	String output = input;
    	while(output.length()<=numberWhiteSpaces) output= output+" ";
    	return output;
	}
    public String getXWhiteSpaces(Integer numWhiteSpaces) {
    	String output = "";
    	while(output.length()<=numWhiteSpaces) output= " "+output;
		return output;
	}
}
