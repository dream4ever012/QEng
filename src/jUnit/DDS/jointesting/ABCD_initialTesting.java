package jUnit.DDS.jointesting;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Before;
import org.junit.Test;
import ResourceStrings.ABCD;
import optimizer.AskWise;
import optimizer.QueryManager;
import qEng.ExternalOracle;
import qEng.InternalH2;

import utils.CreateTablesInMemoryABCD;
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
		// A; A6
		//CreateTablesInMemoryABCD.createTablesInMemoryABCD(myAW);
		//CreateTablesInMemoryABCD.registerTMABCD(myAW);
		
		// A2, A5, 
		//CreateTablesInMemoryABCD.createTablesInMemoryABCD2(myAW);
		//CreateTablesInMemoryABCD.registerTMABCD2(myAW);
		
		//CreateTablesInMemoryABCD.createTablesInMemoryABCD3(myAW);
		//CreateTablesInMemoryABCD.registerTMABCD3(myAW);

		CreateTablesInMemoryABCD.createTablesInMemoryABCD4(myAW);
		//CreateTablesInMemoryABCD.registerTMABCD4(myAW);

		setupIsDone = true;
		System.out.println("Setup Done");
		}
	}
	@Test
	public void test() {
		/*
		 * 
		 * 
		 */
		// AaaBaaCaaD.xml cost: 457 498 291 254 462 // avg 392.4
		// without indexing: AaaBaaCaaD.xml cost: 28068 26806 31447 27452 27406 // avg 28235.8
		// card: 46926
		//AaaBaaCaaD(myAW);
		
		
		//DaaCaaBaaA.xml cost: 223 223 311 250 286 // avg 336.4
		// without indexing: 22071 21816 21422 22311 23840
		//DaaCaaBaaA(myAW);
		
		// AaaBaaC.xml cost: 111 110 90 90 130 // avg. 106.2
		// card 12240
		// WO/ Ind: 4860 4668
		//AaaBaaC(myAW);                       // TOTAL 315   // WO/Ind: 14706 (=4860+9846)
		
		// AaBaCaaD.xml cost: 260 212 174 182 216 // avg. 208.8
		// card: 49408
		// WO/ Ind: 9846 9102
		//AaBaCaaD(myAW);
		
		//BaaCaaD.xml cost: 120 104 110 172 160 // avg. 133.2
		// card 24844
		// WO/ Ind: 4977
		//BaaCaaD(myAW);                          // TOTAL 454.2  // WO/Ind: 23158 (=4977+18181)
		
		// AaaBaCaD.xml cost: 380 260 276 249 440 // avg. 321
		// WO/ Ind: 18181 16239
		//AaaBaCaD(myAW);

		
		
		//TOTAL: 1313.2; WOiDx
		//AaaBaaC2.xml cost: 215 224 279 272 262 // 250.4
		// WO/ind: 23311 37664 
		//AaaBaaC2(myAW);
		//AaBaCaaD2.xml cost: 1082 1124 887 1095 1132 // 1062.8
		// WO/ind: 32994 36265 36677
		//AaBaCaaD2(myAW);
		
		//TOTAL: 1549.2
		//BaaCaaD2.xml cost: 230 240 288 163 307// 245.6
		// WO/ind: 18981 16346 17674 19083
		//BaaCaaD2(myAW);
		//AaaBaCaD2.xml cost: 1267 1565 1300 1367 1019//1303.6
		// WO/ind: 222899 194685
		//AaaBaCaD2(myAW);
		
		/*
		 *  trace link pattern
		 * 
		 */	
		//AaaBaaC3.xml cost: 5110 4549 5400 5162 5264
		//AaaBaaC3(myAW);
		//BaaCaaD3.xml cost: 4020 4782 5038 4971 4547
		// BaaCaaD3(myAW);
		
		// AaBaCaaD3.xml cost: 5538 4655 5505 4867 5468
		//AaBaCaaD3(myAW);
		// AaaBaCaD3.xml cost: 8435 8688 8894 9708 9823
		//AaaBaCaD3(myAW);
		
		/*
		 * "does table size is the critical issue?
		 * A(3k) = AB(9k) = B(3k) = BC(9k) = C(1.5k) = CD(9k) = D(1.5k)
		 * AaaB4
		 */
		// AaaBaaC4.xml cost: 10117 9967 10021  9866
		//AaaBaaC4(myAW);

		// AaBaCaaD4.xml cost: 29169 26568
		//AaBaCaaD4(myAW); 
		
		// BaaCaaD4.xml cost: 9390 9679 10310 10300
		// BaaCaaD4(myAW);
		// AaaBaCaD4.xml cost: 60332 53519 60514
		//AaaBaCaD4(myAW);
		
		
		/* does not show any difference
		 * equal distribution of trace link
		 * 6k
		 */
		// AaaBaaC5.xml cost: 15757 18163 17279
		//AaaBaaC5(myAW);
		//AaBaCaaD5.xml cost: 29792 28734
		//AaBaCaaD5(myAW); 
		// BaaCaaD5.xml cost: 14768 17440 16780
		//BaaCaaD5(myAW);
		// AaaBaCaD5.xml cost: 63802 70650
		//AaaBaCaD5(myAW);
		
		/* does show much different from unequal distribution
		 * equal distribution of trace link
		 * 3k
		 */
		//	AaaBaaC6.xml cost: 4754 5279
		//AaaBaaC6(myAW);
		//	BaaCaaD6.xml cost: 5405 6850 4841
		//BaaCaaD6(myAW);
		//	AaBaCaaD6.xml cost: 18395 21762 20586
		//AaBaCaaD6(myAW);
		//	AaaBaCaD6.xml cost: 17174 16699 15527
		//AaaBaCaD6(myAW);
		
		
		/* smaller tables with the same size trace matrices
		 * CD 3k
		 * equal distribution of trace link
		 * A(2k) = AB(6k) = B(2k) = BC(6k) = C(1k) = CD(3k) = D(1k)
		 */
		//	AaaBaaC8.xml cost: 5186 5045 // card 18k
		//AaaBaaC8(myAW);
		//	AaBaCaaD8.xml cost: 6586 6798 // card 54k
		//AaBaCaaD8(myAW);
		//	BaaCaaD8.xml cost: 3061 3373 // card 18k
		//BaaCaaD8(myAW);
		//	AaaBaCaD8.xml cost: 9679 11598 // card 54k
		//AaaBaCaD8(myAW);
		
		/* smaller tables with the same size trace matrices
		 * equal distribution of trace link
		 * A(2k) = AB(6k) = B(2k) = BC(6k) = C(1k) = CD(6k) = D(1k)
		 */
		//	AaaBaaC7.xml cost: 5856 6410
		//AaaBaaC7(myAW);
		//	BaaCaaD7.xml cost: 4889 5453
		//BaaCaaD7(myAW);
		//	AaBaCaaD7.xml cost: 12072 12799
		//AaBaCaaD7(myAW);
		//	AaaBaCaD7.xml cost: 26846 25317
		//AaaBaCaD7(myAW);
		
		System.out.println("Done");
		
	}

	private static void AaBaCaaD8(QueryManager myAW){	
		File AaBaCaaD8 = new File("./results/AaBaCaaD8.xml"); 
		SQLString =
				"SELECT " + ABCD.CaaD8 + ".DID, " + ABCD.AaaBaaC8 + ".AID" + " " + //COUNT(*) " + // "  + 
				"FROM " + ABCD.CaaD8 + " " + 
				"INNER JOIN " + ABCD.AaaBaaC8 + " " + 
				"ON " + ABCD.AaaBaaC8 + ".CID = " + ABCD.CaaD8 + ".CID"; 
		System.out.println(SQLString);
		MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, AaBaCaaD8);
		//myAW.WriteCSV("./Data_ABCD/AaBaCaaD8.csv", SQLString);
		//myAW.QueryToXML(SQLString, AaBaCaaD8);
	}
	
	private static void AaaBaCaD8(QueryManager myAW){	
		File AaaBaCaD8 = new File("./results/AaaBaCaD8.xml"); 
		SQLString =
				"SELECT " + ABCD.AaaB8 + ".AID, " + ABCD.BaaCaaD8 + ".DID" + " " + //COUNT(*) " + // "  + 
				"FROM " + ABCD.AaaB8 + " " + 
				"INNER JOIN " + ABCD.BaaCaaD8 + " " + 
				"ON " + ABCD.BaaCaaD8 + ".BID = " + ABCD.AaaB8 + ".BID"; 
		System.out.println(SQLString);
		MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, AaaBaCaD8);
		//myAW.WriteCSV("./Data_ABCD/AaaBaCaD8.csv", SQLString);
		//myAW.QueryToXML(SQLString, AaaBaCaD8);
	}
	
	private static void AaaBaaC8(QueryManager myAW){	
		File AaaBaaC8 = new File("./results/AaaBaaC8.xml"); 
		SQLString =
				"SELECT " + ABCD.AaaB8 + ".AID, " + ABCD.BaaC8 + ".CID" + " " + //COUNT(*) " + // "  + 
				"FROM " + ABCD.AaaB8 + " " + 
				"INNER JOIN " + ABCD.BaaC8 + " " + 
				"ON " + ABCD.BaaC8 + ".BID = " + ABCD.AaaB8 + ".BID"; 
		System.out.println(SQLString);
		MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, AaaBaaC8);
		//myAW.WriteCSV("./Data_ABCD/AaaBaaC8.csv", SQLString);
		//myAW.QueryToXML(SQLString, AaaBaaC8);
	}
	
	private static void BaaCaaD8(QueryManager myAW){	
		File BaaCaaD8 = new File("./results/BaaCaaD8.xml"); 
		SQLString =
				"SELECT " + ABCD.BaaC8 + ".BID, " + ABCD.CaaD8 + ".DID" + " " + //COUNT(*) " + // "  + 
				"FROM " + ABCD.BaaC8 + " " + 
				"INNER JOIN " + ABCD.CaaD8 + " " + 
				"ON " + ABCD.CaaD8 + ".CID = " + ABCD.BaaC8 + ".CID"; 
		System.out.println(SQLString);
		MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, BaaCaaD8);
		//myAW.WriteCSV("./Data_ABCD/BaaCaaD8.csv", SQLString);
		//myAW.QueryToXML(SQLString, BaaCaaD8);
	}
	
	private static void AaBaCaaD7(QueryManager myAW){	
		File AaBaCaaD7 = new File("./results/AaBaCaaD7.xml"); 
		SQLString =
				"SELECT " + ABCD.CaaD7 + ".DID, " + ABCD.AaaBaaC7 + ".AID" + " " + //COUNT(*) " + // "  + 
				"FROM " + ABCD.CaaD7 + " " + 
				"INNER JOIN " + ABCD.AaaBaaC7 + " " + 
				"ON " + ABCD.AaaBaaC7 + ".CID = " + ABCD.CaaD7 + ".CID"; 
		System.out.println(SQLString);
		MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, AaBaCaaD7);
		//myAW.WriteCSV("./Data_ABCD/AaBaCaaD7.csv", SQLString);
		//myAW.QueryToXML(SQLString, AaBaCaaD7);
	}
	
	private static void AaaBaCaD7(QueryManager myAW){	
		File AaaBaCaD7 = new File("./results/AaaBaCaD7.xml"); 
		SQLString =
				"SELECT " + ABCD.AaaB7 + ".AID, " + ABCD.BaaCaaD7 + ".DID" + " " + //COUNT(*) " + // "  + 
				"FROM " + ABCD.AaaB7 + " " + 
				"INNER JOIN " + ABCD.BaaCaaD7 + " " + 
				"ON " + ABCD.BaaCaaD7 + ".BID = " + ABCD.AaaB7 + ".BID"; 
		System.out.println(SQLString);
		MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, AaaBaCaD7);
		//myAW.WriteCSV("./Data_ABCD/AaaBaCaD7.csv", SQLString);
		//myAW.QueryToXML(SQLString, AaaBaCaD7);
	}
	
	private static void AaaBaaC7(QueryManager myAW){	
		File AaaBaaC7 = new File("./results/AaaBaaC7.xml"); 
		SQLString =
				"SELECT " + ABCD.AaaB7 + ".AID, " + ABCD.BaaC7 + ".CID" + " " + //COUNT(*) " + // "  + 
				"FROM " + ABCD.AaaB7 + " " + 
				"INNER JOIN " + ABCD.BaaC7 + " " + 
				"ON " + ABCD.BaaC7 + ".BID = " + ABCD.AaaB7 + ".BID"; 
		System.out.println(SQLString);
		MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, AaaBaaC7);
		//myAW.WriteCSV("./Data_ABCD/AaaBaaC7.csv", SQLString);
		//myAW.QueryToXML(SQLString, AaaBaaC7);
	}
	
	private static void BaaCaaD7(QueryManager myAW){	
		File BaaCaaD7 = new File("./results/BaaCaaD7.xml"); 
		SQLString =
				"SELECT " + ABCD.BaaC7 + ".BID, " + ABCD.CaaD7 + ".DID" + " " + //COUNT(*) " + // "  + 
				"FROM " + ABCD.BaaC7 + " " + 
				"INNER JOIN " + ABCD.CaaD7 + " " + 
				"ON " + ABCD.CaaD7 + ".CID = " + ABCD.BaaC7 + ".CID"; 
		System.out.println(SQLString);
		MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, BaaCaaD7);
		//myAW.WriteCSV("./Data_ABCD/BaaCaaD7.csv", SQLString);
		//myAW.QueryToXML(SQLString, BaaCaaD7);
	}
	
	
	private static void AaBaCaaD6(QueryManager myAW){	
		File AaBaCaaD6 = new File("./results/AaBaCaaD6.xml"); 
		SQLString =
				"SELECT " + ABCD.CaaD6 + ".DID, " + ABCD.AaaBaaC6 + ".AID" + " " + //COUNT(*) " + // "  + 
				"FROM " + ABCD.CaaD6 + " " + 
				"INNER JOIN " + ABCD.AaaBaaC6 + " " + 
				"ON " + ABCD.AaaBaaC6 + ".CID = " + ABCD.CaaD6 + ".CID"; 
		System.out.println(SQLString);
		MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, AaBaCaaD6);
		//myAW.WriteCSV("./Data_ABCD/AaBaCaaD6.csv", SQLString);
		//myAW.QueryToXML(SQLString, AaBaCaaD6);
	}
	
	private static void AaaBaCaD6(QueryManager myAW){	
		File AaaBaCaD6 = new File("./results/AaaBaCaD6.xml"); 
		SQLString =
				"SELECT " + ABCD.AaaB6 + ".AID, " + ABCD.BaaCaaD6 + ".DID" + " " + //COUNT(*) " + // "  + 
				"FROM " + ABCD.AaaB6 + " " + 
				"INNER JOIN " + ABCD.BaaCaaD6 + " " + 
				"ON " + ABCD.BaaCaaD6 + ".BID = " + ABCD.AaaB6 + ".BID"; 
		System.out.println(SQLString);
		MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, AaaBaCaD6);
		//myAW.WriteCSV("./Data_ABCD/AaaBaCaD6.csv", SQLString);
		//myAW.QueryToXML(SQLString, AaaBaCaD6);
	}
	private static void AaaBaaC6(QueryManager myAW){	
		File AaaBaaC6 = new File("./results/AaaBaaC6.xml"); 
		SQLString =
				"SELECT " + ABCD.AaaB6 + ".AID, " + ABCD.BaaC6 + ".CID" + " " + //COUNT(*) " + // "  + 
				"FROM " + ABCD.AaaB6 + " " + 
				"INNER JOIN " + ABCD.BaaC6 + " " + 
				"ON " + ABCD.BaaC6 + ".BID = " + ABCD.AaaB6 + ".BID"; 
		System.out.println(SQLString);
		MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, AaaBaaC6);
		//myAW.WriteCSV("./Data_ABCD/AaaBaaC6.csv", SQLString);
		//myAW.QueryToXML(SQLString, AaaBaaC6);
	}
	
	private static void BaaCaaD6(QueryManager myAW){	
		File BaaCaaD6 = new File("./results/BaaCaaD6.xml"); 
		SQLString =
				"SELECT " + ABCD.BaaC6 + ".BID, " + ABCD.CaaD6 + ".DID" + " " + //COUNT(*) " + // "  + 
				"FROM " + ABCD.BaaC6 + " " + 
				"INNER JOIN " + ABCD.CaaD6 + " " + 
				"ON " + ABCD.CaaD6 + ".CID = " + ABCD.BaaC6 + ".CID"; 
		System.out.println(SQLString);
		MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, BaaCaaD6);
		//myAW.WriteCSV("./Data_ABCD/BaaCaaD6.csv", SQLString);
		//myAW.QueryToXML(SQLString, BaaCaaD6);
	}
	
	private static void AaBaCaaD5(QueryManager myAW){	
		File AaBaCaaD5 = new File("./results/AaBaCaaD5.xml"); 
		SQLString =
				"SELECT " + ABCD.AaaBaaC5 + ".AID, " + ABCD.CaaD5 + ".DID" + " " + //COUNT(*) " + // "  + 
				"FROM " + ABCD.CaaD5 + " " + 
				"INNER JOIN " + ABCD.AaaBaaC5 + " " + 
				"ON " + ABCD.AaaBaaC5 + ".CID = " + ABCD.CaaD5 + ".CID"; 
		System.out.println(SQLString);
		MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, AaBaCaaD5);
		//myAW.WriteCSV("./Data_ABCD/AaBaCaaD5.csv", SQLString);
		//myAW.QueryToXML(SQLString, AaBaCaaD5);
	}
	
	private static void AaaBaCaD5(QueryManager myAW){	
		File AaaBaCaD5 = new File("./results/AaaBaCaD5.xml"); 
		SQLString =
				"SELECT " + ABCD.AaaB5 + ".AID, " + ABCD.BaaCaaD5 + ".DID" + " " + //COUNT(*) " + // "  + 
				"FROM " + ABCD.AaaB5 + " " + 
				"INNER JOIN " + ABCD.BaaCaaD5 + " " + 
				"ON " + ABCD.BaaCaaD5 + ".BID = " + ABCD.AaaB5 + ".BID"; 
		System.out.println(SQLString);
		MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, AaaBaCaD5);
		//myAW.WriteCSV("./Data_ABCD/AaaBaCaD5.csv", SQLString);
		//myAW.QueryToXML(SQLString, AaaBaCaD5);
	}
	
	private static void AaaBaaC5(QueryManager myAW){	
		File AaaBaaC5 = new File("./results/AaaBaaC5.xml"); 
		SQLString =
				"SELECT " + ABCD.AaaB5 + ".AID, " + ABCD.BaaC5 + ".CID" + " " + //COUNT(*) " + // "  + 
				"FROM " + ABCD.AaaB5 + " " + 
				"INNER JOIN " + ABCD.BaaC5 + " " + 
				"ON " + ABCD.BaaC5 + ".BID = " + ABCD.AaaB5 + ".BID"; 
		System.out.println(SQLString);
		MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, AaaBaaC5);
		//myAW.WriteCSV("./Data_ABCD/AaaBaaC5.csv", SQLString);
		//myAW.QueryToXML(SQLString, AaaBaaC5);
	}
	
	private static void BaaCaaD5(QueryManager myAW){	
		File BaaCaaD5 = new File("./results/BaaCaaD5.xml"); 
		SQLString =
				"SELECT " + ABCD.BaaC5 + ".BID, " + ABCD.CaaD5 + ".DID" + " " + //COUNT(*) " + // "  + 
				"FROM " + ABCD.BaaC5 + " " + 
				"INNER JOIN " + ABCD.CaaD5 + " " + 
				"ON " + ABCD.CaaD5 + ".CID = " + ABCD.BaaC5 + ".CID"; 
		System.out.println(SQLString);
		MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, BaaCaaD5);
		//myAW.WriteCSV("./Data_ABCD/BaaCaaD5.csv", SQLString);
		//myAW.QueryToXML(SQLString, BaaCaaD5);
	}
	
	private static void AaBaCaaD4(QueryManager myAW){	
		File AaBaCaaD4 = new File("./results/AaBaCaaD4.xml"); 
		SQLString =
				"SELECT " + ABCD.AaaBaaC4 + ".AID, " + ABCD.CaaD4 + ".DID" + " " + //COUNT(*) " + // "  + 
				"FROM " + ABCD.CaaD4 + " " + 
				"INNER JOIN " + ABCD.AaaBaaC4 + " " + 
				"ON " + ABCD.AaaBaaC4 + ".CID = " + ABCD.CaaD4 + ".CID"; 
		System.out.println(SQLString);
		MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, AaBaCaaD4);
		//myAW.WriteCSV("./Data_ABCD/AaBaCaaD4.csv", SQLString);
		//myAW.QueryToXML(SQLString, AaBaCaaD4);
	}
	
	private static void AaaBaCaD4(QueryManager myAW){	
		File AaaBaCaD4 = new File("./results/AaaBaCaD4.xml"); 
		SQLString =
				"SELECT " + ABCD.AaaB4 + ".AID, " + ABCD.BaaCaaD4 + ".DID" + " " + //COUNT(*) " + // "  + 
				"FROM " + ABCD.AaaB4 + " " + 
				"INNER JOIN " + ABCD.BaaCaaD4 + " " + 
				"ON " + ABCD.BaaCaaD4 + ".BID = " + ABCD.AaaB4 + ".BID"; 
		System.out.println(SQLString);
		MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, AaaBaCaD4);
		//myAW.WriteCSV("./Data_ABCD/AaaBaCaD4.csv", SQLString);
		//myAW.QueryToXML(SQLString, AaaBaCaD4);
	}
	
	private static void AaaBaaC4(QueryManager myAW){	
		File AaaBaaC4 = new File("./results/AaaBaaC4.xml"); 
		SQLString =
				"SELECT " + ABCD.AaaB4 + ".AID, " + ABCD.BaaC4 + ".CID" + " " + //COUNT(*) " + // "  + 
				"FROM " + ABCD.AaaB4 + " " + 
				"INNER JOIN " + ABCD.BaaC4 + " " + 
				"ON " + ABCD.BaaC4 + ".BID = " + ABCD.AaaB4 + ".BID"; 
		System.out.println(SQLString);
		MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, AaaBaaC4);
		//myAW.WriteCSV("./Data_ABCD/AaaBaaC4.csv", SQLString);
		//myAW.QueryToXML(SQLString, AaaBaaC4);
	}
	
	private static void BaaCaaD4(QueryManager myAW){	
		File BaaCaaD4 = new File("./results/BaaCaaD4.xml"); 
		SQLString =
				"SELECT " + ABCD.BaaC4 + ".BID, " + ABCD.CaaD4 + ".DID" + " " + //COUNT(*) " + // "  + 
				"FROM " + ABCD.BaaC4 + " " + 
				"INNER JOIN " + ABCD.CaaD4 + " " + 
				"ON " + ABCD.CaaD4 + ".CID = " + ABCD.BaaC4 + ".CID"; 
		System.out.println(SQLString);
		MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, BaaCaaD4);
		//myAW.WriteCSV("./Data_ABCD/BaaCaaD4.csv", SQLString);
		//myAW.QueryToXML(SQLString, BaaCaaD4);
	}
	
	/*
	 *  trace link pattern
	 * 
	 */		
	private static void AaBaCaaD3(QueryManager myAW){	
		File AaBaCaaD3 = new File("./results/AaBaCaaD3.xml"); 
		SQLString =
				"SELECT " + ABCD.AaaBaaC3 + ".AID, " + ABCD.CaaD3 + ".DID" + " " + //COUNT(*) " + // "  + 
				"FROM " + ABCD.CaaD3 + " " + 
				"INNER JOIN " + ABCD.AaaBaaC3 + " " + 
				"ON " + ABCD.AaaBaaC3 + ".CID = " + ABCD.CaaD3 + ".CID"; 
		System.out.println(SQLString);
		MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, AaBaCaaD3);
		//myAW.WriteCSV("./Data_ABCD/AaBaCaaD3.csv", SQLString);
		//myAW.QueryToXML(SQLString, AaBaCaaD3);
	}
	
	private static void AaaBaCaD3(QueryManager myAW){	
		File AaaBaCaD3 = new File("./results/AaaBaCaD3.xml"); 
		SQLString =
				"SELECT " + ABCD.AaaB3 + ".AID, " + ABCD.BaaCaaD3 + ".DID" + " " + //COUNT(*) " + // "  + 
				"FROM " + ABCD.AaaB3 + " " + 
				"INNER JOIN " + ABCD.BaaCaaD3 + " " + 
				"ON " + ABCD.BaaCaaD3 + ".BID = " + ABCD.AaaB3 + ".BID"; 
		System.out.println(SQLString);
		MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, AaaBaCaD3);
		//myAW.WriteCSV("./Data_ABCD/AaaBaCaD3.csv", SQLString);
		//myAW.QueryToXML(SQLString, AaaBaCaD3);
	}
	
	private static void AaaBaaC3(QueryManager myAW){	
		File AaaBaaC3 = new File("./results/AaaBaaC3.xml"); 
		SQLString =
				"SELECT " + ABCD.AaaB3 + ".AID, " + ABCD.BaaC3 + ".CID" + " " + //COUNT(*) " + // "  + 
				"FROM " + ABCD.AaaB3 + " " + 
				"INNER JOIN " + ABCD.BaaC3 + " " + 
				"ON " + ABCD.BaaC3 + ".BID = " + ABCD.AaaB3 + ".BID"; 
		System.out.println(SQLString);
		MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, AaaBaaC3);
		//myAW.WriteCSV("./Data_ABCD/AaaBaaC3.csv", SQLString);
		//myAW.QueryToXML(SQLString, AaaBaaC3);
	}
	
	private static void BaaCaaD3(QueryManager myAW){	
		File BaaCaaD3 = new File("./results/BaaCaaD3.xml"); 
		SQLString =
				"SELECT " + ABCD.BaaC3 + ".BID, " + ABCD.CaaD3 + ".DID" + " " + //COUNT(*) " + // "  + 
				"FROM " + ABCD.BaaC3 + " " + 
				"INNER JOIN " + ABCD.CaaD3 + " " + 
				"ON " + ABCD.CaaD3 + ".CID = " + ABCD.BaaC3 + ".CID"; 
		System.out.println(SQLString);
		MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, BaaCaaD3);
		//myAW.WriteCSV("./Data_ABCD/BaaCaaD3.csv", SQLString);
		//myAW.QueryToXML(SQLString, BaaCaaD3);
	}
	
/*
 *  trace link pattern
 * 
 */	
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
		//myAW.WriteCSV("./Data_DDS/DaaCaaBaaA.csv", SQLString);
		//myAW.QueryToXML(SQLString, DaaCaaBaaA);
	}
	
	
}
