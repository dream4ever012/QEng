import java.io.File;

//Does your github grab this?

//import java.sql.Connection;
//import java.sql.Driver;
//import java.sql.DriverManager;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.sql.Statement;

//TODO: add XLSX support to the driver
//TODO: discover use syntax for .csv , xml etc. the xlSQL_Y8 is a framework for adding files to a HSQLDB in memory.
//TODO: 
public class mainforTesting {

	// excel file URL until I can make them dynamic jdbc:nilostep:excel:[FullPathToTheDirectoryContainingTheExcelFiles]
	
	private static final String XLDriver = "com.nilostep.xlsql.jdbc.xlDriver";
	private static final String XLURLBase = "jdbc:nilostep:excel:./Data/";

	private static final String DEMOTableName = "\"demo.xlsqly8\"";
	//private static final String REQTableName = "\"Requirements2.Requirements2\"";
	//private static final String TMTableName = "\"CC-REQ-TM.csv\"";

	//private static final String CCDIR = "./Data/CodeClass_1.1.xls";
	//private static final String TMDIR = "./Data/CC-REQ-TM.csv";
	
	//TODO: fix resource with CreateLink when using y8SQL, so far most of our problems are in Y8
	//TODO: create table link object interface, to allow sentinal connections to be held for linked tables to speed up performance
	public static void main(String[] args) 
	{
		InternalDB myH2 = new InternalH2();
		
		//myH2.createLink("com.nilostep.xlsql.jdbc.xlDriver", "jdbc:nilostep:excel:./Data/","","","\"demo.xlsqly8\"");
		
	//	System.out.println("Test2");
		
		myH2.createLink(XLDriver, XLURLBase, null,null, "\"demo.xlsqly8\"");
		
	//	System.out.println("Test3");
		
	//	myH2.createLink(XLDriver, XLURLBase,null,null, DEMOTableName);
		
	//	myH2.createLink(XLDriver, XLURLBase,null,null, TMTableName);
		
		File queryResult = null;
		queryResult = myH2.quickXMLFile();
		
		
		myH2.QueryToXML("SELECT Count(*) FROM "  + DEMOTableName + ";", queryResult );
		
		
		System.out.println("Useful Tables");
		//TODO: make these work properly using the form above by changing the Static String URLs above. The problem was for excel linked tables I needed the \"filename.sheetname\" notice the escaped double quotes.
		//Though I think the problem is with the _1.1 because the dot is meaningful to SQL I'm trying to add brackets and such around it to get it to work.
		//Might be a driver issue I might need to tweak the driver to get it working the project is an old one last update was 2008 just after xlsx came out.
		
		//myH2.createLink(XLDriver, XLURLBase, null,null, REQTableName);
		//myH2.createLink(XLDriver, XLURLBase + REQDIR, null,null, "Requirements");
		//myH2.createLink(XLDriver, XLURLBase + CCDIR , null,null, "CodeClass");
			
		//xlSQLTest();
	
		//myH2.Query(TestQueries.TQ001);
		//myH2.Query(TestQueries.TQ002);
		//myH2.Query(TestQueries.TQ003);
	
	}


	
	//Method for testing
/*	private static void xlSQLCreateSheetFromArray(String[] s)
	{
		
		
	}
	*/

	/*private static void xlSQLTest() {
		
		//not great but 
		  try {
	            
	            String driver = "com.nilostep.xlsql.jdbc.xlDriver";
	            // holding d so I could confirm in debug mode that I have the right driver
	            Driver d = (Driver) Class.forName(driver).newInstance();
	            System.out.println("Driver was successfully loaded.");
	            String protocol = "jdbc:nilostep:excel";
	            //String database = System.getProperty("user.dir");
	            String database = "./Data/";
	            String url = protocol + ":" + database;
	            
	            Connection con = DriverManager.getConnection(url);
	            Statement stm = con.createStatement();
	            
	            String sql = "DROP TABLE \"demo.xlsqly8\" IF EXISTS;"
	                       + "CREATE TABLE \"demo.xlsqly8\" (TestColumn varchar);";
	            stm.execute(sql);

	            // because it is release Y8 we'll do 8000
	            //dont do this do batch instead
	            for (int i = 0; i < 8000; i++) {
	                sql = "INSERT INTO \"demo.xlsqly8\" VALUES ('TestValue');";
	                stm.execute(sql);
	            }
	            
	            sql = "select count(*) from \"demo.xlsqly8\"";

	            ResultSet rs = stm.executeQuery(sql);

	           
	            while (rs.next()) {
	                System.out.println("Sheet xlsqly8 has " + rs.getString(1)
	                                   + " rows.");
	            }

	            con.close();
	        } catch (Exception e) {
	            System.out.println("Are you sure this is WinXP and Java 1.4.2 ..? ");
	            e.printStackTrace();
	        }
	}*/
}
