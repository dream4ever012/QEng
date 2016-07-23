package utils;

import java.sql.ResultSet;

import qEng.InternalDB;

public class PrintScanCost {
	private static void printScanCost(InternalDB myDB, String tableName){
		System.out.println("Scan cost of " + tableName + ": " + getScanCost(myDB, tableName));
	}
	
	private static long getScanCost(InternalDB myDB, String tableName){
		ResultSet rsRef =  null;
		
		String SQLString = "SELECT * " +
				"FROM " + tableName + ";";
		long m1 = System.currentTimeMillis();
		rsRef = myDB.QueryToRS(SQLString);
		long m2 = System.currentTimeMillis();
		
		return m2-m1;
	}
}
