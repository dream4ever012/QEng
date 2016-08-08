package utils;

import java.io.File;

import ResourceStrings.SD;
import optimizer.QueryManager;

public class CreateInMemoryTableMethod {

	
	public static void createInMemoryTable(QueryManager myAW, String TableName,File TQ){
		//read CSV trace matrix
		String ArbSQL = "DROP TABLE " + TableName + " IF EXISTS; CREATE TABLE " + TableName + " AS SELECT * FROM " + TableName + ";";
		MeasureCostArbitrary.measureCostArbitrary(myAW, ArbSQL, TQ);
	}
	
	
}
