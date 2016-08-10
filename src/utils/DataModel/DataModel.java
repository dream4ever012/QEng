package utils.DataModel;

import java.util.ArrayList;

import qEng.InternalDB;

public interface DataModel {

	void addNode(DataNode n);

	void removeNode(String name);

	ArrayList<DataNode> getJoinable(String Name);

	void addJoinCols(String Table1Name, String Table1Col, String Table2Name, String Table2Col);

	void populateNode(InternalDB db, String TableName, boolean TM);

	
	
	
}
