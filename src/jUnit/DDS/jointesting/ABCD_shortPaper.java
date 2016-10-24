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
		 *    - [AB = BC] = CD: [6.1] + 12.4 = 18.5 sec <*****
		 *    - AB = [BC=CD]: 26.1 + [5.2] =  31.3 sec		 
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
		 *    - [AB = BC] = CD: [4.9] + 24.1 = 29.0 sec <*****
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
		 *    - [AB = BC] = CD: [5.3] + 7.1 = 12.4 sec <*****
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
		 *    = running time: + 4.1 sec (-22.16%)
		 *    - [AB = BC] = CD: [5.2] + 12.7 = 18.5 sec 
		 *    - AB = [BC=CD]: 9.4 + [5.0] =  14.4 sec	<***** 
		 */
		//	AaaBaaC11.xml cost: 4724 5002 6001 // avg: 5242; card 18k
		//AaaBaaC11(myAW);
		//	BaaCaaD11.xml cost: 4688 5028 5199 // avg: 4972; card 12k
		//BaaCaaD11(myAW);
		//	AaBaCaaD11.xml cost: 13032 12533 12499// avg: 12688; card 36.5k
		//AaBaCaaD11(myAW);
		//	AaaBaCaD11.xml cost: 10503 8869 8843// avg: 9405; card 36.5k
		//AaaBaCaD11(myAW);
		
		/* FAN OUT RATIO 5
		 * smaller tables with the same size trace matrices
		 * equal distribution of trace link
		 * A(2k) = AB(6k) = B(2k) = BC(6k) = C(4k) = CD(6k) = D(1k)
		 * B= 2k		 * 
		 *    = running time: + 5.2 sec (-30.77%)
		 *    - [AB = BC] = CD: [4.7] + 12.2 = 16.9 sec
		 *    - AB = [BC=CD]: 7.3 + [4.4] =  11.7 sec	<***** 
		 */
		//	AaaBaaC12.xml cost: 4944 4697 4455// avg: 4699; card 18k
		//AaaBaaC12(myAW);
		//	BaaCaaD12.xml cost: 4494 4246 4545// avg: 4428; card 9k
		//BaaCaaD12(myAW);
		//	AaBaCaaD12.xml cost: 12414 12254 11894// avg: 12187; card 27k
		//AaBaCaaD12(myAW);
		//	AaaBaCaD12.xml cost: 7341 7422 7115// avg: 7293; card 27k
		//AaaBaCaD12(myAW);
		
		/* FAN OUT RATIO 6
		 * smaller tables with the same size trace matrices
		 * equal distribution of trace link
		 * A(2k) = AB(6k) = B(1k) = BC(6k) = C(3k) = CD(6k) = D(1k)
		 * B=1k
		 * 	  = running time: + 16.0 sec (-53.51%)
		 *    - [AB = BC] = CD: [5.0] + 24.9 = 29.9 sec 
		 *    - AB = [BC=CD]: 9.3 + [4.6] =  13.9 sec	<*****	
		 *    ==> findings: what really matters is a fan out ratio on the other side
		 */
		//	AaaBaaC13.xml cost: 4779 4741 5562 // avg. 5027 ; card 36k
		//AaaBaaC13(myAW); 
		//	BaaCaaD13.xml cost: 4030 4790 5008 // avg. 4609 ; card 12k
		//BaaCaaD13(myAW);
		//	AaBaCaaD13.xml cost: 24620 26477 23700 // avg. 24932; card 72k
		//AaBaCaaD13(myAW);
		//	AaaBaCaD13.xml cost: 9131 9588 9309// avg. 9343; card 72k
		//AaaBaCaD13(myAW);
		
		/* FAN OUT RATIO 7
		 * smaller tables with the same size trace matrices
		 * equal distribution of trace link
		 * A(2k) = AB(6k) = B(1k) = BC(6k) = C(4k) = CD(6k) = D(1k)
		 * B=1k
		 * 	  = running time: + 19.6 sec (-64.26%)
		 *    - [AB = BC] = CD: [5.0] + 25.5 = 30.5 sec 
		 *    - AB = [BC=CD]: 6.2 + [4.7] =  10.9 sec	<*****	
		 *    ==> findings: what really matters is a fan out ratio on the other side
		 */
		//	AaaBaaC14.xml cost: 4884 4720 5296// avg. 4967 ; card 36k
		//AaaBaaC14(myAW); 
		//	BaaCaaD14.xml cost: 4521 4579 4892// avg. 4664 ; card 9k
		//BaaCaaD14(myAW);
		//	AaBaCaaD14.xml cost: 25742 26014 24642// avg. 25466; card 54k
		//AaBaCaaD14(myAW);
		//	AaaBaCaD14.xml cost: 6757 4816 6919// avg. 6164; card 54k
		//AaaBaCaD14(myAW);
		
		/* FAN OUT RATIO8
		 * smaller tables with the same size trace matrices
		 * equal distribution of trace link
		 * A(2k) = AB(6k) = B(4k) = BC(6k) = C(3k) = CD(6k) = D(1k)
		 * B=1k
		 * 	  = running time: + 17.2 sec (-58.11%)
		 *    - [AB = BC] = CD: [5.3] + 7.1 = 12.4 sec <*****
		 *    - AB = [BC=CD]: 24.4 + [5.2] =  29.6 sec		
		 *    ==> findings: what really matters is a fan out ratio on the other side
		 */
		//	AaaBaaC15.xml cost: // avg. ;card: 9k
		//AaaBaaC15(myAW); 
		//	BaaCaaD15.xml cost: // avg. ; card: 36k
		//BaaCaaD15(myAW);
		//	AaBaCaaD15.xml cost:// avg. ; card: 54k
		//AaBaCaaD15(myAW);
		//	AaaBaCaD15.xml cost: // avg. ; card: 54k
		//AaaBaCaD15(myAW);
		
		/* 
		 * A(3k) = AB(6k) = B(5k) = BC(6k) = C(2k) = CD(6k) = D(2k)
		 * B=5k; C=2k
		 * 	  = running time: +  sec (%)
		 *    - [AB = BC] = CD: [] +  =  sec <*****
		 *    - AB = [BC=CD]:  + [] =   sec		
		 *    ==> findings: what really matters is a fan out ratio on the other side
		 */
		//	AaaBaaC16.xml cost: 5719 5572 // avg. ;card: 7141
		//AaaBaaC16(myAW); 
		//	BaaCaaD16.xml cost: 5538 4703// avg. ; card: 18k
		//BaaCaaD16(myAW);
		//	AaBaCaaD16.xml cost: 5891 5412 // avg. ; card: 54k
		/// AaBaCaaD16(myAW);
		//	AaaBaCaD16.xml cost: 13376 9617 13846 9666// avg. ; card: 54k
		//AaaBaCaD16(myAW);
		
		/* 
		 * A(3k) = AB(6k) = B(3k) = BC(1k) = C(2k) = CD(6k) = D(2k)
		 * B=5k; C=2k
		 * 	  = running time: +  sec (%)
		 *    - [AB = BC] = CD: [] +  =  sec <*****
		 *    - AB = [BC=CD]:  + [] =   sec		
		 *    ==> findings: what really matters is a fan out ratio on the other side
		 */
		//	AaaBaaC16.xml cost:  // avg. ;card: 
		//AaaBaaC16(myAW); 
		//	BaaCaaD16.xml cost: // avg. ; card: k
		//BaaCaaD16(myAW);
		//	AaBaCaaD16.xml cost:  // avg. ; card: k
		/// AaBaCaaD16(myAW);
		//	AaaBaCaD16.xml cost: // avg. ; card: k
		//AaaBaCaD16(myAW);

		/* 
		 * A(3k) = AB(6k) = B(3k) = BC(1k) = C(2k) = CD(6k) = D(2k)
		 * B=5k; C=2k
		 * 	  = running time: +  sec (%)
		 *    - [AB = BC] = CD: [] +  =  sec <*****
		 *    - AB = [BC=CD]:  + [] =   sec		
		 *    ==> findings: what really matters is a fan out ratio on the other side
		 */
		//	AaaBaaC17.xml cost: 884 788 723 // avg. 798 ;card: 2k 
		//AaaBaaC17(myAW); 
		//	BaaCaaD17.xml cost: 904  714 653 // avg. 757 ; card: 3k
		//BaaCaaD17(myAW);
		//	AaBaCaaD17.xml cost: 1222 2146 1206 2267// 1710 avg. ; card: 6k
		//AaBaCaaD17(myAW);
		//	AaaBaCaD17.xml cost: 1758 2013 2755// avg. 2175 ; card: 6k
		//AaaBaCaD17(myAW);
		
		/* 
		 * A(3k) = AB(6k) = B(3k) = BC(2k) = C(2k) = CD(6k) = D(2k)
		 * B=5k; C=2k
		 * 	  = running time: +  sec (%)
		 *    - [AB = BC] = CD: [] +  =  sec <*****
		 *    - AB = [BC=CD]:  + [] =   sec		
		 *    ==> findings: what really matters is a fan out ratio on the other side
		 */
		//	AaaBaaC18.xml cost: 2226 2081 2144// avg. ;card: 2k 
		//AaaBaaC18(myAW); 
		//	BaaCaaD18.xml cost: 1592 1236 1459// avg. ; card: 3k
		//BaaCaaD18(myAW);
		//	AaBaCaaD18.xml cost: 3071 4667 3365 // avg. ; card: 6k
		//AaBaCaaD18(myAW);
		//	AaaBaCaD18.xml cost: 6231 3279 4338// avg. ; card: 6k
		AaaBaCaD18(myAW);
		
		
		System.out.println("Done");
		
	}
	
	private static void AaBaCaaD18(QueryManager myAW){	
		File AaBaCaaD18 = new File("./results/AaBaCaaD18.xml"); 
		SQLString =
				"SELECT " + ABCD.CaaD18 + ".DID, " + ABCD.AaaBaaC18 + ".AID" + " " + //COUNT(*) " + // "  + 
				"FROM " + ABCD.CaaD18 + " " + 
				"INNER JOIN " + ABCD.AaaBaaC18 + " " + 
				"ON " + ABCD.AaaBaaC18 + ".CID = " + ABCD.CaaD18 + ".CID"; 
		System.out.println(SQLString);
		MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, AaBaCaaD18);
		//myAW.WriteCSV("./Data_ABCD/AaBaCaaD18.csv", SQLString);
		//myAW.QueryToXML(SQLString, AaBaCaaD18);
	}
	private static void AaaBaCaD18(QueryManager myAW){	
		File AaaBaCaD18 = new File("./results/AaaBaCaD18.xml"); 
		SQLString =
				"SELECT " + ABCD.AaaB18 + ".AID, " + ABCD.BaaCaaD18 + ".DID" + " " + //COUNT(*) " + // "  + 
				"FROM " + ABCD.AaaB18 + " " + 
				"INNER JOIN " + ABCD.BaaCaaD18 + " " + 
				"ON " + ABCD.BaaCaaD18 + ".BID = " + ABCD.AaaB18 + ".BID"; 
		System.out.println(SQLString);
		MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, AaaBaCaD18);
		//myAW.WriteCSV("./Data_ABCD/AaaBaCaD18.csv", SQLString);
		//myAW.QueryToXML(SQLString, AaaBaCaD18);
	}
	private static void AaaBaaC18(QueryManager myAW){	
		File AaaBaaC18 = new File("./results/AaaBaaC18.xml"); 
		SQLString =
				"SELECT " + ABCD.AaaB18 + ".AID, " + ABCD.BaaC18 + ".CID" + " " + //COUNT(*) " + // "  + 
				"FROM " + ABCD.AaaB18 + " " + 
				"INNER JOIN " + ABCD.BaaC18 + " " + 
				"ON " + ABCD.BaaC18 + ".BID = " + ABCD.AaaB18 + ".BID"; 
		System.out.println(SQLString);
		MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, AaaBaaC18);
		//myAW.WriteCSV("./Data_ABCD/AaaBaaC18.csv", SQLString);
		//myAW.QueryToXML(SQLString, AaaBaaC18);
	}
	private static void BaaCaaD18(QueryManager myAW){	
		File BaaCaaD18 = new File("./results/BaaCaaD18.xml"); 
		SQLString =
				"SELECT " + ABCD.BaaC18 + ".BID, " + ABCD.CaaD18 + ".DID" + " " + //COUNT(*) " + // "  + 
				"FROM " + ABCD.BaaC18 + " " + 
				"INNER JOIN " + ABCD.CaaD18 + " " + 
				"ON " + ABCD.CaaD18 + ".CID = " + ABCD.BaaC18 + ".CID"; 
		System.out.println(SQLString);
		MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, BaaCaaD18);
		//myAW.WriteCSV("./Data_ABCD/BaaCaaD18.csv", SQLString);
		//myAW.QueryToXML(SQLString, BaaCaaD18);
	}
	
	private static void AaBaCaaD17(QueryManager myAW){	
		File AaBaCaaD17 = new File("./results/AaBaCaaD17.xml"); 
		SQLString =
				"SELECT " + ABCD.CaaD17 + ".DID, " + ABCD.AaaBaaC17 + ".AID" + " " + //COUNT(*) " + // "  + 
				"FROM " + ABCD.CaaD17 + " " + 
				"INNER JOIN " + ABCD.AaaBaaC17 + " " + 
				"ON " + ABCD.AaaBaaC17 + ".CID = " + ABCD.CaaD17 + ".CID"; 
		System.out.println(SQLString);
		MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, AaBaCaaD17);
		//myAW.WriteCSV("./Data_ABCD/AaBaCaaD17.csv", SQLString);
		//myAW.QueryToXML(SQLString, AaBaCaaD17);
	}
	private static void AaaBaCaD17(QueryManager myAW){	
		File AaaBaCaD17 = new File("./results/AaaBaCaD17.xml"); 
		SQLString =
				"SELECT " + ABCD.AaaB17 + ".AID, " + ABCD.BaaCaaD17 + ".DID" + " " + //COUNT(*) " + // "  + 
				"FROM " + ABCD.AaaB17 + " " + 
				"INNER JOIN " + ABCD.BaaCaaD17 + " " + 
				"ON " + ABCD.BaaCaaD17 + ".BID = " + ABCD.AaaB17 + ".BID"; 
		System.out.println(SQLString);
		MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, AaaBaCaD17);
		//myAW.WriteCSV("./Data_ABCD/AaaBaCaD17.csv", SQLString);
		//myAW.QueryToXML(SQLString, AaaBaCaD17);
	}
	private static void AaaBaaC17(QueryManager myAW){	
		File AaaBaaC17 = new File("./results/AaaBaaC17.xml"); 
		SQLString =
				"SELECT " + ABCD.AaaB17 + ".AID, " + ABCD.BaaC17 + ".CID" + " " + //COUNT(*) " + // "  + 
				"FROM " + ABCD.AaaB17 + " " + 
				"INNER JOIN " + ABCD.BaaC17 + " " + 
				"ON " + ABCD.BaaC17 + ".BID = " + ABCD.AaaB17 + ".BID"; 
		System.out.println(SQLString);
		MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, AaaBaaC17);
		//myAW.WriteCSV("./Data_ABCD/AaaBaaC17.csv", SQLString);
		//myAW.QueryToXML(SQLString, AaaBaaC17);
	}
	private static void BaaCaaD17(QueryManager myAW){	
		File BaaCaaD17 = new File("./results/BaaCaaD17.xml"); 
		SQLString =
				"SELECT " + ABCD.BaaC17 + ".BID, " + ABCD.CaaD17 + ".DID" + " " + //COUNT(*) " + // "  + 
				"FROM " + ABCD.BaaC17 + " " + 
				"INNER JOIN " + ABCD.CaaD17 + " " + 
				"ON " + ABCD.CaaD17 + ".CID = " + ABCD.BaaC17 + ".CID"; 
		System.out.println(SQLString);
		MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, BaaCaaD17);
		//myAW.WriteCSV("./Data_ABCD/BaaCaaD17.csv", SQLString);
		//myAW.QueryToXML(SQLString, BaaCaaD17);
	}

	
	private static void AaBaCaaD16(QueryManager myAW){	
		File AaBaCaaD16 = new File("./results/AaBaCaaD16.xml"); 
		SQLString =
				"SELECT " + ABCD.CaaD16 + ".DID, " + ABCD.AaaBaaC16 + ".AID" + " " + //COUNT(*) " + // "  + 
				"FROM " + ABCD.CaaD16 + " " + 
				"INNER JOIN " + ABCD.AaaBaaC16 + " " + 
				"ON " + ABCD.AaaBaaC16 + ".CID = " + ABCD.CaaD16 + ".CID"; 
		System.out.println(SQLString);
		MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, AaBaCaaD16);
		//myAW.WriteCSV("./Data_ABCD/AaBaCaaD16.csv", SQLString);
		//myAW.QueryToXML(SQLString, AaBaCaaD16);
	}
	private static void AaaBaCaD16(QueryManager myAW){	
		File AaaBaCaD16 = new File("./results/AaaBaCaD16.xml"); 
		SQLString =
				"SELECT " + ABCD.AaaB16 + ".AID, " + ABCD.BaaCaaD16 + ".DID" + " " + //COUNT(*) " + // "  + 
				"FROM " + ABCD.AaaB16 + " " + 
				"INNER JOIN " + ABCD.BaaCaaD16 + " " + 
				"ON " + ABCD.BaaCaaD16 + ".BID = " + ABCD.AaaB16 + ".BID"; 
		System.out.println(SQLString);
		MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, AaaBaCaD16);
		//myAW.WriteCSV("./Data_ABCD/AaaBaCaD16.csv", SQLString);
		//myAW.QueryToXML(SQLString, AaaBaCaD16);
	}
	private static void AaaBaaC16(QueryManager myAW){	
		File AaaBaaC16 = new File("./results/AaaBaaC16.xml"); 
		SQLString =
				"SELECT " + ABCD.AaaB16 + ".AID, " + ABCD.BaaC16 + ".CID" + " " + //COUNT(*) " + // "  + 
				"FROM " + ABCD.AaaB16 + " " + 
				"INNER JOIN " + ABCD.BaaC16 + " " + 
				"ON " + ABCD.BaaC16 + ".BID = " + ABCD.AaaB16 + ".BID"; 
		System.out.println(SQLString);
		MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, AaaBaaC16);
		//myAW.WriteCSV("./Data_ABCD/AaaBaaC16.csv", SQLString);
		//myAW.QueryToXML(SQLString, AaaBaaC16);
	}
	private static void BaaCaaD16(QueryManager myAW){	
		File BaaCaaD16 = new File("./results/BaaCaaD16.xml"); 
		SQLString =
				"SELECT " + ABCD.BaaC16 + ".BID, " + ABCD.CaaD16 + ".DID" + " " + //COUNT(*) " + // "  + 
				"FROM " + ABCD.BaaC16 + " " + 
				"INNER JOIN " + ABCD.CaaD16 + " " + 
				"ON " + ABCD.CaaD16 + ".CID = " + ABCD.BaaC16 + ".CID"; 
		System.out.println(SQLString);
		MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, BaaCaaD16);
		//myAW.WriteCSV("./Data_ABCD/BaaCaaD16.csv", SQLString);
		//myAW.QueryToXML(SQLString, BaaCaaD16);
	}

	
	private static void AaBaCaaD15(QueryManager myAW){	
		File AaBaCaaD15 = new File("./results/AaBaCaaD15.xml"); 
		SQLString =
				"SELECT " + ABCD.CaaD15 + ".DID, " + ABCD.AaaBaaC15 + ".AID" + " " + //COUNT(*) " + // "  + 
				"FROM " + ABCD.CaaD15 + " " + 
				"INNER JOIN " + ABCD.AaaBaaC15 + " " + 
				"ON " + ABCD.AaaBaaC15 + ".CID = " + ABCD.CaaD15 + ".CID"; 
		System.out.println(SQLString);
		MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, AaBaCaaD15);
		//myAW.WriteCSV("./Data_ABCD/AaBaCaaD15.csv", SQLString);
		//myAW.QueryToXML(SQLString, AaBaCaaD15);
	}
	private static void AaaBaCaD15(QueryManager myAW){	
		File AaaBaCaD15 = new File("./results/AaaBaCaD15.xml"); 
		SQLString =
				"SELECT " + ABCD.AaaB15 + ".AID, " + ABCD.BaaCaaD15 + ".DID" + " " + //COUNT(*) " + // "  + 
				"FROM " + ABCD.AaaB15 + " " + 
				"INNER JOIN " + ABCD.BaaCaaD15 + " " + 
				"ON " + ABCD.BaaCaaD15 + ".BID = " + ABCD.AaaB15 + ".BID"; 
		System.out.println(SQLString);
		MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, AaaBaCaD15);
		//myAW.WriteCSV("./Data_ABCD/AaaBaCaD15.csv", SQLString);
		//myAW.QueryToXML(SQLString, AaaBaCaD15);
	}
	private static void AaaBaaC15(QueryManager myAW){	
		File AaaBaaC15 = new File("./results/AaaBaaC15.xml"); 
		SQLString =
				"SELECT " + ABCD.AaaB15 + ".AID, " + ABCD.BaaC15 + ".CID" + " " + //COUNT(*) " + // "  + 
				"FROM " + ABCD.AaaB15 + " " + 
				"INNER JOIN " + ABCD.BaaC15 + " " + 
				"ON " + ABCD.BaaC15 + ".BID = " + ABCD.AaaB15 + ".BID"; 
		System.out.println(SQLString);
		MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, AaaBaaC15);
		//myAW.WriteCSV("./Data_ABCD/AaaBaaC15.csv", SQLString);
		//myAW.QueryToXML(SQLString, AaaBaaC15);
	}
	private static void BaaCaaD15(QueryManager myAW){	
		File BaaCaaD15 = new File("./results/BaaCaaD15.xml"); 
		SQLString =
				"SELECT " + ABCD.BaaC15 + ".BID, " + ABCD.CaaD15 + ".DID" + " " + //COUNT(*) " + // "  + 
				"FROM " + ABCD.BaaC15 + " " + 
				"INNER JOIN " + ABCD.CaaD15 + " " + 
				"ON " + ABCD.CaaD15 + ".CID = " + ABCD.BaaC15 + ".CID"; 
		System.out.println(SQLString);
		MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, BaaCaaD15);
		//myAW.WriteCSV("./Data_ABCD/BaaCaaD15.csv", SQLString);
		//myAW.QueryToXML(SQLString, BaaCaaD15);
	}
	
	private static void AaBaCaaD14(QueryManager myAW){	
		File AaBaCaaD14 = new File("./results/AaBaCaaD14.xml"); 
		SQLString =
				"SELECT " + ABCD.CaaD14 + ".DID, " + ABCD.AaaBaaC14 + ".AID" + " " + //COUNT(*) " + // "  + 
				"FROM " + ABCD.CaaD14 + " " + 
				"INNER JOIN " + ABCD.AaaBaaC14 + " " + 
				"ON " + ABCD.AaaBaaC14 + ".CID = " + ABCD.CaaD14 + ".CID"; 
		System.out.println(SQLString);
		MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, AaBaCaaD14);
		//myAW.WriteCSV("./Data_ABCD/AaBaCaaD14.csv", SQLString);
		//myAW.QueryToXML(SQLString, AaBaCaaD14);
	}
	private static void AaaBaCaD14(QueryManager myAW){	
		File AaaBaCaD14 = new File("./results/AaaBaCaD14.xml"); 
		SQLString =
				"SELECT " + ABCD.AaaB14 + ".AID, " + ABCD.BaaCaaD14 + ".DID" + " " + //COUNT(*) " + // "  + 
				"FROM " + ABCD.AaaB14 + " " + 
				"INNER JOIN " + ABCD.BaaCaaD14 + " " + 
				"ON " + ABCD.BaaCaaD14 + ".BID = " + ABCD.AaaB14 + ".BID"; 
		System.out.println(SQLString);
		MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, AaaBaCaD14);
		//myAW.WriteCSV("./Data_ABCD/AaaBaCaD14.csv", SQLString);
		//myAW.QueryToXML(SQLString, AaaBaCaD14);
	}
	private static void AaaBaaC14(QueryManager myAW){	
		File AaaBaaC14 = new File("./results/AaaBaaC14.xml"); 
		SQLString =
				"SELECT " + ABCD.AaaB14 + ".AID, " + ABCD.BaaC14 + ".CID" + " " + //COUNT(*) " + // "  + 
				"FROM " + ABCD.AaaB14 + " " + 
				"INNER JOIN " + ABCD.BaaC14 + " " + 
				"ON " + ABCD.BaaC14 + ".BID = " + ABCD.AaaB14 + ".BID"; 
		System.out.println(SQLString);
		MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, AaaBaaC14);
		//myAW.WriteCSV("./Data_ABCD/AaaBaaC14.csv", SQLString);
		//myAW.QueryToXML(SQLString, AaaBaaC14);
	}
	private static void BaaCaaD14(QueryManager myAW){	
		File BaaCaaD14 = new File("./results/BaaCaaD14.xml"); 
		SQLString =
				"SELECT " + ABCD.BaaC14 + ".BID, " + ABCD.CaaD14 + ".DID" + " " + //COUNT(*) " + // "  + 
				"FROM " + ABCD.BaaC14 + " " + 
				"INNER JOIN " + ABCD.CaaD14 + " " + 
				"ON " + ABCD.CaaD14 + ".CID = " + ABCD.BaaC14 + ".CID"; 
		System.out.println(SQLString);
		MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, BaaCaaD14);
		//myAW.WriteCSV("./Data_ABCD/BaaCaaD14.csv", SQLString);
		//myAW.QueryToXML(SQLString, BaaCaaD14);
	}
	
	
	private static void AaBaCaaD13(QueryManager myAW){	
		File AaBaCaaD13 = new File("./results/AaBaCaaD13.xml"); 
		SQLString =
				"SELECT " + ABCD.CaaD13 + ".DID, " + ABCD.AaaBaaC13 + ".AID" + " " + //COUNT(*) " + // "  + 
				"FROM " + ABCD.CaaD13 + " " + 
				"INNER JOIN " + ABCD.AaaBaaC13 + " " + 
				"ON " + ABCD.AaaBaaC13 + ".CID = " + ABCD.CaaD13 + ".CID"; 
		System.out.println(SQLString);
		MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, AaBaCaaD13);
		//myAW.WriteCSV("./Data_ABCD/AaBaCaaD13.csv", SQLString);
		//myAW.QueryToXML(SQLString, AaBaCaaD13);
	}
	private static void AaaBaCaD13(QueryManager myAW){	
		File AaaBaCaD13 = new File("./results/AaaBaCaD13.xml"); 
		SQLString =
				"SELECT " + ABCD.AaaB13 + ".AID, " + ABCD.BaaCaaD13 + ".DID" + " " + //COUNT(*) " + // "  + 
				"FROM " + ABCD.AaaB13 + " " + 
				"INNER JOIN " + ABCD.BaaCaaD13 + " " + 
				"ON " + ABCD.BaaCaaD13 + ".BID = " + ABCD.AaaB13 + ".BID"; 
		System.out.println(SQLString);
		MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, AaaBaCaD13);
		//myAW.WriteCSV("./Data_ABCD/AaaBaCaD13.csv", SQLString);
		//myAW.QueryToXML(SQLString, AaaBaCaD13);
	}
	private static void AaaBaaC13(QueryManager myAW){	
		File AaaBaaC13 = new File("./results/AaaBaaC13.xml"); 
		SQLString =
				"SELECT " + ABCD.AaaB13 + ".AID, " + ABCD.BaaC13 + ".CID" + " " + //COUNT(*) " + // "  + 
				"FROM " + ABCD.AaaB13 + " " + 
				"INNER JOIN " + ABCD.BaaC13 + " " + 
				"ON " + ABCD.BaaC13 + ".BID = " + ABCD.AaaB13 + ".BID"; 
		System.out.println(SQLString);
		MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, AaaBaaC13);
		//myAW.WriteCSV("./Data_ABCD/AaaBaaC13.csv", SQLString);
		//myAW.QueryToXML(SQLString, AaaBaaC13);
	}
	private static void BaaCaaD13(QueryManager myAW){	
		File BaaCaaD13 = new File("./results/BaaCaaD13.xml"); 
		SQLString =
				"SELECT " + ABCD.BaaC13 + ".BID, " + ABCD.CaaD13 + ".DID" + " " + //COUNT(*) " + // "  + 
				"FROM " + ABCD.BaaC13 + " " + 
				"INNER JOIN " + ABCD.CaaD13 + " " + 
				"ON " + ABCD.CaaD13 + ".CID = " + ABCD.BaaC13 + ".CID"; 
		System.out.println(SQLString);
		MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, BaaCaaD13);
		//myAW.WriteCSV("./Data_ABCD/BaaCaaD13.csv", SQLString);
		//myAW.QueryToXML(SQLString, BaaCaaD13);
	}
	
	private static void AaBaCaaD12(QueryManager myAW){	
		File AaBaCaaD12 = new File("./results/AaBaCaaD12.xml"); 
		SQLString =
				"SELECT " + ABCD.CaaD12 + ".DID, " + ABCD.AaaBaaC12 + ".AID" + " " + //COUNT(*) " + // "  + 
				"FROM " + ABCD.CaaD12 + " " + 
				"INNER JOIN " + ABCD.AaaBaaC12 + " " + 
				"ON " + ABCD.AaaBaaC12 + ".CID = " + ABCD.CaaD12 + ".CID"; 
		System.out.println(SQLString);
		MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, AaBaCaaD12);
		//myAW.WriteCSV("./Data_ABCD/AaBaCaaD12.csv", SQLString);
		//myAW.QueryToXML(SQLString, AaBaCaaD12);
	}
	private static void AaaBaCaD12(QueryManager myAW){	
		File AaaBaCaD12 = new File("./results/AaaBaCaD11.xml"); 
		SQLString =
				"SELECT " + ABCD.AaaB12 + ".AID, " + ABCD.BaaCaaD12 + ".DID" + " " + //COUNT(*) " + // "  + 
				"FROM " + ABCD.AaaB12 + " " + 
				"INNER JOIN " + ABCD.BaaCaaD12 + " " + 
				"ON " + ABCD.BaaCaaD12 + ".BID = " + ABCD.AaaB12 + ".BID"; 
		System.out.println(SQLString);
		MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, AaaBaCaD12);
		//myAW.WriteCSV("./Data_ABCD/AaaBaCaD12.csv", SQLString);
		//myAW.QueryToXML(SQLString, AaaBaCaD12);
	}
	private static void AaaBaaC12(QueryManager myAW){	
		File AaaBaaC12 = new File("./results/AaaBaaC12.xml"); 
		SQLString =
				"SELECT " + ABCD.AaaB12 + ".AID, " + ABCD.BaaC12 + ".CID" + " " + //COUNT(*) " + // "  + 
				"FROM " + ABCD.AaaB12 + " " + 
				"INNER JOIN " + ABCD.BaaC12 + " " + 
				"ON " + ABCD.BaaC12 + ".BID = " + ABCD.AaaB12 + ".BID"; 
		System.out.println(SQLString);
		MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, AaaBaaC12);
		//myAW.WriteCSV("./Data_ABCD/AaaBaaC12.csv", SQLString);
		//myAW.QueryToXML(SQLString, AaaBaaC12);
	}
	private static void BaaCaaD12(QueryManager myAW){	
		File BaaCaaD12 = new File("./results/BaaCaaD12.xml"); 
		SQLString =
				"SELECT " + ABCD.BaaC12 + ".BID, " + ABCD.CaaD12 + ".DID" + " " + //COUNT(*) " + // "  + 
				"FROM " + ABCD.BaaC12 + " " + 
				"INNER JOIN " + ABCD.CaaD12 + " " + 
				"ON " + ABCD.CaaD12 + ".CID = " + ABCD.BaaC12 + ".CID"; 
		System.out.println(SQLString);
		MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, BaaCaaD12);
		//myAW.WriteCSV("./Data_ABCD/BaaCaaD12.csv", SQLString);
		//myAW.QueryToXML(SQLString, BaaCaaD12);
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
