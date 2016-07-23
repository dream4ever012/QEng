package utils;

import java.sql.ResultSet;
import java.sql.SQLException;
import qEng.InternalDB;

public class PrintRowCount {
	private static void printRowCount(InternalDB myDB, String tableName) {
		System.out.println("# of rows of table " + tableName + ": " + rowCount(myDB, tableName));
	}

	private static int rowCount(InternalDB myDB, String tableName){
		ResultSet rs =  null;
		int rowCount;
				
		String SQLString = "SELECT * " +
				"FROM " + tableName + ";";
		rs = myDB.QueryToRS(SQLString);
		int currentRow;
		try {
			currentRow = rs.getRow();
			rowCount = rs.last() ? rs.getRow() : 0; // determine number of rows
			if (currentRow == 0)					// if there is not current row
				rs.beforeFirst();					// we want next() to go to first row
			else									// if there WAS a current row
				rs.absolute(currentRow);			// restore it
			return rowCount;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}			// get current row
		return -1;
	}

}
