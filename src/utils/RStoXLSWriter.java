package utils;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;


@Deprecated
public class RStoXLSWriter {

	//TODO: redo with apache POI
	
	public static void RStoXLSWrite(ResultSet rs, File output){
		
		//TODO: switch to batch statements for better performance though this is only for testing purposes
		try {
			ResultSetMetaData md = rs.getMetaData();
			int colCount = md.getColumnCount();
			
			String colsSQL = "";
			for(int i = 1; i <= colCount; i++){
			//	if(md.getSchemaName(i) != ""){
			//colsSQL = colsSQL + "\""+ md.getSchemaName(i) + "." + md.getColumnLabel(i) + "\" ";
			//	} else
			//	{
					colsSQL = colsSQL + "\""+ md.getColumnLabel(i) + "\" ";
			//	}
				 if(md.getColumnTypeName(i) == "VARCHAR2")
				 {
					 colsSQL = colsSQL + "VARCHAR";
				 }
				 else{
					 colsSQL = colsSQL + md.getColumnName(i);
				 }
				if(i < colCount){ colsSQL = colsSQL + ", ";}
			}
			
			System.out.println(colsSQL);
			
			Class.forName("com.nilostep.xlsql.jdbc.xlDriver").newInstance();
			String protocol = "jdbc:nilostep:excel";
			String database = "./SecondData/";
			String url = protocol + ":" + database;
			String TableName = "\"" + output.getName() + ".Result\"";

			//Connection setup
			Connection con = DriverManager.getConnection(url);
			Statement stmt = con.createStatement();

			String sql = "DROP TABLE " + TableName + " IF EXISTS;"
					+ "CREATE TABLE " + TableName + " ("+colsSQL+");";
			
			stmt.execute(sql);

			String rowValsSQL;
			int ct;
			while(rs.next()){
				rowValsSQL = "";
				for(int k = 1; k <= colCount; k++){
					ct = md.getColumnType(k);
					if(ct == java.sql.Types.VARCHAR || ct == java.sql.Types.DATE || ct == java.sql.Types.LONGVARCHAR || ct == java.sql.Types.LONGNVARCHAR){
						rowValsSQL = rowValsSQL+ "\'" + rs.getString(k) + "\'";
					} else {
						rowValsSQL = rowValsSQL + rs.getString(k);
					}
				if(k < colCount){ rowValsSQL = rowValsSQL + ", ";}
				}
				sql = "Insert INTO " + TableName + " VALUES (" +rowValsSQL + ");";
				System.out.println(sql);
				stmt.execute(sql);
			}

			sql = "select count(*) from " + TableName;
			ResultSet vRS = stmt.executeQuery(sql);

			while (vRS.next()) {
				System.out.println("Sheet "+ TableName +" has " + vRS.getString(1)
				+ " rows.");
			}
			//closing the connection flushes the database changes through to the underlying file.
			con.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
			
	}

}
