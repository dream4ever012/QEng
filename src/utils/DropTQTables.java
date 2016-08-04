package utils;

import java.io.File;

import qEng.InternalDB;

public class DropTQTables {
	public static void dropTQTables(InternalDB myDB,int k) {
		// TODO Auto-generated method stub
		for (int i = 0; i<k; i++){
			File TQ12 = new File("./results/TQ12.xml");
			String ArbSQL = "DROP TABLE TQ" + i + " IF EXISTS;"; 
			myDB.arbitrarySQL(ArbSQL);
		}
	}
}
