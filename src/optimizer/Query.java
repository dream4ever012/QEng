package optimizer;

import java.io.File;

import qEng.InternalDB;

public interface Query {
	
	public File ExecuteQuery(String SQL, InternalDB internalDBImpl);
	
	

}
