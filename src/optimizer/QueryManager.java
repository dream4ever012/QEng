package optimizer;

import java.io.File;

public interface QueryManager {
	// what our entry point is?
	public File queryToXml (String SQL);

	public void createLink(String jdbc_driver,String url, String user, String pass , String tablename);

	boolean importCSVAsTable(String file, String tableName);
	
}
