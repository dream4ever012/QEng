package jUnit;

import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.Before;
import org.junit.Test;

import ResourceStrings.SD;
import optimizer.AskWise;
import qEng.InternalH2;
import utils.MeasureCostArbitrary;

public class LargeTimWithMockData {

	

	public static Boolean setupIsDone = false;
	public static AskWise myAW;
	String SQLString;

	private static final String H2PROTO = "jdbc:h2:";
	private static final String IH2FP = "./Data/TestCaseDataBases/";
	private static final String IH2DBName = "LargeTimWithMockData";
	private static final String TRACELEVEL = ";TRACE_LEVEL_FILE=3;TRACE_MAX_FILE_SIZE=20";
	private static String IH2DBURL;
	private static final String ResultsURL = "./Results/LargeTimWithMockData/";
	
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
		//myDB = new InternalH2(IH2DBURL);
		myAW = new AskWise(new InternalH2(IH2DBURL));
		
		myAW.ImportSheet(SD.REQSheetFP,SD.REQTableName);
		myAW.ImportSheet(SD.CCSheetFP,SD.CCTableName);
		myAW.ImportSheet(SD.CC10kFP, SD.CC10kTableName);
		myAW.ImportSheet(SD.CC_UCS16kFP, SD.CC_UCS16kTableName);		
		myAW.ImportSheet(SD.CC_SCP12kFP,SD.CC_SCP12kTableName);
		myAW.ImportSheet(SD.G70FP, SD.G70TableName);
		myAW.ImportSheet(SD.G_UC8kFP, SD.G_UC8kTableName);
		myAW.ImportSheet(SD.UC_UCS15kFP, SD.UC_UCS15kTableName);
		myAW.ImportSheet(SD.UCS20kFP, SD.UCS20kTableName);
		myAW.ImportSheet(SD.UCS_EC16kFP, SD.UCS_EC16kTableName);
		myAW.ImportSheet(SD.EC10kFP, SD.EC10kTableName);
		myAW.ImportSheet(SD.EC_ECS24kFP, SD.EC_ECS24kTableName);
		myAW.ImportSheet(SD.ECS30kFP, SD.ECS30kTableName);
		myAW.ImportSheet(SD.R70FP, SD.R70TableName);
		myAW.ImportSheet(SD.RQ_CP7kFP, SD.RQ_CP7kTableName);
		myAW.ImportSheet(SD.CP10kFP, SD.CP10kTableName);
		myAW.ImportSheet(SD.CP_SCP12kFP,SD.CP_SCP12kTableName);
		
		//read CSV trace matrix
		String ArbSQL = "DROP TABLE "+ SD.TMTableName +" IF EXISTS; CREATE TABLE "+ SD.TMTableName +" AS SELECT * FROM CSVREAD('./Data/CC-REQ-TM.csv');";
		
		myAW.arbitrarySQL(ArbSQL);
		setupIsDone = true;
		}
	}
		
		@Test
		public void JoinMockData() {
			File TQ1 = new File(ResultsURL + "TQ1.xml");

			SQLString = "SELECT " + SD.CC10kTableName + ".classname " + // REQTableName + ".* " + 
					"FROM " + SD.CC10kTableName + ";";// " " + 
					//"INNER JOIN " + SD.REQTableName + " " +
					//"ON "+ SD.TMTableName + ".ID = "+ SD.REQTableName + ".ID;";
			
			// myDB.QueryToXML(SQLString, TQ1);
			assertTrue("failure " + TQ1.getName().toString() , 
					MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, TQ1) >= 10.0);
		}
	
	}
