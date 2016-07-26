package optimizer;
import java.io.File;
import qEng.InternalDB;
import qEng.InternalH2;

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
}
