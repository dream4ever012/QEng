import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;


class ComputeMetrics {
	
	double threshold;
	
	MetricLOC mLOC; // Lines of Code
	MetricNCOM mNCOM; // Number of comments
	MetricWMC mWMC; // Weighted methods per class
	
	double LOC = 0.0;
	double NCOM = 0.0;
	double WMC = 0.0;
	
	double faultProneScore = 0.0;
	
	String filename;
	
	public ComputeMetrics(double threshold){
		mLOC = new MetricLOC();
		mNCOM = new MetricNCOM();
		mWMC = new MetricWMC();
		this.threshold = threshold;
	}
	
	public double getFaultProneScore(){
		return faultProneScore;
	}
	
	private void resetAllValues(){
		LOC = 0.0;
		NCOM = 0.0;
		WMC = 0.0;		
		faultProneScore = 0.0;
		mLOC.resetAllValues();
		mNCOM.resetAllValues();
		mWMC.resetAllValues();
	}
	
	boolean isFaultProne(String filename){
		resetAllValues();
		parseFile(filename);
		this.filename = filename;
		faultProneScore = ((LOC*0.1) + NCOM + WMC);
		if(	 faultProneScore > threshold)
			return true;
		else
			return false;		
	}
	
	public void parseFile(String filename){
		// 
		//This will reference one line at a time
        String line = null;

        try {
            // FileReader reads text files in the default encoding.
            FileReader fileReader = 
                new FileReader(filename);

            // Always wrap FileReader in BufferedReader.
            BufferedReader bufferedReader = 
                new BufferedReader(fileReader);

            while((line = bufferedReader.readLine()) != null) {
                //System.out.println(line);
                
                // Increment Metrics as necessary
                if(line.trim().length() > 1)
        			LOC += mLOC.nextLine(line);
        		 	NCOM += mNCOM.nextLine(line);
        		 	mWMC.nextLine(line); // This one just computes it internally.
            }   

            // Always close files.
            bufferedReader.close();         
        }
        catch(FileNotFoundException ex) {
            System.out.println(
                "Unable to open file '" + 
                filename + "'");   
            
        }
        catch(IOException ex) {
            System.out.println(
                "Error reading file '" 
                + filename + "'");                  
        }
	}
	
	// Non essential - for testing
	public void displayMetrics(){
		System.out.println("LOC: " + LOC);
		System.out.println("NCOM: " + NCOM);
		System.out.println("WMC: " + mWMC.getWMCScore());
	}

}
