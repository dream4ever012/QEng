package jUnit;

import static org.junit.Assert.*;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.Before;
import org.junit.Test;

import oracle.jdbc.pool.OracleDataSource;
import qEng.InternalDB;
import qEng.InternalH2;
import utils.RStoXLSWriter;
import utils.ResultSetUtils;

public class OracleComparisons {

	private static final String DriverType = ":thin:";
	private static final String Host = "@rasinsrv06.cstcis.cti.depaul.edu";
	private static final String Port = ":1521";
	private static final String User = "Tiqi";
	private static final String Pass = "Tiqi123";
	private static final String SID = "/oracle12c";
	private static final String protocol = "jdbc:oracle";

	private static final String XLDriver = "com.nilostep.xlsql.jdbc.xlDriver"; // 
	private static final String XLURLBase = "jdbc:nilostep:excel:./SecondData/"; //
	private static final String IH2DBURL = "jdbc:h2:./Data/TestCaseDataBases/H2forOracleTests;TRACE_LEVEL_FILE=1";
	private static final String ResultsURL = "./Results/H2forOracleTests/";

	private static final String REQTableNameTC1 = "\"RequirementsTC1.ReqSheet\"";
	private static final String CCTableName5k = "\"codeclass5k.codeclass\"";
	private static final String TMTableName5k = "CC_REQ_TM5k";

	private InternalDB myDB = new InternalH2(IH2DBURL);

	@Before
	public void init(){
		try {
			//DriverManager.registerDriver (new oracle.jdbc.OracleDriver());
			Class.forName("oracle.jdbc.OracleDriver").newInstance();
		/*	if(new File("./Data/TestCaseDataBases/H2forOracleTests.mv.db").delete())
			{
				System.out.println("Old Database Deleted");
			}
			if(new File("./Data/TestCaseDataBases/H2forOracleTests.trace.db").delete())
			{
				System.out.println("Old Trace Deleted");
			}		
			new File(ResultsURL).mkdirs();
			myDB = new InternalH2(IH2DBURL);

			//create relevant table links
			myDB.createLink(XLDriver, XLURLBase, null,null, CCTableName5k);
			myDB.createLink(XLDriver, XLURLBase, null,null, REQTableNameTC1);

			//read CSV trace matrix
			String ArbSQL = "DROP TABLE "+ TMTableName5k +" IF EXISTS; CREATE TABLE "+ TMTableName5k +" AS SELECT * FROM CSVREAD('./Data/CC-REQ-TM.csv');";
			myDB.arbitrarySQL(ArbSQL);
*/
			
			String URL = "jdbc:oracle:thin:@rasinsrv06.cstcis.cti.depaul.edu:1521/oracle12c";// protocol + DriverType + Host + Port + SID;
			// System.out.println(URL);
/*
			OracleDataSource ods = new OracleDataSource(); 
			ods.setURL(URL); 
			ods.setUser(User); 
			ods.setPassword(Pass); 
			Connection conn = ods.getConnection();
*/
			Connection conn = DriverManager.getConnection(URL, User, Pass);
			
	

			ResultSetUtils.RStoTable(myDB.QueryToRS("SELECT * FROM " + TMTableName5k),
					URL, 
					User,
					Pass,
					TMTableName5k);

			ResultSetUtils.RStoTable(myDB.QueryToRS("SELECT * FROM " + CCTableName5k + ";"),
					URL,
					User,
					Pass,
					CCTableName5k);

			ResultSetUtils.RStoTable(myDB.QueryToRS("SELECT * FROM " + REQTableNameTC1 + ";"),
					URL,
					User,
					Pass,
					REQTableNameTC1);
			
			conn.close();
			
			//Persistent tables for H2
			//ResultSetUtils.RStoTable(myDB.QueryToRS("SELECT * FROM "+ TMTableName5k ), IH2DBURL, "sys", "", TMTableName5k);
			//ResultSetUtils.RStoTable(myDB.QueryToRS("SELECT * FROM "+ CCTableName5k), IH2DBURL, "sys", "", CCTableName5k);
			//ResultSetUtils.RStoTable(myDB.QueryToRS("SELECT * FROM "+ REQTableNameTC1), IH2DBURL, "sys", "", REQTableNameTC1);


			//Connection conn = DriverManager.getConnection(URL,User,Pass);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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

	@Test
	public void test() {
		

		String URL = protocol + DriverType + Host + Port + SID;
		Connection conn;
		try {
			conn = DriverManager.getConnection(URL,User,Pass);
			Statement stmt = conn.createStatement();
			
			ResultSet rs = stmt.executeQuery("SELECT * FROM " + REQTableNameTC1 + ";");
			RStoXLSWriter.RStoXLSWrite(rs, new File("./SecondData/OracleTest.xls"));
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	
		
	}

}
