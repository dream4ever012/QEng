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
		
		JoinSRQtoPPLtoUTL(myAW);
		System.out.println("Done");
		
	}
	
	private static void JoinSRQtoPPLtoUTL(QueryManager myAW){	
		File JoinATtoPJT = new File("./results/JoinATtoHZD.xml"); 
		String SQLString =
				"SELECT *"  + " " +
				"FROM " + DDS.SRQaaSSRQ + " " +
				"INNER JOIN " + DDS.SSRQaaDRQ + " " + 
				"ON " + DDS.SSRQaaDRQ + ".ssrqid = " + DDS.SRQaaSSRQ + ".ssrqid" + " " +
				"INNER JOIN " + DDS.DRQaaCLS + " " +
				"ON " + DDS.DRQaaCLS + ".drqid = " + DDS.SSRQaaDRQ + ".drqid" + " " +
				"INNER JOIN " + DDS.CLSaaUT+ " " + 
				"ON " + DDS.CLSaaUT + ".clsid = " + DDS.DRQaaCLS + ".clsid" + " " +
				"INNER JOIN " + DDS.UTaaUTL + " " +  
				"ON " + DDS.UTaaUTL + ".utid = " + DDS.CLSaaUT + ".utid" + " " + 
				"INNER JOIN " + DDS.BGRaaCLS + " " + 
				"ON " + DDS.BGRaaCLS + ".clsid = " + DDS.CLSaaUT + ".clsid"; // + " " +
//				"INNER JOIN " + DDS.PPLaaBGR + " " + 
//				"ON " + DDS.PPLaaBGR + ".bgrid = " + DDS.BGRaaCLS + ".bgrid"; 
		//System.out.println(SQLString);
		MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, JoinATtoPJT);
		myAW.QueryToXML(SQLString, JoinATtoPJT);
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
