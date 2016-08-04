package jUnit;

import java.io.File;

import org.junit.Before;
import org.junit.Test;

import ResourceStrings.SD;
import qEng.InternalDB;
import qEng.InternalH2;
import utils.*;;


//TODO: setup the timer to return an integer of milliseconds for the operation.
public class InMemoryNY8Test {
	

	public static Boolean setupIsDone = false;
	public static InternalDB myDB;
	String SQLString;

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
		
		myDB.ImportSheet(SD.REQSheetFP,SD.REQTableName);
		myDB.ImportSheet(SD.CCSheetFP,SD.CCTableName);
		
		myDB.ImportSheet(SD.CC10kFP, SD.CC10kTableName);
		myDB.ImportSheet(SD.CC_UCS16kFP, SD.CC_UCS16kTableName);
	
		
		myDB.ImportSheet(SD.CC_SCP12kFP,SD.CC_SCP12kTableName);
		myDB.ImportSheet(SD.G70FP, SD.G70TableName);
		myDB.ImportSheet(SD.G_UC8kFP, SD.G_UC8kTableName);
		myDB.ImportSheet(SD.UC_UCS15kFP, SD.UC_UCS15kTableName);
		myDB.ImportSheet(SD.UCS20kFP, SD.UCS20kTableName);
		myDB.ImportSheet(SD.UCS_EC16kFP, SD.UCS_EC16kTableName);
		myDB.ImportSheet(SD.EC10kFP, SD.EC10kTableName);
		myDB.ImportSheet(SD.EC_ECS24kFP, SD.EC_ECS24kTableName);
		myDB.ImportSheet(SD.ECS30kFP, SD.ECS30kTableName);
		myDB.ImportSheet(SD.R70FP, SD.R70TableName);
		myDB.ImportSheet(SD.RQ_CP7kFP, SD.RQ_CP7kTableName);
		myDB.ImportSheet(SD.CP10kFP, SD.CP10kTableName);
		myDB.ImportSheet(SD.CP_SCP12kFP,SD.CP_SCP12kTableName);
		/* */

		
		//read CSV trace matrix
		String ArbSQL = "DROP TABLE "+ SD.TMTableName +" IF EXISTS; CREATE TABLE "+ SD.TMTableName +" AS SELECT * FROM CSVREAD('./Data/CC-REQ-TM.csv');";
		myDB.arbitrarySQL(ArbSQL);
		setupIsDone = true;
		}
	}

	
	@Test
	public void test() {
		
		File TQ17 = new File("./results/TQ17.xml");
		SQLString = "SELECT * " +
				"FROM REQTableNameT" + " " +
				"INNER JOIN TMTableNameT" + " " +
				"ON TMTableNameT" + ".ID= " + "REQTableNameT.ID;";
		MeasureCostToRS.measureCostToRS(myDB, SQLString, TQ17);
		//measureCostToRS(myDB, SQLString, TQ17);
		
		File TQ18 = new File("./results/TQ18.xml");
		SQLString = "SELECT * " +
				"FROM " + SD.CCTableName5k + " " +
				"INNER JOIN " + SD.TMTableName5k + " " +
				"ON " + SD.TMTableName5k + ".ClassName= " + SD.CCTableName5k + ".ClassName;";
		//new MeasureCostToRS(myDB, SQLString, TQ18);
		MeasureCostToRS.measureCostToRS(myDB, SQLString, TQ18);

		File TQ19 = new File("./results/TQ19.xml");
		SQLString = "SELECT * " +
				"FROM CCTableNameT" + " " +
				"INNER JOIN TMTableNameT" + " " +
				"ON TMTableNameT" + ".ClassName= " + "CCTableNameT.ClassName;";
		MeasureCostToRS.measureCostToRS(myDB, SQLString, TQ19);
		
		File TQ20 = new File("./results/TQ20.xml");
		SQLString = "SELECT " + SD.REQTableNameTC1 + ".*, " + SD.CCTableName5k + ".*" + " " +
				"FROM " + SD.REQTableNameTC1 + " " +
				"INNER JOIN " + SD.TMTableName5k + " " +
				"ON " + SD.TMTableName5k + ".ID = " + SD.REQTableNameTC1 + ".ID" + " " +
				"INNER JOIN " + SD.CCTableName5k + " " +
				"ON " + SD.CCTableName5k + ".ClassName = " + SD.TMTableName5k + ".ClassName;";
		MeasureCostArbitrary.measureCostArbitrary(myDB, SQLString, TQ20);
		
		File TQ21 = new File("./results/TQ21.xml");
		SQLString = "SELECT REQTableNameT.*, " + "CCTableNameT.*" + " " +
				"FROM REQTableNameT" + " " +
				"INNER JOIN TMTableNameT" + " " +
				"ON TMTableNameT.ID = REQTableNameT.ID" + " " +
				"INNER JOIN CCTableNameT" + " " +
				"ON CCTableNameT.ClassName = TMTableNameT.ClassName;";
		MeasureCostArbitrary.measureCostArbitrary(myDB, SQLString, TQ21);

	}
	

}


