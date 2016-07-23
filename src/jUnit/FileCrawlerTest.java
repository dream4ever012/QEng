package jUnit;

import static org.junit.Assert.*;

import java.io.File;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import org.junit.Test;

public class FileCrawlerTest {

	@Test
	public void test() {
		
		ArrayList<File> CassandraClasses = utils.JavaFileFinder.GetFiles(new File("./UDFStuff/src_csdr/src/java"));
		
		System.out.println(CassandraClasses.size());
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

			String sql = "DROP TABLE \"Cassandra.classes\" IF EXISTS;"
					+ "CREATE TABLE \"Cassandra.classes\" (Classes varchar);";
			stm.execute(sql);

			//dont do this do batch instead
			for (File i : CassandraClasses) {
				sql = "INSERT INTO \"Cassandra.classes\" VALUES ('"+ i.getAbsolutePath() +"');";
				stm.execute(sql);
			}

			sql = "select count(*) from \"Cassandra.classes\"";

			ResultSet rs = stm.executeQuery(sql);

			while (rs.next()) {
				System.out.println("Sheet Cassandra.classes has " + rs.getString(1)
				+ " rows.");
			}

			con.close();
		} catch (Exception e) {
			System.out.println("Are you sure this is WindowsXP and Java 1.4.2 ..? or higher");
			e.printStackTrace();
		}
	}

}
