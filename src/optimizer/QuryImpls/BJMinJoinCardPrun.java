package optimizer.QuryImpls;

import java.io.File;

import optimizer.Query;
import qEng.InternalDB;

public class BJMinJoinCardPrun implements Query{

	@Override
	public File ExecuteQuery(String SQL, InternalDB internalDBImpl) {
		// TODO Auto-generated method stub
		// This scheme would optimize @ distributed settings. Join without much optimization
		// has to send
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
