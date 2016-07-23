package utils;

import java.io.File;

import qEng.InternalDB;

public class MeasureCostToXml {
	
	// compare the cost by millisecond with QueryToXML
	public static void measureCostToXml(InternalDB myDB, String SQLString, File TQ)
	{	
		long m1, m2;
		m1 = System.currentTimeMillis();
		myDB.QueryToXML(SQLString, TQ);
		m2 = System.currentTimeMillis();
		System.out.println(TQ.getName() +" cost: " + (m2 - m1));
	}

}
