
public class mainforTesting {

	// excel file URL until I can make them dynamic jdbc:nilostep:excel:[FullPathToTheDirectoryContainingTheExcelFiles]
	
	
	
	public static void main(String[] args) {
		InternalDB myH2 = new InternalH2();
		myH2.createLink(null, null, null, null, null);
	}
}
