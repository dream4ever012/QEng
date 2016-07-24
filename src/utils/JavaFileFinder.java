package utils;
import java.io.File;
import java.util.ArrayList;

public class JavaFileFinder {

	private static ArrayList<File> FileSet; 

	public static ArrayList<File> GetFiles(File directory){
		FileSet = new ArrayList<File>();
		GetFilesHelper(directory);
		return FileSet;
	}

	private static void GetFilesHelper(File directory){

		if(directory.isDirectory()){
			for(File i : directory.listFiles())
			{
				GetFilesHelper(i);
			}
		}else{
			FileSet.add(directory);
		}
	}
}
