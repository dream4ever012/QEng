public class TestQueries {

	static final String TQ001 = "SELECT ID as RequirementID " +
			"FROM Requirements " +
			"WHERE category=\"Basal flow rate\" or \"Sensors\"";

	static final String TQ002 = "SELECT ClassName " + 
			"FROM CodeClass " + 
			"WHERE Version = \"V3.1\"";

	static final String TQ003 = "SELECT ID " +
			"FROM Requirements " +
			"WHERE Date Created >= \"12-15-15\" AND Date Created < \"12-31-16\"";

}