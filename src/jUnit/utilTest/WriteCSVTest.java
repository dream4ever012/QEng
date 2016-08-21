package jUnit.utilTest;

import java.io.File;
import org.junit.Before;
import org.junit.Test;

import ResourceStrings.SD;
import optimizer.AskWise;
import optimizer.QueryManager;
import qEng.InternalH2;
import utils.MeasureCostArbitrary;

public class WriteCSVTest {
	public static Boolean setupIsDone = false;
	private static final String H2PROTO = "jdbc:h2:";
	private static final String IH2FP = "./Data/WriteCSVTest/";
	private static final String IH2DBName = "WriteCSVTest";
	private static final String TRACELEVEL = ";TRACE_LEVEL_FILE=3;TRACE_MAX_FILE_SIZE=20";
	private static String IH2DBURL;
	private String SQLString;
	
	private QueryManager myAW;
	private QueryManager myOAW;
	
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
		myAW = new AskWise(new InternalH2(IH2DBURL));
		//myOAW = new AskWise(new ExternalOracle());
		
		//CreateTablesInMemory.createTablesInMemoryGtoECS(myAW);
		//CreateTablesInMemory.registerTMGtoECS(myAW);
		File CSVWriter = new File("./results/CSVWriter.xml");
		SQLString =
				"DROP TABLE T1 IF EXISTS;" + " " +
				"CREATE TABLE T1 (ID VARCHAR2(10) PRIMARY KEY, VAL NUMBER);" + " " +
				"INSERT INTO T1 VALUES('A', 100);" + " " +
				"INSERT INTO T1 VALUES('B', 100);" + " " +
				"INSERT INTO T1 VALUES('C', 100);" + " " +
				"INSERT INTO T1 VALUES('D', 100);" + " " +
				"INSERT INTO T1 VALUES('E', 100)";		
		MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, CSVWriter);
		setupIsDone = true;
		}
	}
	@Test
	public void test() {
		
		// CSVWriter.xml cost: 17 works!
		myAW.WriteCSV("./results/WriteCSVTest/T1.csv", "SELECT * FROM T1;");
		
		
		
		//read CSV trace matrix
		//String ArbSQL = "DROP TABLE "+ SD.TMTableName +" IF EXISTS; CREATE TABLE "+ SD.TMTableName +" AS SELECT * FROM CSVREAD('./Data/CC-REQ-TM.csv');";
		//myAW.arbitrarySQL(ArbSQL);

	}

}
