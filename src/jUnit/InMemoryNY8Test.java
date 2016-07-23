package jUnit;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Before;
import org.junit.Test;
import qEng.InternalDB;
import qEng.InternalH2;
import utils.*;

public class InMemoryNY8Test {
	
	private InternalDB myDB;
	private String SQLString;
	
	private static final String XLDriver = "com.nilostep.xlsql.jdbc.xlDriver"; // 
	private static final String XLURLBase = "jdbc:nilostep:excel:./SecondData/"; //
	private static final String IH2DBURL = "jdbc:h2:./Data/TestCaseDataBases/SimpleQueryTests;TRACE_LEVEL_FILE=3;TRACE_MAX_FILE_SIZE=20";
	private static final String ResultsURL = "./Results/InMemoryNY8Tests/";

	private static final String REQTableNameTC1 = "\"RequirementsTC1.ReqSheet\"";
	private static final String CCTableName5k = "\"codeclass5k.codeclass\"";
	private static final String TMTableName5k = "CC_REQ_TM5k";
	
	
	@Before
	public void init()
	{
		
		if(new File("./Data/TestCaseDataBases/InMemoryNY8Tests.mv.db").delete())
		{
			System.out.println("Old Database Deleted");
		}
		if(new File("./Data/TestCaseDataBases/InMemoryNY8Tests.trace.db").delete())
		{
			System.out.println("Old Trace Deleted");
		}		
		new File(ResultsURL).mkdirs();
		myDB = new InternalH2(IH2DBURL);
		
		//create relevant table links
		myDB.createLink(XLDriver, XLURLBase, null,null, CCTableName5k);
		myDB.createLink(XLDriver, XLURLBase, null,null, REQTableNameTC1);
		
		//read CSV trace matrix
		String ArbSQL = "DROP TABLE "+ TMTableName5k +" IF EXISTS; CREATE TABLE "+ TMTableName5k +" AS SELECT * FROM CSVREAD('./Data/CC-REQ-TM.csv');";
		myDB.arbitrarySQL(ArbSQL);		
	}

	
	@Test
	public void test() {
		
		File TQ17 = new File("./results/TQ17.xml");
		SQLString = "SELECT * " +
				"FROM REQTableNameT" + " " +
				"INNER JOIN TMTableNameT" + " " +
				"ON TMTableNameT" + ".ID= " + "REQTableNameT.ID;";
		new MeasureCostToRS(myDB, SQLString, TQ17);
		//measureCostToRS(myDB, SQLString, TQ17);
		
		File TQ18 = new File("./results/TQ18.xml");
		SQLString = "SELECT * " +
				"FROM " + CCTableName5k + " " +
				"INNER JOIN " + TMTableName5k + " " +
				"ON " + TMTableName5k + ".ClassName= " + CCTableName5k + ".ClassName;";
		//new MeasureCostToRS(myDB, SQLString, TQ18);
		MeasureCostToRS.measureCostToRS(myDB, SQLString, TQ18);

		File TQ19 = new File("./results/TQ19.xml");
		SQLString = "SELECT * " +
				"FROM CCTableNameT" + " " +
				"INNER JOIN TMTableNameT" + " " +
				"ON TMTableNameT" + ".ClassName= " + "CCTableNameT.ClassName;";
		MeasureCostToRS.measureCostToRS(myDB, SQLString, TQ19);
		
		File TQ20 = new File("./results/TQ20.xml");
		SQLString = "SELECT " + REQTableNameTC1 + ".*, " + CCTableName5k + ".*" + " " +
				"FROM " + REQTableNameTC1 + " " +
				"INNER JOIN " + TMTableName5k + " " +
				"ON " + TMTableName5k + ".ID = " + REQTableNameTC1 + ".ID" + " " +
				"INNER JOIN " + CCTableName5k + " " +
				"ON " + CCTableName5k + ".ClassName = " + TMTableName5k + ".ClassName;";
		MeasureCostArbitrary.measureCostArbitrary(myDB, SQLString, TQ20);
		
		File TQ21 = new File("./results/TQ21.xml");
		SQLString = "SELECT REQTableNameT.*, " + "CCTableNameT.*" + " " +
				"FROM REQTableNameT" + " " +
				"INNER JOIN TMTableNameT" + " " +
				"ON TMTableNameT.ID = REQTableNameT.ID" + " " +
				"INNER JOIN CCTableNameT" + " " +
				"ON CCTableNameT.ClassName = TMTableNameT.ClassName;";
		MeasureCostArbitrary.measureCostArbitrary(myDB, SQLString, TQ21);

	}
	
	@Test
	private static void createTablesInMemory(InternalDB myDB){
		String ArbSQL = null; 

		// create TMTC1 in memory
		File TQ10 = new File("./results/TQ10.xml");
		ArbSQL = "DROP TABLE TMTableNameT IF EXISTS; Create table TMTableNameT AS Select + " + 
				TMTableName5k + ".*" +
				"FROM " + TMTableName5k + ";";
		MeasureCostArbitrary.measureCostArbitrary(myDB, ArbSQL, TQ10);
		
		
		// create ReqTC1 in memory
		File TQ11 = new File("./results/TQ11.xml");
		ArbSQL = "DROP TABLE REQTableNameT IF EXISTS; Create table REQTableNameT AS Select + " + 
				REQTableNameTC1 + ".*" +
				"FROM " + REQTableNameTC1 + ";";
		MeasureCostArbitrary.measureCostArbitrary(myDB, ArbSQL, TQ11);
				
		// create CCTableNameTC1 in memory
		File TQ12 = new File("./results/TQ12.xml");
		ArbSQL = "DROP TABLE CCTableNameT IF EXISTS; Create table CCTableNameT AS Select + " + 
				CCTableName5k + ".*" +
				"FROM " + CCTableName5k + ";";
		MeasureCostArbitrary.measureCostArbitrary(myDB, ArbSQL, TQ12);

	
	}
	

}


