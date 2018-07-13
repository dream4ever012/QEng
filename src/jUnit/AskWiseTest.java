package jUnit;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Before;
import org.junit.Test;

import ResourceStrings.SD;
import optimizer.AskWise;
import optimizer.QueryManager;
import qEng.InternalH2;
import utils.TimerUtils;

public class AskWiseTest {

	private static QueryManager myAW;
	public static Boolean setupIsDone = false;
	private static final String H2PROTO = "jdbc:h2:";
	private static final String IH2FP = "./Data/AskWiseTesting/";
	private static final String IH2DBName = "AW";
	private static final String TRACELEVEL = ";TRACE_LEVEL_FILE=3;TRACE_MAX_FILE_SIZE=20";
	private static String IH2DBURL;
	private static final String ResultsURL = "./Results/AskWiseTesting/";
	
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
		myAW = new AskWise(new InternalH2(IH2DBURL));

		myAW.ImportSheet(SD.REQSheetTC1FP,SD.REQTableNameTC1);
		myAW.ImportSheet(SD.CCSheetFP,SD.CCTableName);
		myAW.ImportSheet(SD.R70FP,SD.R70TableName);
		
		//read CSV trace matrix
		String ArbSQL = "DROP TABLE "+ SD.TMTableName5k +" IF EXISTS; CREATE TABLE "+ SD.TMTableName5k +" AS SELECT * FROM CSVREAD('./Data/CC-REQ-TM.csv');";
		myAW.arbitrarySQL(ArbSQL);
		
		setupIsDone = true;
		}
	}
	
	@Test
	public void testAskWise() {
		String ArbSQL = "SELECT * " +
				"FROM " + SD.REQTableNameTC1 + " " +
				"INNER JOIN " + SD.TMTableName5k + " " +
				"ON " + SD.TMTableName5k + ".ID= " + SD.REQTableNameTC1 + ".ID;";
		
		File TQ2 = new File("./results/TQ2.xml");
		myAW.queryToXmlH2(ArbSQL);
		assertTrue("failure " + TQ2.getName().toString() , TimerUtils.measureCostArbitrary(myAW, ArbSQL, TQ2) >= 5.0);
		
		File TQ3 = new File("./results/TQ3.xml");
		ArbSQL = "SELECT * FROM " + SD.R70TableName + ";";
		assertTrue("failure " + TQ3.getName().toString() , TimerUtils.measureCostArbitrary(myAW, ArbSQL, TQ3) <= 10.0);
	}
}
