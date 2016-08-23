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

public class JoinCardTestUCStoECS {
	public static Boolean setupIsDone = false;
	private static final String H2PROTO = "jdbc:h2:";
	private static final String IH2FP = "./Data/TestCaseDataBases/";
	private static final String IH2DBName = "JoinCardTestUCStoECS";
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
		CreateTablesInMemory.createTablesInMemoryUCStoECS(myAW);
		// create link for 
		CreateTablesInMemory.registerTMUCStoECS(myAW);
		setupIsDone = true;
		}
	}
	@Test
	public void test() {
		// join cardinality: 362219
		// JoinGtoECS.xml cost: 5026 6677 4613 6439 7107
		// JoinUCStoECS(myAW);
		
		// JoinUCStoECSWpred.xml cost: 8489 3288 11835 5786 7080
		//JoinUCStoECSWpred(myAW);
		
		// JoinUCStoECSWpred.xml cost: 506
		// oracle does skip join automoatically
		//JoinUCStoECSWpred(myOAW);
		
		// JoinUCStoECSWpred.xml cost: 3555 2889
		// cardinality: 73322
		// vector multiplication: 72443.8 (= 362219*0.2(predicate selectivity))
		//JoinUCStoECSWpredAbbr(myAW);
		
		// JoinUCStoECSWpred.xml cost: 576
		//JoinUCStoECSWpredAbbr(myOAW);
		
		//JoinUCStoECSWpredEC.xml cost: 481 592
		JoinUCStoECSWpredEC(myOAW);
		
		//JoinUCStoECSWpredEC.xml cost: 1566 1469
		// Join card: 86981
		//  90554.75 (= 362219*0.25(predicate selectivity))
		//JoinUCStoECSWpredEC(myAW);
		
		
		// actual: 15338
		// by predicate selectivity: 18110.95	(= 362219*.2*.25)
		JoinUCStoECSWpredECMultiPrd(myAW);
	
	}
	private static void JoinUCStoECSWpredECMultiPrd(QueryManager myAW){
		File JoinUCStoECSWpredECMultiPrd = new File("./results/JoinUCStoECSWpredECMultiPrd.xml"); 
		String SQLString =
				"SELECT COUNT(*)" + " " +
				"FROM " + SD.UCS20kTableName + " " +
				"INNER JOIN " + SD.UCS_EC16kTableName + " " + 
				"ON " + SD.UCS20kTableName + ".USECASESTEPID = " + SD.UCS_EC16kTableName + ".USECASESTEPID " +
				"INNER JOIN " + SD.EC10kTableName + " " +
				"ON " + SD.UCS_EC16kTableName + ".ExceptionCaseID = " + SD.EC10kTableName + ".ExceptionCaseID " +
				"INNER JOIN " + SD.EC_ECS24kTableName + " " + 
				"ON " + SD.UCS_EC16kTableName + ".EXCEPTIONCASEID = " + SD.EC_ECS24kTableName + ".EXCEPTIONCASEID" + " " +
				"WHERE " + SD.EC10kTableName + ".PRIMARYACTOR = " + "'Patient'" + " " +
				"AND " + SD.UCS20kTableName + ".STEPDESCRIPTION = " +  
				"'The basal flow rate, F(basal) is prescribed by a physican and entred into the PCA pump by scanning the presription from the drug container label as it is loaded into the reservoir.'";
		System.out.println(SQLString);
		//MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, JoinUCStoECSWpredECMultiPrd);
		myAW.QueryToXML(SQLString, JoinUCStoECSWpredECMultiPrd);
	}
	
	private static void JoinUCStoECSWpredEC(QueryManager myAW){
		File JoinUCStoECSWpredEC = new File("./results/JoinUCStoECSWpredEC.xml"); 
		String SQLString =
/*				"SELECT  UCS.StepDescription, ECS.Description " +
				"FROM UCS, UCSAAEC, EC, ECAAECS, ECS " +
				"WHERE UCS.UseCaseStepID = UCSAAEC.UseCaseStepID AND " +
				"UCSAAEC.ExceptionCaseID = EC.ExceptionCaseID AND " +
				"EC.ExceptionCaseID = ECAAECS.ExceptionCaseID AND " +
				"ECAAECS.ExceptionCaseStepID = ECS.ExceptionCaseStepID";
*/
				"SELECT COUNT(*)" + " " +
				"FROM " + SD.UCS20kTableName + " " +
				"INNER JOIN " + SD.UCS_EC16kTableName + " " + 
				"ON " + SD.UCS20kTableName + ".USECASESTEPID = " + SD.UCS_EC16kTableName + ".USECASESTEPID " +
				"INNER JOIN " + SD.EC10kTableName + " " +
				"ON " + SD.UCS_EC16kTableName + ".ExceptionCaseID = " + SD.EC10kTableName + ".ExceptionCaseID " +
				"INNER JOIN " + SD.EC_ECS24kTableName + " " + 
				"ON " + SD.UCS_EC16kTableName + ".EXCEPTIONCASEID = " + SD.EC_ECS24kTableName + ".EXCEPTIONCASEID" + " " +
				"WHERE " + SD.EC10kTableName + ".PRIMARYACTOR = " + "'Patient'";
				//"WHERE " + SD.UCS20kTableName + ".STEPDESCRIPTION = " +  
				//"'The basal flow rate, F(basal) is prescribed by a physican and entred into the PCA pump by scanning the presription from the drug container label as it is loaded into the reservoir.'";
		System.out.println(SQLString);
		MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, JoinUCStoECSWpredEC);
		//myAW.QueryToXML(SQLString, JoinUCStoECSWpredEC);
	}
	
	private static void JoinUCStoECSWpredAbbr(QueryManager myAW){
		File JoinUCStoECSWpredAbbr = new File("./results/JoinUCStoECSWpredAbbr.xml"); 
		String SQLString =
				"SELECT COUNT(*)" + " " + 
				"FROM " + SD.UCS20kTableName + " " +
				"INNER JOIN " + SD.UCS_EC16kTableName + " " + 
				"ON " + SD.UCS_EC16kTableName + ".USECASESTEPID = " + SD.UCS20kTableName + ".USECASESTEPID " +
				"INNER JOIN " + SD.EC_ECS24kTableName + " " + 
				"ON " + SD.UCS_EC16kTableName + ".EXCEPTIONCASEID = " + SD.EC_ECS24kTableName + ".EXCEPTIONCASEID " +
				"WHERE " + SD.UCS20kTableName + ".STEPDESCRIPTION = " +  
				"'The basal flow rate, F(basal) is prescribed by a physican and entred into the PCA pump by scanning the presription from the drug container label as it is loaded into the reservoir.'";
		//System.out.println(SQLString);
		// MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, JoinUCStoECSWpredAbbr);
		myAW.QueryToXML(SQLString, JoinUCStoECSWpredAbbr);
	}	
	
	private static void JoinUCStoECSWpred(QueryManager myAW){
		File JoinUCStoECSWpred = new File("./results/JoinUCStoECSWpred.xml"); 
		String SQLString =
				"SELECT *" + " " + 
				"FROM " + SD.UCS20kTableName + " " +
				"INNER JOIN " + SD.UCS_EC16kTableName + " " + 
				"ON " + SD.UCS_EC16kTableName + ".USECASESTEPID = " + SD.UCS20kTableName + ".USECASESTEPID " +
				"INNER JOIN " + SD.EC10kTableName + " " + 
				"ON " + SD.EC10kTableName + ".EXCEPTIONCASEID = " + SD.UCS_EC16kTableName + ".EXCEPTIONCASEID " +
				"INNER JOIN " + SD.EC_ECS24kTableName + " " + 
				"ON " + SD.EC_ECS24kTableName + ".EXCEPTIONCASEID = " + SD.EC10kTableName + ".EXCEPTIONCASEID " +
				"INNER JOIN " + SD.ECS30kTableName + " " + 
				"ON " + SD.ECS30kTableName + ".EXCEPTIONCASESTEPID = " + SD.EC_ECS24kTableName + ".EXCEPTIONCASESTEPID " +
				"WHERE " + SD.UCS20kTableName + ".STEPDESCRIPTION = " +  
				"'The basal flow rate, F(basal) is prescribed by a physican and entred into the PCA pump by scanning the presription from the drug container label as it is loaded into the reservoir.'";
		//System.out.println(SQLString);
		MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, JoinUCStoECSWpred);
		//myAW.QueryToXML(SQLString, JoinUCStoECSWpred);
	}	
	// JOIN G to ECS
	private static void JoinUCStoECS(QueryManager myAW){
		File JoinGtoECS = new File("./results/JoinGtoECS.xml"); 
		String SQLString =
				"SELECT COUNT(*)" + " " + 
				"FROM " + SD.UCS20kTableName + " " +
				"INNER JOIN " + SD.UCS_EC16kTableName + " " + 
				"ON " + SD.UCS_EC16kTableName + ".USECASESTEPID = " + SD.UCS20kTableName + ".USECASESTEPID " +
				"INNER JOIN " + SD.EC10kTableName + " " + 
				"ON " + SD.EC10kTableName + ".EXCEPTIONCASEID = " + SD.UCS_EC16kTableName + ".EXCEPTIONCASEID " +
				"INNER JOIN " + SD.EC_ECS24kTableName + " " + 
				"ON " + SD.EC_ECS24kTableName + ".EXCEPTIONCASEID = " + SD.EC10kTableName + ".EXCEPTIONCASEID " +
				"INNER JOIN " + SD.ECS30kTableName + " " + 
				"ON " + SD.ECS30kTableName + ".EXCEPTIONCASESTEPID = " + SD.EC_ECS24kTableName + ".EXCEPTIONCASESTEPID";
/*		
		"INNER JOIN " + SD.G_UC8kTableName + " " + 
		"ON " + SD.G_UC8kTableName + ".GOALID = " + SD.G70TableName + ".GOALID " +
		"INNER JOIN " + SD.UC10kTableName + " " + 
		"ON " + SD.G_UC8kTableName + ".USECASEID = " + SD.UC10kTableName + ".USECASEID " +
		"INNER JOIN " + SD.UC_UCS15kTableName + " " + 
		"ON " + SD.UC_UCS15kTableName + ".USECASEID = " + SD.UC10kTableName + ".USECASEID " +
*/		
		MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, JoinGtoECS);
		//myAW.QueryToXML(SQLString, JoinGtoECS);
	}
	
	// JOIN G to ECS
	private static void JoinGtoECS(QueryManager myAW){
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
