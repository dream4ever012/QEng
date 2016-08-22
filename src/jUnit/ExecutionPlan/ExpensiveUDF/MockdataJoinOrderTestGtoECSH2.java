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
import utils.TimerUtils;

public class MockdataJoinOrderTestGtoECSH2 {
	public static Boolean setupIsDone = false;
	private static final String H2PROTO = "jdbc:h2:";
	private static final String IH2FP = "./Data/TestCaseDataBases/";
	private static final String IH2DBName = "MockdataJoinOrderTestGtoECS";
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
		setupIsDone = true;
		}
	}
	@Test
	public void test() {
		// findings: join cost explode as the number of join operation is increased.
		// skip join (oracles does) + pruning
		///////////////// H2 ///////////////////////////
		// JoinGtoECS.xml cost: 5999 6147 5212 8576 5062 5444 5131 5106 5128 5746
		// JoinGtoECS(myAW);
		// JoinGtoECSHalf.xml cost: 689 868 868 714 740
		// JoinGtoECSHalf(myAW);
		// findings: the cost of DISTINCT is minimal
		
		// Findings: oracle is faster than h2
		// when primary keys and foreign keys are not set 
		// ORACLE DOES NOT SKIP JOIN
		///////////////// ORACLE ////////////////////////
		// JoinGtoECSOR.xml cost: 1039 746 763 630 633 737
		// JoinGtoECSOR(myOAW);
		// JoinGtoECSHalf.xml cost: 223 216 229
		//JoinGtoECSHalfOR(myOAW);
		
		// DIFFERENT JOIN ORDER 
		// findings: join order does matter
		// JoinGtoECSDiffJoinOrder.xml cost: 8324 5632 6884 5743
		//JoinGtoECSDiffJoinOrder(myAW);
		
		// OPTIMAL ORDER OF JOIN
		// 1) GOALID(70) <=	USECASEID(10k) <= USECASESTEPID(20k)
		
		// (G) - (TM_G-UC) - (TM_UC-UCS)		
		// 1) OptJoinFP.xml cost: 272 364 289 429 354
		//OptJoinFP(myAW);
		
		// (G) - (TM_UCS-EC) - (TM_EC-ECS)
		// OptJoinSP.xml cost: 3792 3778 3811 3470 4036
		// OptJoinSP(myAW);

		// OptJoinFP join SP 		// G - (TM_G-UC) - (TM_UC-UCS) - (TM_UCS-EC)
		// OptJoinFSP.xml cost: 1006 1175 848 943
		// OptJoinFSP(myAW);
		// different order 		// G - (TM_G-UC) - (TM_UC-UCS) - (TM_UCS-EC)
		// OptJoinFSP1.xml cost: 862 724 792 609 644	
		// OptJoinFSP1(myAW);
		
		JoinUCSaaECjoinECaaECS(myAW);
		//JoinGaaUCjoinUCaaUCS(myAW);
		//JoinUCaaUCSjoinUCSaaEC(myAW);
	}

	// G - (TM_G-UC) - (TM_UC-UCS) - (TM_UCS-EC)
	private static void OptJoinFSP1(QueryManager myAW){
		File OptJoinFSP1 = new File("./results/OptJoinFSP1.xml"); 
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
		assertTrue("failure " + OptJoinFSP1.getName().toString() , 
		MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, OptJoinFSP1) >= 30.0);
		//myAW.QueryToXML(SQLString, OptJoinFSP);
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

