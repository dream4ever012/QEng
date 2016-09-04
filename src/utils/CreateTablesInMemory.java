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
	
	public static void createTablesInMemoryGtoECSCJS(QueryManager myAW){
		// complete join set: G_ECS
		myAW.ImportSheet(SD.G_UCS12kFP, SD.G_UCS12kTableName);
		myAW.ImportSheet(SD.UC_EC12kFP, SD.UC_EC12kTableName);
		myAW.ImportSheet(SD.UCS_ECS65kFP, SD.UCS_ECS65kTableName);
		myAW.ImportSheet(SD.G_EC10kFP, SD.G_EC10kTableName);
		myAW.ImportSheet(SD.UC_ECS65kFP, SD.UC_ECS65kTableName);
		myAW.ImportSheet(SD.G_ECS65kFP, SD.G_ECS65kTableName);
		System.out.println("Table created: GtoECS_Complete Join Set");
	}
	
	
		
	public static void registerTMGtoECS(QueryManager myAW){
		myAW.RegisterTM(SD.G_UC8kTableName, SD.G70TableName, "GOALID", SD.UC10kTableName, "USECASEID");
		myAW.RegisterTM(SD.UC_UCS15kTableName, SD.UC10kTableName, "USECASEID", SD.UCS20kTableName, "USECASESTEPID");
		myAW.RegisterTM(SD.UCS_EC16kTableName, SD.UCS20kTableName, "USECASESTEPID", SD.EC10kTableName, "EXCEPTIONCASEID");
		myAW.RegisterTM(SD.EC_ECS24kTableName, SD.EC10kTableName, "EXCEPTIONCASEID", SD.ECS30kTableName, "EXCEPTIONCASESTEPID");	
		System.out.println("Index created: GtoECS");
	}
	
	public static void registerTMGtoECSCJS(QueryManager myAW){
		// complete join set: G_ECS 
		//myAW.RegisterTM(SD.G_UCS12kTableName, SD.G70TableName, "GOALID", SD.UCS20kTableName, "USECASESTEPID");
		myAW.RegisterTM(SD.G_UCS12kTableName, SD.G70TableName, "GOALID", SD.UCS20kTableName, "USECASESTEPID");
		myAW.RegisterTM(SD.UC_EC12kTableName, SD.UC10kTableName, "USECASEID", SD.EC10kTableName, "EXCEPTIONCASEID");
		myAW.RegisterTM(SD.UCS_ECS65kTableName, SD.UCS20kTableName, "USECASESTEPID", SD.ECS30kTableName, "EXCEPTIONCASESTEPID");
		myAW.RegisterTM(SD.G_EC10kTableName, SD.G70TableName, "GOALID", SD.EC10kTableName, "EXCEPTIONCASEID");		
		myAW.RegisterTM(SD.UC_ECS65kTableName, SD.UC10kTableName, "USECASEID", SD.ECS30kTableName, "EXCEPTIONCASESTEPID");	
		myAW.RegisterTM(SD.G_ECS65kTableName, SD.G70TableName, "GOALID", SD.ECS30kTableName, "EXCEPTIONCASESTEPID");		
		System.out.println("Index created: GtoECS_Complete Join Set");
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
	
	public static void createTablesInMemoryRQtoECSJS(QueryManager myAW){
		myAW.ImportSheet(SD.R_SCP10kFP, SD.R_SCP10kTableName);
		myAW.ImportSheet(SD.CP_CC9kFP, SD.CP_CC9kTableName);
		myAW.ImportSheet(SD.SCP_UCS192kFP, SD.SCP_UCS192kTableName);
		myAW.ImportSheet(SD.CC_EC12kFP, SD.CC_EC12kTableName);
		myAW.ImportSheet(SD.R_CC8kFP, SD.R_CC8kTableName);
		myAW.ImportSheet(SD.CP_UCS143kFP, SD.CP_UCS143kTableName);
		myAW.ImportSheet(SD.SCP_EC52kFP, SD.SCP_EC52kTableName);
		myAW.ImportSheet(SD.CC_ECS285kFP, SD.CC_ECS285kTableName);
		myAW.ImportSheet(SD.R_UCS66kFP, SD.R_UCS66kTableName);
		myAW.ImportSheet(SD.CP_EC52kFP, SD.CP_EC52kTableName);
		myAW.ImportSheet(SD.SCP_ECS66kFP, SD.SCP_ECS66kTableName);
		myAW.ImportSheet(SD.R_EC52kFP, SD.R_EC52kTableName);
		myAW.ImportSheet(SD.CP_ECS1049kFP, SD.CP_ECS1049kTableName);
		System.out.println("Table created: RQtoECSJS");
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
	
	public static void registerTMRQtoECSJS(QueryManager myAW){
		myAW.RegisterTM(SD.R_SCP10kTableName, SD.R70TableName, "ID", SD.SCP15kTableName, "SUBCOMPONENTID");
		myAW.RegisterTM(SD.CP_CC9kTableName, SD.CP10kTableName, "COMPONENTID", SD.CC10kTableName, "CLASSNAME");
		myAW.RegisterTM(SD.SCP_UCS192kTableName, SD.SCP15kTableName, "SUBCOMPONENTID", SD.UCS20kTableName, "USECASESTEPID");
		myAW.RegisterTM(SD.CC_EC12kTableName, SD.CC10kTableName, "CLASSNAME", SD.EC10kTableName, "EXCEPTIONCASEID");
		myAW.RegisterTM(SD.R_CC8kTableName, SD.R70TableName, "ID", SD.CC10kTableName, "CLASSNAME");
		myAW.RegisterTM(SD.CP_UCS143kTableName, SD.CP10kTableName, "COMPONENTID", SD.UCS20kTableName, "USECASESTEPID");
		myAW.RegisterTM(SD.SCP_EC52kTableName, SD.SCP15kTableName, "SUBCOMPONENTID", SD.EC10kTableName, "EXCEPTIONCASEID");
		myAW.RegisterTM(SD.CC_ECS285kTableName, SD.CC10kTableName, "CLASSNAME", SD.ECS30kTableName, "EXCEPTIONCASESTEPID");
		myAW.RegisterTM(SD.R_UCS66kTableName, SD.R70TableName, "ID", SD.UCS20kTableName, "USECASESTEPID");
		myAW.RegisterTM(SD.CP_EC52kTableName, SD.CP10kTableName, "COMPONENTID", SD.EC10kTableName, "EXCEPTIONCASEID");
		myAW.RegisterTM(SD.SCP_ECS66kTableName, SD.SCP15kTableName, "SUBCOMPONENTID", SD.ECS30kTableName, "EXCEPTIONCASESTEPID");
		myAW.RegisterTM(SD.R_EC52kTableName, SD.R70TableName, "ID", SD.EC10kTableName, "EXCEPTIONCASEID");
		myAW.RegisterTM(SD.CP_ECS1049kTableName, SD.CP10kTableName, "COMPONENTID", SD.ECS30kTableName, "EXCEPTIONCASESTEPID");
		System.out.println("Index created: RQtoECSJS");
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

