package utils;

import java.net.URL;
import java.net.URLClassLoader;

public class ClassPathUtils {
	
	
	public static void printclasspath(){
		  ClassLoader cl = ClassLoader.getSystemClassLoader();

	      URL[] urls = ((URLClassLoader)cl).getURLs();

	      for(URL url: urls){
	      	System.out.println(url.getFile());
	      }
		}
		
	
	
}
