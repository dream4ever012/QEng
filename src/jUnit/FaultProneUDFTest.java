package jUnit;

import java.io.File;

import org.junit.Before;
import org.junit.Test;

import qEng.InternalDB;
import qEng.InternalH2;
import utils.MeasureCostArbitrary;


public class FaultProneUDFTest {

	private static final String XLDriver = "com.nilostep.xlsql.jdbc.xlDriver"; // 
	private static final String XLURLBase = "jdbc:nilostep:excel:./SecondData/"; //

	
//	private static final String REQTableName = "\"Requirements.ReqSheet\"";
//	private static final String CCTableName = "\"codeclasses.codeclass\"";	
	private static final String TMTableName = "CC_REQ_TM";
	
	private static final String REQTableNameTC1 = "\"RequirementsTC1.ReqSheet\"";
	private static final String CCTableNameTC = "\"codeclassTC.codeclass\"";
	private static final String CCTableNameTC1 = "\"codeclassTC1.codeclass\"";
	private static final String CCTableName5k = "\"codeclass5k.codeclass\"";
	private static final String TMTableNameTC1 = "CC_REQ_TMTC1";
	private static final String TMTableNameTC2 = "CC_REQ_TMTC2";
	private static final String TMTableName5k = "CC_REQ_TM5k";
	InternalDB myDB = new InternalH2();
	
	String SQLString;
	
	@Before
	public void init()
	{
		
		//myDB.createLink(XLDriver, XLURLBase, null,null, CCTableNameTC);
	}
	
	@Test
	public void test()
	{	
		
		myDB.RegisterCompiledUDF("FAULTPRONE", "src.UDF.isFaultProne");
		/*
		File TQ1212 = new File("./results/TQ1212.xml");
		SQLString =
				//"SELECT FAULTPRONE(" +  CCTableName5k + ".CLASSES) AS FAULTPRONE " +
				"SELECT count(*) " +
				"FROM " + CCTableName5k + ";"; //+ ";";// 
				//"WHERE " + CCTableName5k + ".CREATEDBY IS NOT NULL;";
		MeasureCostArbitrary.measureCostArbitrary(myDB, SQLString, TQ1212);
		myDB.QueryToXML(SQLString, TQ1212);
		
		File TQ1213 = new File("./results/TQ1213.xml");
		SQLString =
				//"SELECT FAULTPRONE(" +  CCTableName5k + ".CLASSES) AS FAULTPRONE " +
				"SELECT count(*) " +
				"FROM " + TMTableName5k + ";"; //+ ";";// 
				//"WHERE " + CCTableName5k + ".CREATEDBY IS NOT NULL;";
		MeasureCostArbitrary.measureCostArbitrary(myDB, SQLString, TQ1213);
		myDB.QueryToXML(SQLString, TQ1213);
		
		
		File TQ12 = new File("./results/TQ12.xml");
		SQLString =  //"DROP TABLE TQ112 IF EXISTS; CREATE TABLE TQ112 AS " + //TEMPORARY
				"SELECT * " +
				"FROM " + CCTableName5k + " " +
				"INNER JOIN " + TMTableName5k + " " + 
				"ON " + TMTableName5k + ".ClassName = " + CCTableName5k + ".ClassName " +
				"WHERE " + CCTableName5k + ".CREATEDBY = 'Caleb';";
		MeasureCostArbitrary.measureCostArbitrary(myDB, SQLString, TQ12);
		myDB.QueryToXML(SQLString, TQ12);
		*/
		
		File TQudfWhere = new File("./results/TQudfWhere.xml");
		SQLString =  //"DROP TABLE TQ112 IF EXISTS; CREATE TABLE TQ112 AS " + //TEMPORARY
				"SELECT * " +
				"FROM " + CCTableName5k + " " +
				"INNER JOIN " + TMTableName5k + " " + 
				"ON " + TMTableName5k + ".ClassName = " + CCTableName5k + ".ClassName " +
				"WHERE " + "FAULTPRONE(" + CCTableName5k + ".CLASSES) = 1 "+ " AND " + CCTableName5k + ".CREATEDBY = 'Caleb';";
		MeasureCostArbitrary.measureCostArbitrary(myDB, SQLString, TQudfWhere);
		myDB.QueryToXML(SQLString, TQudfWhere);

		
		
	}
	
	
}
