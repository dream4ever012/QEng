package utils;

import java.io.File;
import java.sql.ResultSet;

import qEng.InternalDB;

public class MeasureCostToRS {
	// compare the cost by millisecond with QueryToXML
	public MeasureCostToRS(InternalDB myDB, String SQLString, File TQ){
		measureCostToRS(myDB, SQLString, TQ);
	}
	private static void measureCostToRS(InternalDB myDB, String SQLString, File TQ)
	{	
		long m1, m2;
		ResultSet rsRef = null;
		m1 = System.currentTimeMillis();
		rsRef = myDB.QueryToRS(SQLString);
		m2 = System.currentTimeMillis();
		System.out.println(TQ.getName() + " cost: " + (m2 - m1));
	}	
}
