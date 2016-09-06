package jUnit.JoinCardTest;

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

public class JoinCardTestRQtoECS {
	public static Boolean setupIsDone = false;
	private static final String H2PROTO = "jdbc:h2:";
	private static final String IH2FP = "./Data/TestCaseDataBases/";
	private static final String IH2DBName = "JoinCardTestRQtoECS";
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

		// 1) (R-SCP)-CC -CP)-CC
		// JoinRtoSCP.xml cost: 160 149 132 158
		// JoinRtoSCP(myAW); // card 9760
		
		
		
		// JoinRtoECS.xml cost: 21362
		// JoinRtoECS(myAW); // card 230757
		
		JoinR_SCP__CC(myAW);
	}
	
	private static void JoinR_SCP__CC(QueryManager myAW){
		File JoinR_SCP__CC = new File("./results/JoinR_SCP__CC.xml"); 
		String SQLString =
//				"SELECT COUNT (*) " +
				"SELECT " + SD.R_SCP10kTableName + ".ID" + ", " + 
							SD.SCP_CC12kTableName + ".CLASSNAME" + " " +
				"FROM " + SD.R_SCP10kTableName + " " +
				"INNER JOIN " + SD.SCP_CC12kTableName + " " + 
				"ON " + SD.SCP_CC12kTableName + ".SUBCOMPONENTID = " + SD.R_SCP10kTableName + ".SUBCOMPONENTID"; 
		System.out.println(SQLString);
		assertTrue("failure " + JoinR_SCP__CC.getName().toString() , 
					MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, JoinR_SCP__CC) >= 10.0);
		//myAW.QueryToXML(SQLString, JoinR_SCP__CC);
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

	// G - ECS
	private static void JoinRtoECS(QueryManager myAW){
		File JoinRtoECS = new File("./results/JoinRtoECS.xml"); 
		String SQLString =
//				"SELECT COUNT (*) " +
				"SELECT " + SD.RQ_CP7kTableName + ".ID" + ", " + 
							SD.EC_ECS24kTableName + ".EXCEPTIONCASESTEPID" + " " +
				"FROM " + SD.RQ_CP7kTableName + " " +
				"INNER JOIN " + SD.CP_SCP12kTableName + " " + 
				"ON " + SD.CP_SCP12kTableName + ".COMPONENTID = " + SD.RQ_CP7kTableName + ".COMPONENTID" + " " +
				"INNER JOIN " + SD.SCP_CC12kTableName + " " + 
				"ON " + SD.SCP_CC12kTableName + ".SUBCOMPONENTID = " + SD.CP_SCP12kTableName + ".SUBCOMPONENTID" + " " +
				"INNER JOIN " + SD.CC_UCS16kTableName + " " + 
				"ON " + SD.CC_UCS16kTableName + ".CLASSNAME = " + SD.SCP_CC12kTableName + ".CLASSNAME" + " " +
				"INNER JOIN " + SD.UCS_EC16kTableName + " " + 
				"ON " + SD.UCS_EC16kTableName + ".USECASESTEPID = " + SD.CC_UCS16kTableName + ".USECASESTEPID" + " " +
				"INNER JOIN " + SD.EC_ECS24kTableName + " " + 
				"ON " + SD.EC_ECS24kTableName + ".EXCEPTIONCASEID = " + SD.UCS_EC16kTableName + ".EXCEPTIONCASEID"; // + " " +
		System.out.println(SQLString);
		assertTrue("failure " + JoinRtoECS.getName().toString() , 
					MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, JoinRtoECS) >= 10.0);
		myAW.QueryToXML(SQLString, JoinRtoECS);
	}


	// TODO:  JOIN G to ECS to write a CSV
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
}
