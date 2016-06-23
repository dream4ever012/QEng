import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.RowSet;

public class InternalH2 implements InternalDB {
	
	//TODO: Make SINGLETON
	
	// this is a Sentinel connection to keep the database from being closed
	// the embedded h2 will close after the last connection is closed
	// so to avoid constant closure and reopening of the DB during TiQi's session
	// I keep this connection until the close() method is called when TiQi is exiting.
	// This is a sanity check because the connection will close when the program exits anyway
	private		Connection h2conn; 
	private		String IH2DBURL = "jdbc:h2:./Data/test";
	private		String IH2PASS = "";
	private		String IH2USER = "sys";
	
	private		Boolean debugMode = false;

	
	public InternalH2()
	{
		//TODO:Consider setting Page Size to tune performance, default is 2kb and it needs to be set on DB creation.
		try {
			//Register the JDBC driver for internal communication and create a sentinel connection
			Class.forName("org.h2.Driver").newInstance();
			Class.forName("com.nilostep.xlsql.jdbc.xlDriver").newInstance();
			h2conn = DriverManager.getConnection(IH2DBURL,IH2USER,IH2PASS);

		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	//Specialized constructor to create the database at a different location
	//TODO: convert to throws instead of try/catch and handle the exceptions in the containing class
	public InternalH2(String DBURL)
	{
		try {
			//Register the JDBC driver for internal communication and create a sentinel connection
			Class.forName("org.h2.Driver").newInstance();
			h2conn = DriverManager.getConnection(DBURL,"sys","");

		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			//update the URL so the program can find it.
			IH2DBURL = DBURL;
		}
	}
	
	@Override
	public IDBReturnEnum createLink(String jdbc_driver,String url, String user, String pass , String tablename)
	{
		//TODO: add enum return statements, IDK what it returns yet
			try {
			Connection iconn = DriverManager.getConnection(IH2DBURL,IH2USER,IH2PASS);
			Statement stmt = iconn.createStatement();
			
			String TLSQL = "DROP TABLE "+ tablename +" IF EXISTS;" + "CREATE LINKED TABLE " + tablename + "('"+ jdbc_driver + "','" + url + "','" + user + "','" + pass + "','"+tablename+"');";
			//String TLSQL = "CREATE LINKED TABLE DEMO1 ("
				Integer i = stmt.executeUpdate(TLSQL);
			
			if(i==0)
			{
				// SQL returned 0 
				
			}
			if(i>0)
			{
				//Manipulated rows returned (not expected)
			}
			
			iconn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public RowSet Query(String SQLString) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void PopulateLocalTable() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void CreateLocalTable() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public IDBReturnEnum close() {
		//not closed properly if the specific SQL exception isn't seen.
		//would close regardless when the JVM stops
		IDBReturnEnum E = IDBReturnEnum.FAIL;
		try {
			h2conn.close();
		} catch (SQLException e) {
			// TODO: double check the documentation here H2 throws a SQL exception on proper close
			E = IDBReturnEnum.SUCCESS;
			//e.printStackTrace();
		}
		return E;
	}

	public Boolean getDebugMode() {
		return debugMode;
	}

	public void setDebugMode(Boolean debugMode) {
		this.debugMode = debugMode;
	}
	
	public String getIDBURL()
	{
		return IH2DBURL;
	}

	@Override
	public IDBReturnEnum createLink(String JDBC_Driver, String URL, String TableName) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
