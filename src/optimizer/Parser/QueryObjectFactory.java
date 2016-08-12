package optimizer.Parser;

public class QueryObjectFactory {

	//TODO: evaluate weather or not we want to keep this factory, yes if we will allow other optimizations than for Select clauses
	//TODO: might combine this into the Query Object's functionality.
	//TODO: might want to add a feature that looks for stupid queries. ex. selecting all the columns from one table and none from others but joining ALL of them anyway.

	//TODO: what if we parse only the columns that are required in the result and the predicates and figure the rest out with the datamodel? 
	public static QueryStatusObject GetSQLObject(String SQL)
	{
		//SimpleParse(SQL);
		if(SQL != null){

			//return new SelectObject(SimpleParse(SQL));

			QueryStatusObject rt = new SelectObject();

			String[] firstsplit = SQL.split("FROM");
			String secondsplit = firstsplit[0].split("SELECT")[1]; 
			String[] Cols = secondsplit.split(",");

			if(Cols != null){
				rt.AddResultCols(Cols);
			}

			String[] Predicates = firstsplit[1].split("WHERE");

			if(Predicates.length > 1){
				//System.out.println("Preds i : " + Predicates[1]);
				rt.AddPredicates(Predicates[1]);

			}
			return rt;
		}


		return null;

	}


	/*private static String[] SimpleParse(String SQL)
	{
		String[] firstsplit = SQL.split("FROM");
		String secondsplit = firstsplit[0].split("SELECT")[1]; 
		String[] Cols = secondsplit.split(",");

		String[] Predicates = firstsplit[1].split("WHERE");

		if(Predicates.length > 1){
			//System.out.println("Preds i : " + Predicates[1]);

		}




		//	System.out.println(i + ",");



		return Cols;
	}
*/


}
