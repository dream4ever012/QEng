package jUnit.DDS.jointesting;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Before;
import org.junit.Test;
import ResourceStrings.ABCD;
import ResourceStrings.DDS;
import optimizer.AskWise;
import optimizer.QueryManager;
import qEng.ExternalOracle;
import qEng.InternalH2;

import utils.CreateTablesInMemoryABCD;
import utils.CreateTablesInMemoryDDS;
import utils.MeasureCostArbitrary;

public class ABCD_initialTesting {
	public static Boolean setupIsDone = false;
	private static final String H2PROTO = "jdbc:h2:";
	private static final String IH2FP = "./Data/TestCaseDataBases/";
	private static final String IH2DBName = "ABCD_initialTesting";
	private static final String TRACELEVEL = ";TRACE_LEVEL_FILE=3;TRACE_MAX_FILE_SIZE=20";
	private static String IH2DBURL;
	private String SQLString;
	
	private QueryManager myAW;
	// private QueryManager myOAW;
	
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
		// myOAW = new AskWise(new ExternalOracle());
		// create tablelink
		CreateTablesInMemoryABCD.createTablesInMemoryABCD(myAW);
		CreateTablesInMemoryABCD.registerTMABCD(myAW);
		setupIsDone = true;
		System.out.println("Setup Done");
		}
	}
	@Test
	public void test() {

		
		System.out.println("Done");
		
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
	
}
