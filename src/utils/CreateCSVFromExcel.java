package utils;

import ResourceStrings.SD;
import optimizer.QueryManager;

public class CreateCSVFromExcel {
	private static final String Filepath = "./ThirdData/";
	
	public static void createCSVFromExcel(QueryManager myAW){
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
		
		createCSV(myAW, Filepath, SD.REQTableNameTC1);
		createCSV(myAW, Filepath, SD.CCTableName5k);
		createCSV(myAW, Filepath, SD.REQTableName);
		createCSV(myAW, Filepath, SD.CCTableName);
		createCSV(myAW, Filepath, SD.CC10kTableName);
		createCSV(myAW, Filepath, SD.CC_UCS16kTableName);
		createCSV(myAW, Filepath, SD.CC_SCP12kTableName);
		createCSV(myAW, Filepath, SD.G70TableName);
		createCSV(myAW, Filepath, SD.G_UC8kTableName);
		createCSV(myAW, Filepath, SD.UC_UCS15kTableName);
		createCSV(myAW, Filepath, SD.UCS20kTableName);
		createCSV(myAW, Filepath, SD.UCS_EC16kTableName);
		createCSV(myAW, Filepath, SD.EC10kTableName);
		createCSV(myAW, Filepath, SD.EC_ECS24kTableName);
		createCSV(myAW, Filepath, SD.ECS30kTableName);
		createCSV(myAW, Filepath, SD.R70TableName);
		createCSV(myAW, Filepath, SD.RQ_CP7kTableName);
		createCSV(myAW, Filepath, SD.CP10kTableName);
		createCSV(myAW, Filepath, SD.CP_SCP12kTableName);
		createCSV(myAW, Filepath, SD.SCP15kTableName);
		createCSV(myAW, Filepath, SD.UC10kTableName);
	}
	
	//myAW.WriteCSV("./results/WriteCSVTest/T1.csv", "SELECT * FROM T1;");
	
	public static void createCSV(QueryManager myAW, String Filepath, String TableName){
		String SQLString = "SELECT * FROM " + TableName;
		myAW.WriteCSV(Filepath + TableName + ".csv", SQLString);
	}

}
