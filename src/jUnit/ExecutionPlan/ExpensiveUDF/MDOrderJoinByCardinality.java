package jUnit.ExecutionPlan.ExpensiveUDF;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Before;
import org.junit.Test;

import ResourceStrings.SD;
import optimizer.AskWise;
import optimizer.QueryManager;
import qEng.ExternalOracle;
import qEng.InternalH2;
import utils.CreateTablesInMemory;
import utils.MeasureCostArbitrary;

public class MDOrderJoinByCardinality {

	public static Boolean setupIsDone = false;
	private static final String H2PROTO = "jdbc:h2:";
	private static final String IH2FP = "./Data/TestCaseDataBases/";
	private static final String IH2DBName = "MDOrderJoinByCardinality";
	private static final String TRACELEVEL = ";TRACE_LEVEL_FILE=3;TRACE_MAX_FILE_SIZE=20";
	private static String IH2DBURL;
	private String SQLString;
	
	private QueryManager myAW;
	private QueryManager myOAW;
	
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
		myOAW = new AskWise(new ExternalOracle());
		// create tablelink
		CreateTablesInMemory.createTablesInMemoryGtoECS(myAW);
		// create link for 
		CreateTablesInMemory.registerTMGtoECS(myAW);
		
		myAW.ImportSheet(SD.CCSheet5kFP, SD.CCTableName5k);
		myAW.ImportSheet(SD.TMSheet4kFP, SD.TMTableName4k);
		myAW.ImportSheet(SD.REQSheetTC1FP, SD.REQTableNameTC1);
		
		myAW.RegisterTM(SD.TMTableName4k, SD.CCTableName5k, "ClassName" , SD.REQTableNameTC1, "ID");
		
		setupIsDone = true;
		}
	}

	@Test
	public void test() {
		JoinUCSaaECjoinECaaECS(myAW);
		JoinGaaUCjoinUCaaUCS(myAW);
		JoinUCaaUCSjoinUCSaaEC(myAW);
	}
	
	private static void JoinUCSaaECjoin(QueryManager myAW){
		File JoinUCSaaECjoin = new File("./results/JoinUCSaaECjoin.xml"); 
		String SQLString =
				"SELECT COUNT(*)" + " " + 
				"FROM " + SD.UCS_EC16kTableName + " " +
				"INNER JOIN " + SD.EC_ECS24kTableName + " " + 
				"ON " + SD.UCS_EC16kTableName + ".EXCEPTIONCASEID = " + SD.EC_ECS24kTableName + ".EXCEPTIONCASEID";// +
/*		assertTrue("failure " + JoinUCSaaECjoin.getName().toString() , 
		MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, JoinUCSaaECjoin) >= 30.0);
*/		
		//MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, JoinUCSaaECjoin);
		myAW.QueryToXML(SQLString, JoinUCSaaECjoin);
	}
	
	private static void JoinUCSaaECjoinECaaECS(QueryManager myAW){
		File JoinUCSaaECjoinECaaECS = new File("./results/JoinUCSaaECjoinECaaECS.xml"); 
		String SQLString =
				"SELECT COUNT(*)" + " " + 
				"FROM " + SD.UCS_EC16kTableName + " " +
				"INNER JOIN " + SD.EC_ECS24kTableName + " " + 
				"ON " + SD.UCS_EC16kTableName + ".EXCEPTIONCASEID = " + SD.EC_ECS24kTableName + ".EXCEPTIONCASEID";// +
/*		assertTrue("failure " + JoinUCSaaECjoinECaaECS.getName().toString() , 
		MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, JoinUCSaaECjoinECaaECS) >= 30.0);
*/		
		//MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, JoinUCSaaECjoinECaaECS);
		myAW.QueryToXML(SQLString, JoinUCSaaECjoinECaaECS);
	}
	
	private static void JoinGaaUCjoinUCaaUCS(QueryManager myAW){
		File JoinGaaUCjoinUCaaUCS = new File("./results/JoinGaaUCjoinUCaaUCS.xml"); 
		String SQLString =
			"SELECT COUNT(*)" + " " + 
			"FROM " + SD.G_UC8kTableName + " " +
			"INNER JOIN " + SD.UC_UCS15kTableName + " " + 
			"ON " + SD.G_UC8kTableName + ".USECASEID = " + SD.UC_UCS15kTableName + ".USECASEID ";
		//MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, JoinGaaUCjoinUCaaUCS);
		myAW.QueryToXML(SQLString, JoinGaaUCjoinUCaaUCS);
	}
	
	private static void JoinUCaaUCSjoinUCSaaEC(QueryManager myAW){
		File JoinUCaaUCSjoinUCSaaEC = new File("./results/JoinUCaaUCSjoinUCSaaEC.xml"); 
		String SQLString =
				"SELECT COUNT(*)" + " " + 
				"FROM " + SD.UC_UCS15kTableName + " " +
				"INNER JOIN " + SD.UCS_EC16kTableName + " " + 
				"ON " + SD.UCS_EC16kTableName + ".USECASESTEPID = " + SD.UC_UCS15kTableName + ".USECASESTEPID ";

		//System.out.println(SQLString);
		//MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, JoinUCaaUCSjoinUCSaaEC);
		myAW.QueryToXML(SQLString, JoinUCaaUCSjoinUCSaaEC);
	}

	
	private static void JoinTMG_UC_UCS(QueryManager myAW){
		File JoinGtoECS = new File("./results/JoinGtoECS.xml"); 
		String SQLString =
				"SELECT * " +  
				"FROM " + SD.G_UC8kTableName + " " +
				"INNER JOIN " + SD.UC_UCS15kTableName + " " + 
				"ON " + SD.G_UC8kTableName + ".USECASEID = " + SD.UC_UCS15kTableName + ".USECASEID ";
		//MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, JoinGtoECS);
		myAW.WriteCSV("./results/OrderJoinByCard/TM_G_UC_UCS.csv", SQLString);
		//myAW.QueryToXML(SQLString, JoinGtoECS);
	}
		
	// TODO:  JOIN UC to ECS to write a CSV
	private static void JoinGtoECS1(QueryManager myAW){	
		File JoinGtoECS = new File("./results/JoinGtoECS.xml"); 
		String SQLString =
				"SELECT DISTINCT " + SD.G70TableName + ".GOALID" + " " + 
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
				"ON " + SD.EC_ECS24kTableName + ".EXCEPTIONCASESTEPID = " + SD.ECS30kTableName + ".EXCEPTIONCASESTEPID";
		MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, JoinGtoECS);
		//myAW.QueryToXML(SQLString, JoinGtoECS);
	}
	
	// JOIN G to ECS
	private static void JoinGtoECS(QueryManager myAW){	
		File JoinGtoECS = new File("./results/JoinGtoECS.xml"); 
		String SQLString =
				"SELECT COUNT(*) " + SD.G70TableName + ".GOALID" + " " + 
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
				"ON " + SD.EC_ECS24kTableName + ".EXCEPTIONCASESTEPID = " + SD.ECS30kTableName + ".EXCEPTIONCASESTEPID";
		MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, JoinGtoECS);
		//myAW.QueryToXML(SQLString, JoinGtoECS);
	}


}
