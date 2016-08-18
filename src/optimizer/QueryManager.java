package optimizer;

import java.io.File;
import java.sql.ResultSet;

import qEng.IDBReturnEnum;

public interface QueryManager {
	// what our entry point is?
	public File queryToXml (String SQL);

	public void createLink(String jdbc_driver,String url, String user, String pass , String tablename);

	boolean importCSVAsTable(String file, String tableName);
	
	boolean arbitrarySQL(String SQLString);
	
	IDBReturnEnum ImportSheet(String FilePath, String SheetName);

	public ResultSet QueryToRS(String sQLString);
	
	public long measureCostArbitrary(QueryManager myAW, String ArbSQL, File TQ);
	
	public IDBReturnEnum QueryToXML(String SQLString, File FileRef);

	public void RegisterCompiledUDF(String Alias, String classpath); 
	
	public IDBReturnEnum toXML(ResultSet rs, File fref);

	void RegisterTM(String TMTableName, String TableOneTableName, String TableOneColName, String TableTwoTableName, String TableTwoColName);

	//TODO: figure out what needs to be passed here.
	boolean NewTempTable(String TableName, String... Columns);
	
	//TODO: remove after testing is done. ONLY for test cases
	File queryToXmlH2(String SQL);

}

/*
 *
 * 
 * in terms of optimization scheme, factory pattern seems to be a great idea
3*2*2*2 strategies
but, in terms of pruning and skipping
do such must perform better 
yet would like to know what the benefit would be
before making up such strategy
what is pre-required beforehand is table and UDF stats
scancost is not hard to do that you select column from table and divided with the number of rows and then take an average cost per 10k rows or sth like that
cpu cost maybe a method, you read the first row of a columns and then select * from table where col.a = col.1st_row
then this would be one time scancost and the cpu cost 
cpu cost would be the minimal
 * 
 * 
 * 
 * 
*/
