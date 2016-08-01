package jUnit;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Before;
import org.junit.Test;

import optimizer.AskWise;
import optimizer.QueryManager;
import qEng.InternalH2;
import utils.CreateTablesInMemory;

public class AskWiseTest {
	private static final String XLDriver = "com.nilostep.xlsql.jdbc.xlDriver"; // 
	private static final String XLURLBase = "jdbc:nilostep:excel:./SecondData/"; //
	private static final String REQTableNameTC1 ="\"RequirementsTC1.ReqSheet\"";
	private static final String CCTableName5k = "\"codeclass5k.codeclass\"";
	
	//You used hyphens here instead of underscores that you used in all the other examples, hyphens are illegal in table names
	private static final String TMTableName5k = "CC_REQ_TM5k";
	private static final String TMPath5k = "./Data/CC-REQ-TM5k.csv";
	private QueryManager myAW;
	
	@Before
	public void init()
	{
		
		if(new File("./Data/AskWiseTesting/AW.mv.db").delete())
		{
			System.out.println("Old Database Deleted");
		}
		if(new File("./Data/AskWiseTesting/AW.trace.db").delete())
		{
			System.out.println("Old Trace Deleted");
		}		
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
		
		myAW.queryToXml(SQL);
		
		//See here you created a new ask wize that had no links to execute the SQL on. Negating the init.
		//new AskWise().queryToXml(SQL);
	}
}
