package utils;

import java.io.File;
import qEng.InternalDB;

public class MeasureCostArbitrary {
	public static void measureCostArbitrary(InternalDB myDB, String ArbSQL, File TQ)
	{	
		long m1, m2;
		//ResultSet rsRef = null;
		m1 = System.currentTimeMillis();
		myDB.arbitrarySQL(ArbSQL);
		m2 = System.currentTimeMillis();
		System.out.println(TQ.getName() + " cost: " + (m2 - m1));
	}
}
