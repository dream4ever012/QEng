package utils;

import java.io.File;
import java.io.IOException;

public class FileUtility {
	
	// TODO: Uncomment ref.deleteOnExit() to enable temp file cleanup on the JVM close.
	// TODO: to make temp directory and then delete the directory we should set it up to delete a file after program is done with it.
	public static File quickXMLFile(String filePath) {

		File ref = null;
		// TODO Auto-generated method stub
		try {
			new File(filePath).mkdirs();  // returns boolean for robustness check 
			ref = File.createTempFile("tmp",".xml", new File(filePath));	
			// ref.deleteOnExit();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ref; // one entrance + one exit			
	}


}
