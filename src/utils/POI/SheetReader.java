package utils.POI;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellReference;
import org.h2.tools.SimpleResultSet;


public class SheetReader {

	public static Boolean debug = false;

	public static ResultSet SheetRead(Connection conn, String FilePath,String SheetName)
	{
		SimpleResultSet rs = new SimpleResultSet();	
		try {
			InputStream inp = new FileInputStream(FilePath);
			Workbook wb = WorkbookFactory.create(inp);

			Sheet sheet = wb.getSheet(SheetName);

			int rowStart = sheet.getFirstRowNum();
			int rowEnd = sheet.getLastRowNum();
			int ColumnStart = 9999;
			int ColumnEnd = 0;

			//boolean colsFound = false;
			ArrayList<String> ColumnNames = new ArrayList<String>();
			ArrayList<Integer> ColumnTypes = new ArrayList<Integer>();
			//ArrayList<String> ColumnTypes = new ArrayList<String>();
			Row r;

			int rowCursor;
			int colCursor;

			//Finding column names
			//TODO: don't forget to set row start to the first row after column names
			for(rowCursor = rowStart;rowCursor<=rowEnd;rowCursor++)
			{

				if(rowCursor > rowEnd){System.out.println("Something went wrong"); break;}
				r = sheet.getRow(rowCursor);
				if(r == null){continue;}
				for(Cell cell : r){
					ColumnStart = Math.min(ColumnStart,cell.getColumnIndex());
					ColumnEnd 	= Math.max(ColumnEnd, cell.getColumnIndex());
					if(cell.getCellType() == Cell.CELL_TYPE_STRING){
						if(debug == true){System.out.println("Label "+ cell.toString() + " Found at :" + new CellReference(r.getRowNum(), cell.getColumnIndex()).formatAsString());}							
						ColumnNames.add(cell.toString());
					}	
				}

				//TODO: add additional data types especially for number, I will need to make a utility to identify integers from floats etc. Otherwise always double if pulling from sheet.
				rowCursor++;
				rowStart = rowCursor;
				r = sheet.getRow(rowCursor);

				for(colCursor = ColumnStart;colCursor <= ColumnEnd; colCursor++)
				{
					Cell cell = r.getCell(colCursor, Row.RETURN_BLANK_AS_NULL );
					switch (cell.getCellType()) {
					case Cell.CELL_TYPE_STRING:
						if(debug == true){System.out.println("Type VARCHAR " +cell.getRichStringCellValue().getString());}
						ColumnTypes.add(Types.VARCHAR);

						break;
					case Cell.CELL_TYPE_NUMERIC:
						if (DateUtil.isCellDateFormatted(cell)) {
							if(debug == true){System.out.println("Type DATE " + cell.getDateCellValue());}
							ColumnTypes.add(Types.DATE);
						} else {
							if(debug == true){System.out.println("Type NUMBER "+cell.getNumericCellValue());}
							ColumnTypes.add(Types.DOUBLE);
						}
						break;
					case Cell.CELL_TYPE_BOOLEAN:
						if(debug == true){System.out.println(cell.getBooleanCellValue());}
						ColumnTypes.add(Types.BOOLEAN);
						break;
					case Cell.CELL_TYPE_FORMULA:
						if(debug == true){System.out.println(cell.getCellFormula());}
						ColumnTypes.add(Types.VARCHAR);
						break;
					default:
						//TODO: throw exception
						if(debug == true){System.out.println("type unkown");}
						ColumnTypes.add(Types.VARCHAR);
					}
				}
				break;
			}

			//adding the columns to the result set
			//TODO:refactor to add columns in the case statement and set precision and scale based off the type of the cell.
			for(int i = 0; i<ColumnNames.size();i++)
			{
				rs.addColumn(ColumnNames.get(i), ColumnTypes.get(i), 1042, 0);	
			}

			//early out for just reading column names
			if(conn != null)
			{
				if(conn.getMetaData().getURL().equals("jdbc:columnlist:connection")){
					return rs;
				}
			}
			
			for(rowCursor = rowStart; rowCursor <= rowEnd; rowCursor++)
			{
				Object[] rowVals = new Object[Math.max(1+ColumnEnd - ColumnStart,0)];
				r = sheet.getRow(rowCursor);
				
				if(r == null){
					System.out.println("Row is null");//Early out for blank rows
					continue;
				}
				
				for(colCursor = ColumnStart;colCursor <= ColumnEnd; colCursor++)
				{
					//int offset = colCursor-ColumnStart;
					Cell c = r.getCell(colCursor, Row.RETURN_BLANK_AS_NULL);
					rowVals[colCursor-ColumnStart] = c.getStringCellValue();
				}
				rs.addRow(rowVals);
			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (EncryptedDocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return rs;
	}

}
