package optimizer.Parser;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

public class SelectObject implements QueryStatusObject {


	ArrayList<String> SelectCols;
	//ArrayList<String> InvolvedTables;
	ArrayList<String> Predicates;
	ArrayList<String> JoinConditions;
	ArrayList<String> Ordering;
	ArrayList<String> Grouping;
	ArrayList<JoinObj> Joins;
	Set<String> InvolvedTables;

	public SelectObject()
	{
		SelectCols = new ArrayList<String>();
		//InvolvedTables = new ArrayList<String>();
		Joins = new ArrayList<JoinObj>();
		InvolvedTables = new TreeSet<String>();
		Predicates = new ArrayList<String>();
		JoinConditions = new ArrayList<String>();
	}

	public SelectObject(String...ResultColumns) {

		SelectCols = new ArrayList<String>();
		//InvolvedTables = new ArrayList<String>();
		Joins = new ArrayList<JoinObj>();
		InvolvedTables = new TreeSet<String>();
		Predicates = new ArrayList<String>();
		JoinConditions = new ArrayList<String>();
		AddResultCols(ResultColumns);

		for(String i : ResultColumns)
		{
			//String Table = i.split("\\.")[0];
			//System.out.println("Table " + Table + " added");
			//InvolvedTables.add(Table);
			if(i!= "*"){
				InvolvedTables.add(i.split("\\.")[0]);
			}
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

		//TODO: rewrite this to return just the first table from Involved tables so the string returned can be used by a default query object.
		if(InvolvedTables.size()>0){
			SQL = SQL + "FROM ";
			Iterator<String> i = InvolvedTables.iterator();

			while(i.hasNext()){
				SQL = SQL + i.next();	
				if(i.hasNext()){SQL = SQL + ",";} else {SQL = SQL + "\n";}
			}

			/*	for(int i = 0; i<InvolvedTables.size();i++)
			{
				SQL = SQL + ;
				if(i!=InvolvedTables.size()-1)
				{
					SQL = SQL + ",";
				} else { SQL = SQL + "\n";}
			}*/
		}

		if(JoinConditions.size()>0){
			for(int i = 0; i<JoinConditions.size();i++)
			{
				SQL = SQL + JoinConditions.get(i) + "\n";
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
	public void AddResultCols(String... Columns) {

		for(String i: Columns)
		{
			if(i!=null && !i.equals("")){
				SelectCols.add(i);
				if(!i.equals("*") && !i.equalsIgnoreCase("COUNT(*)")){
					InvolvedTables.add(i.split("\\.")[0]);
				}
			}
		}


	}

	@Override
	public void AddInvolvedTables(String... Tables) {


		for(String i: Tables)
		{
			if(i!=null && !i.equals("")){
				InvolvedTables.add(i);
			}
		}
	}

	@Override
	public void AddJoinConditions(String... Conditions) {


		for(String i: Conditions)
		{
			if(i!=null && !i.equals("")){
				JoinConditions.add(i);
			}
		}
	}

	@Override
	public void AddPredicates(String... Preds) {


		for(String i: Preds)
		{
			if(i!=null && !i.equals("")){
				Predicates.add(i);
			}
		}
	}

	@Override
	public void AddOrdering(String...Ordering)
	{

		for(String i: Ordering)
		{
			if(i!=null && !i.equals("")){
				this.Ordering.add(i);
			}
		}
	}

	@Override
	public void AddGrouping(String...Grouping)
	{

		for(String i: Grouping)
		{
			if(i!=null && !i.equals("")){
				this.Grouping.add(i);
			}
		}

	}

	@Override
	public void AddJoins(JoinObj...Joins){

		for(JoinObj i : Joins){
			if(i!=null){
				this.Joins.add(i);
				this.JoinConditions.add(i.getSQL());
			}
		}

	}
}

