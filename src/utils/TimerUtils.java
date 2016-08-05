package utils;

import java.io.File;

import optimizer.QueryManager;
import qEng.InternalDB;

public class TimerUtils {
	public static void measureCostToXml(InternalDB myDB, String SQLString, File TQ)
	{	
		long m1, m2;
		m1 = System.currentTimeMillis();
		myDB.QueryToXML(SQLString, TQ);
		m2 = System.currentTimeMillis();
		System.out.println(TQ.getName() +" cost: " + (m2 - m1));
	}
	
	// compare the cost by millisecond with QueryToXML
	public static void measureCostToRS(InternalDB myDB, String SQLString, File TQ)
	{	
		long m1, m2;
		//ResultSet rsRef = null;
		m1 = System.currentTimeMillis();
		//rsRef = myDB.QueryToRS(SQLString);
		myDB.QueryToRS(SQLString);
		m2 = System.currentTimeMillis();
		System.out.println(TQ.getName() + " cost: " + (m2 - m1));
		//RStoXLSWriter.RStoXLSWrite(rsRef,TQ);
	}
	
	public static void measureCostArbitrary(InternalDB myDB, String ArbSQL, File TQ)
	{	
		long m1, m2;
		//ResultSet rsRef = null;
		m1 = System.currentTimeMillis();
		myDB.arbitrarySQL(ArbSQL);
		m2 = System.currentTimeMillis();
		System.out.println(TQ.getName() + " cost: " + (m2 - m1));
		//RStoXLSWriter.RStoXLSWrite(rsRef,TQ);
	}
	
	public static long measureCostArbitrary(QueryManager myAW, String ArbSQL, File TQ)
	{	
		long m1, m2;
		//ResultSet rsRef = null;
		m1 = System.currentTimeMillis();
		myAW.arbitrarySQL(ArbSQL);
		m2 = System.currentTimeMillis();
		System.out.println(TQ.getName() + " cost: " + (m2 - m1));
		//RStoXLSWriter.RStoXLSWrite(rsRef,TQ);
		return (m2 - m1);
	}
	
	
	
}
