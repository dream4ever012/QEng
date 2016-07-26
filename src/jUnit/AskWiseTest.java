package jUnit;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Before;
import org.junit.Test;

import CreateTablesForTesting.CreateTablesInMemory;
import optimizer.AskWise;
import optimizer.QueryManager;
import qEng.InternalH2;

public class AskWiseTest {
	private static final String XLDriver = "com.nilostep.xlsql.jdbc.xlDriver"; // 
	private static final String XLURLBase = "jdbc:nilostep:excel:./SecondData/"; //
	private static final String REQTableNameTC1 = "\"RequirementsTC1.ReqSheet\"";
	private static final String CCTableName5k = "\"codeclass5k.codeclass\"";
	private static final String TMTableName5k = "CC-REQ-TM5k";
	private static final String TMPath5k = "./Data/CC-REQ-TM5k.csv";
	private QueryManager myAW;
	
	@Before
	public void init()
	{
		myAW = new AskWise();
		
		//create relevant table links
		myAW.createLink(XLDriver, XLURLBase, null,null, CCTableName5k);
		myAW.createLink(XLDriver, XLURLBase, null,null, REQTableNameTC1);
		
		//read CSV trace matrix
		myAW.importCSVAsTable(TMPath5k, TMTableName5k);
	}
	
	@Test
	public void testAskWise() {
		String SQL = "SELECT * " +
				"FROM " + REQTableNameTC1 + " " +
				"INNER JOIN " + TMTableName5k + " " +
				"ON " + TMTableName5k + ".ID= " + REQTableNameTC1 + ".ID;";
		new AskWise().queryToXml(SQL);
	}
}
