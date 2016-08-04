package utils;

import java.io.File;
import java.io.IOException;

public class QuickXMLFile {
	
	public File quickXMLFile() {
		// TODO Auto-generated method stub
		try {
			File ref = File.createTempFile("tmp",".xml");	
			ref.deleteOnExit();
			return ref;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;		
		
	}

}
