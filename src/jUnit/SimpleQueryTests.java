package jUnit;

import java.io.File;

import org.junit.Before;
import org.junit.Test;

import ResourceStrings.SD;
import qEng.InternalDB;
import qEng.InternalH2;

public class SimpleQueryTests {


	public static Boolean setupIsDone = false;
	public static InternalDB myDB;
	String SQLString;

	private static final String H2PROTO = "jdbc:h2:";
	private static final String IH2FP = "./Data/TestCaseDataBases/";
	private static final String IH2DBName = "POITests";
	private static final String TRACELEVEL = ";TRACE_LEVEL_FILE=3;TRACE_MAX_FILE_SIZE=20";
	private static String IH2DBURL;
	private static final String ResultsURL = "./Results/POIxlsTest/";
	
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
		myDB = new InternalH2(IH2DBURL);
		
		myDB.ImportSheet(SD.REQSheetFP,SD.REQTableName);
		myDB.ImportSheet(SD.CCSheetFP,SD.CCTableName);
		
		//read CSV trace matrix
		String ArbSQL = "DROP TABLE "+ SD.TMTableName +" IF EXISTS; CREATE TABLE "+ SD.TMTableName +" AS SELECT * FROM CSVREAD('./Data/CC-REQ-TM.csv');";
		myDB.arbitrarySQL(ArbSQL);
		setupIsDone = true;
		}
	}

	@Test
	public void SimpleJoin1() {
		File SimpleJoin = new File(ResultsURL + "REQandTM.xml");

		SQLString = "SELECT " + SD.TMTableName + ".ClassName, " + SD.REQTableName + ".* " + 
				"FROM " + SD.TMTableName + " " + 
				"INNER JOIN " + SD.REQTableName + " " +
				"ON "+ SD.TMTableName + ".ID = "+ SD.REQTableName + ".ID;";

		myDB.QueryToXML(SQLString, SimpleJoin);
	}

	@Test
	public void SimpleJoin2()
	{
		//Retrieving an xml representation of the tracematrix joined with the codeclass table
		File SimpleJoin2 = new File(ResultsURL + "CCandTM.xml");

		SQLString = "SELECT " + SD.CCTableName + ".*, " + SD.TMTableName + ".ID " +
				"FROM " + SD.CCTableName + " " +
				"INNER JOIN " + SD.TMTableName + " " +
				"ON " + SD.TMTableName + ".ClassName = " + SD.CCTableName + ".ClassName;";

		myDB.QueryToXML(SQLString, SimpleJoin2);
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

		File TripleJoin = new File(ResultsURL + "TripleJoin.xml");

		myDB.QueryToXML(SQLString, TripleJoin);

	}

	@Test
	public void AllThree()
	{
		//Retrieving an xml representation of the tracematrix joined with the requirements table
		File SimpleJoin = new File("./results/REQandTM.xml");

		SQLString = "SELECT " + SD.TMTableName + ".ClassName, " + SD.REQTableName + ".* " + 
				"FROM " + SD.TMTableName + " " + 
				"INNER JOIN " + SD.REQTableName + " " +
				"ON "+ SD.TMTableName + ".ID = "+ SD.REQTableName + ".ID;";

		myDB.QueryToXML(SQLString, SimpleJoin);

		//Retrieving an xml representation of the tracematrix joined with the codeclass table
		File SimpleJoin2 = new File("./results/CCandTM.xml");

		SQLString = "SELECT " + SD.CCTableName + ".*, " + SD.TMTableName + ".ID " +
				"FROM " + SD.CCTableName + " " +
				"INNER JOIN " + SD.TMTableName + " " +
				"ON " + SD.TMTableName + ".ClassName = " + SD.CCTableName + ".ClassName;";

		myDB.QueryToXML(SQLString, SimpleJoin2);

		//Retrieving an xml representation of the join of the three tables CodeClass , TM and Requirements
		SQLString = "SELECT " + SD.REQTableName + ".*, " + SD.CCTableName + ".*" + " " +
				"FROM " + SD.REQTableName + " " +
				"INNER JOIN " + SD.TMTableName + " " +
				"ON " + SD.TMTableName + ".ID = " + SD.REQTableName + ".ID" + " " +
				"INNER JOIN " + SD.CCTableName + " " +
				"ON " + SD.CCTableName + ".ClassName = " + SD.TMTableName + ".ClassName;";

		File TripleJoin = new File("./results/TripleJoin.xml");

		myDB.QueryToXML(SQLString, TripleJoin);

	}
}