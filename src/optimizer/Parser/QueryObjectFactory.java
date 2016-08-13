package optimizer.Parser;

public class QueryObjectFactory {

	//TODO: evaluate weather or not we want to keep this factory, yes if we will allow other optimizations than for Select clauses
	//TODO: might combine this into the Query Object's functionality.
	//TODO: might want to add a feature that looks for stupid queries. ex. selecting all the columns from one table and none from others but joining ALL of them anyway.

	//TODO: what if we parse only the columns that are required in the result and the predicates and figure the rest out with the datamodel? 
	public static QueryStatusObject GetSQLObject(String SQL)
	{
		
		if(SQL.startsWith("SELECT")){
		QueryStatusObject SelectObject = FullParseTokenize(SQL);
		return SelectObject;
		}else{return null;}

	}

	
	//TODO: expand to catch goupby and orderby
	public static void FullParseSplitting(String SQL)
	{
		QueryStatusObject rt = new SelectObject();

		String[] firstsplit = SQL.split("FROM");
		String secondsplit = firstsplit[0].split("SELECT")[1]; 
		String[] Cols = secondsplit.split(",");

		if(Cols != null){
			rt.AddResultCols(Cols);
		}

		String[] thirdSplit = firstsplit[1].split("WHERE");
	
		if(thirdSplit.length >= 1){
			//System.out.println("Preds i : " + Predicates[1]);

			String Predicates[] = thirdSplit[1].split("ORDER"); 
			if(Predicates.length>=1){
				rt.AddPredicates(Predicates[0]);
			} else {rt.AddPredicates(firstsplit[1]);}

		}
		System.out.println("Full Parse ");
		System.out.println(rt.GetSQL());

	}
	
	//TODO: finish this
	public static QueryStatusObject HybridFullParse(String SQL)
	{
		System.out.println("LinearFullParse");
		//QueryStatusObject rt = new SelectObject();
		String[] firstsplit = SQL.split("FROM");
		String secondsplit = firstsplit[0].split("SELECT")[1]; 
		String[] Cols = secondsplit.split(",");
		
		//rt.AddResultCols(Cols);
		
		QueryStatusObject rt = new SelectObject(Cols);
		
		String[] Predicates = firstsplit[1].split("WHERE");
		
		
		
		return rt;
	}
	
	//TODO: split into multiple loops, one that handles until the table after From then the rest of the tokens in a separate loop to avoid unecessary checks.
	public static QueryStatusObject FullParseTokenize(String SQL)
	{
		String[] tokens = SQL.split(",|;|\\s");
		//System.out.println("Full Parse Tokenizer");
//		for(String i: tokens){
//			System.out.println(i);	
//		}
		//getting column tokens.
		
		//Test token runner, I'm hoping to do some internal increments and quick outs.
		//need to REGEX for looking for UDFS, I'm going to get them as a full token currently but I want to create predicate objects that contain the operator and the left and right conditions.
		
		QueryStatusObject rt = new SelectObject();
		
		int i;
		int start=0,end=0;
		for(i = 0; i<tokens.length;i++)
		{
			if(tokens[i].equalsIgnoreCase("SELECT"))
			{
				++i;
				start = i;
				//System.out.println("SELECT FOUND");
				continue;
			}
			
			if(tokens[i].equalsIgnoreCase("FROM"))
			{
				end = i-1;
				for(int k = start; k<=end;k++){
					rt.AddResultCols(tokens[k]);
				}
				
				++i;
				rt.AddInvolvedTables(tokens[i]);
				//System.out.println("FROM FOUND");
				continue;
			}
			
			if(tokens[i].equalsIgnoreCase("JOIN"))
			{
				//System.out.println("JOIN FOUND");
				JoinObj jObj = new JoinObj();
				if(tokens[i-1].equalsIgnoreCase("INNER")){
					//System.out.println("INNER JOIN TYPE");
					jObj.setType("INNER JOIN");
				}
				if(tokens[i-1].equalsIgnoreCase("LEFT")){
					//System.out.println("LEFT JOIN TYPE");
					jObj.setType("LEFT JOIN");
				}
				
				if(tokens[i-1].equalsIgnoreCase("RIGHT"))
				{
					//jObj.setType("RIGHT JOIN");
					System.out.println("RIGHT JOIN TYPE");
				}
				
				if(tokens[i-1].equalsIgnoreCase("OUTER"))
				{
					//System.out.println("FULL OUTER JOIN TYPE");
					jObj.setType("FULL OUTER JOIN");
				}
				
				if(tokens[i+2].equalsIgnoreCase("ON"))
				{
					
					//System.out.println("Joining Table " + tokens[i+1]);
					jObj.setTable(tokens[i+1]);
					while(!tokens[i].equals("="))
					{
						++i;
					}
					jObj.setLeft(tokens[i-1]);
					jObj.setRight(tokens[i+1]);
					++i;
					rt.AddJoins(jObj);
				}
				continue;
			}
			
			//TODO: complete the rest of this method. keeping in mind join may be in where clause.
			//TODO: do without join recog first.
			//TODO: do check's in where for UDF's
			//TODO: create Where Objects.
			if(tokens[i].compareToIgnoreCase("WHERE")==0)
			{
				System.out.println("WHERE FOUND");
				//Search for AND, OR, 
			}
			
			if(tokens[i].compareToIgnoreCase("GROUP")==0)
			{
				System.out.println("GROUP FOUND");
			}
			
			if(tokens[i].compareToIgnoreCase("ORDER")==0)
			{
				System.out.println("ORDER FOUND");
			}
			
		}
		return rt;	
	}

}


//I can't beleive that basically works.
//TODO: add calls to a specialized fullparse to parse through some of the subsections for completeness of parse.
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




