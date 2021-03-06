package jUnit;

import java.io.File;

import org.junit.Before;
import qEng.InternalDB;
import qEng.InternalH2;

public class XlsxImportTest {
	
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

		
		//read CSV trace matrix
		String ArbSQL = "DROP TABLE "+ TMTableName +" IF EXISTS; CREATE TABLE "+ TMTableName +" AS SELECT * FROM CSVREAD('./Data/CC-REQ-TM.csv');";
		myDB.arbitrarySQL(ArbSQL);
		setupIsDone = true;
		}
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
