import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class mainforTesting {

	// excel file URL until I can make them dynamic jdbc:nilostep:excel:[FullPathToTheDirectoryContainingTheExcelFiles]
	
	
	
	private static final String XLDriver = "com.nilostep.xlsql.jdbc.xlDriver";
	private static final String XLURLBase = "jdbc:nilostep:excel:";
	//private static final String CombinedDIR = "./Data/";
	
	//private static final String REQDIR = "./Data/Requirements_1.1";
	//private static final String CCDIR = "./Data/CodeClass_1.1.xls";
	//private static final String TMDIR = "./Data/CC-REQ-TM.csv";
	
	
	public static void main(String[] args) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
		
		InternalDB myH2 = new InternalH2();
		
		myH2.createLink("com.nilostep.xlsql.jdbc.xlDriver", "jdbc:nilostep:excel:./Data/","","","\"demo.xlsqly8\"");
		
		//TODO: make theese work properly using the form above. The problem was for excel linked tables I needed the \"filename.sheetname\" notice the escaped double quotes.
		//myH2.createLink(XLDriver, XLURLBase + CombinedDIR, null,null, "Requirements");
		//myH2.createLink(XLDriver, XLURLBase + REQDIR, null,null, "Requirements");
		//myH2.createLink(XLDriver, XLURLBase + CCDIR , null,null, "CodeClass");
		
		
		//xlSQLTest();
	
	
		
		//myH2.Query(TestQueries.TQ001);
		//myH2.Query(TestQueries.TQ002);
		//myH2.Query(TestQueries.TQ003);
	
		
	}


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
	            for (int i = 0; i < 8000; i++) {
	                sql = "INSERT INTO \"demo.xlsqly8\" VALUES ('TestValue');";
	                stm.execute(sql);
	            }
	            
	            sql = "select count(*) from \"demo.xlsqly8\"";

	            ResultSet rs = stm.executeQuery(sql);

	            //dont do this do batch instead.
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
