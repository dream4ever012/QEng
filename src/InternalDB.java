
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


//ignore these I'm keeping them here for things I might return from or use in the internal H2
//import oracle.jdbc.rowset.OracleWebRowSet;
//import java.sql.SQLException;
//import javax.sql.RowSet;
//import javax.sql.rowset.JoinRowSet;
//import oracle.jdbc.rowset.OracleJoinRowSet;
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.ResultSet;
//import java.sql.ResultSet;
//import java.sql.Statement;