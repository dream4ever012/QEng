package optimizer.QuryImpls;

import java.io.File;

import optimizer.Query;
import qEng.InternalDB;

public class HybridMinCard implements Query{

	@Override
	public File ExecuteQuery(String SQL, InternalDB internalDBImpl) {
		// TODO Auto-generated method stub
		// first join w/ cheapest cost and then find the next cheapest join left deep join
		// method join cost required
		
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
