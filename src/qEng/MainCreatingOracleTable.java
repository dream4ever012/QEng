package qEng;

import java.io.File;

import ResourceStrings.OS;
import ResourceStrings.SD;
import optimizer.AskWise;
import optimizer.QueryManager;
import utils.CreateCSVFromExcel;
import utils.CreateTablesInMemory;
import utils.ResultSetUtils;

//import java.sql.Connection;
//import java.sql.Driver;
//import java.sql.DriverManager;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.sql.Statement;

//TODO: add XLSX support to the driver
//TODO: discover use syntax for .csv , xml etc. the xlSQL_Y8 is a framework for adding files to a HSQLDB in memory.
//TODO: migrate all useful code or tests from this file and then delete it.




public class MainCreatingOracleTable {

	public static QueryManager myAW;
	public static QueryManager myOAW;
	private static boolean setupIsDone = false;
	private static final String H2PROTO = "jdbc:h2:";
	private static final String IH2FP = "./Data/TestCaseDataBases/";
	private static final String IH2DBName = "MainCreatingOracleTable";
	private static final String TRACELEVEL = ";TRACE_LEVEL_FILE=3;TRACE_MAX_FILE_SIZE=20";

	public InternalDB myDB;
	//private static String SQLString;
	//private String IH2DBURL = "jdbc:h2:./Data/TestCaseDataBases/POITests;TRACE_LEVEL_FILE=3;TRACE_MAX_FILE_SIZE=20";
	private static String IH2DBURL;

	private static final String ResultsURL = "./Results/MainCreatingOracleTable/";

	public static void main(String[] args)
	{
		
		try {
			Class.forName("org.h2.Driver").newInstance();
			Class.forName("org.h2.Driver");
			
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if(!setupIsDone){
			IH2DBURL = H2PROTO + IH2FP + IH2DBName + TRACELEVEL;
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
/*
			myAW.ImportSheet(SD.REQSheetFP,SD.REQTableName);
			myAW.ImportSheet(SD.CCSheetFP,SD.CCTableName);

*/			
			
			//CreateTablesInMemory.createTablesInMemory(myAW);
			
			CreateCSVFromExcel.createCSVFromExcel(myAW);
			
			//read CSV trace matrix
			String ArbSQL = "DROP TABLE "+ SD.TMTableName +" IF EXISTS; CREATE TABLE "+ SD.TMTableName +" AS SELECT * FROM CSVREAD('"+ SD.TMFilePath +"');";
			myAW.arbitrarySQL(ArbSQL);
			setupIsDone = true;
			
			//myAW.ImportSheet(SD.CP10kFP, SD.CP10kTableName);
			//ResultSetUtils.CreateOracleTable(myAW, myOAW, OS.URL, OS.User, OS.Pass, SD.CP10kTableName);
			
/*			myAW.ImportSheet(SD.SCP15kFP, SD.SCP15kTableName);
			ResultSetUtils.CreateOracleTable(myAW, myOAW, OS.URL, OS.User, OS.Pass, SD.SCP15kTableName);
			*/
			//myAW.ImportSheet(SD.G70FP, SD.G70TableName);
			//ResultSetUtils.CreateOracleTable(myAW, myOAW, OS.URL, OS.User, OS.Pass, SD.G70TableName);

			//myAW.ImportSheet(SD.REQSheetTC1FP,SD.REQTableNameTC1);
			//ResultSetUtils.CreateOracleTable(myAW, myOAW, OS.URL, OS.User, OS.Pass, SD.REQTableNameTC1);
			// CreateTablesInMemory.createTablesInMemory(myAW);
						/*ResultSetUtils.CreateOracleTable(myAW, myOAW, OS.URL, OS.User, OS.Pass, SD.CC10kTableName);
			ResultSetUtils.CreateOracleTable(myAW, myOAW, OS.URL, OS.User, OS.Pass, SD.CC_UCS16kTableName);
			ResultSetUtils.CreateOracleTable(myAW, myOAW, OS.URL, OS.User, OS.Pass, SD.CC_SCP12kTableName);
			ResultSetUtils.CreateOracleTable(myAW, myOAW, OS.URL, OS.User, OS.Pass, SD.G70TableName);
			ResultSetUtils.CreateOracleTable(myAW, myOAW, OS.URL, OS.User, OS.Pass, SD.G_UC8kTableName);
			ResultSetUtils.CreateOracleTable(myAW, myOAW, OS.URL, OS.User, OS.Pass, SD.UC10kTableName);*//*
			ResultSetUtils.CreateOracleTable(myAW, myOAW, OS.URL, OS.User, OS.Pass, SD.UC_UCS15kTableName);
			ResultSetUtils.CreateOracleTable(myAW, myOAW, OS.URL, OS.User, OS.Pass, SD.UCS20kTableName);*/
/*			ResultSetUtils.CreateOracleTable(myAW, myOAW, OS.URL, OS.User, OS.Pass, SD.UCS_EC16kTableName);
			ResultSetUtils.CreateOracleTable(myAW, myOAW, OS.URL, OS.User, OS.Pass, SD.EC10kTableName);
			ResultSetUtils.CreateOracleTable(myAW, myOAW, OS.URL, OS.User, OS.Pass, SD.EC_ECS24kTableName);
			ResultSetUtils.CreateOracleTable(myAW, myOAW, OS.URL, OS.User, OS.Pass, SD.ECS30kTableName);
			ResultSetUtils.CreateOracleTable(myAW, myOAW, OS.URL, OS.User, OS.Pass, SD.R70TableName);
			ResultSetUtils.CreateOracleTable(myAW, myOAW, OS.URL, OS.User, OS.Pass, SD.RQ_CP7kTableName);
			ResultSetUtils.CreateOracleTable(myAW, myOAW, OS.URL, OS.User, OS.Pass, SD.CP10kTableName);
			ResultSetUtils.CreateOracleTable(myAW, myOAW, OS.URL, OS.User, OS.Pass, SD.CP_SCP12kTableName);
			ResultSetUtils.CreateOracleTable(myAW, myOAW, OS.URL, OS.User, OS.Pass, SD.REQTableNameTC1);
			ResultSetUtils.CreateOracleTable(myAW, myOAW, OS.URL, OS.User, OS.Pass, SD.CCTableName);
			ResultSetUtils.CreateOracleTable(myAW, myOAW, OS.URL, OS.User, OS.Pass, SD.SCP15kTableName);
			ResultSetUtils.CreateOracleTable(myAW, myOAW, OS.URL, OS.User, OS.Pass, SD.UC10kTableName);*/

			//Retrieving an xml representation of the join of the three tables CodeClass , TM and Requirements
/*			SQLString = "SELECT " + SD.REQTableName + ".*, " + SD.CCTableName + ".*" + " " +
					"FROM " + SD.REQTableName + " " +
					"INNER JOIN " + SD.TMTableName + " " +
					"ON " + SD.TMTableName + ".ID = " + SD.REQTableName + ".ID" + " " +
					"INNER JOIN " + SD.CCTableName + " " +
					"ON " + SD.CCTableName + ".ClassName = " + SD.TMTableName + ".ClassName;";

			File TripleJoin = new File(ResultsURL + "TraceTripleJoin.xml");

			//myDB.QueryToXML(SQLString, TripleJoin);
			MeasureCostToRS.measureCostToRS(myAW, SQLString, TripleJoin);

			*/
			ResultSetUtils.CreateOracleTable(myAW, myOAW, OS.URL, OS.User, OS.Pass, SD.CC_SCP12kTableName);
			
		}
	}


}





























































/*
	// excel file URL until I can make them dynamic jdbc:nilostep:excel:[FullPathToTheDirectoryContainingTheExcelFiles]



//	private static final String REQTableName = "\"Requirements.ReqSheet\"";
//	private static final String CCTableName = "\"codeclasses.codeclass\"";	
	private static final String TMTableName = "CC_REQ_TM";

	private static final String REQTableNameTC1 = "\"RequirementsTC1.ReqSheet\"";
	private static final String CCTableNameTC1 = "\"codeclassTC1.codeclass\"";
	private static final String CCTableName5k = "\"codeclass5k.codeclass\"";
	private static final String TMTableNameTC1 = "CC_REQ_TMTC1";
	private static final String TMTableNameTC2 = "CC_REQ_TMTC2";
	private static final String TMTableName5k = "CC_REQ_TM5k";




	//TODO: fix resource with CreateLink when using y8SQL, so far most of our problems are in Y8
	//TODO: fix issue with Y8 where it closes the database if two instances of Y8 are pointing to different folders on the same machine
	//TODO: create table link object interface, to allow sentinal connections to be held for linked tables to speed up performance
	public static void main(String[] args)
	{
		//creates an .xls file through the JDBC driver. I'm just showing off the full SQL support for files.
		//createReqSheet();
		//createCCSheet();
		//printclasspath();

		//This is how our TiQi front end would create an instance of the internal database system for use
		InternalDB myDB = new InternalH2();

		//This is a demo of how our TiQi front end might create links for accessing external datasources
		//these links are persistent so once created they never have to be created again.
//		myDB.createLink(XLDriver, XLURLBase, null,null, CCTableName);
//		myDB.createLink(XLDriver, XLURLBase, null,null, REQTableName);
//		myDB.createLink(XLDriver, XLURLBase, null,null, CCTableNameTC);
//		myDB.createLink(XLDriver, XLURLBase, null,null, CCTableName5k);
//		myDB.createLink(XLDriver, XLURLBase, null,null, REQTableNameTC1);



		//This is an example of an arbirary SQL command that reads the trace matrix info from a .csv file

		String ArbSQL = "DROP TABLE "+ TMTableName +" IF EXISTS; CREATE TABLE "+ TMTableName +" AS SELECT * FROM CSVREAD('./Data/CC-REQ-TM.csv');";
		myDB.arbitrarySQL(ArbSQL);

		String ArbSQL1 = "DROP TABLE "+ TMTableNameTC1 +" IF EXISTS; CREATE TABLE "+ TMTableNameTC1 +" AS SELECT * FROM CSVREAD('./Data/CC-REQ-TMTC1.csv');";
		myDB.arbitrarySQL(ArbSQL1);

		String ArbSQL3 = "DROP TABLE "+ TMTableName5k +" IF EXISTS; CREATE TABLE "+ TMTableName5k +" AS SELECT * FROM CSVREAD('./Data/CC-REQ-TM5k.csv');";
		myDB.arbitrarySQL(ArbSQL3);

		//MUST RUN
		// * create In-memory table

		// createTablesInMemory(myDB);
		// dropTQTables(myDB,2000);
		// TO-DO: probably drop tables?

		// * create In-memory table

		//The following is going to be the execution of the test queries provided to me by Caleb

		String SQLString = null;

		// TMTableNameT
		// REQTableNameT
		// CCTableNameT


		// w/ reduced rows only ==> reducing column ==> both
		File TQ125 = new File("./results/TQ125.xml");
		SQLString =  //"DROP TABLE TQ125 IF EXISTS; CREATE TEMPORARY TABLE TQ125 AS " + 
				"SELECT COUNT(*) " +
				"FROM " + CCTableName5k + " " +
				"INNER JOIN " + TMTableName5k + " " + 
				"ON " + TMTableName5k + ".ClassName = " + CCTableName5k+ ".ClassName;";// +
				//"WHERE " + CCTableName5k + ".CREATEDBY = 'Caleb';";
		TimerUtils.measureCostArbitrary(myDB, SQLString, TQ125);
		myDB.QueryToXML(SQLString, TQ125);

//		xlSQLTest();
	}




		File TQ1612 = new File("./results/TQ1612.xml");
		SQLString = "SELECT COUNT(" + REQTableNameTC1 + ".ID) " +
				"FROM " + REQTableNameTC1 + ", " + TMTableName5k + " " +
				"WHERE " + TMTableName5k + ".ID= " + REQTableNameTC1 + ".ID;";
		TimerUtils.measureCostToRS(myDB, SQLString, TQ1612);
		myDB.QueryToXML(SQLString, TQ1612);

		File TQ16 = new File("./results/TQ16.xml");
		SQLString = "SELECT " + CCTableName5k + ".CreatedOn " +// DATECREATED " + //COUNT(*) " +
				"FROM " + CCTableName5k + ";"; // +
				//"INNER JOIN " + TMTableName5k + " " +
				//"ON " + TMTableName5k + ".ID= " + REQTableNameTC1 + ".ID;";
		TimerUtils.measureCostToRS(myDB, SQLString, TQ16);
		myDB.QueryToXML(SQLString, TQ16);

		File TQ20 = new File("./results/TQ20.xml");
		SQLString =  "SELECT * FROM INFORMATION_SCHEMA.SETTINGS WHERE NAME = 'info.CACHE_MAX_SIZE';";
		TimerUtils.measureCostArbitrary(myDB, SQLString, TQ20);
		myDB.QueryToXML(SQLString, TQ20);


		File TQ1212 = new File("./results/TQ1212.xml");
		SQLString =  "DROP TABLE TQ121 IF EXISTS; CREATE TABLE TQ121 AS " + //TEMPORARY
				"SELECT * " +
				"FROM " + CCTableName5k + " " +
				"WHERE " + CCTableName5k + ".CREATEDBY = 'Caleb';";
		TimerUtils.measureCostArbitrary(myDB, SQLString, TQ1212);

		File TQ1211 = new File("./results/TQ1211.xml");
		SQLString =  //"DROP TABLE TQ121 IF EXISTS; CREATE TABLE TQ121 AS " + //TEMPORARY
				"SELECT * " +
				"FROM TQ121;";
		TimerUtils.measureCostArbitrary(myDB, SQLString, TQ1211);
		ResultSetUtils.getMetaData(myDB, SQLString, TQ1211);

		// w/ reduced rows only ==> reducing column ==> both
		File TQ125 = new File("./results/TQ125.xml");
		SQLString =  "DROP TABLE TQ125 IF EXISTS; CREATE TEMPORARY TABLE TQ125 AS " + 
				"SELECT * " +
				"FROM " + CCTableName5k + " " +
				"INNER JOIN " + TMTableName5k + " " + 
				"ON " + TMTableName5k + ".ClassName = " + CCTableName5k+ ".ClassName;";// +
				//"WHERE " + CCTableName5k + ".CREATEDBY = 'Caleb';";
		TimerUtils.measureCostArbitrary(myDB, SQLString, TQ125);
		// spits out error: duplicate column


		//The following is going to be the execution of the test queries provided to me by Caleb
		String SQLString = null;
		File TQ121 = new File("./results/TQ121.xml");
		SQLString =  "DROP TABLE TQ121 IF EXISTS; CREATE TABLE TQ121 AS " + //TEMPORARY
				"SELECT * " +
				"FROM " + CCTableName5k + " " +
				"WHERE " + CCTableName5k + ".CREATEDBY = 'Caleb';";
		measureCostArbitrary(myDB, SQLString, TQ121);

		File TQ1211 = new File("./results/TQ1211.xml");
		SQLString =  //"DROP TABLE TQ121 IF EXISTS; CREATE TABLE TQ121 AS " + //TEMPORARY
				"SELECT * " +
				"FROM TQ121;";
		measureCostArbitrary(myDB, SQLString, TQ1211);
		getMetaData(myDB, SQLString, TQ1211);


		File TQ122 = new File("./results/TQ122.xml");
		SQLString =  //"DROP TABLE TQ122 IF EXISTS; CREATE TEMPORARY TABLE TQ122 AS " + 
				"SELECT * " +
				"FROM TQ121" + " " +
				"INNER JOIN " + TMTableName5k + " " + 
				"ON " + TMTableName5k + ".ClassName = TQ121.ClassName;";// +
				//"WHERE " + CCTableName5k + ".CREATEDBY = 'Caleb';";
		measureCostArbitrary(myDB, SQLString, TQ122);

		File TQ1221 = new File("./results/TQ1221.xml");
		SQLString =  //"DROP TABLE TQ122 IF EXISTS; CREATE TEMPORARY TABLE TQ122 AS " + 
				"SELECT COUNT(*) " +
				"FROM TQ121;";
		measureCostArbitrary(myDB, SQLString, TQ1221);


		printScanCost(myDB, CCTableName5k);
		printScanCost(myDB, "TQ121");
		printRowCount(myDB, CCTableName5k);
		int temp = rowCount(myDB, CCTableName5k);

		// w/ reduced rows only ==> reducing column ==> both
		File TQ122 = new File("./results/TQ122.xml");
		SQLString =  //"DROP TABLE TQ122 IF EXISTS; CREATE TEMPORARY TABLE TQ122 AS " + 
				"SELECT * " +
				"FROM TQ121" + " " +
				"INNER JOIN " + TMTableName5k + " " + 
				"ON " + TMTableName5k + ".ClassName = TQ121.ClassName;";
		measureCostArbitrary(myDB, SQLString, TQ122);

	File TQ62 = new File("./results/TQ62.xml");
	SQLString = "DROP TABLE TQ62 IF EXISTS; CREATE TEMPORARY TABLE TQ62 AS " +
			"SELECT * " +
			"FROM " + REQTableNameTC1 + " " +
			"WHERE " + REQTableNameTC1 + ".TYPE = 'Functional';";
	measureCostArbitrary(myDB, SQLString, TQ62);

	File TQ64 = new File("./results/TQ64.xml");
	SQLString = "SELECT * " +
		"FROM TQ62" + " " +
		"INNER JOIN "  + TMTableName5k + " " +
		"ON " + TMTableName5k + ".ID= " + "TQ62.ID;";
	measureCostArbitrary(myDB, SQLString, TQ64);

	File TQ12 = new File("./results/TQ12.xml");
	SQLString =  //"DROP TABLE TQ112 IF EXISTS; CREATE TABLE TQ112 AS " + //TEMPORARY
			"SELECT * " +
			"FROM " + CCTableName5k + " " +
			"INNER JOIN " + TMTableName5k + " " + 
			"ON " + TMTableName5k + ".ClassName = " + CCTableName5k + ".ClassName " +
			"WHERE " + CCTableName5k + ".CREATEDBY = 'Caleb';";
	measureCostArbitrary(myDB, SQLString, TQ12);
	myDB.QueryToXML(SQLString, TQ12);

	File TQ13 = new File("./results/TQ13.xml");
	SQLString =  //"DROP TABLE TQ112 IF EXISTS; CREATE TABLE TQ112 AS " + //TEMPORARY
			"SELECT " + CCTableName5k + ".ClassName, CreatedBy " +
			"FROM " + CCTableName5k + " " +
			"INNER JOIN " + TMTableName5k + " " + 
			"ON " + TMTableName5k + ".ClassName = " + CCTableName5k + ".ClassName " +
			"WHERE " + CCTableName5k + ".CREATEDBY = 'Caleb';";
	measureCostArbitrary(myDB, SQLString, TQ13);
	myDB.QueryToXML(SQLString, TQ13);

	// w/ reduced rows only ==> reducing column ==> both
	File TQ124 = new File("./results/TQ124.xml");
	SQLString =  //"DROP TABLE TQ122 IF EXISTS; CREATE TEMPORARY TABLE TQ122 AS " + 
			"SELECT * " +
			"FROM TQ123" + " " +
			"INNER JOIN " + TMTableName5k + " " + 
			"ON " + TMTableName5k + ".ClassName = TQ123.ClassName;";// +
			//"WHERE " + CCTableName5k + ".CREATEDBY = 'Caleb';";
	measureCostArbitrary(myDB, SQLString, TQ124);

	// with predicate reduced rows
	File TQ121 = new File("./results/TQ121.xml");
	SQLString =  "DROP TABLE TQ121 IF EXISTS; CREATE TABLE TQ121 AS " + //TEMPORARY
			"SELECT * " +
			"FROM " + CCTableName5k + " " +
			//"INNER JOIN " + TMTableName5k + " " + 
			//"ON " + TMTableName5k + ".ClassName = " + CCTableName5k + ".ClassName " +
			"WHERE " + CCTableName5k + ".CREATEDBY = 'Caleb';";
	measureCostArbitrary(myDB, SQLString, TQ121);

	// predicate applied: rows and columns reduced
	File TQ123 = new File("./results/TQ123.xml");
	SQLString =  "DROP TABLE TQ123 IF EXISTS; CREATE TABLE TQ123 AS " + //TEMPORARY
			"SELECT ClassName, CreatedBy " +
			"FROM TQ121"  + ";"; //+
			//"INNER JOIN " + TMTableName5k + " " + 
			//"ON " + TMTableName5k + ".ClassName = " + CCTableName5k + ".ClassName " +
			//"WHERE " + TQ121 + ".CreatedBy = 'Caleb';";
	measureCostArbitrary(myDB, SQLString, TQ123);

	// with predicate reduced rows
	File TQ121 = new File("./results/TQ121.xml");
	SQLString =  "DROP TABLE TQ121 IF EXISTS; CREATE TABLE TQ121 AS " + //TEMPORARY
			"SELECT * " +
			"FROM " + CCTableName5k + " " +
			//"INNER JOIN " + TMTableName5k + " " + 
			//"ON " + TMTableName5k + ".ClassName = " + CCTableName5k + ".ClassName " +
			"WHERE " + CCTableName5k + ".CREATEDBY = 'Caleb';";
	measureCostArbitrary(myDB, SQLString, TQ121);

	File TQ12 = new File("./results/TQ12.xml");
	SQLString =  //"DROP TABLE TQ12 IF EXISTS; CREATE TABLE TQ12 AS " + //TEMPORARY
			"SELECT * " +
			"FROM " + CCTableName5k + " " +
			"INNER JOIN " + TMTableName5k + " " + 
			"ON " + TMTableName5k + ".ClassName = " + CCTableName5k + ".ClassName " +
			"WHERE " + CCTableName5k + ".CREATEDBY = 'Caleb';";
	measureCostArbitrary(myDB, SQLString, TQ12);
	myDB.QueryToXML(SQLString, TQ12);

	File TQ112 = new File("./results/TQ112.xml");
	SQLString =  //"DROP TABLE TQ112 IF EXISTS; CREATE TABLE TQ112 AS " + //TEMPORARY
			"SELECT * " +
			"FROM " + CCTableName5k + " " +
			"INNER JOIN " + TMTableName5k + " " + 
			"ON " + TMTableName5k + ".ClassName = " + CCTableName5k + ".ClassName;";
	measureCostArbitrary(myDB, SQLString, TQ112);
	myDB.QueryToXML(SQLString, TQ112);

	File TQ11 = new File("./results/TQ11.xml");
	SQLString = "SELECT " + REQTableNameTC1 + ".*, " + CCTableName5k + ".*" + " " +
			"FROM " + REQTableNameTC1 + " " +
			"INNER JOIN " + TMTableName5k + " " +
			"ON " + TMTableName5k + ".ID = " + REQTableNameTC1 + ".ID" + " " +
			"INNER JOIN " + CCTableName5k + " " +
			"ON " + CCTableName5k + ".ClassName = " + TMTableName5k + ".ClassName;";
	measureCostArbitrary(myDB, SQLString, TQ11);
	myDB.QueryToXML(SQLString, TQ11);

	// triple join1

	// triple join1: switched join order
	File TQ12 = new File("./results/TQ12.xml");
	SQLString = "SELECT " + REQTableNameTC1 + ".*, " + CCTableName5k + ".*" + " " +
			"FROM " + REQTableNameTC1 + " " +
			"INNER JOIN " + CCTableName5k + " " +
			"ON " + CCTableName5k + ".ClassName = " + TMTableName5k + ".ClassName " +
			"INNER JOIN " + TMTableName5k + " " +
			"ON " + TMTableName5k + ".ID = " + REQTableNameTC1 + ".ID;";		
	measureCostArbitrary(myDB, SQLString, TQ12);

	// triple join1
	File TQ11 = new File("./results/TQ11.xml");
	SQLString = "SELECT " + REQTableNameTC1 + ".*, " + CCTableName5k + ".*" + " " +
			"FROM " + REQTableNameTC1 + " " +
			"INNER JOIN " + TMTableName5k + " " +
			"ON " + TMTableName5k + ".ID = " + REQTableNameTC1 + ".ID" + " " +
			"INNER JOIN " + CCTableName5k + " " +
			"ON " + CCTableName5k + ".ClassName = " + TMTableName5k + ".ClassName;";
	measureCostArbitrary(myDB, SQLString, TQ11);

	//  
				// JOIN W/ predicate (selection)
	File TQ62 = new File("./results/TQ62.xml");
	SQLString = "DROP TABLE TQ62 IF EXISTS; CREATE TEMPORARY TABLE TQ62 AS " +
			"SELECT * " +
			"FROM " + REQTableNameTC1 + " " +
			"WHERE " + REQTableNameTC1 + ".TYPE = 'Functional';";
	measureCostArbitrary(myDB, SQLString, TQ62);

	// selection first scheme  
	File TQ65 = new File("./results/TQ64.xml");
	SQLString = "SELECT * " +
			"FROM " + TMTableName5k2 + " " +
			"INNER JOIN TQ62 " +
			"ON " + TMTableName5k2 + ".ID= " + "TQ62.ID;";
	measureCostArbitrary(myDB, SQLString, TQ65);
	File TQ64 = new File("./results/TQ64.xml");
	SQLString = "SELECT * " +
			"FROM " + TMTableName5k + " " +
			"INNER JOIN TQ62 " +
			"ON " + TMTableName5k + ".ID= " + "TQ62.ID;";
	measureCostArbitrary(myDB, SQLString, TQ64);

	File TQ63 = new File("./results/TQ63.xml");
	SQLString = "SELECT * " +
			"FROM TQ62 " + 
			"INNER JOIN " + TMTableName5k + " " +
			"ON " + TMTableName5k + ".ID= " + "TQ62.ID;";
	measureCostArbitrary(myDB, SQLString, TQ63);


	File TQ62 = new File("./results/TQ62.xml");
	SQLString = "DROP TABLE TQ62 IF EXISTS; CREATE TEMPORARY TABLE TQ62 AS " +
			"SELECT * " +
			"FROM " + REQTableNameTC1 + " " +
			"WHERE " + REQTableNameTC1 + ".TYPE = 'Functional';";
	measureCostArbitrary(myDB, SQLString, TQ62);


	File TQ61 = new File("./results/TQ61.xml");
	SQLString = " SELECT * " +
			"FROM " + REQTableNameTC1 + " " +
			"INNER JOIN " + TMTableName5k + " " +
			"ON " + TMTableName5k + ".ID= " + REQTableNameTC1 + ".ID " +
			"WHERE " + REQTableNameTC1 + ".TYPE = 'Functional';";
	//myDB.QueryToXML(SQLString, TQ61);
	measureCostToRS(myDB, SQLString, TQ61);

	File TQ6 = new File("./results/TQ6.xml");
	SQLString = "SELECT * " +
			"FROM " + REQTableNameTC1 + " " +
			"INNER JOIN " + TMTableName5k + " " +
			"ON " + TMTableName5k + ".ID= " + REQTableNameTC1 + ".ID;";
	measureCostToRS(myDB, SQLString, TQ6);

	File TQ9 = new File("./results/TQ9.xml");
	SQLString = "SELECT * " +//"SELECT COUNT(*) " +
			"FROM " + CCTableNameTC1 + " " +
			"INNER JOIN " + TMTableNameTC2 + " " +
			"ON " + TMTableNameTC2 + ".ClassName= " + CCTableNameTC1 + ".ClassName;";
	//measureCostToXml(myDB, SQLString, TQ9);
	measureCostToRS(myDB, SQLString, TQ9);

	File TQ7 = new File("./results/TQ7.xml");
	SQLString = "SELECT COUNT(*) " +
			"FROM " + TMTableName5k + " " +
			"INNER JOIN " + REQTableNameTC1 + " " +
			"ON " + TMTableName5k + ".ID= " + REQTableNameTC1 + ".ID" +
			"WHEN " + REQTableNameTC1 +".TYPE= 'Functional';";
	measureCostToRS(myDB, SQLString, TQ7);	

		File TQ9 = new File("./results/TQ9.xml");
	SQLString = "SELECT * " +//"SELECT COUNT(*) " +
			"FROM " + CCTableNameTC1 + " " +
			"INNER JOIN " + TMTableNameTC2 + " " +
			"ON " + TMTableNameTC2 + ".ClassName= " + CCTableNameTC1 + ".ClassName;";
	//measureCostToXml(myDB, SQLString, TQ9);
	measureCostToRS(myDB, SQLString, TQ9);

	File TQ10 = new File("./results/TQ10.xml");
	SQLString = "SELECT * " +//"SELECT COUNT(*) " +
			"FROM " + TMTableNameTC1 + " " +
			"INNER JOIN " + CCTableNameTC1 + " " +
			"ON " + TMTableNameTC1 + ".ClassName= " + CCTableNameTC1 + ".ClassName;";
	//measureCostToXml(myDB, SQLString, TQ10);
	measureCostToRS(myDB, SQLString, TQ10);

	File TQ10 = new File("./results/TQ10.xml");
	SQLString = "SELECT * " +//"SELECT COUNT(*) " +
			"FROM " + TMTableNameTC1 + " " +
			"INNER JOIN " + CCTableNameTC1 + " " +
			"ON " + TMTableNameTC1 + ".ClassName= " + CCTableNameTC1 + ".ClassName;";
	//measureCostToXml(myDB, SQLString, TQ10);
	measureCostToRS(myDB, SQLString, TQ10);

	File TQ67 = new File("./results/TQ67.xml");
	SQLString = "EXPLAIN SELECT COUNT(*) " +
			"FROM  TMTableNameTC1 " +
			"INNER JOIN REQTableNameTC1 " +
			"ON TMTableNameTC1.ID= " + "REQTableNameTC1.ID;";
	measureCostToRS(myDB, SQLString, TQ67);
	myDB.QueryToXML(SQLString, TQ67);






	// run query with the memory

	//String ArbSQL1 = "DROP TABLE "+ TMTableNameTC1 +" IF EXISTS; CREATE TABLE "+ TMTableNameTC1 +" AS SELECT * FROM CSVREAD('./Data/CC-REQ-TM.csv');";
	//myDB.arbitrarySQL(ArbSQL1);


}
 */