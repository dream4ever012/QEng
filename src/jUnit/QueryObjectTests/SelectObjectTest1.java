package jUnit.QueryObjectTests;

import org.junit.Test;

import ResourceStrings.SD;
import optimizer.Parser.QueryObjectFactory;
import optimizer.Parser.QueryStatusObject;
import optimizer.Parser.SelectObject;

public class SelectObjectTest1 {

	/*@Test
	public void SimpleConstruction1() {

		//		String SQLString = "SELECT " + SD.TMTableName + ".ClassName, " + SD.REQTableName + ".* " + 
		//				"FROM " + SD.TMTableName + " " + 
		//				"INNER JOIN " + SD.REQTableName + " " +
		//				"ON "+ SD.TMTableName + ".ID = "+ SD.REQTableName + ".ID;";

		SelectObject Test1 = new SelectObject();

		Test1.AddResultCols(SD.TMTableName + ".ClassName", SD.REQTableName + ".*");
		Test1.AddInvolvedTables(SD.TMTableName,SD.REQTableName);
		Test1.AddJoinConditions(SD.REQTableName + " " +	"ON "+ SD.TMTableName + ".ID = "+ SD.REQTableName + ".ID");

		System.out.println("SimpleConstruction1 "+Test1.GetSQL() + "\n\n");
	}

	@Test
	public void SimpleConstruction2()
	{
		//		SQLString = "SELECT " + SD.REQTableName + ".*, " + SD.CCTableName + ".*" + " " +
		//				"FROM " + SD.REQTableName + " " +
		//				"INNER JOIN " + SD.TMTableName + " " +
		//				"ON " + SD.TMTableName + ".ID = " + SD.REQTableName + ".ID" + " " +
		//				"INNER JOIN " + SD.CCTableName + " " +
		//				"ON " + SD.CCTableName + ".ClassName = " + SD.TMTableName + ".ClassName;";

		SelectObject Test2 = new SelectObject();

		Test2.AddResultCols(SD.REQTableName + ".*", SD.CCTableName + ".*");
		Test2.AddInvolvedTables(SD.REQTableName,SD.CCTableName,SD.TMTableName);
		Test2.AddJoinConditions(SD.TMTableName + " " + "ON " + SD.TMTableName + ".ID = " + SD.REQTableName + ".ID",
				SD.CCTableName + " " + "ON " + SD.CCTableName + ".ClassName = " + SD.TMTableName + ".ClassName");	

		System.out.println("Simpleconstruction2" + Test2.GetSQL() +  "\n\n");
		

		//TODO: Create a highlevel parser.
		//TODO: I need to create objects that represent the type of commands in SQL,
		//for example instead of breaking down purely into tokens I can break down join conditions
		//One object that has JoinType, Join Condition, and Table Being Joined.
		//Example "INNER JOIN " + SD.TMTableName + " " +
		//		"ON " + SD.TMTableName + ".ID = " + SD.REQTableName + ".ID" + " " +
		//Would be parsed to JoinType = INNER, Join Condition TMTableName.ID = REQTablename.ID;

	}
*/

	@Test
	public void ProposedQuickParse()
	{

		//what if we only parsed predicates and columns and did the rest based off the datamodel?
			String	SQLString = "SELECT " + SD.REQTableName + ".*, " + SD.CCTableName + ".*" + " " +
						"FROM " + SD.REQTableName + " " +
						"INNER JOIN " + SD.TMTableName + " " +
						"ON " + SD.TMTableName + ".ID = " + SD.REQTableName + ".ID" + " " +
						"INNER JOIN " + SD.CCTableName + " " +
						"ON " + SD.CCTableName + ".ClassName = " + SD.TMTableName + ".ClassName;";
		//would become
		
		SelectObject TestProposed = new SelectObject();
		TestProposed.AddResultCols(SD.REQTableName,SD.CCTableName);
		
		//System.out.println("Proposed Parse \n" + TestProposed.GetSQL() + "\n\n");
		
		//then things are rebuilt as needed.
		System.out.println("Actual Parse \n" + QueryObjectFactory.GetSQLObject(SQLString).GetSQL() + "\n\n\n");

	}

	@Test
	public void SimpleParseTest()
	{
		String SQLString = "SELECT " + SD.TMTableName + ".ClassName, " + SD.REQTableName + ".* " + 
				"FROM " + SD.TMTableName + " " + 
				"INNER JOIN " + SD.REQTableName + " " +
				"ON "+ SD.TMTableName + ".ID = "+ SD.REQTableName + ".ID;";

		QueryStatusObject myQueryStats = QueryObjectFactory.GetSQLObject(SQLString);

		System.out.println(myQueryStats.GetSQL());


	}
	
	@Test 
	public void ParseTest2()
	{
	
		String SQLString = "SELECT * " +
				"FROM " + SD.CCTableName5k + " " +
				"INNER JOIN " + SD.TMTableName5k + " " + 
				"ON " + SD.TMTableName5k + ".ClassName = " + SD.CCTableName5k + ".ClassName " +
				"WHERE " + "FAULTPRONE(" + SD.CCTableName5k + ".CLASSES) = 1 "+ " AND " + SD.CCTableName5k + ".CREATEDBY = 'Caleb';";
		
		QueryStatusObject myQueryStats = QueryObjectFactory.GetSQLObject(SQLString);

		System.out.println(myQueryStats.GetSQL());
		//QueryObjectFactory.FullParseSplitting(SQLString);
	}
	
	
	//TODO: set it up to schedule the UDF after the other predicate is applied.
	@Test
	public void FullParseTest()
	{
		String SQLString = "SELECT " + SD.CCTableName5k + ".codeclass, " + SD.TMTableName5k + ".* " + 
				"FROM " + SD.CCTableName5k + " " +
				"INNER JOIN " + SD.TMTableName5k + " " + 
				"ON " + SD.TMTableName5k + ".ClassName = " + SD.CCTableName5k + ".ClassName " +
				"WHERE " + "FAULTPRONE(" + SD.CCTableName5k + ".CLASSES) = 1 "+ " AND " + SD.CCTableName5k + ".CREATEDBY = 'Caleb' " + 
				"ORDER BY "+ SD.CCTableName5k + ".codeclass;";
		
		
		//QueryObjectFactory.FullParseSplitting(SQLString);
		QueryObjectFactory.FullParseTokenize(SQLString);
		
	}

}
