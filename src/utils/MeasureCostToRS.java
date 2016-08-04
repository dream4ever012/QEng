package utils;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import qEng.InternalDB;

public class MeasureCostToRS {
	
	private static final String DriverType = ":thin:";
	private static final String Host = "@rasinsrv06.cstcis.cti.depaul.edu";
	private static final String Port = ":1521";
	private static final String User = "Tiqi";
	private static final String Pass = "Tiqi123";
	private static final String SID = "/oracle12c";
	private static final String protocol = "jdbc:oracle";
	private static Connection conn;
	
	static String URL_TIQI = protocol + DriverType + Host + Port + SID;
	
	// compare the cost by millisecond with QueryToXML
	public static void measureCostToRS(InternalDB myDB, String SQLString, File TQ)
	{	
		long m1, m2;
		m1 = System.currentTimeMillis();
		
		//ResultSet rsRef = myDB.QueryToRS(SQLString);
		 myDB.QueryToRS(SQLString);
		m2 = System.currentTimeMillis();
		System.out.println(TQ.getName() + " cost: " + (m2 - m1));
	}
	
	public static void measureCostToRSOrcle(String SQLString, File TQ)
	{	
		try {
			conn = DriverManager.getConnection(URL_TIQI,User,Pass);
			Statement stmt = conn.createStatement();
			long m1, m2;
			m1 = System.currentTimeMillis();
			//ResultSet rsRef = stmt.executeQuery(SQLString);
			stmt.executeQuery(SQLString);
			m2 = System.currentTimeMillis();
			System.out.println(TQ.getName() + " cost: " + (m2 - m1));
			// RStoXLSWriter.RStoXLSWrite(rsRef, new File("./SecondData/OracleTest.xls"));
			conn.close();
			//RStoXLSWriter.RStoXLSWrite(rsRef, TQ);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
