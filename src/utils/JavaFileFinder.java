package utils;
import java.io.File;
import java.util.ArrayList;

public class JavaFileFinder {

	private static ArrayList<File> FileSet; 

	public static ArrayList<File> GetFiles(File directory, String extension){
		FileSet = new ArrayList<File>();
		GetFilesHelper(directory,extension);
		return FileSet;
	}

	private static void GetFilesHelper(File directory, String extension){

		if(directory.isDirectory()){
			for(File i : directory.listFiles())
			{
				GetFilesHelper(i,extension);
			}
		}else{
			if(directory.getName().toLowerCase().endsWith(extension)){
			FileSet.add(directory);
			}
		}
	}
}
