package optimizer.Parser;

public interface QueryStatusObject {

	
	//a single object to hold information about the query during the course of it's execution.
	/* holds things like a list of tables involved in a query, that will be altered throughout the execution
	 * as sub query's are processed the tables that are no longer needed are removed from the list, and the result of the sub query is added to the list.
	 * processing is complete when only one table is in the list and any final predicates or UDFs can be applied. 
	 * 
	 * would need to create a parser to tokenize and categorize the elements of the SQL query,
	 *  we can then use template statements like Select [injected from object] From [injected] Join [injected] etc.
	 * however I think we should persue a query factory that does not utalize the status object first, but instead calculates what it needs only as needed.
	 * my first design may prove to be better as it would be easier to multithread but this might perform better in a single threaded environment. 
	 */
	
	
	
	public boolean ParseSQL(String SQL);

	String GetSQL();

	boolean AddResultCols(String... Columns);
	boolean AddInvolvedTables(String...Tables);
	boolean AddJoinConditions(String...Conditions);
	boolean AddPredicates(String...Preds);
	
}
