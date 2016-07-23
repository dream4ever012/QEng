package jUnit;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Before;
import org.junit.Test;

import qEng.InternalDB;
import qEng.InternalH2;

public class SimpleQueryTests {

	InternalDB myDB;
	String SQLString;

	private static final String XLDriver = "com.nilostep.xlsql.jdbc.xlDriver"; 
	private static final String XLURLBase = "jdbc:nilostep:excel:./SecondData/"; 
	private static final String IH2DBURL = "jdbc:h2:./Data/TestCaseDataBases/SimpleQueryTests;TRACE_LEVEL_FILE=3;TRACE_MAX_FILE_SIZE=20";

	private static final String REQTableName = "\"Requirements.ReqSheet\"";
	private static final String CCTableName = "\"codeclasses.codeclass\"";	
	private static final String TMTableName = "CC_REQ_TM";

	private static final String ResultsURL = "./Results/SimpleQueryTests/";
	@Before
	public void init()
	{
		
		if(new File("./Data/TestCaseDataBases/SimpleQueryTests.mv.db").delete())
		{
			System.out.println("Old Database Deleted");
		}
		if(new File("./Data/TestCaseDataBases/SimpleQueryTests.trace.db").delete())
		{
			System.out.println("Old Trace Deleted");
		}		
		new File(ResultsURL).mkdirs();
		myDB = new InternalH2(IH2DBURL);
		
		//create relevant table links
		myDB.createLink(XLDriver, XLURLBase, null,null, CCTableName);
		myDB.createLink(XLDriver, XLURLBase, null,null, REQTableName);
		
		//read CSV trace matrix
		String ArbSQL = "DROP TABLE "+ TMTableName +" IF EXISTS; CREATE TABLE "+ TMTableName +" AS SELECT * FROM CSVREAD('./Data/CC-REQ-TM.csv');";
		myDB.arbitrarySQL(ArbSQL);
		
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
}