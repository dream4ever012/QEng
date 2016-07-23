package jUnit;

import static org.junit.Assert.*;

import java.io.File;
import org.junit.Test;
import qEng.InternalDB;
import utils.*;

public class InMemoryNY8Test {
	
	
	private static final String XLDriver = "com.nilostep.xlsql.jdbc.xlDriver"; // 
	private static final String XLURLBase = "jdbc:nilostep:excel:./SecondData/"; //
	private static final String REQTableNameTC1 = "\"RequirementsTC1.ReqSheet\"";
	private static final String CCTableName5k = "\"codeclass5k.codeclass\"";
	private static final String TMTableName5k = "CC_REQ_TM5k";
	private String SQLString;
	InternalDB myDB;
	
	@Test
	public void test() {
		
		File TQ17 = new File("./results/TQ17.xml");
		SQLString = "SELECT * " +
				"FROM REQTableNameT" + " " +
				"INNER JOIN TMTableNameT" + " " +
				"ON TMTableNameT" + ".ID= " + "REQTableNameT.ID;";
		measureCostToRS(myDB, SQLString, TQ17);
		
		File TQ18 = new File("./results/TQ18.xml");
		SQLString = "SELECT * " +
				"FROM " + CCTableName5k + " " +
				"INNER JOIN " + TMTableName5k + " " +
				"ON " + TMTableName5k + ".ClassName= " + CCTableName5k + ".ClassName;";
		measureCostToRS(myDB, SQLString, TQ18);

		File TQ19 = new File("./results/TQ19.xml");
		SQLString = "SELECT * " +
				"FROM CCTableNameT" + " " +
				"INNER JOIN TMTableNameT" + " " +
				"ON TMTableNameT" + ".ClassName= " + "CCTableNameT.ClassName;";
		measureCostToRS(myDB, SQLString, TQ19);
		
		File TQ20 = new File("./results/TQ20.xml");
		SQLString = "SELECT " + REQTableNameTC1 + ".*, " + CCTableName5k + ".*" + " " +
				"FROM " + REQTableNameTC1 + " " +
				"INNER JOIN " + TMTableName5k + " " +
				"ON " + TMTableName5k + ".ID = " + REQTableNameTC1 + ".ID" + " " +
				"INNER JOIN " + CCTableName5k + " " +
				"ON " + CCTableName5k + ".ClassName = " + TMTableName5k + ".ClassName;";
		measureCostArbitrary(myDB, SQLString, TQ20);
		
		File TQ21 = new File("./results/TQ21.xml");
		SQLString = "SELECT REQTableNameT.*, " + "CCTableNameT.*" + " " +
				"FROM REQTableNameT" + " " +
				"INNER JOIN TMTableNameT" + " " +
				"ON TMTableNameT.ID = REQTableNameT.ID" + " " +
				"INNER JOIN CCTableNameT" + " " +
				"ON CCTableNameT.ClassName = TMTableNameT.ClassName;";
		measureCostArbitrary(myDB, SQLString, TQ21);

	}
	
	@Test
	private static void createTablesInMemory(InternalDB myDB){
		String ArbSQL = null; 

		// create TMTC1 in memory
		File TQ10 = new File("./results/TQ10.xml");
		ArbSQL = "DROP TABLE TMTableNameT IF EXISTS; Create table TMTableNameT AS Select + " + 
				TMTableName5k + ".*" +
				"FROM " + TMTableName5k + ";";
		measureCostArbitrary(myDB, ArbSQL, TQ10);
		
		
		// create ReqTC1 in memory
		File TQ11 = new File("./results/TQ11.xml");
		ArbSQL = "DROP TABLE REQTableNameT IF EXISTS; Create table REQTableNameT AS Select + " + 
				REQTableNameTC1 + ".*" +
				"FROM " + REQTableNameTC1 + ";";
		measureCostArbitrary(myDB, ArbSQL, TQ11);
				
		// create CCTableNameTC1 in memory
		File TQ12 = new File("./results/TQ12.xml");
		ArbSQL = "DROP TABLE CCTableNameT IF EXISTS; Create table CCTableNameT AS Select + " + 
				CCTableName5k + ".*" +
				"FROM " + CCTableName5k + ";";
		measureCostArbitrary(myDB, ArbSQL, TQ12);

	
	}
	

}


