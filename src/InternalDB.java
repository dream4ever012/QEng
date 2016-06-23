
//importing only necessary classes to avoid bloat.


import javax.sql.RowSet;

public interface InternalDB {

	//TODO: fix method signatures
	//TODO: add additonal  methods to the interface as neede
	
	public IDBReturnEnum createLink(String JDBC_Driver,String URL, String USER, String PASS, String TableName); //for registering the link for the external datasource 
	public IDBReturnEnum createLink(String JDBC_Driver,String URL, String TableName); //For connecting to tables without Authentication like Flat Files.
	public RowSet Query(String SQLString); 	  //for sending SQL to the internal DB Could also use the JDBC embedded driver to communicate from the module externally	
	
	
	public IDBReturnEnum close();
	
	
	//ForTesting/Future use
	public void PopulateLocalTable();
	public void CreateLocalTable();
	
}
