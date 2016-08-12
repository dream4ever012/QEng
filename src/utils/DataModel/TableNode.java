package utils.DataModel;

import java.util.ArrayList;

public class TableNode implements DataNode{

	//TODO: convert from ArrayLists to Arrays or vectors.
	//TODO: consider making a Column Object that has the ColumnName and the meta data about the column.
	boolean TM;
	ArrayList<String> ColumnNames;
	ArrayList<DataNode> Joinable;
	//Location of data
	//Size of data 
	//other table stats
	
	public TableNode(String TableName)
	{
		
	}
	
	public void Refresh()
	{
		
	}

	@Override
	public ArrayList<DataNode> getJoinable() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<String> JoinableCols() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void AddJoin(DataNode n, String Column) {
		Joinable.add(n);
		ColumnNames.add(Column);
		
	}
	

	
	
}
