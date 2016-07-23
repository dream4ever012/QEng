package utils;

import java.io.File;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import qEng.InternalDB;

public class GetMetaData {
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
