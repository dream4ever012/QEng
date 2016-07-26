package optimizer;

import java.io.File;

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
	
}
