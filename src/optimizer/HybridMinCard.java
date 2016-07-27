package optimizer;

import java.io.File;

import qEng.InternalDB;

public class HybridMinCard implements Query{

	@Override
	public File ExecuteQuery(String SQL, InternalDB internalDBImpl) {
		// TODO Auto-generated method stub
		// first join w/ cheapest cost and then find the next cheapest join left deep join
		// method join cost required
		
		return null;
	}

}
