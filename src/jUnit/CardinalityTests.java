package jUnit;

import java.io.File;

import org.junit.Before;
import org.junit.Test;

import ResourceStrings.SD;
import optimizer.AskWise;
import optimizer.QueryManager;
import qEng.InternalDB;
import qEng.InternalH2;

public class CardinalityTests {

public static QueryManager myAW;
	
	private boolean setupIsDone = false;
	private static final String H2PROTO = "jdbc:h2:";
	private static final String IH2FP = "./Data/TestCaseDataBases/";
	private static final String IH2DBName = "CardinalityTests";
	private static final String TRACELEVEL = ";TRACE_LEVEL_FILE=3;TRACE_MAX_FILE_SIZE=20";

		public static InternalDB myDB;
		String SQLString;
		private static String IH2DBURL;
		
		private static final String ResultsURL = "./Results/POIxlsTest/";
		
		@Before
		public void init()
		{
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
			
			myAW.ImportSheet(SD.REQSheetFP,SD.REQTableName);
			myAW.ImportSheet(SD.CCSheetFP,SD.CCTableName);
		
			//read CSV trace matrix
			String ArbSQL = "DROP TABLE "+ SD.TMTableName +" IF EXISTS; CREATE TABLE "+ SD.TMTableName +" AS SELECT * FROM CSVREAD('"+ SD.TMFilePath +"');";
			myAW.arbitrarySQL(ArbSQL);
			setupIsDone = true;
			
			}
		}
		
		@Test
		public void SimpleJoin1() {
			File SimpleJoin = new File(ResultsURL + "CARDINALITY1.xml");

			SQLString = "SELECT COUNT(*) " + // SD.TMTableName + ".ClassName, " + SD.REQTableName + ".* " + 
					"FROM " + SD.TMTableName + " " + 
					"INNER JOIN " + SD.REQTableName + " " +
					"ON "+ SD.TMTableName + ".ID = "+ SD.REQTableName + ".ID;";

			myDB.QueryToXML(SQLString, SimpleJoin);
			
			//MeasureCostToRS.measureCostToRS(myAW, SQLString, SimpleJoin);
		}

		@Test
		public void SimpleJoin2()
		{
			//Retrieving an xml representation of the tracematrix joined with the codeclass table
			File SimpleJoin2 = new File(ResultsURL + "CARDINALITY2.xml");

			SQLString = "SELECT " + SD.CCTableName + ".*, " + SD.TMTableName + ".ID " +
					"FROM " + SD.CCTableName + " " +
					"INNER JOIN " + SD.TMTableName + " " +
					"ON " + SD.TMTableName + ".ClassName = " + SD.CCTableName + ".ClassName;";

			myDB.QueryToXML(SQLString, SimpleJoin2);
		//	MeasureCostToRS.measureCostToRS(myAW, SQLString, SimpleJoin2);
		}

		@Test
		public void TripleJoin()
		{
			//Retrieving an xml representation of the join of the three tables CodeClass , TM and Requirements
			SQLString = "SELECT " + SD.REQTableName + ".*, " + SD.CCTableName + ".*" + " " +
					"FROM " + SD.REQTableName + " " +
					"INNER JOIN " + SD.TMTableName + " " +
					"ON " + SD.TMTableName + ".ID = " + SD.REQTableName + ".ID" + " " +
					"INNER JOIN " + SD.CCTableName + " " +
					"ON " + SD.CCTableName + ".ClassName = " + SD.TMTableName + ".ClassName;";

			File TripleJoin = new File(ResultsURL + "CARDINALITY3.xml");

			myDB.QueryToXML(SQLString, TripleJoin);
			//MeasureCostToRS.measureCostToRS(myAW, SQLString, TripleJoin);

		}

}
