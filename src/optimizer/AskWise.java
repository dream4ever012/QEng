package optimizer;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.sql.ResultSet;
import java.sql.SQLException;

import optimizer.QuryImpls.DefaultQuery;
import oracle.jdbc.rowset.OracleWebRowSet;
import qEng.IDBReturnEnum;
import qEng.InternalDB;
import qEng.InternalH2;
import utils.MeasureCostArbitrary;
import utils.DataModel.DataGraph;
import utils.DataModel.DataModel;
import utils.DataModel.TableNode;

//TODO: consider moving sentinel connection out of InternalH2 and into QueryManager. It would allow AW to close it's database and switch to a new one.
public class AskWise implements QueryManager{
	
	InternalDB DB;
	private String IH2DBURL = "jdbc:h2:./Data/AskWiseTesting/AW;TRACE_LEVEL_FILE=3;TRACE_MAX_FILE_SIZE=20";
	private boolean IDBCutoff = false;
	
	private DataModel DM;
	//read CSV trace matrix
	//String ArbSQL = ;
	//myAW.arbitrarySQL(ArbSQL);

	public AskWise(){
		DB = new InternalH2(IH2DBURL); // H2 for default 
		DM = new DataGraph();
	}

	public AskWise(InternalDB DB){
		this.DB = DB;
		DM = new DataGraph();
	}

	//TODO: add parser for url to get protocol to map to concrete impls
	public AskWise(String URL, String User, String Pass){
		//Connection setup
		DB = new InternalH2(IH2DBURL,User,Pass);
		DM = new DataGraph();
	}

	// getter
	/*public Connection getConn(){
		return conn;
	}*/

	//TODO: Call the QueryFactory
	@Override
	public File queryToXml(String SQL) {
		// TODO Auto-generated method stub
		//Query head = new DefaultQuery();
		
		
		Query Result = QueryFactory.ResolveQuery(SQL,this);

		
		return Result.ToXML();
		//return head.ExecuteQuery(SQL, DB);
		
	}
	
	
	// for test cases ONLY
	@Override
	public File queryToXmlH2(String SQL) {
		// TODO Auto-generated method stub
		Query head = new DefaultQuery();
		return head.ExecuteQuery(SQL, DB);
	}
	
	//TODO: Make useful maybe not use varargs but did this to teach caleb about varargs methods
	@Override
	public boolean NewTempTable(String TableName, String... columns)
	{
		
		//for(Object i : vars)
		//{
			
		//}
		
		return true;
	}

	@Override
	public void createLink(String jdbc_driver,String url, String user, String pass , String tablename) {
		DB.createLink(jdbc_driver, url, user, pass, tablename);
		DM.addNode(new TableNode(tablename));
	}

	@Override
	public boolean importCSVAsTable(String file, String tableName){
		//String command = "DROP TABLE "+ tableName +" IF EXISTS; CREATE TABLE "+ tableName +" AS SELECT * FROM CSVREAD('./Data/CC-REQ-TM5k.csv');";
		String command = "DROP TABLE "+ tableName +" IF EXISTS; CREATE TABLE "+ tableName +" AS SELECT * FROM CSVREAD('" + file + "');";
		DM.addNode(new TableNode(tableName));
		if(!IDBCutoff){
		return DB.arbitrarySQL(command);
		}else {return true;}
	}
	
	@Override
	public void WriteCSV(String FilePath, String SQLString) {
		DB.WriteCSV(FilePath, SQLString);
		System.out.println("Created " + FilePath);
	}

	@Override
	public boolean arbitrarySQL(String SQLString) {
		return DB.arbitrarySQL(SQLString);
	}

	@Override
	public IDBReturnEnum ImportSheet(String FilePath, String SheetName) {
		DM.addNode(new TableNode(SheetName));
		if(!IDBCutoff){
		return DB.ImportSheet(FilePath, SheetName);
		} else { return IDBReturnEnum.SUCCESS;}
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

	
	//TODO: Redo this method to pass all of the args to DB and have create index handle it internal to the InternalDB IMPL.
	@Override
	public void RegisterTM(String TMTableName, String TableOneTableName, String TableOneColName,
						   String TableTwoTableName, String TableTwoColName) {
		
		DB.CreateIndex(TMTableName, TableOneColName);
		DB.CreateIndex(TMTableName, TableTwoColName);
		
		DB.CreateIndex(TableOneTableName, TableOneColName);
		DB.CreateIndex(TableTwoTableName, TableTwoColName);
	
		//DM.addJoinCols(TMTableName, TableOneColName, TableOneTableName, TableOneColName);
		//DM.addJoinCols(TMTableName, TableTwoColName, TableTwoTableName, TableTwoColName);
			
	}


}
