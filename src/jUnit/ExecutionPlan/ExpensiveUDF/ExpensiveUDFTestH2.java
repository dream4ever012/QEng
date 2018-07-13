package jUnit.ExecutionPlan.ExpensiveUDF;

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


public class ExpensiveUDFTestH2 {
	//	private static final String REQTableName = "\"Requirements.ReqSheet\"";
	//	private static final String CCTableName = "\"codeclasses.codeclass\"";	

	
	public static Boolean setupIsDone = false;
	private static final String H2PROTO = "jdbc:h2:";
	private static final String IH2FP = "./Data/TestCaseDataBases/";
	private static final String IH2DBName = "ExpensiveUDFTestH2";
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
		MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, CostOfPred);
		
		// PredicateTEMP.xml cost: 193
		File PredicateTEMP = new File("./results/PredicateTEMP.xml");
		SQLString = "DROP TABLE CCPredicateTEMP IF EXISTS; CREATE TABLE CCPredicateTEMP AS " + //TEMPORARY
				"SELECT * " +
				"FROM " + SD.CCTableName5k + " " +
				//"INNER JOIN " + SD.TMTableName5k + " " + 
				//"ON " + SD.TMTableName5k + ".ClassName = " + SD.CCTableName5k + ".ClassName " +
				"WHERE " + SD.CCTableName5k + ".CREATEDBY = 'Caleb';"; // "FAULTPRONE(" + SD.CCTableName5k + ".CLASSES) = 1 "+ " AND " +
		myAW.arbitrarySQL(SQLString);
		System.out.println(SQLString);
		//MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, PredicateTEMP);

		// CC_REQ_TM5k cost: 174 191 191 277 231
		TimerUtils.RegisterTM(myAW, SD.TMTableName5k, "CCPredicateTEMP" , "ClassName" , SD.REQTableName, "ID");
		TimerUtils.RegisterTM(myAW, SD.TMTableName5k, SD.CCTableName5k , "ClassName" , SD.REQTableName, "ID");


		File equalizer = new File("./results/equalizer.xml");
		SQLString =
				"SELECT * " +
				"FROM " + "CCPredicateTEMP" + " " +
				"INNER JOIN " + SD.TMTableName5k + " " + 
				"ON " + SD.TMTableName5k + ".ClassName = " + SD.CCTableName5k + ".ClassName " +
				"WHERE " + "FAULTPRONE(" + "CCPredicateTEMP" + ".CLASSES) = 1;"; //+ SD.CCTableName5k + ".CREATEDBY = 'Caleb'" + " AND " + 
		MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, equalizer);
		
		
		
//		CreateTablesInMemory.createTablesInMemory(myAW);


		setupIsDone = true;
		}
	}

	@Test
	public void test()
	{	
		// TO-DO: register tables
		myAW.RegisterCompiledUDF("FAULTPRONE", "src.UDF.isFaultProne");
		
		// benefit of postponing UDF
		//UDFonly1(myAW); // UDFonly1.xml cost: 2290 4630 2602 4601 3501 2637 3220
		//SELECT FAULTPRONE(CCPredicateTEMP.CLASSES) FROM CCPredicateTEMP;
		UDFonly2(myAW); // UDFonly2.xml cost: 6227 5217 7672 4714 5030 4031 6109
		
		//UDFonly1_1(myAW); //2500
		//UDFonly2_1(myAW); //5000
		
		
		// UDFFirst: UDF AND predicate; UDFLater: predicate AND UDF
		// UDFFirst(myAW);	// UDFFirst.xml cost: 3265 3710 4894 2681 3061 3660 2898 2786 3004 4305 4289
		// UDFLater(myAW);	// UDFLater.xml cost: 2780 2868 3389 2871 4359 2846 2945 3986 2835 3399 2587
		
		
		///////2nd experiment
		// created a new table after CreatedBy predicate
		// CompW5k1(myAW);	// CompW5k1.xml cost: 3270 3440 3007 3645 3411
		// case with 
		// CompW5k2(myAW); // CompW5k2.xml cost: 4130 3371 8749 3490 2573
		
		
		// CostOfPred.xml cost: 77 44 63 76 35
		// CC_REQ_TM5k cost: 174 191 191 277 231
		// UDFLaterWTEMP.xml cost: 3269 4575 3593 2524 3165
		// UDFLaterWTEMP(myAW); // 3572 4785 3422 4624 4944
		// UDFLater(myAW);	 
		// UDFFirst(myAW); // 4619 3187 4119 3465 4125
		
		//CompW5k1(myAW); // 3943 3929 3098
		//CompW5k2(myAW); // 4975 4899 3928
		

	}
	
	// Created By 'Caleb' first and then UDF
	private static void UDFonly1(QueryManager myAW){
		File UDFonly1 = new File("./results/UDFonly1.xml");
		String SQLString =
				"SELECT " + "FAULTPRONE(" + "CCPredicateTEMP" + ".CLASSES) " +
				"FROM " + "CCPredicateTEMP" + ";";
				//"INNER JOIN " + SD.TMTableName5k + " " + 
				//"ON " + SD.TMTableName5k + ".ClassName = " + SD.CCTableName5k + ".ClassName " +
				//"WHERE " + "FAULTPRONE(" + "CCPredicateTEMP" + ".CLASSES) = 1;"; //+ SD.CCTableName5k + ".CREATEDBY = 'Caleb'" + " AND " + 
		System.out.println(SQLString);
		MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, UDFonly1);
		//myAW.QueryToXML(SQLString, UDFonly1);
	}
	
	private static void UDFonly1_2(QueryManager myAW){
		File UDFonly1_2 = new File("./results/UDFonly1_2.xml");
		String SQLString =
				"SELECT " + "COUNT(*) " +
				"FROM " + "CCPredicateTEMP" + ";";
				//"INNER JOIN " + SD.TMTableName5k + " " + 
				//"ON " + SD.TMTableName5k + ".ClassName = " + SD.CCTableName5k + ".ClassName " +
				//"WHERE " + "FAULTPRONE(" + "CCPredicateTEMP" + ".CLASSES) = 1;"; //+ SD.CCTableName5k + ".CREATEDBY = 'Caleb'" + " AND " + 
		System.out.println(SQLString);
		MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, UDFonly1_2);
		//myAW.QueryToXML(SQLString, UDFonly1_2);
	}
	
	
	// Created By 'Caleb' first and then UDF
	private static void UDFonly1_1(QueryManager myAW){
		File UDFonly1_1 = new File("./results/UDFonly1_1.xml");
		String SQLString =
				"SELECT " + "COUNT(FAULTPRONE(" + "CCPredicateTEMP" + ".CLASSES)) " +
				"FROM " + "CCPredicateTEMP" + ";";
				//"INNER JOIN " + SD.TMTableName5k + " " + 
				//"ON " + SD.TMTableName5k + ".ClassName = " + SD.CCTableName5k + ".ClassName " +
				//"WHERE " + "FAULTPRONE(" + "CCPredicateTEMP" + ".CLASSES) = 1;"; //+ SD.CCTableName5k + ".CREATEDBY = 'Caleb'" + " AND " + 
		System.out.println(SQLString);
		//MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, UDFonly1_1);
		myAW.QueryToXML(SQLString, UDFonly1_1);
	}
	
	// UDF and then created by 'Caleb'
	private static void UDFonly2(QueryManager myAW){
		File UDFonly2 = new File("./results/UDFonly2.xml");
		String SQLString =
			"SELECT " + "FAULTPRONE(" + SD.CCTableName5k + ".CLASSES) " +
			"FROM " + SD.CCTableName5k + ";";
			//"INNER JOIN " + SD.TMTableName5k + " " + 
			//"ON " + SD.TMTableName5k + ".ClassName = " + SD.CCTableName5k + ".ClassName " +
			//"WHERE " + "FAULTPRONE(" + SD.CCTableName5k + ".CLASSES) = 1 "+ " AND " + SD.CCTableName5k + ".CREATEDBY = 'Caleb';";
		System.out.println(SQLString);
		MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, UDFonly2);
		//myAW.QueryToXML(SQLString, UDFonly2);
	}
	

	private static void UDFonly2_2(QueryManager myAW){
		File UDFonly2_2 = new File("./results/UDFonly2_2.xml");
		String SQLString =
			"SELECT " + "COUNT(*) " +
			"FROM " + SD.CCTableName5k + ";";
			//"INNER JOIN " + SD.TMTableName5k + " " + 
			//"ON " + SD.TMTableName5k + ".ClassName = " + SD.CCTableName5k + ".ClassName " +
			//"WHERE " + "FAULTPRONE(" + SD.CCTableName5k + ".CLASSES) = 1 "+ " AND " + SD.CCTableName5k + ".CREATEDBY = 'Caleb';";
		System.out.println(SQLString);
		MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, UDFonly2_2);
		//myAW.QueryToXML(SQLString, UDFonly2_2);
	}
	
	// UDF and then created by 'Caleb'
	private static void UDFonly2_1(QueryManager myAW){
		File UDFonly2_1 = new File("./results/UDFonly2_1.xml");
		String SQLString =
			"SELECT " + "COUNT(FAULTPRONE(" + SD.CCTableName5k + ".CLASSES) = 1) " +
			"FROM " + SD.CCTableName5k + ";";
			//"INNER JOIN " + SD.TMTableName5k + " " + 
			//"ON " + SD.TMTableName5k + ".ClassName = " + SD.CCTableName5k + ".ClassName " +
			//"WHERE " + "FAULTPRONE(" + SD.CCTableName5k + ".CLASSES) = 1 "+ " AND " + SD.CCTableName5k + ".CREATEDBY = 'Caleb';";
		System.out.println(SQLString);
		//MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, UDFonly2_1);
		myAW.QueryToXML(SQLString, UDFonly2_1);
	}
	
	// CreatedBy predicated pre-executed
	private static void CompW5k1(QueryManager myAW){
		File CompW5k1 = new File("./results/CompW5k1.xml");
		String SQLString =
				"SELECT * " +
				"FROM " + "CCPredicateTEMP " + //+ SD.CCTableName5k + " " +
				//"INNER JOIN " + SD.TMTableName5k + " " + 
				//"ON " + SD.TMTableName5k + ".ClassName = " + "CCPredicateTEMP" + ".ClassName " +
				"WHERE " + "FAULTPRONE(" + "CCPredicateTEMP" + ".CLASSES) = 1;"; //+ "CCPredicateTEMP" + ".CREATEDBY = 'Caleb'" + " AND "  
		System.out.println(SQLString);
		MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, CompW5k1);
	}
	
	// 
	private static void CompW5k2(QueryManager myAW){
		File CompW5k2 = new File("./results/CompW5k2.xml");
		String SQLString =
				"SELECT * " +
				"FROM " + SD.CCTableName5k + " " + //+ SD.CCTableName5k + " " +
				//"INNER JOIN " + SD.TMTableName5k + " " + 
				//"ON " + SD.TMTableName5k + ".ClassName = " + "CCPredicateTEMP" + ".ClassName " +
				"WHERE " + SD.CCTableName5k + ".CREATEDBY = 'Caleb'" + " AND " + "FAULTPRONE(" + SD.CCTableName5k + ".CLASSES) = 1;"; // 
		System.out.println(SQLString);
		MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, CompW5k2);
	}
	
	// to see if h2 optimize for the where predicates
	// predicate CreatedBy ==> UDF
	private static void CompWherePred1(QueryManager myAW){
		File CompWherePred1 = new File("./results/CompWherePred1.xml");
		String SQLString =
				"SELECT * " +
				"FROM " + SD.CCTableName5k + //+ SD.CCTableName5k + " " +
				//"INNER JOIN " + SD.TMTableName5k + " " + 
				//"ON " + SD.TMTableName5k + ".ClassName = " + "CCPredicateTEMP" + ".ClassName " +
				"WHERE " + SD.CCTableName5k + ".CREATEDBY = 'Caleb'" + " AND " + "FAULTPRONE(" + SD.CCTableName5k + ".CLASSES) = 1;"; //  
		System.out.println(SQLString);
		MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, CompWherePred1);
	}

	// UDF ==> predicate CreatedBy
	private static void CompWherePred2(QueryManager myAW){
		File CompWherePred2 = new File("./results/CompWherePred2.xml");
		String SQLString =
				"SELECT * " +
				"FROM " + SD.CCTableName5k + //+ SD.CCTableName5k + " " +
				//"INNER JOIN " + SD.TMTableName5k + " " + 
				//"ON " + SD.TMTableName5k + ".ClassName = " + "CCPredicateTEMP" + ".ClassName " +
				"WHERE " + "FAULTPRONE(" + SD.CCTableName5k + ".CLASSES) = 1" + " AND " + SD.CCTableName5k + ".CREATEDBY = 'Caleb';"; // 
		System.out.println(SQLString);
		MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, CompWherePred2);
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
		System.out.println(SQLString);
		MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, UDFLaterWTEMP);
		//myAW.QueryToXML(SQLString, UDFLaterWTEMP);
	}
	
	private static void UDFFirst(QueryManager myAW){
		File UDFFirst = new File("./results/UDFFirst.xml");
		String SQLString =
				"SELECT * " +
				"FROM " + SD.CCTableName5k + " " +
				//"INNER JOIN " + SD.TMTableName5k + " " + 
				//"ON " + SD.TMTableName5k + ".ClassName = " + SD.CCTableName5k + ".ClassName " +
				"WHERE " + "FAULTPRONE(" + SD.CCTableName5k + ".CLASSES) = 1 "+ " AND " + SD.CCTableName5k + ".CREATEDBY = 'Caleb';";
		System.out.println(SQLString);
		MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, UDFFirst);
		myAW.QueryToXML(SQLString, UDFFirst);
	}
	
	private static void UDFLater(QueryManager myAW){
		File UDFLater = new File("./results/UDFLater.xml");
		String SQLString =
				"SELECT * " +
				"FROM " + "CCPredicateTEMP" + " " +
				//"INNER JOIN " + SD.TMTableName5k + " " + 
				//"ON " + SD.TMTableName5k + ".ClassName = " + SD.CCTableName5k + ".ClassName " +
				"WHERE " + "FAULTPRONE(" + "CCPredicateTEMP" + ".CLASSES) = 1;"; //+ SD.CCTableName5k + ".CREATEDBY = 'Caleb'" + " AND " + 
		System.out.println(SQLString);
		MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, UDFLater);
		myAW.QueryToXML(SQLString, UDFLater);
	}
	

}