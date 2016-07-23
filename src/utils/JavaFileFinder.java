package utils;
import java.io.File;
import java.util.ArrayList;

public class JavaFileFinder {

	private static ArrayList<File> FileSet; 


	public static ArrayList<File> GetFiles(File directory){

		FileSet = new ArrayList<File>();

		if(directory.isDirectory()){
			//System.out.println(directory);
			for(File i : directory.listFiles())
			{
				GetFiles(i,FileSet);
			}
		}else{
			FileSet.add(directory);
		}		
		return FileSet;
	}


	public static void GetFiles(File directory, ArrayList<File> list){

		if(directory.isDirectory()){
			//System.out.println(directory);
			for(File i : directory.listFiles())
			{
				GetFiles(i,FileSet);
			}
		}else{
			FileSet.add(directory);
		}
	}

}
