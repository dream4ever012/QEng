package utils;

//TODO: remove or switch to POI
@Deprecated
public class xlsTestUtil {

/*	@Deprecated
	private static void xlSQLTest() {

		//not great but this is the template for Y8 Connections until I can address some issues in Y8
		  try {

	            String driver = "com.nilostep.xlsql.jdbc.xlDriver";
	            // holding d so I could confirm in debug mode that I have the right driver
	            Driver d = (Driver) Class.forName(driver).newInstance();
	            System.out.println("Driver was successfully loaded.");
	            String protocol = "jdbc:nilostep:excel";
	            //String database = System.getProperty("user.dir");
	            String database = "./SecondData/";
	            String url = protocol + ":" + database;

	            
	            
	            Connection con = DriverManager.getConnection(url);
	            Statement stm = con.createStatement();
	            
	            String sql = "select count(*) from \"codeclasses.codeclass\"";
	            //String sql = "select count(*) from \"RequirementsTC.ReqSheet\"";
	            //String sql = "select count(*) from \"Requirements.ReqSheet\"";
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
	}
	*/
}
