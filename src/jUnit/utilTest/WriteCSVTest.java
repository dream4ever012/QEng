package jUnit.utilTest;

import java.io.File;
import org.junit.Before;
import org.junit.Test;

import ResourceStrings.SD;
import optimizer.AskWise;
import optimizer.QueryManager;
import qEng.InternalH2;
import utils.CreateTablesInMemory;
import utils.MeasureCostArbitrary;

public class WriteCSVTest {
	public static Boolean setupIsDone = false;
	private static final String H2PROTO = "jdbc:h2:";
	private static final String IH2FP = "./Data/WriteCSVTest/";
	private static final String IH2DBName = "WriteCSVTest";
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
		//myOAW = new AskWise(new ExternalOracle());
		
		//CreateTablesInMemory.createTablesInMemoryGtoECS(myAW);
		//CreateTablesInMemory.registerTMGtoECS(myAW);
/*		File CSVWriter = new File("./results/CSVWriter.xml");
		SQLString =
				"DROP TABLE T1 IF EXISTS;" + " " +
				"CREATE TABLE T1 (ID VARCHAR2(10) PRIMARY KEY, VAL NUMBER);" + " " +
				"INSERT INTO T1 VALUES('A', 100);" + " " +
				"INSERT INTO T1 VALUES('B', 100);" + " " +
				"INSERT INTO T1 VALUES('C', 100);" + " " +
				"INSERT INTO T1 VALUES('D', 100);" + " " +
				"INSERT INTO T1 VALUES('E', 100)";		
		MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, CSVWriter);*/
/*		
		File CSVWriterT2 = new File("./results/CSVWriterT2.xml");
		SQLString =
				"DROP TABLE T2 IF EXISTS;" + " " +
				"CREATE TABLE T2 (ID VARCHAR2(10), VAL NUMBER);" + " " +
				"INSERT INTO T2 VALUES('A', 100);" + " " +
				"INSERT INTO T2 VALUES('A', 100);" + " " +
				"INSERT INTO T2 VALUES('A', 100);" + " " +
				"INSERT INTO T2 VALUES('A', 100);" + " " +
				"INSERT INTO T2 VALUES('A', 100);" + " " +
				"INSERT INTO T2 VALUES('B', 100);" + " " +
				"INSERT INTO T2 VALUES('B', 100);" + " " +
				"INSERT INTO T2 VALUES('C', 100);" + " " +
				"INSERT INTO T2 VALUES('C', 100);" + " " +
				"INSERT INTO T2 VALUES('C', 100);" + " " +
				"INSERT INTO T2 VALUES('D', 100);" + " " +
				"INSERT INTO T2 VALUES('D', 100);" + " " +
				"INSERT INTO T2 VALUES('D', 100);" + " " +
				"INSERT INTO T2 VALUES('D', 100);" + " " +
				"INSERT INTO T2 VALUES('E', 100)";		
		MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, CSVWriterT2);
		
	*/	
		// create tablelink
		CreateTablesInMemory.createTablesInMemoryGtoECS(myAW);
		// create link for 
		CreateTablesInMemory.registerTMGtoECS(myAW);
		setupIsDone = true;
		}
	}
	@Test
	public void test() {
		
		// CSVWriter.xml cost: 17 works!
		//myAW.WriteCSV("./results/WriteCSVTest/T1.csv", "SELECT * FROM T1;");
		//myAW.WriteCSV("./results/WriteCSVTest/T2.csv", "SELECT * FROM T2;");		
		//JoinUCSaaECjoinECaaECS(myAW);
		//JoinUCtoECSWPred(myAW); // card 73322
		
		
		
		//read CSV trace matrix
		//String ArbSQL = "DROP TABLE "+ SD.TMTableName +" IF EXISTS; CREATE TABLE "+ SD.TMTableName +" AS SELECT * FROM CSVREAD('./Data/CC-REQ-TM.csv');";
		//myAW.arbitrarySQL(ArbSQL);
		
		
		//JoinG_UCS(myAW); // card 12715 
		// JoinUCS_ECS(myAW); // 362219
		//JoinUC_ECS(myAW); // 12623
		//JoinG_EC(myAW); // 10153
		//JoinUC_ECS(myAW); // 292995
		
		JoinG_UCS(myAW);
		//JoinUC_EC(myAW);
		//JoinUCS_ECS(myAW);
		
		//JoinG_EC(myAW);
		//JoinUCtoECS(myAW);
		
		//JoinGtoECS(myAW);
		
		
		
	}
	
	
	private static void JoinRtoECS(QueryManager myAW){	
		File JoinRQtoECS = new File("./results/JoinRQtoECS.xml"); 
		String SQLString =
				"SELECT " + SD.RQ_CP7kTableName + ".ID" + ", " +
						SD.EC_ECS24kTableName + ".EXCEPTIONCASESTEPID" + " " +
				"FROM " + SD.RQ_CP7kTableName + " " +
				"INNER JOIN " + SD.CP_SCP12kTableName  + " " + 
				"ON " + SD.CP_SCP12kTableName + ".COMPONENTID = " + SD.RQ_CP7kTableName + ".COMPONENTID " +
				"INNER JOIN " + SD.SCP_CC12kTableName + " " + 
				"ON " + SD.SCP_CC12kTableName + ".SUBCOMPONENTID = " + SD.CP_SCP12kTableName + ".SUBCOMPONENTID " +
				
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
		MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, JoinRQtoECS);
		//myAW.QueryToXML(SQLString, JoinRQtoECS);
	}

	
	// JOIN UCS to ECS to write a CSV
	private static void JoinUCS_ECS(QueryManager myAW){	
		File JoinUCS_ECS = new File("./results/JoinUCS_ECS.xml"); 
		String SQLString =
				//"SELECT COUNT (*)" + " " +
				//"FROM (" +
				"SELECT " + SD.UCS_EC16kTableName + ".USECASESTEPID" + ", " +  SD.EC_ECS24kTableName + ".EXCEPTIONCASESTEPID" + " " +
				"FROM " + SD.UCS_EC16kTableName + " " +
				"INNER JOIN " + SD.EC_ECS24kTableName + " " + 
				"ON " + SD.EC_ECS24kTableName + ".EXCEPTIONCASEID = " + SD.UCS_EC16kTableName + ".EXCEPTIONCASEID"; // + " " +
				//") " + "AS UCSaaECS";
		System.out.println(SQLString);
		//MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, JoinUCS_ECS);
		//myAW.QueryToXML(SQLString, JoinUCS_ECS);
		myAW.WriteCSV("./ThirdData/UCSaaECS.csv", SQLString);
	}

	
	// JOIN UC to ECS to write a CSV
	private static void JoinUC_ECS(QueryManager myAW){	
		File JoinUC_ECS = new File("./results/JoinUC_ECS.xml"); 
		String SQLString =
				//"SELECT COUNT (*)" + " " +
				//"FROM (" +
				"SELECT " + SD.UC_UCS15kTableName + ".USECASEID" + ", " +  SD.EC_ECS24kTableName + ".EXCEPTIONCASESTEPID" + " " +
				"FROM " + SD.UC_UCS15kTableName + " " +
				"INNER JOIN " + SD.UCS_EC16kTableName + " " + 
				"ON " + SD.UCS_EC16kTableName + ".USECASESTEPID = " + SD.UC_UCS15kTableName + ".USECASESTEPID" + " " +
				"INNER JOIN " + SD.EC_ECS24kTableName + " " + 
				"ON " + SD.EC_ECS24kTableName + ".EXCEPTIONCASEID = " + SD.UCS_EC16kTableName + ".EXCEPTIONCASEID"; // + " " +
				//") " + "AS UCaaECS";
		System.out.println(SQLString);
		//MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, JoinUC_ECS);
		myAW.QueryToXML(SQLString, JoinUC_ECS);
		//myAW.WriteCSV("./ThirdData/UCaaECS.csv", SQLString);
	}

	
	// JOIN G to EC to write a CSV
	private static void JoinG_EC(QueryManager myAW){	
		File JoinG_EC = new File("./results/JoinG_EC.xml"); 
		String SQLString =
				//"SELECT COUNT (*)" + " " +
				//"FROM (" +
				"SELECT " + SD.G_UC8kTableName + ".GOALID" + ", " +  SD.UCS_EC16kTableName + ".EXCEPTIONCASEID" + " " +
				"FROM " + SD.G_UC8kTableName + " " +
				"INNER JOIN " + SD.UC_UCS15kTableName + " " + 
				"ON " + SD.UC_UCS15kTableName + ".USECASEID = " + SD.G_UC8kTableName + ".USECASEID " +
				"INNER JOIN " + SD.UCS_EC16kTableName + " " + 
				"ON " + SD.UCS_EC16kTableName + ".USECASESTEPID = " + SD.UC_UCS15kTableName + ".USECASESTEPID";
				//") " + "AS GaaEC";
	
		System.out.println(SQLString);
		//MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, JoinG_EC);
		//myAW.QueryToXML(SQLString, JoinG_EC);
		myAW.WriteCSV("./ThirdData/GaaEC.csv", SQLString);
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
		//MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, JoinUC_EC);
		//myAW.QueryToXML(SQLString, JoinUC_EC);
		//myAW.WriteCSV("./ThirdData/UCaaEC.csv", SQLString);
	}
	
	// JOIN UC to ECS to write a CSV
	private static void JoinG_UCS(QueryManager myAW){	
		File JoinG_UCS = new File("./results/JoinG_UCS.xml"); 
		String SQLString =
				//"SELECT COUNT (*)" + " " +
				//"FROM (" +
				"SELECT " + SD.G_UC8kTableName + ".GOALID" + ", " +  SD.UC_UCS15kTableName + ".USECASESTEPID" + " " +
				"FROM " + SD.G_UC8kTableName + " " +
				"INNER JOIN " + SD.UC_UCS15kTableName + " " + 
				"ON " + SD.G_UC8kTableName + ".USECASEID = " + SD.UC_UCS15kTableName + ".USECASEID";// + 
				//") " + "AS GaaUCS";

		//System.out.println(SQLString);
		//MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, JoinG_UCS);
		//myAW.QueryToXML(SQLString, JoinG_UCS);
		myAW.WriteCSV("./ThirdData/GaaUCS.csv", SQLString);
	}

	// JOIN G to ECS
	private static void JoinUCtoECS(QueryManager myAW){	
		File JoinUCtoECS = new File("./results/JoinUCtoECS.xml"); 
		String SQLString =
				//"SELECT COUNT(*)" + " " +
				//"FROM (" + 
				"SELECT " + SD.UC_UCS15kTableName + ".USECASEID" + ", " +
						SD.EC_ECS24kTableName + ".EXCEPTIONCASESTEPID" + " " +
				"FROM " + SD.UC_UCS15kTableName + " " +
				"INNER JOIN " + SD.UCS_EC16kTableName + " " + 
				"ON " + SD.UCS_EC16kTableName + ".USECASESTEPID = " + SD.UC_UCS15kTableName + ".USECASESTEPID " +
				"INNER JOIN " + SD.EC_ECS24kTableName + " " + 
				"ON " + SD.EC_ECS24kTableName + ".EXCEPTIONCASEID = " + SD.UCS_EC16kTableName + ".EXCEPTIONCASEID"; // + " " +
				//") " + "AS UCaaECS";
		//MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, JoinUCtoECS);
		//myAW.QueryToXML(SQLString, JoinUCtoECS);
		myAW.WriteCSV("./ThirdData/UCaaECS.csv", SQLString);
	}
	
	private static void JoinUCSaaECS(QueryManager myAW){
		File JoinUCSaaECS = new File("./results/JoinUCSaaECS.xml"); 
		String SQLString =
				//"SELECT COUNT(*)" + " " +
				"SELECT " + SD.UCS_EC16kTableName + ".USECASESTEPID" + ", " +
							SD.EC_ECS24kTableName + ".EXCEPTIONCASESTEPID" + " " +
				"FROM " + SD.UCS_EC16kTableName + " " +
				"INNER JOIN " + SD.EC_ECS24kTableName + " " + 
				"ON " + SD.UCS_EC16kTableName + ".EXCEPTIONCASEID = " + SD.EC_ECS24kTableName + ".EXCEPTIONCASEID";// +
/*		assertTrue("failure " + JoinUCSaaECjoinECaaECS.getName().toString() , 
		MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, JoinUCSaaECS) >= 30.0);
*/		
		//MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, JoinUCSaaECS);
		//myAW.QueryToXML(SQLString, JoinUCSaaECS);
		myAW.WriteCSV("./ThirdData/UCSaaECS.csv", SQLString);
	}

	// JOIN G to ECS to write a CSV
	private static void JoinGtoECS(QueryManager myAW){	
		File JoinGtoECS = new File("./results/JoinGtoECS.xml"); 
		String SQLString =
				//"SELECT COUNT (*)" + " " +
				//"FROM (" +
				"SELECT " + SD.G_UC8kTableName + ".GOALID" + ", " + SD.EC_ECS24kTableName + ".EXCEPTIONCASESTEPID" + " " + 
				"FROM " + SD.G_UC8kTableName + " " +
				"INNER JOIN " + SD.UC_UCS15kTableName + " " + 
				"ON " + SD.UC_UCS15kTableName + ".USECASEID = " + SD.G_UC8kTableName + ".USECASEID " +
				"INNER JOIN " + SD.UCS_EC16kTableName + " " + 
				"ON " + SD.UCS_EC16kTableName + ".USECASESTEPID = " + SD.UC_UCS15kTableName + ".USECASESTEPID " +
				"INNER JOIN " + SD.EC_ECS24kTableName + " " + 
				"ON " + SD.EC_ECS24kTableName + ".EXCEPTIONCASEID = " + SD.UCS_EC16kTableName + ".EXCEPTIONCASEID"; // + " " +
				//") " + "AS GaaECS";
		//MeasureCostArbitrary.measureCostArbitrary(myAW, SQLString, JoinGtoECS);
		//myAW.QueryToXML(SQLString, JoinGtoECS);
		myAW.WriteCSV("./ThirdData/GaaECS.csv", SQLString);
	}


	// JOIN G to ECS to write a CSV
	private static void JoinGtoECS1(QueryManager myAW){	
		File JoinGtoECS = new File("./results/JoinGtoECS.xml"); 
		String SQLString =
				"SELECT " + SD.G70TableName + ".GOALID" + " " + 
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
