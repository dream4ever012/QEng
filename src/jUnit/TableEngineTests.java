package jUnit;

import static org.junit.Assert.*;

import org.junit.Test;

import qEng.InternalDB;
import qEng.InternalH2;


public class TableEngineTests {

	private		String IH2DBURL = "jdbc:h2:./Data/TableEngineTests";
	private		String IH2PASS = "";
	private		String IH2USER = "sys";
	
	
	@Test
	public void test() {
		//InternalDB myh2 = new InternalH2();
		InternalDB myh2 = new InternalH2(IH2DBURL, IH2USER, IH2PASS);
		
		
		String EngineTestSQL = " ;";
		
		myh2.arbitrarySQL(EngineTestSQL);
		
		
		fail("Not yet implemented");
	}

}
