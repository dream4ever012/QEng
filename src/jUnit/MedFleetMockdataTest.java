package jUnit;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Before;
import org.junit.Test;
import qEng.InternalDB;
import qEng.InternalH2;

public class MedFleetMockdataTest {

	InternalDB myDB;
	String SQLString;

	private static final String XLDriver = "com.nilostep.xlsql.jdbc.xlDriver"; 
	private static final String XLURLBase = "jdbc:nilostep:excel:./SecondData/"; 
	private static final String IH2DBURL = "jdbc:h2:./Data/TestCaseDataBases/MedFleetMockdataTests;TRACE_LEVEL_FILE=3;TRACE_MAX_FILE_SIZE=20";

	private static final String REQTableName = "\"Requirements.ReqSheet\"";
	private static final String CCTableName = "\"codeclasses.codeclass\"";	
	private static final String TMTableName = "CC_REQ_TM";
	
	private static final String Requirements = "\"RQ.RQ\"";
	private static final String RQ_CP7k = "\"RQaaCP.RQaaCP\"";
	private static final String Components10k = "\"CP.CP\"";
	private static final String CP_SCP12k = "\"CPaaSCP.CPaaSCP\"";
	private static final String SubComponents15k = "\"SCP.SCP\"";
	private static final String CC_SCP12k = "\"CCaaSCP.CCaaSCP\"";
	private static final String CodeClasses10k = "\"CC.CC\"";
	
	private static final String Goal70 = "\"G.G\"";
	private static final String G_UC8k = "\"GaaUC.GaaUC\"";
	private static final String UseCases10k = "\"UC.UC\"";
	private static final String UC_UCS15k = "\"UCaaUCS.UCaaUCS\"";
	private static final String UseCaseSteps20k = "\"UCS.UCS\"";
	private static final String UCS_EC15k = "\"UCSaaEC.UCSaaEC\"";
	private static final String ExceptionCases10k = "\"EC.EC\"";
	private static final String EC_ECS24k = "\"ECaaECS.ECaaECS\"";
	private static final String ExceptionCaseSteps30k = "\"ECS.ECS\"";
	private static final String CC_UCS16k = "\"CCaaUCS.CCaaUCS\"";

	private static final String ResultsURL = "./Results/SimpleQueryTests/";
	@Before
	public void init()
	{

		if(new File("./Data/TestCaseDataBases/MedFleetMockdataTests.mv.db").delete())
		{
			System.out.println("Old Database Deleted");
		}
		if(new File("./Data/TestCaseDataBases/MedFleetMockdataTests.trace.db").delete())
		{
			System.out.println("Old Trace Deleted");
		}		
		new File(ResultsURL).mkdirs();
		myDB = new InternalH2(IH2DBURL);

		//create relevant table links
		myDB.createLink(XLDriver, XLURLBase, null,null, CCTableName);
		myDB.createLink(XLDriver, XLURLBase, null,null, REQTableName);

		myDB.createLink(XLDriver, XLURLBase, null,null, Requirements);
		myDB.createLink(XLDriver, XLURLBase, null,null, RQ_CP7k);
		myDB.createLink(XLDriver, XLURLBase, null,null, Components10k);
		myDB.createLink(XLDriver, XLURLBase, null,null, CP_SCP12k);
		myDB.createLink(XLDriver, XLURLBase, null,null, SubComponents15k);
		myDB.createLink(XLDriver, XLURLBase, null,null, CC_SCP12k);
		myDB.createLink(XLDriver, XLURLBase, null,null, CodeClasses10k);
		myDB.createLink(XLDriver, XLURLBase, null,null, Goal70);
		myDB.createLink(XLDriver, XLURLBase, null,null, G_UC8k);
		myDB.createLink(XLDriver, XLURLBase, null,null, UseCases10k);
		myDB.createLink(XLDriver, XLURLBase, null,null, UC_UCS15k);
		myDB.createLink(XLDriver, XLURLBase, null,null, UseCaseSteps20k);
		myDB.createLink(XLDriver, XLURLBase, null,null, UCS_EC15k);
		myDB.createLink(XLDriver, XLURLBase, null,null, ExceptionCases10k);
		myDB.createLink(XLDriver, XLURLBase, null,null, EC_ECS24k);
		myDB.createLink(XLDriver, XLURLBase, null,null, ExceptionCaseSteps30k);
		myDB.createLink(XLDriver, XLURLBase, null,null, CC_UCS16k);

		
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
}