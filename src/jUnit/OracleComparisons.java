package jUnit;

import static org.junit.Assert.*;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.Before;
import org.junit.Test;

import qEng.InternalDB;
import qEng.InternalH2;
import utils.ResultSetUtils;

public class OracleComparisons {

	private static final String DriverType = ":thin:";
	private static final String Host = "@rasinsrv06.cstcis.cti.depual.edu";
	private static final String Port = ":1521";
	private static final String User = "Tiqi";
	private static final String Pass = "tiqi123";
	private static final String SID = ":oracle12c";
	private static final String protocol = "jdbc:oracle";

	private static final String XLDriver = "com.nilostep.xlsql.jdbc.xlDriver"; // 
	private static final String XLURLBase = "jdbc:nilostep:excel:./SecondData/"; //
	private static final String IH2DBURL = "jdbc:h2:./Data/TestCaseDataBases/H2forOracleTests;TRACE_LEVEL_FILE=1";
	private static final String ResultsURL = "./Results/H2forOracleTests/";

	private static final String REQTableNameTC1 = "\"RequirementsTC1.ReqSheet\"";
	private static final String CCTableName5k = "\"codeclass5k.codeclass\"";
	private static final String TMTableName5k = "CC_REQ_TM5k";

	private InternalDB myDB;

	@Before
	public void init(){
		try {
			DriverManager.registerDriver (new oracle.jdbc.OracleDriver());
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
			myDB.arbitrarySQL(ArbSQL);*/


			String URL = protocol + DriverType + Host + Port + SID; 
			
			Connection conn = DriverManager.getConnection(URL,User,Pass);
			
			System.out.println(URL);

		/*	ResultSetUtils.RStoTable(myDB.QueryToRS("SELECT * FROM " + TMTableName5k),
					URL, 
					User,
					Pass,
					TMTableName5k);*/

			/*ResultSetUtils.RStoTable(myDB.QueryToRS("SELECT * FROM " + CCTableName5k),
					URL,
					User,
					Pass,
					CCTableName5k);
*/
		/*	ResultSetUtils.RStoTable(myDB.QueryToRS("SELECT * FROM " + REQTableNameTC1),
					URL,
					User,
					Pass,
					REQTableNameTC1);*/
			
			//Persistent tables for H2
			//ResultSetUtils.RStoTable(myDB.QueryToRS("SELECT * FROM "+ TMTableName5k ), IH2DBURL, "sys", "", TMTableName5k);
			//ResultSetUtils.RStoTable(myDB.QueryToRS("SELECT * FROM "+ CCTableName5k), IH2DBURL, "sys", "", CCTableName5k);
			//ResultSetUtils.RStoTable(myDB.QueryToRS("SELECT * FROM "+ REQTableNameTC1), IH2DBURL, "sys", "", REQTableNameTC1);


			//Connection conn = DriverManager.getConnection(URL,User,Pass);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void test() {
		fail("Not yet implemented");
	}

}
