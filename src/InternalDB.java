
//importing only necessary classes to avoid bloat.


import java.io.File;
import java.sql.ResultSet;

public interface InternalDB {

	//TODO: fix method signatures
	//TODO: add additional  methods to the interface as needed
	
	public IDBReturnEnum createLink(String JDBC_Driver,String URL, String USER, String PASS, String TableName); //for registering the link for the external datasource 
	public IDBReturnEnum createLink(String JDBC_Driver,String URL, String TableName); //For connecting to tables without Authentication like Flat Files.
	public File			 QueryToXML(String SQLString); 	  //for sending SQL to the internal DB Could also use the JDBC embedded driver to communicate from the module externally	
	public ResultSet 	 QueryToRS(String SQLString);
	
	public IDBReturnEnum close();
	
	
	//ForTesting/Future use
	public void PopulateLocalTable();
	public void CreateLocalTable();
	
}
