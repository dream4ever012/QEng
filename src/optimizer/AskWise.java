package optimizer;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import oracle.jdbc.rowset.OracleWebRowSet;
import qEng.IDBReturnEnum;
import qEng.InternalDB;
import qEng.InternalH2;
import utils.MeasureCostArbitrary;

//TODO: consider moving sentinel connection out of InternalH2 and into QueryManager. It would allow AW to close it's database and switch to a new one.
public class AskWise implements QueryManager{
	InternalDB DB;
	private String IH2DBURL = "jdbc:h2:./Data/AskWiseTesting/AW;TRACE_LEVEL_FILE=3;TRACE_MAX_FILE_SIZE=20";
	private static Connection conn;
	private static Statement stmt;	
	
	//read CSV trace matrix
	//String ArbSQL = ;
	//myAW.arbitrarySQL(ArbSQL);
	
	public AskWise(){
		DB = new InternalH2(IH2DBURL); // H2 for default 	
	}
	
	public AskWise(InternalDB DB){
		this.DB = DB;
	}
	
	//TODO: add parser for url to get protocol to map to concrete impls
	public AskWise(String URL, String User, String Pass){
		//Connection setup
		DB = new InternalH2(IH2DBURL,User,Pass);
		try {
			conn = DriverManager.getConnection(URL,User,Pass);
			stmt = conn.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	// getter
	/*public Connection getConn(){
		return conn;
	}*/
	
	@Override
	public File queryToXml(String SQL) {
		// TODO Auto-generated method stub
		Query head = new DefaultQuery();
		return head.ExecuteQuery(SQL, DB);
	}
	
	@Override
	public void createLink(String jdbc_driver,String url, String user, String pass , String tablename) {
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

	// TODO: Not sure if this would be the right way
	@Override
	public long measureCostArbitrary(QueryManager myAW, String ArbSQL, File TQ) {
		return MeasureCostArbitrary.measureCostArbitrary(myAW, ArbSQL, TQ);
	}

	@Override
	public IDBReturnEnum QueryToXML(String SQLString, File FileRef) {
		return DB.QueryToXML(SQLString, FileRef);
	}

	@Override
	public void RegisterCompiledUDF(String Alias, String classpath) {
		DB.RegisterCompiledUDF(Alias, classpath);
	}

	public IDBReturnEnum toXML(ResultSet rs, File fref) {
		//new File("./results/").mkdirs();
		//File f = new File("./results/"+ fref.getPath() + ".xml");
		IDBReturnEnum rt = IDBReturnEnum.FAIL;
		if(fref == null || rs == null){return rt;}

		FileOutputStream fos;
		try {

			fos = new FileOutputStream(fref);
			OracleWebRowSet set = new OracleWebRowSet();
			set.writeXml(rs, fos);
			set.close();

			rt = IDBReturnEnum.SUCCESS;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rt;
	}
}
