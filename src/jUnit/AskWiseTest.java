package jUnit;

import java.io.File;

import org.junit.Before;
import org.junit.Test;

import ResourceStrings.SD;
import optimizer.AskWise;
import optimizer.QueryManager;
import qEng.InternalDB;

public class AskWiseTest {

	private static QueryManager myAW;
	public static Boolean setupIsDone = false;
	public static InternalDB myDB;
	String SQLString;

	private static final String H2PROTO = "jdbc:h2:";
	private static final String IH2FP = "./Data/AskWiseTesting/";
	private static final String IH2DBName = "AW";
	private static final String TRACELEVEL = ";TRACE_LEVEL_FILE=3;TRACE_MAX_FILE_SIZE=20";
	private static String IH2DBURL;;
	private static final String ResultsURL = "./Results/POIxlsTest/";
	
	//TODO: create specialized constructor for askwise(InternalDB) and askwise(String URL, User, Pass) etc.
	//TODO: crate a generalized method for importing.
	//TODO: pass IH2DBURL to the askwise constructor.
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
		//myDB = new InternalH2(IH2DBURL);
		
		myDB.ImportSheet(SD.REQSheetTC1FP,SD.REQTableNameTC1);
		myDB.ImportSheet(SD.CCSheetFP,SD.CCTableName);
		
		//read CSV trace matrix
		String ArbSQL = "DROP TABLE "+ SD.TMTableName +" IF EXISTS; CREATE TABLE "+ SD.TMTableName +" AS SELECT * FROM CSVREAD('./Data/CC-REQ-TM.csv');";
		myDB.arbitrarySQL(ArbSQL);
		
		myAW = new AskWise();
		setupIsDone = true;
		}
	}
	
	@Test
	public void testAskWise() {
		String SQL = "SELECT * " +
				"FROM " + SD.REQTableNameTC1 + " " +
				"INNER JOIN " + SD.TMTableName5k + " " +
				"ON " + SD.TMTableName5k + ".ID= " + SD.REQTableNameTC1 + ".ID;";
		
		myAW.queryToXml(SQL);
		
		//See here you created a new ask wize that had no links to execute the SQL on. Negating the init.
		//new AskWise().queryToXml(SQL);
	}
}
