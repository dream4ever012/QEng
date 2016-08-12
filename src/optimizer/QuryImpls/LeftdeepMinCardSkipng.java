package optimizer.QuryImpls;

import java.io.File;

import optimizer.Query;
import qEng.InternalDB;

public class LeftdeepMinCardSkipng implements Query{

	@Override
	public File ExecuteQuery(String SQL, InternalDB internalDBImpl) {
		// TODO Auto-generated method stub
		// identifies tables without any predicates and run such join before 
		return null;
	}

	@Override
	public File ToXML() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public File ToRS() {
		// TODO Auto-generated method stub
		return null;
	}

}
