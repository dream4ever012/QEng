package jUnit.ExecutionPlan.ExpensiveUDF;

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


public class ExpensiveUDFTestOR {
	//	private static final String REQTableName = "\"Requirements.ReqSheet\"";
	//	private static final String CCTableName = "\"codeclasses.codeclass\"";	

	
	public static Boolean setupIsDone = false;
	private static final String H2PROTO = "jdbc:h2:";
	private static final String IH2FP = "./Data/TestCaseDataBases/";
	private static final String IH2DBName = "ExpensiveUDFTestOR";
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
		
		myAW.ImportSheet(SD.REQSheetFP,SD.REQTableName);
		myAW.ImportSheet(SD.CCSheetFP,SD.CCTableName);
		myAW.ImportSheet(SD.CCSheet5kFP, SD.CCTableName5k);
		
		//read CSV trace matrix
		String ArbSQL = "DROP TABLE "+ SD.TMTableName5k +" IF EXISTS; CREATE TABLE "+ SD.TMTableName5k +" AS SELECT * FROM CSVREAD('"+ SD.TMSheet5kFP +"');";
		myAW.arbitrarySQL(ArbSQL);
		
		// CostOfPred.xml cost: 77 44 63 76 35
		File CostOfPred = new File("./results/CostOfPred.xml");
		SQLString = // "DROP TABLE PredicateTEMP IF EXISTS; CREATE TABLE PredicateTEMP AS " + //TEMPORARY
				"SELECT * " +
				"FROM " + SD.CCTableName5k + " " +
				//"INNER JOIN " + SD.TMTableName5k + " " + 
				//"ON " + SD.TMTableName5k + ".ClassName = " + SD.CCTableName5k + ".ClassName " +
				"WHERE " + SD.CCTableName5k + ".CREATEDBY = 'Caleb';"; // "FAULTPRONE(" + SD.CCTableName5k + ".CLASSES) = 1 "+ " AND " +
		myAW.arbitrarySQL(SQLString);
		//MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, CostOfPred);
		
		// PredicateTEMP.xml cost: 193
		File PredicateTEMP = new File("./results/PredicateTEMP.xml");
		SQLString = "DROP TABLE CCPredicateTEMP IF EXISTS; CREATE TABLE CCPredicateTEMP AS " + //TEMPORARY
				"SELECT * " +
				"FROM " + SD.CCTableName5k + " " +
				//"INNER JOIN " + SD.TMTableName5k + " " + 
				//"ON " + SD.TMTableName5k + ".ClassName = " + SD.CCTableName5k + ".ClassName " +
				"WHERE " + SD.CCTableName5k + ".CREATEDBY = 'Caleb';"; // "FAULTPRONE(" + SD.CCTableName5k + ".CLASSES) = 1 "+ " AND " +
		myAW.arbitrarySQL(SQLString);
		//MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, PredicateTEMP);
		
		// CC_REQ_TM5k cost: 174 191 191 277 231
		TimerUtils.RegisterTM(myAW, SD.TMTableName5k, "CCPredicateTEMP" , "ClassName" , SD.REQTableName, "ID");
		
/*		
		long m1, m2;
		m1 = System.currentTimeMillis();
		myAW.RegisterTM(SD.TMTableName5k, "CCPredicateTEMP" , "ClassName" , SD.REQTableName, "ID");
		m2 = System.currentTimeMillis();
		System.out.println( SD.TMTableName5k+" cost: " + (m2 - m1));
		
*/		
//		CreateTablesInMemory.createTablesInMemory(myAW);


		setupIsDone = true;
		}
	}

	@Test
	public void test()
	{	
		// TO-DO: register tables
		myAW.RegisterCompiledUDF("FAULTPRONE", "src.UDF.isFaultProne");
		
		// UDFFirst: UDF AND predicate; UDFLater: predicate AND UDF
		// UDFFirst(myAW);	// UDFFirst.xml cost: 3265 3710 4894 2681 3061 3660 2898 2786 3004 4305
		// UDFLater(myAW);		// UDFLater.xml cost: 2780 2868 3389 2871 4359 2846 2945 3986 2835 3399
		//////////////////////////
		
		// CostOfPred.xml cost: 77 44 63 76 35
		// CC_REQ_TM5k cost: 174 191 191 277 231
		// UDFLaterWTEMP.xml cost: 3269 4575 3593 2524 3165
		//UDFLaterWTEMP(myAW);
		//UDFLater(myAW);	
		
		
		
	}
	
	private static void Cost(QueryManager myAW){
		File UDFLaterWTEMP = new File("./results/UDFLaterWTEMP.xml");
		String SQLString =
				"SELECT * " +
				"FROM " + "CCPredicateTEMP " + //+ SD.CCTableName5k + " " +
				"INNER JOIN " + SD.TMTableName5k + " " + 
				"ON " + SD.TMTableName5k + ".ClassName = " + "CCPredicateTEMP" + ".ClassName " +
				"WHERE " + "CCPredicateTEMP" + ".CREATEDBY = 'Caleb'" + " AND " + "FAULTPRONE(" + "CCPredicateTEMP" + ".CLASSES) = 1;";
		MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, UDFLaterWTEMP);
	}
	
	//PredicateTEMP
	private static void UDFLaterWTEMP(QueryManager myAW){
		File UDFLaterWTEMP = new File("./results/UDFLaterWTEMP.xml");
		String SQLString =
				"SELECT * " +
				"FROM " + "CCPredicateTEMP " + //+ SD.CCTableName5k + " " +
				"INNER JOIN " + SD.TMTableName5k + " " + 
				"ON " + SD.TMTableName5k + ".ClassName = " + "CCPredicateTEMP" + ".ClassName " +
				"WHERE " + "CCPredicateTEMP" + ".CREATEDBY = 'Caleb'" + " AND " + "FAULTPRONE(" + "CCPredicateTEMP" + ".CLASSES) = 1;";
		MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, UDFLaterWTEMP);
	}
	
	private static void UDFFirst(QueryManager myAW){
		File UDFFirst = new File("./results/UDFFirst.xml");
		String SQLString =
				"SELECT * " +
				"FROM " + SD.CCTableName5k + " " +
				"INNER JOIN " + SD.TMTableName5k + " " + 
				"ON " + SD.TMTableName5k + ".ClassName = " + SD.CCTableName5k + ".ClassName " +
				"WHERE " + "FAULTPRONE(" + SD.CCTableName5k + ".CLASSES) = 1 "+ " AND " + SD.CCTableName5k + ".CREATEDBY = 'Caleb';";
		MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, UDFFirst);
	}
	
	private static void UDFLater(QueryManager myAW){
		File UDFLater = new File("./results/UDFLater.xml");
		String SQLString =
				"SELECT * " +
				"FROM " + SD.CCTableName5k + " " +
				"INNER JOIN " + SD.TMTableName5k + " " + 
				"ON " + SD.TMTableName5k + ".ClassName = " + SD.CCTableName5k + ".ClassName " +
				"WHERE " + SD.CCTableName5k + ".CREATEDBY = 'Caleb'" + " AND " + "FAULTPRONE(" + SD.CCTableName5k + ".CLASSES) = 1;";
		MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, UDFLater);
	}


}
