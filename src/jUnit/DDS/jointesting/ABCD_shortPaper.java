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

public class ABCD_shortPaper {
	public static Boolean setupIsDone = false;
	private static final String H2PROTO = "jdbc:h2:";
	private static final String IH2FP = "./Data/TestCaseDataBases/";
	private static final String IH2DBName = "ABCD_shortPaper";
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

		//CreateTablesInMemoryABCD.createTablesInMemoryABCD4(myAW);
		//CreateTablesInMemoryABCD.registerTMABCD4(myAW);
		
		CreateTablesInMemoryABCD.createTablesInMemoryABCD5(myAW);
		//CreateTablesInMemoryABCD.registerTMABCD5(myAW);

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

		
		/* FAN OUT RATIO1
		 * smaller tables with the same size trace matrices
		 * equal distribution of trace link
		 * A(2k) = AB(6k) = B(2k) = BC(6k) = C(1k) = CD(6k) = D(1k)
		 * B= 2k		 * 
		 *    = running time: + 12.8 sec (+40.89%)
		 *    - [AB = BC] = CD: [6.1] + 12.4 = 18.5 sec
		 *    - AB = [BC=CD]: 26.1 + [5.2] =  31.3 sec		 * 
		 */
		//	AaaBaaC7.xml cost: 5856 6410
		//AaaBaaC7(myAW);
		//	BaaCaaD7.xml cost: 4889 5453
		//BaaCaaD7(myAW);
		//	AaBaCaaD7.xml cost: 12072 12799
		//AaBaCaaD7(myAW);
		//	AaaBaCaD7.xml cost: 26846 25317
		//AaaBaCaD7(myAW);

		
		/* FAN OUT RATIO2
		 * smaller tables with the same size trace matrices
		 * equal distribution of trace link
		 * A(2k) = AB(6k) = B(1k) = BC(6k) = C(1k) = CD(6k) = D(1k)
		 * B=1k
		 * 	  = running time: + 0.8 sec (-6.04%)
		 *    - [AB = BC] = CD: [4.9] + 24.1 = 29.0 sec
		 *    - AB = [BC=CD]: 25.0 + [4.8] =  29.8 sec		
		 *    ==> findings: what really matters is a fan out ratio on the other side
		 */
		//	AaaBaaC9.xml cost:  4997 4808 4997 // avg. 4934; card 36k
		//AaaBaaC9(myAW); 
		//	BaaCaaD9.xml cost: 4845 4859 4693 // avg. 4799; card 36k
		//BaaCaaD9(myAW);
		//	AaBaCaaD9.xml cost: 25481 22888 23827 // avg. 24065; card 215k
		//AaBaCaaD9(myAW);
		//	AaaBaCaD9.xml cost: 24667 26351 23908 // avg. 24975; card 215k
		//AaaBaCaD9(myAW);
		
		/* FAN OUT RATIO3
		 * smaller tables with the same size trace matrices
		 * equal distribution of trace link
		 * A(2k) = AB(6k) = B(4k) = BC(6k) = C(1k) = CD(6k) = D(1k)
		 * B=1k
		 * 	  = running time: + 17.2 sec (-58.11%)
		 *    - [AB = BC] = CD: [5.3] + 7.1 = 12.4 sec
		 *    - AB = [BC=CD]: 24.4 + [5.2] =  29.6 sec		
		 *    ==> findings: what really matters is a fan out ratio on the other side
		 */
		//	AaaBaaC10.xml cost: 5334 5229 5235// avg. 5266;card: 9k
		//AaaBaaC10(myAW); 
		//	BaaCaaD10.xml cost: 5283 5340 5093 // avg. 5239; card: 36k
		//BaaCaaD10(myAW);
		//	AaBaCaaD10.xml cost: 7083 7230 6939// avg. 7084; card: 54k
		//AaBaCaaD10(myAW);
		//	AaaBaCaD10.xml cost: 23868 23536 25703// avg. 24369; card: 54k
		//AaaBaCaD10(myAW);
		
		/* FAN OUT RATIO 4
		 * smaller tables with the same size trace matrices
		 * equal distribution of trace link
		 * A(2k) = AB(6k) = B(2k) = BC(6k) = C(3k) = CD(6k) = D(1k)
		 * B= 2k		 * 
		 *    = running time: + 12.8 sec (-40.89%)
		 *    - [AB = BC] = CD: [6.1] + 12.4 = 18.5 sec
		 *    - AB = [BC=CD]: 26.1 + [5.2] =  31.3 sec		 * 
		 */
		//	AaaBaaC11.xml cost: 4724
		AaaBaaC11(myAW);
		//	BaaCaaD11.xml cost: 4688 5028 5199
		//BaaCaaD11(myAW);
		//	AaBaCaaD11.xml cost: 13032 12533 12499
		//AaBaCaaD11(myAW);
		//	AaaBaCaD11.xml cost: 10503 8869 8843
		//AaaBaCaD11(myAW);
		
		
		System.out.println("Done");
		
	}
	private static void AaBaCaaD11(QueryManager myAW){	
		File AaBaCaaD11 = new File("./results/AaBaCaaD11.xml"); 
		SQLString =
				"SELECT " + ABCD.CaaD11 + ".DID, " + ABCD.AaaBaaC11 + ".AID" + " " + //COUNT(*) " + // "  + 
				"FROM " + ABCD.CaaD11 + " " + 
				"INNER JOIN " + ABCD.AaaBaaC11 + " " + 
				"ON " + ABCD.AaaBaaC11 + ".CID = " + ABCD.CaaD11 + ".CID"; 
		System.out.println(SQLString);
		MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, AaBaCaaD11);
		//myAW.WriteCSV("./Data_ABCD/AaBaCaaD11.csv", SQLString);
		//myAW.QueryToXML(SQLString, AaBaCaaD11);
	}

	
	private static void AaaBaCaD11(QueryManager myAW){	
		File AaaBaCaD11 = new File("./results/AaaBaCaD11.xml"); 
		SQLString =
				"SELECT " + ABCD.AaaB11 + ".AID, " + ABCD.BaaCaaD11 + ".DID" + " " + //COUNT(*) " + // "  + 
				"FROM " + ABCD.AaaB11 + " " + 
				"INNER JOIN " + ABCD.BaaCaaD11 + " " + 
				"ON " + ABCD.BaaCaaD11 + ".BID = " + ABCD.AaaB11 + ".BID"; 
		System.out.println(SQLString);
		MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, AaaBaCaD11);
		//myAW.WriteCSV("./Data_ABCD/AaaBaCaD11.csv", SQLString);
		//myAW.QueryToXML(SQLString, AaaBaCaD11);
	}
	
	private static void AaaBaaC11(QueryManager myAW){	
		File AaaBaaC11 = new File("./results/AaaBaaC11.xml"); 
		SQLString =
				"SELECT " + ABCD.AaaB11 + ".AID, " + ABCD.BaaC11 + ".CID" + " " + //COUNT(*) " + // "  + 
				"FROM " + ABCD.AaaB11 + " " + 
				"INNER JOIN " + ABCD.BaaC11 + " " + 
				"ON " + ABCD.BaaC11 + ".BID = " + ABCD.AaaB11 + ".BID"; 
		System.out.println(SQLString);
		MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, AaaBaaC11);
		//myAW.WriteCSV("./Data_ABCD/AaaBaaC11.csv", SQLString);
		//myAW.QueryToXML(SQLString, AaaBaaC11);
	}
	
	private static void BaaCaaD11(QueryManager myAW){	
		File BaaCaaD11 = new File("./results/BaaCaaD11.xml"); 
		SQLString =
				"SELECT " + ABCD.BaaC11 + ".BID, " + ABCD.CaaD11 + ".DID" + " " + //COUNT(*) " + // "  + 
				"FROM " + ABCD.BaaC11 + " " + 
				"INNER JOIN " + ABCD.CaaD11 + " " + 
				"ON " + ABCD.CaaD11 + ".CID = " + ABCD.BaaC11 + ".CID"; 
		System.out.println(SQLString);
		MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, BaaCaaD11);
		//myAW.WriteCSV("./Data_ABCD/BaaCaaD11.csv", SQLString);
		//myAW.QueryToXML(SQLString, BaaCaaD11);
	}
	
	private static void AaBaCaaD10(QueryManager myAW){	
		File AaBaCaaD10 = new File("./results/AaBaCaaD10.xml"); 
		SQLString =
				"SELECT " + ABCD.CaaD10 + ".DID, " + ABCD.AaaBaaC10 + ".AID" + " " + //COUNT(*) " + // "  + 
				"FROM " + ABCD.CaaD10 + " " + 
				"INNER JOIN " + ABCD.AaaBaaC10 + " " + 
				"ON " + ABCD.AaaBaaC10 + ".CID = " + ABCD.CaaD10 + ".CID"; 
		System.out.println(SQLString);
		MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, AaBaCaaD10);
		//myAW.WriteCSV("./Data_ABCD/AaBaCaaD10.csv", SQLString);
		//myAW.QueryToXML(SQLString, AaBaCaaD10);
	}

	
	private static void AaaBaCaD10(QueryManager myAW){	
		File AaaBaCaD10 = new File("./results/AaaBaCaD10.xml"); 
		SQLString =
				"SELECT " + ABCD.AaaB10 + ".AID, " + ABCD.BaaCaaD10 + ".DID" + " " + //COUNT(*) " + // "  + 
				"FROM " + ABCD.AaaB10 + " " + 
				"INNER JOIN " + ABCD.BaaCaaD10 + " " + 
				"ON " + ABCD.BaaCaaD10 + ".BID = " + ABCD.AaaB10 + ".BID"; 
		System.out.println(SQLString);
		MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, AaaBaCaD10);
		//myAW.WriteCSV("./Data_ABCD/AaaBaCaD10.csv", SQLString);
		//myAW.QueryToXML(SQLString, AaaBaCaD10);
	}
	
	private static void AaaBaaC10(QueryManager myAW){	
		File AaaBaaC10 = new File("./results/AaaBaaC10.xml"); 
		SQLString =
				"SELECT " + ABCD.AaaB10 + ".AID, " + ABCD.BaaC10 + ".CID" + " " + //COUNT(*) " + // "  + 
				"FROM " + ABCD.AaaB10 + " " + 
				"INNER JOIN " + ABCD.BaaC10 + " " + 
				"ON " + ABCD.BaaC10 + ".BID = " + ABCD.AaaB10 + ".BID"; 
		System.out.println(SQLString);
		MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, AaaBaaC10);
		//myAW.WriteCSV("./Data_ABCD/AaaBaaC10.csv", SQLString);
		//myAW.QueryToXML(SQLString, AaaBaaC10);
	}
	
	private static void BaaCaaD10(QueryManager myAW){	
		File BaaCaaD10 = new File("./results/BaaCaaD10.xml"); 
		SQLString =
				"SELECT " + ABCD.BaaC10 + ".BID, " + ABCD.CaaD10 + ".DID" + " " + //COUNT(*) " + // "  + 
				"FROM " + ABCD.BaaC10 + " " + 
				"INNER JOIN " + ABCD.CaaD10 + " " + 
				"ON " + ABCD.CaaD10 + ".CID = " + ABCD.BaaC10 + ".CID"; 
		System.out.println(SQLString);
		MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, BaaCaaD10);
		//myAW.WriteCSV("./Data_ABCD/BaaCaaD10.csv", SQLString);
		//myAW.QueryToXML(SQLString, BaaCaaD10);
	}
	
	private static void AaBaCaaD9(QueryManager myAW){	
		File AaBaCaaD9 = new File("./results/AaBaCaaD9.xml"); 
		SQLString =
				"SELECT " + ABCD.CaaD9 + ".DID, " + ABCD.AaaBaaC9 + ".AID" + " " + //COUNT(*) " + // "  + 
				"FROM " + ABCD.CaaD9 + " " + 
				"INNER JOIN " + ABCD.AaaBaaC9 + " " + 
				"ON " + ABCD.AaaBaaC9 + ".CID = " + ABCD.CaaD9 + ".CID"; 
		System.out.println(SQLString);
		MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, AaBaCaaD9);
		//myAW.WriteCSV("./Data_ABCD/AaBaCaaD9.csv", SQLString);
		//myAW.QueryToXML(SQLString, AaBaCaaD9);
	}

	
	private static void AaaBaCaD9(QueryManager myAW){	
		File AaaBaCaD9 = new File("./results/AaaBaCaD9.xml"); 
		SQLString =
				"SELECT " + ABCD.AaaB9 + ".AID, " + ABCD.BaaCaaD9 + ".DID" + " " + //COUNT(*) " + // "  + 
				"FROM " + ABCD.AaaB9 + " " + 
				"INNER JOIN " + ABCD.BaaCaaD9 + " " + 
				"ON " + ABCD.BaaCaaD9 + ".BID = " + ABCD.AaaB9 + ".BID"; 
		System.out.println(SQLString);
		MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, AaaBaCaD9);
		//myAW.WriteCSV("./Data_ABCD/AaaBaCaD9.csv", SQLString);
		//myAW.QueryToXML(SQLString, AaaBaCaD9);
	}
	
	private static void AaaBaaC9(QueryManager myAW){	
		File AaaBaaC9 = new File("./results/AaaBaaC9.xml"); 
		SQLString =
				"SELECT " + ABCD.AaaB9 + ".AID, " + ABCD.BaaC9 + ".CID" + " " + //COUNT(*) " + // "  + 
				"FROM " + ABCD.AaaB9 + " " + 
				"INNER JOIN " + ABCD.BaaC9 + " " + 
				"ON " + ABCD.BaaC9 + ".BID = " + ABCD.AaaB9 + ".BID"; 
		System.out.println(SQLString);
		MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, AaaBaaC9);
		//myAW.WriteCSV("./Data_ABCD/AaaBaaC9.csv", SQLString);
		//myAW.QueryToXML(SQLString, AaaBaaC9);
	}
	
	private static void BaaCaaD9(QueryManager myAW){	
		File BaaCaaD9 = new File("./results/BaaCaaD9.xml"); 
		SQLString =
				"SELECT " + ABCD.BaaC9 + ".BID, " + ABCD.CaaD9 + ".DID" + " " + //COUNT(*) " + // "  + 
				"FROM " + ABCD.BaaC9 + " " + 
				"INNER JOIN " + ABCD.CaaD9 + " " + 
				"ON " + ABCD.CaaD9 + ".CID = " + ABCD.BaaC9 + ".CID"; 
		System.out.println(SQLString);
		MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, BaaCaaD9);
		//myAW.WriteCSV("./Data_ABCD/BaaCaaD9.csv", SQLString);
		//myAW.QueryToXML(SQLString, BaaCaaD9);
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
}
