package utils;

import java.io.File;

import ResourceStrings.SD;
import optimizer.QueryManager;

public class CreateTablesInMemory {


	public static void createTablesInMemory(QueryManager myAW){
	
	myAW.ImportSheet(SD.REQSheetTC1FP,SD.REQTableNameTC1);
	myAW.ImportSheet(SD.CCSheet5kFP,SD.CCTableName5k);
	myAW.ImportSheet(SD.REQSheetFP,SD.REQTableName);
	myAW.ImportSheet(SD.CCSheetFP,SD.CCTableName);
	myAW.ImportSheet(SD.CC10kFP, SD.CC10kTableName);
	myAW.ImportSheet(SD.CC_UCS16kFP, SD.CC_UCS16kTableName);		
	myAW.ImportSheet(SD.CC_SCP12kFP,SD.CC_SCP12kTableName);
	myAW.ImportSheet(SD.G70FP, SD.G70TableName);
	myAW.ImportSheet(SD.G_UC8kFP, SD.G_UC8kTableName);
	myAW.ImportSheet(SD.UC_UCS15kFP, SD.UC_UCS15kTableName);
	myAW.ImportSheet(SD.UCS20kFP, SD.UCS20kTableName);
	myAW.ImportSheet(SD.UCS_EC16kFP, SD.UCS_EC16kTableName);
	myAW.ImportSheet(SD.EC10kFP, SD.EC10kTableName);
	myAW.ImportSheet(SD.EC_ECS24kFP, SD.EC_ECS24kTableName);
	myAW.ImportSheet(SD.ECS30kFP, SD.ECS30kTableName);
	myAW.ImportSheet(SD.R70FP, SD.R70TableName);
	myAW.ImportSheet(SD.RQ_CP7kFP, SD.RQ_CP7kTableName);
	myAW.ImportSheet(SD.CP10kFP, SD.CP10kTableName);
	myAW.ImportSheet(SD.CP_SCP12kFP,SD.CP_SCP12kTableName);
	myAW.ImportSheet(SD.SCP15kFP,SD.SCP15kTableName);
	myAW.ImportSheet(SD.UC10kFP, SD.UC10kTableName);		
	
	// create TMTC1 in memory
	File TQ1 = new File("./results/TQ1.xml");
	CreateInMemoryTableMethod.createInMemoryTable(myAW, SD.TMTableName5k, TQ1);
	
	// create ReqTC1 in memory
	File TQ2 = new File("./results/TQ2.xml");
	CreateInMemoryTableMethod.createInMemoryTable(myAW, SD.REQTableNameTC1, TQ2);
	
	// create CCTableName5k in memory
	File TQ3 = new File("./results/TQ3.xml");
	CreateInMemoryTableMethod.createInMemoryTable(myAW, SD.CCTableName5k, TQ3);
	
	// create G70TableName in memory
	File TQ4 = new File("./results/TQ4.xml");
	CreateInMemoryTableMethod.createInMemoryTable(myAW, SD.G70TableName, TQ4);
	
	// create G_UC8kTableName in memory
	File TQ5 = new File("./results/TQ5.xml");
	CreateInMemoryTableMethod.createInMemoryTable(myAW, SD.G_UC8kTableName, TQ5);
	
	// create UC10kTableName in memory
	File TQ6 = new File("./results/TQ6.xml");
	CreateInMemoryTableMethod.createInMemoryTable(myAW, SD.UC10kTableName, TQ6);
	
	// create UC_UCS15kTableName in memory
	File TQ7 = new File("./results/TQ7.xml");
	CreateInMemoryTableMethod.createInMemoryTable(myAW, SD.UC_UCS15kTableName, TQ7);
	
	// create UCS20kTableName in memory
	File TQ8 = new File("./results/TQ8.xml");
	CreateInMemoryTableMethod.createInMemoryTable(myAW, SD.UCS20kTableName, TQ8);
	
	// create CC_UCS16kTableName in memory
	File TQ9 = new File("./results/TQ9.xml");
	CreateInMemoryTableMethod.createInMemoryTable(myAW, SD.CC_UCS16kTableName, TQ9);
	
	// create UCS_EC16kTableName in memory
	File TQ10 = new File("./results/TQ10.xml");
	CreateInMemoryTableMethod.createInMemoryTable(myAW, SD.UCS_EC16kTableName, TQ10);	
	
	// create CC10kTableName in memory
	File TQ11 = new File("./results/TQ11.xml");
	CreateInMemoryTableMethod.createInMemoryTable(myAW, SD.CC10kTableName, TQ11);	
		
	// create EC10kTableName in memory
	File TQ12 = new File("./results/TQ12.xml");
	CreateInMemoryTableMethod.createInMemoryTable(myAW, SD.EC10kTableName, TQ12);
	
	// create EC10kTableName in memory
	File TQ13 = new File("./results/TQ13.xml");
	CreateInMemoryTableMethod.createInMemoryTable(myAW, SD.EC_ECS24kTableName, TQ13);

	// create ECS30kTableName in memory
	File TQ14 = new File("./results/TQ14.xml");
	CreateInMemoryTableMethod.createInMemoryTable(myAW, SD.ECS30kTableName, TQ14);
	
	// create CC_SCP12kTableName in memory
	File TQ15 = new File("./results/TQ15.xml");
	CreateInMemoryTableMethod.createInMemoryTable(myAW, SD.CC_SCP12kTableName, TQ15);

	// create SCP15kTableName in memory
	File TQ16 = new File("./results/TQ16.xml");
	CreateInMemoryTableMethod.createInMemoryTable(myAW, SD.SCP15kTableName, TQ16);

	// create CP_SCP12kTableName in memory
	File TQ17 = new File("./results/TQ17.xml");
	CreateInMemoryTableMethod.createInMemoryTable(myAW, SD.CP_SCP12kTableName, TQ17);
	
	// create CP10kTableName in memory
	File TQ18 = new File("./results/TQ18.xml");
	CreateInMemoryTableMethod.createInMemoryTable(myAW, SD.CP10kTableName, TQ18);
	
	// create RQ_CP7kTableName in memory
	File TQ19 = new File("./results/TQ19.xml");
	CreateInMemoryTableMethod.createInMemoryTable(myAW, SD.RQ_CP7kTableName, TQ19);
	
	// create R70TableName in memory
	File TQ20 = new File("./results/TQ20.xml");
	CreateInMemoryTableMethod.createInMemoryTable(myAW, SD.R70TableName, TQ20);
	
	// create REQTableName in memory
	File TQ21 = new File("./results/TQ21.xml");
	CreateInMemoryTableMethod.createInMemoryTable(myAW, SD.REQTableName, TQ21);
	
	// create CCTableName in memory
	File TQ22 = new File("./results/TQ22.xml");
	CreateInMemoryTableMethod.createInMemoryTable(myAW, SD.CCTableName, TQ22);
	
	}
}

