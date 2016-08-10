package utils.DataModel;

import java.util.ArrayList;

public interface DataNode {
	
	public ArrayList<DataNode> getJoinable();
	public ArrayList<String> JoinableCols();
	public void AddJoin(DataNode n, String Column);
	
	
	

}
