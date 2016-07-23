import java.util.regex.Pattern;

public class MetricWMC implements iMetrics{
	
	int methodCount = 0;
	int mcCabeApproximation = 0;

	static String pattern = "public.*(.*)|protected.*(.*)|private.*(.*)";
	static String mcCabePattern =".*[if|else|case|default|for|loop|while|break|continue|&&|throw|catch].*"; // Missing ||

	public MetricWMC(){
		
	}
	
	public Double getWMCScore(){
		return ((double)mcCabeApproximation)/methodCount;
	}
	
	public Double nextLine(String line){
    	if( Pattern.matches(pattern, line.trim())){	  
			// New method found
			methodCount++;
			mcCabeApproximation++; // Each method is worth at least 1.
		
		} else if (Pattern.matches(mcCabePattern, line.trim())){
			mcCabeApproximation++;
		}			
		return 0.0;
	}

	@Override
	public void resetAllValues() {
		methodCount = 0;
		mcCabeApproximation = 0;		
	}

}
