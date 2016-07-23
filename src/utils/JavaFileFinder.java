package utils;
import java.io.File;
import java.util.ArrayList;

public class JavaFileFinder {

	public static ArrayList<File> GetFiles(File directory){
		
		ArrayList<File> FileSet = new ArrayList<File>();
		if(directory.isDirectory()){
			System.out.println(directory);
			for(File i : directory.listFiles())
			{
				GetFiles(i);
			}
		}else{
			FileSet.add(directory);
		}
		return FileSet;
	}
}
