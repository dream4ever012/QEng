package optimizer.QuryImpls;

import java.io.File;

import optimizer.Query;
import qEng.InternalDB;

public class LeftdeepMintimePrun implements Query{

	@Override
	public File ExecuteQuery(String SQL, InternalDB internalDBImpl) {
		// TODO Auto-generated method stub
		// prunning unecessary columns and rows at each join operation
		
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