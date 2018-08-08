package qEng;

//importing only necessary classes to avoid bloat.


import java.io.File;
import java.sql.ResultSet;

//TODO: this interface is getting a little fat see if we can break down the interface into a few simpler interfaces, not a high priority because H2 provides everythign that we need currently we may not implement new concrete InternalDB classes.

public interface InternalDB {

	//TODO: fix method signatures
	//TODO: fix method signatures to all return Enum for example query to xml should be IDBReturnEnum QueryToXML(String SQLString, File file);
	//TODO: add additional  methods to the interface as needed
	//TODO: add robustness across the board, I currently don't eve have null checks for SQL strings and such still in the POC phase of development just adding features as fast as possible.
	
	// necessary when creating table link for External H2 server
	public String getFullURL();
	
	//for registering the link for the external data source 
	public IDBReturnEnum createLink(String JDBC_Driver,String URL, String USER, String PASS, String TableName); 
	
	//For connecting to tables without Authentication like Flat Files.
	public IDBReturnEnum createLink(String JDBC_Driver,String URL, String TableName); 							
		
	//for sending SQL to the internal DB Could also use the JDBC embedded driver to communicate from the module externally	
	public IDBReturnEnum QueryToXML(String SQLString, File FileRef); 	  			  							
	
	public ResultSet QueryToRS(String SQLString);
	
	public IDBReturnEnum RegisterUncompiledUDF(String Alias, String Imports, String Code);
	
	public IDBReturnEnum RegisterUncompiledUDF(String Alias, String Source);
	
	//public IDBReturnEnum RegisterCompiledUDF();
	
	//TODO: setup a nice interface for 
	//public IDBReturnEnum SubQuery(String SQL, String TempTableName);
	
	public IDBReturnEnum close();
	
	// public File quickXMLFile();
	// public IDBReturnEnum FileFree(File ref);
	
	//ForTesting/Future use
	//public void PopulateLocalTable();
	//public void CreateLocalTable();
	public boolean arbitrarySQL(String SQL);

	IDBReturnEnum RegisterCompiledUDF(String Alias, String classpath);
	
	IDBReturnEnum RegisterCompiledUDF(String Alias, String classpath, String directory);

	IDBReturnEnum ImportSheet(String FilePath, String SheetName, String TableName);
	IDBReturnEnum ImportSheet(String FilePath, String SheetName);

	public void CreateIndex(String tMTableName, String tableOneColName);

	public void WriteCSV(String FilePath, String SQLString);
}
