
//importing only necessary classes to avoid bloat.


import java.io.File;
import java.sql.ResultSet;

public interface InternalDB {

	//TODO: fix method signatures
	//TODO: fix method signatures to all return Enum for example query to xml should be IDBReturnEnum QueryToXML(String SQLString, File file);
	//TODO: add additional  methods to the interface as needed
	//TODO: add robustness across the board, I currently don't eve have null checks for SQL strings and such still in the POC phase of development just adding features as fast as possible.
	public IDBReturnEnum createLink(String JDBC_Driver,String URL, String USER, String PASS, String TableName); //for registering the link for the external data source 
	public IDBReturnEnum createLink(String JDBC_Driver,String URL, String TableName); //For connecting to tables without Authentication like Flat Files.
	public File			 QueryToXML(String SQLString); 	  //for sending SQL to the internal DB Could also use the JDBC embedded driver to communicate from the module externally	
	public ResultSet 	 QueryToRS(String SQLString);
	
	public IDBReturnEnum close();
	
	
	//ForTesting/Future use
	public void PopulateLocalTable();
	public void CreateLocalTable();
	
}
