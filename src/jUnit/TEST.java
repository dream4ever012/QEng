package jUnit;

import java.io.File;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import org.junit.Before;
import org.junit.Test;

import ResourceStrings.SD;
import optimizer.AskWise;
import optimizer.QueryManager;
import utils.CreateInOracleTable;
import utils.CreateTablesInMemory;

public class TEST {
	private static QueryManager myAW;
	private static boolean setupIsDone = false;
	private static final String H2PROTO = "jdbc:h2:";
	private static final String IH2FP = "./Data/TESTMAIN/";
	private static final String IH2DBName = "TESTMAIN";
	private static final String TRACELEVEL = ";TRACE_LEVEL_FILE=3;TRACE_MAX_FILE_SIZE=20";
	private static String IH2DBURL;
	private static final String ResultsURL = "./Results/TESTMAIN/";
	
	@Before
	public void init(){
		// private InternalDB myDB;

		
		if(!setupIsDone){
			IH2DBURL = H2PROTO + IH2FP + IH2DBName + TRACELEVEL;
			//if(new File("./Data/TestCaseDataBases/POITests.mv.db").delete())
			if(new File(IH2FP + IH2DBName + ".mv.db").delete())
			{
				System.out.println("Old Database Deleted");
			}
			if(new File(IH2FP + IH2DBName + ".trace.db").delete())
			{
				System.out.println("Old Trace Deleted");
			}		
		new File(ResultsURL).mkdirs();
		myAW = new AskWise();
		try {
			CreateTablesInMemory.createTablesInMemory(myAW);

			// CreateInOracleTable.createInOracleTableMF(myAW);
			
			String ArbSQL = "DROP TABLE "+ SD.R70TableName +" IF EXISTS; CREATE TABLE " 
							+ SD.R70TableName +" AS SELECT * FROM " + SD.R70TableName + ";";
			myAW.arbitrarySQL(ArbSQL);
			
			
			String rowValsSQL;
			ResultSetMetaData md;

			String StringSQL = "SELECT * FROM " + SD.R70TableName + ";";
			ResultSet rs = myAW.QueryToRS(StringSQL);
			md = rs.getMetaData();
			int colCount = md.getColumnCount();

			String colsSQL = "";
			String colNamesSQL = "";

			for(int i = 1; i <= colCount; i++){
					colsSQL = colsSQL + "" + md.getColumnLabel(i) + " " + md.getColumnTypeName(i); //+ md.getSchemaName(i) + "." +
					colNamesSQL = colNamesSQL + md.getColumnLabel(i);
				if(i < colCount){ colsSQL = colsSQL + ", "; colNamesSQL = colNamesSQL + ", ";}
			}

			int ct;
			while(rs.next()){
				rowValsSQL = "";
				for(int k = 1; k <= colCount; k++){
					ct = md.getColumnType(k);
					if(ct == java.sql.Types.VARCHAR || ct == java.sql.Types.DATE || ct == java.sql.Types.LONGVARCHAR || ct == java.sql.Types.LONGNVARCHAR){
						
						rowValsSQL = rowValsSQL+ "\'" + rs.getString(k) + "\'";
						System.out.println(rowValsSQL);
					} else {	
						rowValsSQL = rowValsSQL + rs.getString(k);
						System.out.println(rowValsSQL);
					}
					if(k < colCount){ rowValsSQL = rowValsSQL + ", ";}
				}
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

			


	}
	
	@Test
	public void test(){
		
	}

}
