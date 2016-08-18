package jUnit.ExecutionPlan.ExpensiveUDF;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Before;
import org.junit.Test;

import ResourceStrings.SD;
import optimizer.AskWise;
import optimizer.QueryManager;
import qEng.InternalH2;
import utils.CreateTablesInMemory;
import utils.MeasureCostArbitrary;
import utils.TimerUtils;

public class MockdataJoinOrderTestGtoECS {
	public static Boolean setupIsDone = false;
	private static final String H2PROTO = "jdbc:h2:";
	private static final String IH2FP = "./Data/TestCaseDataBases/";
	private static final String IH2DBName = "MockdataJoinOrderTestGtoECS";
	private static final String TRACELEVEL = ";TRACE_LEVEL_FILE=3;TRACE_MAX_FILE_SIZE=20";
	private static String IH2DBURL;
	private String SQLString;
	
	private QueryManager myAW;
	
	private static final String ResultsURLBase = "./Results/";
	private static final String ResultsURL = ResultsURLBase + IH2DBName+ "/";
	
	//TODO: put the CLASSES column back in CCTableName5k sheet, the cassandra class filepaths.
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
		myAW = new AskWise(new InternalH2(IH2DBURL));
		
		CreateTablesInMemory.createTablesInMemoryGtoECS(myAW);
		CreateTablesInMemory.registerTMGtoECS(myAW);
		
		

/*		File equalizer = new File("./results/equalizer.xml");
		SQLString =
				"SELECT * " +
				"FROM " + "CCPredicateTEMP" + ";";
				//"INNER JOIN " + SD.TMTableName5k + " " + 
				//"ON " + SD.TMTableName5k + ".ClassName = " + SD.CCTableName5k + ".ClassName " +
				//"WHERE " + "FAULTPRONE(" + "CCPredicateTEMP" + ".CLASSES) = 1;"; //+ SD.CCTableName5k + ".CREATEDBY = 'Caleb'" + " AND " + 
		MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, equalizer);
*/		
		
		
		


		setupIsDone = true;
		}
	}
	@Test
	public void test() {
		
		// CompW5k1.xml cost: 19816
		JoinGtoECS(myAW);
		// JoinGtoECSHalf(myAW);
		


	}
	
	private static void JoinGtoECS(QueryManager myAW){
		File JoinGtoECS = new File("./results/JoinGtoECS.xml"); 
		String SQLString =
				"SELECT * " +
				"FROM " + SD.G70TableName + " " +
				"INNER JOIN " + SD.G_UC8kTableName + " " + 
				"ON " + SD.G_UC8kTableName + ".GOALID = " + SD.G70TableName + ".GOALID " +
				"INNER JOIN " + SD.UC10kTableName + " " + 
				"ON " + SD.G_UC8kTableName + ".USECASEID = " + SD.UC10kTableName + ".USECASEID " +
				"INNER JOIN " + SD.UC_UCS15kTableName + " " + 
				"ON " + SD.UC_UCS15kTableName + ".USECASEID = " + SD.UC10kTableName + ".USECASEID " +
				"INNER JOIN " + SD.UCS20kTableName + " " + 
				"ON " + SD.UC_UCS15kTableName + ".USECASESTEPID = " + SD.UCS20kTableName + ".USECASESTEPID " +
				"INNER JOIN " + SD.UCS_EC16kTableName + " " + 
				"ON " + SD.UCS_EC16kTableName + ".USECASESTEPID = " + SD.UCS20kTableName + ".USECASESTEPID " +
				"INNER JOIN " + SD.EC10kTableName + " " + 
				"ON " + SD.UCS_EC16kTableName + ".EXCEPTIONCASEID = " + SD.EC10kTableName + ".EXCEPTIONCASEID " +
				"INNER JOIN " + SD.EC_ECS24kTableName + " " + 
				"ON " + SD.EC_ECS24kTableName + ".EXCEPTIONCASEID = " + SD.EC10kTableName + ".EXCEPTIONCASEID " +
				"INNER JOIN " + SD.ECS30kTableName + " " + 
				"ON " + SD.EC_ECS24kTableName + ".EXCEPTIONCASESTEPID = " + SD.ECS30kTableName + ".EXCEPTIONCASESTEPID;";
		//MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, JoinGtoECS);
		myAW.QueryToXML(SQLString, JoinGtoECS);

	}
	
	private static void JoinGtoECSHalf(QueryManager myAW){
		File JoinGtoECSHalf = new File("./results/JoinGtoECSHalf.xml"); 
		String SQLString =

			"SELECT COUNT(*) " +
			"FROM " + SD.G70TableName + " " +
			"INNER JOIN " + SD.G_UC8kTableName + " " + 
			"ON " + SD.G_UC8kTableName + ".GOALID = " + SD.G70TableName + ".GOALID " +
			"INNER JOIN " + SD.UC10kTableName + " " + 
			"ON " + SD.G_UC8kTableName + ".USECASEID = " + SD.UC10kTableName + ".USECASEID " +
			"INNER JOIN " + SD.UC_UCS15kTableName + " " + 
			"ON " + SD.UC_UCS15kTableName + ".USECASEID = " + SD.UC10kTableName + ".USECASEID " +
			"INNER JOIN " + SD.UCS20kTableName + " " + 
			"ON " + SD.UC_UCS15kTableName + ".USECASESTEPID = " + SD.UCS20kTableName + ".USECASESTEPID;"; //" +
/*			"INNER JOIN " + SD.UCS_EC16kTableName + " " + 
			"ON " + SD.UCS_EC16kTableName + ".USECASESTEPID = " + SD.UCS20kTableName + ".USECASESTEPID " +
			"INNER JOIN " + SD.EC10kTableName + " " + 
			"ON " + SD.UCS_EC16kTableName + ".EXCEPTIONCASEID = " + SD.EC10kTableName + ".EXCEPTIONCASEID " +
			"INNER JOIN " + SD.EC_ECS24kTableName + " " + 
			"ON " + SD.EC_ECS24kTableName + ".EXCEPTIONCASEID = " + SD.EC10kTableName + ".EXCEPTIONCASEID " +
			"INNER JOIN " + SD.ECS30kTableName + " " + 
			"ON " + SD.EC_ECS24kTableName + ".EXCEPTIONCASESTEPID = " + SD.ECS30kTableName + ".EXCEPTIONCASESTEPID;";
*/
		//MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, JoinGtoECSHalf);
		myAW.QueryToXML(SQLString, JoinGtoECSHalf);
		
	}

}

