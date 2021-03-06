package qEng;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import oracle.jdbc.rowset.OracleWebRowSet;


public class ExternalOracle implements InternalDB{
	//TODO: Make SINGLETON

	// this is a Sentinel connection to keep the database from being closed
	// the embedded h2 will close after the last connection is closed
	// so to avoid constant closure and reopening of the DB during TiQi's session
	// I keep this connection until the close() method is called when TiQi is exiting.
	// This is a sanity check because the connection will close when the program exits anyway
	private static final String DriverType = ":thin:";
	private static final String Host = "@rasinsrv06.cstcis.cti.depaul.edu";
	private static final String Port = ":1521";
	private static final String User = "Student12";
	private static final String Pass = "CSC553";
	private static final String SID = "/oracle12c";
	private static final String protocol = "jdbc:oracle";
	//private static Connection conn;

	static String URL_TIQI = protocol + DriverType + Host + Port + SID;


	public ExternalOracle()
	{
		//TODO:Consider setting Page Size to tune performance, default is 2kb and it needs to be set on DB creation.
		try {
			//Register the JDBC driver for internal communication and create a sentinel connection
			Class.forName("oracle.jdbc.OracleDriver").newInstance();
			//Class.forName("com.nilostep.xlsql.jdbc.xlDriver").newInstance();
			// h2conn = DriverManager.getConnection(URL_TIQI,User,Pass);

		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	

	//Specialized constructor to create the database at a different location
	//TODO: convert to throws instead of try/catch and handle the exceptions in the containing class
	public ExternalOracle(String DBURL)
	{
		try {
			//Register the JDBC driver for internal communication and create a sentinel connection
			Class.forName("oracle.jdbc.OracleDriver").newInstance();
			//h2conn = DriverManager.getConnection(DBURL,"sys","");

		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			//update the URL so the program can find it.
			URL_TIQI = DBURL;
		}
	}

	// TODO Convert To Oracle
	@Override
	public IDBReturnEnum createLink(String jdbc_driver,String url, String user, String pass , String tablename)
	{
		//TODO: add enum return statements, IDK what it returns yet
		IDBReturnEnum rt = IDBReturnEnum.FAIL;

		if(url == null || tablename == null){return rt;}

		try {
			Connection iconn = DriverManager.getConnection(URL_TIQI,User,Pass);
			Statement stmt = iconn.createStatement();

			String TLSQL = "DROP TABLE "+ tablename +" IF EXISTS;" + "CREATE LINKED TABLE " + tablename + "('"+ jdbc_driver + "','" + url + "','" + user + "','" + pass + "','"+tablename+"');";
			//String TLSQL = "CREATE LINKED TABLE DEMO1 ("
			stmt.executeUpdate(TLSQL);
			iconn.close();

			rt = IDBReturnEnum.SUCCESS;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rt;
	}


	@Override
	public IDBReturnEnum QueryToXML(String SQLString,File resultLocation) {

		IDBReturnEnum rt = IDBReturnEnum.FAIL;
		if(SQLString == null || resultLocation == null){return rt;}
		try {
			Connection iconn = DriverManager.getConnection(URL_TIQI,User,Pass);
			Statement stmt = iconn.createStatement();

			ResultSet rs = stmt.executeQuery(SQLString);
			rt = toXML(rs, resultLocation);
			rs.close();
			iconn.close();

		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return rt;
	}

	@Override
	public boolean arbitrarySQL(String SQLString)
	{
		boolean rt = false;
		Connection iconn;
		try {
			iconn = DriverManager.getConnection(URL_TIQI,User,Pass);
			Statement stmt = iconn.createStatement();

			stmt.execute(SQLString);
			iconn.close();
			rt = true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rt;
	}

	//TODO: check if fref is a directory and generate the intermediate directories to place the result in a the path then create a UUID for the temporary file.
	//TODO: add an additional toXML file that takes either a writer or an output stream instead of a file for in memory only XML operations if needed.
	//TODO: create additional Enums for result reference is null and ResultSet is null.
	public IDBReturnEnum toXML(ResultSet rs, File fref) {
		//new File("./results/").mkdirs();
		//File f = new File("./results/"+ fref.getPath() + ".xml");
		IDBReturnEnum rt = IDBReturnEnum.FAIL;
		if(fref == null || rs == null){return rt;}

		FileOutputStream fos;
		try {

			fos = new FileOutputStream(fref);
			//https://docs.oracle.com/cd/B19306_01/java.102/b14355/jcrowset.htm#BABCFJBE
			OracleWebRowSet set = new OracleWebRowSet();
			set.writeXml(rs, fos);
			set.close();

			rt = IDBReturnEnum.SUCCESS;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return rt;
	}

	@Override
	public IDBReturnEnum close() {
		//not closed properly if the specific SQL exception isn't seen.
		//would close regardless when the JVM stops
		IDBReturnEnum E = IDBReturnEnum.FAIL;
		return E;
	}

	public String getIDBURL()
	{
		return URL_TIQI;
	}

	@Override
	public IDBReturnEnum createLink(String jdbc_driver, String url, String tablename) {
		IDBReturnEnum rt = IDBReturnEnum.FAIL;

		if(url == null || tablename == null){return rt;}

		try {
			Connection iconn = DriverManager.getConnection(URL_TIQI,User,Pass);
			Statement stmt = iconn.createStatement();

			String TLSQL = "DROP TABLE "+ tablename +" IF EXISTS;" + "CREATE LINKED TABLE " + tablename + "('"+ jdbc_driver + "','" + url + "','','','"+tablename+"');";
			//String TLSQL = "CREATE LINKED TABLE DEMO1 ("
			stmt.executeUpdate(TLSQL);
			iconn.close();

			rt = IDBReturnEnum.SUCCESS;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rt;
	}

	//TODO: try to revert to signature: IDBReturnEnum QueryToRS(String , ResultSet) but java wasn't changing the 
	//TODO: the result set requires a connection which is what (I THINK) is causing the access denied error when using Y8 on multiple source folders though I'm not sure.
	@Override
	public ResultSet QueryToRS(String SQLString) {
		ResultSet rsRef = null;
		try {
			Connection iconn = DriverManager.getConnection(URL_TIQI,User,Pass);
			Statement stmt = iconn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

			rsRef = stmt.executeQuery(SQLString);
			//iconn.close();
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rsRef;
	}

	// TODO: Uncomment ref.deleteOnExit() to enable temp file cleanup on the JVM close.
	/*		@Override
		public File quickXMLFile() {
			// TODO Auto-generated method stub
			try {
				File ref = File.createTempFile("tmp",".xml");	
				ref.deleteOnExit();
				return ref;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;		

		}

		@Override
		public IDBReturnEnum FileFree(File ref) {
			IDBReturnEnum rt = IDBReturnEnum.FAIL;
			if(ref!=null){
			ref.delete();
			rt = IDBReturnEnum.SUCCESS;
			}
			return rt;
		}
	 */
	@Override
	public IDBReturnEnum RegisterUncompiledUDF(String Alias, String Imports, String Code) {
		// TODO Convert To Oracle
		IDBReturnEnum rt = IDBReturnEnum.FAIL;
		Connection iconn;
		try {
			iconn = DriverManager.getConnection(URL_TIQI,User,Pass);
			Statement stmt = iconn.createStatement();

			String UDFString = "CREATE ALIAS " + Alias + " AS $$ " +
					Imports 
					+ "\n@CODE\n" +
					Code 
					+ " $$;";

			stmt.execute(UDFString);
			iconn.close();
			rt = IDBReturnEnum.SUCCESS;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return rt;
	}

	@Override
	public IDBReturnEnum RegisterUncompiledUDF(String Alias, String Source) {
		// TODO Check performance gains of not doing context switch at the end.
		// TODO switch the regex to split after the last import statement not the first
		// TODO Convert To Oracle
		String [] tmp = Source.split("([^;]*\bimport\b[^;]*;)");
		System.out.println("The two strings are ");
		String Imports = tmp[1];
		System.out.println(Imports);
		String Code = tmp[2];
		System.out.println(Code);

		return RegisterUncompiledUDF(Alias, Imports, Code);
	}

	// TODO Convert To Oracle
	@Override
	public IDBReturnEnum RegisterCompiledUDF(String Alias, String classpath)
	{
		IDBReturnEnum rt = IDBReturnEnum.FAIL;

		Connection iconn;
		try {
			iconn = DriverManager.getConnection(URL_TIQI,User,Pass);
			Statement stmt = iconn.createStatement();

			String UDFString = "CREATE ALIAS IF NOT EXISTS " + Alias + " FOR " +
					"\"" + classpath + "\"";

			stmt.execute(UDFString);
			iconn.close();
			rt = IDBReturnEnum.SUCCESS;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return rt;
	}

	// TODO Convert To Oracle
	//TODO: finish adding support for classes outside the classpath, which we may not want to do for security reasons.
	@Override
	public IDBReturnEnum RegisterCompiledUDF(String Alias, String classpath, String directory)
	{
		IDBReturnEnum rt = IDBReturnEnum.FAIL;
		return rt;

	}

	// TODO Convert To Oracle
	@Override
	public IDBReturnEnum ImportSheet(String FilePath, String SheetName, String TableName) {
		// TODO Auto-generated method stub
		return null;
	}

	// TODO Convert To Oracle
	@Override
	public IDBReturnEnum ImportSheet(String FilePath, String SheetName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void CreateIndex(String tMTableName, String tableOneColName) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void WriteCSV(String FilePath, String SQLString) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public String getFullURL() {
		// TODO Auto-generated method stub
		return null;
	}
}
