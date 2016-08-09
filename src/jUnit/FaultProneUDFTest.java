package jUnit;

import java.io.File;

import org.junit.Before;
import org.junit.Test;

import ResourceStrings.SD;
import optimizer.AskWise;
import optimizer.QueryManager;
import qEng.InternalDB;
import qEng.InternalH2;
import utils.CreateTablesInMemory;
import utils.MeasureCostArbitrary;


public class FaultProneUDFTest {

	String SQLString;
	//	private static final String REQTableName = "\"Requirements.ReqSheet\"";
	//	private static final String CCTableName = "\"codeclasses.codeclass\"";	

	
	public static Boolean setupIsDone = false;
	private static final String H2PROTO = "jdbc:h2:";
	private static final String IH2FP = "./Data/TestCaseDataBases/";
	private static final String IH2DBName = "FaultProneUDFTest";
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
		myAW = new AskWise();
		

			myAW.ImportSheet(SD.REQSheetFP,SD.REQTableName);
			myAW.ImportSheet(SD.CCSheetFP,SD.CCTableName);
			CreateTablesInMemory.createTablesInMemory(myAW);

			/* */
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
		/*
		File TQ1212 = new File("./results/TQ1212.xml");
		SQLString =
				//"SELECT FAULTPRONE(" +  CCTableName5k + ".CLASSES) AS FAULTPRONE " +
				"SELECT count(*) " +
				"FROM " + CCTableName5k + ";"; //+ ";";// 
				//"WHERE " + CCTableName5k + ".CREATEDBY IS NOT NULL;";
		MeasureCostArbitrary.measureCostArbitrary(myDB, SQLString, TQ1212);
		myDB.QueryToXML(SQLString, TQ1212);

		File TQ1213 = new File("./results/TQ1213.xml");
		SQLString =
				//"SELECT FAULTPRONE(" +  CCTableName5k + ".CLASSES) AS FAULTPRONE " +
				"SELECT count(*) " +
				"FROM " + TMTableName5k + ";"; //+ ";";// 
				//"WHERE " + CCTableName5k + ".CREATEDBY IS NOT NULL;";
		MeasureCostArbitrary.measureCostArbitrary(myDB, SQLString, TQ1213);
		myDB.QueryToXML(SQLString, TQ1213);


		File TQ12 = new File("./results/TQ12.xml");
		SQLString =  //"DROP TABLE TQ112 IF EXISTS; CREATE TABLE TQ112 AS " + //TEMPORARY
				"SELECT * " +
				"FROM " + CCTableName5k + " " +
				"INNER JOIN " + TMTableName5k + " " + 
				"ON " + TMTableName5k + ".ClassName = " + CCTableName5k + ".ClassName " +
				"WHERE " + CCTableName5k + ".CREATEDBY = 'Caleb';";
		MeasureCostArbitrary.measureCostArbitrary(myDB, SQLString, TQ12);
		myDB.QueryToXML(SQLString, TQ12);
		 */

		File TQudfWhere = new File("./results/TQudfWhere.xml");
		SQLString =  //"DROP TABLE TQ112 IF EXISTS; CREATE TABLE TQ112 AS " + //TEMPORARY
				"SELECT * " +
				"FROM " + SD.CCTableName5k + " " +
				"INNER JOIN " + SD.TMTableName5k + " " + 
				"ON " + SD.TMTableName5k + ".ClassName = " + SD.CCTableName5k + ".ClassName " +
				"WHERE " + "FAULTPRONE(" + SD.CCTableName5k + ".CLASSES) = 1 "+ " AND " + SD.CCTableName5k + ".CREATEDBY = 'Caleb';";
		MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, TQudfWhere);
		myAW.QueryToXML(SQLString, TQudfWhere);



	}


}
