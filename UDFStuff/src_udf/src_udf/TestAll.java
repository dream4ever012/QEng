public class TestAll{

    public static void main(String[] args) {
        // Call Compute metrics on one class only.
        ComputeMetricsForOneClass();
    	
        // Just for illustrative purposes - this calls it on an entire directory.
	// You won't need this though.
    	ComputeMetricsForEntireDirectory allFilesMetrics = new ComputeMetricsForEntireDirectory(75.0);
    	allFilesMetrics.walk("c:\\Cassandra22\\src");
    }   
    
    // This shows how to call it for one particularly .java file 
    public static void ComputeMetricsForOneClass() {

	// Create a ComputeMetrics Object.  You can just do this one time.
	// We can set the threshold.  75 looks about right for now.
	ComputeMetrics computeMetrics = new ComputeMetrics(75.0); 

	// Call this line for each individual file.
	String fileName = "c:\\Cassandra22\\src\\java\\org\\apache\\cassandra\\utils\\SortedBiMultiValMap.java";
	boolean faultProne = computeMetrics.isFaultProne(fileName);
		System.out.println("Faultprone: " + faultProne + " Score: " + computeMetrics.getFaultProneScore());
	}

}