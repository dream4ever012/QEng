package optimizer.QuryImpls;

import java.io.File;

import optimizer.Query;
import qEng.InternalDB;
import utils.FileUtility;

public class DefaultQuery implements Query{
	
	@Override
	public File ExecuteQuery(String SQL, InternalDB internalDBImpl) {
		// TODO Auto-generated method stub
		File fileRef = FileUtility.quickXMLFile("./temp/");
		internalDBImpl.QueryToXML(SQL, fileRef);
		return fileRef;
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
