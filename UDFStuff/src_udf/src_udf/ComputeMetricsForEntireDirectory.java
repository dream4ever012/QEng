
import java.io.File;

public class ComputeMetricsForEntireDirectory{
	
	ComputeMetrics computeMetrics;
	String fileName;
	
	ComputeMetricsForEntireDirectory(double threshold) {
		computeMetrics = new ComputeMetrics(threshold);
	};
	
	
	
    public void walk( String path ) {
    	

        File root = new File( path );
        File[] list = root.listFiles();

        if (list == null) return;

        for ( File f : list ) {
            if ( f.isDirectory() ) {
                walk( f.getAbsolutePath() );
            }
            else{
              //System.out.println( "File:" + f.getAbsoluteFile() );
              String fileName = f.getAbsoluteFile().toString();
              if(fileName.contains(".java")){
              	boolean faultProne = computeMetrics.isFaultProne(fileName);
              	if(faultProne)
              		System.out.println("Faultprone: " + faultProne + "; Score: " + computeMetrics.getFaultProneScore() + "; " + fileName);
              }
            }
        }
    }
}