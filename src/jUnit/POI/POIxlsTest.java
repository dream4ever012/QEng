package jUnit.POI;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Before;
import org.junit.Test;

import qEng.InternalDB;
import qEng.InternalH2;
import utils.MeasureCostToRS;
import utils.POI.SheetReader;

public class POIxlsTest {

	

		public static Boolean setupIsDone = false;
		public static InternalDB myDB;
		String SQLString;

		//private static final String XLDriver = "com.nilostep.xlsql.jdbc.xlDriver"; 
		//private static final String XLURLBase = "jdbc:nilostep:excel:./SecondData/"; 
		private static final String IH2DBURL = "jdbc:h2:./Data/TestCaseDataBases/POITests;TRACE_LEVEL_FILE=3;TRACE_MAX_FILE_SIZE=20";

		//private static final String REQTableName = "\"Requirements.ReqSheet\"";
		//private static final String CCTableName = "\"codeclasses.codeclass\"";
		private static final String REQTableName = "ReqSheet";
		private static final String REQSheetFP = "./SecondData/Requirements.xls";
		private static final String CCTableName = "codeclass";
		private static final String CCSheetFP = "./SecondData/codeclasses.xls";
		private static final String TMTableName = "CC_REQ_TM";
		
		private static final String CC10kFP = "./SecondData/CC.xls";
		private static final String CC10kTableName = "CC";
		private static final String CC_UCS16kFP = "./SecondData/CCaaUCS.xls";
		private static final String CC_UCS16kTableName = "CCaaUCS";
		private static final String CC_SCP12kFP = "./SecondData/CCaaSCP.xls";
		private static final String CC_SCP12kTableName = "CCaaSCP";
		private static final String G70FP = "./SecondData/G.xls";
		private static final String G70TableName = "G";
		private static final String G_UC8kFP = "./SecondData/GaaUC.xls";
		private static final String G_UC8kTableName = "GaaUC";
		private static final String UC_UCS15kFP = "./SecondData/UCaaUCS.xls";
		private static final String UC_UCS15kTableName = "UCaaUCS";
		private static final String UCS20kFP = "./SecondData/UCS.xls";
		private static final String UCS20kTableName = "UCS";
		private static final String UCS_EC16kFP = "./SecondData/UCSaaEC.xls";
		private static final String UCS_EC16kTableName = "UCSaaEC";
		private static final String EC10kFP = "./SecondData/EC.xls";
		private static final String EC10kTableName = "EC";
		private static final String EC_ECS24kFP = "./SecondData/ECaaECS.xls";
		private static final String EC_ECS24kTableName = "ECaaECS";
		private static final String ECS30kFP = "./SecondData/ECS.xls";
		private static final String ECS30kTableName = "ECS";
		private static final String R70FP = "./SecondData/RQ.xls";
		private static final String R70TableName = "RQ";
		private static final String RQ_CP7kFP = "./SecondData/RQaaCP.xls";
		private static final String RQ_CP7kTableName = "RQaaCP";
		private static final String CP10kFP = "./SecondData/CP.xls";
		private static final String CP10kTableName = "CP";
		private static final String CP_SCP12kFP = "./SecondData/CPaaSCP.xls";
		private static final String CP_SCP12kTableName = "CPaaSCP";

		private static final String ResultsURL = "./Results/POIxlsTest/";
		@Before
		public void init()
		{
			if(!setupIsDone){
			if(new File("./Data/TestCaseDataBases/POITests.mv.db").delete())
			{
				System.out.println("Old Database Deleted");
			}
			if(new File("./Data/TestCaseDataBases/POITests.trace.db").delete())
			{
				System.out.println("Old Trace Deleted");
			}		
			new File(ResultsURL).mkdirs();
			myDB = new InternalH2(IH2DBURL);
			
			myDB.ImportSheet(REQSheetFP,REQTableName);
			myDB.ImportSheet(CCSheetFP,CCTableName);
			
			myDB.ImportSheet(CC10kFP, CC10kTableName);
			myDB.ImportSheet(CC_UCS16kFP, CC_UCS16kTableName);			
			myDB.ImportSheet(CC_SCP12kFP, CC_SCP12kTableName);
			myDB.ImportSheet(G70FP, G70TableName);
			myDB.ImportSheet(G_UC8kFP, G_UC8kTableName);
			myDB.ImportSheet(UC_UCS15kFP, UC_UCS15kTableName);
			myDB.ImportSheet(UCS20kFP, UCS20kTableName);
			myDB.ImportSheet(UCS_EC16kFP, UCS_EC16kTableName);
			myDB.ImportSheet(EC10kFP, EC10kTableName);
			myDB.ImportSheet(EC_ECS24kFP, EC_ECS24kTableName);
			myDB.ImportSheet(ECS30kFP, ECS30kTableName);
			myDB.ImportSheet(R70FP, R70TableName);
			myDB.ImportSheet(RQ_CP7kFP, RQ_CP7kTableName);
			myDB.ImportSheet(CP10kFP, CP10kTableName);
			myDB.ImportSheet(CP_SCP12kFP, CP_SCP12kTableName);

			
			//read CSV trace matrix
			String ArbSQL = "DROP TABLE "+ TMTableName +" IF EXISTS; CREATE TABLE "+ TMTableName +" AS SELECT * FROM CSVREAD('./Data/CC-REQ-TM.csv');";
			myDB.arbitrarySQL(ArbSQL);
			setupIsDone = true;
			}
		}
		
		@Test
		public void JoinMockData() {
			File TQ1 = new File(ResultsURL + "TQ1.xml");

			SQLString = "SELECT " + CC10kTableName + ".classname " + // REQTableName + ".* " + 
					"FROM " + CC10kTableName + ";";// " " + 
					//"INNER JOIN " + REQTableName + " " +
					//"ON "+ TMTableName + ".ID = "+ REQTableName + ".ID;";
			
			// myDB.QueryToXML(SQLString, TQ1);
			MeasureCostToRS.measureCostToRS(myDB, SQLString, TQ1);
		}
		
		/*
		@Test
		public void SimpleJoin21() {
			File SimpleJoin1 = new File(ResultsURL + "REQandTM2.xml");

			SQLString = "SELECT " + TMTableName + ".ID, " + REQTableName + ".* " + 
					"FROM " + TMTableName + " " + 
					"INNER JOIN " + REQTableName + " " +
					"ON "+ TMTableName + ".ID = "+ REQTableName + ".ID;";

			myDB.QueryToXML(SQLString, SimpleJoin1);
		}

		
		@Test
		public void SimpleJoin1() {
			File SimpleJoin = new File(ResultsURL + "REQandTM.xml");

			SQLString = "SELECT " + TMTableName + ".ClassName, " + REQTableName + ".* " + 
					"FROM " + TMTableName + " " + 
					"INNER JOIN " + REQTableName + " " +
					"ON "+ TMTableName + ".ID = "+ REQTableName + ".ID;";

			myDB.QueryToXML(SQLString, SimpleJoin);
		}

		@Test
		public void SimpleJoin2()
		{
			//Retrieving an xml representation of the tracematrix joined with the codeclass table
			File SimpleJoin2 = new File(ResultsURL + "CCandTM.xml");

			SQLString = "SELECT " + CCTableName + ".*, " + TMTableName + ".ID " +
					"FROM " + CCTableName + " " +
					"INNER JOIN " + TMTableName + " " +
					"ON " + TMTableName + ".ClassName = " + CCTableName + ".ClassName;";

			myDB.QueryToXML(SQLString, SimpleJoin2);
		}

		@Test
		public void TripleJoin()
		{
			//Retrieving an xml representation of the join of the three tables CodeClass , TM and Requirements
			SQLString = "SELECT " + REQTableName + ".*, " + CCTableName + ".*" + " " +
					"FROM " + REQTableName + " " +
					"INNER JOIN " + TMTableName + " " +
					"ON " + TMTableName + ".ID = " + REQTableName + ".ID" + " " +
					"INNER JOIN " + CCTableName + " " +
					"ON " + CCTableName + ".ClassName = " + TMTableName + ".ClassName;";

			File TripleJoin = new File(ResultsURL + "TripleJoin.xml");

			myDB.QueryToXML(SQLString, TripleJoin);

		}

		@Test
		public void AllThree()
		{
			//Retrieving an xml representation of the tracematrix joined with the requirements table
			File SimpleJoin = new File("./results/REQandTM.xml");

			SQLString = "SELECT " + TMTableName + ".ClassName, " + REQTableName + ".* " + 
					"FROM " + TMTableName + " " + 
					"INNER JOIN " + REQTableName + " " +
					"ON "+ TMTableName + ".ID = "+ REQTableName + ".ID;";

			myDB.QueryToXML(SQLString, SimpleJoin);

			//Retrieving an xml representation of the tracematrix joined with the codeclass table
			File SimpleJoin2 = new File("./results/CCandTM.xml");

			SQLString = "SELECT " + CCTableName + ".*, " + TMTableName + ".ID " +
					"FROM " + CCTableName + " " +
					"INNER JOIN " + TMTableName + " " +
					"ON " + TMTableName + ".ClassName = " + CCTableName + ".ClassName;";

			myDB.QueryToXML(SQLString, SimpleJoin2);

			//Retrieving an xml representation of the join of the three tables CodeClass , TM and Requirements
			SQLString = "SELECT " + REQTableName + ".*, " + CCTableName + ".*" + " " +
					"FROM " + REQTableName + " " +
					"INNER JOIN " + TMTableName + " " +
					"ON " + TMTableName + ".ID = " + REQTableName + ".ID" + " " +
					"INNER JOIN " + CCTableName + " " +
					"ON " + CCTableName + ".ClassName = " + TMTableName + ".ClassName;";

			File TripleJoin = new File("./results/TripleJoin.xml");

			myDB.QueryToXML(SQLString, TripleJoin);

		}
		*/
	}
