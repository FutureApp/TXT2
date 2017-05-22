package xgeneral.modules;

public class MyPrinter {
	
	public static void printProgress(Integer currentLine, long maxLines, Integer printSteps ) {
		
		if((currentLine % printSteps) == 0)
			System.out.println(currentLine+"/"+maxLines);
		
	}

}
