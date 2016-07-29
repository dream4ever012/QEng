package utils;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import qEng.InternalDB;

public class ResultSetUtils{

	public static void RStoTable(ResultSet rs, String URL, String User,String Pass,String TableName)
	{
		try {
			ResultSetMetaData md = rs.getMetaData();

			int colCount = md.getColumnCount();

			String colsSQL = "";
			for(int i = 1; i <= colCount; i++){
				colsSQL = colsSQL + "\""+ md.getSchemaName(i) + "." + md.getColumnLabel(i) + "\" " + md.getColumnTypeName(i);
				if(i < colCount){ colsSQL = colsSQL + ", ";}
			}

			System.out.println(colsSQL);

			//Connection setup
			Connection con = DriverManager.getConnection(URL,User,Pass);
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

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	public static void getMetaData(InternalDB myDB, String SQLString, File TQ)
	{
		ResultSet rs = null;
		rs = myDB.QueryToRS(SQLString);
		ResultSetMetaData md;
		try {
			md = rs.getMetaData();
			int colCount = md.getColumnCount();
			String colsSQL = "";
			for(int i = 1; i <= colCount; i++){
				colsSQL = colsSQL + "\""+ md.getColumnLabel(i) + i + "\" " + md.getColumnTypeName(i);
				if(i < colCount){ colsSQL = colsSQL + ", ";}
			}
			System.out.println(colsSQL);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}	

}
