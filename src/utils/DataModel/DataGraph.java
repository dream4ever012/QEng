package utils.DataModel;

import java.util.ArrayList;
import qEng.InternalDB;

public class DataGraph implements DataModel {

	//HashMap<String,DataNode> test;
	ArrayList<DataNode> test;
	
	
	public DataGraph()
	{
	//	test = new HashMap<String,DataNode>();
		test = new ArrayList<DataNode>();
	}
	
	@Override
	public void addNode(DataNode n)
	{
		test.add(n);
	}
	
	@Override
	public void removeNode(String name)
	{
		
	}
	
	@Override
	public ArrayList<DataNode> getJoinable(String Name)
	{
		
		return null;
	}
	
	@Override
	public void addJoinCols(String Table1Name, String Table1Col, String Table2Name, String Table2Col)
	{
		
		
		
	}

	@Override
	public void populateNode(InternalDB db, String TableName, boolean TM) {
		// TODO Auto-generated method stub
		
	}
	
	
	
}
