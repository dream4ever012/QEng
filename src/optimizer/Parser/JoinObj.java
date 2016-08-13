package optimizer.Parser;

public class JoinObj {

	//TODO: enum jointype
	String JoinType;
	String Table;
	String LeftSide;
	String RightSide;
	
	public JoinObj()
	{
		
	}
	
	public JoinObj(String type,String table,String left,String right)
	{
		JoinType = type;
		Table = table;
		LeftSide = left;
		RightSide = right;	
	}
	
	public String getSQL()
	{
		return JoinType + " " + Table + " ON " + LeftSide + " = " + RightSide;
	}
	
	public void setTable(String table){
		Table = table;
	}
	
	public void setType(String type){
		JoinType=type;
	}
	
	public void setLeft(String left){
		LeftSide = left;
	}
	
	public void setRight(String right){
		RightSide = right;
	}
	
}
