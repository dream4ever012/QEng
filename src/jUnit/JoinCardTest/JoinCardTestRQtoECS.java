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
		CreateTablesInMemory.createTablesInMemoryGtoECSCJS(myAW);
		// create link for 
		CreateTablesInMemory.registerTMGtoECS(myAW);
		CreateTablesInMemory.registerTMGtoECSCJS(myAW);
		setupIsDone = true;
		}
	}
	@Test
	public void test() {
		// 1) ((G-UCS) -EC)-ECS: 2498.4
		// JoinG_UCS(myAW); // JoinG_UCS.xml cost: 197 243 280 250 163 )=> 226.6
		// JoinG_UCS__EC(myAW); // JoinG_UCS_EC.xml cost: 229 212 248 279 322 )=> 258
		// JoinG_EC__ECS(myAW); // JoinG_UCS__EC.xml cost: 1897 2267 1828 1755 2322 )=> 2013.8
		
		// 2) (G-(UC-EC))-ECS: 2426.4
		// JoinUC_EC(myAW); // JoinUC_EC.xml cost: 258 210 242 220 195 )=> 225
		// JoinG__UC_EC(myAW); // JoinG__UC_EC.xml cost: 171 211 209 170 177 )=> 187.6
		// JoinG_EC__ECS(myAW); // JoinG_UCS__EC.xml cost: 1897 2267 1828 1755 2322 )=> 2013.8
		
		// 3) (G-(UC-(UCS-ECS))): 5591.6
		// JoinUCS_ECS(myAW); // JoinUCS_ECS.xml cost: 6233 5216 3638 4188 4049 )=> 4664.8
		// JoinUC__UCS_ECS(myAW); // JoinUC__UCS_ECS.xml cost: 341 360 547 603 327 )=> 435.6
		// JoinG__UC_ECS(myAW); // JoinUC__UCS_ECS.xml cost: 639 763 373 331 350 )=> 491.2
		
		
	}
	
	// UC - (UCS-ECS)
	private static void JoinG__UC_ECS(QueryManager myAW){	
		File JoinUC__UCS_ECS = new File("./results/JoinUC__UCS_ECS.xml"); 
		String SQLString =
				"SELECT " + SD.G_UC8kTableName + ".GOALID" + ", " +  SD.UC_ECS65kTableName + ".EXCEPTIONCASESTEPID" + " " +
				"FROM " + SD.G_UC8kTableName + " " +
				"INNER JOIN " + SD.UC_ECS65kTableName + " " + 
				"ON " + SD.UC_ECS65kTableName + ".USECASEID = " + SD.G_UC8kTableName + ".USECASEID"; // + " " +
		//System.out.println(SQLString);
		MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, JoinUC__UCS_ECS);
		//myAW.QueryToXML(SQLString, JoinUC__UCS_ECS);
	}
	
	// UC - (UCS-ECS)
	private static void JoinUC__UCS_ECS(QueryManager myAW){	
		File JoinUC__UCS_ECS = new File("./results/JoinUC__UCS_ECS.xml"); 
		String SQLString =
				"SELECT " + SD.UC_UCS15kTableName + ".USECASEID" + ", " +  SD.UCS_ECS65kTableName + ".EXCEPTIONCASESTEPID" + " " +
				"FROM " + SD.UC_UCS15kTableName + " " +
				"INNER JOIN " + SD.UCS_ECS65kTableName + " " + 
				"ON " + SD.UCS_ECS65kTableName + ".USECASESTEPID = " + SD.UC_UCS15kTableName + ".USECASESTEPID"; // + " " +
		//System.out.println(SQLString);
		MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, JoinUC__UCS_ECS);
		//myAW.QueryToXML(SQLString, JoinUC__UCS_ECS);
	}
	
	// G-(UC-EC)
	private static void JoinG__UC_EC(QueryManager myAW){	
		File JoinG__UC_EC = new File("./results/JoinG__UC_EC.xml"); 
		String SQLString =
				//"SELECT COUNT (*)" + " " +
				//"FROM (" +
				"SELECT " + SD.G_UC8kTableName+ ".GOALID" + ", " +  SD.UC_EC12kTableName + ".EXCEPTIONCASEID" + " " +
				"FROM " + SD.G_UC8kTableName + " " +
				"INNER JOIN " + SD.UC_EC12kTableName + " " + 
				"ON " + SD.UC_EC12kTableName + ".USECASEID = " + SD.G_UC8kTableName + ".USECASEID";// + 
				//") " + "AS UCaaEC";
		//System.out.println(SQLString);
		MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, JoinG__UC_EC);
		//myAW.QueryToXML(SQLString, JoinG__UC_EC);
	}
	
	
	// JOIN (G to Ec) to ECS
	private static void JoinG_EC__ECS(QueryManager myAW){	
		File JoinG_UCS__EC = new File("./results/JoinG_UCS__EC.xml"); 
		String SQLString =
				//"SELECT COUNT (*)" + " " +
				//"FROM (" +
				"SELECT " + SD.G_EC10kTableName + ".GOALID" + ", " +  SD.EC_ECS24kTableName + ".EXCEPTIONCASESTEPID" + " " +
				"FROM " + SD.G_EC10kTableName + " " +
				"INNER JOIN " + SD.EC_ECS24kTableName + " " + 
				"ON " + SD.EC_ECS24kTableName + ".EXCEPTIONCASEID = " + SD.G_EC10kTableName + ".EXCEPTIONCASEID";// + 
				//") " + "AS GaaUCS";
		//System.out.println(SQLString);
		MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, JoinG_UCS__EC);
		//myAW.QueryToXML(SQLString, JoinG_UCS__EC);
	}
	
	// JOIN (G to UCS) to EC
	private static void JoinG_UCS__EC(QueryManager myAW){	
		File JoinG_UCS__EC = new File("./results/JoinG_UCS__EC.xml"); 
		String SQLString =
				//"SELECT COUNT (*)" + " " +
				//"FROM (" +
				"SELECT " + SD.G_UCS12kTableName + ".GOALID" + ", " +  SD.UCS_EC16kTableName + ".EXCEPTIONCASEID" + " " +
				"FROM " + SD.G_UCS12kTableName + " " +
				"INNER JOIN " + SD.UCS_EC16kTableName + " " + 
				"ON " + SD.UCS_EC16kTableName + ".USECASESTEPID = " + SD.G_UCS12kTableName + ".USECASESTEPID";// + 
				//") " + "AS GaaUCS";
		//System.out.println(SQLString);
		MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, JoinG_UCS__EC);
		//myAW.QueryToXML(SQLString, JoinG_UCS__EC);
	}
	
	// JOIN G to UCS to write a CSV
	private static void JoinG_UCS(QueryManager myAW){	
		File JoinG_UCS = new File("./results/JoinG_UCS.xml"); 
		String SQLString =
				//"SELECT COUNT (*)" + " " +
				//"FROM (" +
				"SELECT " + SD.G_UC8kTableName + ".GOALID" + ", " +  SD.UC_UCS15kTableName + ".USECASEID" + " " +
				"FROM " + SD.G_UC8kTableName + " " +
				"INNER JOIN " + SD.UC_UCS15kTableName + " " + 
				"ON " + SD.G_UC8kTableName + ".USECASEID = " + SD.UC_UCS15kTableName + ".USECASEID";// + 
				//") " + "AS GaaUCS";
		//System.out.println(SQLString);
		MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, JoinG_UCS);
		//myAW.QueryToXML(SQLString, JoinG_UCS);
	}
	
	// JOIN UC to EC to write a CSV
	private static void JoinUC_EC(QueryManager myAW){	
		File JoinUC_EC = new File("./results/JoinUC_EC.xml"); 
		String SQLString =
				"SELECT " + SD.UC_UCS15kTableName + ".USECASEID" + ", " +  SD.UCS_EC16kTableName + ".EXCEPTIONCASEID" + " " +
				"FROM " + SD.UC_UCS15kTableName + " " +
				"INNER JOIN " + SD.UCS_EC16kTableName + " " + 
				"ON " + SD.UCS_EC16kTableName + ".USECASESTEPID = " + SD.UC_UCS15kTableName + ".USECASESTEPID";// + 
		//System.out.println(SQLString);
		MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, JoinUC_EC);
		//myAW.QueryToXML(SQLString, JoinUC_EC);
	}
	
	// JOIN UCS to ECS to write a CSV
	private static void JoinUCS_ECS(QueryManager myAW){	
		File JoinUCS_ECS = new File("./results/JoinUCS_ECS.xml"); 
		String SQLString =
				"SELECT " + SD.UCS_EC16kTableName + ".USECASESTEPID" + ", " +  SD.EC_ECS24kTableName + ".EXCEPTIONCASESTEPID" + " " +
				"FROM " + SD.UCS_EC16kTableName + " " +
				"INNER JOIN " + SD.EC_ECS24kTableName + " " + 
				"ON " + SD.EC_ECS24kTableName + ".EXCEPTIONCASEID = " + SD.UCS_EC16kTableName + ".EXCEPTIONCASEID"; // + " " +
		//System.out.println(SQLString);
		MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, JoinUCS_ECS);
		//myAW.QueryToXML(SQLString, JoinUCS_ECS);
	}
	
	// JOIN UC to ECS to write a CSV
	private static void JoinUC_ECS(QueryManager myAW){	
		File JoinUC_ECS = new File("./results/JoinUC_ECS.xml"); 
		String SQLString =
				"SELECT " + SD.UC_UCS15kTableName + ".USECASEID" + ", " +  SD.EC_ECS24kTableName + ".EXCEPTIONCASESTEPID" + " " +
				"FROM " + SD.UC_UCS15kTableName + " " +
				"INNER JOIN " + SD.UCS_EC16kTableName + " " + 
				"ON " + SD.UCS_EC16kTableName + ".USECASESTEPID = " + SD.UC_UCS15kTableName + ".USECASESTEPID" + " " +
				"INNER JOIN " + SD.EC_ECS24kTableName + " " + 
				"ON " + SD.EC_ECS24kTableName + ".EXCEPTIONCASEID = " + SD.UCS_EC16kTableName + ".EXCEPTIONCASEID"; // + " " +
		System.out.println(SQLString);
		//MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, JoinUC_ECS);
		myAW.QueryToXML(SQLString, JoinUC_ECS);
		//myAW.WriteCSV("./ThirdData/UCaaECS.csv", SQLString);
	}

	
	// JOIN G to EC to write a CSV
	private static void JoinG_EC(QueryManager myAW){	
		File JoinG_EC = new File("./results/JoinG_EC.xml"); 
		String SQLString =
				"SELECT " + SD.G_UC8kTableName + ".GOALID" + ", " +  SD.UCS_EC16kTableName + ".EXCEPTIONCASEID" + " " +
				"FROM " + SD.G_UC8kTableName + " " +
				"INNER JOIN " + SD.UC_UCS15kTableName + " " + 
				"ON " + SD.UC_UCS15kTableName + ".USECASEID = " + SD.G_UC8kTableName + ".USECASEID " +
				"INNER JOIN " + SD.UCS_EC16kTableName + " " + 
				"ON " + SD.UCS_EC16kTableName + ".USECASESTEPID = " + SD.UC_UCS15kTableName + ".USECASESTEPID";
		System.out.println(SQLString);
		//MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, JoinG_EC);
		//myAW.QueryToXML(SQLString, JoinG_EC);
		myAW.WriteCSV("./ThirdData/GaaEC.csv", SQLString);
	}
	

	


	// JOIN G to ECS
	private static void JoinUCtoECS(QueryManager myAW){	
		File JoinUCtoECS = new File("./results/JoinUCtoECS.xml"); 
		String SQLString =
				"SELECT " + SD.UC_UCS15kTableName + ".USECASEID" + ", " +
						SD.EC_ECS24kTableName + ".EXCEPTIONCASESTEPID" + " " +
				"FROM " + SD.UC_UCS15kTableName + " " +
				"INNER JOIN " + SD.UCS_EC16kTableName + " " + 
				"ON " + SD.UCS_EC16kTableName + ".USECASESTEPID = " + SD.UC_UCS15kTableName + ".USECASESTEPID " +
				"INNER JOIN " + SD.EC_ECS24kTableName + " " + 
				"ON " + SD.EC_ECS24kTableName + ".EXCEPTIONCASEID = " + SD.UCS_EC16kTableName + ".EXCEPTIONCASEID"; // + " " +
		//MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, JoinUCtoECS);
		//myAW.QueryToXML(SQLString, JoinUCtoECS);
		myAW.WriteCSV("./ThirdData/UCaaECS.csv", SQLString);
	}
	
	private static void JoinUCSaaECS(QueryManager myAW){
		File JoinUCSaaECS = new File("./results/JoinUCSaaECS.xml"); 
		String SQLString =
				"SELECT " + SD.UCS_EC16kTableName + ".USECASESTEPID" + ", " +
							SD.EC_ECS24kTableName + ".EXCEPTIONCASESTEPID" + " " +
				"FROM " + SD.UCS_EC16kTableName + " " +
				"INNER JOIN " + SD.EC_ECS24kTableName + " " + 
				"ON " + SD.UCS_EC16kTableName + ".EXCEPTIONCASEID = " + SD.EC_ECS24kTableName + ".EXCEPTIONCASEID";// +
		//MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, JoinUCSaaECS);
		//myAW.QueryToXML(SQLString, JoinUCSaaECS);
		myAW.WriteCSV("./ThirdData/UCSaaECS.csv", SQLString);
	}

	// JOIN G to ECS to write a CSV
	private static void JoinGtoECS(QueryManager myAW){	
		File JoinGtoECS = new File("./results/JoinGtoECS.xml"); 
		String SQLString =
				"SELECT " + SD.G_UC8kTableName + ".GOALID" + ", " + SD.EC_ECS24kTableName + ".EXCEPTIONCASESTEPID" + " " + 
				"FROM " + SD.G_UC8kTableName + " " +
				"INNER JOIN " + SD.UC_UCS15kTableName + " " + 
				"ON " + SD.UC_UCS15kTableName + ".USECASEID = " + SD.G_UC8kTableName + ".USECASEID " +
				"INNER JOIN " + SD.UCS_EC16kTableName + " " + 
				"ON " + SD.UCS_EC16kTableName + ".USECASESTEPID = " + SD.UC_UCS15kTableName + ".USECASESTEPID " +
				"INNER JOIN " + SD.EC_ECS24kTableName + " " + 
				"ON " + SD.EC_ECS24kTableName + ".EXCEPTIONCASEID = " + SD.UCS_EC16kTableName + ".EXCEPTIONCASEID"; // + " " +
		//MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, JoinGtoECS);
		//myAW.QueryToXML(SQLString, JoinGtoECS);
		myAW.WriteCSV("./ThirdData/GaaECS.csv", SQLString);
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
