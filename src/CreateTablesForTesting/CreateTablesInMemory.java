package CreateTablesForTesting;

import java.io.File;

import qEng.InternalDB;
import utils.MeasureCostArbitrary;

public class CreateTablesInMemory {
	
	private static final String REQTableNameTC1 = "\"RequirementsTC1.ReqSheet\"";
	private static final String CCTableName5k = "\"codeclass5k.codeclass\"";
	private static final String TMTableName5k = "CC_REQ_TM5k";

	public static void createTablesInMemory(InternalDB myDB){
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
