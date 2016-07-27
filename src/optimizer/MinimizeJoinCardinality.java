package optimizer;

import java.io.File;

import qEng.InternalDB;

public class MinimizeJoinCardinality implements Query{

	@Override
	public File ExecuteQuery(String SQL, InternalDB internalDBImpl) {
		// TODO Auto-generated method stub
		// execute join first that minimize the cardinality of join 
		// NDV(the Number of Distinct Values in a column), histogram and match the top 100 of the and rank them....
		
		return null;
	}

}
