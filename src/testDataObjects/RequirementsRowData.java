package testDataObjects;


public class RequirementsRowData {
	
	public String ID;
	public String Type;
	public String Class;
	public String Category;
	public String Descripton;
	public String DateCreated;
	public String Author;
	public String Priority;
	
	RequirementsRowData(String id, String type, String clss, String cat,
						String desc, String dc, String auth, String pri)
	{
	ID = id;
	Type = type;
	Class = clss;
	Category = cat;
	Descripton = desc;
	DateCreated = dc;
	Author = auth;
	Priority = pri;
	}
}
