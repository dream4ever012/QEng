package jUnit;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.Before;
import org.junit.Test;

import ResourceStrings.OS;
import ResourceStrings.SD;
import optimizer.AskWise;
import optimizer.QueryManager;
import qEng.ExternalOracle;
import qEng.InternalDB;
import qEng.InternalH2;
import utils.CreateInOracleTable;
import utils.CreateTablesInMemory;
import utils.MeasureCostArbitrary;
import utils.MeasureCostToRS;
import utils.ResultSetUtils;

public class OracleComparisonsWithIndex {

	// private InternalDB myDB;
	public static QueryManager myAW;
	public static QueryManager myOAW;
	private boolean setupIsDone = false;
	private static final String H2PROTO = "jdbc:h2:";
	private static final String IH2FP = "./Data/TestCaseDataBases/";
	private static final String IH2DBName = "OracleComparisonsWithIndex";
	private static final String TRACELEVEL = ";TRACE_LEVEL_FILE=3;TRACE_MAX_FILE_SIZE=20";
	private static String IH2DBURL;
	private static final String ResultsURL = "./Results/OracleComparisonsWithIndex/";


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
		
		//myAW = new AskWise(OS.URL, OS.User, OS.Pass);
		
		myOAW = new AskWise(new ExternalOracle());
		myAW = new AskWise(new InternalH2(IH2DBURL));
/*
		read CSV trace matrix
		String ArbSQL = "DROP TABLE "+ SD.TMTableName5k +" IF EXISTS; CREATE TABLE "+ SD.TMTableName5k +" AS SELECT * FROM CSVREAD('" + SD.TMSheet5kFP + "');";
		myAW.arbitrarySQL(ArbSQL);
		*/
		//CreateTablesInMemory.createTablesInMemory(myAW);
		
		myAW.ImportSheet(SD.CCSheet5kFP, SD.CCTableName5k);
		myAW.ImportSheet(SD.TMSheet4kFP, SD.TMTableName4k);
		myAW.ImportSheet(SD.REQSheetTC1FP, SD.REQTableNameTC1);
		
		myAW.RegisterTM(SD.TMTableName4k, SD.CCTableName5k, "ClassName" , SD.REQTableNameTC1, "ID");
		
		System.out.println("Sheets imported");
		
/*		
		String SQLString;
		SQLString = "SELECT * FROM " + SD.TMTableName5k + ";";
		File TQ1 = new File("./results/TQ1.xml");
		myAW.QueryToXML(SQLString, TQ1);
		
		SQLString = "SELECT * FROM " + SD.CCTableName5k + ";";
		File TQ2 = new File("./results/TQ2.xml");
		myAW.QueryToXML(SQLString, TQ2);
		
		SQLString = "SELECT * FROM " + SD.REQTableNameTC1 + ";";
		File TQ3 = new File("./results/TQ3.xml");
		myAW.QueryToXML(SQLString, TQ3);
*/		

		
/*
		ResultSetUtils.RStoOracleTable(myAW.QueryToRS("SELECT * FROM \""+ SD.CCTableName5k+"\""),
										OS.URL, OS.User, OS.Pass, SD.CCTableName5k);
		ResultSetUtils.RStoOracleTable(myAW.QueryToRS("SELECT * FROM \""+  SD.TMTableName4k+"\"" ),
										OS.URL, OS.User, OS.Pass, SD.TMTableName4k);
		ResultSetUtils.RStoOracleTable(myAW.QueryToRS("SELECT * FROM \""+ SD.REQTableNameTC1+"\""), 
										OS.URL, OS.User, OS.Pass, SD.REQTableNameTC1);
*/
		// System.out.println(URL);

//			OracleDataSource ods = new OracleDataSource(); 
//			ods.setURL(URL); 
//			ods.setUser(User); 
//			ods.setPassword(Pass); 
//			Connection conn = ods.getConnection();

		// MedFleet mockdataset
		//CreateInOracleTable.createInOracleTableMF(myAW);
	
		setupIsDone = true;

			
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

		
		File TQ18 = new File("./results/TQ18.xml");
		SQLString = "SELECT * " +
				"FROM " + SD.CCTableName5k + " " + //", " + SD.TMTableName4k;
				"INNER JOIN " + SD.TMTableName4k + " " +
				"ON " + SD.TMTableName4k + ".ClassName= " + SD.CCTableName5k + ".ClassName";
		assertTrue("failure " + TQ18.getName().toString() , 
				MeasureCostArbitrary.measureCostArbitrary(myOAW, SQLString, TQ18) >= 10.0);
		
		File TQ19 = new File("./results/TQ19.xml");
		SQLString = "SELECT * " +
				"FROM " + SD.CCTableName5k + " " +
				"INNER JOIN " + SD.TMTableName4k + " " +
				"ON " + SD.TMTableName4k + ".ClassName = " + SD.CCTableName5k + ".ClassName;";
		assertTrue("failure " + TQ19.getName().toString() , 
				MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, TQ19) >= 10.0);
/*	
		File TQ21 = new File("./results/TQ21.xml");
		SQLString = "SELECT *" + " " +
				"FROM " + SD.REQTableNameTC1 + " " +
				"WHERE " + SD.TMTableName5k + ".ID= " + SD.REQTableNameTC1 + ".ID";
		assertTrue("failure " + TQ21.getName().toString() , 
				MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, TQ21) >= 10.0);
*/
/*
		File TQ22 = new File("./results/TQ22.xml");
		SQLString = "SELECT COUNT(*)" + " " +
				"FROM " + SD.TMTableName5k + " " +
				"WHERE " + SD.TMTableName5k + ".ID= " + SD.REQTableNameTC1 + ".ID";
		assertTrue("failure " + TQ22.getName().toString() , 
				MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, TQ22) >= 10.0);
*/
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
