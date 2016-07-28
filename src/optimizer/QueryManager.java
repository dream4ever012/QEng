package optimizer;

import java.io.File;

public interface QueryManager {
	// what our entry point is?
	public File queryToXml (String SQL);

	public void createLink(String jdbc_driver,String url, String user, String pass , String tablename);

	boolean importCSVAsTable(String file, String tableName);
	
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
