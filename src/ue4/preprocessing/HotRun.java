package ue4.preprocessing;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HotRun {

	public static void main(String[] args) {
		
		ArrayList<File> listf = listf("C:/Users/admin/Desktop/abgabe/data", new ArrayList<>());
		
		
		for (File file : listf) {
			if(file.getAbsolutePath().contains(".")){
				if(file.getAbsolutePath().contains("parrot"))
					System.exit(1);
				System.out.println(file.getAbsolutePath());
			}
			else {
				System.out.println("warning");
				System.exit(1);
			}
		}
		

	}

	
	public static ArrayList<File> listf(String directoryName, ArrayList<File> files) {
	    File directory = new File(directoryName);

	    // get all the files from a directory
	    File[] fList = directory.listFiles();
	    for (File file : fList) {
	        if (file.isFile()) {
	            files.add(file);
	        } else if (file.isDirectory()) {
	            listf(file.getAbsolutePath(), files);
	        }
	    }
		return files;
	}
}
