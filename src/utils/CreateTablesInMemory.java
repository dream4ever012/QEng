package utils;

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
	}
		
	public static void createTablesInMemoryGtoECS(QueryManager myAW){
		myAW.ImportSheet(SD.G70FP, SD.G70TableName);
		myAW.ImportSheet(SD.G_UC8kFP, SD.G_UC8kTableName);
		myAW.ImportSheet(SD.UC10kFP, SD.UC10kTableName);
		myAW.ImportSheet(SD.UC_UCS15kFP, SD.UC_UCS15kTableName);
		myAW.ImportSheet(SD.UCS20kFP, SD.UCS20kTableName);
		myAW.ImportSheet(SD.UCS_EC16kFP, SD.UCS_EC16kTableName);
		myAW.ImportSheet(SD.EC10kFP, SD.EC10kTableName);
		myAW.ImportSheet(SD.EC_ECS24kFP, SD.EC_ECS24kTableName);
		myAW.ImportSheet(SD.ECS30kFP, SD.ECS30kTableName);
		System.out.println("Table created: GtoECS");
	}
	
	public static void registerTMGtoECS(QueryManager myAW){
		myAW.RegisterTM(SD.G_UC8kTableName, SD.G70TableName, "GOALID", SD.UC10kTableName, "USECASEID");
		myAW.RegisterTM(SD.UC_UCS15kTableName, SD.UC10kTableName, "USECASEID", SD.UCS20kTableName, "USECASESTEPID");
		myAW.RegisterTM(SD.UCS_EC16kTableName, SD.UCS20kTableName, "USECASESTEPID", SD.EC10kTableName, "EXCEPTIONCASEID");
		myAW.RegisterTM(SD.EC_ECS24kTableName, SD.EC10kTableName, "EXCEPTIONCASEID", SD.ECS30kTableName, "EXCEPTIONCASESTEPID");
		System.out.println("Index created: GtoECS");
	}

	
	public static void createTablesInMemoryGtoRQ(QueryManager myAW){
		myAW.ImportSheet(SD.G70FP, SD.G70TableName);
		myAW.ImportSheet(SD.G_UC8kFP, SD.G_UC8kTableName); //
		myAW.ImportSheet(SD.UC10kFP, SD.UC10kTableName);
		myAW.ImportSheet(SD.UC_UCS15kFP, SD.UC_UCS15kTableName); //
		myAW.ImportSheet(SD.UCS20kFP, SD.UCS20kTableName);
		myAW.ImportSheet(SD.R70FP, SD.R70TableName);
		myAW.ImportSheet(SD.RQ_CP7kFP, SD.RQ_CP7kTableName); //
		myAW.ImportSheet(SD.CP10kFP, SD.CP10kTableName);
		myAW.ImportSheet(SD.CP_SCP12kFP,SD.CP_SCP12kTableName); //
		myAW.ImportSheet(SD.SCP15kFP,SD.SCP15kTableName);
		myAW.ImportSheet(SD.CC10kFP, SD.CC10kTableName);
		myAW.ImportSheet(SD.CC_UCS16kFP, SD.CC_UCS16kTableName); //		
		myAW.ImportSheet(SD.CC_SCP12kFP,SD.CC_SCP12kTableName); //
		System.out.println("Table created: GtoRQ");
	}

	public static void registerTMGtoRQ(QueryManager myAW){
		myAW.RegisterTM(SD.G_UC8kTableName, SD.G70TableName, "GOALID", SD.UC10kTableName, "USECASEID");
		myAW.RegisterTM(SD.UC_UCS15kTableName, SD.UC10kTableName, "USECASEID", SD.UCS20kTableName, "USECASESTEPID");
		myAW.RegisterTM(SD.RQ_CP7kTableName, SD.R70TableName, "ID", SD.CP10kTableName, "COMPONENTID");
		myAW.RegisterTM(SD.CP_SCP12kTableName, SD.CP10kTableName, "COMPONENTID", SD.SCP15kTableName, "SUBCOMPONENTID");
		myAW.RegisterTM(SD.CC_SCP12kTableName, SD.CC10kTableName, "CLASSNAME", SD.SCP15kTableName, "SUBCOMPONENTID");
		myAW.RegisterTM(SD.CC_UCS16kTableName, SD.CC10kTableName, "CLASSNAME", SD.UCS20kTableName, "USECASESTEPID");
		System.out.println("Index created: GtoRQ");
	}
	
	public static void createTablesInMemoryRQtoECS(QueryManager myAW){
		myAW.ImportSheet(SD.R70FP, SD.R70TableName);
		myAW.ImportSheet(SD.RQ_CP7kFP, SD.RQ_CP7kTableName); //
		myAW.ImportSheet(SD.CP10kFP, SD.CP10kTableName);
		myAW.ImportSheet(SD.CP_SCP12kFP,SD.CP_SCP12kTableName); //
		myAW.ImportSheet(SD.SCP15kFP,SD.SCP15kTableName);
		myAW.ImportSheet(SD.CC10kFP, SD.CC10kTableName);
		myAW.ImportSheet(SD.CC_UCS16kFP, SD.CC_UCS16kTableName); //	
		myAW.ImportSheet(SD.CC_SCP12kFP,SD.CC_SCP12kTableName); //
		myAW.ImportSheet(SD.UCS20kFP, SD.UCS20kTableName);
		myAW.ImportSheet(SD.UCS_EC16kFP, SD.UCS_EC16kTableName); // 
		myAW.ImportSheet(SD.EC10kFP, SD.EC10kTableName);
		myAW.ImportSheet(SD.EC_ECS24kFP, SD.EC_ECS24kTableName); //
		myAW.ImportSheet(SD.ECS30kFP, SD.ECS30kTableName);
		System.out.println("Table created: RQtoECS");
	}
	
	public static void registerTMRQtoECS(QueryManager myAW){
		myAW.RegisterTM(SD.RQ_CP7kTableName, SD.R70TableName, "ID", SD.CP10kTableName, "COMPONENTID");
		myAW.RegisterTM(SD.CP_SCP12kTableName, SD.CP10kTableName, "COMPONENTID", SD.SCP15kTableName, "SUBCOMPONENTID");
		myAW.RegisterTM(SD.CC_SCP12kTableName, SD.CC10kTableName, "CLASSNAME", SD.SCP15kTableName, "SUBCOMPONENTID");
		myAW.RegisterTM(SD.CC_UCS16kTableName, SD.CC10kTableName, "CLASSNAME", SD.UCS20kTableName, "USECASESTEPID");
		myAW.RegisterTM(SD.UCS_EC16kTableName, SD.UCS20kTableName, "USECASESTEPID", SD.EC10kTableName, "EXCEPTIONCASEID");
		myAW.RegisterTM(SD.EC_ECS24kTableName, SD.EC10kTableName, "EXCEPTIONCASEID", SD.ECS30kTableName, "EXCEPTIONCASESTEPID");
		System.out.println("Index created: RQtoECS");
	}
	
	public static void createTablesInMemoryUCStoECS(QueryManager myAW){
		myAW.ImportSheet(SD.UCS20kFP, SD.UCS20kTableName);
		myAW.ImportSheet(SD.UCS_EC16kFP, SD.UCS_EC16kTableName);
		myAW.ImportSheet(SD.EC10kFP, SD.EC10kTableName);
		myAW.ImportSheet(SD.EC_ECS24kFP, SD.EC_ECS24kTableName);
		myAW.ImportSheet(SD.ECS30kFP, SD.ECS30kTableName);
		System.out.println("Table created: UCStoECS");
	}
	
	public static void registerTMUCStoECS(QueryManager myAW){
		myAW.RegisterTM(SD.UCS_EC16kTableName, SD.UCS20kTableName, "USECASESTEPID", SD.EC10kTableName, "EXCEPTIONCASEID");
		myAW.RegisterTM(SD.EC_ECS24kTableName, SD.EC10kTableName, "EXCEPTIONCASEID", SD.ECS30kTableName, "EXCEPTIONCASESTEPID");
		System.out.println("Index created: UCStoECS");
	}
}

