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

public class MockdataJoinOrderTestRQtoECS {
	public static Boolean setupIsDone = false;
	private static final String H2PROTO = "jdbc:h2:";
	private static final String IH2FP = "./Data/TestCaseDataBases/";
	private static final String IH2DBName = "MockdataJoinOrderTestRQtoECS";
	private static final String TRACELEVEL = ";TRACE_LEVEL_FILE=3;TRACE_MAX_FILE_SIZE=20";
	private static String IH2DBURL;
	private String SQLString;
	
	private QueryManager myAW;
	private QueryManager myOAW;
	
	private static final String ResultsURLBase = "./Results/";
	private static final String ResultsURL = ResultsURLBase + IH2DBName+ "/";
	
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
		CreateTablesInMemory.createTablesInMemoryRQtoECS(myAW);
		CreateTablesInMemory.createTablesInMemoryRQtoECSJS(myAW);
		// create link for 
		CreateTablesInMemory.registerTMRQtoECS(myAW);
		CreateTablesInMemory.registerTMRQtoECSJS(myAW);
		setupIsDone = true;
		}
	}
	@Test
	public void test() {
		//JoinRtoSCP.xml cost: 160 149 132 158
		JoinRtoSCP(myAW); // card 9760
	}

	// G - SCP
	private static void JoinRtoSCP(QueryManager myAW){
		File JoinRtoSCP = new File("./results/JoinRtoSCP.xml"); 
		String SQLString =
//				"SELECT COUNT (*) " +
				"SELECT " + SD.RQ_CP7kTableName + ".ID" + ", " + 
							SD.CP_SCP12kTableName + ".SUBCOMPONENTID" + " " +
				"FROM " + SD.RQ_CP7kTableName + " " +
				"INNER JOIN " + SD.CP_SCP12kTableName + " " + 
				"ON " + SD.CP_SCP12kTableName + ".COMPONENTID = " + SD.RQ_CP7kTableName + ".COMPONENTID"; 
		System.out.println(SQLString);
		assertTrue("failure " + JoinRtoSCP.getName().toString() , 
		MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, JoinRtoSCP) >= 10.0);
		myAW.QueryToXML(SQLString, JoinRtoSCP);
	}

	// G - (TM_G-UC) - (TM_UC-UCS) - (TM_UCS-EC)
	private static void OptJoinFSP(QueryManager myAW){
		File OptJoinFSP = new File("./results/OptJoinFSP.xml"); 
		String SQLString =
				"SELECT * " + // SD.G70TableName + ".GOALID" + " " + 
				"FROM " + SD.G70TableName + " " +
				"INNER JOIN " + SD.G_UC8kTableName + " " + 
				"ON " + SD.G_UC8kTableName + ".GOALID = " + SD.G70TableName + ".GOALID " +
				"INNER JOIN " + SD.UC_UCS15kTableName + " " + 
				"ON " + SD.G_UC8kTableName + ".USECASEID = " + SD.UC_UCS15kTableName + ".USECASEID " +
				"INNER JOIN " + SD.UCS_EC16kTableName + " " +
				"ON " + SD.UCS_EC16kTableName + ".USECASESTEPID = " + SD.UC_UCS15kTableName +".USECASESTEPID";
		System.out.println(SQLString);
		assertTrue("failure " + OptJoinFSP.getName().toString() , 
		MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, OptJoinFSP) >= 30.0);
		//MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, OptJoinFSP);
		//myAW.QueryToXML(SQLString, OptJoinFSP);
	}
	// (G) - (TM_UCS-EC) - (TM_EC-ECS)
	private static void OptJoinSP(QueryManager myAW){
		File OptJoinSP = new File("./results/OptJoinSP.xml"); 
		String SQLString =
				"SELECT *" + " " + 
				"FROM " + SD.UCS_EC16kTableName + " " +
				"INNER JOIN " + SD.EC_ECS24kTableName + " " + 
				"ON " + SD.UCS_EC16kTableName + ".EXCEPTIONCASEID = " + SD.EC_ECS24kTableName + ".EXCEPTIONCASEID";// +
		assertTrue("failure " + OptJoinSP.getName().toString() , 
		MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, OptJoinSP) >= 30.0);
		//MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, OptJoinSP);
		//myAW.QueryToXML(SQLString, OptJoinSP);
	}
	
	// (G) - (TM_G-UC) - (TM_UC-UCS)
	private static void OptJoinFP(QueryManager myAW){
		File OptJoinFP = new File("./results/OptJoinFP.xml"); 
		String SQLString =
				"SELECT * " + // SD.G70TableName + ".GOALID" + " " + 
				"FROM " + SD.G70TableName + " " +
				"INNER JOIN " + SD.G_UC8kTableName + " " + 
				"ON " + SD.G_UC8kTableName + ".GOALID = " + SD.G70TableName + ".GOALID " +
				"INNER JOIN " + SD.UC_UCS15kTableName + " " + 
				"ON " + SD.G_UC8kTableName + ".USECASEID = " + SD.UC_UCS15kTableName + ".USECASEID"; //+
		assertTrue("failure " + OptJoinFP.getName().toString() , 
		MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, OptJoinFP) >= 30.0);
		//MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, OptJoinFP);
		//myAW.QueryToXML(SQLString, OptJoinFP);
	}
	
	// JOIN G to ECS
	private static void JoinGtoECS(QueryManager myAW){
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
	
	// JOIN G to ECS with different order
	private static void JoinGtoECSDiffJoinOrder(QueryManager myAW){
		File JoinGtoECSDiffJoinOrder = new File("./results/JoinGtoECSDiffJoinOrder.xml"); 
		String SQLString =
				"SELECT DISTINCT " + SD.G70TableName + ".GOALID" + " " + 
				"FROM " + SD.G70TableName + " " +
				"INNER JOIN " + SD.G_UC8kTableName + " " + 
				"ON " + SD.G_UC8kTableName + ".GOALID = " + SD.G70TableName + ".GOALID " +
				"INNER JOIN " + SD.UC10kTableName + " " + 
				"ON " + SD.G_UC8kTableName + ".USECASEID = " + SD.UC10kTableName + ".USECASEID " +
				"INNER JOIN " + SD.UC_UCS15kTableName + " " + 
				"ON " + SD.UC_UCS15kTableName + ".USECASEID = " + SD.UC10kTableName + ".USECASEID " +
				"INNER JOIN " + SD.EC10kTableName + " " + 
				"ON " + SD.UCS_EC16kTableName + ".EXCEPTIONCASEID = " + SD.EC10kTableName + ".EXCEPTIONCASEID " +
				"INNER JOIN " + SD.EC_ECS24kTableName + " " + 
				"ON " + SD.EC_ECS24kTableName + ".EXCEPTIONCASEID = " + SD.EC10kTableName + ".EXCEPTIONCASEID " +
				"INNER JOIN " + SD.UCS20kTableName + " " + 
				"ON " + SD.UC_UCS15kTableName + ".USECASESTEPID = " + SD.UCS20kTableName + ".USECASESTEPID " +
				"INNER JOIN " + SD.UCS_EC16kTableName + " " + 
				"ON " + SD.UCS_EC16kTableName + ".USECASESTEPID = " + SD.UCS20kTableName + ".USECASESTEPID " +
				"INNER JOIN " + SD.ECS30kTableName + " " + 
				"ON " + SD.EC_ECS24kTableName + ".EXCEPTIONCASESTEPID = " + SD.ECS30kTableName + ".EXCEPTIONCASESTEPID";
		MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, JoinGtoECSDiffJoinOrder);
		//myAW.QueryToXML(SQLString, JoinGtoECSDiffJoinOrder);
	}
	
	private static void JoinGtoECSOR(QueryManager myAW){
		File JoinGtoECSOR = new File("./results/JoinGtoECSOR.xml"); 
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
				"ON " + SD.EC_ECS24kTableName + ".EXCEPTIONCASESTEPID = " + SD.ECS30kTableName + ".EXCEPTIONCASESTEPID " +
				"ORDER BY " + SD.G70TableName + ".GOALID";
		System.out.println(SQLString);
		MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, JoinGtoECSOR);
	}
	
	private static void JoinGtoECSHalf(QueryManager myAW){
		File JoinGtoECSHalf = new File("./results/JoinGtoECSHalf.xml"); 
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
			"ORDER BY " + SD.G70TableName + ".GOALID" + ";";
		MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, JoinGtoECSHalf);
		//myAW.QueryToXML(SQLString, JoinGtoECSHalf);
		//myAW.QueryToXML(SQLString, JoinGtoECSHalf);
	}
	
	private static void JoinGtoECSHalfOR(QueryManager myAW){
		File JoinGtoECSHalf = new File("./results/JoinGtoECSHalf.xml"); 
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
			"ORDER BY " + SD.G70TableName + ".GOALID";
		MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, JoinGtoECSHalf);
		//myAW.QueryToXML(SQLString, JoinGtoECSHalf);
		//myAW.QueryToXML(SQLString, JoinGtoECSHalf);
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

}
