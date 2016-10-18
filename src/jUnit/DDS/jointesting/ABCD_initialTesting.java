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
	private static String SQLString;
	
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
//		CreateTablesInMemoryABCD.registerTMABCD(myAW);
		
//		CreateTablesInMemoryABCD.createTablesInMemoryABCD2(myAW);
//		CreateTablesInMemoryABCD.registerTMABCD2(myAW);
		setupIsDone = true;
		System.out.println("Setup Done");
		}
	}
	@Test
	public void test() {
		
		// AaaBaaCaaD.xml cost: 457 498 291 254 462 // avg 392.4
		// without indexing: AaaBaaCaaD.xml cost: 28068 26806 31447 27452 27406 // avg 28235.8
		// card: 46926
		// AaaBaaCaaD(myAW);
		
		
		//DaaCaaBaaA.xml cost: 223 223 311 250 286 // avg 336.4
		// without indexing: 22071 21816 21422 22311
		//DaaCaaBaaA(myAW);
		
		// AaaBaaC.xml cost: 111 110 90 90 130 // avg. 106.2
		// card 12240
		// WO/ Ind: 4860
		// AaaBaaC(myAW);                       // TOTAL 315
		
		// AaBaCaaD.xml cost: 260 212 174 182 216 // avg. 208.8
		// card: 49408
		// WO/ Ind: 9846
		//AaBaCaaD(myAW);
		
		//BaaCaaD.xml cost: 120 104 110 172 160 // avg. 133.2
		// card 24844
		// WO/ Ind: 4977
		//BaaCaaD(myAW);                          // TOTAL 454.2
		
		// AaaBaCaD.xml cost: 380 260 276 249 440 // avg. 321
		// WO/ Ind: 
		AaaBaCaD(myAW);

		
		
		//TOTAL: 1313.2; WOiDx
		//AaaBaaC2.xml cost: 215 224 279 272 262 // 250.4
		// WO/ind: 23311
		//AaaBaaC2(myAW);
		//AaBaCaaD2.xml cost: 1082 1124 887 1095 1132 // 1062.8
		// WO/ind: 32994
		//AaBaCaaD2(myAW);
		
		//TOTAL: 1549.2
		//BaaCaaD2.xml cost: 230 240 288 163 307// 245.6
		// WO/ind: 18981
		//BaaCaaD2(myAW);
		//AaaBaCaD2.xml cost: 1267 1565 1300 1367 1019//1303.6
		// WO/ind: 73779
		//AaaBaCaD2(myAW);

		
		System.out.println("Done");
		
	}
	
	private static void AaBaCaaD2(QueryManager myAW){	
		File AaBaCaaD2 = new File("./results/AaBaCaaD2.xml"); 
		SQLString =
				"SELECT " + ABCD.AaaBaaC2 + ".AID, " + ABCD.CaaD2 + ".DID" + " " + //COUNT(*) " + // "  + 
				"FROM " + ABCD.CaaD2 + " " + 
				"INNER JOIN " + ABCD.AaaBaaC2 + " " + 
				"ON " + ABCD.AaaBaaC2 + ".CID = " + ABCD.CaaD2 + ".CID"; 
		System.out.println(SQLString);
		MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, AaBaCaaD2);
		//myAW.WriteCSV("./Data_ABCD/AaBaCaaD2.csv", SQLString);
		//myAW.QueryToXML(SQLString, AaBaCaaD2);
	}
	
	private static void AaaBaCaD2(QueryManager myAW){	
		File AaaBaCaD2 = new File("./results/AaaBaCaD2.xml"); 
		SQLString =
				"SELECT " + ABCD.AaaB2 + ".AID, " + ABCD.BaaCaaD2 + ".DID" + " " + //COUNT(*) " + // "  + 
				"FROM " + ABCD.AaaB2 + " " + 
				"INNER JOIN " + ABCD.BaaCaaD2 + " " + 
				"ON " + ABCD.BaaCaaD2 + ".BID = " + ABCD.AaaB2 + ".BID"; 
		System.out.println(SQLString);
		MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, AaaBaCaD2);
		//myAW.WriteCSV("./Data_ABCD/AaaBaCaD2.csv", SQLString);
		//myAW.QueryToXML(SQLString, AaaBaCaD2);
	}
	
	private static void AaaBaaC2(QueryManager myAW){	
		File AaaBaaC2 = new File("./results/AaaBaaC2.xml"); 
		SQLString =
				"SELECT " + ABCD.AaaB2 + ".AID, " + ABCD.BaaC2 + ".CID" + " " + //COUNT(*) " + // "  + 
				"FROM " + ABCD.AaaB2 + " " + 
				"INNER JOIN " + ABCD.BaaC2 + " " + 
				"ON " + ABCD.BaaC2 + ".BID = " + ABCD.AaaB2 + ".BID"; 
		System.out.println(SQLString);
		MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, AaaBaaC2);
		//myAW.WriteCSV("./Data_ABCD/AaaBaaC2.csv", SQLString);
		//myAW.QueryToXML(SQLString, AaaBaaC2);
	}
	
	private static void BaaCaaD2(QueryManager myAW){	
		File BaaCaaD2 = new File("./results/BaaCaaD2.xml"); 
		SQLString =
				"SELECT " + ABCD.BaaC2 + ".BID, " + ABCD.CaaD2 + ".DID" + " " + //COUNT(*) " + // "  + 
				"FROM " + ABCD.BaaC2 + " " + 
				"INNER JOIN " + ABCD.CaaD2 + " " + 
				"ON " + ABCD.CaaD2 + ".CID = " + ABCD.BaaC2 + ".CID"; 
		System.out.println(SQLString);
		MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, BaaCaaD2);
		//myAW.WriteCSV("./Data_ABCD/BaaCaaD2.csv", SQLString);
		//myAW.QueryToXML(SQLString, BaaCaaD2);
	}
	
	
	private static void AaaBaCaD(QueryManager myAW){	
		File AaaBaCaD = new File("./results/AaaBaCaD.xml"); 
		SQLString =
				"SELECT " + ABCD.AaaB + ".AID, " + ABCD.BaaCaaD + ".DID" + " " + //COUNT(*) " + // "  + 
				"FROM " + ABCD.AaaB + " " + 
				"INNER JOIN " + ABCD.BaaCaaD + " " + 
				"ON " + ABCD.BaaCaaD + ".BID = " + ABCD.AaaB + ".BID"; 
		System.out.println(SQLString);
		MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, AaaBaCaD);
		//myAW.WriteCSV("./Data_ABCD/AaaBaCaD.csv", SQLString);
		//myAW.QueryToXML(SQLString, AaaBaCaD);
	}
	
	private static void AaBaCaaD(QueryManager myAW){	
		File AaBaCaaD = new File("./results/AaBaCaaD.xml"); 
		SQLString =
				"SELECT " + ABCD.AaaBaaC + ".AID, " + ABCD.CaaD + ".DID" + " " + //COUNT(*) " + // "  + 
				"FROM " + ABCD.AaaBaaC + " " + 
				"INNER JOIN " + ABCD.CaaD + " " + 
				"ON " + ABCD.CaaD + ".CID = " + ABCD.AaaBaaC + ".CID"; 
		System.out.println(SQLString);
		MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, AaBaCaaD);
		//myAW.WriteCSV("./Data_ABCD/AaBaCaaD.csv", SQLString);
		//myAW.QueryToXML(SQLString, AaBaCaaD);
	}
	
	
	private static void AaaBaaC(QueryManager myAW){	
		File AaaBaaC = new File("./results/AaaBaaC.xml"); 
		SQLString =
				"SELECT " + ABCD.AaaB + ".AID, " + ABCD.BaaC + ".CID" + " " + //COUNT(*) " + // "  + 
				"FROM " + ABCD.AaaB + " " + 
				"INNER JOIN " + ABCD.BaaC + " " + 
				"ON " + ABCD.BaaC + ".BID = " + ABCD.AaaB + ".BID"; 
		System.out.println(SQLString);
		MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, AaaBaaC);
		//myAW.WriteCSV("./Data_ABCD/AaaBaaC.csv", SQLString);
		//myAW.QueryToXML(SQLString, AaaBaaC);
	}
	
	private static void BaaCaaD(QueryManager myAW){	
		File BaaCaaD = new File("./results/BaaCaaD.xml"); 
		SQLString =
				"SELECT " + ABCD.BaaC + ".BID, " + ABCD.CaaD + ".DID" + " " + //COUNT(*) " + // "  + 
				"FROM " + ABCD.BaaC + " " + 
				"INNER JOIN " + ABCD.CaaD + " " + 
				"ON " + ABCD.CaaD + ".CID = " + ABCD.BaaC + ".CID"; 
		System.out.println(SQLString);
		MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, BaaCaaD);
		//myAW.WriteCSV("./Data_ABCD/BaaCaaD.csv", SQLString);
		//myAW.QueryToXML(SQLString, BaaCaaD);
	}
	
	private static void AaaBaaCaaD(QueryManager myAW){	
		File AaaBaaCaaD = new File("./results/AaaBaaCaaD.xml"); 
		SQLString =
				"SELECT COUNT(*) " + // "  + DDS.SRQaaSSRQ + ".srqid, " + DDS.SSRQaaDRQ + ".drqid" + " " +
				"FROM " + ABCD.AaaB + " " + 
				"INNER JOIN " + ABCD.BaaC + " " + 
				"ON " + ABCD.BaaC + ".BID = " + ABCD.AaaB + ".BID" + " " +
				"INNER JOIN " + ABCD.CaaD + " " +
				"ON " + ABCD.CaaD + ".CID = " + ABCD.BaaC + ".CID"; //
		System.out.println(SQLString);
		MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, AaaBaaCaaD);
		//myAW.WriteCSV("./Data_DDS/AaaBaaCaaD.csv", SQLString);
		//myAW.QueryToXML(SQLString, AaaBaaCaaD);
	}
	
	private static void DaaCaaBaaA(QueryManager myAW){	
		File DaaCaaBaaA = new File("./results/DaaCaaBaaA.xml"); 
		SQLString =
				"SELECT COUNT(*) " + // "  + DDS.SRQaaSSRQ + ".srqid, " + DDS.SSRQaaDRQ + ".drqid" + " " +
				"FROM " + ABCD.BaaC + " " + 
				"INNER JOIN " + ABCD.CaaD + " " + 
				"ON " + ABCD.CaaD + ".CID = " + ABCD.BaaC + ".CID" + " " +
				"INNER JOIN " + ABCD.AaaB + " " +
				"ON " + ABCD.AaaB + ".BID = " + ABCD.BaaC + ".BID"; //
		System.out.println(SQLString);
		MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, DaaCaaBaaA);
		//myAW.QueryToXML(SQLString, DaaCaaBaaA);
	}
	
	
}
