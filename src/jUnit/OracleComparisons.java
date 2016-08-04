package jUnit;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.junit.Before;
import org.junit.Test;

import ResourceStrings.SD;
import qEng.InternalDB;
import qEng.InternalH2;
import utils.CreateTablesInMemory;
import utils.MeasureCostToRS;
import utils.ResultSetUtils;

public class OracleComparisons {

	private static final String protocol = "jdbc:oracle";
	private static final String DriverType = ":thin:";
	private static final String Host = "@rasinsrv06.cstcis.cti.depaul.edu";
	private static final String Port = ":1521";
	private static final String SID = "/oracle12c";
	private static final String URL = protocol + DriverType + Host + Port + SID;
	private static final String User = "Tiqi";
	private static final String Pass = "Tiqi123";

	private static final String REQTableNameTC1 = "\"RequirementsTC1.ReqSheet\"";
	private static final String CCTableName5k = "\"codeclass5k.codeclass\"";
	private static final String TMTableName5k = "CC_REQ_TM5k";

	private InternalDB myDB;
	private boolean setupIsDone;
	private static final String H2PROTO = "jdbc:h2:";
	private static final String IH2FP = "./Data/TestCaseDataBases/";
	private static final String IH2DBName = "POITests";
	private static final String TRACELEVEL = ";TRACE_LEVEL_FILE=3;TRACE_MAX_FILE_SIZE=20";
	private static String IH2DBURL;
	private static final String ResultsURL = "./Results/POIxlsTest/";

	@Before
	public void init()
	{

		if(!setupIsDone){
			IH2DBURL = H2PROTO + IH2FP + IH2DBName + TRACELEVEL;
			//if(new File("./Data/TestCaseDataBases/POITests.mv.db").delete())
			if(new File(IH2FP + IH2DBName + ".mv.db").delete())
			{
				System.out.println("Old Database Deleted");
			}
			if(new File(IH2FP + IH2DBName + ".trace.db").delete())
			{
				System.out.println("Old Trace Deleted");
			}		
			new File(ResultsURL).mkdirs();
			myDB = new InternalH2(IH2DBURL);
			try {

			myDB.ImportSheet(SD.REQSheetTC1FP,SD.REQTableNameTC1);
			myDB.ImportSheet(SD.CCSheet5kFP,SD.CCTableName5k);


			//read CSV trace matrix
			String ArbSQL = "DROP TABLE "+ TMTableName5k +" IF EXISTS; CREATE TABLE "+ TMTableName5k +" AS SELECT * FROM CSVREAD('./Data/CC-REQ-TM.csv');";
			myDB.arbitrarySQL(ArbSQL);

			CreateTablesInMemory.createTablesInMemory(myDB);

			// System.out.println(URL);

//			OracleDataSource ods = new OracleDataSource(); 
//			ods.setURL(URL); 
//			ods.setUser(User); 
//			ods.setPassword(Pass); 
//			Connection conn = ods.getConnection();

			Connection conn = DriverManager.getConnection(URL, User, Pass);



			ResultSetUtils.RStoOracleTable(myDB.QueryToRS("SELECT * FROM " + TMTableName5k + ";"),
					URL, 
					User,
					Pass,
					TMTableName5k);

			ResultSetUtils.RStoOracleTable(myDB.QueryToRS("SELECT * FROM " + CCTableName5k + ";"),
					URL,
					User,
					Pass,
					CCTableName5k);

			ResultSetUtils.RStoOracleTable(myDB.QueryToRS("SELECT * FROM " + REQTableNameTC1 + ";"),
					URL,
					User,
					Pass,
					REQTableNameTC1);

		
				conn.close();
				setupIsDone = true;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
			//Persistent tables for H2
			//ResultSetUtils.RStoTable(myDB.QueryToRS("SELECT * FROM "+ TMTableName5k ), IH2DBURL, "sys", "", TMTableName5k);
			//ResultSetUtils.RStoTable(myDB.QueryToRS("SELECT * FROM "+ CCTableName5k), IH2DBURL, "sys", "", CCTableName5k);
			//ResultSetUtils.RStoTable(myDB.QueryToRS("SELECT * FROM "+ REQTableNameTC1), IH2DBURL, "sys", "", REQTableNameTC1);


			//Connection conn = DriverManager.getConnection(URL,User,Pass);
		
	}

	@Test
	public void test() {
		String SQLString;

		File TQ21 = new File("./results/TQ21.xml");
		SQLString = "SELECT COUNT(*)" + " " +
				"FROM " + REQTableNameTC1 + " " +
				"WHERE " + TMTableName5k + ".ID= " + REQTableNameTC1 + ".ID";
		MeasureCostToRS.measureCostToRSOrcle(SQLString, TQ21);

		File TQ22 = new File("./results/TQ22.xml");
		SQLString = "SELECT COUNT(*)" + " " +
				"FROM " + TMTableName5k + " " +
				"WHERE " + TMTableName5k + ".ID= " + REQTableNameTC1 + ".ID";
		MeasureCostToRS.measureCostToRSOrcle(SQLString, TQ22);


		/*
		File TQ23 = new File("./results/TQ23.xml");
		SQLString = "SELECT " + REQTableNameTC1 + " " +
				"FROM " + REQTableNameTC1 + " " +
				"WHERE " + TMTableName5k + ".ID= " + REQTableNameTC1 + ".ID;";
		TimerUtils.measureCostToRS(myDB, SQLString, TQ23);
		myDB.QueryToXML(SQLString, TQ23);
		 */	
	}

}
