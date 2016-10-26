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
		
		//CreateTablesInMemoryABCD.createTablesInMemoryABCD5(myAW);
		//CreateTablesInMemoryABCD.registerTMABCD5(myAW);

		CreateTablesInMemoryABCD.createTablesInMemoryABCD6(myAW);
		//CreateTablesInMemoryABCD.registerTMABCD6(myAW);
		
		CreateTablesInMemoryABCD.createTablesInMemoryABCD7(myAW);
		//CreateTablesInMemoryABCD.registerTMABCD7(myAW);
		
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
		//	AaaBaaC18.xml cost: 2226 2081 2144// avg.2150 ;card: 2k 
		//AaaBaaC18(myAW); 
		//	BaaCaaD18.xml cost: 1592 1236 1459// avg.1429 ; card: 3k
		//BaaCaaD18(myAW);
		//	AaBaCaaD18.xml cost: 3071 4667 3365 // avg.3701 ; card: 6k
		//AaBaCaaD18(myAW);
		//	AaaBaCaD18.xml cost: 4987 3279 4338// avg.4201 ; card: 6k
		//AaaBaCaD18(myAW);
		
		/* 
		 * A(3k) = AB(6k) = B(3k) = BC(3k) = C(2k) = CD(6k) = D(2k)
		 * B=5k; C=2k
		 * 	  = running time: +  sec (%)
		 *    - [AB = BC] = CD: [] +  =  sec <*****
		 *    - AB = [BC=CD]:  + [] =   sec		
		 *    ==> findings: what really matters is a fan out ratio on the other side
		 */
		//	AaaBaaC19.xml cost: 2604 2385 // avg.2495 ;card: 2k 
		//AaaBaaC19(myAW); 
		//	BaaCaaD19.xml cost: 3041 3013// avg.3027 ; card: 3k
		//BaaCaaD19(myAW);
		//	AaBaCaaD19.xml cost: 5893 4530 // avg.5112 ; card: 6k
		//AaBaCaaD19(myAW);
		//	AaaBaCaD19.xml cost: 6341 6539// avg.6440 ; card: 6k
		//AaaBaCaD19(myAW);
		
		/* 
		 * A(3k) = AB(6k) = B(3k) = BC(4k) = C(2k) = CD(6k) = D(2k)
		 * B=5k; C=2k
		 * 	  = running time: +  sec (%)
		 *    - [AB = BC] = CD: [] +  =  sec <*****
		 *    - AB = [BC=CD]:  + [] =   sec		
		 *    ==> findings: what really matters is a fan out ratio on the other side
		 */
		//	AaaBaaC20.xml cost: 3540 3510 2512// avg.3187 ;card: 2k 
		//AaaBaaC20(myAW); 
		//	BaaCaaD20.xml cost: 3200 3222 2731// avg.3051 ; card: 3k
		//BaaCaaD20(myAW);
		//	AaBaCaaD20.xml cost: 4679 6106 7017// avg.5934 ; card: 6k
		//AaBaCaaD20(myAW);
		//	AaaBaCaD20.xml cost: 7578 9231 7091// avg.7967 ; card: 6k
		//AaaBaCaD20(myAW);
		
		/* 
		 * A(3k) = AB(6k) = B(3k) = BC(5k) = C(2k) = CD(6k) = D(2k)
		 * B=5k; C=2k
		 * 	  = running time: +  sec (%)
		 *    - [AB = BC] = CD: [] +  =  sec <*****
		 *    - AB = [BC=CD]:  + [] =   sec		
		 *    ==> findings: what really matters is a fan out ratio on the other side
		 */
		//	AaaBaaC21.xml cost: 2614 3105// avg.2860 ;card: 8k 
		//AaaBaaC21(myAW); 
		//	BaaCaaD21.xml cost: 3575 2629// avg.3102 ; card: 12k
		//BaaCaaD21(myAW);
		//	AaBaCaaD21.xml cost: 8948 6892// avg.7920 ; card: 24k
		//AaBaCaaD21(myAW);
		//	AaaBaCaD21.xml cost: 8645 12098// avg.10371 ; card: 24k
		//AaaBaCaD21(myAW);
		
		/* 
		 * A(3k) = AB(6k) = B(3k) = BC(6k) = C(2k) = CD(6k) = D(2k)
		 * B=5k; C=2k
		 * 	  = running time: +  sec (%)
		 *    - [AB = BC] = CD: [] +  =  sec <*****
		 *    - AB = [BC=CD]:  + [] =   sec		
		 *    ==> findings: what really matters is a fan out ratio on the other side
		 */
		//	AaaBaaC22.xml cost: 4027 4884// avg.4456 ;card: 8k 
		//AaaBaaC22(myAW); 
		//	BaaCaaD22.xml cost: 4353 4573 // avg.4463 ; card: 12k
		//BaaCaaD22(myAW);
		//	AaBaCaaD22.xml cost: 8512 9060// avg.8786 ; card: 24k
		//AaBaCaaD22(myAW);
		//	AaaBaCaD22.xml cost: 13712 11497 // avg.12605 ; card: 24k
		//AaaBaCaD22(myAW);
		
		
		/* 
		 * A(3k) = AB(6k) = B(3k) = BC(6k) = C(1.5k) = CD(1k) = D(1.5k)
		 * B=5k; C=2k
		 * 	  = running time: +  sec (%)
		 *    - [AB = BC] = CD: [] +  =  sec <*****
		 *    - AB = [BC=CD]:  + [] =   sec		
		 *    ==> findings: what really matters is a fan out ratio on the other side
		 */
		//	AaaBaaC23.xml cost: 5745 4948// avg.5347 ;card: 12k 
		//AaaBaaC23(myAW); 
		//	BaaCaaD23.xml cost: 1530 1358// avg.1444 ; card: 4k
		//BaaCaaD23(myAW);
		//	AaBaCaaD23.xml cost: 1605 2431// avg.2018 ; card: 8k
		//AaBaCaaD23(myAW);
		//	AaaBaCaD23.xml cost: 3852 4215// avg.4034 ; card: 8k
		//AaaBaCaD23(myAW);
		
		/* 
		 * A(3k) = AB(6k) = B(3k) = BC(6k) = C(1.5k) = CD(2k) = D(1.5k)
		 * B=5k; C=2k
		 * 	  = running time: +  sec (%)
		 *    - [AB = BC] = CD: [] +  =  sec <*****
		 *    - AB = [BC=CD]:  + [] =   sec		
		 *    ==> findings: what really matters is a fan out ratio on the other side
		 */
		//	AaaBaaC24.xml cost: 3536 3889// avg.3713 ;card: 12k 
		//AaaBaaC24(myAW); 
		//	BaaCaaD24.xml cost: 1948 2270 // avg.2109	; card: 8k
		//BaaCaaD24(myAW);
		//	AaBaCaaD24.xml cost: 3704 3246// avg.3475 ; card: 16k
		//AaBaCaaD24(myAW);
		//	AaaBaCaD24.xml cost: 7039 6379// avg.6709 ; card: 16k
		//AaaBaCaD24(myAW);

		
		/* 
		 * A(3k) = AB(6k) = B(3k) = BC(6k) = C(1.5k) = CD(3k) = D(1.5k)
		 * B=5k; C=2k
		 * 	  = running time: +  sec (%)
		 *    - [AB = BC] = CD: [] +  =  sec <*****
		 *    - AB = [BC=CD]:  + [] =   sec		
		 *    ==> findings: what really matters is a fan out ratio on the other side
		 */
		//	AaaBaaC25.xml cost: 4801 5206// avg.5004 ;card: 12k 
		//AaaBaaC25(myAW); 
		//	BaaCaaD25.xml cost: 2627 3942 // avg.3285	; card: 12k
		//BaaCaaD25(myAW);
		//	AaBaCaaD25.xml cost: 5555 5881// avg.5718 ; card: 24k
		//AaBaCaaD25(myAW);
		//	AaaBaCaD25.xml cost: 8159 8955// avg.8557; card: 24k
		//AaaBaCaD25(myAW);		
		
		/* 
		 * A(3k) = AB(6k) = B(3k) = BC(6k) = C(1.5k) = CD(4k) = D(1.5k)
		 * B=5k; C=2k
		 * 	  = running time: +  sec (%)
		 *    - [AB = BC] = CD: [] +  =  sec <*****
		 *    - AB = [BC=CD]:  + [] =   sec		
		 *    ==> findings: what really matters is a fan out ratio on the other side
		 */
		//	AaaBaaC26.xml cost: 4483 5153// avg.4818 ;card: 12k 
		//AaaBaaC26(myAW); 
		//	BaaCaaD26.xml cost: 3628 4179 // avg.3904	; card: 16k
		//BaaCaaD26(myAW);
		//	AaBaCaaD26.xml cost: 5964 6704// avg.6334 ; card: 32k
		//AaBaCaaD26(myAW);
		//	AaaBaCaD26.xml cost: 9083 8896// avg.8990 ; card: 32k (proportionally)
		//AaaBaCaD26(myAW);		

		/* 
		 * A(3k) = AB(6k) = B(3k) = BC(6k) = C(1.5k) = CD(5k) = D(1.5k)
		 * B=5k; C=2k
		 * 	  = running time: +  sec (%)
		 *    - [AB = BC] = CD: [] +  =  sec <*****
		 *    - AB = [BC=CD]:  + [] =   sec		
		 *    ==> findings: what really matters is a fan out ratio on the other side
		 */
		//	AaaBaaC27.xml cost: 4811 5239// avg.5025 ;card: 12k 
		//AaaBaaC27(myAW); 
		//	BaaCaaD27.xml cost: 4210 5786 // avg.4998	; card: 20k
		//BaaCaaD27(myAW);
		//	AaBaCaaD27.xml cost: 7107 7043// avg.7075 ; card: 40k
		//AaBaCaaD27(myAW);
		//	AaaBaCaD27.xml cost: 10812 12840// avg.11826 ; card: 40k (proportionally)
		//AaaBaCaD27(myAW);	
		
		/* 
		 * A(3k) = AB(6k) = B(3k) = BC(6k) = C(1.5k) = CD(6k) = D(1.5k)
		 * B=5k; C=2k
		 * 	  = running time: +  sec (%)
		 *    - [AB = BC] = CD: [] +  =  sec <*****
		 *    - AB = [BC=CD]:  + [] =   sec		
		 *    ==> findings: what really matters is a fan out ratio on the other side
		 */
		//	AaaBaaC28.xml cost: 4972 4677// avg.4825 ;card: 12k 
		//AaaBaaC28(myAW); 
		//	BaaCaaD28.xml cost: 4298 5612 // avg.4955	; card: 24k
		//BaaCaaD28(myAW);
		//	AaBaCaaD28.xml cost: 9226 9020// avg.9123 ; card: 48k
		//AaBaCaaD28(myAW);
		//	AaaBaCaD28.xml cost: 17737 17187// avg.17462 ; card: 48k (proportionally)
		//AaaBaCaD28(myAW);	
		
		/* 
		 * Oracle test
		 * A(2k) = AB(6.6k) = B(4k) = BC(6k) = C(35k) = CD(5.1k) = D(1k)
		 * (AB-BC)-CD: 15sec; AB-(BC-CD): 22sec 
		 */
		
		//	AaaBaaC98.xml cost: 5672 5917
		//AaaBaaC98(myAW);
		//AaBaCaaD98.xml cost: 5286 7794 6280 4518
		AaBaCaaD98(myAW);
		
		//	BaaCaaD98.xml cost: 5008 5279
		//BaaCaaD98(myAW);
		//	AaaBaCaD98.xml cost:  6665 9510 8771 8952
		AaaBaCaD98(myAW);
		
		/* 
		 * Predicate test: CAUTHOR ='Jane'
		 * A(4k) = AB(6k) = B(4k) = BC(12k) = C(10k) = CD(11k) = D(4k)
		 * (AB-BC)-CD: ; AB-(BC-CD):
		 */
		
		//	AaaBaaC28.xml cost: 5672 5917
		//AaaBaaC98(myAW);
		//AaBaCaaD28.xml cost: 8926 11003 6245 9894
		//AaBaCaaD28(myAW);
		
		//	BaaCaaD98.xml cost: 5008 5279
		//BaaCaaD98(myAW);
		//	AaaBaCaD28.xml cost: 17803 17219
		//AaaBaCaD28(myAW);	
		

			
		System.out.println("Done");
		
	}

	private static void AaBaCaaD29(QueryManager myAW){	
		File AaBaCaaD29 = new File("./results/AaBaCaaD29.xml"); 
		SQLString =
				"SELECT " + ABCD.CaaD29 + ".DID, " + ABCD.AaaBaaC29 + ".AID" + " " + //COUNT(*) " + // "  + 
				"FROM " + ABCD.CaaD29 + " " + 
				"INNER JOIN " + ABCD.AaaBaaC29 + " " + 
				"ON " + ABCD.AaaBaaC29 + ".CID = " + ABCD.CaaD29 + ".CID"; 
		System.out.println(SQLString);
		MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, AaBaCaaD29);
		//myAW.WriteCSV("./Data_ABCD/AaBaCaaD29.csv", SQLString);
		//myAW.QueryToXML(SQLString, AaBaCaaD29);
	}
	private static void AaaBaCaD29(QueryManager myAW){	
		File AaaBaCaD29 = new File("./results/AaaBaCaD29.xml"); 
		SQLString =
				"SELECT " + ABCD.AaaB29 + ".AID, " + ABCD.BaaCaaD29 + ".DID" + " " + //COUNT(*) " + // "  + 
				"FROM " + ABCD.AaaB29 + " " + 
				"INNER JOIN " + ABCD.BaaCaaD29 + " " + 
				"ON " + ABCD.BaaCaaD29 + ".BID = " + ABCD.AaaB29 + ".BID"; 
		System.out.println(SQLString);
		MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, AaaBaCaD29);
		//myAW.WriteCSV("./Data_ABCD/AaaBaCaD29.csv", SQLString);
		//myAW.QueryToXML(SQLString, AaaBaCaD29);
	}
	private static void AaaBaaC29(QueryManager myAW){	
		File AaaBaaC29 = new File("./results/AaaBaaC29.xml"); 
		SQLString =
				"SELECT " + ABCD.AaaB29 + ".AID, " + ABCD.BaaC29 + ".CID" + " " + //COUNT(*) " + // "  + 
				"FROM " + ABCD.AaaB29 + " " + 
				"INNER JOIN " + ABCD.BaaC29 + " " + 
				"ON " + ABCD.BaaC29 + ".BID = " + ABCD.AaaB29 + ".BID"; 
		System.out.println(SQLString);
		MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, AaaBaaC29);
		//myAW.WriteCSV("./Data_ABCD/AaaBaaC29.csv", SQLString);
		//myAW.QueryToXML(SQLString, AaaBaaC29);
	}
	private static void BaaCaaD29(QueryManager myAW){	
		File BaaCaaD29 = new File("./results/BaaCaaD29.xml"); 
		SQLString =
				"SELECT " + ABCD.BaaC29+ ".BID, " + ABCD.CaaD29+ ".DID" + " " + //COUNT(*) " + // "  + 
				"FROM " + ABCD.BaaC29 + " " + 
				"INNER JOIN " + ABCD.CaaD29 + " " + 
				"ON " + ABCD.CaaD29 + ".CID = " + ABCD.BaaC29 + ".CID"; 
		System.out.println(SQLString);
		MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, BaaCaaD29);
		//myAW.WriteCSV("./Data_ABCD/BaaCaaD29.csv", SQLString);
		//myAW.QueryToXML(SQLString, BaaCaaD29);
	}
	
	private static void AaBaCaaD98(QueryManager myAW){	
		File AaBaCaaD98 = new File("./results/AaBaCaaD98.xml"); 
		SQLString =
				"SELECT " + ABCD.CaaD98 + ".DID, " + ABCD.AaaBaaC98 + ".AID" + " " + //COUNT(*) " + // "  + 
				"FROM " + ABCD.CaaD98 + " " + 
				"INNER JOIN " + ABCD.AaaBaaC98 + " " + 
				"ON " + ABCD.AaaBaaC98 + ".CID = " + ABCD.CaaD98 + ".CID"; 
		System.out.println(SQLString);
		MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, AaBaCaaD98);
		//myAW.WriteCSV("./Data_ABCD/AaBaCaaD98.csv", SQLString);
		//myAW.QueryToXML(SQLString, AaBaCaaD98);
	}
	private static void AaaBaCaD98(QueryManager myAW){	
		File AaaBaCaD98 = new File("./results/AaaBaCaD98.xml"); 
		SQLString =
				"SELECT " + ABCD.AaaB98 + ".AID, " + ABCD.BaaCaaD98 + ".DID" + " " + //COUNT(*) " + // "  + 
				"FROM " + ABCD.AaaB98 + " " + 
				"INNER JOIN " + ABCD.BaaCaaD98 + " " + 
				"ON " + ABCD.BaaCaaD98 + ".BID = " + ABCD.AaaB98 + ".BID"; 
		System.out.println(SQLString);
		MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, AaaBaCaD98);
		//myAW.WriteCSV("./Data_ABCD/AaaBaCaD98.csv", SQLString);
		//myAW.QueryToXML(SQLString, AaaBaCaD98);
	}
	private static void AaaBaaC98(QueryManager myAW){	
		File AaaBaaC98 = new File("./results/AaaBaaC98.xml"); 
		SQLString =
				"SELECT " + ABCD.AaaB98 + ".AID, " + ABCD.BaaC98 + ".CID" + " " + //COUNT(*) " + // "  + 
				"FROM " + ABCD.AaaB98 + " " + 
				"INNER JOIN " + ABCD.BaaC98 + " " + 
				"ON " + ABCD.BaaC98 + ".BID = " + ABCD.AaaB98 + ".BID"; 
		System.out.println(SQLString);
		MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, AaaBaaC98);
		//myAW.WriteCSV("./Data_ABCD/AaaBaaC98.csv", SQLString);
		//myAW.QueryToXML(SQLString, AaaBaaC98);
	}
	private static void BaaCaaD98(QueryManager myAW){	
		File BaaCaaD98 = new File("./results/BaaCaaD98.xml"); 
		SQLString =
				"SELECT " + ABCD.BaaC98+ ".BID, " + ABCD.CaaD98+ ".DID" + " " + //COUNT(*) " + // "  + 
				"FROM " + ABCD.BaaC98 + " " + 
				"INNER JOIN " + ABCD.CaaD98 + " " + 
				"ON " + ABCD.CaaD98 + ".CID = " + ABCD.BaaC98 + ".CID"; 
		System.out.println(SQLString);
		MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, BaaCaaD98);
		//myAW.WriteCSV("./Data_ABCD/BaaCaaD98.csv", SQLString);
		//myAW.QueryToXML(SQLString, BaaCaaD98);
	}
	
	private static void AaBaCaaD28(QueryManager myAW){	
		File AaBaCaaD28 = new File("./results/AaBaCaaD28.xml"); 
		SQLString =
				"SELECT " + ABCD.CaaD28 + ".DID, " + ABCD.AaaBaaC28 + ".AID" + " " + //COUNT(*) " + // "  + 
				"FROM " + ABCD.CaaD28 + " " + 
				"INNER JOIN " + ABCD.AaaBaaC28 + " " + 
				"ON " + ABCD.AaaBaaC28 + ".CID = " + ABCD.CaaD28 + ".CID"; 
		System.out.println(SQLString);
		MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, AaBaCaaD28);
		//myAW.WriteCSV("./Data_ABCD/AaBaCaaD28.csv", SQLString);
		//myAW.QueryToXML(SQLString, AaBaCaaD28);
	}
	private static void AaaBaCaD28(QueryManager myAW){	
		File AaaBaCaD28 = new File("./results/AaaBaCaD28.xml"); 
		SQLString =
				"SELECT " + ABCD.AaaB28 + ".AID, " + ABCD.BaaCaaD28 + ".DID" + " " + //COUNT(*) " + // "  + 
				"FROM " + ABCD.AaaB28 + " " + 
				"INNER JOIN " + ABCD.BaaCaaD28 + " " + 
				"ON " + ABCD.BaaCaaD28 + ".BID = " + ABCD.AaaB28 + ".BID"; 
		System.out.println(SQLString);
		MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, AaaBaCaD28);
		//myAW.WriteCSV("./Data_ABCD/AaaBaCaD28.csv", SQLString);
		//myAW.QueryToXML(SQLString, AaaBaCaD28);
	}
	private static void AaaBaaC28(QueryManager myAW){	
		File AaaBaaC28 = new File("./results/AaaBaaC28.xml"); 
		SQLString =
				"SELECT " + ABCD.AaaB28 + ".AID, " + ABCD.BaaC28 + ".CID" + " " + //COUNT(*) " + // "  + 
				"FROM " + ABCD.AaaB28 + " " + 
				"INNER JOIN " + ABCD.BaaC28 + " " + 
				"ON " + ABCD.BaaC28 + ".BID = " + ABCD.AaaB28 + ".BID"; 
		System.out.println(SQLString);
		MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, AaaBaaC28);
		//myAW.WriteCSV("./Data_ABCD/AaaBaaC28.csv", SQLString);
		//myAW.QueryToXML(SQLString, AaaBaaC28);
	}
	private static void BaaCaaD28(QueryManager myAW){	
		File BaaCaaD28 = new File("./results/BaaCaaD28.xml"); 
		SQLString =
				"SELECT " + ABCD.BaaC28+ ".BID, " + ABCD.CaaD28+ ".DID" + " " + //COUNT(*) " + // "  + 
				"FROM " + ABCD.BaaC28 + " " + 
				"INNER JOIN " + ABCD.CaaD28 + " " + 
				"ON " + ABCD.CaaD28 + ".CID = " + ABCD.BaaC28 + ".CID"; 
		System.out.println(SQLString);
		MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, BaaCaaD28);
		//myAW.WriteCSV("./Data_ABCD/BaaCaaD28.csv", SQLString);
		//myAW.QueryToXML(SQLString, BaaCaaD28);
	}
	
	private static void AaBaCaaD27(QueryManager myAW){	
		File AaBaCaaD27 = new File("./results/AaBaCaaD27.xml"); 
		SQLString =
				"SELECT " + ABCD.CaaD27 + ".DID, " + ABCD.AaaBaaC27 + ".AID" + " " + //COUNT(*) " + // "  + 
				"FROM " + ABCD.CaaD27 + " " + 
				"INNER JOIN " + ABCD.AaaBaaC27 + " " + 
				"ON " + ABCD.AaaBaaC27 + ".CID = " + ABCD.CaaD27 + ".CID"; 
		System.out.println(SQLString);
		MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, AaBaCaaD27);
		//myAW.WriteCSV("./Data_ABCD/AaBaCaaD27.csv", SQLString);
		//myAW.QueryToXML(SQLString, AaBaCaaD27);
	}
	private static void AaaBaCaD27(QueryManager myAW){	
		File AaaBaCaD27 = new File("./results/AaaBaCaD27.xml"); 
		SQLString =
				"SELECT " + ABCD.AaaB27 + ".AID, " + ABCD.BaaCaaD27 + ".DID" + " " + //COUNT(*) " + // "  + 
				"FROM " + ABCD.AaaB27 + " " + 
				"INNER JOIN " + ABCD.BaaCaaD27 + " " + 
				"ON " + ABCD.BaaCaaD27 + ".BID = " + ABCD.AaaB27 + ".BID"; 
		System.out.println(SQLString);
		MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, AaaBaCaD27);
		//myAW.WriteCSV("./Data_ABCD/AaaBaCaD27.csv", SQLString);
		//myAW.QueryToXML(SQLString, AaaBaCaD27);
	}
	private static void AaaBaaC27(QueryManager myAW){	
		File AaaBaaC27 = new File("./results/AaaBaaC27.xml"); 
		SQLString =
				"SELECT " + ABCD.AaaB27 + ".AID, " + ABCD.BaaC27 + ".CID" + " " + //COUNT(*) " + // "  + 
				"FROM " + ABCD.AaaB27 + " " + 
				"INNER JOIN " + ABCD.BaaC27 + " " + 
				"ON " + ABCD.BaaC27 + ".BID = " + ABCD.AaaB27 + ".BID"; 
		System.out.println(SQLString);
		MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, AaaBaaC27);
		//myAW.WriteCSV("./Data_ABCD/AaaBaaC27.csv", SQLString);
		//myAW.QueryToXML(SQLString, AaaBaaC27);
	}
	private static void BaaCaaD27(QueryManager myAW){	
		File BaaCaaD27 = new File("./results/BaaCaaD27.xml"); 
		SQLString =
				"SELECT " + ABCD.BaaC27+ ".BID, " + ABCD.CaaD27+ ".DID" + " " + //COUNT(*) " + // "  + 
				"FROM " + ABCD.BaaC27 + " " + 
				"INNER JOIN " + ABCD.CaaD27 + " " + 
				"ON " + ABCD.CaaD27 + ".CID = " + ABCD.BaaC27 + ".CID"; 
		System.out.println(SQLString);
		MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, BaaCaaD27);
		//myAW.WriteCSV("./Data_ABCD/BaaCaaD27.csv", SQLString);
		//myAW.QueryToXML(SQLString, BaaCaaD27);
	}	
	
	private static void AaBaCaaD26(QueryManager myAW){	
		File AaBaCaaD26 = new File("./results/AaBaCaaD26.xml"); 
		SQLString =
				"SELECT " + ABCD.CaaD26 + ".DID, " + ABCD.AaaBaaC26 + ".AID" + " " + //COUNT(*) " + // "  + 
				"FROM " + ABCD.CaaD26 + " " + 
				"INNER JOIN " + ABCD.AaaBaaC26 + " " + 
				"ON " + ABCD.AaaBaaC26 + ".CID = " + ABCD.CaaD26 + ".CID"; 
		System.out.println(SQLString);
		MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, AaBaCaaD26);
		//myAW.WriteCSV("./Data_ABCD/AaBaCaaD26.csv", SQLString);
		//myAW.QueryToXML(SQLString, AaBaCaaD26);
	}
	private static void AaaBaCaD26(QueryManager myAW){	
		File AaaBaCaD26 = new File("./results/AaaBaCaD26.xml"); 
		SQLString =
				"SELECT " + ABCD.AaaB26 + ".AID, " + ABCD.BaaCaaD26 + ".DID" + " " + //COUNT(*) " + // "  + 
				"FROM " + ABCD.AaaB26 + " " + 
				"INNER JOIN " + ABCD.BaaCaaD26 + " " + 
				"ON " + ABCD.BaaCaaD26 + ".BID = " + ABCD.AaaB26 + ".BID"; 
		System.out.println(SQLString);
		MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, AaaBaCaD26);
		//myAW.WriteCSV("./Data_ABCD/AaaBaCaD26.csv", SQLString);
		//myAW.QueryToXML(SQLString, AaaBaCaD26);
	}
	private static void AaaBaaC26(QueryManager myAW){	
		File AaaBaaC26 = new File("./results/AaaBaaC26.xml"); 
		SQLString =
				"SELECT " + ABCD.AaaB26 + ".AID, " + ABCD.BaaC26 + ".CID" + " " + //COUNT(*) " + // "  + 
				"FROM " + ABCD.AaaB26 + " " + 
				"INNER JOIN " + ABCD.BaaC26 + " " + 
				"ON " + ABCD.BaaC26 + ".BID = " + ABCD.AaaB26 + ".BID"; 
		System.out.println(SQLString);
		MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, AaaBaaC26);
		//myAW.WriteCSV("./Data_ABCD/AaaBaaC26.csv", SQLString);
		//myAW.QueryToXML(SQLString, AaaBaaC26);
	}
	private static void BaaCaaD26(QueryManager myAW){	
		File BaaCaaD26 = new File("./results/BaaCaaD26.xml"); 
		SQLString =
				"SELECT " + ABCD.BaaC26+ ".BID, " + ABCD.CaaD26+ ".DID" + " " + //COUNT(*) " + // "  + 
				"FROM " + ABCD.BaaC26 + " " + 
				"INNER JOIN " + ABCD.CaaD26 + " " + 
				"ON " + ABCD.CaaD26 + ".CID = " + ABCD.BaaC26 + ".CID"; 
		System.out.println(SQLString);
		MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, BaaCaaD26);
		//myAW.WriteCSV("./Data_ABCD/BaaCaaD26.csv", SQLString);
		//myAW.QueryToXML(SQLString, BaaCaaD26);
	}	
	
	private static void AaBaCaaD25(QueryManager myAW){	
		File AaBaCaaD25 = new File("./results/AaBaCaaD25.xml"); 
		SQLString =
				"SELECT " + ABCD.CaaD25 + ".DID, " + ABCD.AaaBaaC25 + ".AID" + " " + //COUNT(*) " + // "  + 
				"FROM " + ABCD.CaaD25 + " " + 
				"INNER JOIN " + ABCD.AaaBaaC25 + " " + 
				"ON " + ABCD.AaaBaaC25 + ".CID = " + ABCD.CaaD25 + ".CID"; 
		System.out.println(SQLString);
		MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, AaBaCaaD25);
		//myAW.WriteCSV("./Data_ABCD/AaBaCaaD25.csv", SQLString);
		//myAW.QueryToXML(SQLString, AaBaCaaD25);
	}
	private static void AaaBaCaD25(QueryManager myAW){	
		File AaaBaCaD25 = new File("./results/AaaBaCaD25.xml"); 
		SQLString =
				"SELECT " + ABCD.AaaB25 + ".AID, " + ABCD.BaaCaaD25 + ".DID" + " " + //COUNT(*) " + // "  + 
				"FROM " + ABCD.AaaB25 + " " + 
				"INNER JOIN " + ABCD.BaaCaaD25 + " " + 
				"ON " + ABCD.BaaCaaD25 + ".BID = " + ABCD.AaaB25 + ".BID"; 
		System.out.println(SQLString);
		MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, AaaBaCaD25);
		//myAW.WriteCSV("./Data_ABCD/AaaBaCaD25.csv", SQLString);
		//myAW.QueryToXML(SQLString, AaaBaCaD25);
	}
	private static void AaaBaaC25(QueryManager myAW){	
		File AaaBaaC25 = new File("./results/AaaBaaC25.xml"); 
		SQLString =
				"SELECT " + ABCD.AaaB25 + ".AID, " + ABCD.BaaC25 + ".CID" + " " + //COUNT(*) " + // "  + 
				"FROM " + ABCD.AaaB25 + " " + 
				"INNER JOIN " + ABCD.BaaC25 + " " + 
				"ON " + ABCD.BaaC25 + ".BID = " + ABCD.AaaB25 + ".BID"; 
		System.out.println(SQLString);
		MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, AaaBaaC25);
		//myAW.WriteCSV("./Data_ABCD/AaaBaaC25.csv", SQLString);
		//myAW.QueryToXML(SQLString, AaaBaaC25);
	}
	private static void BaaCaaD25(QueryManager myAW){	
		File BaaCaaD25 = new File("./results/BaaCaaD25.xml"); 
		SQLString =
				"SELECT " + ABCD.BaaC25+ ".BID, " + ABCD.CaaD25+ ".DID" + " " + //COUNT(*) " + // "  + 
				"FROM " + ABCD.BaaC25 + " " + 
				"INNER JOIN " + ABCD.CaaD25 + " " + 
				"ON " + ABCD.CaaD25 + ".CID = " + ABCD.BaaC25 + ".CID"; 
		System.out.println(SQLString);
		MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, BaaCaaD25);
		//myAW.WriteCSV("./Data_ABCD/BaaCaaD25.csv", SQLString);
		//myAW.QueryToXML(SQLString, BaaCaaD25);
	}		
	
	private static void AaBaCaaD24(QueryManager myAW){	
		File AaBaCaaD24 = new File("./results/AaBaCaaD24.xml"); 
		SQLString =
				"SELECT " + ABCD.CaaD24 + ".DID, " + ABCD.AaaBaaC24 + ".AID" + " " + //COUNT(*) " + // "  + 
				"FROM " + ABCD.CaaD24 + " " + 
				"INNER JOIN " + ABCD.AaaBaaC24 + " " + 
				"ON " + ABCD.AaaBaaC24 + ".CID = " + ABCD.CaaD24 + ".CID"; 
		System.out.println(SQLString);
		MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, AaBaCaaD24);
		//myAW.WriteCSV("./Data_ABCD/AaBaCaaD24.csv", SQLString);
		//myAW.QueryToXML(SQLString, AaBaCaaD24);
	}
	private static void AaaBaCaD24(QueryManager myAW){	
		File AaaBaCaD24 = new File("./results/AaaBaCaD24.xml"); 
		SQLString =
				"SELECT " + ABCD.AaaB24 + ".AID, " + ABCD.BaaCaaD24 + ".DID" + " " + //COUNT(*) " + // "  + 
				"FROM " + ABCD.AaaB24 + " " + 
				"INNER JOIN " + ABCD.BaaCaaD24 + " " + 
				"ON " + ABCD.BaaCaaD24 + ".BID = " + ABCD.AaaB24 + ".BID"; 
		System.out.println(SQLString);
		MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, AaaBaCaD24);
		//myAW.WriteCSV("./Data_ABCD/AaaBaCaD24.csv", SQLString);
		//myAW.QueryToXML(SQLString, AaaBaCaD24);
	}
	private static void AaaBaaC24(QueryManager myAW){	
		File AaaBaaC24 = new File("./results/AaaBaaC24.xml"); 
		SQLString =
				"SELECT " + ABCD.AaaB24 + ".AID, " + ABCD.BaaC24 + ".CID" + " " + //COUNT(*) " + // "  + 
				"FROM " + ABCD.AaaB24 + " " + 
				"INNER JOIN " + ABCD.BaaC24 + " " + 
				"ON " + ABCD.BaaC24 + ".BID = " + ABCD.AaaB24 + ".BID"; 
		System.out.println(SQLString);
		MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, AaaBaaC24);
		//myAW.WriteCSV("./Data_ABCD/AaaBaaC24.csv", SQLString);
		//myAW.QueryToXML(SQLString, AaaBaaC24);
	}
	private static void BaaCaaD24(QueryManager myAW){	
		File BaaCaaD24 = new File("./results/BaaCaaD24.xml"); 
		SQLString =
				"SELECT " + ABCD.BaaC24+ ".BID, " + ABCD.CaaD24+ ".DID" + " " + //COUNT(*) " + // "  + 
				"FROM " + ABCD.BaaC24 + " " + 
				"INNER JOIN " + ABCD.CaaD24 + " " + 
				"ON " + ABCD.CaaD24 + ".CID = " + ABCD.BaaC24 + ".CID"; 
		System.out.println(SQLString);
		MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, BaaCaaD24);
		//myAW.WriteCSV("./Data_ABCD/BaaCaaD24.csv", SQLString);
		//myAW.QueryToXML(SQLString, BaaCaaD24);
	}	
	
	
	private static void AaBaCaaD23(QueryManager myAW){	
		File AaBaCaaD23 = new File("./results/AaBaCaaD23.xml"); 
		SQLString =
				"SELECT " + ABCD.CaaD23 + ".DID, " + ABCD.AaaBaaC23 + ".AID" + " " + //COUNT(*) " + // "  + 
				"FROM " + ABCD.CaaD23 + " " + 
				"INNER JOIN " + ABCD.AaaBaaC23 + " " + 
				"ON " + ABCD.AaaBaaC23 + ".CID = " + ABCD.CaaD23 + ".CID"; 
		System.out.println(SQLString);
		MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, AaBaCaaD23);
		//myAW.WriteCSV("./Data_ABCD/AaBaCaaD23.csv", SQLString);
		//myAW.QueryToXML(SQLString, AaBaCaaD23);
	}
	private static void AaaBaCaD23(QueryManager myAW){	
		File AaaBaCaD23 = new File("./results/AaaBaCaD23.xml"); 
		SQLString =
				"SELECT " + ABCD.AaaB23 + ".AID, " + ABCD.BaaCaaD23 + ".DID" + " " + //COUNT(*) " + // "  + 
				"FROM " + ABCD.AaaB23 + " " + 
				"INNER JOIN " + ABCD.BaaCaaD23 + " " + 
				"ON " + ABCD.BaaCaaD23 + ".BID = " + ABCD.AaaB23 + ".BID"; 
		System.out.println(SQLString);
		MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, AaaBaCaD23);
		//myAW.WriteCSV("./Data_ABCD/AaaBaCaD23.csv", SQLString);
		//myAW.QueryToXML(SQLString, AaaBaCaD23);
	}
	private static void AaaBaaC23(QueryManager myAW){	
		File AaaBaaC23 = new File("./results/AaaBaaC23.xml"); 
		SQLString =
				"SELECT " + ABCD.AaaB23 + ".AID, " + ABCD.BaaC23 + ".CID" + " " + //COUNT(*) " + // "  + 
				"FROM " + ABCD.AaaB23 + " " + 
				"INNER JOIN " + ABCD.BaaC23 + " " + 
				"ON " + ABCD.BaaC23 + ".BID = " + ABCD.AaaB23 + ".BID"; 
		System.out.println(SQLString);
		MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, AaaBaaC23);
		//myAW.WriteCSV("./Data_ABCD/AaaBaaC23.csv", SQLString);
		//myAW.QueryToXML(SQLString, AaaBaaC23);
	}
	private static void BaaCaaD23(QueryManager myAW){	
		File BaaCaaD23 = new File("./results/BaaCaaD23.xml"); 
		SQLString =
				"SELECT " + ABCD.BaaC23 + ".BID, " + ABCD.CaaD23 + ".DID" + " " + //COUNT(*) " + // "  + 
				"FROM " + ABCD.BaaC23 + " " + 
				"INNER JOIN " + ABCD.CaaD23 + " " + 
				"ON " + ABCD.CaaD23 + ".CID = " + ABCD.BaaC23 + ".CID"; 
		System.out.println(SQLString);
		MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, BaaCaaD23);
		//myAW.WriteCSV("./Data_ABCD/BaaCaaD23.csv", SQLString);
		//myAW.QueryToXML(SQLString, BaaCaaD23);
	}	

	
	
	private static void AaBaCaaD22(QueryManager myAW){	
		File AaBaCaaD22 = new File("./results/AaBaCaaD22.xml"); 
		SQLString =
				"SELECT " + ABCD.CaaD22 + ".DID, " + ABCD.AaaBaaC22 + ".AID" + " " + //COUNT(*) " + // "  + 
				"FROM " + ABCD.CaaD22 + " " + 
				"INNER JOIN " + ABCD.AaaBaaC22 + " " + 
				"ON " + ABCD.AaaBaaC22 + ".CID = " + ABCD.CaaD22 + ".CID"; 
		System.out.println(SQLString);
		MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, AaBaCaaD22);
		//myAW.WriteCSV("./Data_ABCD/AaBaCaaD22.csv", SQLString);
		//myAW.QueryToXML(SQLString, AaBaCaaD22);
	}
	private static void AaaBaCaD22(QueryManager myAW){	
		File AaaBaCaD22 = new File("./results/AaaBaCaD22.xml"); 
		SQLString =
				"SELECT " + ABCD.AaaB22 + ".AID, " + ABCD.BaaCaaD22 + ".DID" + " " + //COUNT(*) " + // "  + 
				"FROM " + ABCD.AaaB22 + " " + 
				"INNER JOIN " + ABCD.BaaCaaD22 + " " + 
				"ON " + ABCD.BaaCaaD22 + ".BID = " + ABCD.AaaB22 + ".BID"; 
		System.out.println(SQLString);
		MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, AaaBaCaD22);
		//myAW.WriteCSV("./Data_ABCD/AaaBaCaD22.csv", SQLString);
		//myAW.QueryToXML(SQLString, AaaBaCaD22);
	}
	private static void AaaBaaC22(QueryManager myAW){	
		File AaaBaaC22 = new File("./results/AaaBaaC22.xml"); 
		SQLString =
				"SELECT " + ABCD.AaaB22 + ".AID, " + ABCD.BaaC22 + ".CID" + " " + //COUNT(*) " + // "  + 
				"FROM " + ABCD.AaaB22 + " " + 
				"INNER JOIN " + ABCD.BaaC22 + " " + 
				"ON " + ABCD.BaaC22 + ".BID = " + ABCD.AaaB22 + ".BID"; 
		System.out.println(SQLString);
		MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, AaaBaaC22);
		//myAW.WriteCSV("./Data_ABCD/AaaBaaC22.csv", SQLString);
		//myAW.QueryToXML(SQLString, AaaBaaC22);
	}
	private static void BaaCaaD22(QueryManager myAW){	
		File BaaCaaD22 = new File("./results/BaaCaaD22.xml"); 
		SQLString =
				"SELECT " + ABCD.BaaC22 + ".BID, " + ABCD.CaaD22 + ".DID" + " " + //COUNT(*) " + // "  + 
				"FROM " + ABCD.BaaC22 + " " + 
				"INNER JOIN " + ABCD.CaaD22 + " " + 
				"ON " + ABCD.CaaD22 + ".CID = " + ABCD.BaaC22 + ".CID"; 
		System.out.println(SQLString);
		MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, BaaCaaD22);
		//myAW.WriteCSV("./Data_ABCD/BaaCaaD22.csv", SQLString);
		//myAW.QueryToXML(SQLString, BaaCaaD22);
	}	
	
	private static void AaBaCaaD21(QueryManager myAW){	
		File AaBaCaaD21 = new File("./results/AaBaCaaD21.xml"); 
		SQLString =
				"SELECT " + ABCD.CaaD21 + ".DID, " + ABCD.AaaBaaC21 + ".AID" + " " + //COUNT(*) " + // "  + 
				"FROM " + ABCD.CaaD21 + " " + 
				"INNER JOIN " + ABCD.AaaBaaC21 + " " + 
				"ON " + ABCD.AaaBaaC21 + ".CID = " + ABCD.CaaD21 + ".CID"; 
		System.out.println(SQLString);
		MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, AaBaCaaD21);
		//myAW.WriteCSV("./Data_ABCD/AaBaCaaD21.csv", SQLString);
		//myAW.QueryToXML(SQLString, AaBaCaaD21);
	}
	private static void AaaBaCaD21(QueryManager myAW){	
		File AaaBaCaD21 = new File("./results/AaaBaCaD21.xml"); 
		SQLString =
				"SELECT " + ABCD.AaaB21 + ".AID, " + ABCD.BaaCaaD21 + ".DID" + " " + //COUNT(*) " + // "  + 
				"FROM " + ABCD.AaaB21 + " " + 
				"INNER JOIN " + ABCD.BaaCaaD21 + " " + 
				"ON " + ABCD.BaaCaaD21 + ".BID = " + ABCD.AaaB21 + ".BID"; 
		System.out.println(SQLString);
		MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, AaaBaCaD21);
		//myAW.WriteCSV("./Data_ABCD/AaaBaCaD21.csv", SQLString);
		//myAW.QueryToXML(SQLString, AaaBaCaD21);
	}
	private static void AaaBaaC21(QueryManager myAW){	
		File AaaBaaC21 = new File("./results/AaaBaaC21.xml"); 
		SQLString =
				"SELECT " + ABCD.AaaB21 + ".AID, " + ABCD.BaaC21 + ".CID" + " " + //COUNT(*) " + // "  + 
				"FROM " + ABCD.AaaB21 + " " + 
				"INNER JOIN " + ABCD.BaaC21 + " " + 
				"ON " + ABCD.BaaC21 + ".BID = " + ABCD.AaaB21 + ".BID"; 
		System.out.println(SQLString);
		MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, AaaBaaC21);
		//myAW.WriteCSV("./Data_ABCD/AaaBaaC21.csv", SQLString);
		//myAW.QueryToXML(SQLString, AaaBaaC21);
	}
	private static void BaaCaaD21(QueryManager myAW){	
		File BaaCaaD21 = new File("./results/BaaCaaD21.xml"); 
		SQLString =
				"SELECT " + ABCD.BaaC21 + ".BID, " + ABCD.CaaD21 + ".DID" + " " + //COUNT(*) " + // "  + 
				"FROM " + ABCD.BaaC21 + " " + 
				"INNER JOIN " + ABCD.CaaD21 + " " + 
				"ON " + ABCD.CaaD21 + ".CID = " + ABCD.BaaC21 + ".CID"; 
		System.out.println(SQLString);
		MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, BaaCaaD21);
		//myAW.WriteCSV("./Data_ABCD/BaaCaaD21.csv", SQLString);
		//myAW.QueryToXML(SQLString, BaaCaaD21);
	}
	
	private static void AaBaCaaD20(QueryManager myAW){	
		File AaBaCaaD20 = new File("./results/AaBaCaaD20.xml"); 
		SQLString =
				"SELECT " + ABCD.CaaD20 + ".DID, " + ABCD.AaaBaaC20 + ".AID" + " " + //COUNT(*) " + // "  + 
				"FROM " + ABCD.CaaD20 + " " + 
				"INNER JOIN " + ABCD.AaaBaaC20 + " " + 
				"ON " + ABCD.AaaBaaC20 + ".CID = " + ABCD.CaaD20 + ".CID"; 
		System.out.println(SQLString);
		MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, AaBaCaaD20);
		//myAW.WriteCSV("./Data_ABCD/AaBaCaaD20.csv", SQLString);
		//myAW.QueryToXML(SQLString, AaBaCaaD20);
	}
	private static void AaaBaCaD20(QueryManager myAW){	
		File AaaBaCaD20 = new File("./results/AaaBaCaD20.xml"); 
		SQLString =
				"SELECT " + ABCD.AaaB20 + ".AID, " + ABCD.BaaCaaD20 + ".DID" + " " + //COUNT(*) " + // "  + 
				"FROM " + ABCD.AaaB20 + " " + 
				"INNER JOIN " + ABCD.BaaCaaD20 + " " + 
				"ON " + ABCD.BaaCaaD20 + ".BID = " + ABCD.AaaB20 + ".BID"; 
		System.out.println(SQLString);
		MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, AaaBaCaD20);
		//myAW.WriteCSV("./Data_ABCD/AaaBaCaD20.csv", SQLString);
		//myAW.QueryToXML(SQLString, AaaBaCaD20);
	}
	private static void AaaBaaC20(QueryManager myAW){	
		File AaaBaaC20 = new File("./results/AaaBaaC20.xml"); 
		SQLString =
				"SELECT " + ABCD.AaaB20 + ".AID, " + ABCD.BaaC20 + ".CID" + " " + //COUNT(*) " + // "  + 
				"FROM " + ABCD.AaaB20 + " " + 
				"INNER JOIN " + ABCD.BaaC20 + " " + 
				"ON " + ABCD.BaaC20 + ".BID = " + ABCD.AaaB20 + ".BID"; 
		System.out.println(SQLString);
		MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, AaaBaaC20);
		//myAW.WriteCSV("./Data_ABCD/AaaBaaC20.csv", SQLString);
		//myAW.QueryToXML(SQLString, AaaBaaC20);
	}
	private static void BaaCaaD20(QueryManager myAW){	
		File BaaCaaD20 = new File("./results/BaaCaaD20.xml"); 
		SQLString =
				"SELECT " + ABCD.BaaC20 + ".BID, " + ABCD.CaaD20 + ".DID" + " " + //COUNT(*) " + // "  + 
				"FROM " + ABCD.BaaC20 + " " + 
				"INNER JOIN " + ABCD.CaaD20 + " " + 
				"ON " + ABCD.CaaD20 + ".CID = " + ABCD.BaaC20 + ".CID"; 
		System.out.println(SQLString);
		MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, BaaCaaD20);
		//myAW.WriteCSV("./Data_ABCD/BaaCaaD20.csv", SQLString);
		//myAW.QueryToXML(SQLString, BaaCaaD20);
	}	
	
	private static void AaBaCaaD19(QueryManager myAW){	
		File AaBaCaaD19 = new File("./results/AaBaCaaD19.xml"); 
		SQLString =
				"SELECT " + ABCD.CaaD19 + ".DID, " + ABCD.AaaBaaC19 + ".AID" + " " + //COUNT(*) " + // "  + 
				"FROM " + ABCD.CaaD19 + " " + 
				"INNER JOIN " + ABCD.AaaBaaC19 + " " + 
				"ON " + ABCD.AaaBaaC19 + ".CID = " + ABCD.CaaD19 + ".CID"; 
		System.out.println(SQLString);
		MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, AaBaCaaD19);
		//myAW.WriteCSV("./Data_ABCD/AaBaCaaD19.csv", SQLString);
		//myAW.QueryToXML(SQLString, AaBaCaaD19);
	}
	private static void AaaBaCaD19(QueryManager myAW){	
		File AaaBaCaD19 = new File("./results/AaaBaCaD19.xml"); 
		SQLString =
				"SELECT " + ABCD.AaaB19 + ".AID, " + ABCD.BaaCaaD19 + ".DID" + " " + //COUNT(*) " + // "  + 
				"FROM " + ABCD.AaaB19 + " " + 
				"INNER JOIN " + ABCD.BaaCaaD19 + " " + 
				"ON " + ABCD.BaaCaaD19 + ".BID = " + ABCD.AaaB19 + ".BID"; 
		System.out.println(SQLString);
		MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, AaaBaCaD19);
		//myAW.WriteCSV("./Data_ABCD/AaaBaCaD19.csv", SQLString);
		//myAW.QueryToXML(SQLString, AaaBaCaD19);
	}
	private static void AaaBaaC19(QueryManager myAW){	
		File AaaBaaC19 = new File("./results/AaaBaaC19.xml"); 
		SQLString =
				"SELECT " + ABCD.AaaB19 + ".AID, " + ABCD.BaaC19 + ".CID" + " " + //COUNT(*) " + // "  + 
				"FROM " + ABCD.AaaB19 + " " + 
				"INNER JOIN " + ABCD.BaaC19 + " " + 
				"ON " + ABCD.BaaC19 + ".BID = " + ABCD.AaaB19 + ".BID"; 
		System.out.println(SQLString);
		MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, AaaBaaC19);
		//myAW.WriteCSV("./Data_ABCD/AaaBaaC19.csv", SQLString);
		//myAW.QueryToXML(SQLString, AaaBaaC19);
	}
	private static void BaaCaaD19(QueryManager myAW){	
		File BaaCaaD19 = new File("./results/BaaCaaD19.xml"); 
		SQLString =
				"SELECT " + ABCD.BaaC19 + ".BID, " + ABCD.CaaD19 + ".DID" + " " + //COUNT(*) " + // "  + 
				"FROM " + ABCD.BaaC19 + " " + 
				"INNER JOIN " + ABCD.CaaD19 + " " + 
				"ON " + ABCD.CaaD19 + ".CID = " + ABCD.BaaC19 + ".CID"; 
		System.out.println(SQLString);
		MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, BaaCaaD19);
		//myAW.WriteCSV("./Data_ABCD/BaaCaaD19.csv", SQLString);
		//myAW.QueryToXML(SQLString, BaaCaaD19);
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
