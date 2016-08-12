package optimizer;

import optimizer.Parser.QueryObjectFactory;
import optimizer.Parser.QueryStatusObject;
import optimizer.Parser.SelectObject;
import optimizer.QuryImpls.DefaultQuery;

public class QueryFactory {

	//TODO: consider changing the returning defaultquery if is not select object to a thrown exception because ResolveQuery should only recieve Select statements.
	public static Query ResolveQuery(String SQL, QueryManager AW)
	{
		QueryStatusObject SQLObj = QueryObjectFactory.GetSQLObject(SQL);

		if(SQLObj instanceof SelectObject)
		{
			return new DefaultQuery();
		}else {
			return new DefaultQuery();
		}
	}


	public static Query ResolveQuery(SelectObject SQL, QueryManager AW)
	{
		return new DefaultQuery();
	}

	
}
