package jUnit.DDS.jointesting;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Before;
import org.junit.Test;
import ResourceStrings.DDS;
import ResourceStrings.SD;
import optimizer.AskWise;
import optimizer.QueryManager;
import qEng.ExternalOracle;
import qEng.InternalH2;

import utils.CreateTablesInMemoryDDS;
import utils.MeasureCostArbitrary;

public class DDS_initalTesting {
	public static Boolean setupIsDone = false;
	private static final String H2PROTO = "jdbc:h2:";
	private static final String IH2FP = "./Data/TestCaseDataBases/";
	private static final String IH2DBName = "JoinCardTestGtoECS";
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
		// create tablelink
		CreateTablesInMemoryDDS.createTablesInMemory(myAW);
		CreateTablesInMemoryDDS.registerTMDDS(myAW);
		setupIsDone = true;
		}
	}
	@Test
	public void test() {
		//testing(myAW);
		// card: 7669
		//JoinATtoPJT(myAW); // 706 606 636 642
		
		// card: 1258349
		// 6435
		// JoinSRQtoPPLtoUT(myAW);
		
		// card:  103760
		// 973 730 738 745
		//JoinSRQtoPPL(myAW);
		
		// card: 2393098
		// 1800 1801 1841
		// JoinCLS_UTL(myAW);
		
		// card: 4451239
		// 8312 7768
		//JoinBGR_UTL(myAW);
		
		// card: 19,173,104
		//JoinSRQtoPPLtoUTL.xml cost: 31393 31175 29130
		//JoinSRQtoPPLtoUTL(myAW);
		
		//SRQaaUTwPredOnCLS1.xml cost: 529 			wo/ predicate 933 922
		// card: wo/ pred 315141; w/ pred 2363  7.498%
		//SRQaaUTwPredOnCLS1(myAW); 
		
		
		// SRQaaUTwPredOnCLS1.xml cost: 452 12883 //  1354 1325
		// card: wo/pred 4795637; w/ pred 36902  7.694%
		SRQaaUTLwPredOnCLS1(myAW);
		
		System.out.println("Done");
		
	}
	
	private static void SRQaaUTLwPredOnCLS1(QueryManager myAW){	
		File SRQaaUTLwPredOnCLS1 = new File("./results/SRQaaUTLwPredOnCLS1.xml"); 
		String SQLString =
				"SELECT " + DDS.SRQaaSSRQ + ".srqid, " + DDS.UTaaUTL + ".utlid" + " " +
				"FROM " + DDS.SRQaaSSRQ + " " + 
				"INNER JOIN " + DDS.SSRQaaDRQ + " " + 
				"ON " + DDS.SSRQaaDRQ + ".ssrqid = " + DDS.SRQaaSSRQ + ".ssrqid" + " " +
				"INNER JOIN " + DDS.DRQaaCLS + " " +
				"ON " + DDS.DRQaaCLS + ".drqid = " + DDS.SSRQaaDRQ + ".drqid" + " " +
				"INNER JOIN " + DDS.CLS +  " " +
				"ON " + DDS.CLS + ".clsid = " + DDS.DRQaaCLS + ".clsid" + " " +
				"INNER JOIN " + DDS.CLSaaUT + " " +
				"ON " + DDS.CLSaaUT + ".clsid = " + DDS.CLS + ".clsid" + " " + // ;
				"INNER JOIN " + DDS.UTaaUTL+ " " + 
				"ON " + DDS.UTaaUTL + ".utid = " + DDS.CLSaaUT + ".utid" + " " +
				"WHERE " + DDS.CLS + ".CLSAUTHOR = 'Caleb3'";

		System.out.println(SQLString);
		MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, SRQaaUTLwPredOnCLS1);
		//myAW.QueryToXML(SQLString, SRQaaUTLwPredOnCLS1);
	}
	
	private static void SRQaaUTwPredOnCLS1(QueryManager myAW){	
		File SRQaaUTwPredOnCLS1 = new File("./results/SRQaaUTwPredOnCLS1.xml"); 
		String SQLString =
				"SELECT COUNT(*) " + // "  + DDS.SRQaaSSRQ + ".srqid, " + DDS.SSRQaaDRQ + ".drqid" + " " +
				"FROM " + DDS.SRQaaSSRQ + " " + //  "," + DDS.CLS + " " +
				"INNER JOIN " + DDS.SSRQaaDRQ + " " + 
				"ON " + DDS.SSRQaaDRQ + ".ssrqid = " + DDS.SRQaaSSRQ + ".ssrqid" + " " +
				"INNER JOIN " + DDS.DRQaaCLS + " " +
				"ON " + DDS.DRQaaCLS + ".drqid = " + DDS.SSRQaaDRQ + ".drqid" + " " +
				"INNER JOIN " + DDS.CLS +  " " +
				"ON " + DDS.CLS + ".clsid = " + DDS.DRQaaCLS + ".clsid" + " " +
				"INNER JOIN " + DDS.CLSaaUT + " " +
				"ON " + DDS.CLSaaUT + ".clsid = " + DDS.CLS + ".clsid"; // + " " + // ;
				//"WHERE " + DDS.CLS + ".CLSAUTHOR = 'Caleb3'";
//				"INNER JOIN " + DDS.CLSaaUT+ " " + 
//				"ON " + DDS.CLSaaUT + ".clsid = " + DDS.DRQaaCLS + ".clsid"; // + " " +

		System.out.println(SQLString);
		//MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, SRQaaUTwPredOnCLS1);
		myAW.QueryToXML(SQLString, SRQaaUTwPredOnCLS1);
	}
	
	private static void JoinBGR_UTL(QueryManager myAW){	
		File JoinBGR_UTL = new File("./results/JoinBGR_UTL.xml"); 
		String SQLString =
				"SELECT COUNT(*)"  + " " +
				"FROM " + DDS.BGRaaCLS + " " +
				"INNER JOIN " + DDS.CLSaaUT + " " + 
				"ON " + DDS.CLSaaUT + ".clsid = " + DDS.BGRaaCLS + ".clsid" + " " +
				"INNER JOIN " + DDS.UTaaUTL + " " + 
				"ON " + DDS.UTaaUTL + ".utid = " + DDS.CLSaaUT + ".utid";
		MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, JoinBGR_UTL);
		//myAW.QueryToXML(SQLString, JoinBGR_UTL);
	}
	
	private static void JoinCLS_UTL(QueryManager myAW){	
		File JoinCLS_UTL = new File("./results/JoinCLS_UTL.xml"); 
		String SQLString =
				"SELECT COUNT(*)"  + " " +
				"FROM " + DDS.CLSaaUT + " " +
				"INNER JOIN " + DDS.UTaaUTL + " " + 
				"ON " + DDS.UTaaUTL + ".utid = " + DDS.CLSaaUT + ".utid";
		MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, JoinCLS_UTL);
		//myAW.QueryToXML(SQLString, JoinCLS_UTL);
	}
	
	private static void JoinSRQtoPPL(QueryManager myAW){	
		File JoinSRQtoPPL = new File("./results/JoinSRQtoPPL.xml"); 
		String SQLString =
				"SELECT COUNT(*)"  + " " +
				"FROM " + DDS.SRQaaSSRQ + " " +
				"INNER JOIN " + DDS.SSRQaaDRQ + " " + 
				"ON " + DDS.SSRQaaDRQ + ".ssrqid = " + DDS.SRQaaSSRQ + ".ssrqid" + " " +
				"INNER JOIN " + DDS.DRQaaCLS + " " +
				"ON " + DDS.DRQaaCLS + ".drqid = " + DDS.SSRQaaDRQ + ".drqid" + " " +
//				"INNER JOIN " + DDS.CLSaaUT+ " " + 
//				"ON " + DDS.CLSaaUT + ".clsid = " + DDS.DRQaaCLS + ".clsid" + " " +
//				"INNER JOIN " + DDS.UTaaUTL + " " +  
//				"ON " + DDS.UTaaUTL + ".utid = " + DDS.CLSaaUT + ".utid" + " " + 
				"INNER JOIN " + DDS.BGRaaCLS + " " + 
				"ON " + DDS.BGRaaCLS + ".clsid = " + DDS.DRQaaCLS + ".clsid" + " " +
				"INNER JOIN " + DDS.PPLaaBGR + " " + 
				"ON " + DDS.PPLaaBGR + ".bgrid = " + DDS.BGRaaCLS + ".bgrid"; 
		//System.out.println(SQLString);
		MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, JoinSRQtoPPL);
		myAW.QueryToXML(SQLString, JoinSRQtoPPL);
	}
	
	private static void JoinSRQtoPPLtoUT(QueryManager myAW){	
		File JoinSRQtoPPLtoUT = new File("./results/JoinSRQtoPPLtoUT.xml"); 
		String SQLString =
				"SELECT COUNT(*)"  + " " +
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
				"ON " + DDS.PPLaaBGR + ".bgrid = " + DDS.BGRaaCLS + ".bgrid"; 
//				"INNER JOIN " + DDS.UTaaUTL + " " +  
//				"ON " + DDS.UTaaUTL + ".utid = " + DDS.CLSaaUT + ".utid" + " " + 

		//System.out.println(SQLString);
		MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, JoinSRQtoPPLtoUT);
		myAW.QueryToXML(SQLString, JoinSRQtoPPLtoUT);
	}
	
	private static void JoinSRQtoPPLtoUTL(QueryManager myAW){	
		File JoinSRQtoPPLtoUTL = new File("./results/JoinSRQtoPPLtoUTL.xml"); 
		String SQLString =
				"SELECT COUNT(*)"  + " " +
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
				"ON " + DDS.UTaaUTL + ".utid = " + DDS.CLSaaUT + ".utid"; // + " " + 

		//System.out.println(SQLString);
		MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, JoinSRQtoPPLtoUTL);
		//myAW.QueryToXML(SQLString, JoinSRQtoPPLtoUTL);
	}
	
	private static void JoinATtoPJT(QueryManager myAW){	
		File JoinATtoPJT = new File("./results/JoinATtoHZD.xml"); 
		String SQLString =
				"SELECT *"  + " " +
				"FROM " + DDS.ATaaUC + " " +
				"INNER JOIN " + DDS.UCaaGL + " " + 
				"ON " + DDS.UCaaGL + ".ucid = " + DDS.ATaaUC + ".ucid" + " " +
				"INNER JOIN " + DDS.GLaaPJT + " " +
				"ON " + DDS.GLaaPJT + ".glid = " + DDS.UCaaGL + ".glid" + " " +
				"INNER JOIN " + DDS.GLaaSRQ + " " + 
				"ON " + DDS.GLaaSRQ + ".glid = " + DDS.UCaaGL + ".glid" + " " +
				"INNER JOIN " + DDS.FLTaaSRQ + " " +  
				"ON " + DDS.FLTaaSRQ + ".srqid = " + DDS.GLaaSRQ + ".srqid" + " " + 
				"INNER JOIN " + DDS.HZDaaFLT + " " + 
				"ON " + DDS.HZDaaFLT + ".fltid = " + DDS.FLTaaSRQ + ".fltid";
		//System.out.println(SQLString);
		MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, JoinATtoPJT);
		myAW.QueryToXML(SQLString, JoinATtoPJT);
	}
	
	private static void testing(QueryManager myAW){	
		File testing = new File("./results/testing.xml"); 
		String SQLString =
				"SELECT *"  + " " +
				"FROM " + DDS.AT;
		
		//System.out.println(SQLString);
		MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, testing);
		//myAW.QueryToXML(SQLString, testing);
	}	
}
