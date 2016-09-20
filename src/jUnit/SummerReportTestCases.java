package jUnit;

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

public class SummerReportTestCases {
	public static Boolean setupIsDone = false;
	private static final String H2PROTO = "jdbc:h2:";
	private static final String IH2FP = "./Data/SummerReportTestCases/";
	private static final String IH2DBName = "SummerReportTestCases";
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
		//CreateTablesInMemory.createTablesInMemoryRQtoECSJS(myAW);
		// create link for 
		CreateTablesInMemory.registerTMRQtoECS(myAW);
		//CreateTablesInMemory.registerTMRQtoECSJS(myAW);
		
		// TO-DO: register tables
		myAW.RegisterCompiledUDF("FAULTPRONE", "src.UDF.isFaultProne");
		

		setupIsDone = true;
		}
	}
	@Test
	public void test() {
		//SkipJoin_R_SC_CC_woSkip(myAW); //325 390 389 442 372
		
		//SkipJoin_R_SC_CC_wSkip.xml cost: //221 139 145 155
		//SkipJoin_R_SC_CC_wSkip(myAW);
		
		//POSTJoinWoPrun(myAW); // 5191 4920
		//POSTJoinWPrun(myAW); // 4859 
// POST JOIN PRUNING: INCONCLUSIVE	
//		POSTJoinWPrunS0(myAW); // 4319
//		POSTJoinWPrunS1_1(myAW); // 4545
//		POSTJoinWPrunS1(myAW); // 33330
//		POSTJoinWPrunS2(myAW); // 4371
//		POSTJoinWPrunS3(myAW); // 5691
//		POSTJoinWPrunS4(myAW);
		
//		// create table + running udf 
//		// ExpenUDFFirstUDF1_1.xml cost: 8052 13321
//		ExpenUDFFirstUDF1_1(myAW);
//		// hasing: cost 153
//		//ExpenUDFFirstUDF1_1_1.xml cost: 153 228
//		ExpenUDFFirstUDF1_1_1(myAW);
//		// join with the other table 
//		// ExpenUDFFirstUDF1_2.xml cost: 2306 2452
//		ExpenUDFFirstUDF1_2(myAW);
//		// drop table
//		//ExpenUDFFirstUDF1_3.xml cost: 9 12
//		ExpenUDFFirstUDF1_3(myAW);
		
		// for 10k rows without predicate // estimated cost .85 ms/row agrees with other test result of .89ms/row
		//ExpenUDFFirstUDF0_1(myAW); // 8512 10239 14121 10398 ==> 10789
		// for 5k rows with predicate of selectivity .5 CreatedBy = 'Caleb'
		//ExpenUDFFirstUDF0_2(myAW); // 4885 6130 6579 9543 5430 ==> 6513
		
		// step1: create table
		//ExpenUDFLaterUDF1_1.xml cost: 3843
		ExpenUDFLaterUDF1_1(myAW);
		//step2: hashing
		//ExpenUDFLaterUDF1_2.xml cost: 3549
		ExpenUDFLaterUDF1_2(myAW);
		// step3: UDF column join w/ CC-SCP 
		//ExpenUDFLaterUDF1_3.xml cost: 670 join
		ExpenUDFLaterUDF1_3(myAW);
		// step 4 hashing
		// ExpenUDFLaterUDF1_3_1.xml cost: 3659
		ExpenUDFLaterUDF1_3_1(myAW);
		// step 5 hashing
		// ExpenUDFLaterUDF1_4.xml cost: 85
		ExpenUDFLaterUDF1_4(myAW);
		// step6: join UDF column with the interim join table
		// ExpenUDFLaterUDF1_5.xml cost: 2195
		ExpenUDFLaterUDF1_5(myAW);
		ExpenUDFLaterUDF1_6(myAW);
		
	}

	private static void POSTJoinWoPrun(QueryManager myAW){
		File POSTJoinWoPrun = new File("./results/POSTJoinWoPrun.xml"); 
		String SQLString = 
				"DROP TABLE TQ121 IF EXISTS; CREATE TABLE TQ121 AS " +
				"SELECT  ECaaECS.*, UCSaaEC.USECASESTEPID  " + 
				"FROM UCSaaEC" + " " +
				"INNER JOIN ECaaECS ON ECaaECS.EXCEPTIONCASEID = UCSaaEC.EXCEPTIONCASEID " +
				"INNER JOIN CCaaUCS ON UCSaaEC.USECASESTEPID = CCaaUCS.USECASESTEPID;" +
				"DROP TABLE TQ121 IF EXISTS";			
		System.out.println(SQLString);
		assertTrue("failure " + POSTJoinWoPrun.getName().toString() , 
					MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, POSTJoinWoPrun) >= 10.0);
		//myAW.QueryToXML(SQLString, POSTJoinWoPrun);
	}
	
	private static void POSTJoinWPrunS0(QueryManager myAW){
		File POSTJoinWPrunS0 = new File("./results/POSTJoinWPrunS0.xml"); 
		String SQLString = 
				"DROP TABLE TQ121 IF EXISTS; CREATE TABLE TQ121 AS " +
				"SELECT ECaaECS.EXCEPTIONCASESTEPID, UCSaaEC.USECASESTEPID  " + 
				"FROM UCSaaEC" + " " +
				"INNER JOIN ECaaECS ON ECaaECS.EXCEPTIONCASEID = UCSaaEC.EXCEPTIONCASEID;";
		System.out.println(SQLString);
		assertTrue("failure " + POSTJoinWPrunS0.getName().toString() , 
					MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, POSTJoinWPrunS0) >= 10.0);
		//myAW.QueryToXML(SQLString, POSTJoinWPrunS1);
	}
	
	private static void POSTJoinWPrunS1(QueryManager myAW){
		File POSTJoinWPrunS1 = new File("./results/POSTJoinWPrunS1.xml"); 
		String SQLString = 
				"DROP TABLE TQ121 IF EXISTS; CREATE TABLE TQ121 AS " +
				"SELECT ECaaECS.EXCEPTIONCASESTEPID, UCSaaEC.USECASESTEPID  " + 
				"FROM UCSaaEC" + " " +
				"INNER JOIN ECaaECS ON ECaaECS.EXCEPTIONCASEID = UCSaaEC.EXCEPTIONCASEID;";
		System.out.println(SQLString);
		assertTrue("failure " + POSTJoinWPrunS1.getName().toString() , 
					MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, POSTJoinWPrunS1) >= 10.0);
		//myAW.QueryToXML(SQLString, POSTJoinWPrunS1);
	}
	
	private static void POSTJoinWPrunS1_1(QueryManager myAW){
		File POSTJoinWPrunS1_1 = new File("./results/POSTJoinWPrunS1_1.xml"); 
		String SQLString = 
				"SELECT DISTINCT * " + 
				"FROM TQ121";
		System.out.println(SQLString);
		assertTrue("failure " + POSTJoinWPrunS1_1.getName().toString() , 
					MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, POSTJoinWPrunS1_1) >= 10.0);
		//myAW.QueryToXML(SQLString, POSTJoinWPrunS1_1);
	}
	
	private static void POSTJoinWPrunS2(QueryManager myAW){
		File POSTJoinWPrunS2 = new File("./results/POSTJoinWPrunS2.xml"); 
		String SQLString = 
				"CREATE HASH INDEX ON TQ121 (EXCEPTIONCASESTEPID);" +
				"CREATE HASH INDEX ON TQ121 (USECASESTEPID);";
				//"DROP TABLE TQ121 IF EXISTS";
		System.out.println(SQLString);
		assertTrue("failure " + POSTJoinWPrunS2.getName().toString() , 
					MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, POSTJoinWPrunS2) >= 10.0);
	}

	private static void POSTJoinWPrunS3(QueryManager myAW){
		File POSTJoinWPrunS3 = new File("./results/POSTJoinWPrunS3.xml"); 
		String SQLString = 
				"SELECT * " +
				"FROM TQ121 " +
				"INNER JOIN CCaaUCS ON TQ121.USECASESTEPID = CCaaUCS.USECASESTEPID;"; // +
				//"DROP TABLE TQ121 IF EXISTS";
		System.out.println(SQLString);
		assertTrue("failure " + POSTJoinWPrunS3.getName().toString() , 
					MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, POSTJoinWPrunS3) >= 10.0);
	}
	
	private static void POSTJoinWPrunS4(QueryManager myAW){
		File POSTJoinWPrunS4 = new File("./results/POSTJoinWPrunS4.xml"); 
		String SQLString = 
				"DROP TABLE TQ121 IF EXISTS";
		System.out.println(SQLString);
		assertTrue("failure " + POSTJoinWPrunS4.getName().toString() , 
					MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, POSTJoinWPrunS4) >= 10.0);
	}

	
	
	private static void POSTJoinWPrun(QueryManager myAW){
		File POSTJoinWPrun = new File("./results/POSTJoinWPrun.xml"); 
		String SQLString = 
				"DROP TABLE TQ121 IF EXISTS; CREATE TABLE TQ121 AS " +
				"SELECT DISTINCT ECaaECS.EXCEPTIONCASESTEPID, UCSaaEC.USECASESTEPID  " + 
				"FROM UCSaaEC" + " " +
				"INNER JOIN ECaaECS ON ECaaECS.EXCEPTIONCASEID = UCSaaEC.EXCEPTIONCASEID;" +
				// "CREATE HASH INDEX ON TQ121 (EXCEPTIONCASEID);" +
				"CREATE HASH INDEX ON TQ121 (EXCEPTIONCASESTEPID);" +
				"CREATE HASH INDEX ON TQ121 (USECASESTEPID);" +
				"SELECT * " +
				"FROM TQ121 " +
				"INNER JOIN CCaaUCS ON TQ121.USECASESTEPID = CCaaUCS.USECASESTEPID;"; // +
				//"DROP TABLE TQ121 IF EXISTS";
		System.out.println(SQLString);
		assertTrue("failure " + POSTJoinWPrun.getName().toString() , 
					MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, POSTJoinWPrun) >= 10.0);
		//myAW.QueryToXML(SQLString, POSTJoinWPrun);
	}

	
	private static void SkipJoin_R_SC_CC_woSkip(QueryManager myAW){
		File SkipJoin_R_SC_CC_woSkip = new File("./results/SkipJoin_R_SC_CC_woSkip.xml"); 
		String SQLString =
				"SELECT * " + // SD.G70TableName + ".GOALID" + " " + 
				"FROM " + SD.R_SCP10kTableName+ " " +
				"INNER JOIN " + SD.SCP15kTableName + " " + 
				"ON " + SD.SCP15kTableName + ".SUBCOMPONENTID = " + SD.R_SCP10kTableName + ".SUBCOMPONENTID " +
				"INNER JOIN " + SD.SCP_CC12kTableName + " " + 
				"ON " + SD.SCP_CC12kTableName + ".SUBCOMPONENTID = " + SD.SCP15kTableName + ".SUBCOMPONENTID";  
		System.out.println(SQLString);
		assertTrue("failure " + SkipJoin_R_SC_CC_woSkip.getName().toString() ,
		MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, SkipJoin_R_SC_CC_woSkip) >= 30.0);
		//myAW.QueryToXML(SQLString, SkipJoin_R_SC_CC_woSkip);
	}
	
	private static void SkipJoin_R_SC_CC_wSkip(QueryManager myAW){
		File SkipJoin_R_SC_CC_wSkip = new File("./results/SkipJoin_R_SC_CC_wSkip.xml"); 
		String SQLString =
				"SELECT * " + // SD.G70TableName + ".GOALID" + " " + 
				"FROM " + SD.R_SCP10kTableName+ " " +
				"INNER JOIN " + SD.SCP_CC12kTableName + " " + 
				"ON " + SD.SCP_CC12kTableName + ".SUBCOMPONENTID = " + SD.R_SCP10kTableName + ".SUBCOMPONENTID";  
		System.out.println(SQLString);
		assertTrue("failure " + SkipJoin_R_SC_CC_wSkip.getName().toString() ,
		MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, SkipJoin_R_SC_CC_wSkip) >= 30.0);
		//myAW.QueryToXML(SQLString, SkipJoin_R_SC_CC_wSkip);
	}
//	"DROP TABLE TQ121 IF EXISTS; CREATE TABLE TQ121 AS " +
//	"SELECT  ECaaECS.*, UCSaaEC.USECASESTEPID  " + 
//	"FROM UCSaaEC" + " " +
//	"INNER JOIN ECaaECS ON ECaaECS.EXCEPTIONCASEID = UCSaaEC.EXCEPTIONCASEID " +
//	"INNER JOIN CCaaUCS ON UCSaaEC.USECASESTEPID = CCaaUCS.USECASESTEPID;" +
//	"DROP TABLE TQ121 IF EXISTS";			

	// C
	private static void ExpenUDFFirstUDF0_1(QueryManager myAW){
		File ExpenUDFFirstUDF0_1 = new File("./results/ExpenUDFFirstUDF0_1.xml"); 
		String SQLString =
				"SELECT " + "FAULTPRONE(" + SD.CC10kTableName + ".CLASSES) AS FR " +
				"FROM " + SD.CC10kTableName;
		System.out.println(SQLString);
		assertTrue("failure " + ExpenUDFFirstUDF0_1.getName().toString() ,
		MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, ExpenUDFFirstUDF0_1) >= 30.0);
		//myAW.QueryToXML(SQLString, ExpenUDFFirstUDF0_1);
	}
	
	private static void ExpenUDFFirstUDF0_2(QueryManager myAW){
		File ExpenUDFFirstUDF0_2 = new File("./results/ExpenUDFFirstUDF0_2.xml"); 
		String SQLString =
				"SELECT " + "FAULTPRONE(" + SD.CC10kTableName + ".CLASSES) AS FR " +
				"FROM " + SD.CC10kTableName + " " +
				"WHERE " + SD.CC10kTableName + ".CREATEDBY = 'Caleb'";
		System.out.println(SQLString);
		assertTrue("failure " + ExpenUDFFirstUDF0_2.getName().toString() ,
		MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, ExpenUDFFirstUDF0_2) >= 30.0);
		//myAW.QueryToXML(SQLString, ExpenUDFFirstUDF0_2);
	}
	
	// UDFfirst step1: run UDF first 
	private static void ExpenUDFFirstUDF1_1(QueryManager myAW){
		File ExpenUDFFirstUDF1_1 = new File("./results/ExpenUDFFirstUDF1_1.xml"); 
		String SQLString =
				"DROP TABLE TT1 IF EXISTS; CREATE TABLE TT1 AS " +
				"SELECT " + SD.CC10kTableName + ".ClassName, " + 
						"FAULTPRONE(" + SD.CC10kTableName + ".CLASSES) AS FR " +
				"FROM " + SD.CC10kTableName;
		System.out.println(SQLString);
		assertTrue("failure " + ExpenUDFFirstUDF1_1.getName().toString() ,
		MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, ExpenUDFFirstUDF1_1) >= 30.0);
		//myAW.QueryToXML(SQLString, ExpenUDFFirstUDF1_1);
	}
	
	// UDFfirst step2: create hash index
	private static void ExpenUDFFirstUDF1_1_1(QueryManager myAW){
		File ExpenUDFFirstUDF1_1_1 = new File("./results/ExpenUDFFirstUDF1_1_1.xml"); 
		String SQLString =
				"CREATE HASH INDEX ON TT1 (ClassName);" + " " +
				"CREATE HASH INDEX ON TT1 (FR);";
		System.out.println(SQLString);
		assertTrue("failure " + ExpenUDFFirstUDF1_1_1.getName().toString() ,
		MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, ExpenUDFFirstUDF1_1_1) >= 30.0);
		//myAW.QueryToXML(SQLString, ExpenUDFFirstUDF1_1_1);
	}
	
	// join scp_cc = TT1 = cc_ucs
	private static void ExpenUDFFirstUDF1_2(QueryManager myAW){
		File ExpenUDFFirstUDF1_2 = new File("./results/ExpenUDFFirstUDF1_2.xml"); 
		String SQLString =
				"SELECT *" +
				"FROM " + SD.SCP_CC12kTableName + " " +
				"INNER JOIN " + "TT1" + " " +
				"ON " + "TT1.ClassName = " + SD.SCP_CC12kTableName + ".ClassName " +
				"INNER JOIN " + SD.CC_UCS16kTableName + " " +
				"ON " + SD.CC_UCS16kTableName + ".ClassName = " + SD.SCP_CC12kTableName + ".ClassName";
		System.out.println(SQLString);
		assertTrue("failure " + ExpenUDFFirstUDF1_2.getName().toString() ,
		MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, ExpenUDFFirstUDF1_2) >= 30.0);
		//myAW.QueryToXML(SQLString, ExpenUDFFirstUDF1_2);
	}
	
	private static void ExpenUDFFirstUDF1_3(QueryManager myAW){
		File ExpenUDFFirstUDF1_3 = new File("./results/ExpenUDFFirstUDF1_3.xml"); 
		String SQLString =
				"DROP TABLE TT1 IF EXISTS;";
		System.out.println(SQLString);
		assertTrue("failure " + ExpenUDFFirstUDF1_3.getName().toString() ,
		MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, ExpenUDFFirstUDF1_3) >= 5.0);
		//myAW.QueryToXML(SQLString, ExpenUDFFirstUDF1_3);
	}
	
	private static void ExpenUDFLaterUDF1_1(QueryManager myAW){
		File ExpenUDFLaterUDF1_1 = new File("./results/ExpenUDFLaterUDF1_1.xml"); 
		String SQLString =
				"DROP TABLE TT1 IF EXISTS; CREATE TABLE TT1 AS" + " " +
				"SELECT " + SD.SCP_CC12kTableName + ".SubcomponentID" + "," + " " +
						SD.SCP_CC12kTableName + ".ClassName" + "," + " " + 
						SD.CC_UCS16kTableName + ".UsecasestepID" + " " +
				"FROM " + SD.SCP_CC12kTableName + " " +
				"INNER JOIN " + SD.CC_UCS16kTableName + " " +
				"ON " + SD.CC_UCS16kTableName + ".ClassName = " + SD.SCP_CC12kTableName + ".ClassName";
		System.out.println(SQLString);
		assertTrue("failure " + ExpenUDFLaterUDF1_1.getName().toString() ,
		MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, ExpenUDFLaterUDF1_1) >= 30.0);
		//myAW.QueryToXML(SQLString, ExpenUDFLaterUDF1_1);
	}


	// step2: create hash index
	private static void ExpenUDFLaterUDF1_2(QueryManager myAW){
		File ExpenUDFLaterUDF1_2 = new File("./results/ExpenUDFLaterUDF1_2.xml"); 
		String SQLString =
				"CREATE HASH INDEX ON TT1 (SubcomponentID);" + " " +
				"CREATE HASH INDEX ON TT1 (ClassName);" + " " +
				"CREATE HASH INDEX ON TT1 (UseCasestepID);";
		System.out.println(SQLString);
		assertTrue("failure " + ExpenUDFLaterUDF1_2.getName().toString() ,
		MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, ExpenUDFLaterUDF1_2) >= 30.0);
		//myAW.QueryToXML(SQLString, ExpenUDFLaterUDF1_2);
	}
	
	private static void ExpenUDFLaterUDF1_3(QueryManager myAW){
		File ExpenUDFLaterUDF1_3 = new File("./results/ExpenUDFLaterUDF1_3.xml"); 
		String SQLString =
				"DROP TABLE TT2 IF EXISTS; CREATE TABLE TT2 AS" + " " +
				"SELECT " + SD.CC10kTableName + ".CLASSES, " + SD.CC10kTableName + ".ClassName " +
				"FROM " + SD.CC10kTableName + " " +
				"INNER JOIN TT1 " +
				"ON " + SD.CC10kTableName + ".ClassName = " + "TT1.ClassName " +
				"GROUP BY " +SD.CC10kTableName + ".ClassName" ; 
		System.out.println(SQLString);
		assertTrue("failure " + ExpenUDFLaterUDF1_3.getName().toString() ,
		MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, ExpenUDFLaterUDF1_3) >= 30.0);
		//myAW.QueryToXML(SQLString, ExpenUDFLaterUDF1_3);
	}
	
	private static void ExpenUDFLaterUDF1_3_1(QueryManager myAW){
		File ExpenUDFLaterUDF1_3_1 = new File("./results/ExpenUDFLaterUDF1_3_1.xml"); 
		String SQLString =
				"DROP TABLE TT3 IF EXISTS; CREATE TABLE TT3 AS" + " " +
				"SELECT TT2.ClassName, FAULTPRONE(" + "TT2" + ".CLASSES) AS FP" + " " +
				"FROM " + "TT2"; 
		System.out.println(SQLString);
		assertTrue("failure " + ExpenUDFLaterUDF1_3_1.getName().toString() ,
		MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, ExpenUDFLaterUDF1_3_1) >= 30.0);
		// myAW.QueryToXML(SQLString, ExpenUDFLaterUDF1_3_1);
	}


	private static void ExpenUDFLaterUDF1_4(QueryManager myAW){
		File ExpenUDFLaterUDF1_4 = new File("./results/ExpenUDFLaterUDF1_4.xml"); 
		String SQLString =
				"CREATE HASH INDEX ON TT3 (ClassName);" + " " +
				"CREATE HASH INDEX ON TT3 (FP);";
		System.out.println(SQLString);
		assertTrue("failure " + ExpenUDFLaterUDF1_4.getName().toString() ,
		MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, ExpenUDFLaterUDF1_4) >= 30.0);
		//myAW.QueryToXML(SQLString, ExpenUDFLaterUDF1_4);
	}

	private static void ExpenUDFLaterUDF1_5(QueryManager myAW){
		File ExpenUDFLaterUDF1_5 = new File("./results/ExpenUDFLaterUDF1_5.xml"); 
		String SQLString =
				"SELECT *" + " " +
				"FROM " + "TT3" + " " + 
				"INNER JOIN TT1" + " " +
				"ON  TT1.ClassName = TT3.ClassName"; 
		System.out.println(SQLString);
		assertTrue("failure " + ExpenUDFLaterUDF1_5.getName().toString() ,
		MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, ExpenUDFLaterUDF1_5) >= 30.0);
		//myAW.QueryToXML(SQLString, ExpenUDFLaterUDF1_5);
	}
	
	private static void ExpenUDFLaterUDF1_6(QueryManager myAW){
		File ExpenUDFLaterUDF1_6 = new File("./results/ExpenUDFLaterUDF1_6.xml"); 
		String SQLString =
				"DROP TABLE TT1 IF EXISTS;";
		System.out.println(SQLString);
		assertTrue("failure " + ExpenUDFLaterUDF1_6.getName().toString() ,
		MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, ExpenUDFLaterUDF1_6) >= 5.0);
		//myAW.QueryToXML(SQLString, ExpenUDFLaterUDF1_6);
	}
	

}