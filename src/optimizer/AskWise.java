package optimizer;
import java.io.File;
import java.sql.ResultSet;

import qEng.IDBReturnEnum;
import qEng.InternalDB;
import qEng.InternalH2;
import utils.MeasureCostArbitrary;

public class AskWise implements QueryManager{
	InternalDB DB;
	private String IH2DBURL = "jdbc:h2:./Data/AskWiseTesting/AW;TRACE_LEVEL_FILE=3;TRACE_MAX_FILE_SIZE=20";
	
	
	//read CSV trace matrix
	//String ArbSQL = ;
	//myAW.arbitrarySQL(ArbSQL);
	
	public AskWise(){
		DB = new InternalH2(IH2DBURL); // H2 for default 	
	}
	
	
	
	@Override
	public File queryToXml(String SQL) {
		// TODO Auto-generated method stub
		Query head = new DefaultQuery();
		return head.ExecuteQuery(SQL, DB);
	}
	
	

	@Override
	public void createLink(String jdbc_driver,String url, String user, String pass , String tablename) {
		// TODO Auto-generated method stub
		DB.createLink(jdbc_driver, url, user, pass, tablename);
	}
	
	@Override
	public boolean importCSVAsTable(String file, String tableName){
		//String command = "DROP TABLE "+ tableName +" IF EXISTS; CREATE TABLE "+ tableName +" AS SELECT * FROM CSVREAD('./Data/CC-REQ-TM5k.csv');";
		String command = "DROP TABLE "+ tableName +" IF EXISTS; CREATE TABLE "+ tableName +" AS SELECT * FROM CSVREAD('" + file + "');";
		return DB.arbitrarySQL(command);
	}

	@Override
	public boolean arbitrarySQL(String SQLString) {
		return DB.arbitrarySQL(SQLString);
	}

	@Override
	public IDBReturnEnum ImportSheet(String FilePath, String SheetName) {
		return DB.ImportSheet(FilePath, SheetName);
	}

	@Override
	public ResultSet QueryToRS(String SQLString) {
		return DB.QueryToRS(SQLString);
		
	}

	// TO-DO: Not sure if this would be the right way
	@Override
	public long measureCostArbitrary(QueryManager myAW, String ArbSQL, File TQ) {
		return MeasureCostArbitrary.measureCostArbitrary(myAW, ArbSQL, TQ);
	}



	@Override
	public IDBReturnEnum QueryToXML(String SQLString, File FileRef) {
		return DB.QueryToXML(SQLString, FileRef);
	}
}
