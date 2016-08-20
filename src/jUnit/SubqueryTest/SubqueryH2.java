package jUnit.SubqueryTest;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Before;
import org.junit.Test;

import ResourceStrings.SD;
import optimizer.AskWise;
import optimizer.QueryManager;
import qEng.InternalH2;
import utils.MeasureCostArbitrary;

public class SubqueryH2 {
	public static Boolean setupIsDone = false;
	private static final String H2PROTO = "jdbc:h2:";
	private static final String IH2FP = "./Data/SubqueryTest/";
	private static final String IH2DBName = "SubqueryTestH2";
	private static final String TRACELEVEL = ";TRACE_LEVEL_FILE=3;TRACE_MAX_FILE_SIZE=20";
	private static String IH2DBURL;
	private String SQLString;
	
	private QueryManager myAW;
	
	private static final String ResultsURLBase = "./Results/";
	private static final String ResultsURL = ResultsURLBase + IH2DBName+ "/";
	
	@Before
	public void init(){
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
		
		myAW.ImportSheet(SD.REQSheetFP,SD.REQTableName);
		myAW.ImportSheet(SD.CCSheetFP,SD.CCTableName);
		myAW.ImportSheet(SD.CCSheet5kFP, SD.CCTableName5k);
		
		//read CSV trace matrix		
		String ArbSQL = "DROP TABLE "+ SD.TMTableName5k +" IF EXISTS; CREATE TABLE "+ SD.TMTableName5k +" AS SELECT * FROM CSVREAD('"+ SD.TMSheet5kFP +"');";
		myAW.arbitrarySQL(ArbSQL);
		ArbSQL = "DROP TABLE "+ SD.TMTableName +" IF EXISTS; CREATE TABLE "+ SD.TMTableName +" AS SELECT * FROM CSVREAD('"+ SD.TMFilePath +"');";
		myAW.arbitrarySQL(ArbSQL);
		
		File Subquery = new File("./results/Subquery.xml");
		SQLString =
				"DROP TABLE T1 IF EXISTS;" + " " +
				"CREATE TABLE T1 (ID VARCHAR2(10) PRIMARY KEY, VAL NUMBER);" + " " +
				"INSERT INTO T1 VALUES('A', 100);" + " " +
				"INSERT INTO T1 VALUES('B', 100);" + " " +
				"INSERT INTO T1 VALUES('C', 100);" + " " +
				"INSERT INTO T1 VALUES('D', 100);" + " " +
				"INSERT INTO T1 VALUES('E', 100)";		
		MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, Subquery);
		
		
		}
	}

	

	@Test
	public void test() {
		File Subquery = new File("./results/Subquery.xml");
		SQLString =
				"SELECT T1.* " +
				"FROM " + "(SELECT * FROM " + SD.CCTableName5k + ") " + "T1" + ";";
		MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, Subquery);
		myAW.QueryToXML(SQLString, Subquery);
		
		File Subquery1 = new File("./results/Subquery1.xml");
		SQLString =
				"SELECT T1.* " +
				"FROM " + "(SELECT * FROM " + SD.CCTableName5k + ") AS " + "T1" + ";";
		MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, Subquery1);	
		
	}

}
