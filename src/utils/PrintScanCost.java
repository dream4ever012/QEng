package utils;

import qEng.InternalDB;

//TODO: check to see if this method is duplicated in the code.
public class PrintScanCost {
	public static void printScanCost(InternalDB myDB, String tableName){
		System.out.println("Scan cost of " + tableName + ": " + getScanCost(myDB, tableName));
	}
	
	public static long getScanCost(InternalDB myDB, String tableName){
		//ResultSet rsRef =  null;
		
		String SQLString = "SELECT * " +
				"FROM " + tableName + ";";
		long m1 = System.currentTimeMillis();
		//rsRef = myDB.QueryToRS(SQLString);
		myDB.QueryToRS(SQLString);
		long m2 = System.currentTimeMillis();
		
		return m2-m1;
	}
}
