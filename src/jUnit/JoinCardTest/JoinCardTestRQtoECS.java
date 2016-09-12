package jUnit.JoinCardTest;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Before;
import org.junit.Test;

import ResourceStrings.SD;
import optimizer.AskWise;
import optimizer.QueryManager;
import qEng.ExternalOracle;
import qEng.InternalH2;
import utils.CreateTablesInMemory;
import utils.MeasureCostArbitrary;

public class JoinCardTestRQtoECS {
	public static Boolean setupIsDone = false;
	private static final String H2PROTO = "jdbc:h2:";
	private static final String IH2FP = "./Data/TestCaseDataBases/";
	private static final String IH2DBName = "JoinCardTestRQtoECS";
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
		CreateTablesInMemory.createTablesInMemoryRQtoECS(myAW);
		CreateTablesInMemory.createTablesInMemoryRQtoECSJS(myAW);
		// create link for 
		CreateTablesInMemory.registerTMRQtoECS(myAW);
		CreateTablesInMemory.registerTMRQtoECSJS(myAW);
		setupIsDone = true;
		}
	}
	@Test
	public void test() {

		// 1) (R-SCP)-CC -CP)-CC: ORACLE OPTIMAL PATH 2000 (=160+214+1147+479)
		// JoinRtoSCP.xml cost: 160
		// JoinRtoSCP(myAW); // card 9760		

		//JoinR_SCP__CC.xml cost: 214
		//JoinR_SCP__CC(myAW); // card 7551
	
		// JoinR_CC__UCS.xml cost: 1147
		// JoinR_CC__UCS(myAW); //121300
		
		// JoinR_UCS__EC.xml cost: 479
		// JoinR_UCS__EC(myAW); // 51844
		
		// 2) ((R-(CP-CC))-(CC-EC))-ECS) 1453 (= 173+128+231+921)
		//JoinCP_CC.xml cost: 173
		// JoinCP_CC(myAW); // card 9468
		
		// JoinR__CP_CC.xml cost: 128
		// JoinR__CP_CC(myAW); // card 7552
		
		// JoinCC_EC.xml cost: 231
		// JoinCC_EC(myAW); // card 12876
		
		// JoinR_CC_EC.xml cost: 921
		JoinR_CC_EC(myAW); // card 96909
		
		// JoinR_EC__ECS.xml cost: 450
		// JoinR_EC__ECS(myAW);
		
		
		// JoinRtoECS.xml cost: 21362
		//JoinRtoECS(myAW); // card 2230265
		//JoinRtoECS(myOAW); // JoinRtoECS.xml cost: 751
		//JoinR_ECS_OR(myOAW);
		//JoinR_ECS_OR_ord(myOAW); // JoinR_ECS_OR_ord.xml cost:  430 450 440 425 450
		//JoinR_ECS_OR(myOAW); // JoinR_ECS_OR.xml cost:            540 450 469 447 460 // /*+ORDERED */ 3686 1187 537 540 981
																					  // 954 484 988 1067 989 514 /*+ORDERED */
																					  // 643 520 628 604 590 867
		
		// test(myOAW); //  440 463 514 490 466 458 459 443 524 455 w/ +ORDERED
		// test(myOAW);	//  513 472 474 453 456 465 445 446 461
		
		// OAW
		//JoinR_ECS_OR_ord_woOrd(myOAW); // 889 787 744 773 760 // 653 777 692
		//JoinR_ECS_OR_ord(myOAW);  //    744 684 708 765 799 // 704 667 690 682 695
		
		//JoinR_ECS_OR_ord_woOrd(myAW);
		//JoinR_ECS_OR_ord(myAW); //32167
		
		//JoinSCP_ECS_OR(myOAW); // 674 524 676 541 572
		JoinSCP_ECS_OR_ord(myOAW); //581 550 557 605 533 568
		
	}
	private static void JoinSCP_ECS_OR_ord(QueryManager myAW){
		File JoinSCP_ECS_OR_ord = new File("./results/JoinSCP_ECS_OR_ord.xml"); 
//		String SQLString =
//				"SELECT /*+ORDERED */ * " + 
//				"FROM RQaaCP, CPaaSCP, SCPaaCC, CCaaUCS, UCSaaEC, ECaaECS" + " " +
//				"WHERE CPaaSCP.COMPONENTID = RQaaCP.COMPONENTID" + " " +
//				    "AND SCPaaCC.SUBCOMPONENTID = CPaaSCP.SUBCOMPONENTID" + " " +
//				    "AND CCaaUCS.CLASSNAME = SCPaaCC.CLASSNAME" + " " + 
//				    "AND UCSaaEC.USECASESTEPID = CCaaUCS.USECASESTEPID" + " " + 
//				    "AND ECaaECS.EXCEPTIONCASEID = UCSaaEC.EXCEPTIONCASEID";
		String SQLString = 
				"SELECT /*+ORDERED */ * " + //    
				"FROM SCPaaCC" + " " +
				"INNER JOIN CCaaUCS ON CCaaUCS.CLASSNAME = SCPaaCC.CLASSNAME" + " " +
				"INNER JOIN UCSaaEC ON UCSaaEC.USECASESTEPID = CCaaUCS.USECASESTEPID" + " " +
				"INNER JOIN ECaaECS ON ECaaECS.EXCEPTIONCASEID = UCSaaEC.EXCEPTIONCASEID";
		System.out.println(SQLString);
		assertTrue("failure " + JoinSCP_ECS_OR_ord.getName().toString() , 
					MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, JoinSCP_ECS_OR_ord) >= 10.0);
		//myAW.QueryToXML(SQLString, JoinSCP_ECS_OR_ord);
	}
	private static void JoinSCP_ECS_OR(QueryManager myAW){
		File JoinSCP_ECS_OR = new File("./results/JoinSCP_ECS_OR.xml"); 
//		String SQLString =
//				"SELECT /*+ORDERED */ * " + 
//				"FROM RQaaCP, CPaaSCP, SCPaaCC, CCaaUCS, UCSaaEC, ECaaECS" + " " +
//				"WHERE CPaaSCP.COMPONENTID = RQaaCP.COMPONENTID" + " " +
//				    "AND SCPaaCC.SUBCOMPONENTID = CPaaSCP.SUBCOMPONENTID" + " " +
//				    "AND CCaaUCS.CLASSNAME = SCPaaCC.CLASSNAME" + " " + 
//				    "AND UCSaaEC.USECASESTEPID = CCaaUCS.USECASESTEPID" + " " + 
//				    "AND ECaaECS.EXCEPTIONCASEID = UCSaaEC.EXCEPTIONCASEID";
		String SQLString = 
				"SELECT  * " + // /*+ORDERED */   
				"FROM SCPaaCC" + " " +
				"INNER JOIN CCaaUCS ON CCaaUCS.CLASSNAME = SCPaaCC.CLASSNAME" + " " +
				"INNER JOIN UCSaaEC ON UCSaaEC.USECASESTEPID = CCaaUCS.USECASESTEPID" + " " +
				"INNER JOIN ECaaECS ON ECaaECS.EXCEPTIONCASEID = UCSaaEC.EXCEPTIONCASEID";
		System.out.println(SQLString);
		assertTrue("failure " + JoinSCP_ECS_OR.getName().toString() , 
					MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, JoinSCP_ECS_OR) >= 10.0);
		//myAW.QueryToXML(SQLString, JoinR_ECS_OR);
	}

	
	private static void test(QueryManager myAW){
		File test = new File("./results/test.xml"); 
		String SQLString =
				"SELECT  * " + // /*+ORDERED */
				"FROM RQaaCP, CPaaSCP, SCPaaCC, CCaaUCS, UCSaaEC, ECaaECS" + " " +
				"WHERE CPaaSCP.COMPONENTID = RQaaCP.COMPONENTID" + " " +
				    "AND SCPaaCC.SUBCOMPONENTID = CPaaSCP.SUBCOMPONENTID" + " " +
				    "AND CCaaUCS.CLASSNAME = SCPaaCC.CLASSNAME" + " " + 
				    "AND UCSaaEC.USECASESTEPID = CCaaUCS.USECASESTEPID" + " " + 
				    "AND ECaaECS.EXCEPTIONCASEID = UCSaaEC.EXCEPTIONCASEID";
//		String SQLString = 
//				"SELECT  * " + //   /*+ORDERED */
//				"FROM CPaaSCP" + " " +
//				"INNER JOIN SCPaaCC ON SCPaaCC.SUBCOMPONENTID = CPaaSCP.SUBCOMPONENTID" + " " +
//				"INNER JOIN RQaaCP ON RQaaCP.COMPONENTID = CPaaSCP.COMPONENTID" + " " +
//				"INNER JOIN CCaaUCS ON CCaaUCS.CLASSNAME = SCPaaCC.CLASSNAME" + " " +
//				"INNER JOIN UCSaaEC ON UCSaaEC.USECASESTEPID = CCaaUCS.USECASESTEPID" + " " +
//				"INNER JOIN ECaaECS ON ECaaECS.EXCEPTIONCASEID = UCSaaEC.EXCEPTIONCASEID";
//		String SQLString = 
//				"SELECT * " + // /*+ORDERED */ 
//				"FROM ECaaECS";
		System.out.println(SQLString);
		assertTrue("failure " + test.getName().toString() , 
					MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, test) >= 10.0);

	}
	
	private static void JoinR_ECS_OR(QueryManager myAW){
		File JoinR_ECS_OR = new File("./results/JoinR_ECS_OR.xml"); 
//		String SQLString =
//				"SELECT /*+ORDERED */ * " + 
//				"FROM RQaaCP, CPaaSCP, SCPaaCC, CCaaUCS, UCSaaEC, ECaaECS" + " " +
//				"WHERE CPaaSCP.COMPONENTID = RQaaCP.COMPONENTID" + " " +
//				    "AND SCPaaCC.SUBCOMPONENTID = CPaaSCP.SUBCOMPONENTID" + " " +
//				    "AND CCaaUCS.CLASSNAME = SCPaaCC.CLASSNAME" + " " + 
//				    "AND UCSaaEC.USECASESTEPID = CCaaUCS.USECASESTEPID" + " " + 
//				    "AND ECaaECS.EXCEPTIONCASEID = UCSaaEC.EXCEPTIONCASEID";
		String SQLString = 
				"SELECT /*+ORDERED */ * " + //   
				"FROM CPaaSCP" + " " +
				"INNER JOIN SCPaaCC ON SCPaaCC.SUBCOMPONENTID = CPaaSCP.SUBCOMPONENTID" + " " +
				"INNER JOIN RQaaCP ON RQaaCP.COMPONENTID = CPaaSCP.COMPONENTID" + " " +
				"INNER JOIN CCaaUCS ON CCaaUCS.CLASSNAME = SCPaaCC.CLASSNAME" + " " +
				"INNER JOIN UCSaaEC ON UCSaaEC.USECASESTEPID = CCaaUCS.USECASESTEPID" + " " +
				"INNER JOIN ECaaECS ON ECaaECS.EXCEPTIONCASEID = UCSaaEC.EXCEPTIONCASEID";
//				"SELECT  *" + " " + 
//				"FROM CPaaSCP" + " " + 
//				"INNER JOIN RQaaCP ON RQaaCP.COMPONENTID = CPaaSCP.COMPONENTID" + " " + 
//				"INNER JOIN SCPaaCC ON SCPaaCC.SUBCOMPONENTID = CPaaSCP.SUBCOMPONENTID" + " " + 
//				"INNER JOIN CCaaUCS ON CCaaUCS.CLASSNAME = SCPaaCC.CLASSNAME" + " " + 
//				"INNER JOIN UCSaaEC ON UCSaaEC.USECASESTEPID = CCaaUCS.USECASESTEPID" + " " + 
//				"INNER JOIN ECaaECS ON ECaaECS.EXCEPTIONCASEID = UCSaaEC.EXCEPTIONCASEID";
		System.out.println(SQLString);
		assertTrue("failure " + JoinR_ECS_OR.getName().toString() , 
					MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, JoinR_ECS_OR) >= 10.0);
		//myAW.QueryToXML(SQLString, JoinR_ECS_OR);
	}
	
	private static void JoinR_ECS_OR_ord_woOrd(QueryManager myAW){
		File JoinR_ECS_OR_ord = new File("./results/JoinR_ECS_OR_ord.xml"); 
		String SQLString =
//				SELECT /*+ORDERED */ *
//				FROM SCPaaCC, CPaaSCP, RQaaCP, UCSaaEC, CCaaUCS, ECaaECS
//				WHERE SCPaaCC.SUBCOMPONENTID = CPaaSCP.SUBCOMPONENTID
//				    AND RQaaCP.COMPONENTID = CPaaSCP.COMPONENTID
//				    AND UCSaaEC.USECASESTEPID = CCaaUCS.USECASESTEPID
//				    AND CCaaUCS.CLASSNAME = SCPaaCC.CLASSNAME 
//				    AND ECaaECS.EXCEPTIONCASEID = UCSaaEC.EXCEPTIONCASEID;
				"SELECT * " + 
				"FROM SCPaaCC, CPaaSCP, RQaaCP, CCaaUCS, UCSaaEC, ECaaECS" + " " +
				"WHERE  SCPaaCC.SUBCOMPONENTID = CPaaSCP.SUBCOMPONENTID" + " " +
				    "AND CPaaSCP.COMPONENTID = RQaaCP.COMPONENTID" + " " +
				    "AND CCaaUCS.CLASSNAME = SCPaaCC.CLASSNAME" + " " + 
				    "AND UCSaaEC.USECASESTEPID = CCaaUCS.USECASESTEPID" + " " + 
				    "AND ECaaECS.EXCEPTIONCASEID = UCSaaEC.EXCEPTIONCASEID";
		System.out.println(SQLString);
		assertTrue("failure " + JoinR_ECS_OR_ord.getName().toString() , 
					MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, JoinR_ECS_OR_ord) >= 10.0);
		//myAW.QueryToXML(SQLString, JoinR_ECS_OR_ord);
	}
	
	private static void JoinR_ECS_OR_ord(QueryManager myAW){
		File JoinR_ECS_OR_ord = new File("./results/JoinR_ECS_OR_ord.xml"); 
		String SQLString =
//				SELECT /*+ORDERED */ *
//				FROM SCPaaCC, CPaaSCP, RQaaCP, UCSaaEC, CCaaUCS, ECaaECS
//				WHERE SCPaaCC.SUBCOMPONENTID = CPaaSCP.SUBCOMPONENTID
//				    AND RQaaCP.COMPONENTID = CPaaSCP.COMPONENTID
//				    AND UCSaaEC.USECASESTEPID = CCaaUCS.USECASESTEPID
//				    AND CCaaUCS.CLASSNAME = SCPaaCC.CLASSNAME 
//				    AND ECaaECS.EXCEPTIONCASEID = UCSaaEC.EXCEPTIONCASEID;
				"SELECT /*+ORDERED */ * " + 
				"FROM SCPaaCC, CPaaSCP, RQaaCP, CCaaUCS, UCSaaEC, ECaaECS" + " " +
				"WHERE  SCPaaCC.SUBCOMPONENTID = CPaaSCP.SUBCOMPONENTID" + " " +
				    "AND CPaaSCP.COMPONENTID = RQaaCP.COMPONENTID" + " " +
				    "AND CCaaUCS.CLASSNAME = SCPaaCC.CLASSNAME" + " " + 
				    "AND UCSaaEC.USECASESTEPID = CCaaUCS.USECASESTEPID" + " " + 
				    "AND ECaaECS.EXCEPTIONCASEID = UCSaaEC.EXCEPTIONCASEID";
		System.out.println(SQLString);
		assertTrue("failure " + JoinR_ECS_OR_ord.getName().toString() , 
					MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, JoinR_ECS_OR_ord) >= 10.0);
		//myAW.QueryToXML(SQLString, JoinR_ECS_OR_ord);
	}

	
	private static void JoinR_CC_EC(QueryManager myAW){
		File JoinR_CC_EC = new File("./results/JoinR_CC_EC.xml"); 
		String SQLString =
				"SELECT COUNT (*) " +
//				"SELECT " + SD.R_CC8kTableName + ".ID" + ", " + 
//							SD.CC_EC12kTableName + ".EXCEPTIONCASEID" + " " +
				"FROM " + SD.R_CC8kTableName + " " +
				"INNER JOIN " + SD.CC_EC12kTableName + " " + 
				"ON " + SD.CC_EC12kTableName + ".CLASSNAME = " + SD.R_CC8kTableName + ".CLASSNAME"; 
		System.out.println(SQLString);
		assertTrue("failure " + JoinR_CC_EC.getName().toString() , 
					MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, JoinR_CC_EC) >= 10.0);
		myAW.QueryToXML(SQLString, JoinR_CC_EC);
	}
	
	private static void JoinCC_EC(QueryManager myAW){
		File JoinCC_EC = new File("./results/JoinCC_EC.xml"); 
		String SQLString =
//				"SELECT COUNT (*) " +
				"SELECT " + SD.CC_UCS16kTableName + ".CLASSNAME" + ", " + 
							SD.UCS_EC16kTableName + ".EXCEPTIONCASEID" + " " +
				"FROM " + SD.CC_UCS16kTableName + " " +
				"INNER JOIN " + SD.UCS_EC16kTableName + " " + 
				"ON " + SD.UCS_EC16kTableName + ".USECASESTEPID = " + SD.CC_UCS16kTableName + ".USECASESTEPID"; 
		System.out.println(SQLString);
		assertTrue("failure " + JoinCC_EC.getName().toString() , 
					MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, JoinCC_EC) >= 10.0);
		//myAW.QueryToXML(SQLString, JoinCC_EC);
	}

	
	private static void JoinR__CP_CC(QueryManager myAW){
		File JoinR__CP_CC = new File("./results/JoinR__CP_CC.xml"); 
		String SQLString =
//				"SELECT COUNT (*) " +
				"SELECT " + SD.RQ_CP7kTableName + ".ID" + ", " + 
							SD.CP_CC9kTableName + ".CLASSNAME" + " " +
				"FROM " + SD.RQ_CP7kTableName + " " +
				"INNER JOIN " + SD.CP_CC9kTableName + " " + 
				"ON " + SD.CP_CC9kTableName + ".COMPONENTID = " + SD.RQ_CP7kTableName + ".COMPONENTID"; 
		System.out.println(SQLString);
		assertTrue("failure " + JoinR__CP_CC.getName().toString() , 
					MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, JoinR__CP_CC) >= 10.0);
		//myAW.QueryToXML(SQLString, JoinR__CP_CC);
	}

	
	private static void JoinCP_CC(QueryManager myAW){
		File JoinCP_CC = new File("./results/JoinCP_CC.xml"); 
		String SQLString =
//				"SELECT COUNT (*) " +
				"SELECT " + SD.CP_SCP12kTableName + ".COMPONENTID" + ", " + 
							SD.SCP_CC12kTableName + ".CLASSNAME" + " " +
				"FROM " + SD.CP_SCP12kTableName + " " +
				"INNER JOIN " + SD.SCP_CC12kTableName + " " + 
				"ON " + SD.SCP_CC12kTableName + ".SUBCOMPONENTID = " + SD.CP_SCP12kTableName + ".SUBCOMPONENTID"; 
		System.out.println(SQLString);
		assertTrue("failure " + JoinCP_CC.getName().toString() , 
					MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, JoinCP_CC) >= 10.0);
		//myAW.QueryToXML(SQLString, JoinCP_CC);
	}

	private static void JoinR_EC__ECS(QueryManager myAW){
		File JoinR_EC__ECS = new File("./results/JoinR_EC__ECS.xml"); 
		String SQLString =
//				"SELECT COUNT (*) " +
				"SELECT " + SD.R_UCS66kTableName + ".ID" + ", " + 
							SD.UCS_EC16kTableName + ".EXCEPTIONCASEID" + " " +
				"FROM " + SD.R_UCS66kTableName + " " +
				"INNER JOIN " + SD.UCS_EC16kTableName + " " + 
				"ON " + SD.UCS_EC16kTableName + ".USECASESTEPID = " + SD.R_UCS66kTableName + ".USECASESTEPID"; 
		System.out.println(SQLString);
		assertTrue("failure " + JoinR_EC__ECS.getName().toString() , 
					MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, JoinR_EC__ECS) >= 10.0);
		//myAW.QueryToXML(SQLString, JoinR_UCS__EC);
	}

	
	private static void JoinR_UCS__EC(QueryManager myAW){
		File JoinR_UCS__EC = new File("./results/JoinR_UCS__EC.xml"); 
		String SQLString =
//				"SELECT COUNT (*) " +
				"SELECT " + SD.R_UCS66kTableName + ".ID" + ", " + 
							SD.UCS_EC16kTableName + ".EXCEPTIONCASEID" + " " +
				"FROM " + SD.R_UCS66kTableName + " " +
				"INNER JOIN " + SD.UCS_EC16kTableName + " " + 
				"ON " + SD.UCS_EC16kTableName + ".USECASESTEPID = " + SD.R_UCS66kTableName + ".USECASESTEPID"; 
		System.out.println(SQLString);
		assertTrue("failure " + JoinR_UCS__EC.getName().toString() , 
					MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, JoinR_UCS__EC) >= 10.0);
		//myAW.QueryToXML(SQLString, JoinR_UCS__EC);
	}

	
	private static void JoinR_CC__UCS(QueryManager myAW){
		File JoinR_CC__UCS = new File("./results/JoinR_CC__UCS.xml"); 
		String SQLString =
//				"SELECT COUNT (*) " +
				"SELECT " + SD.R_CC8kTableName + ".ID" + ", " + 
							SD.CC_UCS16kTableName + ".USECASESTEPID" + " " +
				"FROM " + SD.R_CC8kTableName + " " +
				"INNER JOIN " + SD.CC_UCS16kTableName + " " + 
				"ON " + SD.CC_UCS16kTableName + ".CLASSNAME = " + SD.R_CC8kTableName + ".CLASSNAME"; 
		System.out.println(SQLString);
		assertTrue("failure " + JoinR_CC__UCS.getName().toString() , 
					MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, JoinR_CC__UCS) >= 10.0);
		//myAW.QueryToXML(SQLString, JoinR_CC__UCS);
	}

	
	private static void JoinR_SCP__CC(QueryManager myAW){
		File JoinR_SCP__CC = new File("./results/JoinR_SCP__CC.xml"); 
		String SQLString =
//				"SELECT COUNT (*) " +
				"SELECT " + SD.R_SCP10kTableName + ".ID" + ", " + 
							SD.SCP_CC12kTableName + ".CLASSNAME" + " " +
				"FROM " + SD.R_SCP10kTableName + " " +
				"INNER JOIN " + SD.SCP_CC12kTableName + " " + 
				"ON " + SD.SCP_CC12kTableName + ".SUBCOMPONENTID = " + SD.R_SCP10kTableName + ".SUBCOMPONENTID"; 
		System.out.println(SQLString);
		assertTrue("failure " + JoinR_SCP__CC.getName().toString() , 
					MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, JoinR_SCP__CC) >= 10.0);
		//myAW.QueryToXML(SQLString, JoinR_SCP__CC);
	}

	
	// G - SCP
	private static void JoinRtoSCP(QueryManager myAW){
		File JoinRtoSCP = new File("./results/JoinRtoSCP.xml"); 
		String SQLString =
//				"SELECT COUNT (*) " +
				"SELECT " + SD.RQ_CP7kTableName + ".ID" + ", " + 
							SD.CP_SCP12kTableName + ".SUBCOMPONENTID" + " " +
				"FROM " + SD.RQ_CP7kTableName + " " +
				"INNER JOIN " + SD.CP_SCP12kTableName + " " + 
				"ON " + SD.CP_SCP12kTableName + ".COMPONENTID = " + SD.RQ_CP7kTableName + ".COMPONENTID"; 
		System.out.println(SQLString);
		assertTrue("failure " + JoinRtoSCP.getName().toString() , 
					MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, JoinRtoSCP) >= 10.0);
		myAW.QueryToXML(SQLString, JoinRtoSCP);
	}

	// G - ECS
	private static void JoinRtoECS(QueryManager myAW){
		File JoinRtoECS = new File("./results/JoinRtoECS.xml"); 
		String SQLString =
//				"SELECT COUNT (*) " +
				"SELECT" + SD.RQ_CP7kTableName + ".ID" + ", " + 
							SD.EC_ECS24kTableName + ".EXCEPTIONCASESTEPID" + " " +
				"FROM " + SD.RQ_CP7kTableName + " " +
				"INNER JOIN " + SD.CP_SCP12kTableName + " " + 
				"ON " + SD.CP_SCP12kTableName + ".COMPONENTID = " + SD.RQ_CP7kTableName + ".COMPONENTID" + " " +
				"INNER JOIN " + SD.SCP_CC12kTableName + " " + 
				"ON " + SD.SCP_CC12kTableName + ".SUBCOMPONENTID = " + SD.CP_SCP12kTableName + ".SUBCOMPONENTID" + " " +
				"INNER JOIN " + SD.CC_UCS16kTableName + " " + 
				"ON " + SD.CC_UCS16kTableName + ".CLASSNAME = " + SD.SCP_CC12kTableName + ".CLASSNAME" + " " +
				"INNER JOIN " + SD.UCS_EC16kTableName + " " + 
				"ON " + SD.UCS_EC16kTableName + ".USECASESTEPID = " + SD.CC_UCS16kTableName + ".USECASESTEPID" + " " +
				"INNER JOIN " + SD.EC_ECS24kTableName + " " + 
				"ON " + SD.EC_ECS24kTableName + ".EXCEPTIONCASEID = " + SD.UCS_EC16kTableName + ".EXCEPTIONCASEID"; // + " " +
		System.out.println(SQLString);
		assertTrue("failure " + JoinRtoECS.getName().toString() , 
					MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, JoinRtoECS) >= 10.0);
		myAW.QueryToXML(SQLString, JoinRtoECS);
	}


	// TODO:  JOIN G to ECS to write a CSV
	private static void JoinGtoECS1(QueryManager myAW){	
		File JoinGtoECS = new File("./results/JoinGtoECS.xml"); 
		String SQLString =
				"SELECT DISTINCT " + SD.G70TableName + ".GOALID" + " " + 
				"FROM " + SD.G70TableName + " " +
				"INNER JOIN " + SD.G_UC8kTableName + " " + 
				"ON " + SD.G_UC8kTableName + ".GOALID = " + SD.G70TableName + ".GOALID " +
				"INNER JOIN " + SD.UC10kTableName + " " + 
				"ON " + SD.G_UC8kTableName + ".USECASEID = " + SD.UC10kTableName + ".USECASEID " +
				"INNER JOIN " + SD.UC_UCS15kTableName + " " + 
				"ON " + SD.UC_UCS15kTableName + ".USECASEID = " + SD.UC10kTableName + ".USECASEID " +
				"INNER JOIN " + SD.UCS20kTableName + " " + 
				"ON " + SD.UC_UCS15kTableName + ".USECASESTEPID = " + SD.UCS20kTableName + ".USECASESTEPID " +
				"INNER JOIN " + SD.UCS_EC16kTableName + " " + 
				"ON " + SD.UCS_EC16kTableName + ".USECASESTEPID = " + SD.UCS20kTableName + ".USECASESTEPID " +
				"INNER JOIN " + SD.EC10kTableName + " " + 
				"ON " + SD.UCS_EC16kTableName + ".EXCEPTIONCASEID = " + SD.EC10kTableName + ".EXCEPTIONCASEID " +
				"INNER JOIN " + SD.EC_ECS24kTableName + " " + 
				"ON " + SD.EC_ECS24kTableName + ".EXCEPTIONCASEID = " + SD.EC10kTableName + ".EXCEPTIONCASEID " +
				"INNER JOIN " + SD.ECS30kTableName + " " + 
				"ON " + SD.EC_ECS24kTableName + ".EXCEPTIONCASESTEPID = " + SD.ECS30kTableName + ".EXCEPTIONCASESTEPID";
		MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, JoinGtoECS);
		//myAW.QueryToXML(SQLString, JoinGtoECS);
	}
}
