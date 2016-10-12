package jUnit.utilTest;

import static org.junit.Assert.assertTrue;
import java.io.File;
import org.junit.Before;
import org.junit.Test;
import ResourceStrings.DDS;
import ResourceStrings.SD;
import optimizer.AskWise;
import optimizer.QueryManager;
import qEng.ExternalOracle;
import qEng.InternalH2;
import utils.CreateTablesInMemory;
import utils.CreateTablesInMemoryDDS;
import utils.MeasureCostArbitrary;

public class WriteCSVTest_DDS {
	public static Boolean setupIsDone = false;
	private static final String H2PROTO = "jdbc:h2:";
	private static final String IH2FP = "./Data/WriteCSVTest_DDS/";
	private static final String IH2DBName = "WriteCSVTest_DDS";
	private static final String TRACELEVEL = ";TRACE_LEVEL_FILE=3;TRACE_MAX_FILE_SIZE=20";
	private static String IH2DBURL;
	private String SQLString;
	
	private QueryManager myAW;
	private QueryManager myOAW;
	
	private static final String ResultsURLBase = "./Results/";
	private static final String ResultsURL = ResultsURLBase + IH2DBName+ "/";
	
	//TODO: put the CLASSES column back in CCTableName5k sheet, the cassandra class filepaths.
	@Before
	public void init()
	{		
		if(!setupIsDone){
			IH2DBURL = H2PROTO + IH2FP + IH2DBName + TRACELEVEL;
		//if(new File("./Data/TestCaseDataBases/POITests.mv.db").delete())
		if(new File(IH2FP + IH2DBName + ".mv.db").delete())
		{
			System.out.println("Old Database Deleted");
		}
		if(new File(IH2FP + IH2DBName + ".trace.db").delete())
		{
			System.out.println("Old Trace Deleted");
		}		
		new File(ResultsURL).mkdirs();
		myAW = new AskWise(new InternalH2(IH2DBURL));
		myOAW = new AskWise(new ExternalOracle());
		
//		// create in memory tables
//		CreateTablesInMemoryDDS.createTablesInMemory(myAW);
//		CreateTablesInMemoryDDS.createTablesInMemory_twoT(myAW);
//		// indexing
//		CreateTablesInMemoryDDS.registerTMDDS(myAW);
//		CreateTablesInMemoryDDS.registerTMDDS_twoT(myAW);
		
		
		setupIsDone = true;
		}
	}
	@Test
	public void test() {
		
//		//card: 6010
//		SRQaaDRQ(myAW);
//		
//		//card: 21800
//		SSRQaaCLS(myAW);
//		
//		// card: 206270
//		DRQaaUT(myAW);
//		
//		// card: 31285
//		DRQaaBGR(myAW);
//		
//		// card: 52004
//		CLSaaPPL(myAW);
//		
//		// card: 2393098
//		CLSaaUTL(myAW);
//		
		// card: 469
//		SSRQaaEA(myAW);
		
//		// card: 
//		CLSaaEA(myAW);
		
		// JoinSRQtoPPLtoUTL.xml cost: 142974
		//JoinSRQtoPPLtoUTL(myAW);
		JoinSRQtoPPLtoUTL(myOAW);
	}
	/*
	 * two table joins
	 */
	private static void SRQaaDRQ(QueryManager myAW){	
		File SRQaaDRQ = new File("./results/SRQaaDRQ.xml"); 
		String SQLString =
				"SELECT " +  DDS.SRQaaSSRQ + ".srqid, " + DDS.SSRQaaDRQ + ".drqid" + " " + //COUNT(*) " + //
				"FROM " + DDS.SRQaaSSRQ + " " +
				"INNER JOIN " + DDS.SSRQaaDRQ + " " + 
				"ON " + DDS.SSRQaaDRQ + ".ssrqid = " + DDS.SRQaaSSRQ + ".ssrqid"; // + " " +
		System.out.println(SQLString);
		MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, SRQaaDRQ);
		//myAW.WriteCSV("./Data_DDS/SRQaaDRQ.csv", SQLString);
		//myAW.QueryToXML(SQLString, SRQaaDRQ);
	}
	
	private static void SSRQaaCLS(QueryManager myAW){	
		File SSRQaaCLS = new File("./results/SSRQaaCLS.xml"); 
		String SQLString =
				"SELECT " + DDS.SSRQaaDRQ + ".ssrqid, " + DDS.DRQaaCLS + ".clsid" + " " + //COUNT(*) " 
				"FROM " + DDS.SSRQaaDRQ + " " +
				"INNER JOIN " + DDS.DRQaaCLS + " " +
				"ON " + DDS.DRQaaCLS +  ".drqid = " + DDS.SSRQaaDRQ + ".drqid";
		System.out.println(SQLString);
		MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, SSRQaaCLS);
		//myAW.WriteCSV("./Data_DDS/SSRQaaCLS.csv", SQLString);
		//myAW.QueryToXML(SQLString, SSRQaaCLS);
	}
	
	private static void SSRQaaEA(QueryManager myAW){	
		File SSRQaaEA = new File("./results/SSRQaaEA.xml"); 
		String SQLString =
				"SELECT " + DDS.SSRQaaDRQ + ".ssrqid, " + DDS.DRQaaEA + ".eaid" + " " + //COUNT(*) " 
				"FROM " + DDS.SSRQaaDRQ + " " +
				"INNER JOIN " + DDS.DRQaaEA + " " +
				"ON " + DDS.DRQaaEA +  ".drqid = " + DDS.SSRQaaDRQ + ".drqid";
		System.out.println(SQLString);
		MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, SSRQaaEA);
		//myAW.WriteCSV("./Data_DDS/SSRQaaEA.csv", SQLString);
		//myAW.QueryToXML(SQLString, SSRQaaEA);
	}
	

	
	private static void CLSaaEA(QueryManager myAW){	
		File CLSaaEA = new File("./results/CLSaaEA.xml"); 
		String SQLString =
				"SELECT " + DDS.DRQaaCLS + ".clsid, " + DDS.DRQaaEA + ".eaid" + " " + //COUNT(*) " 
				"FROM " + DDS.DRQaaCLS + " " +
				"INNER JOIN " + DDS.DRQaaEA + " " +
				"ON " + DDS.DRQaaEA +  ".drqid = " + DDS.DRQaaCLS + ".drqid";
		System.out.println(SQLString);
		MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, CLSaaEA);
		myAW.WriteCSV("./Data_DDS/CLSaaEA.csv", SQLString);
		//myAW.QueryToXML(SQLString, CLSaaEA);
	}
	
	private static void DRQaaUT(QueryManager myAW){	
		File DRQaaUT = new File("./results/DRQaaUT.xml"); 
		String SQLString =
				"SELECT " + DDS.DRQaaCLS + ".drqid, " + DDS.CLSaaUT + ".utid" + " " + // COUNT(*) " + //
				"FROM " + DDS.DRQaaCLS + " " +
				"INNER JOIN " + DDS.CLSaaUT + " " +
				"ON " + DDS.CLSaaUT +  ".clsid = " + DDS.DRQaaCLS + ".clsid";
		//System.out.println(SQLString);
		MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, DRQaaUT);
		//myAW.WriteCSV("./Data_DDS/DRQaaUT.csv", SQLString);
		//myAW.QueryToXML(SQLString, DRQaaUT);
	}
	
	private static void DRQaaBGR(QueryManager myAW){	
		File DRQaaBGR = new File("./results/DRQaaBGR.xml"); 
		String SQLString =
				"SELECT " + DDS.DRQaaCLS + ".drqid, " + DDS.BGRaaCLS + ".bgrid" + " " + //COUNT(*) " + //
				"FROM " + DDS.DRQaaCLS + " " +
				"INNER JOIN " + DDS.BGRaaCLS + " " +
				"ON " + DDS.BGRaaCLS +  ".clsid = " + DDS.DRQaaCLS + ".clsid";
		//System.out.println(SQLString);
		MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, DRQaaBGR);
		//myAW.WriteCSV("./Data_DDS/DRQaaBGR.csv", SQLString);
		//myAW.QueryToXML(SQLString, DRQaaBGR);
	}
	
	private static void CLSaaPPL(QueryManager myAW){	
		File CLSaaPPL = new File("./results/CLSaaPPL.xml"); 
		String SQLString =
				"SELECT " + DDS.BGRaaCLS + ".clsid, " + DDS.PPLaaBGR + ".pplid" + " " + //COUNT(*) " + // 
				"FROM " + DDS.BGRaaCLS + " " +
				"INNER JOIN " + DDS.PPLaaBGR + " " +
				"ON " + DDS.PPLaaBGR +  ".bgrid = " + DDS.BGRaaCLS + ".bgrid";
		//System.out.println(SQLString);
		MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, CLSaaPPL);
		//myAW.WriteCSV("./Data_DDS/CLSaaPPL.csv", SQLString);
		//myAW.QueryToXML(SQLString, CLSaaPPL);
	}
	
	private static void CLSaaUTL(QueryManager myAW){	
		File CLSaaUTL = new File("./results/CLSaaUTL.xml"); 
		String SQLString =
				"SELECT " + DDS.CLSaaUT + ".clsid, " + DDS.UTaaUTL + ".utlid" + " " + //COUNT(*) " + //
				"FROM " + DDS.CLSaaUT + " " +
				"INNER JOIN " + DDS.UTaaUTL + " " +
				"ON " + DDS.UTaaUTL +  ".utid = " + DDS.CLSaaUT + ".utid";
		//System.out.println(SQLString);
		MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, CLSaaUTL);
		//myAW.WriteCSV("./Data_DDS/CLSaaUTL.csv", SQLString);
		//myAW.QueryToXML(SQLString, CLSaaUTL);
	}
	
	// three table join
	private static void SRQaaCLS(QueryManager myAW){	
		File SRQaaCLS = new File("./results/SRQaaCLS.xml"); 
		String SQLString =
				"SELECT " + DDS.SRQaaSSRQ + ".srqid, " + DDS.DRQaaCLS + ".clsid" + " " + //COUNT(*) " + //
				"FROM " + DDS.SRQaaSSRQ + " " +
				"INNER JOIN " + DDS.SSRQaaDRQ + " " + 
				"ON " + DDS.SSRQaaDRQ + ".ssrqid = " + DDS.SRQaaSSRQ + ".ssrqid" + " " +
				"INNER JOIN " + DDS.DRQaaCLS + " " +
				"ON " + DDS.DRQaaCLS +  ".drqid = " + DDS.SSRQaaDRQ + ".drqid";
		//System.out.println(SQLString);
		//MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, SRQaaCLS);
		myAW.WriteCSV("./Data_DDS/SRQaaCLS.csv", SQLString);
		//myAW.QueryToXML(SQLString, SRQaaCLS);
	}
	

	
	private static void JoinSRQtoPPLtoUTL(QueryManager myAW){	
		File JoinSRQtoPPLtoUTL = new File("./results/JoinSRQtoPPLtoUTL.xml"); 
		String SQLString =
				"SELECT "  +  DDS.SRQaaSSRQ + ".srqid, " + DDS.UTaaUTL + ".utlid" + " " +
				"FROM " + DDS.SRQaaSSRQ + " " +
				"INNER JOIN " + DDS.SSRQaaDRQ + " " + 
				"ON " + DDS.SSRQaaDRQ + ".ssrqid = " + DDS.SRQaaSSRQ + ".ssrqid" + " " +
				"INNER JOIN " + DDS.DRQaaCLS + " " +
				"ON " + DDS.DRQaaCLS + ".drqid = " + DDS.SSRQaaDRQ + ".drqid" + " " +
				"INNER JOIN " + DDS.CLSaaUT+ " " + 
				"ON " + DDS.CLSaaUT + ".clsid = " + DDS.DRQaaCLS + ".clsid" + " " +
				"INNER JOIN " + DDS.BGRaaCLS + " " + 
				"ON " + DDS.BGRaaCLS + ".clsid = " + DDS.CLSaaUT + ".clsid" + " " +
				"INNER JOIN " + DDS.PPLaaBGR + " " + 
				"ON " + DDS.PPLaaBGR + ".bgrid = " + DDS.BGRaaCLS + ".bgrid" + " " +
				"INNER JOIN " + DDS.UTaaUTL + " " +  
				"ON " + DDS.UTaaUTL + ".utid = " + DDS.CLSaaUT + ".utid";
		System.out.println(SQLString);
		MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, JoinSRQtoPPLtoUTL);
		//myAW.WriteCSV("./Data_DDS/CPaaECS.csv", SQLString);
		//myAW.QueryToXML(SQLString, JoinSRQtoPPLtoUTL);
	}
	


}
