package optimizer.Parser;

import java.util.ArrayList;

public class SelectObject implements QueryStatusObject {


	ArrayList<String> SelectCols;
	ArrayList<String> InvolvedTables;
	ArrayList<String> Predicates;
	ArrayList<String> JoinConditions;
	

	public SelectObject()
	{
		SelectCols = new ArrayList<String>();
		InvolvedTables = new ArrayList<String>();
		Predicates = new ArrayList<String>();
		JoinConditions = new ArrayList<String>();
	}

	public SelectObject(String...ResultColumns) {

		SelectCols = new ArrayList<String>();
		InvolvedTables = new ArrayList<String>();
		Predicates = new ArrayList<String>();
		JoinConditions = new ArrayList<String>();
		AddResultCols(ResultColumns);
		
		for(String i : ResultColumns)
		{
			//String Table = i.split("\\.")[0];
			//System.out.println("Table " + Table + " added");
			//InvolvedTables.add(Table);
			InvolvedTables.add(i.split("\\.")[0]);
		}

	}

	@Override
	public boolean ParseSQL(String SQL) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public String GetSQL(){
		String SQL = "SELECT ";

		for(int i = 0; i<SelectCols.size();i++)
		{
			SQL = SQL + SelectCols.get(i);
			if(i!=SelectCols.size()-1)
			{
				SQL = SQL + ",";
			} else { SQL = SQL + "\n";}
		}

		if(InvolvedTables.size()>0){
			SQL = SQL + "FROM ";

			for(int i = 0; i<InvolvedTables.size();i++)
			{
				SQL = SQL + InvolvedTables.get(i);
				if(i!=InvolvedTables.size()-1)
				{
					SQL = SQL + ",";
				} else { SQL = SQL + "\n";}
			}
		}
		
		if(JoinConditions.size()>0){
			for(int i = 0; i<JoinConditions.size();i++)
			{
				SQL = SQL + "JOIN " + JoinConditions.get(i) + "\n";
			}
		}
		if(Predicates.size()>0){
			SQL = SQL + "WHERE ";

			for(int i = 0; i<Predicates.size();i++)
			{
				SQL = SQL + Predicates.get(i);
				if(i!=Predicates.size())
				{
					SQL = SQL + ",";
				} else { SQL = SQL + "\n";}
			}
		}
		return SQL;
	}

	@Override
	public boolean AddResultCols(String... Columns) {

		for(String i: Columns)
		{
			SelectCols.add(i);
		}

		return true;
	}

	@Override
	public boolean AddInvolvedTables(String... Tables) {


		for(String i: Tables)
		{
			InvolvedTables.add(i);
		}

		return true;
	}

	@Override
	public boolean AddJoinConditions(String... Conditions) {


		for(String i: Conditions)
		{
			JoinConditions.add(i);
		}

		return true;
	}

	@Override
	public boolean AddPredicates(String... Preds) {


		for(String i: Preds)
		{
			Predicates.add(i);
		}

		return true;
	}

}
