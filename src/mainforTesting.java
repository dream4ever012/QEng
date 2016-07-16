import java.io.File;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ListIterator;


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

	private static final String XLDriver = "com.nilostep.xlsql.jdbc.xlDriver"; // 
	private static final String XLURLBase = "jdbc:nilostep:excel:./SecondData/"; //

	private static final String REQTableName = "\"Requirements.ReqSheet\"";
	private static final String CCTableName = "\"codeclasses.codeclass\"";	
	private static final String TMTableName = "CC_REQ_TM";
	
	private static final String REQTableNameTC1_0 = "\"RequirementsTC.ReqSheet\"";
	private static final String CCTableNameTC1_0 = "\"codeclassTC.codeclass\"";
	private static final String TMTableNameTC1_0 = "CC_REQ_TM";

	//TODO: fix resource with CreateLink when using y8SQL, so far most of our problems are in Y8
	//TODO: fix issue with Y8 where it closes the database if two instances of Y8 are pointing to different folders on the same machine
	//TODO: create table link object interface, to allow sentinal connections to be held for linked tables to speed up performance
	public static void main(String[] args) 
	{
		//Demo

		//creates an .xls file through the JDBC driver. I'm just showing off the full SQL support for files.
		//createReqSheet();
		//createCCSheet();

		//This is how our TiQi front end would create an instance of the internal database system for use
		InternalDB myDB = new InternalH2();

		//This is a demo of how our TiQi front end might create links for accessing external datasources
		//these links are persistent so once created they never have to be created again.
//		myDB.createLink(XLDriver, XLURLBase, null,null, CCTableName);
//		myDB.createLink(XLDriver, XLURLBase, null,null, REQTableName);
//		myDB.createLink(XLDriver, XLURLBase, null,null, CCTableNameTC1_0);
//		myDB.createLink(XLDriver, XLURLBase, null,null, REQTableNameTC1_0);


		//This is an example of an arbirary SQL command that reads the trace matrix info from a .csv file

		String ArbSQL = "DROP TABLE "+ TMTableName +" IF EXISTS; CREATE TABLE "+ TMTableName +" AS SELECT * FROM CSVREAD('./Data/CC-REQ-TM.csv');";

		myDB.arbitrarySQL(ArbSQL);

		//Retriveing an xml representation of the .csv generated table
		String SQLString = "SELECT * FROM " + TMTableName;
		File ArbFile = new File("./results/Arbfile.xml");

		myDB.QueryToXML(SQLString, ArbFile);

		//Retrieving an xml representation of the tracematrix joined with the requirements table
		File SimpleJoin = new File("./results/REQandTM.xml");

		SQLString = "SELECT " + TMTableName + ".ClassName, " + REQTableName + ".* " + 
				"FROM " + TMTableName + " " + 
				"INNER JOIN " + REQTableName + " " +
				"ON "+ TMTableName + ".ID = "+ REQTableName + ".ID;";

		myDB.QueryToXML(SQLString, SimpleJoin);

		//Retrieving an xml representation of the tracematrix joined with the codeclass table
		File SimpleJoin2 = new File("./results/CCandTM.xml");

		SQLString = "SELECT " + CCTableName + ".*, " + TMTableName + ".ID " +
				"FROM " + CCTableName + " " +
				"INNER JOIN " + TMTableName + " " +
				"ON " + TMTableName + ".ClassName = " + CCTableName + ".ClassName;";

		myDB.QueryToXML(SQLString, SimpleJoin2);

		//Retrieving an xml representation of the join of the three tables CodeClass , TM and Requirements
		SQLString = "SELECT " + REQTableName + ".*, " + CCTableName + ".*" + " " +
				"FROM " + REQTableName + " " +
				"INNER JOIN " + TMTableName + " " +
				"ON " + TMTableName + ".ID = " + REQTableName + ".ID" + " " +
				"INNER JOIN " + CCTableName + " " +
				"ON " + CCTableName + ".ClassName = " + TMTableName + ".ClassName;";

		File TripleJoin = new File("./results/TripleJoin.xml");

		myDB.QueryToXML(SQLString, TripleJoin);

		//The following is going to be the execution of the test queries provided to me by Caleb

		File TQ1 = new File ("./results/TQ1.xml");

		SQLString = "SELECT ID as RequirementID " +
				"FROM " + REQTableName + " " +
				"WHERE CATEGORY='Basal flow rate' OR CATEGORY='Sensors';";

		myDB.QueryToXML(SQLString,TQ1);

		File TQ2 = new File ("./results/TQ2.xml");

		SQLString = "SELECT ClassName " +
				"FROM " + CCTableName + " " +
				"WHERE Version = 'V3.1';";

		myDB.QueryToXML(SQLString, TQ2);

		File TQ3 = new File ("./results/TQ3.xml");

		SQLString = "SELECT ID " +
				"FROM " + REQTableName + " " +
				"WHERE DateCreated >= '12-15-15' AND DateCreated < '12-31-16';"; 

		myDB.QueryToXML(SQLString, TQ3);

		File TQ4 = new File ("./results/TQ4.xml");

		SQLString = "SELECT ID as RequirementID " +
				"FROM " + REQTableName + " " +
				"WHERE Type = 'Functional';";

		myDB.QueryToXML(SQLString, TQ4);

		File TQ5 = new File ("./results/TQ5.xml");

		SQLString = "SELECT ID as RequirementID " +
				"From " + REQTableName + " " +
				"WHERE Type = 'Functional';";

		myDB.QueryToXML(SQLString, TQ5);


		File TQ6 = new File("./results/TQ6.xml");
		
		SQLString = "SELECT COUNT(*) " +
				"FROM " + REQTableName + " " +
				"INNER JOIN " + TMTableName + " " +
				"ON " + TMTableName + ".ID= " + REQTableName + ".ID;";
		myDB.QueryToXML(SQLString, TQ6);
		
		
		
		
		
		
		
		
			
//		xlSQLTest();
	}


	//Method for testing
	@SuppressWarnings("unused")
	private static void createReqSheet()
	{

		RequirementsTableData reqtable = new RequirementsTableData();

		//Logistics setup
		try {
			Class.forName("com.nilostep.xlsql.jdbc.xlDriver").newInstance();
			String protocol = "jdbc:nilostep:excel";
			String database = "./SecondData/";
			String url = protocol + ":" + database;

			//Connection setup
			Connection con = DriverManager.getConnection(url);
			Statement stmt = con.createStatement();

			String sql = "DROP TABLE \"Requirements.ReqSheet\" IF EXISTS;"
					+ "CREATE TABLE \"Requirements.ReqSheet\" (ID varchar, Type varchar, Class varchar, Category varchar, Description varchar(3028), DateCreated varchar, Author varchar, Priority varchar);";
			stmt.execute(sql);

			ListIterator<RequirementsRowData> it = reqtable.ReqTestData.listIterator();

			while(it.hasNext()){
				RequirementsRowData d = it.next();
				// sql = "INSERT INTO \"Requirements.ReqSheet\" VALUES ('" + d.ID + "','" + d.Type + "','" + d.Class + "','" + d.Category + "\",\"" + d.Descripton + "\",\"" + d.DateCreated + "\",\"" + d.Author + "\",\"" + d.Priority + "\");";
				sql = "Insert INTO \"Requirements.ReqSheet\" VALUES ('" + d.ID + "','" + d.Type+ "','" + d.Class + "','" + d.Category + "','"+ d.Descripton + "','" + d.DateCreated+ "','" + d.Author + "','" + d.Priority + "');";
				stmt.execute(sql);
			}

			sql = "select count(*) from \"Requirements.ReqSheet\"";
			ResultSet rs = stmt.executeQuery(sql);

			while (rs.next()) {
				System.out.println("Sheet ReqSheet has " + rs.getString(1)
				+ " rows.");
			}
			//closing the connection flushes the database changes through to the underlying file.
			con.close();

		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}

	@SuppressWarnings("unused")
	private static void createCCSheet()
	{
		RequirementsTableData reqtable = new RequirementsTableData();

		//Logistics setup
		try {
			Class.forName("com.nilostep.xlsql.jdbc.xlDriver").newInstance();
			String protocol = "jdbc:nilostep:excel";
			String database = "./SecondData/";
			String url = protocol + ":" + database;

			//Connection setup
			Connection con = DriverManager.getConnection(url);
			Statement stmt = con.createStatement();

			String sql = "DROP TABLE \"codeclasses.codeclass\" IF EXISTS;"
					+ "CREATE TABLE \"codeclasses.codeclass\" (ClassName varchar, Description varchar, CreatedBy varchar, CreatedOn varchar, Version varchar);";
			stmt.execute(sql);
			for(int i = 0; i<ClassNameTableData.TableData.length; i++)
			{
				
				sql = "INSERT INTO \"codeclasses.codeclass\" VALUES (" + ClassNameTableData.TableData[i] + ");";
				stmt.execute(sql);  

			}

			sql = "select count(*) from \"codeclasses.codeclass\"";
			ResultSet rs = stmt.executeQuery(sql);

			while (rs.next()) {
				System.out.println("Sheet CCSheet has " + rs.getString(1)
				+ " rows.");
			}
			//closing the connection flushes the database changes through to the underlying file.
			con.close();

		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}

	/*	            

	private static void xlSQLTest() {

		//not great but this is the template for Y8 Connections until I can address some issues in Y8
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
	}
*/
	
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

}
