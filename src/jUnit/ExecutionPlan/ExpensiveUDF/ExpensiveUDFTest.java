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


public class ExpensiveUDFTest {

	String SQLString;
	//	private static final String REQTableName = "\"Requirements.ReqSheet\"";
	//	private static final String CCTableName = "\"codeclasses.codeclass\"";	

	
	public static Boolean setupIsDone = false;
	private static final String H2PROTO = "jdbc:h2:";
	private static final String IH2FP = "./Data/TestCaseDataBases/";
	private static final String IH2DBName = "ExpensiveUDFTest";
	private static final String TRACELEVEL = ";TRACE_LEVEL_FILE=3;TRACE_MAX_FILE_SIZE=20";
	private static String IH2DBURL;
	
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
		
		CreateTablesInMemory.createTablesInMemory(myAW);

		//read CSV trace matrix
		String ArbSQL = "DROP TABLE "+ SD.TMTableName5k +" IF EXISTS; CREATE TABLE "+ SD.TMTableName5k +" AS SELECT * FROM CSVREAD('"+ SD.TMSheet5kFP +"');";
		myAW.arbitrarySQL(ArbSQL);
		setupIsDone = true;
		}
	}

	@Test
	public void test()
	{	
		// TO-DO: register tables
		myAW.RegisterCompiledUDF("FAULTPRONE", "src.UDF.isFaultProne");
		
		File TQ1212 = new File("./results/TQ1212.xml");
		SQLString =
				//"SELECT FAULTPRONE(" +  SD.CCTableName5k + ".CLASSES) AS FAULTPRONE " +
				"SELECT count(*) " +
				"FROM " + SD.CCTableName5k + ";"; //+ ";";// 
				//"WHERE " + SD.CCTableName5k + ".CREATEDBY IS NOT NULL;";
		MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, TQ1212);
		myAW.QueryToXML(SQLString, TQ1212);

		File TQ1213 = new File("./results/TQ1213.xml");
		SQLString =
				//"SELECT FAULTPRONE(" +  CCTableName5k + ".CLASSES) AS FAULTPRONE " +
				"SELECT count(*) " +
				"FROM " + SD.TMTableName5k + ";"; //+ ";";// 
				//"WHERE " + SD.CCTableName5k + ".CREATEDBY IS NOT NULL;";
		MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, TQ1213);
		myAW.QueryToXML(SQLString, TQ1213);


		File TQ12 = new File("./results/TQ12.xml");
		SQLString =  //"DROP TABLE TQ112 IF EXISTS; CREATE TABLE TQ112 AS " + //TEMPORARY
				"SELECT * " +
				"FROM " + SD.CCTableName5k + " " +
				"INNER JOIN " + SD.TMTableName5k + " " + 
				"ON " + SD.TMTableName5k + ".ClassName = " + SD.CCTableName5k + ".ClassName " +
				"WHERE " + SD.CCTableName5k + ".CREATEDBY = 'Caleb';";
		MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, TQ12);
		myAW.QueryToXML(SQLString, TQ12);
		

		//TQudfWhere(myAW);
		//TQ12(myAW);
		File TQudfWhere = new File("./results/TQudfWhere.xml");
		String SQLString =  
				"SELECT * " +
				"FROM " + SD.CCTableName5k + " " +
				"INNER JOIN " + SD.TMTableName5k + " " + 
				"ON " + SD.TMTableName5k + ".ClassName = " + SD.CCTableName5k + ".ClassName " +
				"WHERE " + "FAULTPRONE('" + SD.CCTableName5k + ".CLASSES') = 1 "+ " AND " + SD.CCTableName5k + ".CREATEDBY = 'Caleb';";
		 //"SELECT * FROM codeclass  WHERE FAULTPRONE(CODECLASS.CLASSES) = 1;";
		MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, TQudfWhere);
//		myAW.QueryToXML(SQLString, TQudfWhere);
	}
	
	private static void TQudfWhere(QueryManager myAW){
		File TQudfWhere = new File("./results/TQudfWhere.xml");
		String SQLString =  //"DROP TABLE TQ112 IF EXISTS; CREATE TABLE TQ112 AS " + //TEMPORARY
				"SELECT * " +
				"FROM " + SD.CCTableName5k + " " +
				"INNER JOIN " + SD.TMTableName5k + " " + 
				"ON " + SD.TMTableName5k + ".ClassName = " + SD.CCTableName5k + ".ClassName " +
				"WHERE " + "FAULTPRONE(\"" + SD.CCTableName5k + "\".CLASSES) = 1 "+ " AND " + SD.CCTableName5k + ".CREATEDBY = 'Caleb';";
		MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, TQudfWhere);
		//myAW.QueryToXML(SQLString, TQudfWhere);
	}
	
	private static void TQ12(QueryManager myAW){
		File TQudfWhere = new File("./results/TQudfWhere.xml");
		String SQLString =  //"DROP TABLE TQ112 IF EXISTS; CREATE TABLE TQ112 AS " + //TEMPORARY
				"SELECT * " +
				"FROM " + SD.CCTableName5k + " " +
				"INNER JOIN " + SD.TMTableName5k + " " + 
				"ON " + SD.TMTableName5k + ".ClassName = " + SD.CCTableName5k + ".ClassName " +
				"WHERE " + "FAULTPRONE(\"" + SD.CCTableName5k + "\".CLASSES) = 1 "+ " AND " + SD.CCTableName5k + ".CREATEDBY = 'Caleb';";
		MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, TQudfWhere);
		myAW.QueryToXML(SQLString, TQudfWhere);

	}


}
