import java.io.*;
import java.util.Scanner;



public class fileHandler {

	
	public String readFile(String path) throws IOException {
		
	    StringBuilder text = new StringBuilder();
	    String NL = System.getProperty("line.separator");
	    Scanner scanner = new Scanner(new FileInputStream(path));
	    try {
	      while (scanner.hasNextLine()){
	        text.append(scanner.nextLine() + NL);
	      }
	    }
	    finally{
	      scanner.close();
	    }

		
		return text.toString();
	}
	
	
	
	
	
}
