package utils;

import java.io.File;
import java.sql.ResultSet;

import qEng.InternalDB;

public class MeasureCostArbitrary {
	private static void measureCostArbitrary(InternalDB myDB, String ArbSQL, File TQ)
	{	
		long m1, m2;
		ResultSet rsRef = null;
		m1 = System.currentTimeMillis();
		myDB.arbitrarySQL(ArbSQL);
		m2 = System.currentTimeMillis();
		System.out.println(TQ.getName() + " cost: " + (m2 - m1));
	}
}
