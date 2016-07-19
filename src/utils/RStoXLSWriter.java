package utils;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ListIterator;

import testDataObjects.RequirementsRowData;
import testDataObjects.RequirementsTableData;

public class RStoXLSWriter {

	public static void RStoXLSWrite(ResultSet rs, File output){
		
		//RequirementsTableData reqtable = new RequirementsTableData();

		//Logistics setup
		
		
		try {
			ResultSetMetaData md = rs.getMetaData();
			int colCount = md.getColumnCount();
			
			String colsSQL = "";
			for(int i = 0; i<= colCount;i++){
			colsSQL = colsSQL + md.getColumnLabel(i) + " " + md.getColumnType(i);
				
			}
			
			System.out.println(colsSQL);
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
		
/*		try {
			Class.forName("com.nilostep.xlsql.jdbc.xlDriver").newInstance();
			String protocol = "jdbc:nilostep:excel";
			String database = "./Results/";
			String url = protocol + ":" + database;

			
			
			//Connection setup
			Connection con = DriverManager.getConnection(url);
			Statement stmt = con.createStatement();

			String sql = "DROP TABLE \""+ output.getName() + ".Result\" IF EXISTS;"
					+ "CREATE TABLE \""+ output.getName() + ".Result\" (ID varchar, Type varchar, Class varchar, Category varchar, Description varchar(3028), DateCreated varchar, Author varchar, Priority varchar);";
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
		*/
		
		
	}

}
